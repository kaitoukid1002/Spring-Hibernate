package vn.viettel.browser.webservice.service.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.viettel.browser.webservice.dao.CategoryDAO;
import vn.viettel.browser.webservice.entity.CategoryEntity;
import vn.viettel.browser.webservice.entity.SourceEntity;
import vn.viettel.browser.webservice.global.Variables;
import vn.viettel.browser.webservice.service.CategoryService;
import vn.viettel.browser.webservice.utils.HibernateUtility;

import java.util.ArrayList;

/**
 * Created by quytx on 01/11/17.
 * vn.viettel.browser.webservice.service.Impl : NewsWebService
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public ArrayList<CategoryEntity> getAll() {
        ArrayList<CategoryEntity> categoryEntities = new ArrayList<>();
        SessionFactory factory = HibernateUtility.getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            if (!session.beginTransaction().isActive()) {
                session.beginTransaction().begin();
            }
            categoryEntities = categoryDAO.getAll(session);
        }catch (Exception e){
            Variables.logger.warning(e.getMessage());
        }
        return categoryEntities;
    }
}
