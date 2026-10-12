public class Person {
    private String id;
    private String nama;
    private int tahunLahir;
    private String kategori;
    private int pengalamanKerja; // Atribut Baru (Bonus +20 - Komponen JSlider)

    // Default Constructor
    public Person() {
    }

    // Parameterized Constructor
    public Person(String id, String nama, int tahunLahir, String kategori, int pengalamanKerja) {
        this.id = id;
        this.nama = nama;
        this.tahunLahir = tahunLahir;
        this.kategori = kategori;
        this.pengalamanKerja = pengalamanKerja;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getTahunLahir() {
        return tahunLahir;
    }

    public void setTahunLahir(int tahunLahir) {
        this.tahunLahir = tahunLahir;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getPengalamanKerja() {
        return pengalamanKerja;
    }

    public void setPengalamanKerja(int pengalamanKerja) {
        this.pengalamanKerja = pengalamanKerja;
    }
}
