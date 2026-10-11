import queue.CustomerQueue;

/**
 * Demonstrasi sistem antrean customer service:
 * tambah pelanggan, layani pelanggan, dan tampilkan antrean.
 */
public class QueueDemo {
    // Menjalankan skenario antrean sesuai contoh output pada soal
    public static void main(String[] args) {
        CustomerQueue queue = new CustomerQueue();

        // Tiga pelanggan datang dan masuk ke antrean
        queue.enqueue("Andi");
        queue.enqueue("Budi");
        queue.enqueue("Siti");
        queue.displayQueue();

        // Pelanggan paling depan dilayani lebih dulu (FIFO)
        System.out.println();
        queue.dequeue();
        queue.displayQueue();

        System.out.println("\nPelanggan berikutnya: " + queue.peek());
        System.out.println("Jumlah pelanggan dalam antrean: " + queue.getSize());
    }
}
