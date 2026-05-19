# Kullanılan Tasarım Örüntüleri

---

## Faz 1 — Creational Patterns

### Factory Method Pattern

**Nerede:** `GameObjectFactory.java`

**Neden:** God Class olan `GameObject` tüm nesne tiplerini tek sınıfta yönetiyordu. Nesne yaratma if-else zincirleriyle yapılıyordu.

**Kazanım:** Her nesne tipi kendi alt sınıfında (Player, Enemy, Collectible, Obstacle). Yaratma mantığı `GameObjectFactory`'de merkezileştirildi. Yeni tip eklemek için sadece yeni sınıf + factory metodu yeterli.

---

## Faz 2 — Structural Patterns

### Decorator Pattern

**Nerede:** `GameObjectDecorator.java`, `ArmorDecorator.java`, `SpeedBoostDecorator.java`, `DamageBoostDecorator.java`

**Neden:** Oyun nesnelerine mevcut sınıfları değiştirmeden dinamik yetenek eklemek gerekiyordu.

**Kazanım:**
- Zırh, hız artışı, hasar artışı runtime'da ekleniyor
- Decorator zinciri: `Player → Armor → Speed → Damage`
- OCP uyumu — yeni decorator sınıfı yazmak yeterli

### Facade Pattern

**Nerede:** `GameEngine.java`

**Neden:** Main sınıfı GameManager, Renderer ve Factory ile doğrudan etkileşiyordu.

**Kazanım:**
- `engine.addPlayer()`, `engine.run()` gibi basit API
- Alt sistem karmaşıklığı gizlendi
- Decorator ve Observer yönetimi de facade üzerinden

---

## Faz 3 — Behavioral Patterns

### Strategy Pattern

**Nerede:** `MovementStrategy.java` (arayüz), `RandomMovement.java`, `AggressiveMovement.java`, `DefensiveMovement.java`, `BossMovement.java`, `PatrolMovement.java`

**Neden:** `Enemy.update()` içindeki if-else zinciri her düşman tipi için farklı hareket mantığı içeriyordu. Yeni hareket tipi eklemek tüm kodu değiştirmeyi gerektiriyordu.

**Kazanım:**
1. Her hareket algoritması kendi sınıfında kapsüllendi
2. Runtime'da strateji değiştirilebiliyor: `enemy.setMovementStrategy(new PatrolMovement(3))`
3. **OCP gösterimi:** `PatrolMovement` mevcut hiçbir dosyayı değiştirmeden eklendi

**Önce (if-else):**
```java
// Enemy.update() — Faz 0
if (enemyType.equals("BASIC")) {
    x += (int)(Math.random() * 3) - 1;
} else if (enemyType.equals("FAST")) {
    x += (int)(Math.random() * 5) - 2;
} else if (enemyType.equals("TANK")) {
    x += (int)(Math.random() * 2) - 1;
} else if (enemyType.equals("BOSS")) { ... }
```

**Sonra (Strategy):**
```java
// Enemy.update() — Faz 3
movementStrategy.move(this); // Tek satır, if-else yok
```

### Observer Pattern

**Nerede:** `GameEventListener.java` (arayüz), `EventManager.java` (subject), `GameEvent.java`, `ScoreListener.java`, `LogListener.java`

**Neden:** Skor hesaplama, loglama gibi yan etkiler `GameManager.cleanupDead()` içine gömülüydü. Yeni bir yan etki eklemek mevcut kodu değiştirmeyi gerektiriyordu.

**Kazanım:**
1. Olay yayınla/dinle sistemi — gevşek bağımlılık
2. Yeni listener eklemek mevcut kodu değiştirmez (OCP)
3. Belirli olay tiplerine abone olma desteği
4. Global listener ile tüm olayları yakalama

**Örnek:**
```java
// Listener ekle (Main.java)
engine.addEventListener(GameEvent.EventType.ENEMY_KILLED, scoreListener);
engine.addGlobalEventListener(logListener);

// Olay yayınla (GameManager.java)
eventManager.publish(new GameEvent(ENEMY_KILLED, "Goblin yok edildi!", player, enemy, 10));
```
