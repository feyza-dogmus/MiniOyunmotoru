# Faz 1 — AI Günlüğü (Creational Patterns)

## AI Aracı
**Kullanılan AI:** Claude (Antigravity — pair programming modu)

---

## Oturum 1: Tasarım Analizi

### AI'a ne soruldu (prompt):
"Bu kodda hangi tasarım sorunlarını görüyorsun? Hangi tasarım örüntüleri bu sorunları çözebilir?"

### AI ne yanıtladı (özet):
AI, başlangıç kodunda şu sorunları tespit etti:
1. **God Class** — `GameObject` tüm tipleri tek sınıfta yönetiyor
2. **if-else zincirleri** — Nesne tipine göre davranış farklılaştırması
3. **Nesne yaratma karmaşıklığı** — `GameManager` içinde if-else ile nesne yaratma
4. **Sıkı bağımlılık** — Renderer, GameManager ve GameObject arasında

AI, **Factory Method** ve **Abstract Factory** pattern'lerini önerdi. Ayrıca **Builder Pattern**'i karmaşık düşman yaratımı için (farklı stat'lar, tipler) önerdi.

### Ne uygulandım ve neden:
- **Factory Method** uyguladım çünkü nesne yaratma sorumluluğunu merkezileştirmek en acil ihtiyaçtı.
- **Abstract Factory** uygulamadım çünkü şu an tek bir oyun teması var; farklı tema aileleri (örneğin uzay, orta çağ) olsaydı Abstract Factory daha uygun olurdu.
- **Builder Pattern** uygulamadım çünkü düşman yaratımı henüz o kadar karmaşık değil; `setEnemyType()` ile yeterince yönetilebiliyor. Ancak ileride düşman yapılandırması daha karmaşık hale gelirse Builder eklenebilir.

---

## Oturum 2: Kod Review

### AI'a ne soruldu:
"Factory Method uygulamam doğru mu? Eksik veya hatalı bir şey var mı?"

### AI ne yanıtladı:
AI şu geri bildirimleri verdi:
- ✅ God Class başarıyla parçalandı
- ✅ Factory Method doğru uygulanmış
- ⚠️ `Enemy.update()` ve `Enemy.setEnemyType()` içinde hâlâ if-else zincirleri var — bunlar Faz 3'te Strategy Pattern ile çözülebilir
- ⚠️ `Renderer` hâlâ `instanceof` kontrolleri yapıyor — Faz 2'de Visitor veya polymorphic render ile çözülebilir
- ⚠️ `Collectible.applyEffect()` içinde if-else var — Faz 3'te Strategy ile çözülebilir

### Değerlendirme:
AI'ın uyarıları yerinde. Ancak Faz 1'in kapsamı sadece Creational pattern'ler olduğu için, bu sorunları Faz 2 (Structural) ve Faz 3 (Behavioral) için not ettim. Tüm sorunları tek fazda çözmeye çalışmak, fazların amacına aykırı olurdu.

---

## Özet
| Konu | AI Önerisi | Benim Kararım |
|------|-----------|----------------|
| God Class | Factory Method + alt sınıflar | ✅ Uyguladım |
| Abstract Factory | Farklı tema aileleri için | ❌ Şu an gerekli değil |
| Builder Pattern | Karmaşık düşman yaratımı | ❌ Şu an yeterince basit |
| Düşman AI if-else | Strategy Pattern | 📋 Faz 3'e bıraktım |
| Renderer instanceof | Visitor/Polymorphism | 📋 Faz 2'ye bıraktım |
