package pl.edu.agh.mobilecodereviewer.model;

import java.util.List;
import java.util.Map;

public class LabelInfo {

    String name;

    List<ApprovalInfo> all;

    Map<Integer, String> values;

    public LabelInfo(String name, List<ApprovalInfo> all, Map<Integer, String> values){
        this.name = name;
        this.all = all;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ApprovalInfo> getAll() {
        return all;
    }

    public void setAll(List<ApprovalInfo> all) {
        this.all = all;
    }

    public Map<Integer, String> getValues() {
        return values;
    }

    public void setValues(Map<Integer, String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "LabelInfo{" +
                "name='" + name + '\'' +
                ", all=" + all +
                ", values=" + values +
                '}';
    }

}
