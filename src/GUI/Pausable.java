package GUI;

public interface Pausable {
    double rate  = 1;
    void setRate(double rate);
    void pause();
    void resume();
}
