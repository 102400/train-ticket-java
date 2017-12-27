package com.stiei.trainticket.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stiei.trainticket.Final;
import com.stiei.trainticket.annotation.Token;
import com.stiei.trainticket.bean.JsonParam;
import com.stiei.trainticket.mapper.entity.User;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Deprecated
@Component
public class AuthWebRequestInterceptor implements HandlerInterceptor {

    /**
     * 是否允许访问
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (!(o instanceof HandlerMethod)) return true;  // 允许访问

        HandlerMethod handlerMethod = (HandlerMethod) o;
        Token tokenAnnotation = handlerMethod.getMethod().getAnnotation(Token.class);
        if (tokenAnnotation != null && !tokenAnnotation.value()) return true;

//        StringBuilder sb = new StringBuilder();
//        httpServletRequest.getReader().lines().forEach(sb::append);
//        httpServletRequest.getReader().lines().forEach(str -> {
//            sb.append(str.replace("\t", ""));
//        });

//        System.out.println(sb.toString());

//        JsonParam jsonParam = new ObjectMapper().readValue(sb.toString(), new TypeReference<JsonParam<Object>>() {});


        try {
            JsonParam jsonParam = new ObjectMapper().readValue(httpServletRequest.getReader(), JsonParam.class);

            RedissonClient redissonClient = Redisson.create();  // 后续改用spring redis
            RMap<String, String> map = redissonClient.getMap("train_ticket_java_phone_" + jsonParam.getPhone());
            if (!map.get("token").equals(jsonParam.getToken())) {
                httpServletResponse.setStatus(403);

                return false;
            }

            httpServletRequest.setAttribute(Final.USER, new User()
                    .setId(Long.parseLong(map.get("id")))
                    .setPhone(jsonParam.getPhone())
            );

            System.out.println("hahaha");

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();

            httpServletResponse.setStatus(403);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        httpServletResponse.setHeader("Content-Type","application/json");
    }
}
