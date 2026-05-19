/**
 * GameObjectDecorator - Decorator Pattern temel sınıfı.
 * 
 * GameObject'lere dinamik olarak yeni özellikler/yetenekler ekler.
 * Mevcut sınıfları değiştirmeden (OCP) yeni davranışlar kazandırır.
 * 
 * Örnek kullanım: Bir oyuncuya zırh, hız artışı veya zehir efekti
 * eklemek için mevcut Player sınıfını değiştirmek yerine decorator sarılır.
 */
public abstract class GameObjectDecorator extends GameObject {

    protected GameObject wrappedObject;

    public GameObjectDecorator(GameObject wrappedObject) {
        super(wrappedObject.getName(),
              wrappedObject.getX(), wrappedObject.getY(),
              wrappedObject.getHealth(), wrappedObject.getMaxHealth(),
              wrappedObject.getSpeed(), wrappedObject.getDamage());
        this.wrappedObject = wrappedObject;
    }

    // --- Tüm çağrıları sarılı nesneye delege et ---

    @Override
    public void update() {
        wrappedObject.update();
    }

    @Override
    public void onCollision(GameObject other) {
        wrappedObject.onCollision(other);
    }

    @Override
    public String getDisplaySymbol() {
        return wrappedObject.getDisplaySymbol();
    }

    @Override
    public String getType() {
        return wrappedObject.getType();
    }

    @Override
    public String getName() { return wrappedObject.getName(); }

    @Override
    public int getX() { return wrappedObject.getX(); }

    @Override
    public int getY() { return wrappedObject.getY(); }

    @Override
    public int getHealth() { return wrappedObject.getHealth(); }

    @Override
    public int getMaxHealth() { return wrappedObject.getMaxHealth(); }

    @Override
    public int getSpeed() { return wrappedObject.getSpeed(); }

    @Override
    public int getDamage() { return wrappedObject.getDamage(); }

    @Override
    public boolean isAlive() { return wrappedObject.isAlive(); }

    @Override
    public void setX(int x) { wrappedObject.setX(x); }

    @Override
    public void setY(int y) { wrappedObject.setY(y); }

    @Override
    public void setHealth(int health) { wrappedObject.setHealth(health); }

    @Override
    public void setAlive(boolean alive) { wrappedObject.setAlive(alive); }

    @Override
    public void takeDamage(int amount) {
        wrappedObject.takeDamage(amount);
    }

    @Override
    public void heal(int amount) {
        wrappedObject.heal(amount);
    }

    /**
     * Sarılı asıl nesneyi döndürür (decorator zincirini çözmek için).
     */
    public GameObject getWrappedObject() {
        return wrappedObject;
    }
}
