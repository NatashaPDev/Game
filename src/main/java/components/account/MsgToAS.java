package components.account;

import components.base.Abonent;
import components.base.Address;
import components.message.Msg;

public abstract class MsgToAS extends Msg {

    public MsgToAS(Address from, Address to) {
        super(from, to);
    }

    protected void exec(Abonent abonent) {
        if(abonent instanceof AccountService){
            exec((AccountService) abonent);
        }
    }

    abstract void exec(AccountService accountService);
}