package data;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model {
    private volatile static Model instance;
    private static int studentIndex = 0;
    private static int gradeIndex = 0;

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

    private Map<Integer, Student> students = new HashMap<>();
    private Map<Integer, Course> courses = new HashMap<>();

    public ArrayList<Student> getStudents(){
        return new ArrayList<>(this.students.values());
    }

    public void addStudent(Student student){
        studentIndex++;
        student.setIndex(studentIndex);
        students.put(studentIndex, student);
    }

    public ArrayList<Course> getCourses(){
        return new ArrayList<>(this.courses.values());
    }

    public Course getCourse(int id) {
        return courses.getOrDefault(id, null);
    }

    public Student getStudent(int id) {
        return students.getOrDefault(id, null);
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

    public boolean deleteStudent(int id) {
        if(students.remove(id) != null)
            return true;
        else
            return false;
    }

    public void addCourse(Course course){
        this.courses.put(course.getId(), course);
    }


    public Grade getGrade(int studentId, int gradeId){
        Student student = students.get(studentId);
        if (student != null) {
            Grade grade = student.getGrades().get(gradeId);
            if (grade != null)
                return grade;
        }
        return null;
    }

    public ArrayList<Grade> getGrades(int id) {
        Student student = students.get(id);
        return student.getGrades();
    }

    public int getGradeIndex() {
        gradeIndex++;
        return gradeIndex;
    }

    public boolean addGrade(Grade grade, int id){
        Student student = students.get(id);
        if(student != null) {
            grade.setId(getGradeIndex());
            ArrayList<Grade> grades = student.getGrades();
            grades.add(grade);
            student.setGrades(grades);
            return true;
        }
        return false;
    }
}
