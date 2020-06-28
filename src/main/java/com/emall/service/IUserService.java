package com.emall.service;

import com.emall.common.ServerResponse;
import com.emall.pojo.User;

/**
 * Created by kaiyiwang on 18/1/22.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);
}
