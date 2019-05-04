package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import endpoint.Courses;
import endpoint.Grades;
import endpoint.Students;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

import javax.security.auth.Subject;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@Embedded
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Grade {
    private Integer id;
    private double value;
    private Date date;
    @Reference
    private Course course;

    public Grade(){}
    public Grade(double value, Date date, Course course){
        this.id = null;
        this.value = value;
        this.date = date;
        this.course = course;
    }

//    private static int lastId = 0;
//    private synchronized int generateId(){ return lastId++; }

    @XmlAttribute
    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }

    @XmlElement
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    @XmlElement
    @XmlSchemaType(name="date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="CET")
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @XmlElement
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @InjectLinks({
            @InjectLink(
                    rel = "self",
                    resource = Grades.class,
                    bindings = @Binding(name = "idG", value = "${instance.id}"),
                    method = "get"),
            @InjectLink(
                    rel = "parent",
                    resource = Grades.class,
                    method = "getAll"),
            @InjectLink(
                    rel = "student",
                    resource = Students.class,
                    method = "get"),
            @InjectLink(
                    rel = "course",
                    resource = Courses.class,
                    method = "getCourse",
                    bindings = @Binding(name = "id", value = "${instance.course.id}")),
    })
    @XmlElement(name="link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;
}
