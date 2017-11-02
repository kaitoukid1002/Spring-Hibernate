package vn.viettel.browser.webservice.dao;

import org.hibernate.Session;
import vn.viettel.browser.webservice.entity.SourceEntity;

import java.util.ArrayList;

/**
 * Created by quytx on 31/10/17.
 * vn.viettel.browser.webservice.dao : NewsWebService
 */
public interface SourceDAO {

    ArrayList<SourceEntity> getListAllowSource(Session session);
}
