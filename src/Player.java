/**
 * Player - Oyuncu nesnesini temsil eder.
 * Oyuncuya özel alanlar (score) ve davranışlar burada tanımlı.
 */
public class Player extends GameObject {

    private int score;

    public Player(String name, int x, int y) {
        super(name, x, y, 100, 100, 5, 10);
        this.score = 0;
    }

    @Override
    public void update() {
        if (!isAlive) return;
        System.out.println(name + " pozisyon: (" + x + ", " + y + ") | Can: " + health + "/" + maxHealth + " | Skor: " + score);
    }

    @Override
    public void onCollision(GameObject other) {
        if (!this.isAlive || !other.isAlive()) return;

        if (other instanceof Enemy) {
            this.takeDamage(other.getDamage());
            System.out.println("⚔ " + this.name + " düşmandan " + other.getDamage() + " hasar aldı! Kalan can: " + this.health);
            if (!this.isAlive) {
                System.out.println("💀 " + this.name + " öldü! GAME OVER");
            }
        } else if (other instanceof Collectible) {
            Collectible item = (Collectible) other;
            item.applyEffect(this);
            other.setAlive(false);
            this.score += 10;
        } else if (other instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) other;
            obstacle.hitBy(this);
        }
    }

    /**
     * Oyuncu hareketi
     */
    public void move(String direction) {
        if (direction.equals("UP")) {
            y -= speed;
        } else if (direction.equals("DOWN")) {
            y += speed;
        } else if (direction.equals("LEFT")) {
            x -= speed;
        } else if (direction.equals("RIGHT")) {
            x += speed;
        }
    }

    @Override
    public String getDisplaySymbol() { return "P"; }

    @Override
    public String getType() { return "PLAYER"; }

    public int getScore() { return score; }
    public void addScore(int points) { this.score += points; }
}
