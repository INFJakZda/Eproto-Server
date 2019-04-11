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

    // **** COURSES ****
    private Map<Integer, Course> courses = new HashMap<>();

    public ArrayList<Course> getCourses(){
        return new ArrayList<>(this.courses.values());
    }

    public Course getCourse(int id) {
        return courses.getOrDefault(id, null);
    }

    public void addCourse(Course course){
        this.courses.put(course.getId(), course);
    }

    public boolean checkCourseId(Course course) {
        if (courses.containsKey(course.getId())) {
            return false;
        }
        else
            return true;
    }

    public boolean deleteCourse(int id) {
        if(courses.remove(id) != null) {
            for (Student student : students.values()) {
                student.getGrades().removeIf(x -> x.getCourse().getId() == id);
//                for(Grade grade: student.getGrades()) {
//                    grade.getCourse().getId().r
//                }
            }
            return true;
        }
        else
            return false;
    }

    // **** STUDENTS ****
    private Map<Integer, Student> students = new HashMap<>();

    public ArrayList<Student> getStudents(){
        return new ArrayList<>(this.students.values());
    }

    public void addStudent(Student student){
        studentIndex++;
        student.setIndex(studentIndex);
        students.put(studentIndex, student);
    }

    public Student getStudent(int id) {
        return students.getOrDefault(id, null);
    }

    public boolean deleteStudent(int id) {
        if(students.remove(id) != null)
            return true;
        else
            return false;
    }

    // **** GRADES ****
    public boolean deleteGrade(int idg, int id) {
        Grade grade = getGrade(id, idg);
        if (grade != null) {
            Student student = students.get(id);
            ArrayList<Grade> grades = student.getGrades();
            grades.remove(grade);
            student.setGrades(grades);
            return true;
        }
        return false;
    }

    public Grade getGrade(int studentId, int gradeId){
        Student student = students.get(studentId);
        if (student != null) {
            ArrayList<Grade> grades = student.getGrades();
            for (Grade grade: grades)
                if (grade.getId() == gradeId)
                    return grade;
        }
        return null;
    }

    public ArrayList<Grade> getGrades(int id) {
        Student student = students.get(id);
        if (student != null) {
            return student.getGrades();
        }
        return null;
    }

    public int getGradeIndex() {
        gradeIndex++;
        return gradeIndex;
    }

    public boolean addGrade(Grade grade, int id){
        Student student = students.get(id);
        if(student != null) {
            grade.setId(getGradeIndex());
            int courseId = grade.getCourse().getId();
            Course course = courses.get(courseId);
            if (course == null) {
                return false;
            }
            grade.setCourse(course);
            ArrayList<Grade> grades = student.getGrades();
            grades.add(grade);
            student.setGrades(grades);
            return true;
        }
        return false;
    }
}
