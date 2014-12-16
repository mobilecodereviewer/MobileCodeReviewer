package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.utils;

import dk.brics.automaton.Automaton;
import dk.brics.automaton.BasicAutomata;
import dk.brics.automaton.BasicOperations;
import dk.brics.automaton.RegExp;

public class StringPatternMatcher {
    private final Automaton regexpAutomation;

    public StringPatternMatcher(String regexp) {
        if (regexp != null) {
            if (regexp.length() > 0 && regexp.charAt(0) == '^') {
                regexp = regexp.substring(1);
                regexpAutomation = new RegExp(regexp).toAutomaton();
            } else {
                regexpAutomation = BasicAutomata.makeString(regexp);
            }
        } else {
            regexpAutomation = null; // not used
        }
    }

    public boolean match(String item) {
        if (regexpAutomation == null){
            return item == null;
        }

        return BasicOperations.run(regexpAutomation,item);
    }
}
