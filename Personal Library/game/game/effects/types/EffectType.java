package game.effects.types;

import game.entity.EntityCharacter;

public abstract class EffectType {
    private double baseDuration;

    public EffectType(double baseDuration) {
        this.baseDuration = baseDuration;
    }
    
    public double getBaseDuration() {
        return baseDuration;
    }
    
    public abstract void applyEffect(EntityCharacter character , int level);
}
