package com.jw22.community.provider;

import com.alibaba.fastjson.JSON;
import com.jw22.community.dto.AccessTokenDTO;
import com.jw22.community.dto.UserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {
    public String getToken(AccessTokenDTO dto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String tokenStr = string.split("&")[0];
            return tokenStr.split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserDTO getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
           String responseStr = response.body().string();
           return JSON.parseObject(responseStr, UserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
