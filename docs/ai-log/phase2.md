# Faz 2 — AI Günlüğü (Structural Patterns)

## AI Aracı
**Kullanılan AI:** Claude (Antigravity — pair programming modu)

---

## Oturum 1: Örüntü Seçimi Tartışması

### AI'a ne soruldu (prompt):
"Adapter pattern burada uygun mu, yoksa Decorator veya Facade mı? Farkını açıkla. Mini oyun motorumda hangi Structural pattern'leri kullanmalıyım?"

### AI ne yanıtladı (özet):
AI üç pattern'i karşılaştırdı:

| Pattern | Kullanım Amacı | Bu Proje İçin |
|---------|---------------|---------------|
| **Adapter** | İki uyumsuz arayüzü birleştirme | ❌ Alt sistemler zaten uyumlu |
| **Decorator** | Var olan nesneye dinamik özellik ekleme | ✅ Oyun nesnelerine buff/debuff sistemi |
| **Facade** | Karmaşık alt sistemi basit arayüzle sunma | ✅ GameManager+Renderer+Factory birleştirme |

AI, Composite pattern'i de önerdi (düşman grupları için) ancak şu an tek tek düşmanlar yeterli olduğu için bunu reddettim.

### Benim kararım:
- **Decorator** seçtim çünkü oyun nesnelerine runtime'da yetenek ekleme doğal bir ihtiyaç
- **Facade** seçtim çünkü Main sınıfı üç alt sisteme doğrudan erişiyordu
- **Adapter** reddettim çünkü arayüz uyumsuzluğu problemi yok

---

## Oturum 2: Decorator Uygulama Tartışması

### AI'a ne soruldu:
"Decorator pattern'de takeDamage() metodunu override ettiğimde, constructor'daki health değeri mi yoksa wrappedObject'in health'i mi kullanılacak?"

### AI ne yanıtladı:
AI, decorator'ın constructor'da parent sınıfa health değerini kopyaladığını ama tüm getter'ların wrappedObject'e delege edilmesi gerektiğini söyledi. Aksi halde iki farklı health değeri oluşur (parent'taki ve wrappedObject'teki) — bu bir bug kaynağı olur.

### AI'ın yanlış/eksik önerdiği bir şey:
AI başta Decorator'ın `update()` metodunda decorator'a özgü bilgiyi yazdırırken `System.out.println` kullanılmasının Renderer ile çakışabileceğini fark etmedi. Ben bunu fark ettim ve decorator'ın update çıktısını girintili (`  🛡`) yazdırarak ana nesnenin update çıktısından ayırdım. AI bu görsel ayrım detayını atlıyordu — doğrudan fonksiyonel sonuca odaklanıyordu.

---

## Oturum 3: Facade Kapsam Tartışması

### AI'a ne soruldu:
"GameEngine facade'ı alt sistemlere doğrudan erişim vermeli mi, yoksa tamamen gizlemeli mi?"

### AI ne yanıtladı:
AI iki yaklaşımı sundu:
1. **Opaque Facade** — Alt sistemlere hiç erişim yok (daha sıkı kapsülleme)
2. **Transparent Facade** — `getGameManager()` gibi getter'lar ile ihtiyaç halinde erişim

AI, Opaque yaklaşımı önerdi.

### Benim kararım:
**Transparent Facade** tercih ettim çünkü bazı ileri düzey senaryolarda (özel render, doğrudan nesne manipülasyonu) alt sistemlere erişim gerekebilir. Ancak `getGameManager()` metodunu sadece "gerektiğinde" kullanılmak üzere API'nin alt kısmına yerleştirdim. Ana kullanım hâlâ facade metotları üzerinden.

---

## Özet Tablo
| Konu | AI Önerisi | Benim Kararım |
|------|-----------|----------------|
| Decorator vs Adapter | Decorator uygun | ✅ Aynı fikirdeyim |
| Facade vs Adapter | Facade uygun | ✅ Aynı fikirdeyim |
| Composite (düşman grupları) | Eklenebilir | ❌ Şu an gerekli değil |
| Opaque vs Transparent Facade | Opaque önerdi | ⚠️ Transparent tercih ettim |
| Decorator health bug riski | Tüm getter'ları delege et | ✅ Uyguladım |
| Update çıktı formatlama | Atladı | 🔍 Ben fark ettim ve düzelttim |
