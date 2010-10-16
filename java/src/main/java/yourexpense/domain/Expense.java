package yourexpense.domain;

import javax.jdo.annotations.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Expense {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Float amount;

    @Persistent
    private String category;

    @Persistent
    private String comment;

    @Persistent
    private Date date;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
