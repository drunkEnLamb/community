package life.beyond.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;
    public CustomizeException(CustomizeErrorCode customizeErrorCode) {
        message = customizeErrorCode.getMessage();
        code = customizeErrorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
