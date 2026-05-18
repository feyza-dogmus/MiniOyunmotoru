/**
 * Main - Oyunun giriş noktası.
 * 
 * Faz 2: GameEngine Facade kullanılarak basitleştirildi.
 * Decorator pattern ile dinamik yetenekler ekleniyor.
 */
public class Main {

    public static void main(String[] args) {

        // Facade ile oyun motoru oluştur
        GameEngine engine = new GameEngine();
        engine.initialize();

        // --- Oyuncu oluştur ---
        Player player = engine.addPlayer("Kahraman", 10, 10);

        // --- Düşmanlar oluştur ---
        engine.addEnemy("Goblin", 12, 11, "BASIC");
        engine.addEnemy("Kurt", 8, 9, "FAST");
        Enemy golem = engine.addEnemy("Golem", 15, 10, "TANK");
        engine.addEnemy("Ejderha", 20, 15, "BOSS");

        // --- Item ve engeller ---
        engine.addCollectible("Sağlık İksiri", 11, 10, "HEALTH", 25);
        engine.addCollectible("Hız Büyüsü", 9, 12, "SPEED", 3);
        engine.addCollectible("Güç Yüzüğü", 14, 8, "DAMAGE", 5);
        engine.addObstacle("Kaya", 13, 10, true, 3);
        engine.addObstacle("Duvar", 10, 13, false, 0);

        System.out.println();

        // --- Decorator Pattern Demo ---
        System.out.println("═══ DECORATOR PATTERN DEMO ═══");

        // Oyuncuya zırh ekle (Decorator)
        ArmorDecorator armoredPlayer = engine.addArmor(player, 30);

        // Zırhlı oyuncuya hız artışı ekle (Decorator zinciri)
        SpeedBoostDecorator boostedPlayer = engine.addSpeedBoost(armoredPlayer, 3, 5);

        // Hasar artışı ekle (üçlü decorator zinciri)
        DamageBoostDecorator poweredPlayer = engine.addDamageBoost(boostedPlayer, 8);

        System.out.println("\n📊 Decorator zinciri sonucu:");
        System.out.println("  Orijinal hız: " + player.getSpeed() + " → Decorated hız: " + poweredPlayer.getSpeed());
        System.out.println("  Orijinal hasar: " + player.getDamage() + " → Decorated hasar: " + poweredPlayer.getDamage());

        System.out.println();

        // Zırh testi
        System.out.println("═══ ZIRH TESTİ ═══");
        System.out.println("Hasar öncesi — Can: " + poweredPlayer.getHealth() + " | Zırh: " + armoredPlayer.getArmorPoints());
        poweredPlayer.takeDamage(20);
        System.out.println("20 hasar sonrası — Can: " + poweredPlayer.getHealth() + " | Zırh: " + armoredPlayer.getArmorPoints());
        poweredPlayer.takeDamage(25);
        System.out.println("25 hasar daha — Can: " + poweredPlayer.getHealth() + " | Zırh: " + armoredPlayer.getArmorPoints());

        System.out.println();

        // --- Facade ile render ---
        engine.showStatus();
        engine.renderAll();

        System.out.println();

        // --- Oyun döngüsü ---
        engine.run(3);

        // Son durum
        System.out.println("\n=== FINAL DURUMU ===");
        engine.showStatus();
    }
}
