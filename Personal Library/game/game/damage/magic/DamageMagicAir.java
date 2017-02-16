package game.damage.magic;

import game.entity.EntityCharacter;

public class DamageMagicAir extends DamageMagic {
    public DamageMagicAir(EntityCharacter victim , double value) {
        super(victim , 2 * value);
    }
}
