package pl.edu.agh.mobilecodereviewer.model;

public class SubmissionResult {

    private boolean success;

    private String message;

    public SubmissionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SubmissionResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
