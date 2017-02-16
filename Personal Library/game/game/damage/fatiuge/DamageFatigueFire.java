package game.damage.fatiuge;

import game.entity.EntityCharacter;

public class DamageFatigueFire extends DamageFatigue {
    public DamageFatigueFire(EntityCharacter victim , double value) {
        super(victim , 2 * value);
    }
}
