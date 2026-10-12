/**
 * Class Person merepresentasikan model data entitas untuk seorang personel / tenaga medis rumah sakit.
 * Menyimpan data berupa ID, Nama, Tahun Lahir, Kategori Profesi,
 * serta Pengalaman Kerja (sebagai atribut baru komponen bonus JSlider).
 */
public class Person {
    // Atribut untuk menyimpan data personel
    private String id;
    private String nama;
    private int tahunLahir;
    private String kategori;
    private int pengalamanKerja; // Atribut Baru (Bonus +20 - Komponen JSlider)

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    // Constructor default (tanpa parameter)
    public Person() {
    }

    // Constructor berparameter lengkap untuk menginisialisasi seluruh nilai atribut saat objek dibuat
    public Person(String id, String nama, int tahunLahir, String kategori, int pengalamanKerja) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.kategori = kategori;
        this.pengalamanKerja = pengalamanKerja;
    }

    // ==========================================
    // GETTER & SETTER (PER BLOK ATRIBUT)
    // ==========================================

    // Getter untuk mengambil nilai ID
    public String getId() {
        return id;
    }

    // Setter untuk mengatur / mengubah nilai ID
    public void setId(String id) {
        this.id = id;
    }

    // Getter untuk mengambil nilai Nama
    public String getNama() {
        return nama;
    }

    // Setter untuk mengatur / mengubah nilai Nama
    public void setNama(String nama) {
        this.nama = nama;
    }

    // Getter untuk mengambil nilai Tahun Lahir
    public int getTahunLahir() {
        return tahunLahir;
    }

    // Setter untuk mengatur / mengubah nilai Tahun Lahir
    public void setTahunLahir(int tahunLahir) {
        this.tahunLahir = tahunLahir;
    }

    // Getter untuk mengambil nilai Kategori
    public String getKategori() {
        return kategori;
    }

    // Setter untuk mengatur / mengubah nilai Kategori
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    // Getter untuk mengambil nilai Pengalaman Kerja (dalam tahun)
    public int getPengalamanKerja() {
        return pengalamanKerja;
    }

    // Setter untuk mengatur / mengubah nilai Pengalaman Kerja (dalam tahun)
    public void setPengalamanKerja(int pengalamanKerja) {
        this.pengalamanKerja = pengalamanKerja;
    }
}
