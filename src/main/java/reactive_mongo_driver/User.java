package reactive_mongo_driver;

import org.bson.Document;

public class User {
    public final int id;


    public final Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public User(Document doc) {
        this(doc.getDouble("id").intValue(), Currency.valueOf(doc.getString("currency")));
    }

    public User(int id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                '}';
    }
    public Document toDocument() {
        return new Document("id", id)
                .append("currency", currency.toString());
    }


}