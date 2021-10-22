package studentskills.UpdateType;

/**
 * Enum to store the update type (Update or Modify)
 */
public enum UpdateType {
    INSERT("Insert_update"),
    MODIFY("Modify_update");

    private String update;
    UpdateType(String modify_update) {
        this.update = modify_update;
    }

}
