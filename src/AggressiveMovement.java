/**
 * AggressiveMovement - Hızlı ve agresif hareket stratejisi.
 * FAST düşmanlar için.
 */
public class AggressiveMovement implements MovementStrategy {

    @Override
    public void move(GameObject obj) {
        obj.setX(obj.getX() + (int)(Math.random() * 5) - 2);
        obj.setY(obj.getY() + (int)(Math.random() * 5) - 2);
    }

    @Override
    public String getStrategyName() { return "Agresif Hareket"; }
}
