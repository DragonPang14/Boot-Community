package space.springboot.community;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import space.springboot.community.controller.UserController;
import space.springboot.community.service.QuestionService;
import space.springboot.community.service.UserService;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceTest extends BaseTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void TagTest() {
        System.out.println(questionService.findTagByName("灌水"));
    }

    @Test
    public void aopTest() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(new Date(System.currentTimeMillis())));
    }

    @Test
    public void normalTest() {

        String[] test = {"   X O  O "," X X    O ","X  X    O ","X    OX O ","X   XO  O ","X  X O  O ","O  X O  O ","     O  OX","     O  O ","   X XXXO "};
        int len = test.length;
        char[][] list = new char[test.length][test.length];
        for (int i = 0; i < test.length; i++) {
            list[i] = test[i].toCharArray();
        }
        for (int i = 0; i < len; i++) {
            boolean flag = true;
            for (int j = 0; j < len; j++) {
                if (list[i][j] != list[i][0]){
                    flag = false;
                    break;
                }
            }
            if (flag)
                System.out.println("row:" + String.valueOf(list[i][0]));
        }
        for (int j = 0; j < len; j++) {
            boolean flag = true;
            for (int i = 0; i < len; i++) {
                if (list[i][j] != list[0][j]){
                    flag = false;
                    break;
                }
            }
            if (flag){
                System.out.println("col:" + String.valueOf(list[0][j]));
            }
        }

        boolean crossFlag = true;
        for (int i = 0; i < len ; i++) {
            if (list[i][i] != list[0][0]){
                crossFlag = false;
                break;
            }
        }
        if (crossFlag){
            System.out.println("cross:" + String.valueOf(list[0][0]));
        }

        boolean reCrossFlag = true;
        for (int i = 0; i < len; i++) {
            if (list[i][len - i - 1] != list[0][len - 1]){
                reCrossFlag = false;
                break;
            }
        }
        if (reCrossFlag){
            System.out.println("reCross:" + String.valueOf(list[0][len - 1]));
        }

        System.out.println("Darw");
    }

    @Test
    public void breakTest() throws Exception{
        UserController userController = new UserController();
        UserService userService = new UserService();
        Class<? extends UserController> clazz = userController.getClass();
        Field service = clazz.getDeclaredField("userService");
        for (int i = 0 ;i < 10; i++){
            if (i == 5)
                break;
            System.out.println("not break" + i);
        }
    }

    @Test
    public void contextTest(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tx.xml");
    }
}
