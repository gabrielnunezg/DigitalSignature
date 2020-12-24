import java.util.Scanner;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        try {
            System.out.println("Digital Signature Valitation MD5+RSA");
            System.out.println("On the sender's side, the Signature Text will be hashed and encrypted. The result will be sent to the receiver");
            System.out.println("On the receiver's side, the encrypted hash is decrypted and then validated with the sender's hash. If they match, the validation was successful");
            System.out.println("Please Enter Signature Text");
            String senderText = keyboard.nextLine();
            int textLength = senderText.length();

            // Text Bit Length Validation (RSA-512 accepts messages up to 53 bits long)
            while (textLength > 54) {
                System.out.println("RSA does not accept source messages longer than 53 bytes");
                System.out.println("Message lenght in bytes: " + textLength);
                senderText = keyboard.nextLine();
                textLength = senderText.length();
            }

            // Sender's Side
            System.out.println("Accepted Message Length");
            System.out.println("-----------------------------------------------");
            
            // Sender's Side
            System.out.println("Sender's Side");
            RSA.keyPairGenerate();
            String senderHash = MD5.hashString(senderText);
            System.out.println("Sender Signature Hash : "+senderHash);
            
            byte[] senderHashArray = RSA.RSAencrypt(senderHash);
            String senderHashEncrypted = Base64.getEncoder().encodeToString(senderHashArray);
            System.out.println("Encrypted Hash for Receiver : " + senderHashEncrypted);
            System.out.println("-----------------------------------------------");
            
            // Receiver's Side
            System.out.println("Receiver's Side");
            System.out.println("Receiver decrypts the Encrypted Hash: "+senderHashEncrypted);
            String receiverHash = RSA.RSAdecrypt(senderHashArray);
            System.out.println("Receiver Signature Hash : " + receiverHash);

            System.out.println("-----------------------------------------------");
            System.out.println("Sender and Receiver Signature Hashes Validation");

            if (receiverHash.equals(senderHash)) {
                System.out.println("Sender and Receiver Signature Hashes match. Validation Successful");
            }
            else {
                System.out.println("Sender and Receiver Signature Hashes do not match. Validation not Successful");
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
