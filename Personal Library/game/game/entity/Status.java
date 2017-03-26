package game.entity;

public class Status {
    private double value;
    private double delta;
    
    public Status(double value) {
        this.value = value;
    }
    
    public double getValue() {
        if(value + delta < 0)
            return 0;
        else
            return value + delta;
    }
    
    public void resetDelta() {
        delta = 0;
    }
    
    public void addToDelta(double add) {
        delta += add;
    }
}
