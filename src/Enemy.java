/**
 * Enemy - Düşman nesnesini temsil eder.
 * Düşmana özel alanlar (enemyType, detectionRange) ve AI davranışları burada.
 */
public class Enemy extends GameObject {

    private String enemyType; // "BASIC", "FAST", "TANK", "BOSS"
    private int detectionRange;

    public Enemy(String name, int x, int y) {
        super(name, x, y, 50, 50, 3, 15);
        this.enemyType = "BASIC";
        this.detectionRange = 8;
    }

    @Override
    public void update() {
        if (!isAlive) return;

        // Düşman tipine göre farklı AI hareketi
        if (enemyType.equals("BASIC")) {
            x += (int)(Math.random() * 3) - 1;
            y += (int)(Math.random() * 3) - 1;
        } else if (enemyType.equals("FAST")) {
            x += (int)(Math.random() * 5) - 2;
            y += (int)(Math.random() * 5) - 2;
        } else if (enemyType.equals("TANK")) {
            x += (int)(Math.random() * 2) - 1;
            y += (int)(Math.random() * 2) - 1;
        } else if (enemyType.equals("BOSS")) {
            x += (int)(Math.random() * 3) - 1;
            y += (int)(Math.random() * 3) - 1;
            if (health < maxHealth) {
                health += 1; // Boss iyileşir
            }
        }

        System.out.println("[Düşman] " + name + " (" + enemyType + ") pozisyon: (" + x + ", " + y + ") | Can: " + health);
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
     * Düşman tipini ayarla ve stat'ları güncelle
     */
    public void setEnemyType(String enemyType) {
        this.enemyType = enemyType;

        if (enemyType.equals("BASIC")) {
            this.health = 30; this.maxHealth = 30;
            this.speed = 3; this.damage = 10;
        } else if (enemyType.equals("FAST")) {
            this.health = 20; this.maxHealth = 20;
            this.speed = 7; this.damage = 8;
        } else if (enemyType.equals("TANK")) {
            this.health = 100; this.maxHealth = 100;
            this.speed = 1; this.damage = 25;
        } else if (enemyType.equals("BOSS")) {
            this.health = 200; this.maxHealth = 200;
            this.speed = 2; this.damage = 30;
            this.detectionRange = 15;
        }
    }

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
