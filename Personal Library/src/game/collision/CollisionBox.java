package game.collision;

import java.lang.reflect.Modifier;
import java.util.HashSet;

public abstract class CollisionBox<T extends Collision> {
    public static final class CannotInteractException extends Exception {
        @SuppressWarnings("rawtypes")
        public CannotInteractException(Class<? extends CollisionBox> thisType , Class<? extends CollisionBox> boxType) {
            super(String.format("%s cannot interact with %s" , thisType , boxType));
        }
    }
    
    public static final class InvalidCollisionBox extends RuntimeException {
        public InvalidCollisionBox(Class<? extends CollisionBox<?>> boxType) {
            super(String.format("Cannot interact with the non-final type %s" , boxType));
        }
    }
    
    private HashSet<Class<? extends CollisionBox<?>>> canInteract;
    
    public CollisionBox() {
        canInteract = new HashSet<>();
    }
    
    public void addInteractable(Class<? extends CollisionBox<?>> boxType) {
        if(Modifier.isFinal(boxType.getModifiers()))
            canInteract.add(boxType);
        else
            throw new InvalidCollisionBox(boxType);
    }
    
    public void removeInteractable(Class<? extends CollisionBox<?>> boxType) {
        canInteract.remove(boxType);
    }
    
    public void checkInteractWith(CollisionBox<?> box) throws CannotInteractException {
        if(!canInteract.contains(box.getClass()))
            throw new CannotInteractException(getClass() , box.getClass());
    }
    
    public abstract boolean checkCollision(CollisionBox<?> box) throws CannotInteractException;
    
    public abstract T[] getCollisions();
}
