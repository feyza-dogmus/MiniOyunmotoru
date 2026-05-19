/**
 * BossMovement - Boss düşmanlar için özel hareket stratejisi.
 * Hareket eder ve aynı zamanda iyileşir.
 */
public class BossMovement implements MovementStrategy {

    @Override
    public void move(GameObject obj) {
        obj.setX(obj.getX() + (int)(Math.random() * 3) - 1);
        obj.setY(obj.getY() + (int)(Math.random() * 3) - 1);

        // Boss iyileşme yeteneği
        if (obj.getHealth() < obj.getMaxHealth()) {
            obj.heal(1);
        }
    }

    @Override
    public String getStrategyName() { return "Boss Hareketi"; }
}
