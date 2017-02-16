package game.resistances;

import game.damage.Damage;
import game.damage.DamageMagic;

public class ResistanceMagic extends Resistance {
    private double blk;
    private double resist;
    
    public ResistanceMagic(double block , double resist) {
        this.blk = block;
        this.resist = resist;
        
        addValidDamageType(DamageMagic.class);
    }
    
    public double getResist() {
        return resist;
    }
    
    public double getBlock() {
        return blk;
    }
    
    public void setBlock(double block) {
        blk = block;
    }
    
    public void setResist(double resist) {
        this.resist = resist;
    }
    
    @Override
    protected double getRawModifier(Damage damage) {
        return blk + damage.getRawValue() * resist;
    }
}
