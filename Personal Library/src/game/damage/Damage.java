package game.damage;

import game.entity.EntityCharacter;
import game.resistances.Resistance;

public abstract class Damage {
    private EntityCharacter victim;
    
    public Damage(EntityCharacter victim) {
        this.victim = victim;
    }
    
    public EntityCharacter getVictim() {
        return victim;
    }
    
    public abstract double getRawValue();
    
    public double getValue(Resistance... resistances) {
        double mods = 0;
        for(Resistance resistance: resistances) {
            if(resistance.isValidDamageType(getClass()))
                mods += resistance.getModifier(this);
        }
        
        return getRawValue() - mods;
    }
}
