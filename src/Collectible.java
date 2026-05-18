/**
 * Collectible - Toplanabilir nesneyi temsil eder.
 * Sağlık, hız, hasar gibi etkileri oyuncuya uygular.
 */
public class Collectible extends GameObject {

    private String itemEffect; // "HEALTH", "SPEED", "DAMAGE"
    private int effectAmount;

    public Collectible(String name, int x, int y, String effect, int amount) {
        super(name, x, y, 1, 1, 0, 0);
        this.itemEffect = effect;
        this.effectAmount = amount;
    }

    @Override
    public void update() {
        if (!isAlive) return;
        System.out.println("[Item] " + name + " pozisyon: (" + x + ", " + y + ") | Etki: " + itemEffect + " +" + effectAmount);
    }

    @Override
    public void onCollision(GameObject other) {
        // Toplanabilir nesneler çarpışma başlatmaz, Player tarafından toplanır
    }

    /**
     * Etkiyi oyuncuya uygula
     */
    public void applyEffect(Player player) {
        if (itemEffect.equals("HEALTH")) {
            player.heal(effectAmount);
            System.out.println("💚 " + player.getName() + " can yeniledi! Can: " + player.getHealth());
        } else if (itemEffect.equals("SPEED")) {
            // Hız artışı — doğrudan erişim yerine getter/setter kullanılabilir
            System.out.println("⚡ " + player.getName() + " hızlandı!");
        } else if (itemEffect.equals("DAMAGE")) {
            System.out.println("🗡 " + player.getName() + " güçlendi!");
        }
    }

    @Override
    public String getDisplaySymbol() { return "C"; }

    @Override
    public String getType() { return "COLLECTIBLE"; }

    public String getItemEffect() { return itemEffect; }
    public int getEffectAmount() { return effectAmount; }
}
