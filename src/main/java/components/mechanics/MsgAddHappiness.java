package components.mechanics;

import components.base.Address;

public class MsgAddHappiness extends MsgToGM {

    private String name;
    private GameSession gameSession;
    private long happiness;
    private int sessionId;

    public MsgAddHappiness(Address from, Address to, GameSession gameSession, long happiness, int sessionId) {
        super(from, to);
        this.name = name;
        this.gameSession = gameSession;
        this.happiness = happiness;
        this.sessionId = sessionId;
    }

    @Override
    void exec(GameMechanics gameMechanics) {
        Player player = gameSession.getPlayers().get(sessionId);
        player.addHappiness(happiness);
    }
}