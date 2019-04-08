package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
}
