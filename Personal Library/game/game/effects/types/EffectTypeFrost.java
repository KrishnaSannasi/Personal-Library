package game.effects.types;

import game.damage.fatiuge.DamageFatigueFrost;
import game.entity.EntityCharacter;

public class EffectTypeFrost extends EffectType implements Damaging {
    private double damage;
    
    public EffectTypeFrost(double baseDuration , double damage) {
        super(baseDuration);
        
        this.damage = damage;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }
    
    @Override
    public void applyEffect(EntityCharacter character , int level) {
        character.addDamage(new DamageFatigueFrost(character , damage * level));
    }
    
}
