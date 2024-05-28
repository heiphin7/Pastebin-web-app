package pastebin.mainservice.error;

import lombok.Data;

import java.util.Date;

@Data
public class ApplicationError {

    private String message;
    private int httpStatus;
    private String path;
    private String errorType;
    private Date timeStamp;

    // For messages with exception
    public ApplicationError(int code, String message, String errorType, String path) {
        this.httpStatus = code;
        this.path = path;
        this.errorType = errorType;
        this.message = message;
        this.timeStamp = new Date();
    }

    // For succes messages
    public ApplicationError(int code, String message, String path) {
        this.httpStatus = code;
        this.message = message;
        this.timeStamp = new Date();
        this.path = path;
        this.errorType = "Success";
    }

}
