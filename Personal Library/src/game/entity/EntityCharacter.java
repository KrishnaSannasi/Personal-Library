package game.entity;

import java.util.Arrays;

import game.damage.Damage;
import game.resistances.Resistance;

public abstract class EntityCharacter extends EntityLiving {
    private Resistance[] resistances;
    
    public EntityCharacter(double x , double y , double z , boolean isAffectedByGravity , double maxHealth , Resistance[] resistances) {
        super(x , y , z , isAffectedByGravity , maxHealth , maxHealth);
        
        this.resistances = Arrays.copyOf(resistances , resistances.length);
    }
    
    public void doDamage(Damage damage) {
        damage.getValue(resistances);
    }
    
    public static void main(String[] args) {
        
    }
}
