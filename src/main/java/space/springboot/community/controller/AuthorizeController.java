package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.springboot.community.dto.AccessTokenDto;
import space.springboot.community.dto.GitHubUserDto;
import space.springboot.community.provider.GitHubProvider;


@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    private static String client_secret = "ed0b52d1c11bf5d5b27d969570dc0b8688051cfc";
    private static String client_id = "dc364cf81cbdc28a0e43";
    private static String callbackUrl = "http://localhost:8887/callback";

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state, Model model){

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setRedirect_uri(callbackUrl);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        String accessToken= gitHubProvider.getAccessToken(accessTokenDto);
        GitHubUserDto gitHubUserDto = gitHubProvider.getUser(accessToken);
        System.out.println(gitHubUserDto.getName());
        return "index";
    }
}
