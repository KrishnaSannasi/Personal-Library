package game.resistances;

import game.damage.Damage;
import game.damage.DamagePhysical;

public class ResistancePhysical extends Resistance {
    private double blk;
    
    public ResistancePhysical(double block) {
        this.blk = block;
        
        addValidDamageType(DamagePhysical.class);
    }
    
    public double getBlock() {
        return blk;
    }
    
    public void setBlock(double block) {
        blk = block;
    }
    
    @Override
    protected double getRawModifier(Damage damage) {
        return blk;
    }
}
