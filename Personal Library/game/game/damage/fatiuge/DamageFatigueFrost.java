package game.damage.fatiuge;

import game.entity.EntityCharacter;

public class DamageFatigueFrost extends DamageFatigue {
    public DamageFatigueFrost(EntityCharacter victim , double value) {
        super(victim , 2 * value);
    }
}
