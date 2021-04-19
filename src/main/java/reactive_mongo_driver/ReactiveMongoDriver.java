package reactive_mongo_driver;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import rx.Observable;
import static com.mongodb.client.model.Filters.eq;

public class ReactiveMongoDriver {


    private static final String DATABASE = "db";
    private static final String USER_COLLECTION = "user";
    private static final String PRODUCT_COLLECTION = "product";

    private static MongoClient client = createMongoClient();

    private MongoCollection<Document> getUserCollection() {
        return client.getDatabase(DATABASE).getCollection(USER_COLLECTION);
    }

    private MongoCollection<Document> getProductCollection() {
        return client.getDatabase(DATABASE).getCollection(PRODUCT_COLLECTION);
    }

    public Observable<Success> addUser(User user) {
        return getUserCollection().insertOne(user.toDocument());
    }

    public Observable<Success> addProduct(Product product) {
        return getProductCollection().insertOne(product.toDocument());
    }

    public Observable<Product> getAllProducts() {
        return getProductCollection().find().toObservable().map(Product::new);
    }

    public Observable<User> getUser(int id) {
        return getUserCollection().find(eq("id", id)).toObservable().map(User::new);
    }

    private static Observable<User> getAllUsers(MongoCollection<Document> collection) {
        return collection.find().toObservable().map(User::new);
    }

    private static MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    private static void getPrintln(User user) {
        System.out.println(user);
    }
}
