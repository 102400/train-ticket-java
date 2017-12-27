package com.stiei.trainticket.controller;

import com.stiei.trainticket.Final;
import com.stiei.trainticket.annotation.Token;
import com.stiei.trainticket.bean.AjaxResult;
import com.stiei.trainticket.bean.JsonParam;
import com.stiei.trainticket.json.PhoneAndPasswordJson;
import com.stiei.trainticket.mapper.entity.User;
import com.stiei.trainticket.service.AccountService;
import com.stiei.trainticket.util.JsonUtil;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/account", method = RequestMethod.POST)
public class AccountController extends BaseController {

    @Autowired private AccountService accountService;

    @Token(false)  // 不带token访问
    @RequestMapping(path = "/register")
    public AjaxResult register(@RequestBody JsonParam<PhoneAndPasswordJson> param) {
        AjaxResult ajaxResult = AjaxResult.newError("");
        try {ajaxResult = accountService.register(param);} catch (Exception e) {e.printStackTrace();}
        return ajaxResult;
    }

    @Token(false)
    @RequestMapping(path = "/login")
    public AjaxResult login(@RequestBody JsonParam<PhoneAndPasswordJson> param) {
        AjaxResult ajaxResult = AjaxResult.newError("");
        try {ajaxResult = accountService.login(param);} catch (Exception e) {e.printStackTrace();}
        return ajaxResult;
    }

    @RequestMapping(path = "/about")
    public AjaxResult about(@RequestBody JsonParam<String> param) {
        AjaxResult[] ajaxResult = {AjaxResult.newIllegalAccess("")};

        JsonUtil.getUser(param).ifPresent(user -> {
            try {ajaxResult[0] = accountService.about(user, param);} catch (Exception e) {e.printStackTrace();AjaxResult.newError("");}
        });

        return ajaxResult[0];
    }

}