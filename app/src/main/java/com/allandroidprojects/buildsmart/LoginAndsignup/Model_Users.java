package com.allandroidprojects.buildsmart.LoginAndsignup;

public class Model_Users {
    String mobileno,password;

    public Model_Users() {
    }

    public Model_Users(String mobileno , String password) {
        this.mobileno = mobileno;
        this.password= password;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
