package linkedlist;

import java.io.PrintStream;

/**
 * Single Linked List untuk mengelola data mahasiswa.
 * Mendukung insertion, deletion, update, searching, dan traversal.
 */
public class StudentLinkedList {
    private StudentNode head;
    private StudentNode tail;
    private int size;

    // Membuat linked list kosong
    public StudentLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Menambahkan mahasiswa ke akhir linked list.
     * Dengan pointer tail, insertion di akhir memiliki kompleksitas O(1).
     */
    public void addStudent(String nim, String name, double grade) {
        validateStudentData(nim, name, grade);

        StudentNode newNode = new StudentNode(nim.trim(), name.trim(), grade);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }

        size++;
    }

    /**
     * Menghapus mahasiswa berdasarkan NIM.
     * Pencarian node dilakukan dari head sehingga worst case O(n).
     */
    public boolean removeStudent(String nim) {
        if (nim == null || nim.trim().isEmpty() || isEmpty()) {
            return false;
        }

        String targetNim = nim.trim();
        StudentNode current = head;
        StudentNode previous = null;

        while (current != null) {
            if (current.getNim().equals(targetNim)) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }

                if (current == tail) {
                    tail = previous;
                }

                current.setNext(null);
                size--;

                if (size == 0) {
                    head = null;
                    tail = null;
                }

                return true;
            }

            previous = current;
            current = current.getNext();
        }

        return false;
    }

    /**
     * Mengupdate nilai mahasiswa berdasarkan NIM.
     * Worst case O(n) karena node harus dicari dari head.
     */
    public boolean updateGrade(String nim, double newGrade) {
        validateGrade(newGrade);

        StudentNode student = findStudent(nim);
        if (student == null) {
            return false;
        }

        student.setGrade(newGrade);
        return true;
    }

    /**
     * Mencari mahasiswa berdasarkan NIM dengan traversal linear.
     */
    public StudentNode findStudent(String nim) {
        if (nim == null || nim.trim().isEmpty()) {
            return null;
        }

        String targetNim = nim.trim();
        StudentNode current = head;

        while (current != null) {
            if (current.getNim().equals(targetNim)) {
                return current;
            }
            current = current.getNext();
        }

        return null;
    }

    /**
     * Menampilkan seluruh mahasiswa dari head sampai node terakhir.
     */
    public void displayStudents() {
        displayStudents(System.out);
    }

    // Menampilkan seluruh mahasiswa ke output tertentu (layar atau output kosong)
    public void displayStudents(PrintStream out) {
        if (isEmpty()) {
            out.println("Daftar mahasiswa kosong.");
            return;
        }

        out.println("Daftar Mahasiswa:");
        StudentNode current = head;
        int number = 1;

        while (current != null) {
            out.printf(
                "%d. NIM: %s, Nama: %s, Nilai: %.2f%n",
                number,
                current.getNim(),
                current.getName(),
                current.getGrade()
            );
            current = current.getNext();
            number++;
        }
    }

    // Memeriksa apakah daftar mahasiswa kosong
    public boolean isEmpty() {
        return head == null;
    }

    // Mengambil jumlah mahasiswa dalam daftar
    public int getSize() {
        return size;
    }

    // Mengambil node pertama (head) dari linked list
    public StudentNode getHead() {
        return head;
    }

    // Memastikan NIM, nama, dan nilai valid sebelum data ditambahkan
    private void validateStudentData(String nim, String name, double grade) {
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("NIM tidak boleh kosong.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama mahasiswa tidak boleh kosong.");
        }
        validateGrade(grade);
    }

    // Memastikan nilai berada pada rentang 0-100
    private void validateGrade(double grade) {
        if (Double.isNaN(grade) || grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException(
                "Nilai harus berada pada rentang 0-100."
            );
        }
    }
}
