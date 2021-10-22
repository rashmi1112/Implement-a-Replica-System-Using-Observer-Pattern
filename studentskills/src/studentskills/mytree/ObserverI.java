package studentskills.mytree;

import studentskills.UpdateType.UpdateType;

/**
 * Interface that contains methods to be implemented by Observer Nodes
 * @author Rashmi Badadale
 */

public interface ObserverI {
    void update(StudentRecord updatedNode, StudentRecord studentRecord, UpdateType updateType);
}
