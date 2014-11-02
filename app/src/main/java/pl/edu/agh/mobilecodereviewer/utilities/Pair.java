package pl.edu.agh.mobilecodereviewer.utilities;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (!first.equals(pair.first)) return false;
        if (!second.equals(pair.second)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

}
