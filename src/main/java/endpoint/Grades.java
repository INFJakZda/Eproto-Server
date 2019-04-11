package endpoint;

import data.Model;
import model.Grade;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;

@Path("/students/{id}/grades")
public class Grades {
    Model model = Model.getInstance();

//    @GET
//    @Produces(MediaType.APPLICATION_XML)
//    public ArrayList<Grade> getAll(@PathParam("id") int id) {
//        return model.getGrades(id);
//    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Grade> getAll(@PathParam("id") int id) {
        ArrayList<Grade> grades = model.getGrades(id);
        if(grades != null) {
            return grades;
        }
        return null;
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
    @PUT
    @Path("{idG}")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response put(Grade gradePut, @PathParam("id") int id, @PathParam("idG") int idG) {
        Grade grade = model.getGrade(id, idG);
        boolean modified = false;
        if (grade == null){
            return Response.status(404).build();
        }
        if (grade.getValue() != gradePut.getValue()) {
            grade.setValue(gradePut.getValue());
            modified = true;
        }
        if (grade.getDate() != gradePut.getDate()) {
            grade.setDate(gradePut.getDate());
            modified = true;
        }
        if (grade.getCourse() == gradePut.getCourse()) {
            grade.setCourse(gradePut.getCourse());
            modified = true;
        }
        if (modified)
            return Response.status(200).entity(grade).build();
        else
            return Response.status(304).build();
    }
//
    @DELETE
    @Path("{idG}")
    @Produces(MediaType.APPLICATION_XML)
    public Response delete(@PathParam("idG") int idG, @PathParam("id") int id) {
        if (model.deleteGrade(idG, id))
            return Response.status(200).build();
        else
            return Response.status(404).build();

    }
}