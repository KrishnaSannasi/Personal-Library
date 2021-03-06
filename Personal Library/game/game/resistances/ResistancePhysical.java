package game.resistances;

import game.damage.Damage;
import game.damage.physical.DamagePhysical;

public class ResistancePhysical extends Resistance {
    private double resist;
    
    public ResistancePhysical(double resist) {
        this.resist = resist;
        
        addValidDamageTypes(DamagePhysical.class);
    }
    
    public double getResist() {
        return resist;
    }
    
    public void setResist(double resist) {
        this.resist = resist;
    }
    
    @Override
    protected double getRawModifier(Damage damage) {
        return damage.getRawValue() * resist;
    }
}
