package Main.Creatures;

import gameEngine.ADT.TraitMap;
import gameEngine.GameObject;
import gameEngine.graphics.Mesh;

public interface Creature {
    public abstract GameObject getGameObject();
    public abstract boolean isDead();
    public abstract void update();
    public abstract float getNutritionalValue();
    public abstract void setNutritionalValue(float nutritionalValue);
    public abstract void setDeath(boolean death);
    public abstract boolean isPlant();
    public abstract boolean isPrey();
}
