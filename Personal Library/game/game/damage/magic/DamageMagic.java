package game.damage.magic;

import game.damage.Damage;
import game.entity.EntityCharacter;

public class DamageMagic extends Damage {
    private double value;
    
    public DamageMagic(EntityCharacter victim , double value) {
        super(victim);
        
        this.value = value;
    }
    
    @Override
    public double getRawValue() {
        return value;
    }
    
}
