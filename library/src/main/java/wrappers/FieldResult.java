package wrappers;


public class FieldResult<T> implements Result<T> {
    private T result;
    private String error;
    private int errorType;

    public FieldResult() {
    }

    public FieldResult(String error, int type){
        this.error = error;
        this.errorType = type;
    }

    public FieldResult(T result) {
        this.result = result;
    }

    public boolean hasError() {
        if (error == null) return false;
        return true;
    }

    public void setError(String error, int errorType) {
        this.error = error;
        this.errorType = errorType;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public T getResult() {
        return result;
    }

    @Override
    public int getErrorType() {
        return errorType;
    }


}
