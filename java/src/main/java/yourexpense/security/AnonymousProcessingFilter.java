package yourexpense.security;

import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.memory.UserAttribute;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
public class AnonymousProcessingFilter
        extends org.springframework.security.providers.anonymous.AnonymousProcessingFilter {

    public AnonymousProcessingFilter() {
        final UserAttribute initialAttribute = new UserAttribute();
        initialAttribute.setPassword("anonymousUser");
        initialAttribute.setAuthorities(asList(new GrantedAuthorityImpl("ROLE_ANONYMOUS")));
        setUserAttribute(initialAttribute);
        setKey("anonymousKey");
    }
}
