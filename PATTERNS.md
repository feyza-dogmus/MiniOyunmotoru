# Kullanılan Tasarım Örüntüleri

## Faz 1 — Creational Patterns

### Factory Method Pattern

**Nerede uygulandı:** `GameObjectFactory.java`

**Neden uygulandı:**
Başlangıç kodunda nesne yaratma işlemi if-else zincirleriyle yönetiliyordu. Factory Method ile nesne yaratma merkezileştirildi.

**Ne kazandık:**
1. God Class çözüldü — her nesne tipi kendi alt sınıfında
2. Nesne yaratma merkezileşti
3. Polimorfizm ile if-else zincirleri ortadan kalktı
4. Yeni tip eklemek için sadece alt sınıf + factory metodu yeterli

---

## Faz 2 — Structural Patterns

### Decorator Pattern

**Nerede uygulandı:** `GameObjectDecorator.java`, `ArmorDecorator.java`, `SpeedBoostDecorator.java`, `DamageBoostDecorator.java`

**Neden uygulandı:**
Oyun nesnelerine dinamik olarak yeni yetenekler eklemek gerekiyordu (zırh, hız artışı, hasar artışı). Mevcut sınıfları değiştirmeden (OCP) bu yetenekleri eklemek için Decorator pattern kullanıldı.

**Neden Decorator, Adapter değil?**
- Adapter mevcut bir arayüzü başka bir arayüze uyarlar — bizim amacımız arayüz uyarlama değil, yeni davranış ekleme.
- Decorator aynı arayüzü koruyarak üstüne yeni özellik ekler — tam ihtiyacımız olan şey.

**Ne kazandık:**
1. **Dinamik yetenek sistemi** — Runtime'da nesneye zırh, hız, hasar ekleniyor
2. **Decorator zinciri** — Birden fazla yetenek üst üste ekleniyor (Armor → Speed → Damage)
3. **OCP uyumu** — Yeni yetenek eklemek için yeni decorator yazılır, mevcut kod değişmez
4. **Esneklik** — Geçici (SpeedBoost, süreli) ve kalıcı (DamageBoost) yetenekler destekleniyor

**Decorator Zinciri Örneği:**
```
Player → ArmorDecorator → SpeedBoostDecorator → DamageBoostDecorator
  hız=5        hız=5              hız=5+3=8              hız=8
  dmg=10       dmg=10             dmg=10                 dmg=10+8=18
  armor=0      armor=30           armor=30               armor=30
```

### Facade Pattern

**Nerede uygulandı:** `GameEngine.java`

**Neden uygulandı:**
Main sınıfı GameManager, Renderer ve GameObjectFactory ile doğrudan etkileşiyordu. Bu üç alt sistemi tek bir basit arayüz arkasında gizlemek için Facade pattern kullanıldı.

**Neden Facade, Adapter değil?**
- Adapter iki uyumsuz arayüzü birleştirmek içindir — bizim alt sistemlerimiz zaten uyumlu, sadece karmaşık.
- Facade karmaşıklığı gizleyerek basit bir API sunar — tam ihtiyacımız olan şey.

**Ne kazandık:**
1. **Basit API** — `engine.addPlayer()`, `engine.run()` gibi tek satır çağrılar
2. **Bağımlılık azaltma** — Main artık GameManager/Renderer detaylarını bilmiyor
3. **Hızlı kurulum** — `quickSetup()` ile tek çağrıda hazır oyun
4. **Decorator entegrasyonu** — `addArmor()`, `addSpeedBoost()` facade üzerinden

---

### Faz 2 Mimari Diyagram

```
                    ┌──────────────────────┐
                    │      Main            │
                    └──────────┬───────────┘
                               │ kullanır
                    ┌──────────▼───────────┐
                    │    GameEngine         │
                    │    (FACADE)           │
                    │                      │
                    │ + addPlayer()         │
                    │ + addEnemy()          │
                    │ + addArmor()          │
                    │ + renderAll()         │
                    │ + run()               │
                    └──┬───────┬────────┬───┘
                       │       │        │
            ┌──────────▼┐  ┌──▼─────┐  ┌▼──────────────┐
            │GameManager│  │Renderer│  │GameObjectFactory│
            └──────┬────┘  └────────┘  └────────────────┘
                   │ yönetir
         ┌─────────▼──────────┐
         │    GameObject      │
         │    (abstract)      │
         └────────┬───────────┘
                  │ extends
    ┌─────┬───────┼───────┬──────────┐
    ▼     ▼       ▼       ▼          ▼
 Player Enemy Collectible Obstacle  GameObjectDecorator
                                     (DECORATOR base)
                                         │ extends
                               ┌─────────┼──────────┐
                               ▼         ▼          ▼
                         ArmorDeco  SpeedBoost  DamageBoost
```
