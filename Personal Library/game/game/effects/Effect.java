package game.effects;

import game.effects.types.EffectType;
import game.entity.EntityCharacter;

public class Effect {
    private EntityCharacter victim;
    private EffectType      effectType;
    
    private int    level;
    private double duration;
    
    public Effect(EntityCharacter victim , EffectType effectType , int level) {
        this.victim = victim;
        
        this.effectType = effectType;
        this.level = level;
        this.duration = effectType.getBaseDuration() * Math.pow(1.25 , level - 1);
    }
    
    public EntityCharacter getVictim() {
        return victim;
    }
    
    public EffectType getEffectType() {
        return effectType;
    }
    
    public int getLevel() {
        return level;
    }
    
    public double getDuration() {
        return duration;
    }
    
    public void update(double deltaT) {
        if(duration <= 0) {
            duration = 0;
        }
        else {
            duration -= deltaT;
            effectType.applyEffect(victim , level);
        }
    }
}
