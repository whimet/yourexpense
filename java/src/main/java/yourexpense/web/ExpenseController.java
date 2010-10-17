package yourexpense.web;

import com.google.appengine.api.datastore.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import yourexpense.domain.Expense;
import yourexpense.domain.ExpenseDao;
import yourexpense.domain.User;
import yourexpense.domain.UserDao;
import yourexpense.security.UserService;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
public class ExpenseController {
    @Autowired
    private PersistenceManagerFactory factory;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ExpenseDao expenseDao;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index", "username", userService.currentUserName());
    }

    @RequestMapping("expenses")
    public ModelAndView expenses() {
        return new ModelAndView("expenses", "user", userService.currentUser());
    }

    @RequestMapping(value = "expense", method = RequestMethod.GET)
    public ModelAndView get() {
        return new ModelAndView("expense", "expense", new Expense());
    }

    @RequestMapping(value = "expense", method = RequestMethod.POST)
    public ModelAndView createExpense(Expense expense) {
        if (expense.getDate() == null) {
            expense.setDate(new Date());
        }
        User user = userService.currentUser();
        Key key = user.getKey();
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            User userInThisSession = pm.getObjectById(User.class, key);
            expense.setUser(userInThisSession);
            pm.makePersistent(expense);
        } finally {
            pm.close();
        }

        return new ModelAndView("redirect:/expenses");
    }
}
