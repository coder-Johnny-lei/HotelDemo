package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.annotation.UserLoginToken;
import com.example.demo.entity.User;
import com.example.demo.pojo.ResponseData;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody User user, HttpServletResponse response){
        Map<String,Object> map=new HashMap<String,Object>();
        User userForBase = new User();
        User userByUserName = userService.findUserByUserName(user.getUserName());
        if(userByUserName==null){
            map.put("code",1);
            map.put("msg","用户名错误,请重新输入");
            map.put("token",null);
            return map;
        }
        userForBase.setUserId(userByUserName.getUserId());
        userForBase.setUserName(userByUserName.getUserName());
        userForBase.setPassword(userByUserName.getPassword());
        if(!userForBase.getPassword().equals(user.getPassword())){
            map.put("code2",2);
            map.put("message","登录失败,密码错误!");
            map.put("token",null);
            return map;
        }else {
            String token = tokenService.getToken(userForBase);
            map.put("code",3);
            map.put("msg","你已经登陆成功!");
            map.put("token",token);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return  map;
        }

    }


    @UserLoginToken
    @GetMapping("/list")
    public ResponseData getUserList(){
        List<User> userListAll = userService.getUserListAll();
        return new ResponseData(
                userListAll.size()==0?ResponseData.STATUS_FAIL:ResponseData.STATUS_OK,
                userListAll.size()==0?"查找失败":"查找成功",
                userListAll
        );
    }
}
