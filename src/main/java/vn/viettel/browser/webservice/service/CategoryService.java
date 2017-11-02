package vn.viettel.browser.webservice.service;

import vn.viettel.browser.webservice.entity.CategoryEntity;

import java.util.ArrayList;

/**
 * Created by quytx on 01/11/17.
 * vn.viettel.browser.webservice.service : NewsWebService
 */
public interface CategoryService {

    ArrayList<CategoryEntity> getAll();
}
