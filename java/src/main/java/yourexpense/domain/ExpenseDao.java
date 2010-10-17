package yourexpense.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseDao {
    @Autowired
    private PersistenceManagerFactory factory;
    @Autowired
    private UserDao userDao;

    public List<Expense> findAll() {
        List<Expense> list;
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            Query query = pm.newQuery(Expense.class);
            query.setOrdering("date desc");
            List<Expense> result = (List<Expense>) query.execute();
            list = new ArrayList<Expense>(result);
            query.closeAll();
        } finally {
            pm.close();
        }
        return list;
    }
}
