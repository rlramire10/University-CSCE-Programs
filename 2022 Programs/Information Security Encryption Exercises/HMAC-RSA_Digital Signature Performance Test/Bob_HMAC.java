import java.util.Scanner;
import java.io.*;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Bob_HMAC {
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
			Bob_HMAC hmac = new Bob_HMAC();
			hmac.init();
			
			//Retreive plaintext from plaintext.txt
			String plaintext = hmac.readFile("plaintext.txt");
			
			//Retreive Secret Key from key.txt
			String encodedkey = hmac.readFile("key.txt");
			byte[] decodedkey = hmac.decode(encodedkey);
			SecretKey key = new SecretKeySpec(decodedkey, 0, decodedkey.length, "HmacSHA256");
			
			//Retreive mactext from mactext.txt
			String orig_mactext = hmac.readFile("mactext.txt");
			
			//Create new mactext
			String test_mactext = hmac.HMAC(plaintext, key);
			
			//Verification
			if (test_mactext.equals(orig_mactext)) {
				//System.out.println("Verification Successful");
			}
			else {
				//System.out.println("Verification Failed");
			}
			
		} catch (Exception ignored) {
        }
	}
}