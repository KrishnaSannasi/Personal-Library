package game.damage.magic;

import game.entity.EntityCharacter;

public class DamageMagicElectricity extends DamageMagic {
    public DamageMagicElectricity(EntityCharacter victim , double value) {
        super(victim , 2 * value);
    }
}
