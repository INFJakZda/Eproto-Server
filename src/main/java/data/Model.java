package data;

import model.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import java.util.*;

public class Model {
    private volatile static Model instance;
    private volatile static Datastore datastore;
    private IdDB idDB = IdDB.getInstance();
    
    private static int studentIndex = 0;
    private static int gradeIndex = 0;

    private Model() {
    }

    public static Model getInstance() {
        if (instance == null) {
            synchronized (Model.class) {
                if (instance == null) {
                    instance = new Model();
                    datastore = MongoDB.getInstance().getDatastore();
                }
            }
        }
        return instance;
    }

    // **** COURSES ****
    public Collection<Course> getCourses(String professorParam, String nameParam) {

        Query<Course> query = datastore.createQuery(Course.class);

        Optional<String> professor = Optional.ofNullable(professorParam);
        Optional<String> name = Optional.ofNullable(nameParam);
        professor.ifPresent(s -> query.and(query.criteria("professor").containsIgnoreCase(s)));
        name.ifPresent(s -> query.and(query.criteria("name").containsIgnoreCase(s)));

        return query.asList();
    }

    public Course getCourse(ObjectId id) {
        return datastore.find(Course.class, "id", id).get();
    }

    public Course addCourse(Course object) {
        Key<Course> key = datastore.save(object);
        object.setId((ObjectId) key.getId());
        return object;
    }

    public void updateCourse(Course object) {
//        Course current = datastore.find(Course.class, "id", object.getId()).get();
//        object.setId(current.getId());
        datastore.save(object);
    }

    public boolean deleteCourse(ObjectId id) {
        Course course = this.getCourse(id);
        if (course == null) {
            return false;
        } else {
            datastore.delete(datastore.find(Course.class, "id", id));
            return true;
        }
    }

    // **** STUDENTS ****
    public Collection<Student> getStudents() {
        try {
            Query<Student> query = datastore.createQuery(Student.class);
            return query.asList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student getStudent(int id) {
        return datastore.find(Student.class, "index", id).get();
    }

    public Student addStudent(Student object) {
        object.setIndex(idDB.getNewStudentIndex());
        datastore.save(object);
        System.out.println(object);
        return object;
    }

    public void updateData(Student student) {
        Student current = datastore.find(Student.class, "index", student.getIndex()).get();
        student.setIndex(current.getIndex());
        student.setId(current.getId());
        datastore.save(student);
    }

    public boolean deleteStudent(int id) {
        Student student = this.getStudent(id);
        if (student == null) {
            return false;
        } else {
            datastore.delete(datastore.find(Student.class, "index", id));
            return true;
        }

    }

    public boolean containsData(int id) {
        return !datastore.find(Student.class, "index", id).asList().isEmpty();
    }

    // **** GRADES ****
    public boolean deleteGrade(int idg, int id) {
//        Grade grade = getGrade(id, idg);
//        if (grade != null) {
//            Student student = students.get(id);
//            ArrayList<Grade> grades = student.getGrades();
//            grades.remove(grade);
//            student.setGrades(grades);
//            return true;
//        }
        return false;
    }

    public Grade getGrade(int studentId, int gradeId){
//        Student student = students.get(studentId);
//        if (student != null) {
//            ArrayList<Grade> grades = student.getGrades();
//            for (Grade grade: grades)
//                if (grade.getId() == gradeId)
//                    return grade;
//        }
        return null;
    }

    public ArrayList<Grade> getGrades(int id) {
//        Student student = students.get(id);
//        if (student != null) {
//            return student.getGrades();
//        }
        return null;
    }

    public int getGradeIndex() {
        gradeIndex++;
        return gradeIndex;
    }

    public boolean addGrade(Grade grade, int id){
//        Student student = students.get(id);
//        if(student != null) {
//            grade.setId(getGradeIndex());
//            int courseId = grade.getCourse().getId();
//            Course course = courses.get(courseId);
//            if (course == null) {
//                return false;
//            }
//            grade.setCourse(course);
//            ArrayList<Grade> grades = student.getGrades();
//            grades.add(grade);
//            student.setGrades(grades);
//            return true;
//        }
        return false;
    }
}
