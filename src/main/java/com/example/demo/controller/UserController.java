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

    @PostMapping("/doLogin")
    public Map<String,Object> doLogin(@RequestBody User user) {
        Map<String,Object> map=new HashMap<String,Object>();
        String userName = user.getUserName();
        String password = user.getPassword();
        User user2 = userService.findUserByUserName(userName);
        if (user2 == null) {
            map.put("code",1);
            map.put("msg","用户名错误,请重新输入");
            return map;
        } else if (!user2.getPassword().equals(password)) {
            map.put("code",1);
            map.put("msg","密码输入错误");
            return  map;
        } else {
            //登录成功
            String token = TokenUtil.sign(userName,password);
            System.out.println(token);
            //登录成功
            map.put("code",0);
            map.put("token",token);
            map.put("userId",user2.getUserId());
            map.put("msg","登录成功");
            return map;
        }
    }


    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody User user, HttpServletResponse response){
        Map<String,Object> map=new HashMap<String,Object>();
        User userForBase = new User();
        User userByUserName = userService.findUserByUserName(user.getUserName());
        if(userByUserName==null){
            map.put("code",1);
            map.put("msg","用户名错误,请重新输入");
            return map;
        }
        userForBase.setUserId(userService.findUserByUserName(user.getUserName()).getUserId());
        userForBase.setUserName(userService.findUserByUserName(user.getUserName()).getUserName());
        userForBase.setPassword(userService.findUserByUserName(user.getUserName()).getPassword());
        if(!userForBase.getPassword().equals(user.getPassword())){
            map.put("message","登录失败,密码错误!");
            return map;
        }else {
            String token = tokenService.getToken(userForBase);
            map.put("token",token);
            map.put("msg","你已经登陆成功!");
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return  map;
        }

    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        System.out.println(TokenUtil.getTokenUserId());
        return "您已通过验证";
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
