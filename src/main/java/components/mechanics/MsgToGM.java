package components.mechanics;

import components.base.Abonent;
import components.base.Address;
import components.message.Msg;

public abstract class MsgToGM extends Msg {

    public MsgToGM(Address from, Address to) {
        super(from, to);
    }

    protected void exec(Abonent abonent) {
        if(abonent instanceof GameMechanics){
            exec((GameMechanics) abonent);
        }
    }

    abstract void exec(GameMechanics gameMechanics);
}