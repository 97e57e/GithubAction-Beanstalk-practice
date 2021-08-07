package lee.garden.cicdtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class CicdTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CicdTestApplication.class, args);
    }

    @RestController
    class Health {

        @GetMapping("/")
        public String health() {
            return "hello world!!";
        }
    }

}
