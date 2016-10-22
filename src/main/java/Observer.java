
public interface Observer {
    public abstract void update(Cell cell, String string);
    public abstract void notifyAboutGameEnd(String string);
}
