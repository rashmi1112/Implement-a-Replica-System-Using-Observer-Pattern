package studentskills.mytree;

import studentskills.UpdateType.UpdateType;
import studentskills.util.MyLogger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class of Student Record Node
 * @author Rashmi Badadale
 */

public class StudentRecord implements SubjectI, ObserverI,Cloneable {


    private int bNumber;
    private String firstName;
    private String lastName;
    private double gpa;
    private String major;
    private int height;
    private Set<String> skills = new HashSet<>();
    private StudentRecord leftChild;
    private StudentRecord rightChild;

    /**
     * Constructor of the Student Record
     * @param bNumberIn int BNumber
     * @param firstNameIn String First Name
     * @param lastNameIn String Last Name
     * @param gpaIn double GPA
     * @param majorIn String Major
     * @param skillsIn Set of Strings for skills
     */
    public StudentRecord(int bNumberIn, String firstNameIn, String lastNameIn, double gpaIn,
                         String majorIn, Set<String> skillsIn) {
        bNumber = bNumberIn;
        firstName = firstNameIn;
        lastName = lastNameIn;
        gpa = gpaIn;
        major = majorIn;
        skills.addAll(skillsIn);
        MyLogger.writeMessage("Constructor of StudentRecord" +" called.", MyLogger.DebugLevel.CONSTRUCTOR);
    }

    public String toString(){
        return "Student Record node created with BNumber " + bNumber;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<StudentRecord> getListenerObjects() {
        return new ArrayList<>(listenerObjects);
    }

    private List<StudentRecord> listenerObjects = new ArrayList<>();

    public void setbNumber(int bNumber) {
        this.bNumber = bNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getbNumber(){
        return this.bNumber;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public StudentRecord getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(StudentRecord leftChild) {
        this.leftChild = leftChild;
    }


    public StudentRecord getRightChild() {
        return rightChild;
    }

    public void setRightChild(StudentRecord rightChild) {
        this.rightChild = rightChild;
    }

    public Set<String> getSkills(){
        return new HashSet<>(this.skills);
    }

    public void setSkills(Set<String> skillsIn){
        this.skills = new HashSet<>(skillsIn);
    }

    /**
     * Overriding the clone method
     * @return StudentRecord Cloned StudentRecord node
     */

    public StudentRecord clone() throws CloneNotSupportedException {
        StudentRecord clonedStudentRecord =(StudentRecord) super.clone();
         clonedStudentRecord.skills = new HashSet<>(this.skills);
        clonedStudentRecord.listenerObjects = new ArrayList<>(this.listenerObjects);
         return clonedStudentRecord;
    }

    /**
     * Implementing  update method of the ObserverI interface
     * @param updatedNode StudentRecord that contains the changes
     * @param studentRecord StudentRecord that needs to be updated/modified
     * @param updateType Enum to determine its an update or modify
     */

    @Override
    public void update(StudentRecord updatedNode, StudentRecord studentRecord, UpdateType updateType) {
        if (studentRecord.getbNumber() != updatedNode.getbNumber()) {
            studentRecord.setbNumber(updatedNode.getbNumber());
        }
        if (!studentRecord.getFirstName().equals(updatedNode.getFirstName())) {
            studentRecord.setFirstName(updatedNode.getFirstName());
        }
        if (!studentRecord.getLastName().equals(updatedNode.getLastName())) {
            studentRecord.setLastName(updatedNode.getLastName());
        }
        if (!studentRecord.getMajor().equals(updatedNode.getMajor())) {
            studentRecord.setMajor(updatedNode.getMajor());
        }
        if (updateType == UpdateType.INSERT) {
            if (studentRecord.getGpa() != updatedNode.getGpa()) {
                studentRecord.setGpa(updatedNode.getGpa());
            }
            if (studentRecord.getSkills() != updatedNode.getSkills()) {
                Set<String> tempSet;
                tempSet = updatedNode.getSkills();
                if (studentRecord.getSkills().removeAll(updatedNode.getSkills())) {
                    tempSet.addAll(studentRecord.getSkills());
                }
                studentRecord.setSkills(tempSet);
            }
        }
        else {
            if (studentRecord.getSkills() != updatedNode.getSkills()) {
                Set<String> tempSet;
                tempSet = updatedNode.getSkills();
                studentRecord.setSkills(tempSet);
            }
        }
        MyLogger.writeMessage("Student Record with BNumber" + studentRecord.bNumber +" updated!\n", MyLogger.DebugLevel.NODE);
    }

    /**
     * Implementing the Register method of the SubjectI Interface
     * @param studentRecord StudentRecord Node that needs r=to be registered
     */

    @Override
    public void register(StudentRecord studentRecord) {
        listenerObjects.add(studentRecord);
    }

    /**
     * Implementing the Unregister method of the SubjectI interface
     * @param studentRecord StudentRecord Node that needs to be unregistered
     */

    @Override
    public void unregister(StudentRecord studentRecord) {
        listenerObjects.remove(studentRecord);
    }

    /**
     * Implementing the notifyAll method of the SubjectI interface
     * @param updatedNode StudentRecord Node that contains the changes
     * @param updateType Enum Update or Modify
     */
    @Override
    public void notifyAll(StudentRecord updatedNode, UpdateType updateType) {
        List<StudentRecord> listenerObjects;
        listenerObjects = updatedNode.getListenerObjects();
        for (StudentRecord studentRecord:listenerObjects){
            studentRecord.update(updatedNode,studentRecord,updateType);
        }
    }
}
