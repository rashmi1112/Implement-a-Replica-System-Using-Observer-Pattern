package studentskills.util;

import studentskills.mytree.AVLTree;

/**
 * Class to get the unique ID of the tree
 */
public class TreeID {

    /**
     * Methdo to get the TreeID
     * @param avlTree AVLTree Tree whose ID is required
     * @return int TreeID
     */
    public static int getTreeID(AVLTree avlTree){
        return avlTree.getTreeID();
    }

    public String toString(){
        return " Class to get the TreeID of the tree";
    }
}
