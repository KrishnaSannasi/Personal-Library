package game.resistances;

import game.damage.Damage;

public class ResistanceFatigue extends Resistance {
    private double blk;
    private double resist;
    
    public ResistanceFatigue(double block , double resist) {
        this.blk = block;
        this.resist = resist;
    }
    
    public double getBlock() {
        return blk;
    }
    
    public double getResist() {
        return resist;
    }
    
    public void setBlock(double block) {
        this.blk = block;
    }
    
    public void setResist(double resist) {
        this.resist = resist;
    }
    
    @Override
    protected double getRawModifier(Damage damage) {
        return damage.getRawValue() * resist + blk;
    }
    
}
