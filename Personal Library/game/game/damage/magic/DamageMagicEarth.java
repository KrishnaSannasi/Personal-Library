package game.damage.magic;

import game.entity.EntityCharacter;

public class DamageMagicEarth extends DamageMagic {
    public DamageMagicEarth(EntityCharacter victim , double value) {
        super(victim , 2 * value);
    }
}
