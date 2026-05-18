/**
 * DamageBoostDecorator - Hasar artışı yeteneği ekleyen Decorator.
 * 
 * Sarılı nesnenin verdiği hasarı artırır.
 * Kalıcı bir güçlendirme — süresi dolmaz.
 */
public class DamageBoostDecorator extends GameObjectDecorator {

    private int bonusDamage;

    public DamageBoostDecorator(GameObject wrappedObject, int bonusDamage) {
        super(wrappedObject);
        this.bonusDamage = bonusDamage;
    }

    @Override
    public int getDamage() {
        return super.getDamage() + bonusDamage;
    }

    @Override
    public void update() {
        super.update();
        System.out.println("  🗡 [Hasar artışı aktif: +" + bonusDamage + "]");
    }

    public int getBonusDamage() { return bonusDamage; }
}
