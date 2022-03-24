import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;


public class Bob_AES{
    //Initialize Global Variables
	private SecretKey Key;
    private IvParameterSpec IV;
	private final int KEY_SIZE = 128;
    private Cipher decryptionCipher;
	
	
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
	
	//Decryption
    //**********************************************************************************
	public String decrypt(String encryptedMessage, SecretKey key, IvParameterSpec iv) throws Exception {
        //Create byte array of ciphertext
		byte[] messageInBytes = decode(encryptedMessage);
        
		//Create AES decryption cipher
		decryptionCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
		//Initialize AES decryption cipher with generated key and IV
		decryptionCipher.init(Cipher.DECRYPT_MODE, key, iv);
        
		//Create decrypted message
		byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        
		//Return decrypted message
		return new String(decryptedBytes);
    }
	
	//Convert decrypted message from String to byte array
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
	//**********************************************************************************

    public static void main(String[] args) {
        try {
            //Initialization
			Bob_AES aes = new Bob_AES();
            aes.init();
			
			//Retrieve shared key from key.txt
			File keytext = new File("key.txt");
			Scanner myReader1 = new Scanner(keytext);
			String encodedkey = "";
			while (myReader1.hasNextLine()) {
				encodedkey = myReader1.nextLine();	
			}
			myReader1.close();
			byte[] decodedkey = Base64.getDecoder().decode(encodedkey);
			SecretKey key = new SecretKeySpec(decodedkey, 0, decodedkey.length, "AES");
			
			//Retrieve IV from IV.txt
			File IVtext = new File("IV.txt");
			Scanner myReader2 = new Scanner(IVtext);
			String encodedIV = "";
			while (myReader2.hasNextLine()) {
				encodedIV = myReader2.nextLine();	
			}
			myReader2.close();
			byte[] decodedIV = Base64.getDecoder().decode(encodedIV);
			IvParameterSpec iv = new IvParameterSpec(decodedIV);
			
			//Retrieve ciphertext from ctext.txt
			File ctext = new File("ctext_AES.txt");
			Scanner myReader3 = new Scanner(ctext);
			String cyphertext = "";
			while (myReader3.hasNextLine()) {
				cyphertext = myReader3.nextLine();	
			}
			myReader3.close();
			
			//Decryption Process
			String decryptedMessage = aes.decrypt(cyphertext, key, iv);
            //System.out.println("");
			//System.out.println("Decrypted Plaintext:  " + decryptedMessage);
			
        } catch (Exception ignored) {
        }
    }
}