package data;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDB {

    private volatile static MongoDB instance;
    private Datastore datastore;

    private MongoDB() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        Morphia morphia = new Morphia();
        datastore = morphia.createDatastore(mongo, "university");
        morphia.mapPackage("model");
    }

    public static MongoDB getInstance() {
        if (instance == null) {
            synchronized (MongoDB.class) {
                if (instance == null) {
                    instance = new MongoDB();
                }
            }
        }
        return instance;
    }

    protected Datastore getDatastore() {
        return datastore;
    }
}
