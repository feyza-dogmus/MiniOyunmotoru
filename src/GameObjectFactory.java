/**
 * GameObjectFactory - Factory Method Pattern uygulaması.
 * 
 * Nesne yaratma sorumluluğunu merkezileştirir.
 * GameManager'daki if-else zincirleri kaldırıldı.
 * Yeni nesne tipi eklemek için sadece yeni bir alt sınıf ve
 * bu factory'ye yeni bir metot eklemek yeterli.
 */
public class GameObjectFactory {

    /**
     * Oyuncu oluşturur.
     */
    public Player createPlayer(String name, int x, int y) {
        return new Player(name, x, y);
    }

    /**
     * Düşman oluşturur — Factory Method.
     * Düşman tipi parametresine göre uygun stat'larla yapılandırır.
     */
    public Enemy createEnemy(String name, int x, int y, String enemyType) {
        Enemy enemy = new Enemy(name, x, y);
        enemy.setEnemyType(enemyType);

        // Oluşturma bilgisi
        switch (enemyType) {
            case "BOSS":
                System.out.println("⚠ BOSS düşman oluşturuldu: " + name);
                break;
            case "TANK":
                System.out.println("🛡 Tank düşman oluşturuldu: " + name);
                break;
            case "FAST":
                System.out.println("💨 Hızlı düşman oluşturuldu: " + name);
                break;
            default:
                System.out.println("👾 Düşman oluşturuldu: " + name);
                break;
        }

        return enemy;
    }

    /**
     * Toplanabilir nesne oluşturur.
     */
    public Collectible createCollectible(String name, int x, int y, String effect, int amount) {
        return new Collectible(name, x, y, effect, amount);
    }

    /**
     * Engel oluşturur.
     */
    public Obstacle createObstacle(String name, int x, int y, boolean destructible, int durability) {
        return new Obstacle(name, x, y, destructible, durability);
    }
}
