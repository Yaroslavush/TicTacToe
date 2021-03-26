public class GameScore {

    private int firstScore = 0;
    private int secondScore = 0;
    private int victoryScore;

    GameScore(int victoryScore) {
        this.victoryScore = victoryScore;
    }

    public void enlargeFirstScore() {
        firstScore++;
    }

    public void enlargeSecondScore() {
        secondScore++;
    }

    public int getFirstScore() {
        return firstScore;
    }

    public int getSecondScore() {
        return secondScore;
    }

    public void nullifyScore() {
        firstScore = 0;
        secondScore = 0;
    }

    public boolean checkVictory() {
        if (firstScore == victoryScore || secondScore == victoryScore) {
            return true;
        }
        return false;
    }

    public int getVictoryScore() {
        return victoryScore;
    }

}
