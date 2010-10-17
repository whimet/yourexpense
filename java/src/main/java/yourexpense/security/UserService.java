package yourexpense.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import yourexpense.domain.User;
import yourexpense.domain.UserDao;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public String currentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        
        if ("anonymousUser".equals(principal)) {
            return null;
        }

        UserDetails user = (UserDetails) principal;
        return user.getUsername();
    }

    public User currentUser() {
        String name = currentUserName();
        if (name != null) {
            return userDao.findByName(name);
        }
        return null;
    }
}
