package com.stiei.trainticket.mapper;

import com.stiei.trainticket.mapper.entity.User;

public interface UserMapper {

    void insert(User user);
    User selectByPhone(String phone);

}
