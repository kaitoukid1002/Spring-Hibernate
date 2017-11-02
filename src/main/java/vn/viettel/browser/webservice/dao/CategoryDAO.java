package vn.viettel.browser.webservice.dao;

import org.hibernate.Session;
import vn.viettel.browser.webservice.entity.CategoryEntity;

import java.util.ArrayList;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.dao : NewsWebService
 */

public interface CategoryDAO {

    ArrayList<CategoryEntity> getAll(Session session);
}
