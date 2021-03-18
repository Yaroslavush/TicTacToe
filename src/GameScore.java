public class GameScore {
    private int firstScore = 0;
    private int secondScore = 0;

    public void enlargeFirstScore() {
        firstScore++;
    }

    public void enlargeSecondScore() {
        secondScore++;
    }

    public int getFirstScore() { return firstScore; }

    public int getSecondScore() {
        return secondScore;
    }

    public void nullifyScore() {

        firstScore = 0;
        secondScore = 0;
    }

}
