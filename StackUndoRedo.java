import java.util.Scanner;

/**
 * Fitur undo/redo pada editor teks sederhana menggunakan dua Stack.
 */
public class StackUndoRedo {

    // Node menyimpan teks dan penghubung ke node berikutnya
    static class StackNode {
        private String data;
        private StackNode next;

        // Membuat node baru berisi satu kondisi teks
        public StackNode(String data) {
            this.data = data;
            this.next = null;
        }

        // Mengambil teks yang tersimpan dalam node
        public String getData() {
            return data;
        }

        // Mengubah teks dalam node
        public void setData(String data) {
            this.data = data;
        }

        // Mengambil node berikutnya
        public StackNode getNext() {
            return next;
        }

        // Menghubungkan node dengan node berikutnya
        public void setNext(StackNode next) {
            this.next = next;
        }
    }

    // Stack menggunakan prinsip [ LIFO ]
    // data yang terakhir masuk akan diambil lebih dahulu.
    static class HistoryStack {
        private StackNode top;
        private int size;

        // Membuat stack kosong
        public HistoryStack() {
            top = null;
            size = 0;
        }

        // Menambahkan data ke posisi paling atas
        public void push(String data) {
            if (data == null) {
                return;
            }
            // Buat node baru dan simpan sementara dalam temp
            StackNode temp = new StackNode(data);
            // Hubungkan node baru ke node yang sebelumnya paling atas
            temp.setNext(top);
            // Pindahkan posisi top ke node baru
            top = temp;
            size++;
        }

        // Mengambil sekaligus menghapus data paling atas
        public String pop() {
            if (isEmpty()) {
                return null;
            }
            // Simpan node di posisi awal top ke temp
            StackNode temp = top;
            // Pindahkan top ke node berikutnya.
            top = temp.getNext();
            size--;

            // Kembalikan data dari node yang tadi diambil
            return temp.getData();
        }

        // Memeriksa apakah stack kosong
        public boolean isEmpty() {
            return top == null;
        }

        // Mengambil jumlah data dalam stack
        public int getSize() {
            return size;
        }

        // Mengosongkan stack dengan mengatur ulang top dan size
        public void clear() {
            top = null;
            size = 0;
        }
    }

    // Editor menggunakan dua stack untuk riwayat undo dan redo
    static class TextEditor {
        private String currentText;
        private HistoryStack undoStack;
        private HistoryStack redoStack;

        // Membuat editor dengan teks kosong dan dua stack kosong
        public TextEditor() {
            currentText = "";
            undoStack = new HistoryStack();
            redoStack = new HistoryStack();
        }

        // Menambahkan teks baru di akhir teks yang sudah ada
        public void addText(String newText) {
            if (newText == null || newText.isEmpty()) {
                return;
            }

            // Simpan kondisi teks sebelum perubahan
            String teksAwal = currentText;
            undoStack.push(teksAwal);
            // Gabungkan teks sebelumnya dengan teks baru
            currentText = teksAwal + newText;
            // Perubahan baru membuat riwayat redo lama tidak berlaku
            redoStack.clear();
        }

        // Mengembalikan teks ke kondisi sebelum perubahan terakhir
        public void undo() {
            if (undoStack.isEmpty()) {
                return;
            }
            // Simpan teks saat ini agar bisa dikembalikan melalui redo
            redoStack.push(currentText);

            // Ambil kondisi sebelumnya dari stack undo
            currentText = undoStack.pop();
        }

        // Mengembalikan perubahan yang sebelumnya di-undo
        public void redo() {
            if (redoStack.isEmpty()) {
                return;
            }
            // Simpan teks saat ini agar bisa di-undo kembali
            undoStack.push(currentText);
            // Ambil kondisi berikutnya dari stack redo
            currentText = redoStack.pop();
        }

        // Mengambil teks saat ini untuk ditampilkan
        public String getCurrentText() {
            return currentText;
        }
    }

    // Menjalankan demonstrasi tambah teks, undo, dan redo
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TextEditor editor = new TextEditor();

        // Membaca teks pertama dari pengguna
        System.out.print("Masukkan teks pertama: ");
        String text1 = scanner.nextLine();
        editor.addText(text1);

        // Membaca teks kedua untuk ditambahkan ke teks pertama
        System.out.print("Masukkan teks kedua: ");
        String text2 = scanner.nextLine();
        editor.addText(text2);

        // Menampilkan hasil setelah kedua teks digabungkan
        System.out.println(
            "\nTeks saat ini: \"" + editor.getCurrentText() + "\""
        );

        // Menjalankan undo
        editor.undo();

        System.out.println(
            "Undo: \"" + editor.getCurrentText() + "\""
        );

        // Menjalankan redo
        editor.redo();

        System.out.println(
            "Redo: \"" + editor.getCurrentText() + "\""
        );

        // Menutup Scanner setelah selesai membaca input.
        scanner.close();
    }
}
