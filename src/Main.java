/**
 * Main - Oyunun giriş noktası.
 * 
 * Faz 3: Strategy ve Observer pattern'leri demo edilir.
 * - Düşmanlar farklı hareket stratejileri kullanır
 * - Olay sistemi ile skor ve loglama yönetilir
 * - OCP gösterimi: PatrolMovement mevcut kodu değiştirmeden eklendi
 */
public class Main {

    public static void main(String[] args) {

        // Facade ile oyun motoru oluştur
        GameEngine engine = new GameEngine();
        engine.initialize();

        // --- Observer Pattern: Listener'ları ekle ---
        ScoreListener scoreListener = new ScoreListener();
        LogListener logListener = new LogListener();

        // Skor listener'ı belirli olaylara abone et
        engine.addEventListener(GameEvent.EventType.ENEMY_KILLED, scoreListener);
        engine.addEventListener(GameEvent.EventType.ITEM_COLLECTED, scoreListener);
        engine.addEventListener(GameEvent.EventType.OBSTACLE_DESTROYED, scoreListener);

        // Log listener'ı tüm olaylara abone et (global)
        engine.addGlobalEventListener(logListener);

        System.out.println("📢 Observer'lar eklendi: ScoreListener, LogListener\n");

        // --- Oyuncu oluştur ---
        Player player = engine.addPlayer("Kahraman", 10, 10);

        // --- Düşmanlar (Strategy Pattern ile farklı hareket) ---
        Enemy goblin = engine.addEnemy("Goblin", 12, 11, "BASIC");
        Enemy kurt = engine.addEnemy("Kurt", 8, 9, "FAST");
        Enemy golem = engine.addEnemy("Golem", 15, 10, "TANK");
        Enemy ejderha = engine.addEnemy("Ejderha", 20, 15, "BOSS");

        // --- OCP Demo: PatrolMovement — mevcut kodu değiştirmeden yeni strateji ---
        Enemy nobetci = engine.addEnemy("Nöbetçi", 5, 5, "BASIC");
        nobetci.setMovementStrategy(new PatrolMovement(3));

        // --- Item ve engeller ---
        engine.addCollectible("Sağlık İksiri", 11, 10, "HEALTH", 25);
        engine.addCollectible("Hız Büyüsü", 9, 12, "SPEED", 3);
        engine.addObstacle("Kaya", 13, 10, true, 3);

        System.out.println();

        // --- Strategy Pattern gösterimi ---
        System.out.println("═══ STRATEJİ ÖZETİ ═══");
        System.out.println("  Goblin: " + goblin.getMovementStrategy().getStrategyName());
        System.out.println("  Kurt: " + kurt.getMovementStrategy().getStrategyName());
        System.out.println("  Golem: " + golem.getMovementStrategy().getStrategyName());
        System.out.println("  Ejderha: " + ejderha.getMovementStrategy().getStrategyName());
        System.out.println("  Nöbetçi: " + nobetci.getMovementStrategy().getStrategyName() + " (OCP demo)");
        System.out.println();

        // Başlangıç durumu
        engine.showStatus();
        engine.renderAll();

        System.out.println();

        // --- Oyun döngüsü ---
        engine.run(5);

        // --- Olay geçmişi ---
        logListener.printLog();

        // Son durum
        System.out.println("Final skor (Observer): " + scoreListener.getTotalScore());
        engine.showStatus();
    }
}
