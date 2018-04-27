package components.servlets;

import components.frontend.Frontend;
import components.frontend.FrontendImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = AuthorizationServlet.REST_URL)
public class AuthorizationServlet extends HttpServlet {

    static final String REST_URL = "/auth";

    @Autowired
    private Frontend frontend;

    @Override
    @GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        frontend.handleAuthorizationGet(req, resp);
    }

    @Override
    @PostMapping
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        frontend.handleAuthorizationPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        frontend = (FrontendImpl) WebApplicationContextUtils.getWebApplicationContext(getServletContext())
                .getBean("frontendImpl");
    }
}
