package com.felipesilva.event_ingest_service.exception;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(){

    }

    public void setStatus(int status){
        this.status = status;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public int getStatus(){
        return this.status;
    }

    public String getMessage(){
        return this.message;
    }
}
