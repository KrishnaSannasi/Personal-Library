package game.entity;

import game.collision.CollisionBox;

public abstract class Entity {
    private CollisionBox<?> collisionBox;
    
    private double          x , y , z;
    private boolean         isAffectedByGravity;
    
    public Entity(double x , double y , double z , boolean isAffectedByGravity) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.isAffectedByGravity = isAffectedByGravity;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getZ() {
        return z;
    }
    
    public boolean isAffectedByGravity() {
        return isAffectedByGravity;
    }
    
    public CollisionBox<?> getCollisionBox() {
        return collisionBox;
    }
    
    public abstract void update();
    
    public abstract void collide(Entity e);
}
