/**
 * Obstacle - Engel nesnesini temsil eder.
 * Yıkılabilir veya yıkılmaz olabilir.
 */
public class Obstacle extends GameObject {

    private boolean destructible;
    private int durability;

    public Obstacle(String name, int x, int y, boolean destructible, int durability) {
        super(name, x, y, durability * 10, durability * 10, 0, 0);
        this.destructible = destructible;
        this.durability = durability;
    }

    @Override
    public void update() {
        if (!isAlive) return;

        if (destructible) {
            System.out.println("[Engel] " + name + " pozisyon: (" + x + ", " + y + ") | Dayanıklılık: " + durability);
        } else {
            System.out.println("[Engel] " + name + " pozisyon: (" + x + ", " + y + ") | Yıkılmaz");
        }
    }

    @Override
    public void onCollision(GameObject other) {
        // Engeller çarpışma başlatmaz
    }

    /**
     * Engele vurulduğunda
     */
    public void hitBy(GameObject attacker) {
        if (destructible) {
            durability--;
            System.out.println("🧱 " + name + " hasar aldı! Dayanıklılık: " + durability);
            if (durability <= 0) {
                this.isAlive = false;
                System.out.println("💥 " + name + " yıkıldı!");
                if (attacker instanceof Player) {
                    ((Player) attacker).addScore(5);
                }
            }
        } else {
            System.out.println("🚫 " + name + " yıkılmaz engel! Geçilemez.");
        }
    }

    @Override
    public String getDisplaySymbol() { return "#"; }

    @Override
    public String getType() { return "OBSTACLE"; }

    public boolean isDestructible() { return destructible; }
    public int getDurability() { return durability; }
}
