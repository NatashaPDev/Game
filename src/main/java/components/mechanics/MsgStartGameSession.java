package components.mechanics;

import components.base.Address;

public class MsgStartGameSession extends MsgToGM {

    private String name;
    private Integer firstId;
    private Integer secondId;

    public MsgStartGameSession(Address from, Address to, Integer firstId, Integer secondId) {
        super(from, to);
        this.name = name;
        this.firstId = firstId;
        this.secondId = secondId;
    }

    @Override
    void exec(GameMechanics gameMechanics) {
        gameMechanics.createGameSession(firstId, secondId);
    }

}