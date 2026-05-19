/**
 * GameEvent - Oyun olaylarını temsil eden sınıf.
 * Observer pattern'de Subject'in Listener'lara gönderdiği olay nesnesi.
 */
public class GameEvent {

    public enum EventType {
        ENEMY_KILLED,
        ITEM_COLLECTED,
        PLAYER_DAMAGED,
        PLAYER_HEALED,
        OBSTACLE_DESTROYED,
        GAME_OVER,
        GAME_WON
    }

    private final EventType type;
    private final String description;
    private final GameObject source;
    private final GameObject target;
    private final int value;

    public GameEvent(EventType type, String description, GameObject source, GameObject target, int value) {
        this.type = type;
        this.description = description;
        this.source = source;
        this.target = target;
        this.value = value;
    }

    public EventType getType() { return type; }
    public String getDescription() { return description; }
    public GameObject getSource() { return source; }
    public GameObject getTarget() { return target; }
    public int getValue() { return value; }

    @Override
    public String toString() {
        return "[" + type + "] " + description;
    }
}
