package components.mechanics;

import components.base.Address;
import components.frontend.FrontendImpl;
import components.frontend.MsgGetGameSession;
import components.message.MessageSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.TimeHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GameMechanicsImpl implements GameMechanics {

    private Address address;
    private MessageSystem ms;
    private List<GameSession> gameSessions = new ArrayList<>();

    @Autowired
    public GameMechanicsImpl(Address address, MessageSystem ms) {
        this.address = address;
        this.ms = ms;
        ms.addService(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void run() {
        while(true){

            ms.execForAbonent(this);
            TimeHelper.sleep(100);

            Date date = new Date();
            long currentTime = date.getTime();
            gameSessions.forEach((g) -> g.setDuration(currentTime - g.getStartTime()));

            if (gameSessions.size() > 0) {
                Address addressFrontend = ms.getAddressService().getAddress(FrontendImpl.class);
                ms.sendMessage(new MsgGetGameSession(getAddress(), addressFrontend, gameSessions));
            }
        }
    }

    @Override
    public void createGameSession(int firstId, int secondId) {
        gameSessions.add(new GameSession(firstId, secondId));
    }
}
