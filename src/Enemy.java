/**
 * Enemy - Düşman nesnesini temsil eder.
 * 
 * Faz 3: Strategy pattern ile hareket davranışı dışarıdan enjekte ediliyor.
 * if-else zincirleri tamamen kaldırıldı — her düşman tipine ait
 * hareket mantığı MovementStrategy implementasyonlarında.
 */
public class Enemy extends GameObject {

    private String enemyType;
    private int detectionRange;
    private MovementStrategy movementStrategy; // Strategy Pattern

    public Enemy(String name, int x, int y) {
        super(name, x, y, 50, 50, 3, 15);
        this.enemyType = "BASIC";
        this.detectionRange = 8;
        this.movementStrategy = new RandomMovement(); // varsayılan strateji
    }

    @Override
    public void update() {
        if (!isAlive) return;

        // Strategy Pattern — hareket davranışı strateji nesnesine delege edildi
        movementStrategy.move(this);

        System.out.println("[Düşman] " + name + " (" + enemyType + ") pozisyon: (" + x + ", " + y + ") | Can: " + health + " | Strateji: " + movementStrategy.getStrategyName());
    }

    @Override
    public void onCollision(GameObject other) {
        if (!this.isAlive || !other.isAlive()) return;

        if (other instanceof Player) {
            Player player = (Player) other;
            player.takeDamage(this.damage);
            System.out.println("⚔ " + other.getName() + " düşmandan " + this.damage + " hasar aldı!");
            if (!other.isAlive()) {
                System.out.println("💀 " + other.getName() + " öldü! GAME OVER");
            }
        } else if (other instanceof Enemy) {
            System.out.println(this.name + " ve " + other.getName() + " birbirine çarptı ama bir şey olmadı.");
        }
    }

    /**
     * Düşman tipini ayarla ve uygun stratejiyi ata.
     */
    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;

        // Düşman tipine göre stat ayarla ve strateji ata
        switch (enemyType) {
            case "BASIC":
                this.health = 30; this.maxHealth = 30;
                this.speed = 3; this.damage = 10;
                this.movementStrategy = new RandomMovement();
                break;
            case "FAST":
                this.health = 20; this.maxHealth = 20;
                this.speed = 7; this.damage = 8;
                this.movementStrategy = new AggressiveMovement();
                break;
            case "TANK":
                this.health = 100; this.maxHealth = 100;
                this.speed = 1; this.damage = 25;
                this.movementStrategy = new DefensiveMovement();
                break;
            case "BOSS":
                this.health = 200; this.maxHealth = 200;
                this.speed = 2; this.damage = 30;
                this.detectionRange = 15;
                this.movementStrategy = new BossMovement();
                break;
        }
    }

    /**
     * Runtime'da hareket stratejisini değiştir — Strategy Pattern'in gücü.
     */
    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
        System.out.println("🔄 " + name + " strateji değiştirdi: " + strategy.getStrategyName());
    }

    public MovementStrategy getMovementStrategy() { return movementStrategy; }

    @Override
    public String getDisplaySymbol() {
        if ("BOSS".equals(enemyType)) return "B";
        if ("TANK".equals(enemyType)) return "T";
        if ("FAST".equals(enemyType)) return "F";
        return "E";
    }

    @Override
    public String getType() { return "ENEMY"; }

    public String getEnemyType() { return enemyType; }
    public int getDetectionRange() { return detectionRange; }
}
