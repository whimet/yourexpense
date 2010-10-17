package yourexpense.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import yourexpense.domain.User;
import yourexpense.domain.UserDao;

import static java.lang.String.format;

@Controller
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView handleRegister() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView register(User user) {
        if (userDao.findByName(user.getName()) != null) {
            return new ModelAndView("register", "error", format("User[%s] already exists!", user.getName()));
        }

        userDao.save(user);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "login_error", required = false) String loginError) {
        return new ModelAndView("login", "loginError", loginError);
    }
}
