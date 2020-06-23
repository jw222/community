package com.jw22.community.controller;

import com.jw22.community.dto.AccessTokenDTO;
import com.jw22.community.dto.UserDTO;
import com.jw22.community.model.User;
import com.jw22.community.provider.GitHubProvider;
import com.jw22.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class OAuthController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(value="code") String code,
                           @RequestParam(value="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getToken(accessTokenDTO);
        UserDTO userDTO = gitHubProvider.getUser(accessToken);
        if (userDTO != null) {
            // write session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setAccountId(String.valueOf(userDTO.getId()));
            user.setName(userDTO.getName());
            user.setToken(token);
            user.setProfilePath(userDTO.getAvatar_url());
            userService.createOrUpdate(user);

            // write cookie
            response.addCookie(new Cookie("token", token));
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else {
            // login again
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
