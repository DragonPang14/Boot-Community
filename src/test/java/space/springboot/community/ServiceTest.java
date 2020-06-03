package space.springboot.community;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import space.springboot.community.aspect.HyperLogInc;
import space.springboot.community.service.QuestionService;

public class ServiceTest extends BaseTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void TagTest(){
        System.out.println(questionService.findTagByName("灌水"));
    }

    @Test
    @HyperLogInc(description = "test")
    public void aopTest(){
        System.out.println("aop Test!");
    }
}
