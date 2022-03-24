import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;


public class Alice_AES{
    //Initialize Global Variables
	private SecretKey Key;
    private IvParameterSpec IV;
	private final int KEY_SIZE = 128;
    private Cipher encryptionCipher;
	
	
	//Initialize AES Class
    public void init() throws Exception {
        //Generate Key
		KeyGenerator key_generator = KeyGenerator.getInstance("AES");
        key_generator.init(KEY_SIZE);
        Key = key_generator.generateKey();
		
		//Generate IV
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		IV = new IvParameterSpec(iv);
    }
	
	public SecretKey getKey() {
		return Key;
	}
	
	public IvParameterSpec getIV() {
		return IV;
	}
	
	//Encryption
	//**********************************************************************************
    public String encrypt(String message, SecretKey key, IvParameterSpec iv) throws Exception {
        //Create byte array of plaintext
		byte[] messageInBytes = message.getBytes();
		
		//Create AES encryption cipher
        encryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
		//Initialize AES encryption cipher with generated key and IV
		encryptionCipher.init(Cipher.ENCRYPT_MODE, key, iv);
        
		//Create encrypted message
		byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        
		//Return encrypted message
		return encode(encryptedBytes);
    }
	
	//Convert encrypted message from byte array to String
	private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
	//**********************************************************************************

    public static void main(String[] args) {
        try {
            //Initialization
			Alice_AES aes = new Alice_AES();
            aes.init();
			SecretKey key = aes.getKey();
			IvParameterSpec iv = aes.getIV();
			
			//Retrieve plaintext from plaintext.txt
			File Plaintext = new File("plaintext.txt");
			Scanner myReader = new Scanner(Plaintext);
			String plaintext = "";
			while (myReader.hasNextLine()) {
				plaintext = myReader.nextLine();	
			}
			myReader.close();
			
			//Encryption Process
            String encryptedMessage = aes.encrypt(plaintext, key, iv);
			//System.out.println("");
			//System.out.println("Encrypted Ciphertext: " + encryptedMessage);
			
			//Save shared key into key.txt
			String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
			FileWriter myWriter1 = new FileWriter("key.txt");
			myWriter1.write(encodedKey);
			myWriter1.close();
			
			//Save IV into IV.txt
			String encodedIV = Base64.getEncoder().encodeToString(iv.getIV());
			FileWriter myWriter2 = new FileWriter("IV.txt");
			myWriter2.write(encodedIV);
			myWriter2.close();
			
			//Save encrpyted ciphertext into ctext.txt
			FileWriter myWriter3 = new FileWriter("ctext_AES.txt");
			myWriter3.write(encryptedMessage);
			myWriter3.close();
			
        } catch (Exception ignored) {
        }
    }
}