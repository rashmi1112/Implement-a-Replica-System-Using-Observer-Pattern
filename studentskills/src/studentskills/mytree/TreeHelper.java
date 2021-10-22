package studentskills.mytree;

import studentskills.UpdateType.UpdateType;
import studentskills.util.MyLogger;
import studentskills.util.Results;
import studentskills.util.TreeID;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static studentskills.driver.Driver.NUMBER_OF_REPLICAS;

/**
 * Helper class to create node, clone it and insert them to respective trees.
 * @author Rashmi Badadale
 */
public class TreeHelper {

    private StudentRecord studentRecordReplica0;
    private StudentRecord studentRecordReplica1;
    private StudentRecord studentRecordReplica2;
    private List<StudentRecord> referencesToRootsList;
    private List<AVLTree> listOfTrees = new ArrayList<>();
    private AVLTree treeReplica0 = new AVLTree(0);
    private AVLTree treeReplica1 = new AVLTree(1);
    private AVLTree treeReplica2 = new AVLTree(2);
    private String filename1, filename2, filename3;

    public TreeHelper(String filename1In, String filename2In, String filename3In){
        filename1 = filename1In;
        filename2 = filename2In;
        filename3 = filename3In;
        MyLogger.writeMessage("Constructor for TreeHelper" + " called.\n", MyLogger.DebugLevel.CONSTRUCTOR);
    }

    public String toString(){
        return "Creating Student Record node and its clones.";
    }

    /**
     * Method to create the student record nodes and create its clones
     * @param bNumberIn Integer BNumber - unique ID for teh node
     * @param firstNameIn String First Name
     * @param lastNameIn String Last Name
     * @param gpaIn Double GPA of the student
     * @param majorIn String Major
     * @param skillsIn Set of Skills
     */

    public void createNode(int bNumberIn, String firstNameIn, String lastNameIn, double gpaIn,
                      String majorIn, Set<String> skillsIn) throws CloneNotSupportedException {
        studentRecordReplica0 = new StudentRecord(bNumberIn,firstNameIn,lastNameIn,gpaIn,majorIn,skillsIn);
        MyLogger.writeMessage("Replica 0 of Student Record Node with BNumber" + studentRecordReplica0.getbNumber() +
                        " created.\n", MyLogger.DebugLevel.NODE);
        cloneNodes();
        MyLogger.writeMessage("Clones (Replica1 & Replica2) of Student Record Node with BNumber" + studentRecordReplica0.getbNumber() +" created!\n",
                MyLogger.DebugLevel.NODE);
        MyLogger.writeMessage("New node of BNumber " + studentRecordReplica0.getbNumber() +" created as StudentRecordReplica0 and its clones StudentRecordReplica1 and StudentRecordReplica2 are now" +
                " implemented as subjects and are observers of each other.", MyLogger.DebugLevel.OBSERVER_PATTERN);
        buildTrees();
    }

    /**
     * Method to create clones of the created nodes and implement observer pattern in them.
     */

    public void cloneNodes() throws CloneNotSupportedException {
        studentRecordReplica1 = studentRecordReplica0.clone();
        studentRecordReplica2 = studentRecordReplica0.clone();
        referencesToRootsList = new ArrayList<>();
        referencesToRootsList.add(studentRecordReplica0);
        referencesToRootsList.add(studentRecordReplica1);
        referencesToRootsList.add(studentRecordReplica2);
        for (int i = 0; i < NUMBER_OF_REPLICAS; i++) {
            for (StudentRecord studentRecord : referencesToRootsList) {
                if (studentRecord != referencesToRootsList.get(i)) {
                    referencesToRootsList.get(i).register(studentRecord);
                }
            }
        }
    }

    /**
     * Method to insert the nodes to their respective trees or update the nodes.
     */

    public void buildTrees(){
        StudentRecord previousNode;
        StudentRecord updatedStudentRecord0;
        if(treeReplica0.getRoot() != null){
            previousNode = treeReplica0.searchNode(studentRecordReplica0.getbNumber(), treeReplica0.getRoot());
            if (previousNode == null) {
                if (studentRecordReplica0 != treeReplica0.getRoot()) {
                    treeReplica0.insert(studentRecordReplica0);
                    treeReplica1.insert(studentRecordReplica1);
                    treeReplica2.insert(studentRecordReplica2);
                    MyLogger.writeMessage("Student Record Node with BNumber" + studentRecordReplica0.getbNumber()
                                    +" inserted to Tree"+ TreeID.getTreeID(treeReplica0)+ ", Tree"+ TreeID.getTreeID(treeReplica1) +
                            " and Tree" + TreeID.getTreeID(treeReplica2) + ".\n", MyLogger.DebugLevel.TREE);
                }
            } else {
                 updatedStudentRecord0 = updateNode(studentRecordReplica0, previousNode);
                 MyLogger.writeMessage("StudentRecord0 with BNumber" + studentRecordReplica0.getbNumber() +" was updated." +
                         "Sending notifications for update to all listeners.\n", MyLogger.DebugLevel.NODE);
                 updatedStudentRecord0.notifyAll(updatedStudentRecord0,UpdateType.INSERT);
                 MyLogger.writeMessage("All listeners of StudentRecordReplica0 notified.", MyLogger.DebugLevel.OBSERVER_PATTERN);
            }
        }
        else{
            treeReplica0.insert(studentRecordReplica0);
            treeReplica1.insert(studentRecordReplica1);
            treeReplica2.insert(studentRecordReplica2);
            MyLogger.writeMessage("Student Record Node with BNumber" + studentRecordReplica0.getbNumber()
                    +" inserted to Tree"+ TreeID.getTreeID(treeReplica0)+ ", Tree"+ TreeID.getTreeID(treeReplica1) +
                    " and Tree" + TreeID.getTreeID(treeReplica2) + ".\n", MyLogger.DebugLevel.TREE);
        }
    }

    /**
     * Method to recognize the parameter to be changed from input file and update the node from the tree accordingly.
     * @param studentRecord StudentRecord Node containing the change information
     * @param previousNode StudentRecord Previous node present in the tree that needs to be updated.
     * @return StudentRecord Return node after updating and notifying its listeners about the change.
     */

    public StudentRecord updateNode(StudentRecord studentRecord, StudentRecord previousNode){
            if (studentRecord.getbNumber() != previousNode.getbNumber()) {
                previousNode.setbNumber(previousNode.getbNumber());
            } else if (!studentRecord.getFirstName().equals(previousNode.getFirstName())) {
                previousNode.setFirstName(previousNode.getFirstName());
            } else if (!studentRecord.getLastName().equals(previousNode.getLastName())) {
                previousNode.setLastName(previousNode.getLastName());
            } else if (studentRecord.getGpa() != previousNode.getGpa()) {
                previousNode.setGpa(previousNode.getGpa());
            }
            if (!studentRecord.getMajor().equals(previousNode.getMajor())) {
                previousNode.setMajor(previousNode.getMajor());
            }
            if (studentRecord.getSkills() != previousNode.getSkills()) {
                Set<String> tempSet;
                tempSet = previousNode.getSkills();
                if (studentRecord.getSkills().removeAll(previousNode.getSkills())) {
                    tempSet.addAll(studentRecord.getSkills());
                }
                previousNode.setSkills(tempSet);
            }
            return previousNode;
    }

    /**
     * Methdd to change the parameter of the node with given bNumber from Modify file
     * @param replicaID Integer TreeID into which the update needs to be done
     * @param bNumber Integer BNumber of the node that needs to be changed
     * @param valueToBeChanged String Value that needs to be changed
     * @param valueChangeTo String Value that needs to be changed to
     * @param results Results instance
     */
    public void modifyNode(int replicaID, int bNumber, String valueToBeChanged, String valueChangeTo,Results results){
        listOfTrees.add(treeReplica0);
        listOfTrees.add(treeReplica1);
        listOfTrees.add(treeReplica2);
        for(AVLTree avlTree : listOfTrees){
            if(TreeID.getTreeID(avlTree) == replicaID){
                StudentRecord prevNode;
                prevNode = avlTree.searchNode(bNumber,avlTree.getRoot());
                if(prevNode != null){
                    if(Double.toString(prevNode.getGpa()).equals(valueToBeChanged)){
                        results.storeErrorMessages("GPA cannot be changed!\n");
                    }
                    if(prevNode.getFirstName().equals(valueToBeChanged)){
                        prevNode.setFirstName(valueChangeTo);
                    }
                    if(prevNode.getLastName().equals(valueToBeChanged)){
                        prevNode.setLastName(valueChangeTo);
                    }
                    if(prevNode.getMajor().equals(valueToBeChanged)){
                        prevNode.setMajor(valueChangeTo);
                    }

                    for(String string: prevNode.getSkills()){
                        Set<String> tempSet;
                        if(string.equals(valueToBeChanged)){
                           tempSet = prevNode.getSkills();
                           tempSet.remove(valueToBeChanged);
                           tempSet.add(valueChangeTo);
                           prevNode.setSkills(tempSet);
                        }
                    }
                    MyLogger.writeMessage("Field " + valueToBeChanged+" of Student Record with BNumber" + bNumber + " of Tree" + replicaID +
                            " was modified to" + valueChangeTo + ".\n", MyLogger.DebugLevel.NODE);
                    prevNode.notifyAll(prevNode, UpdateType.MODIFY);
                    MyLogger.writeMessage("Listeners of Student Record Replica" + replicaID + " were " +
                            "notified about the modification.", MyLogger.DebugLevel.OBSERVER_PATTERN);
                }
            }
        }


    }

    /**
     * Method to create instances of the AVL trees and storing the trees in Results instance
     * @return List<Results> references to the stored trees (Sorted in ascending order with respect to BNumber)
     */

    public List<Results>  printTrees() {
        Results results1 = new Results(filename1);
        Results results2 = new Results(filename2);
        Results results3 = new Results(filename3);
        List<Results> listOfResultsInstances = new ArrayList<>();
        MyLogger.writeMessage("Tree" + TreeID.getTreeID(treeReplica0) +", Tree"+ TreeID.getTreeID(treeReplica1) +
                " and Tree" + TreeID.getTreeID(treeReplica2)+ " created!\n", MyLogger.DebugLevel.TREE);
        treeReplica0.printNodes(results1,treeReplica0.getRoot());
        treeReplica1.printNodes(results2,treeReplica1.getRoot());
        treeReplica2.printNodes(results3,treeReplica2.getRoot());
        listOfResultsInstances.add(results1);
        listOfResultsInstances.add(results2);
        listOfResultsInstances.add(results3);
        return listOfResultsInstances;
    }
}
