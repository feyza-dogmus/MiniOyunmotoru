/**
 * Renderer - Oyun nesnelerini ekrana çizen sınıf.
 * GameManager ve GameObject'e sıkı bağımlı.
 * Tip kontrolü burada da tekrarlanıyor — DRY ihlali.
 */
public class Renderer {

    /**
     * Tek bir nesneyi render et — tip bazlı farklı görsel
     */
    public void renderObject(GameObject obj) {
        if (!obj.isAlive()) return;

        if (obj.getType().equals("PLAYER")) {
            System.out.println("🟦 [P] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ")");
        } else if (obj.getType().equals("ENEMY")) {
            // Düşman tipine göre farklı render
            if (obj.getEnemyType() != null && obj.getEnemyType().equals("BOSS")) {
                System.out.println("🟥 [BOSS] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ") ❤ " + obj.getHealth());
            } else if (obj.getEnemyType() != null && obj.getEnemyType().equals("TANK")) {
                System.out.println("🟧 [TANK] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ") ❤ " + obj.getHealth());
            } else if (obj.getEnemyType() != null && obj.getEnemyType().equals("FAST")) {
                System.out.println("🟨 [FAST] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ") ❤ " + obj.getHealth());
            } else {
                System.out.println("🟫 [E] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ") ❤ " + obj.getHealth());
            }
        } else if (obj.getType().equals("COLLECTIBLE")) {
            if (obj.getItemEffect() != null && obj.getItemEffect().equals("HEALTH")) {
                System.out.println("💚 [HP] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ")");
            } else if (obj.getItemEffect() != null && obj.getItemEffect().equals("SPEED")) {
                System.out.println("💛 [SPD] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ")");
            } else if (obj.getItemEffect() != null && obj.getItemEffect().equals("DAMAGE")) {
                System.out.println("💜 [DMG] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ")");
            }
        } else if (obj.getType().equals("OBSTACLE")) {
            if (obj.isDestructible()) {
                System.out.println("🟫 [O] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ") 🔨");
            } else {
                System.out.println("⬛ [O] " + obj.getName() + " (" + obj.getX() + "," + obj.getY() + ") 🔒");
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
     * HUD (Heads-Up Display) render — oyuncu bilgileri
     */
    public void renderHUD(GameManager gameManager) {
        GameObject player = gameManager.getPlayer();
        if (player != null && player.isAlive()) {
            System.out.println("┌─────────── HUD ───────────┐");
            System.out.println("│ Oyuncu: " + player.getName());
            System.out.println("│ Can: " + player.getHealth() + "/" + 100);
            System.out.println("│ Skor: " + player.getScore());
            System.out.println("│ Pozisyon: (" + player.getX() + ", " + player.getY() + ")");
            System.out.println("│ Hız: " + player.getSpeed() + " | Hasar: " + player.getDamage());
            System.out.println("└───────────────────────────┘");
        }
    }

    /**
     * Harita render — basit grid gösterimi
     * Bu metot GameManager'a doğrudan erişiyor — yüksek bağımlılık
     */
    public void renderMap(GameManager gameManager, int width, int height) {
        char[][] map = new char[height][width];

        // Boş harita
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = '.';
            }
        }

        // Nesneleri yerleştir — yine tip kontrolü
        for (GameObject obj : gameManager.getGameObjects()) {
            if (obj.isAlive()) {
                int px = Math.max(0, Math.min(obj.getX(), width - 1));
                int py = Math.max(0, Math.min(obj.getY(), height - 1));

                if (obj.getType().equals("PLAYER")) {
                    map[py][px] = 'P';
                } else if (obj.getType().equals("ENEMY")) {
                    map[py][px] = 'E';
                } else if (obj.getType().equals("COLLECTIBLE")) {
                    map[py][px] = 'C';
                } else if (obj.getType().equals("OBSTACLE")) {
                    map[py][px] = '#';
                }
            }
        }

        // Haritayı yazdır
        System.out.println("Harita (" + width + "x" + height + "):");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
