package vn.viettel.browser.webservice.service.Impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import vn.viettel.browser.webservice.service.DashboardService;
import vn.viettel.browser.webservice.utils.ElasticSearchUtility;

import java.util.ArrayList;

import static vn.viettel.browser.webservice.utils.ElasticSearchUtility.*;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.service.Impl : NewsWebService
 */
@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService {
    public JSONObject queryChart(String input) {
        ElasticSearchUtility elasticSearchUtility = new ElasticSearchUtility();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) parser.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonArray = new JSONObject();
        if (jsonObject != null && !jsonObject.isEmpty()) {
            Long timeStart, timeEnd, duration;
            String type;
            ArrayList<String> filter;
            try {
                timeStart = Long.valueOf(jsonObject.get("time_start").toString());
                timeEnd = Long.valueOf(jsonObject.get("time_end").toString());
                duration = Long.valueOf(jsonObject.get("duration").toString());
                type = jsonObject.get("type").toString();
                filter = (ArrayList<String>) jsonObject.get("filter");
            } catch (Exception e) {
                e.printStackTrace();
                return jsonArray;
            }
            switch (type) {
                case "news":
                    jsonArray = elasticSearchUtility.queryNewsCategory(timeStart, timeEnd, duration, filter, "source");
                    break;
                case "duplicate":
                    jsonArray = elasticSearchUtility.queryDuplicate(timeStart, timeEnd, duration, filter, "duplicated");
                    break;
                case "category":
                    jsonArray = elasticSearchUtility.queryNewsCategory(timeStart, timeEnd, duration, filter, "category.id");
                    break;
            }
            return jsonArray;
        }
        return jsonArray;
    }

    public ArrayList<JSONObject> queryTop(String input) {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = new JSONObject();
        try {
            jsonData = (JSONObject) parser.parse(input);
        } catch (ParseException ignored) {

        }
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        Long time, duration, querySize;
        String type;
        try {
            time = Long.valueOf(jsonData.get("time").toString());
            querySize = Long.valueOf(jsonData.get("query_size").toString());
            duration = Long.valueOf(jsonData.get("duration").toString());
            type = jsonData.get("type").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return arrayList;
        }

        switch (type) {
            case "url":
                arrayList = queryUrl(time, querySize, duration);
                break;
            case "category":
                arrayList = queryCategory(time, querySize, duration);
                break;
            case "website":
                arrayList = queryWebsite(time, querySize, duration);
                break;
        }
        return arrayList;
    }
}
