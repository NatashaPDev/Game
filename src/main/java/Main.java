
import components.account.AccountService;
import components.frontend.Frontend;
import components.frontend.FrontendImpl;
import components.mechanics.GameMechanics;
import org.eclipse.jetty.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        AccountService accountService = (AccountService) context.getBean("accountServiceImpl");
        GameMechanics gameMechanics = (GameMechanics) context.getBean("gameMechanicsImpl");
        Frontend frontend = (Frontend) context.getBean("frontendImpl");

        (new Thread(frontend)).start();
        (new Thread(accountService)).start();
        (new Thread(gameMechanics)).start();

        Server server = new Server(8080);
        server.setHandler((FrontendImpl) frontend);

        server.start();
        server.join();
    }
}