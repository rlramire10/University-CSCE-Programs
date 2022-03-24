import java.util.Scanner;
import java.io.*;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Bob_RSA_DSig {
	//Initialize Global Variables
    private PrivateKey privateKey;
    private PublicKey publicKey;
	private final int KEY_SIZE = 2048;
	private Cipher decryptionCipher;
	
	public String readFile1(String filename) throws Exception {
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		String text = "";
		String line = "";
		while ((line = br.readLine()) != null) {
			text = line;
		}
		br.close();
		fr.close();
		return text;
	}
	
	public String[] readFile2(String filename) throws Exception {
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		int i = 0;
		String[] text = {"",""};
		String line = "";
		while ((line = br.readLine()) != null) {
			text[i] = line;
			i++;
		}
		br.close();
		fr.close();
		return text;
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
	
	//Convert encrypted message from byte array to String
    private String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }
	
	//Verify Signature
	//**********************************************************************************
	public boolean verify_signature(String plaintext, String signature, PublicKey public_key) throws Exception {
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(public_key);
		publicSignature.update(plaintext.getBytes());

		byte[] signatureBytes = decode(signature);

		return publicSignature.verify(signatureBytes);
	}
	//**********************************************************************************
	
	
    public static void main(String[] args) {
        try{
            //Initialization
			Bob_RSA_DSig rsa = new Bob_RSA_DSig();
			
			//************************************************************************************************************
			//Retrieve key pair from keypair.txt
			String[] list1 = rsa.readFile2("keypair.txt");
			String encodedPublicKey  = list1[0];
			String encodedPrivateKey = list1[1];
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			byte[] decodedPublicKey = rsa.decode(encodedPublicKey);
			PublicKey public_key = keyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublicKey));
			
			byte[] decodedPrivatecKey = rsa.decode(encodedPrivateKey);
			PrivateKey private_key = keyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedPrivatecKey));
			//************************************************************************************************************
			
			//Retreieve Ciphertext and Signature from sigtext.txt
			String[] list2 = rsa.readFile2("sigtext.txt");
			String ciphertext = list2[0];
			String signature = list2[1];
			
			//Verify Digital Signature
			boolean isCorrect = rsa.verify_signature("bad password", signature, public_key);
			//System.out.println("Signature correct: " + isCorrect);
			if(isCorrect) {
				/*
				//Decryption Process
				String decryptedMessage = rsa.decrypt(ciphertext, private_key);
				System.out.println("");
				System.out.println("Decrypted Plaintext:  " + decryptedMessage);
				*/
			}
			else {
				//System.out.println("Verification Failed");
			}
			
        }catch (Exception ingored){}
    }
}