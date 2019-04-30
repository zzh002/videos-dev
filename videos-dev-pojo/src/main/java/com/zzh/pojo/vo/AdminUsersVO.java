package com.zzh.pojo.vo;

/**
 * @author ZZH
 * @date 2019/1/23 9:11
 **/
public class AdminUsersVO {

    private String id;
    private String username;
    private String password;
    private String token;

    public AdminUsersVO() {
    }

    public AdminUsersVO(String id, String username, String password, String token) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
