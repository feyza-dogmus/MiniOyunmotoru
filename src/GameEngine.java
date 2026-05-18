/**
 * GameEngine - Facade Pattern uygulaması.
 * 
 * Oyun motorunun karmaşık alt sistemlerini (GameManager, Renderer,
 * GameObjectFactory) basit bir arayüz arkasında gizler.
 * 
 * Kullanıcı (Main sınıfı) artık alt sistemlerin detaylarını bilmek
 * zorunda değil. Tek bir GameEngine nesnesi üzerinden tüm işlemler yapılır.
 */
public class GameEngine {

    private GameManager gameManager;
    private Renderer renderer;
    private boolean initialized;

    public GameEngine() {
        this.gameManager = new GameManager();
        this.renderer = new Renderer();
        this.initialized = false;
    }

    // ==========================================
    //  FACADE METOTLARI — Basitleştirilmiş API
    // ==========================================

    /**
     * Oyunu başlat — tüm alt sistemleri hazırla
     */
    public void initialize() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    🎮 MİNİ OYUN MOTORU v2.0     ║");
        System.out.println("║   Faz 2 — Structural Patterns    ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();
        this.initialized = true;
    }

    /**
     * Oyuncu ekle — basit tek satır
     */
    public Player addPlayer(String name, int x, int y) {
        checkInitialized();
        return gameManager.addPlayer(name, x, y);
    }

    /**
     * Düşman ekle — basit tek satır
     */
    public Enemy addEnemy(String name, int x, int y, String enemyType) {
        checkInitialized();
        return gameManager.addEnemy(name, x, y, enemyType);
    }

    /**
     * Toplanabilir nesne ekle
     */
    public Collectible addCollectible(String name, int x, int y, String effect, int amount) {
        checkInitialized();
        return gameManager.addCollectible(name, x, y, effect, amount);
    }

    /**
     * Engel ekle
     */
    public Obstacle addObstacle(String name, int x, int y, boolean destructible, int durability) {
        checkInitialized();
        return gameManager.addObstacle(name, x, y, destructible, durability);
    }

    /**
     * Bir nesneye zırh ekle — Decorator kullanımı facade üzerinden
     */
    public ArmorDecorator addArmor(GameObject target, int armorPoints) {
        checkInitialized();
        ArmorDecorator armored = new ArmorDecorator(target, armorPoints);
        System.out.println("🛡 " + target.getName() + " zırh kazandı! (+" + armorPoints + " zırh)");
        return armored;
    }

    /**
     * Bir nesneye hız artışı ekle — Decorator kullanımı facade üzerinden
     */
    public SpeedBoostDecorator addSpeedBoost(GameObject target, int bonusSpeed, int duration) {
        checkInitialized();
        SpeedBoostDecorator boosted = new SpeedBoostDecorator(target, bonusSpeed, duration);
        System.out.println("⚡ " + target.getName() + " hızlandı! (+" + bonusSpeed + " hız, " + duration + " tur)");
        return boosted;
    }

    /**
     * Bir nesneye hasar artışı ekle — Decorator kullanımı facade üzerinden
     */
    public DamageBoostDecorator addDamageBoost(GameObject target, int bonusDamage) {
        checkInitialized();
        DamageBoostDecorator powered = new DamageBoostDecorator(target, bonusDamage);
        System.out.println("🗡 " + target.getName() + " güçlendi! (+" + bonusDamage + " hasar)");
        return powered;
    }

    /**
     * Sahneyi render et — tüm render işlemleri tek çağrı
     */
    public void renderAll() {
        checkInitialized();
        renderer.renderHUD(gameManager);
        renderer.renderScene(gameManager);
        renderer.renderMap(gameManager, 25, 20);
    }

    /**
     * Oyun durumunu göster
     */
    public void showStatus() {
        checkInitialized();
        gameManager.printStatus();
    }

    /**
     * Oyun döngüsünü çalıştır
     */
    public void run(int maxTurns) {
        checkInitialized();
        gameManager.gameLoop(maxTurns);
    }

    /**
     * Hızlı oyun kurulumu — tek çağrı ile hazır oyun
     */
    public void quickSetup() {
        initialize();

        addPlayer("Kahraman", 10, 10);

        addEnemy("Goblin", 12, 11, "BASIC");
        addEnemy("Kurt", 8, 9, "FAST");
        addEnemy("Golem", 15, 10, "TANK");
        addEnemy("Ejderha", 20, 15, "BOSS");

        addCollectible("Sağlık İksiri", 11, 10, "HEALTH", 25);
        addCollectible("Hız Büyüsü", 9, 12, "SPEED", 3);
        addCollectible("Güç Yüzüğü", 14, 8, "DAMAGE", 5);

        addObstacle("Kaya", 13, 10, true, 3);
        addObstacle("Duvar", 10, 13, false, 0);

        System.out.println("\n✅ Oyun kurulumu tamamlandı!\n");
    }

    // --- İç yardımcı metotlar ---

    private void checkInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Oyun motoru henüz başlatılmadı! initialize() çağırın.");
        }
    }

    // --- Alt sistemlere erişim (gerektiğinde) ---
    public GameManager getGameManager() { return gameManager; }
    public Renderer getRenderer() { return renderer; }
}
