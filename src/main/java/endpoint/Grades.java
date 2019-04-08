package endpoint;

import data.Model;
import model.Grade;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;

@Path("/students/{id}/grades")
public class Grades {
    Model model = Model.getInstance();

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Grade> getAll(@PathParam("id") int id) {
        return model.getGrades(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response add(Grade grade, @Context UriInfo uriInfo, @PathParam("id") int id) {
        if (model.addGrade(grade, id)) {
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(grade.getId()));
            return Response.created(builder.build()).build();
        } else {
            return Response.status(400).build();
        }

    }

    @GET
    @Path("{idG}")
    @Produces(MediaType.APPLICATION_XML)
    public Response get(@PathParam("idG") int idG, @PathParam("id") int id) {
        ArrayList<Grade> gradeList = model.getGrades(id);
        for (Grade grade : gradeList) {
            if (grade.getId() == idG) {
                return Response.status(200).entity(grade).build();
            }
        }
        return Response.status(404).build();
    }
//
//    @PUT
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_XML)
//    @Consumes(MediaType.APPLICATION_XML)
//    public Response put(Grade gradePut, @PathParam("id") int id) {
//        Grade grade = model.getGrade(id);
//        boolean modified = false;
//        if (grade == null){
//            return Response.status(404).build();
//        }
//        if (!grade.getSurname().equals(gradePut.getSurname())){
//            grade.setSurname(gradePut.getSurname());
//            modified = true;
//        }
//        if (!grade.getName().equals(gradePut.getName())){
//            grade.setName(gradePut.getName());
//            modified = true;
//        }
//        if (!grade.getBirthDate().equals(gradePut.getBirthDate())) {
//            grade.setBirthDate(gradePut.getBirthDate());
//            modified = true;
//        }
//        if (modified)
//            return Response.status(200).entity(grade).build();
//        else
//            return Response.status(304).build();
//    }
//
//    @DELETE
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_XML)
//    public Response delete(@PathParam("id") int id) {
//        if (model.deleteGrade(id))
//            return Response.status(200).build();
//        else
//            return Response.status(404).build();
//
//    }
}
