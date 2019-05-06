package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import endpoint.Grades;
import endpoint.Students;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.*;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "student")
public class Student {
    @Indexed(options = @IndexOptions(unique = true))
    private Integer index;
    private String name;
    private String surname;
    private Date birthDate;
//    private int order;
    @Embedded
    @XmlTransient
    private ArrayList<Grade> grades;

    public Student(){}
    public Student(String name, String surname, Date birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.grades = new ArrayList<>();
//        this.order = 0;
    }

    @Id
    @XmlTransient
    private ObjectId id;
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }

    @XmlAttribute
    public Integer getIndex() { return index; }
    public void setIndex(Integer index) { this.index = index; }

    @XmlElement
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlElement
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    @XmlElement
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",  timezone="CET")
    public Date getBirthDate(){ return birthDate; }
    public void setBirthDate(Date birthDate){ this.birthDate = birthDate; }

//    @XmlTransient
    @XmlElementWrapper(name = "grades")
    @XmlElement(name = "grade")
    public ArrayList<Grade> getGrades() { return grades; }
    public void setGrades(ArrayList<Grade> grades) { this.grades = grades; }

//    @XmlTransient
    @InjectLinks({
        @InjectLink(
                rel = "self",
                resource = Students.class,
                bindings = @Binding(name = "id", value = "${instance.index}"),
                method = "get"),
        @InjectLink(
                rel = "parent",
                method = "getAll",
                resource = Students.class),
        @InjectLink(
                rel = "grades",
                method = "getGrades",
                resource = Grades.class,
                bindings = @Binding(name = "id", value = "${instance.index}"))
    })
    @XmlElement(name="link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

//    public int getOrder() {
//        return order;
//    }
//
//    public void setOrder(int order) {
//        this.order = order;
//    }
}
