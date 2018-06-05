/**
 * @author Mohanad Arafe #40042922
 * COMP 249 - QQ
 * Assignment 4 - 13/04/2018
 * Purpose: Before anything, we must define the Course objects
 * and their information such as ID, credits and so on.
 */
package Main;

import java.util.Scanner;

public class Course{

    private String courseID;
    private String courseName;
    private double credit;
    private String preReqID;
    private String coReqID;

    //Constructors
    public Course(String courseID, String courseName, double credit, String preReqID, String coReqID) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.credit = credit;
        this.preReqID = preReqID;
        this.coReqID = coReqID;
    }

    public Course(Course C, String value){
        this.courseID = value;
        this.courseName = C.getCourseName();
        this.credit = C.getCredit();
        this.preReqID = C.getPreReqID();
        this.coReqID = C.getCoReqID();
    }

    //Getters & Setters
    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getPreReqID() {
        return preReqID;
    }

    public void setPreReqID(String preReqID) {
        this.preReqID = preReqID;
    }

    public String getCoReqID() {
        return coReqID;
    }

    public void setCoReqID(String coReqID) {
        this.coReqID = coReqID;
    }

    //toString & equals methods
    public boolean equals(Course otherCourse) {
        if (otherCourse == null || this.getClass() != otherCourse.getClass())
            return false;
        else
        {
            return (this.courseName.equals(otherCourse.courseName) && this.credit==otherCourse.credit && this.preReqID.equals(otherCourse.preReqID)
            && this.coReqID.equals(otherCourse.coReqID));
        }
    }

    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credit=" + credit +
                ", preReqID='" + preReqID + '\'' +
                ", coReqID='" + coReqID + '\'' +
                '}';
    }

    //clone method
    public Course clone(String ID){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter a new course ID: ");
            ID = sc.nextLine();
        Course c = new Course(ID, getCourseName(), getCredit(), getPreReqID(), getCoReqID());
        sc.close();
        return c;
    }
}