/**
 * Main - Oyunun giriş noktası.
 * Tüm nesneler burada elle yaratılıyor ve oyun döngüsü başlatılıyor.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    🎮 MİNİ OYUN MOTORU v0.1     ║");
        System.out.println("║       Faz 0 — Başlangıç         ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();

        // Oyun yöneticisini oluştur
        GameManager gameManager = new GameManager();
        Renderer renderer = new Renderer();

        // Oyuncu oluştur
        gameManager.createObject("PLAYER", "Kahraman", 10, 10);

        // Düşmanlar oluştur — farklı tipler
        gameManager.createEnemy("Goblin", 12, 11, "BASIC");
        gameManager.createEnemy("Kurt", 8, 9, "FAST");
        gameManager.createEnemy("Golem", 15, 10, "TANK");
        gameManager.createEnemy("Ejderha", 20, 15, "BOSS");

        // Toplanabilir nesneler oluştur
        gameManager.createCollectible("Sağlık İksiri", 11, 10, "HEALTH", 25);
        gameManager.createCollectible("Hız Büyüsü", 9, 12, "SPEED", 3);
        gameManager.createCollectible("Güç Yüzüğü", 14, 8, "DAMAGE", 5);

        // Engeller oluştur
        gameManager.createObject("OBSTACLE", "Kaya", 13, 10);
        gameManager.createObject("OBSTACLE", "Duvar", 10, 13);

        // Başlangıç durumunu göster
        gameManager.printStatus();

        // Sahneyi render et
        renderer.renderHUD(gameManager);
        renderer.renderScene(gameManager);
        renderer.renderMap(gameManager, 25, 20);

        System.out.println();

        // Oyun döngüsünü başlat (5 tur)
        gameManager.gameLoop(5);

        // Son durumu göster
        System.out.println("\n=== FINAL DURUMU ===");
        gameManager.printStatus();
    }
}
