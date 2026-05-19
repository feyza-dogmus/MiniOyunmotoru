/**
 * RandomMovement - Rastgele hareket stratejisi.
 * BASIC düşmanlar için varsayılan strateji.
 */
public class RandomMovement implements MovementStrategy {

    @Override
    public void move(GameObject obj) {
        obj.setX(obj.getX() + (int)(Math.random() * 3) - 1);
        obj.setY(obj.getY() + (int)(Math.random() * 3) - 1);
    }

    @Override
    public String getStrategyName() { return "Rastgele Hareket"; }
}
