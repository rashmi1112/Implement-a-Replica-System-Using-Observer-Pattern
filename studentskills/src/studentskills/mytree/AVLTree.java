package studentskills.mytree;

import studentskills.util.MyLogger;
import studentskills.util.Results;

/**
 * Class for performing operations over the AVL tree.
 * @author Rashmi Badadale
 */
public class AVLTree {
    private StudentRecord root;
    private final int treeID;

    public AVLTree(int treeID){
        this.treeID = treeID;
        MyLogger.writeMessage("Constructor for tree" + this.treeID +" called", MyLogger.DebugLevel.CONSTRUCTOR);
    }

    public String toString(){
        return "Tree created with TreeID " + getTreeID();
    }

    /**
     * Function to return the treeID of the tree.
     * @return Integer treeID
     */
    public int getTreeID() {
        return treeID;
    }

    /**
     * Methdo to return the root of the tree
     * @return StudentRecord Root of tree
     */

    public StudentRecord getRoot(){
        return root;
    }

    /**
     * Method to insert a node into the tree
     * @param studentRecord StudentNode to be inserted
     */

    public void insert(StudentRecord studentRecord){
        root = insert(studentRecord,root);
    }

    /**
     * Method to search for a node in the tree using BNumber
     * @param bNumber Integer BNumber of the node
     * @param root StudentRecord Root of the tree
     * @return StudentRecord node if present or null if not
     */

    public StudentRecord searchNode(int bNumber, StudentRecord root){
        StudentRecord currentNode;
        currentNode = root;
        if(currentNode == null){
            return null;
        }
        if(currentNode.getbNumber() == bNumber){
            return currentNode;
        }
        else if(bNumber < currentNode.getbNumber()){
            currentNode = currentNode.getLeftChild();
            return searchNode(bNumber,currentNode);
        }
        else if(bNumber > currentNode.getbNumber()){
            currentNode = currentNode.getRightChild();
            return searchNode(bNumber,currentNode);
        }
        return root;
    }

    /**
     * Overloaded method for insertion.
     * @param studentRecord StudentRecord node to be inserted into the tree
     * @param root  StudentRecord Root of the tree
     * @return Root after inserting the node into the tree
     */

    private StudentRecord insert(StudentRecord studentRecord, StudentRecord root) {
        if (root == null) {
            return studentRecord;
        }

        if(studentRecord.getbNumber() < root.getbNumber()){
            root.setLeftChild(insert(studentRecord, root.getLeftChild()));
        }
        else{
            root.setRightChild(insert(studentRecord,root.getRightChild()));
        }

        root.setHeight(calculateHeight(root));
        root = balanceTree(root);
        return root;
    }

    /**
     * Method to balance the AVL tree after inserting the node.
     * @param root StudentRecord Current Root of the tree after balancing
     * @return StudentRecord Root of the tree after balancing
     */

    private StudentRecord balanceTree(StudentRecord root){
        if(isLeftHeavy(root)){
            if(balanceFactor(root.getLeftChild()) < 0){
                root.setLeftChild(rotateLeft(root.getLeftChild()));
            }
            return rotateRight(root);
        }
        else if(isRightHeavy(root) ){
            if(balanceFactor(root.getRightChild())>0) {
                root.setRightChild(rotateRight(root.getRightChild()));
            }
            return rotateLeft(root);
            }
        return root;
    }

    /**
     * Method to perform left rotation for balancing
     * @param root StudentRecord root of the tree
     * @return Root after rotating left
     */

    private StudentRecord rotateLeft(StudentRecord root){
        StudentRecord newRoot;
        newRoot = root.getRightChild();
        root.setRightChild(newRoot.getLeftChild());
        newRoot.setLeftChild(root);
        calculateHeight(root);
        calculateHeight(newRoot);
        return newRoot;
    }

    /**
     * Method to perform right rotation for balancing
     * @param root StudentRecord root of the tree
     * @return Root after rotating right
     */

    private StudentRecord rotateRight(StudentRecord root){
        StudentRecord newRoot;
        newRoot = root.getLeftChild();
        root.setLeftChild(newRoot.getRightChild());
        newRoot.setRightChild(root);
        calculateHeight(root);
        calculateHeight(newRoot);
        return newRoot;
    }

    /**
     * Method to detect if the tree is left heavy
     * @param studentRecord StudentRecord root of the tree
     * @return boolean True if left heavy and false if not
     */

    private boolean isLeftHeavy(StudentRecord studentRecord){
        return  balanceFactor(studentRecord) > 1;
    }

    /**
     * Method to detect if the tree is right heavy
     * @param studentRecord StudentRecord root of the tree
     * @return boolean True if right heavy and false if not
     */

    private boolean isRightHeavy(StudentRecord studentRecord){
        return balanceFactor(studentRecord) < -1;
    }

    /**
     * Method to calculate the balance factor(height of left subtree - height of right sub tree) of the tree
     * @param studentRecord StudentRecord node whose balance factor needs to be called
     * @return int balance factor
     */

    private int balanceFactor(StudentRecord studentRecord) {
        if (studentRecord ==  null) {
            return 0;
        }
        else {
            return calculateHeight(studentRecord.getLeftChild()) - calculateHeight(studentRecord.getRightChild());
        }
    }

    /**
     * Calculate height of the tree
     * @param studentRecord Root of the tree
     * @return int height
     */

    private int calculateHeight(StudentRecord studentRecord){
        if(studentRecord == null){
            return 0;
        }
        else{
            studentRecord.setHeight(Math.max(calculateHeight(studentRecord.getLeftChild()) ,calculateHeight(studentRecord.getRightChild())) + 1);
            return studentRecord.getHeight();
        }
    }

    /**
     * Traverse the AVL tree inorder and stpre the BNumber and skills into the string builder of results instance.
     * @param results Results instance
     * @param root Root of the tree
     */

    public void printNodes(Results results, StudentRecord root){
        if(root == null){
            return;
        }
        printNodes(results, root.getLeftChild());
        results.storeResults(root.getbNumber(),root.getSkills());
        printNodes(results,root.getRightChild());
        MyLogger.writeMessage("All nodes of Tree" + this.treeID +" stored in data structure of Results" +
                " instance.", MyLogger.DebugLevel.OUTPUT_DATA_STORED);
    }
}
