package pl.edu.agh.mobilecodereviewer.model;

/**
 * Model represents brief information about File.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class FileInfo {

    private String fileName;

    /**
     * No-argument constructor ,doesnt initialize any fields etc.
     */
    public FileInfo() {
    }

    public FileInfo(String fileName) {
        this.fileName = fileName;
    }

    public static FileInfo valueOf(String fileName) {
        return new FileInfo(String.valueOf(fileName));
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "fileName='" + fileName + '\'' +
                '}';
    }
}
