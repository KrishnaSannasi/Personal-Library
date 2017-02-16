package game.damage;

import game.entity.EntityCharacter;

public class DamageLatent extends Damage {
    private Damage damage;
    private long   latency;
    
    public DamageLatent(EntityCharacter victim , Damage damage , long latency) {
        super(victim);
        
        this.latency = latency + System.currentTimeMillis();
    }
    
    public Damage getDamage() {
        return damage;
    }
    
    public long getLatency() {
        return latency;
    }
    
    @Override
    public double getRawValue() {
        if(System.currentTimeMillis() < latency)
            return -1;
        else
            return damage.getRawValue();
    }
}
