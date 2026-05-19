/**
 * MovementStrategy - Strategy Pattern arayüzü.
 * 
 * Farklı hareket algoritmalarını kapsüller.
 * Yeni bir hareket stratejisi eklemek için bu arayüzü implemente etmek yeterli.
 * Mevcut kod değiştirilmez — Açık/Kapalı Prensibi (OCP).
 */
public interface MovementStrategy {

    /**
     * Nesnenin hareketini hesapla ve uygula.
     * @param obj Hareket edecek nesne
     */
    void move(GameObject obj);

    /**
     * Stratejinin adını döndürür (debug/log için).
     */
    String getStrategyName();
}
