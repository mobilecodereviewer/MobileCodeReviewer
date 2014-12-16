package pl.edu.agh.mobilecodereviewer.controllers.utilities;

import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;

public class ChangesFilter {
    private String name;
    private String query;

    public ChangesFilter(String name, String query) {
        this.name = name;
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public String getQuery() {
        return query;
    }

    public static ChangesFilter createChangeFilterFromChangeStatus(ChangeStatus changeStatus) {
        switch (changeStatus) {
            case NEW:
                return new ChangesFilter("NEW","status:open");
            case MERGED:
                return new ChangesFilter("MERGED","status:merged");
            case ABANDONED:
                return new ChangesFilter("ABANDONED","status:abandoned");
            case ALL:
                return new ChangesFilter("ALL", "status:all");
            case DRAFT:
                return new ChangesFilter("DRAFT","is:draft");
            case SUBMITTED:
                return new ChangesFilter("SUBMITTED","is:submitted");
        }
        throw new IllegalArgumentException("Not Found status");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangesFilter that = (ChangesFilter) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (query != null ? !query.equals(that.query) : that.query != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (query != null ? query.hashCode() : 0);
        return result;
    }
}
