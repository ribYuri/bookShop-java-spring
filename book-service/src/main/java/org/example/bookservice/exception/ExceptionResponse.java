package org.example.bookservice.exception;

import java.io.Serializable;
import java.util.List;


public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private List<String> erros;

    public ExceptionResponse(String message, List<String> erros) {
        this.message = message;
        this.erros = erros;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErros() {
        return this.erros;
    }

    public void setErros(String details) {
        this.erros = erros;
    }

}
