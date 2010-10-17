package yourexpense.domain;

import javax.jdo.annotations.*;
import java.util.List;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class User {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    @Unique
    private String name;

    @Persistent
    private String password;

    @Persistent(mappedBy = "user", defaultFetchGroup = "true")
    @Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="date desc"))
    private List<Expense> expenses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Key getKey() {
        return key;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
