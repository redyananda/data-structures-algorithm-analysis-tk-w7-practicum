package linkedlist;

/**
 * Node untuk Single Linked List data mahasiswa.
 * Setiap node menyimpan data mahasiswa dan referensi ke node berikutnya.
 */
public class StudentNode {
    private String nim;
    private String name;
    private double grade;
    private StudentNode next;

    // Membuat node baru berisi NIM, nama, dan nilai mahasiswa
    public StudentNode(String nim, String name, double grade) {
        this.nim = nim;
        this.name = name;
        this.grade = grade;
        this.next = null;
    }

    // Mengambil NIM mahasiswa
    public String getNim() {
        return nim;
    }

    // Mengubah NIM mahasiswa
    public void setNim(String nim) {
        this.nim = nim;
    }

    // Mengambil nama mahasiswa
    public String getName() {
        return name;
    }

    // Mengubah nama mahasiswa
    public void setName(String name) {
        this.name = name;
    }

    // Mengambil nilai mahasiswa
    public double getGrade() {
        return grade;
    }

    // Mengubah nilai mahasiswa
    public void setGrade(double grade) {
        this.grade = grade;
    }

    // Mengambil node berikutnya
    public StudentNode getNext() {
        return next;
    }

    // Menghubungkan node ini dengan node berikutnya
    public void setNext(StudentNode next) {
        this.next = next;
    }
}
