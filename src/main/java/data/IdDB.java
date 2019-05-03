package data;

import model.LastId;
import org.mongodb.morphia.Datastore;

public class IdDB {

    private volatile static IdDB instance;
    private volatile static Datastore datastore;

    private IdDB() {
        datastore = MongoDB.getInstance().getDatastore();
    }

    public static IdDB getInstance() {
        if (instance == null) {
            synchronized (IdDB.class) {
                if (instance == null) {
                    instance = new IdDB();
                }
            }
        }
        return instance;
    }

    Integer getNewGradeId() {
        return getAndIncrement("gradeId").getGradeId();
    }

    Integer getNewStudentIndex() {
        return getAndIncrement("studentIndex").getStudentIndex();
    }

    private LastId getAndIncrement(String field) {
        if (datastore.createQuery(LastId.class).asList().isEmpty()) {
            datastore.save(new LastId(0, 0));
            return new LastId(0, 0);
        }
        return datastore.findAndModify(
                    datastore.find(LastId.class).limit(1),
                    datastore.createUpdateOperations(LastId.class).inc(field)
        );
    }
}
