package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.parser;

/**
 * Created by lee on 2014-10-31.
 */
public class AgeValue {
    private final AgeType type;
    private final int value;

    public AgeValue(AgeType type, int value) {
        this.type = type;
        this.value = value;
    }

    public int value() {
        return value;
    }

    public AgeType type() {
        return type;
    }
}
