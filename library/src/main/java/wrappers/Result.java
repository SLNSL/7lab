package wrappers;

public interface Result<T> {

    boolean hasError();

    void setError(String error,  int errorType);

    void setResult(T result);

    String getError();

    T getResult();

    int getErrorType();

}
