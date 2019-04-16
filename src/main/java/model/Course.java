package model;

import endpoint.Courses;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@XmlRootElement(name = "course")
public class Course {
    private  int id;
    private String name;
    private String professor;

    public Course(){}
    public Course(int id, String name, String professor){
        this.id = id;
        this.name = name;
        this.professor = professor;
    }

    @XmlAttribute
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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
