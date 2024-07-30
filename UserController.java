package org.example.digitalculturalportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.digitalculturalportal.common.CommonResult;
import org.example.digitalculturalportal.common.ResultCode;
import org.example.digitalculturalportal.pojo.User;
import org.example.digitalculturalportal.pojo.UserLoginParam;
import org.example.digitalculturalportal.service.UserService;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;


/**
 * @Author: adlx
 * @Description: TODO
 * @DateTime: 2024/7/11 18:57
 **/
@Controller
@Api(tags = "UserController")
@Tag(name = "UserController", description = "用户管理")
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @ApiOperation("用户登录,返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginParam userLoginParam) {
        String username = userLoginParam.getUsername();
        String password = userLoginParam.getPassword();
        LOGGER.info("passwordEncode: {}", passwordEncoder.encode("123456"));
        String token = userService.login(username,password);
        if (token == null) {
            return CommonResult.error(ResultCode.INPUT_ERROR);
        }
        LOGGER.info("登录参数：{}， token: {}", userLoginParam, token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestBody User user) {

        return CommonResult.success();
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/getInfo",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getInfo(@RequestParam("token") String token) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "adlx");
        map.put("avatar", "https://example.com/avatar.jpg");
        return CommonResult.success(map);
    }

    @ApiOperation("退出登录")
    @PreAuthorize("hasAuthority('test')")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
//    @PreAuthorize("hasAuthority('dcc:use')")
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success();
    }



}
