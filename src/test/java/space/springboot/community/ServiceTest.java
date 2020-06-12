package space.springboot.community;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import space.springboot.community.service.QuestionService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceTest extends BaseTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void TagTest(){
        System.out.println(questionService.findTagByName("灌水"));
    }

    @Test
    public void aopTest(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(new Date(System.currentTimeMillis())));
    }

    @Test
    public void normalTest(){
            String str=1897+"";
            StringBuffer sb=new StringBuffer(str);
            StringBuffer rsb=sb.reverse();
            System.out.println(rsb);
    }
}
