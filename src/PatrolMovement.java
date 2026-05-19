/**
 * PatrolMovement - Devriye hareket stratejisi.
 * 
 * OCP GÖSTERİMİ: Bu strateji mevcut hiçbir kodu değiştirmeden eklendi.
 * Sadece yeni bir sınıf oluşturuldu ve MovementStrategy implemente edildi.
 * 
 * Belirli bir rota üzerinde ileri-geri hareket eder.
 */
public class PatrolMovement implements MovementStrategy {

    private int patrolRange;
    private int startX;
    private boolean movingRight;
    private boolean initialized;

    public PatrolMovement(int patrolRange) {
        this.patrolRange = patrolRange;
        this.movingRight = true;
        this.initialized = false;
    }

    @Override
    public void move(GameObject obj) {
        if (!initialized) {
            this.startX = obj.getX();
            this.initialized = true;
        }

        if (movingRight) {
            obj.setX(obj.getX() + 1);
            if (obj.getX() >= startX + patrolRange) {
                movingRight = false;
            }
        } else {
            obj.setX(obj.getX() - 1);
            if (obj.getX() <= startX - patrolRange) {
                movingRight = true;
            }
        }
    }

    @Override
    public String getStrategyName() { return "Devriye Hareketi"; }
}
