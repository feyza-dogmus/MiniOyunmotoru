import java.util.ArrayList;
import java.util.List;

/**
 * GameManager - Oyun döngüsünü ve tüm nesneleri yöneten sınıf.
 * Nesne yaratma, güncelleme, çarpışma kontrolü hepsi burada.
 * Sıkı bağlı (tightly coupled) ve şişirilmiş bir yapı.
 */
public class GameManager {

    private List<GameObject> gameObjects;
    private GameObject player;
    private boolean isRunning;
    private int turnCount;
    private int mapWidth = 50;
    private int mapHeight = 50;

    public GameManager() {
        this.gameObjects = new ArrayList<>();
        this.isRunning = false;
        this.turnCount = 0;
    }

    /**
     * Nesne yaratma — tip kontrolü ile farklı parametreler
     */
    public GameObject createObject(String type, String name, int x, int y) {
        GameObject obj = new GameObject(type, name, x, y);

        if (type.equals("PLAYER")) {
            if (player != null) {
                System.out.println("Zaten bir oyuncu var!");
                return null;
            }
            player = obj;
        } else if (type.equals("ENEMY")) {
            // Varsayılan düşman tipi
            obj.setEnemyType("BASIC");
        } else if (type.equals("COLLECTIBLE")) {
            obj.setItemEffect("HEALTH", 20);
        } else if (type.equals("OBSTACLE")) {
            // Varsayılan olarak yıkılabilir
        }

        gameObjects.add(obj);
        return obj;
    }

    /**
     * Özel düşman yaratma — daha fazla if-else
     */
    public GameObject createEnemy(String name, int x, int y, String enemyType) {
        GameObject enemy = new GameObject("ENEMY", name, x, y);
        enemy.setEnemyType(enemyType);

        // Düşman tipine göre özel ayarlar — burada da if-else
        if (enemyType.equals("BOSS")) {
            System.out.println("⚠ BOSS düşman oluşturuldu: " + name);
        } else if (enemyType.equals("TANK")) {
            System.out.println("🛡 Tank düşman oluşturuldu: " + name);
        } else if (enemyType.equals("FAST")) {
            System.out.println("💨 Hızlı düşman oluşturuldu: " + name);
        } else {
            System.out.println("👾 Düşman oluşturuldu: " + name);
        }

        gameObjects.add(enemy);
        return enemy;
    }

    /**
     * Özel item yaratma
     */
    public GameObject createCollectible(String name, int x, int y, String effect, int amount) {
        GameObject item = new GameObject("COLLECTIBLE", name, x, y);
        item.setItemEffect(effect, amount);
        gameObjects.add(item);
        return item;
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

            // Tüm nesneleri güncelle
            updateAll();

            // Çarpışma kontrolü
            checkCollisions();

            // Ölü nesneleri temizle
            cleanupDead();

            // Oyun bitti mi?
            if (player != null && !player.isAlive()) {
                System.out.println("\n💀 GAME OVER! Skor: " + player.getScore());
                isRunning = false;
            }

            // Tüm düşmanlar öldü mü?
            boolean allEnemiesDead = true;
            for (GameObject obj : gameObjects) {
                if (obj.getType().equals("ENEMY") && obj.isAlive()) {
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

    /**
     * Tüm nesneleri güncelle
     */
    private void updateAll() {
        for (GameObject obj : gameObjects) {
            if (obj.isAlive()) {
                obj.update();
            }
        }
    }

    /**
     * Çarpışma kontrolü — basit mesafe kontrolü, O(n²) karmaşıklık
     */
    private void checkCollisions() {
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject a = gameObjects.get(i);
                GameObject b = gameObjects.get(j);

                if (!a.isAlive() || !b.isAlive()) continue;

                // Basit mesafe kontrolü
                int dx = Math.abs(a.getX() - b.getX());
                int dy = Math.abs(a.getY() - b.getY());

                if (dx <= 2 && dy <= 2) {
                    a.onCollision(b);
                }
            }
        }
    }

    /**
     * Ölü nesneleri temizle
     */
    private void cleanupDead() {
        List<GameObject> toRemove = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (!obj.isAlive()) {
                // Tip kontrolü — yine if-else
                if (obj.getType().equals("ENEMY")) {
                    System.out.println("☠ Düşman yok edildi: " + obj.getName());
                    if (player != null && player.isAlive()) {
                        // Düşman tipine göre farklı skor — bir kez daha if-else
                        if (obj.getEnemyType() != null && obj.getEnemyType().equals("BOSS")) {
                            player.setHealth(player.getHealth()); // placeholder
                            System.out.println("🏆 Boss yenildi! +50 puan");
                        } else if (obj.getEnemyType() != null && obj.getEnemyType().equals("TANK")) {
                            System.out.println("🏆 Tank yenildi! +30 puan");
                        } else {
                            System.out.println("🏆 Düşman yenildi! +10 puan");
                        }
                    }
                } else if (obj.getType().equals("COLLECTIBLE")) {
                    System.out.println("✨ Item toplandı: " + obj.getName());
                } else if (obj.getType().equals("OBSTACLE")) {
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
                // Tip bazlı farklı durum çıktısı — if-else
                if (obj.getType().equals("PLAYER")) {
                    System.out.println("  [OYUNCU] " + obj.getName() + " — Can: " + obj.getHealth() + " | Skor: " + obj.getScore());
                } else if (obj.getType().equals("ENEMY")) {
                    System.out.println("  [DÜŞMAN] " + obj.getName() + " (" + obj.getEnemyType() + ") — Can: " + obj.getHealth());
                } else if (obj.getType().equals("COLLECTIBLE")) {
                    System.out.println("  [ITEM] " + obj.getName() + " — Etki: " + obj.getItemEffect());
                } else if (obj.getType().equals("OBSTACLE")) {
                    System.out.println("  [ENGEL] " + obj.getName() + " — Yıkılabilir: " + obj.isDestructible());
                }
            }
        }
        System.out.println("===================\n");
    }

    public GameObject getPlayer() { return player; }
    public List<GameObject> getGameObjects() { return gameObjects; }
    public boolean isRunning() { return isRunning; }
}
