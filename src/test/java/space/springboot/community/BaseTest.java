package space.springboot.community;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
public class BaseTest {

    @Before
    public void init(){
        System.out.println("test start");
    }

    @After
    public void after(){
        System.out.println("test end");
    }
}
