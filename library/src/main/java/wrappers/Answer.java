package wrappers;

public interface Answer {

    Object getResult();

    void setResult(Object result);

    String getError();

    void setError(String error);

    int getErrorType();

    void setErrorType(int errorType);

    boolean hasError();

    void setObject(Object obj);

    Object getObject();

    void setAnswerType(AnswerType answerType);

    AnswerType getAnswerType();
}
