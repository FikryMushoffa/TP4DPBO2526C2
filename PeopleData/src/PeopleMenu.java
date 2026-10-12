import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Year;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunField;
    private JComboBox<String> kategoriComboBox;
    private JSlider pengalamanSlider;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JTable personTable;
    private JLabel titleLabel;

    private ArrayList<Person> listPerson;
    private int selectedIndex = -1;

    // 1. main()
    public static void main(String[] args) {
        // Buat object window
        PeopleMenu menu = new PeopleMenu();

        // Atur ukuran window
        menu.setSize(750, 650);

        // Letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // Isi window dengan mainPanel
        menu.setContentPane(menu.mainPanel);

        // Ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // Tampilkan window
        menu.setVisible(true);

        // Agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // 2. PeopleMenu() (Constructor)
    public PeopleMenu() {
        // Inisialisasi listPerson
        listPerson = new ArrayList<>();

        // Isi listPerson dengan data awal (populateList)
        populateList();

        // Isi tabel personel & terapkan styling tabel
        refreshTable();

        // Ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // Atur isi combo box kategori
        String[] kategoriData = { "???", "Dokter Spesialis", "Dokter Umum", "Perawat (Nurse)", "Apoteker", "Tenaga Laboratorium" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Konfigurasi komponen tambahan JSlider (Pengalaman Kerja dalam Tahun: 0 - 30)
        pengalamanSlider.setMinimum(0);
        pengalamanSlider.setMaximum(30);
        pengalamanSlider.setValue(0);
        pengalamanSlider.setMajorTickSpacing(5);
        pengalamanSlider.setMinorTickSpacing(1);
        pengalamanSlider.setPaintTicks(true);
        pengalamanSlider.setPaintLabels(true);
        pengalamanSlider.setSnapToTicks(true);
        pengalamanSlider.setToolTipText("Pengalaman: 0 Tahun");

        // Listener untuk memperbarui tooltip slider secara realtime saat digeser
        pengalamanSlider.addChangeListener(e -> {
            pengalamanSlider.setToolTipText("Pengalaman: " + pengalamanSlider.getValue() + " Tahun");
        });

        // Sembunyikan button delete pada kondisi awal
        deleteButton.setVisible(false);

        // Saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // Saat tombol delete ditekan (dengan dialog konfirmasi interaktif Yes/No)
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0 && selectedIndex < listPerson.size()) {
                    String targetNama = listPerson.get(selectedIndex).getNama();
                    int confirmation = JOptionPane.showConfirmDialog(
                            null,
                            "Apakah Anda yakin ingin menghapus data \"" + targetNama + "\"?",
                            "Konfirmasi Hapus Data",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (confirmation == JOptionPane.YES_OPTION) {
                        deleteData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Penghapusan data dibatalkan.");
                    }
                }
            }
        });

        // Saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // Konfigurasi seleksi baris tabel (Single Selection)
        personTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listener klik mouse pada baris tabel
        personTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = personTable.getSelectedRow();
                if (row >= 0 && row < listPerson.size()) {
                    selectedIndex = row;
                    loadSelectedData();
                }
            }
        });

        // Listener navigasi keyboard (Panah Atas / Bawah) pada tabel
        personTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = personTable.getSelectedRow();
                if (row >= 0 && row < listPerson.size()) {
                    selectedIndex = row;
                    loadSelectedData();
                }
            }
        });
    }

    // Helper: Mengisi data dari baris terpilih ke dalam formulir input
    private void loadSelectedData() {
        if (selectedIndex >= 0 && selectedIndex < listPerson.size()) {
            Person p = listPerson.get(selectedIndex);

            idField.setText(p.getId());
            namaField.setText(p.getNama());
            tahunField.setText(String.valueOf(p.getTahunLahir()));
            kategoriComboBox.setSelectedItem(p.getKategori());
            pengalamanSlider.setValue(p.getPengalamanKerja());

            addUpdateButton.setText("Update");
            deleteButton.setVisible(true);
        }
    }

    // Method untuk mengisi data awal listPerson (10 data awal yang beragam)
    public void populateList() {
        listPerson.add(new Person("MED001", "dr. Hendra Pratama, Sp.A", 1982, "Dokter Spesialis", 16));
        listPerson.add(new Person("MED002", "dr. Sarah Kartika, Sp.JP", 1985, "Dokter Spesialis", 13));
        listPerson.add(new Person("MED003", "dr. Aditya Nugraha", 1993, "Dokter Umum", 6));
        listPerson.add(new Person("MED004", "Ns. Ratna Sari, S.Kep", 1990, "Perawat (Nurse)", 10));
        listPerson.add(new Person("MED005", "apt. Bambang Pamungkas, S.Farm", 1995, "Apoteker", 5));
        listPerson.add(new Person("MED006", "Dewi Anggraini, A.Md.AK", 1997, "Tenaga Laboratorium", 3));
        listPerson.add(new Person("MED007", "dr. Faisal Rahman", 1991, "Dokter Umum", 7));
        listPerson.add(new Person("MED008", "Ns. Tri Wahyuni, S.Kep", 1994, "Perawat (Nurse)", 6));
        listPerson.add(new Person("MED009", "apt. Maya Indah, M.Farm", 1989, "Apoteker", 11));
        listPerson.add(new Person("MED010", "Rizky Firmansyah, A.Md.Rad", 1998, "Tenaga Laboratorium", 2));
    }

    // 3. setTable() - Membangun Model Tabel dengan proteksi Non-Editable Cells
    public final DefaultTableModel setTable() {
        // Tentukan kolom tabel
        Object[] cols = { "No", "ID Staf", "Nama", "Tahun Lahir", "Kategori", "Pengalaman (Thn)" };

        // Buat objek tabel dengan isCellEditable dinonaktifkan agar sel tidak bisa diubah langsung di tabel
        DefaultTableModel tmp = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Isi tabel dengan data listPerson
        for (int i = 0; i < listPerson.size(); i++) {
            Object[] row = {
                    i + 1,
                    listPerson.get(i).getId(),
                    listPerson.get(i).getNama(),
                    listPerson.get(i).getTahunLahir(),
                    listPerson.get(i).getKategori(),
                    listPerson.get(i).getPengalamanKerja() + " Tahun"
            };
            tmp.addRow(row);
        }

        return tmp;
    }

    // Helper: Menerapkan model baru dan merapikan tampilan tabel (Row height, alignment, lebar kolom)
    private void refreshTable() {
        personTable.setModel(setTable());
        personTable.setRowHeight(24);
        personTable.getTableHeader().setReorderingAllowed(false);

        // Styling alignment tengah untuk kolom No, ID Staf, Tahun Lahir, dan Pengalaman
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        if (personTable.getColumnCount() >= 6) {
            personTable.getColumnModel().getColumn(0).setPreferredWidth(45);
            personTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

            personTable.getColumnModel().getColumn(1).setPreferredWidth(85);
            personTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

            personTable.getColumnModel().getColumn(2).setPreferredWidth(210);

            personTable.getColumnModel().getColumn(3).setPreferredWidth(95);
            personTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

            personTable.getColumnModel().getColumn(4).setPreferredWidth(140);

            personTable.getColumnModel().getColumn(5).setPreferredWidth(125);
            personTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        }
    }

    // Helper: Validasi ID unik
    public boolean isIdExists(String id, int excludeIndex) {
        for (int i = 0; i < listPerson.size(); i++) {
            if (i != excludeIndex && listPerson.get(i).getId().equalsIgnoreCase(id.trim())) {
                return true;
            }
        }
        return false;
    }

    // 4. insertData()
    public void insertData() {
        String id = idField.getText().trim();
        String nama = namaField.getText().trim();
        String tahunStr = tahunField.getText().trim();
        String kategori = (kategoriComboBox.getSelectedItem() != null) ? kategoriComboBox.getSelectedItem().toString() : "";
        int pengalaman = pengalamanSlider.getValue();

        // Validasi input kosong
        if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field teks wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi kategori belum dipilih
        if (kategori.equals("???")) {
            JOptionPane.showMessageDialog(null, "Silakan pilih Kategori yang valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi ID unik
        if (isIdExists(id, -1)) {
            JOptionPane.showMessageDialog(null, "ID '" + id + "' sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int tahunLahir = Integer.parseInt(tahunStr);
            int currentYear = Year.now().getValue();

            // Validasi rentang tahun lahir
            if (tahunLahir < 1920 || tahunLahir > currentYear) {
                JOptionPane.showMessageDialog(null, "Tahun lahir harus antara 1920 dan " + currentYear + "!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tambahkan data ke dalam list
            listPerson.add(new Person(id, nama, tahunLahir, kategori, pengalaman));

            // Update tabel
            refreshTable();

            // Bersihkan form
            clearForm();

            // Feedback
            System.out.println("Insert berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tahun lahir harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 5. updateData()
    public void updateData() {
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Pilih data pada tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = idField.getText().trim();
        String nama = namaField.getText().trim();
        String tahunStr = tahunField.getText().trim();
        String kategori = (kategoriComboBox.getSelectedItem() != null) ? kategoriComboBox.getSelectedItem().toString() : "";
        int pengalaman = pengalamanSlider.getValue();

        // Validasi input kosong
        if (id.isEmpty() || nama.isEmpty() || tahunStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field teks wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi kategori
        if (kategori.equals("???")) {
            JOptionPane.showMessageDialog(null, "Silakan pilih Kategori yang valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi ID unik (tidak boleh bentrok dengan data milik baris lain)
        if (isIdExists(id, selectedIndex)) {
            JOptionPane.showMessageDialog(null, "ID '" + id + "' sudah digunakan oleh orang lain!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int tahunLahir = Integer.parseInt(tahunStr);
            int currentYear = Year.now().getValue();

            // Validasi rentang tahun lahir
            if (tahunLahir < 1920 || tahunLahir > currentYear) {
                JOptionPane.showMessageDialog(null, "Tahun lahir harus antara 1920 dan " + currentYear + "!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ubah data di list
            Person p = listPerson.get(selectedIndex);
            p.setId(id);
            p.setNama(nama);
            p.setTahunLahir(tahunLahir);
            p.setKategori(kategori);
            p.setPengalamanKerja(pengalaman);

            // Update tabel
            refreshTable();

            // Bersihkan form
            clearForm();

            // Feedback
            System.out.println("Update berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tahun lahir harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 6. deleteData()
    public void deleteData() {
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris tabel yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Hapus data dari list
        listPerson.remove(selectedIndex);

        // Update tabel
        refreshTable();

        // Bersihkan form
        clearForm();

        // Feedback
        System.out.println("Delete berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    // 7. clearForm()
    public void clearForm() {
        // Kosongkan semua textfield dan reset combo box serta slider
        idField.setText("");
        namaField.setText("");
        tahunField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        pengalamanSlider.setValue(0);

        // Ubah button "Update" kembali menjadi "Add"
        addUpdateButton.setText("Add");

        // Sembunyikan button delete
        deleteButton.setVisible(false);

        // Reset selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;

        // Hilangkan seleksi pada tabel
        personTable.clearSelection();
    }
}
