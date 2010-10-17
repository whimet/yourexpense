package yourexpense.security;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        
        if ("anonymousUser".equals(principal)) {
            return null;
        }

        UserDetails user = (UserDetails) principal;
        return user.getUsername();
    }
}
