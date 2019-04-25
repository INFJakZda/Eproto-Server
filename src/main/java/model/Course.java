package model;

import endpoint.Courses;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "course")
public class Course {
    @Id
    private ObjectId id;
    private String name;
    private String professor;

    public Course(){}
    public Course(String name, String professor){
//        this.id = id;
        this.name = name;
        this.professor = professor;
    }

    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    @XmlAttribute
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    @XmlElement
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlElement
    public String getProfessor() { return professor; }
    public void setProfessor(String professor) { this.professor = professor; }

    @InjectLinks({
        @InjectLink(
                rel = "self",
                resource = Courses.class,
                bindings = @Binding(name = "id", value = "${instance.id}"),
                method = "getCourse"),
        @InjectLink(
                rel = "parent",
                resource = Courses.class,
                method = "getCourses"),
    })
    @XmlElement(name="link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;
}
