/**
 * GameObject - Tüm oyun nesnelerinin ortak soyut sınıfı.
 * 
 * Faz 1 Refactoring: God Class yapısı kaldırıldı.
 * Her nesne tipi artık kendi alt sınıfında tanımlanıyor.
 * Ortak davranışlar burada, özel davranışlar alt sınıflarda.
 */
public abstract class GameObject {

    protected String name;
    protected int x, y;
    protected int health;
    protected int maxHealth;
    protected int speed;
    protected int damage;
    protected boolean isAlive;

    public GameObject(String name, int x, int y, int health, int maxHealth, int speed, int damage) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.health = health;
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.damage = damage;
        this.isAlive = true;
    }

    /**
     * Her frame çağrılır — alt sınıflar kendi davranışını tanımlar.
     */
    public abstract void update();

    /**
     * Çarpışma tepkisi — alt sınıflar kendi çarpışma mantığını tanımlar.
     */
    public abstract void onCollision(GameObject other);

    /**
     * Nesnenin görsel sembolünü döndürür (render için).
     */
    public abstract String getDisplaySymbol();

    /**
     * Nesne tipini döndürür (geriye dönük uyumluluk için).
     */
    public abstract String getType();

    // --- Ortak davranışlar ---

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    public void heal(int amount) {
        this.health = Math.min(this.health + amount, this.maxHealth);
    }

    // --- Getter'lar ---
    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getSpeed() { return speed; }
    public int getDamage() { return damage; }
    public boolean isAlive() { return isAlive; }

    // --- Setter'lar ---
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setHealth(int health) { this.health = health; }
    public void setAlive(boolean alive) { this.isAlive = alive; }
}
