package pl.edu.agh.mobilecodereviewer.model;

/**
 * Model represents brief information about change.
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class ChangeInfo {
    /**
     * Name of change
     */
    private String name;

    /**
     * No-argument constructor ,doesnt initialize any fields etc.
     */
    public ChangeInfo() {
    }

    /**
     * Construct change with given name
     * @param name
     */
    public ChangeInfo(String name) {
        this.name = name;
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#name}
     * @return Name of change
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo#name}
     * @param name Name to be set for change
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Construct {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo} from given name,
     * when name is null then initialize it as zero character string
     * @param name Name of change
     * @return Constructed {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo}
     */
    public static ChangeInfo valueOf(String name) {
        return new ChangeInfo( String.valueOf(name) );
    }

    /**
     * Return textual representation of @{link ChangeInfo}
     * @return Name of change
     */
    @Override
    public String toString() {
        return name;
    }
}
