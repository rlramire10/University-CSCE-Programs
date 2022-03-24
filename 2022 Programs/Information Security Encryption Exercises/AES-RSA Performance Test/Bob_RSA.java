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

public class Bob_RSA {
	//Initialize Global Variables
    private PrivateKey privateKey;
    private PublicKey publicKey;
	private final int KEY_SIZE = 2048;
	private Cipher decryptionCipher;
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	//Decryption
	//**********************************************************************************
    public String decrypt(String encryptedMessage, PrivateKey private_key) throws Exception{
        //Create byte array of ciphertext
		byte[] encryptedBytes = decode(encryptedMessage);
        
		//Create RSA decryption cipher
		decryptionCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
		//Initialize RSA decryption cipher with Bob's generated private key
		decryptionCipher.init(Cipher.DECRYPT_MODE, private_key);
        
		//Create decrypted message
		byte[] decryptedMessage = decryptionCipher.doFinal(encryptedBytes);
        
		//Return decrypted message
		return new String(decryptedMessage,"UTF8");
    }
	
	//Convert decrypted message from String to byte array
    private byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }
	//**********************************************************************************

    public static void main(String[] args) {
        try{
            //Initialization
			Bob_RSA rsa = new Bob_RSA();
			
			//Retrieve private key from privateKey.txt
			File privatekeytext = new File("privateKey.txt");
			Scanner myReader1 = new Scanner(privatekeytext);
			String encodedPrivateKey = "";
			while (myReader1.hasNextLine()) {
				encodedPrivateKey = myReader1.nextLine();	
			}
			myReader1.close();
			byte[] decodedPrivatecKey = Base64.getDecoder().decode(encodedPrivateKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey private_key = keyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedPrivatecKey));
			
			//Retrieve ciphertext from ctext.txt
			File ctext = new File("ctext_RSA.txt");
			Scanner myReader2 = new Scanner(ctext);
			String cyphertext = "";
			while (myReader2.hasNextLine()) {
				cyphertext = myReader2.nextLine();	
			}
			myReader2.close();
			
            //Decryption Process
			String decryptedMessage = rsa.decrypt(cyphertext, private_key);
			//System.out.println("");
			//System.out.println("Decrypted Plaintext:  " + decryptedMessage);
			
        }catch (Exception ingored){}
    }
}