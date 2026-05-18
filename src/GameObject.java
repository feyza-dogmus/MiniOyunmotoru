/**
 * GameObject - Tüm oyun nesnelerini temsil eden tek sınıf.
 * Oyuncu, düşman, toplanabilir nesne ve engel aynı sınıfta yönetiliyor.
 * Davranış farkları if-else blokları ile ayrılıyor.
 */
public class GameObject {

    // Nesne tipleri string olarak tutuluyor
    private String type; // "PLAYER", "ENEMY", "COLLECTIBLE", "OBSTACLE"
    private String name;
    private int x, y;
    private int health;
    private int maxHealth;
    private int speed;
    private int damage;
    private int score;
    private boolean isAlive;

    // Sadece toplanabilir nesneler için
    private String itemEffect; // "HEALTH", "SPEED", "DAMAGE"
    private int effectAmount;

    // Sadece düşmanlar için
    private String enemyType; // "BASIC", "FAST", "TANK", "BOSS"
    private int detectionRange;

    // Sadece engeller için
    private boolean isDestructible;
    private int durability;

    public GameObject(String type, String name, int x, int y) {
        this.type = type;
        this.name = name;
        this.x = x;
        this.y = y;
        this.isAlive = true;
        this.score = 0;

        // Tip bazlı başlangıç değerleri — if-else zinciri
        if (type.equals("PLAYER")) {
            this.health = 100;
            this.maxHealth = 100;
            this.speed = 5;
            this.damage = 10;
            this.detectionRange = 0;
            this.isDestructible = false;
        } else if (type.equals("ENEMY")) {
            this.health = 50;
            this.maxHealth = 50;
            this.speed = 3;
            this.damage = 15;
            this.detectionRange = 8;
            this.isDestructible = false;
        } else if (type.equals("COLLECTIBLE")) {
            this.health = 1;
            this.maxHealth = 1;
            this.speed = 0;
            this.damage = 0;
            this.itemEffect = "HEALTH";
            this.effectAmount = 20;
            this.isDestructible = false;
        } else if (type.equals("OBSTACLE")) {
            this.health = 30;
            this.maxHealth = 30;
            this.speed = 0;
            this.damage = 0;
            this.isDestructible = true;
            this.durability = 3;
        }
    }

    /**
     * Her frame çağrılır. Nesne tipine göre farklı güncelleme mantığı.
     */
    public void update() {
        if (!isAlive) return;

        if (type.equals("PLAYER")) {
            // Oyuncu hareketi — basit simülasyon
            System.out.println(name + " pozisyon: (" + x + ", " + y + ") | Can: " + health + "/" + maxHealth + " | Skor: " + score);
        } else if (type.equals("ENEMY")) {
            // Düşman AI — tipine göre farklı davranış
            if (enemyType != null && enemyType.equals("BASIC")) {
                // Rastgele hareket
                x += (int)(Math.random() * 3) - 1;
                y += (int)(Math.random() * 3) - 1;
            } else if (enemyType != null && enemyType.equals("FAST")) {
                // Hızlı hareket
                x += (int)(Math.random() * 5) - 2;
                y += (int)(Math.random() * 5) - 2;
            } else if (enemyType != null && enemyType.equals("TANK")) {
                // Yavaş ama güçlü
                x += (int)(Math.random() * 2) - 1;
                y += (int)(Math.random() * 2) - 1;
            } else if (enemyType != null && enemyType.equals("BOSS")) {
                // Boss özel hareket
                x += (int)(Math.random() * 3) - 1;
                y += (int)(Math.random() * 3) - 1;
                // Boss ayrıca iyileşir
                if (health < maxHealth) {
                    health += 1;
                }
            }
            System.out.println("[Düşman] " + name + " (" + enemyType + ") pozisyon: (" + x + ", " + y + ") | Can: " + health);
        } else if (type.equals("COLLECTIBLE")) {
            // Toplanabilir nesneler — animasyon simülasyonu
            System.out.println("[Item] " + name + " pozisyon: (" + x + ", " + y + ") | Etki: " + itemEffect + " +" + effectAmount);
        } else if (type.equals("OBSTACLE")) {
            // Engeller — statik
            if (isDestructible) {
                System.out.println("[Engel] " + name + " pozisyon: (" + x + ", " + y + ") | Dayanıklılık: " + durability);
            } else {
                System.out.println("[Engel] " + name + " pozisyon: (" + x + ", " + y + ") | Yıkılmaz");
            }
        }
    }

    /**
     * Çarpışma kontrolü — tip bazlı if-else zinciri
     */
    public void onCollision(GameObject other) {
        if (!this.isAlive || !other.isAlive) return;

        if (this.type.equals("PLAYER")) {
            if (other.type.equals("ENEMY")) {
                // Oyuncu düşmana çarptı — hasar al
                this.health -= other.damage;
                System.out.println("⚔ " + this.name + " düşmandan " + other.damage + " hasar aldı! Kalan can: " + this.health);
                if (this.health <= 0) {
                    this.isAlive = false;
                    System.out.println("💀 " + this.name + " öldü! GAME OVER");
                }
            } else if (other.type.equals("COLLECTIBLE")) {
                // Oyuncu item topladı
                if (other.itemEffect != null && other.itemEffect.equals("HEALTH")) {
                    this.health = Math.min(this.health + other.effectAmount, this.maxHealth);
                    System.out.println("💚 " + this.name + " can yeniledi! Can: " + this.health);
                } else if (other.itemEffect != null && other.itemEffect.equals("SPEED")) {
                    this.speed += other.effectAmount;
                    System.out.println("⚡ " + this.name + " hızlandı! Hız: " + this.speed);
                } else if (other.itemEffect != null && other.itemEffect.equals("DAMAGE")) {
                    this.damage += other.effectAmount;
                    System.out.println("🗡 " + this.name + " güçlendi! Hasar: " + this.damage);
                }
                other.isAlive = false; // Item toplandı, yok et
                this.score += 10;
            } else if (other.type.equals("OBSTACLE")) {
                // Oyuncu engele çarptı
                if (other.isDestructible) {
                    other.durability--;
                    System.out.println("🧱 " + other.name + " hasar aldı! Dayanıklılık: " + other.durability);
                    if (other.durability <= 0) {
                        other.isAlive = false;
                        System.out.println("💥 " + other.name + " yıkıldı!");
                        this.score += 5;
                    }
                } else {
                    System.out.println("🚫 " + other.name + " yıkılmaz engel! Geçilemez.");
                }
            }
        } else if (this.type.equals("ENEMY")) {
            if (other.type.equals("PLAYER")) {
                // Düşman oyuncuya saldırdı
                other.health -= this.damage;
                System.out.println("⚔ " + other.name + " düşmandan " + this.damage + " hasar aldı!");
                if (other.health <= 0) {
                    other.isAlive = false;
                    System.out.println("💀 " + other.name + " öldü! GAME OVER");
                }
            } else if (other.type.equals("ENEMY")) {
                // Düşmanlar birbirine çarpmaz — şimdilik
                System.out.println(this.name + " ve " + other.name + " birbirine çarptı ama bir şey olmadı.");
            }
        }
    }

    /**
     * Oyuncu hareketi — sadece PLAYER tipi için
     */
    public void move(String direction) {
        if (!type.equals("PLAYER")) {
            System.out.println("Sadece oyuncu hareket edebilir!");
            return;
        }

        if (direction.equals("UP")) {
            y -= speed;
        } else if (direction.equals("DOWN")) {
            y += speed;
        } else if (direction.equals("LEFT")) {
            x -= speed;
        } else if (direction.equals("RIGHT")) {
            x += speed;
        }
    }

    /**
     * Düşman tipini ayarla — sadece ENEMY tipi için
     */
    public void setEnemyType(String enemyType) {
        if (!type.equals("ENEMY")) {
            System.out.println("Sadece düşmanlara tip atanabilir!");
            return;
        }
        this.enemyType = enemyType;

        // Düşman tipine göre stat ayarla — yine if-else
        if (enemyType.equals("BASIC")) {
            this.health = 30;
            this.maxHealth = 30;
            this.speed = 3;
            this.damage = 10;
        } else if (enemyType.equals("FAST")) {
            this.health = 20;
            this.maxHealth = 20;
            this.speed = 7;
            this.damage = 8;
        } else if (enemyType.equals("TANK")) {
            this.health = 100;
            this.maxHealth = 100;
            this.speed = 1;
            this.damage = 25;
        } else if (enemyType.equals("BOSS")) {
            this.health = 200;
            this.maxHealth = 200;
            this.speed = 2;
            this.damage = 30;
            this.detectionRange = 15;
        }
    }

    /**
     * Item etkisini ayarla — sadece COLLECTIBLE tipi için
     */
    public void setItemEffect(String effect, int amount) {
        if (!type.equals("COLLECTIBLE")) {
            System.out.println("Sadece toplanabilir nesnelere etki atanabilir!");
            return;
        }
        this.itemEffect = effect;
        this.effectAmount = amount;
    }

    // Getter'lar
    public String getType() { return type; }
    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHealth() { return health; }
    public int getSpeed() { return speed; }
    public int getDamage() { return damage; }
    public int getScore() { return score; }
    public boolean isAlive() { return isAlive; }
    public String getEnemyType() { return enemyType; }
    public String getItemEffect() { return itemEffect; }
    public boolean isDestructible() { return isDestructible; }

    // Setter'lar
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setHealth(int health) { this.health = health; }
    public void setAlive(boolean alive) { this.isAlive = alive; }
}
