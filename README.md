# 🎮 Mini Oyun Motoru

## Konu Seçimi: C — Mini Oyun Motoru

Bu projeyi seçmemin nedeni, oyun motorlarının nesne yönetimi, davranış farklılıkları ve genişletilebilirlik gibi tasarım sorunlarını en somut biçimde ortaya koymasıdır. Oyun nesneleri (oyuncu, düşman, toplanabilir nesne) arasındaki davranış farklarının if-else zincirleriyle yönetilmesi, tasarım örüntülerinin neden gerekli olduğunu doğrudan hissettiren bir senaryodur.

## Proje Açıklaması

Konsol tabanlı 2D mini oyun motoru. Oyuncu, düşman, toplanabilir nesne ve engelleri yönetir. Proje, yazılım tasarım örüntülerini aşamalı olarak uygulayarak sıfırdan evrimleşmiştir.

## Kullanılan Tasarım Örüntüleri

| Faz | Örüntü | Tür | Açıklama |
|-----|--------|-----|----------|
| 1 | **Factory Method** | Creational | Nesne yaratma sorumluluğunu merkezileştirdi. God Class parçalandı. |
| 2 | **Decorator** | Structural | Oyun nesnelerine dinamik yetenekler (zırh, hız, hasar) ekliyor. |
| 2 | **Facade** | Structural | GameEngine ile alt sistem karmaşıklığını gizliyor. |
| 3 | **Strategy** | Behavioral | Düşman hareket algoritmalarını kapsülleyerek değiştirilebilir kılıyor. |
| 3 | **Observer** | Behavioral | Olay sistemi ile skor, loglama gibi yan etkileri gevşek bağımlı yapıyor. |

## Mimari Diyagram

```
                        ┌───────────┐
                        │   Main    │
                        └─────┬─────┘
                              │
                    ┌─────────▼──────────┐
                    │   GameEngine       │
                    │   (FACADE)         │
                    └──┬──────┬───────┬──┘
                       │      │       │
            ┌──────────▼┐ ┌──▼────┐ ┌▼──────────────┐
            │GameManager│ │Render │ │GameObjectFactory│
            │           │ └───────┘ └────────────────┘
            │ EventMgr  │ (FACTORY METHOD)
            │(OBSERVER) │
            └─────┬─────┘
                  │ yönetir
        ┌─────────▼──────────┐
        │   GameObject       │
        │   (abstract)       │
        └────────┬───────────┘
                 │ extends
   ┌─────┬───────┼────────┬───────────┐
   ▼     ▼       ▼        ▼           ▼
Player Enemy  Collect.  Obstacle  Decorator
         │                          (DECORATOR)
         │ uses                    ┌────┼─────┐
         ▼                       Armor Speed Damage
   MovementStrategy
   (STRATEGY)
   ┌───┬───┬────┬────┐
   ▼   ▼   ▼    ▼    ▼
 Rand Aggr Def Boss Patrol

   GameEventListener ◄── ScoreListener
   (OBSERVER)        ◄── LogListener
```

## Nasıl Çalıştırılır

```bash
cd src
javac *.java
java Main
```

## Proje Yapısı

```
├── README.md                   ← Bu dosya
├── PATTERNS.md                ← Tüm örüntülerin belgelenmesi
├── PROBLEMS.md                ← Başlangıç kodu analizi (Faz 0)
├── src/                       ← Kaynak kod
│   ├── Main.java
│   ├── GameObject.java         (abstract base)
│   ├── Player.java
│   ├── Enemy.java
│   ├── Collectible.java
│   ├── Obstacle.java
│   ├── GameManager.java
│   ├── GameObjectFactory.java  (Factory Method)
│   ├── GameEngine.java         (Facade)
│   ├── Renderer.java
│   ├── GameObjectDecorator.java (Decorator base)
│   ├── ArmorDecorator.java
│   ├── SpeedBoostDecorator.java
│   ├── DamageBoostDecorator.java
│   ├── MovementStrategy.java   (Strategy interface)
│   ├── RandomMovement.java
│   ├── AggressiveMovement.java
│   ├── DefensiveMovement.java
│   ├── BossMovement.java
│   ├── PatrolMovement.java     (OCP demo)
│   ├── GameEvent.java          (Observer event)
│   ├── GameEventListener.java  (Observer interface)
│   ├── EventManager.java       (Observer subject)
│   ├── ScoreListener.java
│   └── LogListener.java
├── docs/
│   ├── diagrams/               ← UML diyagramları
│   └── ai-log/
│       ├── phase1.md
│       ├── phase2.md
│       └── phase3.md
└── .github/workflows/ci.yml   ← GitHub Actions CI
```

## Branch Yapısı

- `main` → Temiz, merge edilmiş son durum
- `phase-1` → Creational (Factory Method)
- `phase-2` → Structural (Decorator + Facade)
- `phase-3` → Behavioral (Strategy + Observer)
