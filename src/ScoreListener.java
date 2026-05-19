/**
 * ScoreListener - Skor takip eden Observer.
 * Düşman öldürüldüğünde, item toplandığında skor günceller.
 */
public class ScoreListener implements GameEventListener {

    private int totalScore;

    public ScoreListener() {
        this.totalScore = 0;
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ENEMY_KILLED:
                totalScore += event.getValue();
                System.out.println("🏆 [SKOR] +" + event.getValue() + " puan! (Toplam: " + totalScore + ")");
                break;
            case ITEM_COLLECTED:
                totalScore += 10;
                System.out.println("🏆 [SKOR] +10 puan (item)! (Toplam: " + totalScore + ")");
                break;
            case OBSTACLE_DESTROYED:
                totalScore += 5;
                System.out.println("🏆 [SKOR] +5 puan (engel)! (Toplam: " + totalScore + ")");
                break;
            default:
                break;
        }
    }

    public int getTotalScore() { return totalScore; }
}
