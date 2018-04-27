package components.mechanics;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GameSession {

    Integer firstUserId;
    Integer secondUserId;
    Long startTime;
    Long duration;
    private Map<Integer, Player> players = new HashMap<>();

    public GameSession(int firstId, int secondId) {
        this.firstUserId = firstId;
        this.secondUserId = secondId;
        Date date = new Date();
        startTime = date.getTime();

        players.put(firstId, new Player());
        players.put(secondId, new Player());
    }

    public Integer getFirstUserId() {
        return firstUserId;
    }

    public Integer getSecondUserId() {
        return secondUserId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
    public Map<Integer, Player> getPlayers() {
        return players;
    }
}
