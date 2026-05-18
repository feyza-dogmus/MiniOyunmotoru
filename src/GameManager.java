import java.util.ArrayList;
import java.util.List;

/**
 * GameManager - Oyun döngüsünü ve nesneleri yöneten sınıf.
 * 
 * Faz 1 Refactoring: Nesne yaratma sorumluluğu GameObjectFactory'ye devredildi.
 * if-else zincirleri büyük ölçüde azaltıldı.
 */
public class GameManager {

    private List<GameObject> gameObjects;
    private Player player;
    private GameObjectFactory factory;
    private boolean isRunning;
    private int turnCount;

    public GameManager() {
        this.gameObjects = new ArrayList<>();
        this.factory = new GameObjectFactory();
        this.isRunning = false;
        this.turnCount = 0;
    }

    /**
     * Oyuncu oluştur — Factory kullanarak
     */
    public Player addPlayer(String name, int x, int y) {
        if (player != null) {
            System.out.println("Zaten bir oyuncu var!");
            return null;
        }
        player = factory.createPlayer(name, x, y);
        gameObjects.add(player);
        return player;
    }

    /**
     * Düşman oluştur — Factory kullanarak
     */
    public Enemy addEnemy(String name, int x, int y, String enemyType) {
        Enemy enemy = factory.createEnemy(name, x, y, enemyType);
        gameObjects.add(enemy);
        return enemy;
    }

    /**
     * Toplanabilir nesne oluştur — Factory kullanarak
     */
    public Collectible addCollectible(String name, int x, int y, String effect, int amount) {
        Collectible item = factory.createCollectible(name, x, y, effect, amount);
        gameObjects.add(item);
        return item;
    }

    /**
     * Engel oluştur — Factory kullanarak
     */
    public Obstacle addObstacle(String name, int x, int y, boolean destructible, int durability) {
        Obstacle obstacle = factory.createObstacle(name, x, y, destructible, durability);
        gameObjects.add(obstacle);
        return obstacle;
    }

    /**
     * Ana oyun döngüsü
     */
    public void gameLoop(int maxTurns) {
        isRunning = true;
        System.out.println("=== OYUN BAŞLADI ===\n");

        while (isRunning && turnCount < maxTurns) {
            turnCount++;
            System.out.println("--- Tur " + turnCount + " ---");

            updateAll();
            checkCollisions();
            cleanupDead();

            // Oyun bitti mi?
            if (player != null && !player.isAlive()) {
                System.out.println("\n💀 GAME OVER! Skor: " + player.getScore());
                isRunning = false;
            }

            // Tüm düşmanlar öldü mü?
            boolean allEnemiesDead = true;
            for (GameObject obj : gameObjects) {
                if (obj instanceof Enemy && obj.isAlive()) {
                    allEnemiesDead = false;
                    break;
                }
            }
            if (allEnemiesDead && turnCount > 1) {
                System.out.println("\n🎉 TÜM DÜŞMANLAR YOK EDİLDİ! Skor: " + player.getScore());
                isRunning = false;
            }

            System.out.println();
        }

        System.out.println("=== OYUN BİTTİ === (Toplam tur: " + turnCount + ")");
    }

    private void updateAll() {
        for (GameObject obj : gameObjects) {
            if (obj.isAlive()) {
                obj.update(); // Polimorfizm — her nesne kendi update'ini çalıştırır
            }
        }
    }

    private void checkCollisions() {
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject a = gameObjects.get(i);
                GameObject b = gameObjects.get(j);

                if (!a.isAlive() || !b.isAlive()) continue;

                int dx = Math.abs(a.getX() - b.getX());
                int dy = Math.abs(a.getY() - b.getY());

                if (dx <= 2 && dy <= 2) {
                    a.onCollision(b);
                }
            }
        }
    }

    private void cleanupDead() {
        List<GameObject> toRemove = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (!obj.isAlive()) {
                // Polimorfizm sayesinde artık tip kontrolüne gerek kalmadı
                if (obj instanceof Enemy) {
                    Enemy enemy = (Enemy) obj;
                    System.out.println("☠ Düşman yok edildi: " + enemy.getName());
                    if (player != null && player.isAlive()) {
                        int points = enemy.getEnemyType().equals("BOSS") ? 50 :
                                     enemy.getEnemyType().equals("TANK") ? 30 : 10;
                        player.addScore(points);
                        System.out.println("🏆 +" + points + " puan!");
                    }
                } else if (obj instanceof Collectible) {
                    System.out.println("✨ Item toplandı: " + obj.getName());
                } else if (obj instanceof Obstacle) {
                    System.out.println("💥 Engel yıkıldı: " + obj.getName());
                }
                toRemove.add(obj);
            }
        }
        gameObjects.removeAll(toRemove);
    }

    /**
     * Mevcut oyun durumunu yazdır
     */
    public void printStatus() {
        System.out.println("\n=== OYUN DURUMU ===");
        System.out.println("Toplam nesne: " + gameObjects.size());
        for (GameObject obj : gameObjects) {
            if (obj.isAlive()) {
                if (obj instanceof Player) {
                    Player p = (Player) obj;
                    System.out.println("  [OYUNCU] " + p.getName() + " — Can: " + p.getHealth() + " | Skor: " + p.getScore());
                } else if (obj instanceof Enemy) {
                    Enemy e = (Enemy) obj;
                    System.out.println("  [DÜŞMAN] " + e.getName() + " (" + e.getEnemyType() + ") — Can: " + e.getHealth());
                } else if (obj instanceof Collectible) {
                    Collectible c = (Collectible) obj;
                    System.out.println("  [ITEM] " + c.getName() + " — Etki: " + c.getItemEffect());
                } else if (obj instanceof Obstacle) {
                    Obstacle o = (Obstacle) obj;
                    System.out.println("  [ENGEL] " + o.getName() + " — Yıkılabilir: " + o.isDestructible());
                }
            }
        }
        System.out.println("===================\n");
    }

    public Player getPlayer() { return player; }
    public List<GameObject> getGameObjects() { return gameObjects; }
    public boolean isRunning() { return isRunning; }
}
