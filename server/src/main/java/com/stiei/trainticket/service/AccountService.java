package com.stiei.trainticket.service;

import com.stiei.trainticket.bean.AjaxResult;
import com.stiei.trainticket.bean.JsonParam;
import com.stiei.trainticket.json.PhoneAndPasswordJson;
import com.stiei.trainticket.controller.BaseController;
import com.stiei.trainticket.mapper.UserMapper;
import com.stiei.trainticket.mapper.entity.User;
import com.stiei.trainticket.util.SecurityUtil;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AccountService extends BaseController {

    @Autowired private UserMapper userMapper;  // 无视这个错误

    public AjaxResult register(JsonParam<PhoneAndPasswordJson> param) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        userMapper.insert(new User()
                .setPhone(param.getData().getPhone())
                .setPassword(SecurityUtil.MD5(param.getData().getPassword()))
        );

        return AjaxResult.newSuccess("");
    }

    public AjaxResult login(JsonParam<PhoneAndPasswordJson> param) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        boolean[] ok = {false};

        String token = SecurityUtil.MD5(new Date().getTime() + param.getData().getPassword());
        String password = SecurityUtil.MD5(param.getData().getPassword());

        Optional.ofNullable(userMapper.selectByPhone(param.getData().getPhone())).ifPresent(user -> {
            if (user.getPassword().equals(password)) {
                RedissonClient redissonClient = Redisson.create();  // 后续改用spring redis
                RMap<String, String> map = redissonClient.getMap("train_ticket_java_phone_" + user.getPhone());
                map.put("id", user.getId().toString());
                map.put("token", token);
                ok[0] = true;
            }
        });

        return ok[0] ? AjaxResult.newSuccess(Map.of("token", token)) : AjaxResult.newError("");
    }

    public AjaxResult about(User user, JsonParam<String> param) {

//        return AjaxResult.newSuccess("id " + user.getId() + ", phone" + user.getPhone());

        return AjaxResult.newSuccess(Map.of(
                "id", user.getId(),
                "phone", user.getPhone()
        ));
    }

}
