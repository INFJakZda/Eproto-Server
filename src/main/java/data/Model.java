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

            Collection<Student> students = getStudents(null, null, null, 0);
            for (Student student : students) {

                ArrayList<Grade> grades = student.getGrades();
                if (grades != null) {
//                    grades.removeIf(grad -> grad.getCourse().getId() == id);
                    for(Grade grade : grades) {
                        if (grade.getCourse().getId().equals(id)) {
                            deleteGrade(grade.getId(), student.getIndex());
                        }
                    }
                }
//                datastore.save(student);
            }
            datastore.delete(datastore.find(Course.class, "id", id));
            return true;
        }
    }

    // **** STUDENTS ****
    public Collection<Student> getStudents(String nameParam, String surnameParam, Date dateParam, int order) {
        try {
            Query<Student> query = datastore.createQuery(Student.class);

            Optional<String> name = Optional.ofNullable(nameParam);
            Optional<String> surname = Optional.ofNullable(surnameParam);
            Optional<Date> dateQuery = Optional.ofNullable(dateParam);

            name.ifPresent(s -> query.and(query.criteria("name").containsIgnoreCase(s)));
            surname.ifPresent(s -> query.and(query.criteria("surname").containsIgnoreCase(s)));
            System.out.println(dateParam);
//            if (dateParam != null) {
            if (order < 0)
                dateQuery.ifPresent(date -> query.and(query.criteria("birthDate").lessThan(date)));
            else if (order == 0)
                dateQuery.ifPresent(date -> query.and(query.criteria("birthDate").equal(date)));
            else
                dateQuery.ifPresent(date -> query.and(query.criteria("birthDate").greaterThan(date)));

//            }

            List<Student> list = query.asList();

//            index.ifPresent(i -> list.removeIf(item -> !String.valueOf(item.getIndex()).contains(i.toString())));

            return list;
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

    public void updateStudent(Student student) {
//        Student current = datastore.find(Student.class, "index", student.getIndex()).get();
//        student.setIndex(current.getIndex());
//        student.setId(current.getId());
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
        Grade grade = getGrade(id, idg);
        if (grade != null) {
            Student student = getStudent(id);
            student.getGrades().removeIf(grad -> grad.getId() == grade.getId());
            datastore.save(student);
            return true;
        }
        return false;
    }

    public Grade getGrade(int studentId, int gradeId){
        Student student = getStudent(studentId);
        if (student != null) {
            ArrayList<Grade> grades = student.getGrades();
            if (grades != null) {
                for (Grade grade: grades)
                    if (grade.getId() == gradeId)
                        return grade;
            }
        }
        return null;
    }

    public Collection<Grade> getGrades(int id) {
        Student student = getStudent(id);
        if (student != null) {
            Collection<Grade> grades = student.getGrades();
            return grades;
        }
        return null;
    }

//    public Collection<Grade> getGrades(int idS, ObjectId courseIdParam, float valueParam, int orderParam) {
//        Student student = getStudent(idS);
//        if (student != null) {
//            Optional<ObjectId> courseId = Optional.ofNullable(courseIdParam);
//            Optional<Float> value = Optional.ofNullable(valueParam);
//
//            Collection<Grade> grades = student.getGrades();
//
//            courseId.ifPresent(id -> grades.removeIf(grade -> !grade.getCourse().getId().equals(id)));
//            if (orderParam < 0)
//                value.ifPresent(f -> grades.removeIf(grade -> grade.getValue() < f));
//            if (orderParam == 0)
//                value.ifPresent(f -> grades.removeIf(grade -> grade.getValue() == f));
//            else
//                value.ifPresent(f -> grades.removeIf(grade -> grade.getValue() > f));
//            return grades;
//        }
//        return null;
//    }

    public boolean updateGrade(int id, Grade object, Grade old) {
        Student student = getStudent(id);
        Course course = getCourse(old.getCourse().getId());
        if (student != null && course != null) {
            object.setCourse(course);
            student.getGrades().removeIf(grade -> grade.getId() == object.getId());
            student.getGrades().add(object);
            datastore.save(student);
            return true;
        }
        return false;
    }

    public boolean addGrade(Grade grade, int id){
        Student student = getStudent(id);
        if(student != null) {
            grade.setId(idDB.getNewGradeId());
            ObjectId courseId = grade.getCourse().getId();
            Course course = getCourse(courseId);
            if (course == null) {
                return false;
            }
            grade.setCourse(course);
            ArrayList<Grade> grades = student.getGrades();
            if (grades == null) {
                grades = new ArrayList<>();
            }
            grades.add(grade);
            student.setGrades(grades);
            updateStudent(student);
            return true;
        }
        return false;
    }
    public void addGradeId(Grade grade){
        grade.setId(idDB.getNewGradeId());
    }
}
