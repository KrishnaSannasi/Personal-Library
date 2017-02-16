package game.entity;

public abstract class EntityLiving extends Entity {
    private double maxHealth;
    private double health;
    
    public EntityLiving(double x , double y , double z , boolean isAffectedByGravity , double maxHealth , double health) {
        super(x , y , z , isAffectedByGravity);
        
        this.maxHealth = maxHealth;
        this.health = health;
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public double getMaxHealth() {
        return maxHealth;
    }
    
    public double getHealth() {
        return health;
    }
    
    public void adjustHealth(double delta_health) {
        health += delta_health;
        if(health < 0)
            health = 0;
        if(health > maxHealth)
            health = maxHealth;
    }
}
