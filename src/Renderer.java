/**
 * Renderer - Oyun sahnesini çizen sınıf.
 * 
 * Faz 1: instanceof kullanılarak tip kontrolü yapılıyor.
 * String karşılaştırması yerine polimorfizm ve instanceof kullanıldı.
 * (Faz 2'de Decorator/Adapter ile daha da iyileştirilebilir)
 */
public class Renderer {

    /**
     * Tek bir nesneyi render et
     */
    public void renderObject(GameObject obj) {
        if (!obj.isAlive()) return;

        if (obj instanceof Player) {
            System.out.println("🟦 [P] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ")");
        } else if (obj instanceof Enemy) {
            Enemy enemy = (Enemy) obj;
            String icon;
            switch (enemy.getEnemyType()) {
                case "BOSS": icon = "🟥 [BOSS]"; break;
                case "TANK": icon = "🟧 [TANK]"; break;
                case "FAST": icon = "🟨 [FAST]"; break;
                default:     icon = "🟫 [E]"; break;
            }
            System.out.println(icon + " " + enemy.getName() + " (" + enemy.getX() + "," + enemy.getY() + ") ❤ " + enemy.getHealth());
        } else if (obj instanceof Collectible) {
            Collectible item = (Collectible) obj;
            String icon;
            switch (item.getItemEffect()) {
                case "HEALTH": icon = "💚 [HP]"; break;
                case "SPEED":  icon = "💛 [SPD]"; break;
                case "DAMAGE": icon = "💜 [DMG]"; break;
                default:       icon = "⬜ [?]"; break;
            }
            System.out.println(icon + " " + item.getName() + " (" + item.getX() + "," + item.getY() + ")");
        } else if (obj instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) obj;
            if (obstacle.isDestructible()) {
                System.out.println("🟫 [O] " + obstacle.getName() + " (" + obstacle.getX() + "," + obstacle.getY() + ") 🔨");
            } else {
                System.out.println("⬛ [O] " + obstacle.getName() + " (" + obstacle.getX() + "," + obstacle.getY() + ") 🔒");
            }
        }
    }

    /**
     * Tüm oyun sahnesini render et
     */
    public void renderScene(GameManager gameManager) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          OYUN SAHNESI                ║");
        System.out.println("╠══════════════════════════════════════╣");

        for (GameObject obj : gameManager.getGameObjects()) {
            if (obj.isAlive()) {
                System.out.print("║ ");
                renderObject(obj);
            }
        }

        System.out.println("╚══════════════════════════════════════╝");
    }

    /**
     * HUD render — oyuncu bilgileri
     */
    public void renderHUD(GameManager gameManager) {
        Player player = gameManager.getPlayer();
        if (player != null && player.isAlive()) {
            System.out.println("┌─────────── HUD ───────────┐");
            System.out.println("│ Oyuncu: " + player.getName());
            System.out.println("│ Can: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println("│ Skor: " + player.getScore());
            System.out.println("│ Pozisyon: (" + player.getX() + ", " + player.getY() + ")");
            System.out.println("│ Hız: " + player.getSpeed() + " | Hasar: " + player.getDamage());
            System.out.println("└───────────────────────────┘");
        }
    }

    /**
     * Harita render
     */
    public void renderMap(GameManager gameManager, int width, int height) {
        char[][] map = new char[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = '.';
            }
        }

        for (GameObject obj : gameManager.getGameObjects()) {
            if (obj.isAlive()) {
                int px = Math.max(0, Math.min(obj.getX(), width - 1));
                int py = Math.max(0, Math.min(obj.getY(), height - 1));
                map[py][px] = obj.getDisplaySymbol().charAt(0);
            }
        }

        System.out.println("Harita (" + width + "x" + height + "):");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
