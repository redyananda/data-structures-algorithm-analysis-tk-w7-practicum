package queue;

/**
 * Queue berbasis Linked List untuk antrean layanan customer service.
 * Menggunakan prinsip FIFO: pelanggan yang datang lebih dulu dilayani lebih dulu.
 */
public class CustomerQueue {
    private QueueNode front;
    private QueueNode rear;
    private int size;

    // Membuat antrean kosong
    public CustomerQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    // Menambahkan pelanggan ke belakang antrean
    public void enqueue(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            System.out.println("Nama pelanggan tidak boleh kosong.");
            return;
        }

        QueueNode newNode = new QueueNode(customerName.trim());

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.setNext(newNode);
            rear = newNode;
        }

        size++;
        System.out.println(
            "Pelanggan " + customerName.trim()
            + " berhasil ditambahkan."
        );
    }

    // Melayani pelanggan paling depan
    public String dequeue() {
        if (isEmpty()) {
            System.out.println("Antrean kosong.");
            return null;
        }

        String servedCustomer = front.getCustomerName();
        front = front.getNext();
        size--;

        if (front == null) {
            rear = null;
        }

        System.out.println("Melayani pelanggan: " + servedCustomer);
        return servedCustomer;
    }

    // Menampilkan semua pelanggan
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("Antrean kosong.");
            return;
        }

        System.out.println("\nPelanggan dalam antrean:");
        QueueNode current = front;
        int number = 1;

        while (current != null) {
            System.out.println(
                number + ". " + current.getCustomerName()
            );

            current = current.getNext();
            number++;
        }
    }

    // Memeriksa apakah antrean kosong
    public boolean isEmpty() {
        return front == null;
    }

    // Mengambil jumlah pelanggan
    public int getSize() {
        return size;
    }

    // Melihat pelanggan paling depan tanpa menghapusnya
    public String peek() {
        if (isEmpty()) {
            return null;
        }

        return front.getCustomerName();
    }
}
