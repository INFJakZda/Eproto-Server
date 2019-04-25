package endpoint;

import data.*;
import model.*;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;

@Path("/courses")
public class Courses {
    Model model = Model.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Course> getCourses(@QueryParam("professor") String professor,
                                         @QueryParam("name") String name) {
        return model.getCourses(professor, name);
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response add(Course course, @Context UriInfo uriInfo) {
        course = model.addCourse(course);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(course.getId().toString());
        return Response.created(builder.build()).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getCourse(@PathParam("id") ObjectId id) {
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
    public Response put(Course coursePut, @PathParam("id") ObjectId id) {
        coursePut.setId(id);
        Course course = model.getCourse(id);
        if (course == null) {
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
        if (modified) {
            model.updateCourse(course);
            return Response.ok().build();
        }
        else {
            return Response.status(304).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") ObjectId id) {
        if (model.deleteCourse(id))
            return Response.status(200).build();
        else
            return Response.status(404).build();
    }
}
