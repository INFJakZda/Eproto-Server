package endpoint;

import data.Model;
import model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Path("/students")
@XmlRootElement(name="students")
public class Students {
    Model model = Model.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Student> getAll(@Context UriInfo info,
                                      @QueryParam("name") String name,
                                      @QueryParam("surname") String surname,
                                      @QueryParam("birthDate") Date date
//                                      @QueryParam("order") int order
//                                      @QueryParam("index") Integer index

    ) {
        String orders = info.getQueryParameters().getFirst("order");
        int order = orders == null ? 0 : Integer.parseInt(orders);
//        if (date == null) {
//            date = new Date();
//        }
//        return model.getStudents(name, surname, date, Integer.parseInt(order));
        return model.getStudents(name, surname, date, order);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response add(Student student, @Context UriInfo uriInfo) {
        model.addStudent(student);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(student.getIndex()));
        return Response.created(builder.build()).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response get(@PathParam("id") int id) {
        Student student = model.getStudent(id);
        if (student == null) {
            return Response.status(404).build();
        } else {
            return Response.status(200).entity(student).build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response put(Student studentPut, @PathParam("id") int id) {
        Student student = model.getStudent(id);
        boolean modified = false;
        if (student == null){
            return Response.status(404).build();
        }
        if (!student.getSurname().equals(studentPut.getSurname())){
            student.setSurname(studentPut.getSurname());
            modified = true;
        }
        if (!student.getName().equals(studentPut.getName())){
            student.setName(studentPut.getName());
            modified = true;
        }
        if (!student.getBirthDate().equals(studentPut.getBirthDate())) {
            student.setBirthDate(studentPut.getBirthDate());
            modified = true;
        }
        if (modified) {
            model.updateStudent(student);
            return Response.status(200).entity(student).build();
        }
        else
            return Response.status(304).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) {
        if (model.deleteStudent(id))
            return Response.status(200).build();
        else
            return Response.status(404).build();

    }
}
