package fileRead;

import java.util.Scanner;
import java.io.*;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Alice_RSA_DSig {
	//Initialize Global Variables
    private PrivateKey privateKey;
    private PublicKey publicKey;
	private final int KEY_SIZE = 2048;
	private Cipher encryptionCipher;
	
	public String readFile(String filename) throws Exception {
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
	
	public String[] readKeyPairFile(String filename) throws Exception {
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
	
	public void writeSigTextFile(String filename, String ciphertext, String signature) throws Exception {
		FileWriter fw = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(ciphertext+"\n");
		bw.write(signature+"\n");
		bw.close();
	}
	
	//Encryption
	//**********************************************************************************
    public String encrypt(String plaintext, PublicKey public_key) throws Exception{
        //Create byte array of plaintext
		byte[] messageToBytes = plaintext.getBytes();
        
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
	
	//Convert decrypted message from String to byte array
    private byte[] decode(String data){
        return Base64.getDecoder().decode(data);
    }
	
    //Digital Signature
	//**********************************************************************************
	public String signature(String plaintext, PrivateKey private_key) throws Exception {
		Signature privateSignature = Signature.getInstance("SHA256withRSA");
		privateSignature.initSign(private_key);
		privateSignature.update(plaintext.getBytes());

		byte[] signature = privateSignature.sign();

		return encode(signature);
	}
	//**********************************************************************************
	
	public static void main(String[] args) {
        try{
            //Initialization
			Alice_RSA_DSig rsa = new Alice_RSA_DSig();
			
			//************************************************************************************************************
			//Retrieve key pair from keypair.txt
			String[] list = rsa.readKeyPairFile("keypair.txt");
			String encodedPublicKey  = list[0];
			String encodedPrivateKey = list[1];
			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			
			byte[] decodedPublicKey = rsa.decode(encodedPublicKey);
			PublicKey public_key = keyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublicKey));
			
			byte[] decodedPrivatecKey = rsa.decode(encodedPrivateKey);
			PrivateKey private_key = keyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedPrivatecKey));
			
			//************************************************************************************************************
			
			//Accept Plaintext from User
			Scanner myReader = new Scanner(System.in);
			System.out.println("Enter plaintext to encrypt: ");
			String plaintext = myReader.nextLine();
			myReader.close();
			
			//Retrieve plaintext from plaintext.txt
			//String plaintext = rsa.readFile("plaintext.txt");
			
			//Encryption Process
			String encryptedMessage = rsa.encrypt(plaintext, public_key);
			System.out.println("");
			System.out.println("Encrypted Ciphertext: " + encryptedMessage);
			
			//Create Digital Signature
			String signature = rsa.signature("bad password", private_key);
			
			//Save Encrypted Message and Signature into signtext.txt
			rsa.writeSigTextFile("sigtext.txt", encryptedMessage, signature);
			
        }catch (Exception ingored){}
    }
}