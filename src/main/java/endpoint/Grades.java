package endpoint;

import data.Model;
import model.Grade;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Collection;

@Path("/students/{id}/grades")
public class Grades {
    Model model = Model.getInstance();

//    @GET
//    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Collection<Grade> getAll(@PathParam("id") int id) {
//        Collection<Grade> grades = model.getGrades(id);
//        if(grades != null) {
//            return grades;
//        }
//        return null;
//    }
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Grade> getAll(@Context UriInfo info,
                                    @PathParam("id") int id) {
        String subjectIdString = info.getQueryParameters().getFirst("courseId");
//        String value = info.getQueryParameters().getFirst("value");
//        String order = info.getQueryParameters().getFirst("order");
        ObjectId subjectId = (subjectIdString != null) ? new ObjectId(subjectIdString) : null;
//        Collection<Grade> grades = model.getGrades(id, subjectId, Float.valueOf(value), Integer.parseInt(order));
        Collection<Grade> grades = model.getGrades(id, subjectId, 0, 0);
        if(grades != null) {
            return grades;
        }
        return null;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response get(@PathParam("idG") int idG, @PathParam("id") int id) {
//        Collection<Grade> gradeList = model.getGrades(id, null, 0, 0);
        Collection<Grade> gradeList = model.getGrades(id, null, 0, 0);
        if (gradeList != null){
            for (Grade grade : gradeList) {
                if (grade.getId() == idG) {
                    return Response.status(200).entity(grade).build();
                }
            }
        }
        return Response.status(404).build();
    }

    @PUT
    @Path("{idG}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response put(Grade gradePut, @PathParam("id") int id, @PathParam("idG") int idG) {
        Grade grade = model.getGrade(id, idG);
        Grade oldGrade = model.getGrade(id, idG);
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
        if (modified) {
            if (model.updateGrade(id, grade, gradePut)) {
                return Response.status(200).entity(grade).build();
            }
            else {
                return Response.status(404).build();
            }
        }
        else
            return Response.status(304).build();
    }
//
    @DELETE
    @Path("{idG}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("idG") int idG, @PathParam("id") int id) {
        if (model.deleteGrade(idG, id))
            return Response.status(200).build();
        else
            return Response.status(404).build();

    }
}
