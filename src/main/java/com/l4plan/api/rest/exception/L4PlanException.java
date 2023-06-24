package com.l4plan.api.rest.exception;

public class L4PlanException extends RuntimeException {

    public L4PlanException(){
        super();
    }

    public L4PlanException(String msg){
        super(msg);
    }
}
