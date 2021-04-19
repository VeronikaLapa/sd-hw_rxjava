package server;

import reactive_mongo_driver.*;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class Command {

    static ReactiveMongoDriver driver = new ReactiveMongoDriver();

    static Observable<String> process(String action, Map<String, List<String>> queryParameters) {
        switch (action) {
            case "addProduct":
                return addProduct(queryParameters);
            case "addUser":
                return addUser(queryParameters);
            case "getProducts":
                return getProducts(queryParameters);
            default:
                throw new RuntimeException("Bad request");
        }
    }
    private static Observable<String> getProducts(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get("id").get(0));
        return driver
                .getUser(id)
                .map(User::getCurrency)
                .flatMap(currency -> driver.getAllProducts()
                        .map(product -> product.showProduct(currency)));

    }

    private static Observable<String> addUser(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get("id").get(0));
        Currency currency = Currency.valueOf(queryParameters.get("currency").get(0));
        return driver.addUser(new User(id, currency))
                .map(success -> "New User added");
    }

    private static Observable<String> addProduct(Map<String, List<String>> queryParameters) {
        int id = Integer.parseInt(queryParameters.get("id").get(0));
        String name = queryParameters.get("name").get(0);
        Price price = new Price(Double.parseDouble(queryParameters.get("price").get(0)),
                Currency.valueOf(queryParameters.get("currency").get(0)));
        return driver.addProduct(new Product(id, name, price))
                .map(success -> "New Product added");
    }

}