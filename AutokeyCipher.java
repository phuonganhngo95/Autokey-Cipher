package AutokeyCipher;// AutokeyCipher.AutokeyCipher.java
import java.util.Scanner;

public class AutokeyCipher {
    private int key;

    public AutokeyCipher(int key) {
        this.key = key;
    }

    public static int charToNum(char ch) {
        return Character.toUpperCase(ch) - 'A';
    }

    public static char numToChar(int num) {
        return (char) ((num % 26) + 'A');
    }

    public String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase().replace(" ", "");
        StringBuilder ciphertext = new StringBuilder();

        // Khởi tạo khóa
        int[] keys = new int[plaintext.length()];
        keys[0] = key;

        for (int i = 0; i < plaintext.length(); i++) {
            if (i > 0) {
                // Khóa tiếp theo là ký tự bản rõ trước đó
                keys[i] = charToNum(plaintext.charAt(i - 1));
            }

            int pNum = charToNum(plaintext.charAt(i));
            int kNum = keys[i];

            // Mã hóa: (p + k) mod 26
            int cNum = (pNum + kNum) % 26;
            ciphertext.append(numToChar(cNum));
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        ciphertext = ciphertext.toUpperCase().replace(" ", "");
        StringBuilder plaintext = new StringBuilder();

        // Khởi tạo khóa
        int[] keys = new int[ciphertext.length()];
        keys[0] = key;

        for (int i = 0; i < ciphertext.length(); i++) {
            int cNum = charToNum(ciphertext.charAt(i));
            int kNum = keys[i];

            // Giải mã: (c - k) mod 26
            int pNum = (cNum - kNum + 26) % 26;
            plaintext.append(numToChar(pNum));

            // Khóa tiếp theo là ký tự bản rõ vừa giải mã
            if (i < ciphertext.length() - 1) {
                keys[i + 1] = pNum;
            }
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== AUTOKEY CIPHER EXAMPLE ===");

        System.out.print("Nhập khóa K: ");
        int key = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Nhập bản rõ (chỉ chữ cái): ");
        String plaintext = scanner.nextLine().toUpperCase().replace(" ", "");

        AutokeyCipher cipher = new AutokeyCipher(key);

        String encrypted = cipher.encrypt(plaintext);
        String decrypted = cipher.decrypt(encrypted);

        System.out.println("\nKết quả:");
        System.out.println("Khóa K: " + key);
        System.out.println("Bản rõ: " + plaintext);
        System.out.println("Ngược lại:");
        System.out.println("Bản mã: " + encrypted);
        System.out.println("Giải mã: " + decrypted);
    }
}