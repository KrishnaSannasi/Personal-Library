package game.resistances;

import java.util.HashSet;

import game.damage.Damage;

public abstract class Resistance {
    private HashSet<Class<? extends Damage>> validDamageTypes;
    
    public Resistance() {
        validDamageTypes = new HashSet<>();
    }
    
    @SafeVarargs
    protected final void addValidDamageTypes(Class<? extends Damage>... damageTypes) {
        for(Class<? extends Damage> d: damageTypes)
            validDamageTypes.add(d);
    }
    
    public boolean isValidDamageType(Class<? extends Damage> damageType) {
        return validDamageTypes.contains(damageType);
    }
    
    public double getModifier(Damage damage) {
        if(isValidDamageType(damage.getClass()))
            return getRawModifier(damage);
        else
            return 0;
    }
    
    /**
     * pos vals remove from damage and neg valus add to damage
     */
    protected abstract double getRawModifier(Damage damage);
}
