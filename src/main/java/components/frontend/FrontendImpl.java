package components.frontend;

import components.account.AccountServiceImpl;
import components.account.MsgGetUserId;
import components.base.Address;
import components.base.UserSession;
import components.mechanics.GameMechanicsImpl;
import components.mechanics.GameSession;
import components.mechanics.MsgAddHappiness;
import components.mechanics.MsgStartGameSession;
import components.message.MessageSystem;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.PageGenerator;
import utils.TimeHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Component
public class FrontendImpl extends AbstractHandler implements Frontend {

    private static Logger log = Logger.getLogger(FrontendImpl.class.getName());
    private static AtomicInteger sessionIdCreator = new AtomicInteger();
    private static Map<Integer, UserSession> sessionMap = new HashMap<>();
    private static AtomicInteger usersCount = new AtomicInteger();

    private int handleCount;
    private Address address;
    private MessageSystem ms;

    private Map<String, Integer> nameToId = new HashMap<String, Integer>();

    @Autowired
    public FrontendImpl(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
    }

    public void run() {
        while (true) {
            ms.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public Address getAddress() {
        return address;
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {

        AtomicInteger sessionId = new AtomicInteger();

        String id = request.getParameter("id");
        String name = request.getParameter("login");
        String happiness = request.getParameter("happiness");

        if (id == null || id.isEmpty() || Integer.parseInt(id) == 0) {
            sessionId.set(sessionIdCreator.incrementAndGet());
            sessionMap.put(sessionId.get(), new UserSession(sessionId.get()));
        } else {
            sessionId.set(Integer.parseInt(id));
        }

        if (name != null) {
            sessionMap.get(sessionId.get()).setName(name);
        }
        Integer userId = nameToId.get(name);

        if (name != null && userId == null) {
            Address addressAS = ms.getAddressService().getAddress(AccountServiceImpl.class);
            ms.sendMessage(new MsgGetUserId(getAddress(), addressAS, name));
        }

        if (happiness != null) {
            Address addressGM = ms.getAddressService().getAddress(GameMechanicsImpl.class);
            ms.sendMessage(new MsgAddHappiness(getAddress(), addressGM,
                    sessionMap.get(sessionId.get()).getGameSession(), Long.parseLong(happiness), sessionId.get()));
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", sessionId.toString());
        pageVariables.put("time", sessionMap.get(sessionId.get()).getTime());
        pageVariables.put("score", sessionMap.get(sessionId.get()).getScore());
        pageVariables.put("happiness", 0);

        response.getWriter().println(PageGenerator.getPage(sessionMap.get(sessionId.get()), pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        handleCount++;
    }

    public void handleAuthorizationGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int sessionId = sessionIdCreator.incrementAndGet();
        UserSession userSession = new UserSession(sessionId);

        sessionMap.put(sessionId, userSession);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", String.valueOf(sessionId));

        String page = PageGenerator.getPage(userSession, pageVariables);

        resp.getWriter().println(page);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public void handleAuthorizationPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        String name = req.getParameter("login");

        int sessionId = Integer.parseInt(id);

        if (name != null) {
            sessionMap.get(sessionId).setName(name);
        }
        Integer userId = nameToId.get(name);

        if (name != null && userId == null) {
            Address addressAS = ms.getAddressService().getAddress(AccountServiceImpl.class);
            ms.sendMessage(new MsgGetUserId(getAddress(), addressAS, name));
        }

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", sessionId);
        pageVariables.put("time", sessionMap.get(sessionId).getTime());
        pageVariables.put("score", sessionMap.get(sessionId).getScore());
        pageVariables.put("happiness", 0);

        resp.getWriter().println(PageGenerator.getPage(sessionMap.get(sessionId), pageVariables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public void setId(String name, Integer id) {
        if (nameToId.get(name) == null) {
            usersCount.incrementAndGet();
            if (usersCount.get() == 2) {
                Integer[] sessionIds = sessionMap.keySet().toArray(new Integer[2]);
                Address addressGM = ms.getAddressService().getAddress(GameMechanicsImpl.class);
                ms.sendMessage(new MsgStartGameSession(getAddress(), addressGM, sessionIds[0], sessionIds[1]));
            }
        }
        nameToId.put(name, id);
        sessionMap.values()
                .stream()
                .filter((u) -> name.equals(u.getName()))
                .forEach((u) -> u.setUserId(id));
    }

    @Override
    public void updateGames(List<GameSession> gameSessions) {
        gameSessions.forEach((g) -> {
            sessionMap.get(g.getFirstUserId()).setTime(g.getDuration());
            sessionMap.get(g.getFirstUserId()).setGameSession(g);
            sessionMap.get(g.getSecondUserId()).setTime(g.getDuration());
            sessionMap.get(g.getSecondUserId()).setGameSession(g);

            g.getPlayers().forEach((key, value) -> sessionMap.get(key).setScore(value.getScore()));
        });
    }

}