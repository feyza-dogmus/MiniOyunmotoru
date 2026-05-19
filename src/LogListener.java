import java.util.ArrayList;
import java.util.List;

/**
 * LogListener - Tüm olayları loglar — Observer.
 * Oyun geçmişini kayıt altına alır.
 */
public class LogListener implements GameEventListener {

    private List<String> eventLog;

    public LogListener() {
        this.eventLog = new ArrayList<>();
    }

    @Override
    public void onEvent(GameEvent event) {
        String logEntry = "[LOG] " + event.toString();
        eventLog.add(logEntry);
        System.out.println("📋 " + logEntry);
    }

    /**
     * Tüm log kayıtlarını yazdır.
     */
    public void printLog() {
        System.out.println("\n═══ OLAY GEÇMİŞİ ═══");
        for (int i = 0; i < eventLog.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + eventLog.get(i));
        }
        System.out.println("═══════════════════════\n");
    }

    public List<String> getEventLog() { return eventLog; }
    public int getEventCount() { return eventLog.size(); }
}
