package components.account;

import components.base.Abonent;
import components.message.MessageSystem;

public interface AccountService extends Runnable, Abonent {
    Integer getUserId(String name);

    MessageSystem getMessageSystem();
}
