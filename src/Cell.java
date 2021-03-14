public class Cell {
    private boolean isClosed = true;
    public void open(){
        isClosed = false;
    }
    public boolean isClosed(){
        return isClosed;
    }

}
