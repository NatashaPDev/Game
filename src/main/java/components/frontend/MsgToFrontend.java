package components.frontend;

import components.base.Abonent;
import components.base.Address;
import components.message.Msg;

public abstract class MsgToFrontend extends Msg {

    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof Frontend){
            exec((Frontend) abonent);
        }
    }

    abstract void exec(Frontend frontend);
}