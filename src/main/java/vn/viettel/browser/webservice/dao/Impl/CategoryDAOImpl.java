package vn.viettel.browser.webservice.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.viettel.browser.webservice.dao.CategoryDAO;
import vn.viettel.browser.webservice.entity.CategoryEntity;
import vn.viettel.browser.webservice.entity.SourceEntity;

import java.util.ArrayList;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.dao.Impl : NewsWebService
 */

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public ArrayList<CategoryEntity> getAll(Session session) {
        return (ArrayList<CategoryEntity>)session.createQuery("from CategoryEntity ").list();
    }
}
