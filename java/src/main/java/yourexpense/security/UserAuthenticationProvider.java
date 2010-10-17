package yourexpense.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.userdetails.UserDetails;
import yourexpense.domain.User;
import yourexpense.domain.UserDao;

public class UserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private UserDao userDao;
    private static final String ROLE_REGISTERED_USER = "registered_user";

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        User user = userDao.find(username);
        if (user == null) {
            throw new AuthenticationServiceException(String.format("No such user with name: %s", username));
        }
        GrantedAuthority[] grantedAuthorities = {new GrantedAuthorityImpl(ROLE_REGISTERED_USER)};
        return new org.springframework.security.userdetails.User(user.getName(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }
}
