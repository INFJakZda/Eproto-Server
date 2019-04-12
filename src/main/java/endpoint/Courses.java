package endpoint;

import data.*;
import model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;

@Path("/courses")
public class Courses {
    Model model = Model.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<Course> getCourses() {
        return model.getCourses();
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response add(Course course, @Context UriInfo uriInfo) {
        if (!model.checkCourseId(course))
            return Response.status(403).build();
        model.addCourse(course);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(course.getId()));
        return Response.created(builder.build()).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCourse(@PathParam("id") int id) {
        Course course = model.getCourse(id);
        if (course == null) {
            return Response.status(404).build();
        } else {
            return Response.status(200).entity(course).build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response put(Course coursePut, @PathParam("id") int id) {
        Course course = model.getCourse(id);
        if (course == null){
            return Response.status(404).build();
        }
        boolean modified = false;
        if (!course.getName().equals(coursePut.getName())){
            course.setName(coursePut.getName());
            modified = true;
        }
        if (!course.getProfessor().equals(coursePut.getProfessor())) {
            course.setProfessor(coursePut.getProfessor());
            modified = true;
        }
        if (modified)
            return Response.status(200).entity(course).build();
        else
            return Response.status(304).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) {
        if (model.deleteCourse(id))
            return Response.status(200).build();
        else
            return Response.status(404).build();

    }
}
