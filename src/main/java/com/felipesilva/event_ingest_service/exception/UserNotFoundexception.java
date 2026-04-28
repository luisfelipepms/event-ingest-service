package com.felipesilva.event_ingest_service.exception;

public class UserNotFoundexception extends RuntimeException{
    public UserNotFoundexception(){
        super("Usuário não encontrado!");
    };
}
