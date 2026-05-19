/**
 * GameEngine - Facade Pattern uygulaması.
 * 
 * Faz 3: Observer pattern entegrasyonu eklendi.
 * Olay sistemi facade üzerinden yönetiliyor.
 */
public class GameEngine {

    private GameManager gameManager;
    private Renderer renderer;
    private EventManager eventManager;
    private boolean initialized;

    public GameEngine() {
        this.gameManager = new GameManager();
        this.renderer = new Renderer();
        this.eventManager = gameManager.getEventManager();
        this.initialized = false;
    }

    public void initialize() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    🎮 MİNİ OYUN MOTORU v3.0     ║");
        System.out.println("║  Faz 3 — Behavioral Patterns     ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();
        this.initialized = true;
    }

    // --- Observer yönetimi ---

    public void addEventListener(GameEvent.EventType type, GameEventListener listener) {
        checkInitialized();
        eventManager.subscribe(type, listener);
    }

    public void addGlobalEventListener(GameEventListener listener) {
        checkInitialized();
        eventManager.subscribeAll(listener);
    }

    // --- Nesne ekleme ---

    public Player addPlayer(String name, int x, int y) {
        checkInitialized();
        return gameManager.addPlayer(name, x, y);
    }

    public Enemy addEnemy(String name, int x, int y, String enemyType) {
        checkInitialized();
        return gameManager.addEnemy(name, x, y, enemyType);
    }

    public Collectible addCollectible(String name, int x, int y, String effect, int amount) {
        checkInitialized();
        return gameManager.addCollectible(name, x, y, effect, amount);
    }

    public Obstacle addObstacle(String name, int x, int y, boolean destructible, int durability) {
        checkInitialized();
        return gameManager.addObstacle(name, x, y, destructible, durability);
    }

    // --- Decorator metotları ---

    public ArmorDecorator addArmor(GameObject target, int armorPoints) {
        checkInitialized();
        ArmorDecorator armored = new ArmorDecorator(target, armorPoints);
        System.out.println("🛡 " + target.getName() + " zırh kazandı! (+" + armorPoints + " zırh)");
        return armored;
    }

    public SpeedBoostDecorator addSpeedBoost(GameObject target, int bonusSpeed, int duration) {
        checkInitialized();
        SpeedBoostDecorator boosted = new SpeedBoostDecorator(target, bonusSpeed, duration);
        System.out.println("⚡ " + target.getName() + " hızlandı! (+" + bonusSpeed + " hız, " + duration + " tur)");
        return boosted;
    }

    public DamageBoostDecorator addDamageBoost(GameObject target, int bonusDamage) {
        checkInitialized();
        DamageBoostDecorator powered = new DamageBoostDecorator(target, bonusDamage);
        System.out.println("🗡 " + target.getName() + " güçlendi! (+" + bonusDamage + " hasar)");
        return powered;
    }

    // --- Oyun kontrol ---

    public void renderAll() {
        checkInitialized();
        renderer.renderHUD(gameManager);
        renderer.renderScene(gameManager);
    }

    public void showStatus() {
        checkInitialized();
        gameManager.printStatus();
    }

    public void run(int maxTurns) {
        checkInitialized();
        gameManager.gameLoop(maxTurns);
    }

    private void checkInitialized() {
        if (!initialized) {
            throw new IllegalStateException("Oyun motoru henüz başlatılmadı! initialize() çağırın.");
        }
    }

    public GameManager getGameManager() { return gameManager; }
    public Renderer getRenderer() { return renderer; }
    public EventManager getEventManager() { return eventManager; }
}
