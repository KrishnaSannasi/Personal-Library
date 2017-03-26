package game.entity;

import java.util.ArrayList;
import java.util.Arrays;

import game.damage.Damage;
import game.effects.Effect;
import game.resistances.Resistance;

public abstract class EntityCharacter extends EntityLiving {
    private Resistance[] innateResistances;
    
    private ArrayList<Damage> damages;
    private ArrayList<Effect> effects;
    
    private Status speed;
    private Status weight;
    
    public EntityCharacter(double x , double y , double z , boolean isAffectedByGravity , double maxHealth , Resistance[] resistances) {
        super(x , y , z , isAffectedByGravity , maxHealth , maxHealth);
        
        this.innateResistances = Arrays.copyOf(resistances , resistances.length);
        this.damages = new ArrayList<>();
        this.effects = new ArrayList<>();
    }
    
    public void addDamage(Damage damage) {
        damages.add(damage);
    }
    
    public void addEffect(Effect effect) {
        effects.add(effect);
    }
    
    public double getSpeed() {
        return speed.getValue();
    }
    
    public double getWeight() {
        return weight.getValue();
    }
    
    @Override
    public void update(double deltaT) {
        speed.resetDelta();
        weight.resetDelta();
        
        speed.addToDelta(-weight.getValue() / 50);
        
        for(int i = effects.size() - 1; i >= 0; i--) {
            Effect e = effects.get(i);
            e.update(deltaT);
            
            if(e.getDuration() <= 0)
                effects.remove(i);
        }
        
        for(int i = damages.size() - 1; i >= 0; i--) {
            Damage d = damages.get(i);
            double val = d.getValue(innateResistances);
            
            if(val != -1) {
                adjustHealth(-val);
                damages.remove(i);
            }
        }
    }
}
