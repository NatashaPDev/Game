package components.servlets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/auth")
public class RootController {

    @GetMapping(value = "/")
    public String login() {
        return "auth";
    }

    @GetMapping(value = "/auth")
    public void doSomething () {
        System.out.println("Good");;
    }

}
