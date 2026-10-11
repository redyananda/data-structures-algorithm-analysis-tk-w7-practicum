import java.io.OutputStream;
import java.io.PrintStream;

import queue.CustomerQueue;
import linkedlist.StudentLinkedList;

/**
 * Eksperimen performa: mengukur waktu eksekusi operasi pada
 * Queue, Stack, dan Linked List menggunakan System.nanoTime().
 */
public class PerformanceTest {

    // Jumlah data yang diuji
    private static final int[] DATA_SIZES = {1000, 5000, 10000};

    // Setiap pengujian diulang beberapa kali, lalu dirata-rata
    private static final int REPEAT = 10;

    // Jumlah operasi update dan hapus yang diukur pada Linked List
    private static final int SEARCH_OPS = 100;

    // Output asli ke layar
    private static final PrintStream CONSOLE = System.out;

    // Output "kosong", dipakai agar pesan println di dalam method
    // tidak tercetak ke layar selama waktu sedang diukur
    private static final PrintStream SILENT =
        new PrintStream(OutputStream.nullOutputStream());

    /**
     * Menjalankan seluruh eksperimen: pemanasan, pengukuran waktu
     * setiap struktur data, lalu menampilkan tabel hasil rata-rata.
     */
    public static void main(String[] args) {
        // Pemanasan (warm-up): pengujian dijalankan tanpa dicatat
        // agar JVM sudah siap sebelum pengukuran sebenarnya
        for (int w = 0; w < 20; w++) {
            for (int n : DATA_SIZES) {
                testQueue(n);
                testStack(n);
                testLinkedList(n);
            }
        }

        // Total waktu: [indeks ukuran data][indeks operasi]
        long[][] queueTotal = new long[DATA_SIZES.length][3];
        long[][] stackTotal = new long[DATA_SIZES.length][3];
        long[][] linkedListTotal = new long[DATA_SIZES.length][4];

        // Pengukuran: setiap putaran menguji semua ukuran data
        // secara bergantian agar kondisi JVM sama untuk semuanya
        for (int r = 0; r < REPEAT; r++) {
            for (int s = 0; s < DATA_SIZES.length; s++) {
                addResult(queueTotal[s], testQueue(DATA_SIZES[s]));
                addResult(stackTotal[s], testStack(DATA_SIZES[s]));
                addResult(linkedListTotal[s],
                    testLinkedList(DATA_SIZES[s]));
            }
        }

        CONSOLE.println("HASIL EKSPERIMEN PERFORMA");
        CONSOLE.println("Pengulangan per pengujian: " + REPEAT
            + " kali (rata-rata)");
        printHeader();

        // Hasil Queue
        for (int s = 0; s < DATA_SIZES.length; s++) {
            int n = DATA_SIZES[s];
            long[] q = queueTotal[s];
            printRow("Queue", "enqueue (tambah pelanggan)", n, q[0], n);
            printRow("Queue", "dequeue (layani pelanggan)", n, q[1], n);
            printRow("Queue", "displayQueue (tampilkan)", n, q[2], n);
        }
        printLine();

        // Hasil Stack
        for (int s = 0; s < DATA_SIZES.length; s++) {
            int n = DATA_SIZES[s];
            long[] st = stackTotal[s];
            printRow("Stack", "addText (tambah teks)", n, st[0], n);
            printRow("Stack", "undo", n, st[1], n);
            printRow("Stack", "redo", n, st[2], n);
        }
        printLine();

        // Hasil Linked List
        for (int s = 0; s < DATA_SIZES.length; s++) {
            int n = DATA_SIZES[s];
            long[] ll = linkedListTotal[s];
            printRow("LinkedList", "tambah mahasiswa", n, ll[0], n);
            printRow("LinkedList", "update nilai (x100)", n,
                ll[1], SEARCH_OPS);
            printRow("LinkedList", "tampilkan daftar", n, ll[2], n);
            printRow("LinkedList", "hapus mahasiswa (x100)", n,
                ll[3], SEARCH_OPS);
        }
        printLine();
    }

    /**
     * Mengukur waktu operasi Queue untuk n pelanggan.
     * Mengembalikan waktu dalam nanodetik:
     * [0] = enqueue n kali, [1] = dequeue n kali,
     * [2] = displayQueue 1 kali.
     */
    private static long[] testQueue(int n) {
        // Nama pelanggan disiapkan di luar pengukuran
        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            names[i] = "Pelanggan" + (i + 1);
        }

        CustomerQueue queue = new CustomerQueue();
        System.setOut(SILENT);

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            queue.enqueue(names[i]);
        }
        long enqueueTime = System.nanoTime() - start;

        start = System.nanoTime();
        queue.displayQueue();
        long displayTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            queue.dequeue();
        }
        long dequeueTime = System.nanoTime() - start;

        System.setOut(CONSOLE);
        return new long[] {enqueueTime, dequeueTime, displayTime};
    }

    /**
     * Mengukur waktu operasi Stack (fitur undo/redo)
     * untuk n perubahan teks.
     * Mengembalikan waktu dalam nanodetik:
     * [0] = addText n kali, [1] = undo n kali, [2] = redo n kali.
     */
    private static long[] testStack(int n) {
        StackUndoRedo.TextEditor editor =
            new StackUndoRedo.TextEditor();

        // Setiap perubahan menambah satu karakter, seperti mengetik
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            editor.addText("a");
        }
        long addTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            editor.undo();
        }
        long undoTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            editor.redo();
        }
        long redoTime = System.nanoTime() - start;

        return new long[] {addTime, undoTime, redoTime};
    }

    /**
     * Mengukur waktu operasi Linked List untuk n mahasiswa.
     * Mengembalikan waktu dalam nanodetik:
     * [0] = tambah n mahasiswa,
     * [1] = update nilai SEARCH_OPS mahasiswa,
     * [2] = tampilkan n mahasiswa 1 kali,
     * [3] = hapus SEARCH_OPS mahasiswa.
     * Update dan hapus dilakukan pada mahasiswa di sekitar
     * posisi tengah list, sehingga setiap operasi harus
     * menelusuri sekitar n/2 node.
     */
    private static long[] testLinkedList(int n) {
        // NIM dan nama mahasiswa disiapkan di luar pengukuran
        String[] nims = new String[n];
        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            nims[i] = String.format("%010d", i);
            names[i] = "Mahasiswa" + i;
        }
        // Indeks mahasiswa pertama di posisi tengah list
        int middle = n / 2;

        StudentLinkedList list = new StudentLinkedList();

        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            list.addStudent(nims[i], names[i], 80.0);
        }
        long addTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < SEARCH_OPS; i++) {
            list.updateGrade(nims[middle + i], 95.0);
        }
        long updateTime = System.nanoTime() - start;

        start = System.nanoTime();
        list.displayStudents(SILENT);
        long displayTime = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < SEARCH_OPS; i++) {
            list.removeStudent(nims[middle + i]);
        }
        long removeTime = System.nanoTime() - start;

        return new long[] {addTime, updateTime, displayTime,
            removeTime};
    }

    // Menjumlahkan hasil satu kali pengujian ke total
    private static void addResult(long[] total, long[] result) {
        for (int i = 0; i < total.length; i++) {
            total[i] += result[i];
        }
    }

    // Menampilkan judul tabel hasil
    private static void printHeader() {
        printLine();
        CONSOLE.printf("| %-11s | %-28s | %7s | %13s | %20s |%n",
            "Struktur", "Operasi", "N", "Total (ms)",
            "Rata-rata/op (ns)");
        printLine();
    }

    // Menampilkan satu baris hasil. totalNanos adalah jumlah waktu
    // dari seluruh putaran, count adalah jumlah operasi atau data
    // dalam satu putaran
    private static void printRow(String structure, String operation,
                                 int n, long totalNanos, int count) {
        double avgNanos = (double) totalNanos / REPEAT;
        CONSOLE.printf("| %-11s | %-28s | %7d | %13.3f | %20.1f |%n",
            structure, operation, n,
            avgNanos / 1_000_000.0, avgNanos / count);
    }

    // Menampilkan garis pemisah tabel
    private static void printLine() {
        CONSOLE.println("+-------------+------------------------------"
            + "+---------+---------------+----------------------+");
    }
}
