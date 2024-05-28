package pastebin.mainservice.error;

import lombok.Data;

import java.util.Date;

@Data
public class ApplicationError {

    private String message;
    private int code;
    private Date timeStamp;

    public ApplicationError(int code, String message) {
        this.code = code;
        this.message = message;
        this.timeStamp = new Date();
    }

}
