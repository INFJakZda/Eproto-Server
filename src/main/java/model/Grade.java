package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Grade {

    Grade(){}
    Grade(float value, Date date, Course course){
        this.value = value;
        this.date = date;
        this.course = course;
    }

    private static int lastId = 0;
    private synchronized int generateId(){ return lastId++; }

    @XmlElement
    private Integer id;
    public Integer getId() { return id; }
    public void setId(int id) { this.id = id; }

    @XmlElement
    private Float value;
    public Float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    @XmlElement
    private Date date;
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @XmlElement
    private Course course;
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
