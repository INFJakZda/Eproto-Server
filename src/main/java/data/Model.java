package data;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
    private volatile static Model instance;

    private Model() {
    }

    public static Model getInstance() {
        if (instance == null) {
            synchronized (Model.class) {
                if (instance == null) {
                    instance = new Model();
                }
            }
        }
        return instance;
    }

//    private Map<Integer, Student> students = new HashMap<>();
    private Map<Integer, Course> courses = new HashMap<>();

//    public Map<Integer, Student> getStudents(){
//        return this.students;
//    }
//
//    public void addStudent(Student student){
//        students.put(student.getIndex(), student);
//    }


    public ArrayList<Course> getCourses(){
        return new ArrayList<>(this.courses.values());
    }

    public Course getCourse(int id) {
        return courses.getOrDefault(id, null);
    }

    public boolean checkCourseId(Course course) {
        if (courses.containsKey(course.getId())) {
            return false;
        }
        else
            return true;
    }

    public boolean deleteCourse(int id) {
        if(courses.remove(id) != null)
            return true;
        else
            return false;
    }

    public void addCourse(Course course){
        this.courses.put(course.getId(), course);
    }


//    public Grade getGrade(int studentId, int gradeId){
//        Student student = students.get(studentId);
//        if (student != null) {
//            Grade grade = student.getGrades().get(gradeId);
//            if (grade != null)
//                return grade;
//        }
//        return null;
//    }
}
