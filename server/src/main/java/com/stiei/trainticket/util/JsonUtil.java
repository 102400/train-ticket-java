package com.stiei.trainticket.util;

import com.stiei.trainticket.bean.JsonParam;
import com.stiei.trainticket.mapper.entity.User;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.Optional;

public class JsonUtil {

    public static Optional<User> getUser(JsonParam jsonParam) {
        RedissonClient redissonClient = Redisson.create();  // 后续改用spring redis

        RMap<String, String> map = redissonClient.getMap("train_ticket_java_phone_" + jsonParam.getPhone());
        if (!map.get("token").equals(jsonParam.getToken())) {
            return Optional.empty();
        }
        return Optional.of(new User()
                    .setId(Long.parseLong(map.get("id")))
                    .setPhone(jsonParam.getPhone()));
    }

}
