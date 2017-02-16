package game.damage;

import game.entity.EntityCharacter;
import game.resistances.Resistance;

public abstract class Damage {
    private EntityCharacter victim;
    private boolean         applied;
    
    public Damage(EntityCharacter victim) {
        this.victim = victim;
        applied = false;
    }
    
    public EntityCharacter getVictim() {
        return victim;
    }
    
    public boolean isApplied() {
        return applied;
    }
    
    public abstract double getRawValue();
    
    public double getValue(Resistance... resistances) {
        double raw = getRawValue();
        
        if(raw == -1)
            return 0;
        
        double mods = 0;
        Damage damage = this;
        if(this instanceof DamageLatent)
            damage = ((DamageLatent) this).getDamage();
        
        Class<? extends Damage> type = damage.getClass();
        
        for(Resistance resistance: resistances) {
            if(resistance.isValidDamageType(type))
                mods += resistance.getModifier(damage);
        }
        
        if(raw < mods)
            return 0;
        else
            return raw - mods;
    }
}
