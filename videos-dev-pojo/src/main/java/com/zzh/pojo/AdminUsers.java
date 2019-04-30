package com.zzh.pojo;

/**
 * @author ZZH
 * @date 2019/1/23 9:11
 **/
public class AdminUsers {

    private String id;
    private String username;
    private String password;

    public AdminUsers() {
    }

    public AdminUsers(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
