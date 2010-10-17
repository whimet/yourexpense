package yourexpense.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private PersistenceManagerFactory factory;

    public User findByName(String username) {
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            Query query = pm.newQuery(User.class);
            query.setFilter("name == username");
            query.declareParameters("String username");

            List<User> list = (List<User>) query.execute(username);
            try {
                if (!list.isEmpty()) {
                    return list.get(0);
                }
            } finally {
                query.closeAll();
            }
        } finally {
            pm.close();
        }
        return null;
    }

    public void save(User user) {
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            pm.makePersistent(user);
        } finally {
            pm.close();
        }
    }

    public User find(Long userId) {
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            return pm.getObjectById(User.class, userId);
        } finally {
            pm.close();
        }
    }
}
