package pl.edu.agh.mobilecodereviewer.exceptions;

public class HTTPErrorException extends Exception{

    private int errorCode;

    public HTTPErrorException(int errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
