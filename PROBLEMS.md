# PROBLEMS.md — Başlangıç Kodunun Tasarım Sorunları Analizi

## Faz 0: Kod Analizi

Bu belge, Mini Oyun Motoru'nun başlangıç kodundaki tasarım sorunlarını listeler ve AI analizi ile karşılaştırır.

---

## 🔍 Benim Tespit Ettiğim Sorunlar

### Sorun 1: God Class — GameObject Her Şeyi Yapıyor
`GameObject` sınıfı oyuncu, düşman, toplanabilir nesne ve engel olmak üzere dört farklı nesne tipini tek bir sınıfta yönetiyor. Her tipin kendine özgü alanları var (`enemyType`, `itemEffect`, `durability`) ama hepsi aynı sınıfta tutuluyor. Bir oyuncu nesnesinin `enemyType` alanına sahip olması anlamsız ve kafa karıştırıcı.

### Sorun 2: if-else Zincirleri — Tip Kontrolü Her Yerde
`update()`, `onCollision()`, `render()` gibi temel metotlarda nesne tipi `String` karşılaştırması ile kontrol ediliyor. Yeni bir nesne tipi (örneğin "NPC" veya "PROJECTILE") eklemek istediğimizde tüm dosyalardaki tüm if-else bloklarını bulmamız ve güncellememiz gerekiyor. Bu Open/Closed Principle'ın (OCP) açık ihlalidir.

### Sorun 3: Nesne Yaratma Sorumluluğu Dağınık
`GameManager` sınıfında `createObject()`, `createEnemy()`, `createCollectible()` gibi ayrı metotlar var. Her biri kendi içinde yine if-else zincirleri ile nesne özelliklerini ayarlıyor. Nesne yaratma mantığı merkezileştirilmemiş ve tekrar ediyor.

### Sorun 4: Sıkı Bağımlılık (Tight Coupling)
`Renderer` sınıfı `GameObject`'in iç yapısına (type, enemyType, itemEffect) doğrudan bağımlı. `GameManager` da aynı şekilde `GameObject`'in her detayını biliyor. Bir değişiklik yapmak istediğimde üç dosyayı birden değiştirmek zorundayım.

### Sorun 5: DRY İhlali — Tekrarlanan Tip Kontrol Kodları
Tip kontrolü (`type.equals("PLAYER")`, `type.equals("ENEMY")`, vb.) `GameObject.java`, `GameManager.java` ve `Renderer.java` dosyalarında aynı şekilde tekrarlanıyor. Aynı mantık en az 8-10 farklı yerde kopyala-yapıştır edilmiş durumda.

### Sorun 6: String Tabanlı Tip Sistemi — Tip Güvenliği Yok
Nesne tipleri `"PLAYER"`, `"ENEMY"` gibi String değerlerle temsil ediliyor. Bir yazım hatası (örneğin `"PLYER"`) derleme zamanında yakalanamaz, runtime'da sessizce hatalı çalışır.

### Sorun 7: Single Responsibility Principle (SRP) İhlali
`GameManager` hem nesne yaratıyor, hem oyun döngüsünü yönetiyor, hem çarpışma kontrolü yapıyor, hem ölü nesneleri temizliyor, hem de skor hesaplıyor. Tek bir sınıf çok fazla sorumluluk üstlenmiş.

---

## 🤖 AI Analizi

**Kullanılan AI:** Claude (Antigravity)
**Sorulan Prompt:** "Bu kodda hangi tasarım sorunlarını görüyorsun? Hangi tasarım örüntüleri bu sorunları çözebilir? Her sorun için kısa bir açıklama yaz."

### AI'ın Tespit Ettiği Sorunlar ve Önerdiği Örüntüler:

| # | Sorun | Önerilen Örüntü |
|---|-------|-----------------|
| 1 | **God Class**: `GameObject` tüm nesne tiplerini tek sınıfta yönetiyor. Oyuncu, düşman, item, engel alanları birbirine karışmış. | **Factory Method / Abstract Factory**: Her nesne tipi için ayrı sınıf hiyerarşisi oluşturulmalı. |
| 2 | **if-else zincirleri**: `update()`, `onCollision()`, `renderObject()` gibi metotlarda her tip için ayrı if-else blokları var. Yeni tip eklemek tüm dosyaları etkiler. | **Strategy Pattern**: Her nesne tipinin davranışı (hareket, çarpışma, render) ayrı strategy nesnelerine delege edilmeli. |
| 3 | **Nesne yaratma karmaşıklığı**: `createObject()`, `createEnemy()`, `createCollectible()` metotları farklı parametrelerle benzer iş yapıyor. | **Builder Pattern**: Karmaşık nesne yaratma adımlarını bir builder ile yönetmek, parametrelerin doğru sırada ve tipte verilmesini sağlar. |
| 4 | **Sıkı bağımlılık (Tight Coupling)**: `Renderer` doğrudan `GameObject`'in getter'larına ve `GameManager`'ın listesine erişiyor. | **Observer Pattern / Mediator**: Nesneler birbirlerini soyutlama katmanı üzerinden tanımalı. Ayrıca **Facade** ile renderer'ın karmaşıklığı gizlenebilir. |
| 5 | **Davranış farklılıkları statik**: Düşman tipleri (BASIC, FAST, TANK, BOSS) `setEnemyType()` ile ayarlanıyor ama davranış değişikliği if-else ile yapılıyor. Runtime'da davranış değiştirilemez. | **State Pattern / Strategy Pattern**: Düşman davranışları runtime'da değiştirilebilir strategy/state nesneleri ile temsil edilmeli. |
| 6 | **Çarpışma yönetimi dağınık**: Çarpışma mantığı `GameObject.onCollision()` içinde her tip çifti için if-else ile yazılmış. Yeni tip eklendiğinde tüm kombinasyonlar güncellenmeli. | **Visitor Pattern / Chain of Responsibility**: Çarpışma mantığı double dispatch veya chain of responsibility ile yönetilmeli. |
| 7 | **Genişletilebilirlik yok**: Yeni bir özellik (örneğin ses efektleri, parçacık sistemi) eklemek mevcut kodun derinlemesine değiştirilmesini gerektiriyor. | **Decorator Pattern**: Nesnelere dinamik olarak yeni özellikler eklemek için decorator yapısı kullanılmalı. |
| 8 | **String tabanlı tip kontrolü**: Enum yerine String kullanılması tip güvenliğini ortadan kaldırıyor. | **Enum + Polymorphism**: String sabitler yerine enum ve polimorfizm kullanılmalı. |

---

## 🔄 Karşılaştırma: Ben vs AI

### Ortak Tespitler
Hem ben hem de AI şu sorunları tespit ettik:
- **God Class** sorunu (GameObject çok fazla sorumluluk taşıyor)
- **if-else zincirleri** ve bunun genişletilebilirliği engellemesi
- **Sıkı bağımlılık** (Renderer ↔ GameObject ↔ GameManager)
- **Nesne yaratma karmaşıklığı**
- **DRY ihlali** (tekrarlanan tip kontrolleri)

### AI'ın Ek Tespitleri
- AI, **Visitor Pattern** ve **Chain of Responsibility** gibi daha ileri düzey örüntüleri önerdi — bu benim ilk analizimde düşünmediğim çözümlerdi
- AI, **Decorator Pattern** ile dinamik özellik eklemeyi önerdi — ben bu açıdan bakmamıştım
- AI, **State Pattern**'i düşman davranışları için önerdi — ben sadece "if-else kötü" demiştim ama çözüm yönünü bu kadar somutlaştıramamıştım

### Benim Ek Tespitlerim
- Ben **SRP ihlalini** GameManager özelinde daha detaylı analiz ettim — AI daha çok GameObject'e odaklandı
- Ben **String tabanlı tip sisteminin** yazım hatası riskini vurguladım — AI de bunu gördü ama ben runtime hata senaryosunu daha somut ifade ettim

### Sonuç
AI'ın önerileri genel olarak doğru ve kapsamlı. Ancak AI, her soruna hemen bir örüntü atama eğiliminde — bazen basit bir refactoring (enum kullanmak gibi) yeterli olabilir. Örüntüleri uygulamadan önce gerçekten gerekli olup olmadığını değerlendirmek önemli.
