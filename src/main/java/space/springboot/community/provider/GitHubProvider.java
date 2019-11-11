package space.springboot.community.provider;

import okhttp3.*;
import org.springframework.stereotype.Component;
import space.springboot.community.dto.AccessTokenDto;

import java.io.IOException;

@Component
public class GitHubProvider {

    private static String getAccessUrl = "https://github.com/login/oauth/access_token";

    /**
     * 获取github的accesstoken
     * @param accessTokenDto
     * @return
     */
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(getAccessUrl)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            System.out.println(body);
            return body;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
