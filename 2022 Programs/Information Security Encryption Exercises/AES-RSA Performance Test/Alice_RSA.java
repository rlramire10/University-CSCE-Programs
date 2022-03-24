import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Alice_RSA {
	//Initialize Global Variables
    private PrivateKey privateKey;
    private PublicKey publicKey;
	private final int KEY_SIZE = 2048;
	private Cipher encryptionCipher;
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	//Encryption
	//**********************************************************************************
    public String encrypt(String message, PublicKey public_key) throws Exception{
        //Create byte array of plaintext
		byte[] messageToBytes = message.getBytes();
        
		//Create RSA encryption cipher
		encryptionCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
		//Initialize RSA encryption cipher with Bob's generated public key
		encryptionCipher.init(Cipher.ENCRYPT_MODE, public_key);
        
		//Create encrypted message
		byte[] encryptedBytes = encryptionCipher.doFinal(messageToBytes);
        
		//Return encrypted message
		return encode(encryptedBytes);
    }
	
	//Convert encrypted message from byte array to String
    private String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
	//**********************************************************************************

    public static void main(String[] args) {
        try{
            //Initialization
			Alice_RSA rsa = new Alice_RSA();
			
			//Retrieve public key from publicKey.txt
			File publickeytext = new File("publicKey.txt");
			Scanner myReader1 = new Scanner(publickeytext);
			String encodedPublicKey = "";
			while (myReader1.hasNextLine()) {
				encodedPublicKey = myReader1.nextLine();	
			}
			myReader1.close();
			byte[] decodedPublicKey = Base64.getDecoder().decode(encodedPublicKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey public_key = keyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublicKey));
			
			//Accept Plaintext from User
			//Scanner myReader2 = new Scanner(System.in);
			//System.out.println("Enter plaintext to encrypt: ");
			//String plaintext = myReader2.nextLine();
			//myReader2.close();
			
			//Retrieve plaintext from plaintext.txt
			File Plaintext = new File("plaintext.txt");
			Scanner myReader2 = new Scanner(Plaintext);
			String plaintext = "";
			while (myReader2.hasNextLine()) {
				plaintext = myReader2.nextLine();	
			}
			myReader2.close();
			
			//Encryption Process
			String encryptedMessage = rsa.encrypt(plaintext, public_key);
			//System.out.println("");
			//System.out.println("Encrypted Ciphertext: " + encryptedMessage);
			
			//Save encrpyted ciphertext into ctext.txt
			FileWriter myWriter = new FileWriter("ctext_RSA.txt");
			myWriter.write(encryptedMessage);
			myWriter.close();
			
        }catch (Exception ingored){}
    }
}