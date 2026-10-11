package queue;

/**
 * Node untuk Queue berbasis Linked List.
 * Setiap node menyimpan nama satu pelanggan dan referensi ke node berikutnya.
 */
public class QueueNode {
    private String customerName;
    private QueueNode next;

    // Membuat node baru berisi nama pelanggan, belum terhubung ke node lain
    public QueueNode(String customerName) {
        this.customerName = customerName;
        this.next = null;
    }

    // Mengambil nama pelanggan yang tersimpan di node
    public String getCustomerName() {
        return customerName;
    }

    // Mengubah nama pelanggan yang tersimpan di node
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // Mengambil node berikutnya (pelanggan di belakangnya)
    public QueueNode getNext() {
        return next;
    }

    // Menghubungkan node ini dengan node berikutnya
    public void setNext(QueueNode next) {
        this.next = next;
    }
}
