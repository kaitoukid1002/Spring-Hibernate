package vn.viettel.browser.webservice.service;

import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.service : NewsWebService
 */
public interface DashboardService {
    JSONObject queryChart(String input);

    ArrayList<JSONObject> queryTop(String input);
}
