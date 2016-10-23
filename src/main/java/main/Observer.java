package main;

public interface Observer {
    void update(Cell cell, String string);
    void notifyAboutGameEnd(String string);
    void notifyAboutDraw();
}
