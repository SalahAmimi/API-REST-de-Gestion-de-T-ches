package com.example.demo.exception;

public class TaskDoesNotExist extends Exception{
    private String msg;
    public TaskDoesNotExist(String msg){
        super(msg);
        //this.msg = msg;
    }
    public String toString(){
        return msg;
    }
}
