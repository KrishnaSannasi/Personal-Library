package game.damage.fatiuge;

import game.damage.Damage;
import game.entity.EntityCharacter;

public abstract class DamageFatigue extends Damage {
    private double value;
    
    public DamageFatigue(EntityCharacter victim , double value) {
        super(victim);
        
        this.value = value;
    }
    
    @Override
    public double getRawValue() {
        return value;
    }
}
