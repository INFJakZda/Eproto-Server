import data.Model;
import model.Course;
import endpoint.*;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

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
            Course course1 = new Course(0, "SINT", "Pawlak");
            Course course2 = new Course(1, "PIRO", "Kowalski");
            model.addCourse(course1);
            model.addCourse(course2);
        } catch (Exception e) {
            System.out.println("BŁĄÐ - createData");
        }
    }
}
