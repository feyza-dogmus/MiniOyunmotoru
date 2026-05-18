/**
 * ArmorDecorator - Zırh yeteneği ekleyen Decorator.
 * 
 * Sarılı nesnenin aldığı hasarı azaltır.
 * Zırh belirli bir dayanıklılığa sahiptir ve zamanla kırılır.
 */
public class ArmorDecorator extends GameObjectDecorator {

    private int armorPoints;
    private final int maxArmorPoints;

    public ArmorDecorator(GameObject wrappedObject, int armorPoints) {
        super(wrappedObject);
        this.armorPoints = armorPoints;
        this.maxArmorPoints = armorPoints;
    }

    @Override
    public void update() {
        super.update();
        if (armorPoints > 0) {
            System.out.println("  🛡 [Zırh aktif: " + armorPoints + "/" + maxArmorPoints + "]");
        }
    }

    @Override
    public void takeDamage(int amount) {
        if (armorPoints > 0) {
            int absorbed = Math.min(amount, armorPoints);
            armorPoints -= absorbed;
            int remaining = amount - absorbed;
            System.out.println("  🛡 Zırh " + absorbed + " hasar emdi! Kalan zırh: " + armorPoints);

            if (remaining > 0) {
                System.out.println("  ⚠ Zırh kırıldı! " + remaining + " hasar geçti.");
                super.takeDamage(remaining);
            }
        } else {
            super.takeDamage(amount);
        }
    }

    @Override
    public String getDisplaySymbol() {
        return super.getDisplaySymbol();
    }

    public int getArmorPoints() { return armorPoints; }
}
