package game.damage.magic;

import game.entity.EntityCharacter;

public class DamageMagicWater extends DamageMagic {
    public DamageMagicWater(EntityCharacter victim , double value) {
        super(victim , 2 * value);
    }
}
