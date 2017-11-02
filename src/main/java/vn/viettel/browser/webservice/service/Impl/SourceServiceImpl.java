package vn.viettel.browser.webservice.service.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.viettel.browser.webservice.dao.SourceDAO;
import vn.viettel.browser.webservice.entity.SourceEntity;
import vn.viettel.browser.webservice.service.SourceService;
import vn.viettel.browser.webservice.utils.HibernateUtility;

import java.util.ArrayList;
import vn.viettel.browser.webservice.global.Variables;
/**
 * Created by quytx on 01/11/17.
 * vn.viettel.browser.webservice.service.Impl : NewsWebService
 */
@Service("sourceService")
public class SourceServiceImpl implements SourceService {

    @Autowired
    SourceDAO sourceDAO;

    @Override
    public ArrayList<SourceEntity> getListAllowSource() {
        ArrayList<SourceEntity> sourceEntities = new ArrayList<>();
        SessionFactory factory = HibernateUtility.getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            if (!session.beginTransaction().isActive()) {
                session.beginTransaction().begin();
            }
            sourceEntities = sourceDAO.getListAllowSource(session);
        }catch (Exception e){
            Variables.logger.warning(e.getMessage());
        }
        return sourceEntities;
    }
}
