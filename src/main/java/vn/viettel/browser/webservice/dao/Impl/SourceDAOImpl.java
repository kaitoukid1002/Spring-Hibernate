package vn.viettel.browser.webservice.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.viettel.browser.webservice.dao.SourceDAO;
import vn.viettel.browser.webservice.entity.SourceEntity;

import java.util.ArrayList;

import vn.viettel.browser.webservice.global.Variables;

import javax.persistence.TypedQuery;

/**
 * Created by quytx on 01/11/17.
 * vn.viettel.browser.webservice.dao.Impl : NewsWebService
 */
@Repository
public class SourceDAOImpl implements SourceDAO {


    @Override
    public ArrayList<SourceEntity> getListAllowSource(Session session) {
        ArrayList<SourceEntity> sourceEntities = new ArrayList<>();
        try{
            TypedQuery<SourceEntity> query = (TypedQuery<SourceEntity>) session.createQuery("from SourceEntity where status= :status");
            query.setParameter("status", 1);
            sourceEntities = (ArrayList<SourceEntity>) query.getResultList();
        }catch (Exception e){
            Variables.logger.warning(e.getMessage());
        }

        return sourceEntities;
    }
}
