package pl.edu.agh.mobilecodereviewer.model;


import com.google.common.collect.Lists;

import java.util.List;

public class SourceCode {
    private List<Line> lines;

    public SourceCode() {
        lines = Lists.newArrayList();
    }

    public SourceCode(List<Line> lines) {
        this.lines = lines;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
