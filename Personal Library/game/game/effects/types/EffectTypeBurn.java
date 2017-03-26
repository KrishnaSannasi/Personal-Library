package game.effects.types;

import game.damage.fatiuge.DamageFatigueFire;
import game.entity.EntityCharacter;

public class EffectTypeBurn extends EffectType implements Damaging {
    private double damage;
    
    public EffectTypeBurn(double baseDuration , double damage) {
        super(baseDuration);
        
        this.damage = damage;
    }
    
    @Override
    public double getDamage() {
        return damage;
    }
    
    @Override
    public void applyEffect(EntityCharacter character , int level) {
        character.addDamage(new DamageFatigueFire(character , damage * level));
    }
}
