package com.jw22.community.controller;

import com.jw22.community.dto.AccessTokenDTO;
import com.jw22.community.dto.UserDTO;
import com.jw22.community.mapper.UserMapper;
import com.jw22.community.model.UserModel;
import com.jw22.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class OAuthController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(value="code") String code,
                           @RequestParam(value="state") String state,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        String token = gitHubProvider.getToken(accessTokenDTO);
        UserDTO userDTO = gitHubProvider.getUser(token);
        if (userDTO != null) {
            // write cookie and session information
            UserModel userModel = new UserModel();
            userModel.setAccountId(String.valueOf(userDTO.getId()));
            userModel.setName(userDTO.getName());
            userModel.setToken(UUID.randomUUID().toString());
            userModel.setCreateTime(System.currentTimeMillis());
            userModel.setModifyTime(userModel.getCreateTime());
            userMapper.insert(userModel);
            request.getSession().setAttribute("user", userDTO);
            return "redirect:/";
        } else {
            // login again
            return "redirect:/";
        }
    }
}
