package com.usermanager.service;

public class UserService {

    public Boolean registerUser(){
        return false;
    }
    /**
     * above main method to register and check if user exist in db
     */

    public String encodePassword(final String password){
        return "";
    }
    public void readPassword(final Long userId){
        // this method we can use for "I forgot password"
    }

    public boolean isUserExistInDb(){
        return true;
    }

}
