import java.util.Scanner;
import java.io.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Alice_HMAC {
	private SecretKey Key;

	public void init() throws Exception {
		//Generate Key
		KeyGenerator key_generator = KeyGenerator.getInstance("HmacSHA256");
		Key = key_generator.generateKey();
	}
	
	public SecretKey getKey() {
		return Key;
	}
	
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
	
	public void writeFile(String filename, String text) throws Exception {
		FileWriter fw = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(text+"\n");
		bw.close();
	}
	
	public String HMAC(String input, SecretKey key) throws Exception {
		//Create Secretkey Spec
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getEncoded(), "HmacSHA256");
		
		//Create MAC utilizing SHA_256
		Mac mac = Mac.getInstance("HmacSHA256");
		
		//Initialize MAC with Secretkey Spec
		mac.init(secretKeySpec);
		
		//Return encrypted hex as String
		return encode(mac.doFinal(input.getBytes()));
	}
	
	//Convert encrypted message from byte array to String
	private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
	
	//Convert decrypted message from String to byte array
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
	
	public static void main(String[] args) {
		try {
			//Initialization
			Alice_HMAC hmac = new Alice_HMAC();
			hmac.init();
			SecretKey key = hmac.getKey();
			
			//Retrieve plaintext from plaintext.txt
			String plaintext = hmac.readFile("plaintext.txt");
			
			//Encryption
			String mactext = hmac.HMAC(plaintext, key);
			
			//Save plaintext into plaintext.txt
			hmac.writeFile("plaintext.txt", plaintext);
			
			//Save Secret Key into key.txt
			String encodedKey = hmac.encode(key.getEncoded());
			hmac.writeFile("key.txt", encodedKey);
			
			//Save mactext into mactext.txt
			hmac.writeFile("mactext.txt", mactext);
			
		} catch (Exception ignored) {
        }
	}
}