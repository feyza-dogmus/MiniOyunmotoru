/**
 * Main - Oyunun giriş noktası.
 * 
 * Faz 1: Nesne yaratma artık GameObjectFactory üzerinden yapılıyor.
 * GameManager, factory'yi kendi içinde kullanıyor.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    🎮 MİNİ OYUN MOTORU v1.0     ║");
        System.out.println("║   Faz 1 — Factory Method         ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();

        // Oyun yöneticisini oluştur (factory dahili)
        GameManager gameManager = new GameManager();
        Renderer renderer = new Renderer();

        // Oyuncu oluştur — factory üzerinden
        gameManager.addPlayer("Kahraman", 10, 10);

        // Düşmanlar oluştur — factory üzerinden, tip belirterek
        gameManager.addEnemy("Goblin", 12, 11, "BASIC");
        gameManager.addEnemy("Kurt", 8, 9, "FAST");
        gameManager.addEnemy("Golem", 15, 10, "TANK");
        gameManager.addEnemy("Ejderha", 20, 15, "BOSS");

        // Toplanabilir nesneler — factory üzerinden
        gameManager.addCollectible("Sağlık İksiri", 11, 10, "HEALTH", 25);
        gameManager.addCollectible("Hız Büyüsü", 9, 12, "SPEED", 3);
        gameManager.addCollectible("Güç Yüzüğü", 14, 8, "DAMAGE", 5);

        // Engeller — factory üzerinden
        gameManager.addObstacle("Kaya", 13, 10, true, 3);
        gameManager.addObstacle("Duvar", 10, 13, false, 0);

        // Başlangıç durumunu göster
        gameManager.printStatus();

        // Sahneyi render et
        renderer.renderHUD(gameManager);
        renderer.renderScene(gameManager);
        renderer.renderMap(gameManager, 25, 20);

        System.out.println();

        // Oyun döngüsünü başlat
        gameManager.gameLoop(5);

        // Son durumu göster
        System.out.println("\n=== FINAL DURUMU ===");
        gameManager.printStatus();
    }
}
