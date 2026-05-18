# Kullanılan Tasarım Örüntüleri

## Faz 1 — Creational Patterns

### Factory Method Pattern

**Nerede uygulandı:** `GameObjectFactory.java`

**Neden uygulandı:**
Başlangıç kodunda (`GameManager.createObject()`) nesne yaratma işlemi if-else zincirleriyle yönetiliyordu. Her nesne tipi için farklı parametreler ve başlangıç değerleri aynı metot içinde belirleniyor, yeni bir tip eklemek mevcut kodu kırmayı gerektiriyordu.

**Ne kazandık:**
1. **God Class çözüldü:** `GameObject` artık abstract bir sınıf; `Player`, `Enemy`, `Collectible`, `Obstacle` kendi alt sınıflarında tanımlı.
2. **Nesne yaratma merkezileşti:** `GameObjectFactory` tüm yaratma mantığını tek bir yerde tutuyor.
3. **Polimorfizm:** `update()`, `onCollision()` gibi metotlar artık her alt sınıfta kendi davranışını tanımlıyor — if-else zincirleri ortadan kalktı.
4. **Genişletilebilirlik:** Yeni bir nesne tipi eklemek için sadece yeni bir alt sınıf ve factory'ye bir metot eklemek yeterli.

**Önce/Sonra karşılaştırması:**

### Önce (Faz 0):
```
┌───────────────────────────────────┐
│          GameObject               │
│  (God Class)                      │
│                                   │
│  - type: String                   │
│  - enemyType: String              │
│  - itemEffect: String             │
│  - durability: int                │
│  ...tüm alanlar tek sınıfta...   │
│                                   │
│  + update()     // if-else        │
│  + onCollision() // if-else       │
│  + move()        // if type=PLAYER│
│  + setEnemyType() // if type=ENEMY│
└───────────────────────────────────┘
         ↕ doğrudan bağımlılık
┌───────────────────────────────────┐
│        GameManager                │
│  + createObject() // if-else      │
│  + createEnemy()  // if-else      │
│  + cleanupDead()  // if-else      │
└───────────────────────────────────┘
```

### Sonra (Faz 1):
```
              ┌──────────────────┐
              │   GameObject     │
              │   (abstract)     │
              │                  │
              │ + update()*      │
              │ + onCollision()* │
              │ + getType()*     │
              └────────┬─────────┘
                       │ extends
        ┌──────┬───────┼───────┬──────────┐
        ▼      ▼       ▼       ▼          │
   ┌────────┐┌──────┐┌───────────┐┌──────────┐
   │ Player ││Enemy ││Collectible││Obstacle  │
   │        ││      ││           ││          │
   │+move() ││+setE.││+applyEff()││+hitBy()  │
   │+score  ││+eType││+effect    ││+durabil. │
   └────────┘└──────┘└───────────┘└──────────┘
                       ▲
                       │ creates
              ┌────────────────────┐
              │ GameObjectFactory  │
              │                    │
              │ +createPlayer()    │
              │ +createEnemy()     │
              │ +createCollectible()│
              │ +createObstacle()  │
              └────────────────────┘
```
