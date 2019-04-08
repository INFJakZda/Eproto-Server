package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Grade {
    private Integer id;
    private double value;
    private Date date;
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
    public void setValue(float value) { this.value = value; }

    @XmlElement
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @XmlElement
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
