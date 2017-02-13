package game.collision;

public abstract class Collision {
    private CollisionBox<?> collisionBox;
    
    public Collision(CollisionBox<?> box) {
        collisionBox = box;
    }
    
    public CollisionBox<?> getCollisionBox() {
        return collisionBox;
    }
}
