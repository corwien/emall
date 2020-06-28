package com.emall.controller.portal;

import com.emall.common.Const;
import com.emall.common.ServerResponse;
import com.emall.pojo.User;
import com.emall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by kaiyiwang on 18/1/22.
 *
 // https://how2j.cn/k/ssm/ssm-tutorial/1137.html#nowhere 【20200629】
 // 在实例化CategoryController的时候，注入CategoryServiceImpl。 (自动装配实现了CategoryService接口的的实例，只有CategoryServiceImpl实现了CategoryService接口，所以就会注入CategoryServiceImpl)
 // 1.实例化UserController,注入UserServiceImpl,(自动装配实现了IUserService接口的实例,只有UserServiceImpl实现了IUserService接口，所以就会注入UserServiceImpl)
 // 2.在实例化 UserServiceImpl 的时候,又注入 UserMapper
 // 3.根据ApplicationContext.xml中的配置信息，将UserMapper.java和UserMapper.xml关联起来了。
 */

// UserController起到Controller的作用
@Controller

// 下面的意思是请求地址全部打到/user/命名空间下,起到重用的作用
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     *
     * /user/login.do
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    @ResponseBody  // 该注释,返回时自动返回序列化为json
    public ServerResponse<User> login(String username, String password, HttpSession session){

        // service->mybatis->dao

        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @RequestMapping(value="login.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){

        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type){
        return iUserService.checkValid(str, type);
    }


    /**
     * 获取用户信息
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }

        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前信息");

    }

    @RequestMapping(value = "forget_get_question.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);

    }

    @RequestMapping(value = "forget_check_answer.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer){

        // https://how2j.cn/k/ssm/ssm-tutorial/1137.html#nowhere 【20200629】
        // 在实例化CategoryController的时候，注入CategoryServiceImpl。 (自动装配实现了CategoryService接口的的实例，只有CategoryServiceImpl实现了CategoryService接口，所以就会注入CategoryServiceImpl)
        // 1.实例化UserController,注入UserServiceImpl,(自动装配实现了IUserService接口的实例,只有UserServiceImpl实现了IUserService接口，所以就会注入UserServiceImpl)
        // 2.在实例化 UserServiceImpl 的时候,又注入 UserMapper
        // 3.根据ApplicationContext.xml中的配置信息，将UserMapper.java和UserMapper.xml关联起来了。
        return iUserService.checkAnswer(username, question, answer);

    }




}
