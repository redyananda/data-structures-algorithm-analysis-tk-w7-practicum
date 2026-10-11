import linkedlist.StudentLinkedList;

/**
 * Demonstrasi operasi wajib Linked List:
 * tambah, hapus, update nilai, dan tampilkan daftar mahasiswa.
 */
public class LinkedListDemo {
    // Menjalankan skenario tambah, update, hapus, dan tampilkan mahasiswa
    public static void main(String[] args) {
        StudentLinkedList students = new StudentLinkedList();

        students.addStudent("12345", "Andi", 85);
        students.addStudent("67890", "Budi", 90);
        students.addStudent("24680", "Siti", 88);

        System.out.println("=== DATA AWAL ===");
        students.displayStudents();

        System.out.println("\nUpdate nilai Budi (NIM 67890) menjadi 95...");
        if (students.updateGrade("67890", 95)) {
            System.out.println("Nilai berhasil diupdate.");
        }

        System.out.println("\nHapus mahasiswa Siti (NIM 24680)...");
        if (students.removeStudent("24680")) {
            System.out.println("Mahasiswa berhasil dihapus.");
        }

        System.out.println("\n=== DATA SETELAH UPDATE DAN DELETE ===");
        students.displayStudents();
        System.out.println("\nJumlah mahasiswa: " + students.getSize());
    }
}
