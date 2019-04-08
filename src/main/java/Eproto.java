import data.Model;
import model.*;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

public class Eproto {
    public static void main(String[] args) throws Exception
    {
        Model model = Model.getInstance();
        createData(model);

        URI baseUri = UriBuilder.fromUri("http://localhost").port(9998).build();
        ResourceConfig config = new ResourceConfig().packages("endpoint");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        server.start();
    }

    public static void createData(Model model) {
        try {
            // COURSES
            Course course1 = new Course(0, "SINT", "Pawlak");
            Course course2 = new Course(1, "PIRO", "Kowalski");
            model.addCourse(course1);
            model.addCourse(course2);

            // STUDENTS
            Student student1 = new Student("Ala", "Nowicka", new Date());
            Student student2 = new Student("Alaasd", "Kowalska", new Date());
            Student student3 = new Student("Alarete", "Monta", new Date());

            // GRADES
            Grade grade = new Grade(3.5, new Date(), course1);
            grade.setId(model.getGradeIndex());
            ArrayList<Grade> gradesS1 = new ArrayList<>();
            gradesS1.add(grade);
            student1.setGrades(gradesS1);

            model.addStudent(student1);
            model.addStudent(student2);
            model.addStudent(student3);


        } catch (Exception e) {
            System.out.println("BŁĄÐ - createData");
        }
    }
}
