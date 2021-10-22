package studentskills.mytree;

import studentskills.UpdateType.UpdateType;

/**
 * Interface that contains methods to be implemented by Subject Nodes
 * @author Rashmi Badadale
 */

public interface SubjectI {
    void register(StudentRecord studentRecord);
    void unregister(StudentRecord studentRecord);
    void notifyAll(StudentRecord studentRecord, UpdateType updateType);
}
