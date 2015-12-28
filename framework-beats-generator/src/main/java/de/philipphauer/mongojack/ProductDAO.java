package de.philipphauer.mongojack;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.mongojack.JacksonDBCollection;

import java.net.UnknownHostException;

public class ProductDAO {

    public void save(Product product) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));
        DB db = mongoClient.getDB("test");
        DBCollection collection = db.getCollection("products");
        JacksonDBCollection<Product, String> productCollection = JacksonDBCollection.wrap(collection, Product.class,
                String.class);
        productCollection.insert(product);
        mongoClient.close();
    }
}
