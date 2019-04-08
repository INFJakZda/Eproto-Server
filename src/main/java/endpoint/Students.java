package endpoint;

import data.Model;
import model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;

@Path("/students")
public class Students {
    Model model = Model.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Student> getAll() {
        return model.getStudents();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response add(Student student, @Context UriInfo uriInfo) {
        model.addStudent(student);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(student.getIndex()));
        return Response.created(builder.build()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
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
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
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
        if (modified)
            return Response.status(200).entity(student).build();
        else
            return Response.status(304).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response delete(@PathParam("id") int id) {
        if (model.deleteStudent(id))
            return Response.status(200).build();
        else
            return Response.status(404).build();

    }
}
