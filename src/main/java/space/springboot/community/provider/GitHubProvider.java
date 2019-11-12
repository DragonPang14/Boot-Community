package space.springboot.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import space.springboot.community.dto.AccessTokenDto;
import space.springboot.community.dto.GitHubUserDto;

import java.io.IOException;

@Component
public class GitHubProvider {

    private static String getAccessUrl = "https://github.com/login/oauth/access_token";
    private static String getUserUrl = "https://api.github.com/user?";

    /**
     * 获取github的accesstoken
     * @param accessTokenDto
     * @return
     */
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url(getAccessUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String resultBody = response.body().string();
            System.out.println(resultBody);
            return resultBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @desc 获取GitHub用户
     * @param accessToken
     * @return
     */
    public GitHubUserDto getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getUserUrl + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String resStr = response.body().string();
            System.out.println(resStr);
            GitHubUserDto gitHubUserDto = JSON.parseObject(resStr, GitHubUserDto.class);
            return gitHubUserDto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
