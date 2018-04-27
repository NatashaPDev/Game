package components.mechanics;

import components.base.Abonent;

public interface GameMechanics extends Runnable, Abonent {
    void createGameSession(int firstId, int secondId);
}
