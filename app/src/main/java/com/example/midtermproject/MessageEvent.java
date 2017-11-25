package com.example.midtermproject;

/**
 * Created by qingming on 2017/10/29.
 */
public  class MessageEvent {
    private int code;
    private Role role;
    public MessageEvent(){
        role=new Role();
    }
    public MessageEvent(Role role1){
        setRole(role1);
    }
    public void setRole(Role role1){
        this.role=role1;
    }
    public Role getRole(){
        return role;
    }
}