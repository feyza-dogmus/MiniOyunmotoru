/**
 * SpeedBoostDecorator - Hız artışı yeteneği ekleyen Decorator.
 * 
 * Sarılı nesnenin hızını artırır.
 * Belirli tur sonra etki sona erer.
 */
public class SpeedBoostDecorator extends GameObjectDecorator {

    private int bonusSpeed;
    private int remainingTurns;

    public SpeedBoostDecorator(GameObject wrappedObject, int bonusSpeed, int durationTurns) {
        super(wrappedObject);
        this.bonusSpeed = bonusSpeed;
        this.remainingTurns = durationTurns;
    }

    @Override
    public int getSpeed() {
        if (remainingTurns > 0) {
            return super.getSpeed() + bonusSpeed;
        }
        return super.getSpeed();
    }

    @Override
    public void update() {
        super.update();
        if (remainingTurns > 0) {
            remainingTurns--;
            System.out.println("  ⚡ [Hız artışı aktif: +" + bonusSpeed + " | Kalan tur: " + remainingTurns + "]");
        }
    }

    public int getRemainingTurns() { return remainingTurns; }
    public boolean isExpired() { return remainingTurns <= 0; }
}
