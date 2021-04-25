package wrappers;

import java.io.Serializable;

public class AnswerPacket implements Answer, Serializable {
    private static final long serialVersionUID = 1234567890L;
    private Object result;

    private String error = "";
    private int errorType;//2 - выход; 1 - ошибка есть, просто вывести; 0 - ничего не делать

    private Object object;

    private AnswerType answerType = AnswerType.DEFAULT;

    public AnswerPacket() {
    }


    public AnswerPacket(Object result) {
        this.result = result;
    }

    public AnswerPacket(String error, int errorType) {
        this.error = error;
        this.errorType = errorType;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    @Override
    public boolean hasError() {
        return !error.isEmpty();
    }

    @Override
    public void setObject(Object obj) {
        this.object = obj;
    }

    @Override
    public Object getObject() {
        return object;
    }

    @Override
    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    @Override
    public AnswerType getAnswerType() {
        return answerType;
    }
}
