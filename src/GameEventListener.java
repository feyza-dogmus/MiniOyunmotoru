/**
 * GameEventListener - Observer Pattern arayüzü.
 * 
 * Oyun olaylarını dinleyen tüm sınıflar bu arayüzü implemente eder.
 * Yeni bir listener eklemek mevcut kodu değiştirmez — OCP.
 */
public interface GameEventListener {

    /**
     * Bir oyun olayı gerçekleştiğinde çağrılır.
     */
    void onEvent(GameEvent event);
}
