
package com.sk.userman;


import com.sk.userman.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsermanApplicationTests {



    @Test
    public void healthz() throws Exception {
        System.out.println("Test success");
    }

}
