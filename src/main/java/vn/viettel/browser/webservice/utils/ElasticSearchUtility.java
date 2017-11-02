package vn.viettel.browser.webservice.utils;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.jetbrains.annotations.Contract;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.viettel.browser.webservice.entity.CategoryEntity;
import vn.viettel.browser.webservice.entity.SourceEntity;
import vn.viettel.browser.webservice.global.Variables;
import vn.viettel.browser.webservice.service.CategoryService;
import vn.viettel.browser.webservice.service.SourceService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static vn.viettel.browser.webservice.global.Variables.*;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.utils : NewsWebService
 */
@Repository
public class ElasticSearchUtility {
    private static Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
    @Autowired
    private SourceService sourceService;
    @Autowired
    private CategoryService categoryService;

    @Contract(pure = true)
    public JSONObject queryNewsCategory(long timeStart, long timeEnd, long duration, ArrayList<String> filter, String type) {
        JSONObject responseData = new JSONObject();
        ArrayList<JSONObject> jsonList = new ArrayList<>();
        ArrayList<JSONObject> itemsList = new ArrayList<>();
        ArrayList<Long> times = new ArrayList<>();
        if (filter.size() == 0) {
            String urlElasticSearchPost = Variables.urlElasticSearchPost;
            long timeNext = timeStart - 1;
            int checkBreak = 0;
            // Query get Top 30
            String endDataPost;
            if (type.equals("source")) {
                endDataPost = "\"aggs\": {\"get_value\": {\"terms\": {\"field\": \"" + type + "\", \"size\": "
                        + maxSource + ",\"exclude\" : \"vn\"}}}}";
            } else {
                endDataPost = "\"aggs\": {\"get_value\": {\"terms\": {\"field\": \"" + type + "\", \"size\": "
                        + maxSource + "}}}}";
            }

            String dataPost = "{\"size\": 0,\"query\": {\"range\" : { \"time_post\" : {";
            dataPost += " \"gte\" : " + timeStart + ", \"lte\" :" + timeEnd + "}}}," + endDataPost;
            WebTarget rootTarget = client.target(urlElasticSearchPost);
            Response response = rootTarget.request() // Build request
                    .post(Entity.json(dataPost)); // Call get method
            if (response.getStatus() == 200) {
                jsonList = parseReponseCount(response);
            }
            for (JSONObject jsonData : jsonList) {
                JSONObject jsonDataPut = new JSONObject();
                jsonDataPut.put("key", jsonData.get("key"));
                jsonDataPut.put("total", jsonData.get("doc_count"));
                jsonDataPut.put("values", new ArrayList<Integer>());
                itemsList.add(jsonDataPut);
            }
            JSONParser parser = new JSONParser();
            JSONObject json;

            // Query detail time
            timeNext = timeNext + 1;
            while (true) {
                timeStart = timeNext;
                timeNext = timeStart + duration;
                if (timeNext >= timeEnd) {
                    timeNext = timeEnd;
                    checkBreak = 1;
                }
                // Query follow source
                for (JSONObject item : itemsList) {
                    urlElasticSearchPost = Variables.urlElasticSearch;
                    ArrayList<Integer> value = (ArrayList<Integer>) item.get("values");
                    String filteredElement = item.get("key").toString();
                    if (!times.contains(timeNext)) times.add(timeNext);
                    urlElasticSearchPost += "time_post:[" + timeStart + "%20TO%20" + timeNext + "]";
                    urlElasticSearchPost += "%20AND%20" + type + ":" + filteredElement;
//                    url_search += "%20AND%20source:"+ filter_element;
                    rootTarget = client.target(urlElasticSearchPost);
                    response = rootTarget.request().get();
                    if (response.getStatus() == 200) {
                        try {
                            json = (JSONObject) parser.parse(response.readEntity(JSONObject.class).toString());
                            json = (JSONObject) parser.parse(json.get("hits").toString());
                            int total_local = Integer.valueOf(json.get("total").toString());
                            value.add(total_local);
                            item.put("values", value);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (checkBreak == 1) break;
            }
            responseData.put("times", times);
            responseData.put("items", itemsList);

        } else {
            filter = new ArrayList<>();
            if (type.equals("source")) {
                ArrayList<SourceEntity> sourceEntities = sourceService.getListAllowSource();
                for (SourceEntity entity:sourceEntities) {
                    filter.add(entity.getName());
                }
            } else {
                ArrayList<CategoryEntity> categoryEntities = categoryService.getAll();
                for (CategoryEntity entity : categoryEntities)
                    filter.add(String.valueOf(entity.getId()));
            }

            JSONParser parser = new JSONParser();
            JSONObject json;
            String urlSearch;
            long timeNext = timeStart - 1;
            int checkBreak = 0;

            // Query get source in filter_list
            for (String filteredElement : filter) {
                urlSearch = urlElasticSearch;
                JSONObject dataJsonPut = new JSONObject();
                urlSearch += "time_post:[" + timeStart + "%20TO%20" + timeEnd + "]";
                urlSearch += "%20AND%20" + type + ":" + filteredElement;
//                url_search += "%20AND%20source:"+ filter_element;
                WebTarget rootTarget = client.target(urlSearch);
                Response response = rootTarget.request().get();
                if (response.getStatus() == 200) {
                    try {
                        json = (JSONObject) parser.parse(response.readEntity(JSONObject.class).toString());
                        json = (JSONObject) parser.parse(json.get("hits").toString());
                        int totalLocal = Integer.valueOf(json.get("total").toString());
                        dataJsonPut.put("key", filteredElement);
                        dataJsonPut.put("total", totalLocal);
                        dataJsonPut.put("values", new ArrayList<Integer>());
                        itemsList.add(dataJsonPut);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Query detail time
            timeNext = timeNext + 1;
            while (true) {
                timeStart = timeNext;
                timeNext = timeStart + duration;
                if (timeNext >= timeEnd) {
                    timeNext = timeEnd;
                    checkBreak = 1;
                }
                if (checkBreak == 1) break;
                responseData.put("times", times);
                responseData.put("items", itemsList);
            }
        }
        return responseData;
    }

    private static ArrayList<JSONObject> parseReponseCount(Response response) {
        ArrayList<JSONObject> jsonList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject json;
        JSONArray msg;
        try {
            json = (JSONObject) parser.parse(response.readEntity(JSONObject.class).toString());
            json = (JSONObject) parser.parse(json.get("aggregations").toString());
            json = (JSONObject) parser.parse(json.get("get_value").toString());
            msg = (JSONArray) parser.parse(json.get("buckets").toString());
            for (Object aMsg : msg) {
                JSONObject value = (JSONObject) aMsg;
                jsonList.add(value);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonList;
    }

    @Contract(pure = true)
    public JSONObject queryDuplicate(Long time_start, Long time_end, Long duration, ArrayList<String> filter, String duplicated) {
        JSONObject reponseData = new JSONObject();

        return reponseData;
    }

    @Contract(pure = true)
    public static ArrayList<JSONObject> queryWebsite(Long time, Long query_size, Long duration) {
        return new ArrayList<>();
    }

    @Contract(pure = true)
    public static ArrayList<JSONObject> queryCategory(Long time, Long query_size, Long duration) {
        return new ArrayList<>();
    }

    @Contract(pure = true)
    public static ArrayList<JSONObject> queryUrl(Long time, Long query_size, Long duration) {
        return new ArrayList<>();
    }


}
