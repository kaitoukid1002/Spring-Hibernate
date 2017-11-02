package vn.viettel.browser.webservice.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.viettel.browser.webservice.service.DashboardService;

import java.util.ArrayList;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.controller : NewsWebService
 */
@RestController
public class DashboardController {
    private final
    DashboardService dashboardService;


    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping(value = "/query_chart", method = RequestMethod.POST)
    public JSONObject queryChart(@RequestBody String input) {
        JSONObject response;
        response = dashboardService.queryChart(input.trim());
        return response;
    }

    @RequestMapping(value = "/query_top", method = RequestMethod.POST)
    public ArrayList<JSONObject> queryTop(@RequestBody String input) {
        ArrayList<JSONObject> response;
        response = dashboardService.queryTop(input.trim());
        return response;
    }
}
