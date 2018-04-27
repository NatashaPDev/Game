package components.account;

import components.base.Address;
import components.message.MessageSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.TimeHelper;

import java.util.HashMap;
import java.util.Map;

@Component
public class AccountServiceImpl implements AccountService {

    private Address address;
    private MessageSystem ms;

    private Map<String, Integer> fakeAccounter = new HashMap<String, Integer>();

    @Autowired
    public AccountServiceImpl(MessageSystem ms, Address address){
        this.ms = ms;
        this.address = address;
        ms.addService(this);
        this.fakeAccounter.put("Tully", 1);
        this.fakeAccounter.put("Sully", 2);
    }

    public void run(){
        while(true){
            ms.execForAbonent(this);
            TimeHelper.sleep(10);
        }
    }

    public Integer getUserId(String name){
        TimeHelper.sleep(5000);
        return fakeAccounter.get(name);
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem(){
        return ms;
    }
}