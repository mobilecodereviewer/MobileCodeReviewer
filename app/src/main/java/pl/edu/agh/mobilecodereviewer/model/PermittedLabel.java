package pl.edu.agh.mobilecodereviewer.model;

import java.util.List;

public class PermittedLabel {

    private String name;

    private List<Integer> values;

    public PermittedLabel(String name, List<Integer> values){
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "PermittedLabel{" +
                "name='" + name + '\'' +
                ", values=" + values +
                '}';
    }
}
