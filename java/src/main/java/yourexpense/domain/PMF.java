package yourexpense.domain;

import org.springframework.beans.factory.FactoryBean;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class PMF implements FactoryBean<PersistenceManagerFactory> {

    private static final PersistenceManagerFactory pmfInstance =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");


    public PersistenceManagerFactory getObject() throws Exception {
        return pmfInstance;
    }

    public Class<?> getObjectType() {
        return PersistenceManagerFactory.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
