package vn.viettel.browser.webservice.service;

import vn.viettel.browser.webservice.entity.SourceEntity;

import java.util.ArrayList;

/**
 * Created by quytx on 01/11/17.
 * vn.viettel.browser.webservice.service : NewsWebService
 */
public interface SourceService {
    ArrayList<SourceEntity> getListAllowSource();
}
