package components.frontend;

import components.base.Abonent;
import components.mechanics.GameSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface Frontend extends Runnable, Abonent {

    void handleAuthorizationGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    void handleAuthorizationPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    void setId(String name, Integer id);

    void updateGames(List<GameSession> gameSessions);
}
