package components.message;

import components.base.Abonent;
import components.base.Address;
import components.base.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class MessageSystem {
    private Map<Address, ConcurrentLinkedQueue<Msg>> messages = new HashMap<Address, ConcurrentLinkedQueue<Msg>>();

    private AddressService addressService;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void addService(Abonent abonent){
        addressService.setAbonentAddress(abonent);
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<
                Msg>());
    }

    public void sendMessage(Msg message){
        Queue<Msg> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) {
        Queue<Msg> messageQueue = messages.get(abonent.getAddress());
        if(messageQueue == null){
            return;
        }
        while(!messageQueue.isEmpty()){
            Msg message = messageQueue.poll();
            message.exec(abonent);
        }
    }

    public AddressService getAddressService(){
        return addressService;
    }
}