package com.zt.solr.dto;

import com.zt.solr.domain.user.User;

import java.util.List;

/**
 * User: zhangtan
 * Date: 13-6-7
 * Time: 下午6:55
 */
public class UserIndexDto {

    List<User>  userList ;


    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
