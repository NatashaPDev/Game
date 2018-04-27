package components.mechanics;

public class Player {

    long score = 0L;

    long happiness = 0L;

    public void addHappiness(long happiness) {
        this.happiness += happiness;
        calculateScore();
    }

    private void calculateScore() {
        score += happiness;
    }

    public Long getScore() {
        return score;
    }



}
