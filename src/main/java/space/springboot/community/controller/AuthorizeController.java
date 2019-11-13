package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.springboot.community.dto.AccessTokenDto;
import space.springboot.community.dto.GitHubUserDto;
import space.springboot.community.provider.GitHubProvider;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${callback.url}")
    private String callbackUrl;

    /**
     * @desc github的OAuth回调方法
     * @param code
     * @param state
     * @param model
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           Model model,
                           HttpServletRequest request){

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setRedirect_uri(callbackUrl);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        String accessToken= gitHubProvider.getAccessToken(accessTokenDto);
        GitHubUserDto user = gitHubProvider.getUser(accessToken);
        if(user != null){
//            登陆成功
            request.getSession().setAttribute("user",user);
//            重定向
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
