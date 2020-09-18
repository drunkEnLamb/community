package life.beyond.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    public CustomizeException(CustomizeErrorCode customizeErrorCode) {
        message = customizeErrorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
