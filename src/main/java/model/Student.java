package model;

import endpoint.Grades;
import endpoint.Students;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "student")
public class Student {
    private int index;
    private String name;
    private String surname;
    private Date birthDate;
    private ArrayList<Grade> grades;

    public Student(){}
    public Student(String name, String surname, Date birthDate) {
        this.index = 0;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.grades = new ArrayList<Grade>();
    }

//    private static int lastIndex = 0;
//    private int generateIndex(){
//        lastIndex;
//        return lastIndex;
//    }

    @XmlAttribute
    public int getIndex() { return index; }
    public void setIndex(int index) { this.index = index; }

    @XmlElement
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @XmlElement
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    @XmlElement
    public Date getBirthDate(){ return birthDate; }
    public void setBirthDate(Date birthDate){ this.birthDate = birthDate; }

    @XmlElementWrapper(name = "grades")
    @XmlElement(name = "grade")
    public ArrayList<Grade> getGrades() { return grades; }
    public void setGrades(ArrayList<Grade> grades) { this.grades = grades; }

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
}
