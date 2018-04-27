package components.message;

import components.base.Abonent;
import components.base.Address;

public abstract class Msg {
    private Address from;
    private Address to;

    public Msg(Address from, Address to){
        this.from = from;
        this.to = to;
    }

    protected Address getFrom(){
        return from;
    }

    protected Address getTo(){
        return to;
    }

    protected abstract void exec(Abonent abonent);
}