package components.frontend;

import components.base.Address;
import components.mechanics.GameSession;

import java.util.List;

public class MsgGetGameSession extends MsgToFrontend {

    private String name;
    private List<GameSession> gameSessions;

    public MsgGetGameSession(Address from, Address to, List<GameSession> gameSessions) {
        super(from, to);
        this.name = name;
        this.gameSessions = gameSessions;
    }

    @Override
    void exec(Frontend frontend) {
        frontend.updateGames(gameSessions);
    }

}