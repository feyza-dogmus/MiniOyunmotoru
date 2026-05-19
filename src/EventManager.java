import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventManager - Observer Pattern Subject/Publisher sınıfı.
 * 
 * Listener'ları yönetir ve olayları yayınlar.
 * Belirli olay tiplerine abone olma desteği var.
 */
public class EventManager {

    private Map<GameEvent.EventType, List<GameEventListener>> listeners;
    private List<GameEventListener> globalListeners;

    public EventManager() {
        this.listeners = new HashMap<>();
        this.globalListeners = new ArrayList<>();

        // Her olay tipi için boş liste oluştur
        for (GameEvent.EventType type : GameEvent.EventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    /**
     * Belirli bir olay tipine listener ekle.
     */
    public void subscribe(GameEvent.EventType eventType, GameEventListener listener) {
        listeners.get(eventType).add(listener);
    }

    /**
     * Tüm olayları dinleyen global listener ekle.
     */
    public void subscribeAll(GameEventListener listener) {
        globalListeners.add(listener);
    }

    /**
     * Belirli bir olay tipinden listener çıkar.
     */
    public void unsubscribe(GameEvent.EventType eventType, GameEventListener listener) {
        listeners.get(eventType).remove(listener);
    }

    /**
     * Olay yayınla — tüm ilgili listener'ları bilgilendir.
     */
    public void publish(GameEvent event) {
        // Olay tipine abone olan listener'lar
        List<GameEventListener> typeListeners = listeners.get(event.getType());
        if (typeListeners != null) {
            for (GameEventListener listener : typeListeners) {
                listener.onEvent(event);
            }
        }

        // Global listener'lar
        for (GameEventListener listener : globalListeners) {
            listener.onEvent(event);
        }
    }
}
