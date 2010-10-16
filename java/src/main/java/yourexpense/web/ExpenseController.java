package yourexpense.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import yourexpense.domain.Expense;
import yourexpense.domain.PMF;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Controller
public class ExpenseController {
    @Autowired
    private PersistenceManagerFactory factory;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/")
    public ModelAndView index() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            Query query = pm.newQuery(Expense.class);
            query.setOrdering("date desc");
            List<Expense> result = (List<Expense>) query.execute();
            ArrayList<Expense> list = new ArrayList<Expense>(result);
            map.put("expenses", list);
            query.closeAll();
        } finally {
            pm.close();
        }
        return new ModelAndView("index", map);
    }

    @RequestMapping(value = "expense", method = RequestMethod.GET)
    public ModelAndView get(Expense expense) {
        return new ModelAndView("expense");
    }

    @RequestMapping(value = "expense", method = RequestMethod.POST)
    public ModelAndView post(Expense expense) {
        if (expense.getDate() == null) {
            expense.setDate(new Date());
        }
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            pm.makePersistent(expense);
        } finally {
            pm.close();
        }

        return new ModelAndView("redirect:/");
    }
}
