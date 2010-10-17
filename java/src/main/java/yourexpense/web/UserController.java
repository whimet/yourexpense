package yourexpense.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import yourexpense.domain.Expense;
import yourexpense.domain.User;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

@Controller
public class UserController {
    @Autowired
    private PersistenceManagerFactory factory;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView get() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView post(User user) {
        PersistenceManager pm = factory.getPersistenceManager();
        try {
            pm.makePersistent(user);
        } finally {
            pm.close();
        }

        return new ModelAndView("redirect:/");
    }
}
