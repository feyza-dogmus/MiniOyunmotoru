/**
 * DefensiveMovement - Yavaş ve savunmacı hareket stratejisi.
 * TANK düşmanlar için.
 */
public class DefensiveMovement implements MovementStrategy {

    @Override
    public void move(GameObject obj) {
        obj.setX(obj.getX() + (int)(Math.random() * 2) - 1);
        obj.setY(obj.getY() + (int)(Math.random() * 2) - 1);
    }

    @Override
    public String getStrategyName() { return "Savunmacı Hareket"; }
}
