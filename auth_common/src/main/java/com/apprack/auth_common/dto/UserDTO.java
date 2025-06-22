package com.apprack.auth_common.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private Long id;
    private String username;
    private String createdTimeStamp;

    public UserDTO() {}

    public UserDTO(Long id, String username, String createdTimeStamp) {
        this.id = id;
        this.username = username;
        this.createdTimeStamp = createdTimeStamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(String createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }
}
