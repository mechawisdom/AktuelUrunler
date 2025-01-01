# Aktüel Ürünler

**Aktüel Ürünler**, Türkiye'deki marketlerin aktüel ürün kataloglarını kullanıcıların kolayca incelemesi ve günlük alışveriş listesi oluşturulup takip edilmesi için tasarlanmış bir mobil uygulama prototipidir. 
Backend kısmı eksik olduğu için proje tamamlanamamıştır. Veriler mevcut bir API olmadığı için sitelerden veri çekme (web scraping) yöntemiyle alınması planlanmıştır. 
Ancak bu süreç otomatize/yarı otomatize edilemediği için proje geliştirilmesi durdurulmuştur.



![download](https://github.com/user-attachments/assets/7208d34d-7db5-4d0e-95b7-72eab0b5f3e8)


---

## Özellikler
- Kullanıcı dostu arayüz (Material Design)
- Görseller için **Zoom** ve **Lottie Animasyonları**
- Asenkron veri işlemleri için **Coroutines** ve **WorkManager**
- Yerel veri saklama için **Room**
- Modern mimari: **Hilt** ile Dependency Injection
- Responsive layout ve gezinme için **Navigation Component**

---

## Kullanılan Teknolojiler ve Kütüphaneler
- **Programlama Dili**: Kotlin  
- **Arayüz**: ViewBinding, Material Design, RecyclerView, ConstraintLayout  
- **Veritabanı**: Room  
- **Dependency Injection**: Hilt  
- **Ağ İşlemleri**: Retrofit (Gson dönüştürücü ile), OkHttp (Loglama desteği ile)  
- **Asenkron İşlemler**: Coroutines, WorkManager  
- **Navigasyon**: Navigation Component
- **Görseller**: Glide, RoundedImageView, Lottie Animations  
- **Ekstra Özellikler**: LikeButton, ZoomLayout  

---

## Eksiklikler ve Gelecek Geliştirme Planları
- **Backend Geliştirme**:  
  Verilerin otomatize bir sistemle alınması ve API oluşturulması gerekiyor.
- **Scraping ve Veri İşleme**:  
  Verilerin çeşitli sitelerden alınması ve filtrelenmesi için bir web scraping çözümü eklenmeli.
- **Daha İyi UI/UX Tasarımı**:  
  Daha modern ve kullanıcı dostu bir tasarım için iyileştirmeler yapılabilir.
