package game.damage;

import game.entity.EntityCharacter;

public class DamageMagic extends Damage {
    double value;
    
    public DamageMagic(EntityCharacter victim , double value) {
        super(victim);
        
        this.value = value;
    }
    
    @Override
    public double getRawValue() {
        return value;
    }
    
}
