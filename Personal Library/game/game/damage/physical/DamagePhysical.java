package game.damage.physical;

import game.damage.Damage;
import game.entity.EntityCharacter;

public class DamagePhysical extends Damage {
    private double value;
    
    public DamagePhysical(EntityCharacter victim , double value) {
        super(victim);
        
        this.value = value;
    }
    
    @Override
    public double getRawValue() {
        return value;
    }
    
}
