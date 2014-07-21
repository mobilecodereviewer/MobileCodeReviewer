package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;

/**
 * Tuple of the two element
 * @param <X> Type of first element
 * @param <Y> Type of second element
 */
public class Pair<X,Y> {
    /**
     * First element of tuple
     */
    public X first;

    /**
     * Second element of tuple
     */
    public Y second;

    /**
     * Construct empty tuple
     */
    public Pair() {
    }

    /**
     * Construct two element tuple
     * @param first First element of tuple
     * @param second Second element of tuple
     */
    public Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }
}
