package Main;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CourseList {

    private CourseNode head = null;
    private CourseNode currentNode;
    private CourseNode previousNode;
    private int size = this.size();

    /**
     * @return size of CourseList
     */
    public int size(){
        int counter = 0;
        CourseNode temp = head;
        while( temp != null){
            counter++;
            temp = temp.nextNode;
        }
        return counter;
    }

    //default const.
    public CourseList() {
        head = null;
        size = 0;
    }

    /**
     * @param cL copy CourseList
     */
    public CourseList(CourseList cL) {
        this.head = cL.head;
        this.size = cL.size;
    }

    /**
     * @param c Course
     */
    public void addToStart(Course c){
        head = new CourseNode(c, head);
    }

    /**
     * @param c Course
     * @param index index number
     */
    public void insertAtIndex(Course c, int index){
        try{
            if(index < 0 || index > this.size())
                throw new NoSuchElementException();
        } catch(NoSuchElementException e){
            System.out.println("Index number is not valid!");
            System.exit(0);
        }
        //If the code reaches here with no exceptions being
        //thrown, the index is valid.
        if(head == null){
            head = new CourseNode(c, head);
            return;
        }
        if(index == 0){
            this.addToStart(c);
            return;
        }
        currentNode = head;
        int counter = 0;
        while (counter < index){
            previousNode = currentNode;
            currentNode = currentNode.nextNode;
            counter++;
        }
        previousNode.setNextNode(new CourseNode(c, currentNode));
    }

    /**
     * Deletes head node
     */
    public void deleteFromStart(){
        this.head = head.nextNode;
    }

    /**
     * Deletes a node at specific index
     * @param index index
     */
    public void deleteFromIndex(int index){
        try{
            if(index < 0 || index > this.size())
                throw new NoSuchElementException();
        } catch(NoSuchElementException e){
            System.out.println("Index number is not valid!");
            System.exit(0);
        }
        //If the code reaches here with no exceptions being
        //thrown, the index is valid.
        if(head == null)
            return;
        if(index == 0) {
            this.deleteFromStart();
            return;
        }

        currentNode = head;
        for (int i = 0; i < index-1; i++){
            currentNode = currentNode.nextNode;
        }
        currentNode.nextNode = currentNode.nextNode.nextNode;
    }

    /**
     * replaces a Node at specific index
     * @param c Course
     * @param index index
     */
    public void replaceAtIndex(Course c, int index) {
        try {
            if (index < 0 || index > this.size())
                throw new NoSuchElementException();
        }
        catch (NoSuchElementException e) {
            System.out.println("Not a valid index.");
        }
        this.deleteFromIndex(index);
        this.insertAtIndex(c, index);
    }

    /**
     * Finds a course in a node
     * @param courseID courseID of node
     */
    public void find(String courseID){
        currentNode = head;
        int counter = 0;

        while (currentNode != null){
            if(currentNode.getCourse().getCourseID().equals(courseID))
               break;
            else{
                currentNode = currentNode.nextNode;
                counter++;
            }
            if(counter == (this.size()))
                System.out.println("Course ID was not found.");
            else
                System.out.println("There were a total of " + counter + " iterations.");
        }
    }

    /**
     * Finds if a node contains a courseID. This should return a boolean
     * or else a privacy leak can be created. If the return type is a String, one
     * could have a pointer to the courseNode and modify it.
     * @param courseID search courseID in node
     * @return boolean
     */
    public boolean contains(String courseID){
        currentNode = head;
        while (currentNode != null){
            if(currentNode.getCourse().getCourseID().equals(courseID))
                return true;
            else{
                currentNode = currentNode.nextNode;
            }
        }
        return false;
    }

    /**
     * Determines if 2 nodes have same courses
     * @param CL course
     */
    public void duplicateEntries(CourseList CL){
        currentNode = head;
        CourseNode movingNode = currentNode.nextNode;

        for(int i = 1; i <= CL.size(); i++){
            for(int j = CL.size()-i; j > 0; j--){
                    if(currentNode.getCourse().getCourseID().equals(movingNode.getCourse().getCourseID())) {
                       CL.deleteFromIndex(i-1);
                    }
                    movingNode = movingNode.nextNode;
            }

            if(i == CL.size())
                return;
            if(i == CL.size()-1){
                movingNode = currentNode.nextNode;
                if(currentNode.getCourse().getCourseID().equals(movingNode.getCourse().getCourseID()))
                    CL.deleteFromIndex(i);
            }
                currentNode = currentNode.nextNode;
                movingNode = currentNode.nextNode;
        }
    }

    /**
     * Main method that determines if a student an enroll in a course.
     * @param requestedList requestedList classes
     * @param finishedList finishedlist classes
     * @param CL courselist
     */
    public void canEnroll(ArrayList<String> requestedList, ArrayList<String> finishedList,  CourseList CL){

        for(int i = 0; i < requestedList.size(); i++) {
            currentNode = head;
            req: for(int j = 0 ; j < CL.size(); j++){
                if(CL.currentNode.getCourse().getCourseID().equals(requestedList.get(i))){
                    for(int w = 0; w < finishedList.size(); w++){

                        if(CL.currentNode.getCourse().getPreReqID().equals(finishedList.get(w))
                                && (CL.currentNode.getCourse().getCoReqID() == null || requestedList.contains(CL.currentNode.getCourse().getCoReqID()) || finishedList.contains(CL.currentNode.getCourse().getCoReqID()))){

                                if(CL.currentNode.getCourse().getCoReqID() == null) {
                                    System.out.println("The student can enroll in " + requestedList.get(i) + " since he/she has completed the pre-requisite "
                                            + finishedList.get(w));
                                } else{
                                    System.out.println("The student can enroll in " + requestedList.get(i) + " since he/she has completed the pre-requisite "
                                            + finishedList.get(w) + " and " + CL.currentNode.getCourse().getCoReqID());
                                }
                            break req;
                        }
                        if(w == finishedList.size()-1) {
                            System.out.println("The student may not enroll in " + requestedList.get(i) + " as he/she does not have sufficient background.");
                            break req;
                        }
                    }
                }
                else
                    currentNode = currentNode.nextNode;
            }
        }
        if(requestedList.size()==0){
            System.out.println("No enrollment courses found");
            System.exit(0);
        }
        if(finishedList.size()==0){
            for(int w = 0; w < requestedList.size(); w++){
                currentNode = head;
                for(int i = 0; w < CL.size(); i++){
                    if(CL.currentNode.getCourse().getCourseID().equals(requestedList.get(w))){
                        //find right course in node
                        if(CL.currentNode.getCourse().getPreReqID() == null) {
                            System.out.println("The student can enroll in " + requestedList.get(w));
                            break;
                        }
                        else {
                            System.out.println("The student cannot enroll in " + requestedList.get(w) + " as he/she does not have sufficient background.");
                            break;
                        }
                    }
                    else
                        currentNode = currentNode.nextNode;
                }
            }
        }

    }

    /**
     * This method helps de-bugging to keep track of memory
     */
    public void showContents() {
        CourseNode current = head;
        while (current != null) {
                System.out.println(current.getCourse().toString());
                current = current.nextNode;
                if(current == null) {
                    break;
                }
        }
    }

    /**
     * This overwritten equals method determnines if two nodes have the same content.
     * @param other other CourseList
     * @return Boolean
     */
    public boolean equals(CourseList other)    {
        currentNode = head;
        CourseNode othercurrentNode = other.head;
        while(currentNode != null) {
            if (currentNode.equals(other.currentNode)) {
                currentNode = currentNode.nextNode;
                othercurrentNode = othercurrentNode. nextNode;
            }
            else {
                System.out.println("The lists are not equal");
                return false;
            }
        }
        System.out.println("Both lists are equal");
        return true;
    }

    /**
     * Getters and Setters
     * @return CourseNode and Size
     */
    public CourseNode getHead() { return head; }
    public void setHead(CourseNode head) { this.head = head; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

//---------------------INNER CLASS-------------------------------//
    public class CourseNode{

        private Course course;
        private CourseNode nextNode; //pointer to next node

        //default constructor
        CourseNode(){
            this.course = null;
            this.nextNode = null;
        }

        //parametrized
        CourseNode(Course course, CourseNode courseNode) {
            this.course = course;
            this.nextNode = courseNode;
        }

        //copy
        CourseNode (CourseNode cn){
            this.course = cn.course;
            this.nextNode = cn.nextNode;
        }

    /**
     * This method creates a clone node
     * @return CourseNode
     */
    public CourseNode clone() {
            CourseNode newCourseNode = new CourseNode(this.course, this.nextNode);
            return newCourseNode;
    }

    /**
     * Getters and Setters
     * @return CourseNode and Course
     */
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public CourseNode getNextNode() { return nextNode; }
    public void setNextNode(CourseNode courseNode) { this.nextNode = courseNode; }

    }
}
