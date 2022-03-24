import java.util.Scanner;
import java.io.*;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class GenerateKeyPair {
	//Initialize Global Variables
    private PrivateKey privateKey;
    private PublicKey publicKey;
	private final int KEY_SIZE = 2048;
	
	//Initialize Key Generator
    public void init() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(KEY_SIZE);
		KeyPair pair = generator.generateKeyPair();
		privateKey = pair.getPrivate();
		publicKey = pair.getPublic();
    }
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public void writeKeyPairFile(String filename, String public_key, String private_key) throws Exception {
		FileWriter fw = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(public_key+"\n");
		bw.write(private_key+"\n");
		bw.close();
	}
	
	//Convert encrypted message from byte array to String
    private String encode(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    public static void main(String[] args) {
        try{
            //Initialization
			GenerateKeyPair key_generator = new GenerateKeyPair();
			key_generator.init();
			
			PrivateKey private_key = key_generator.getPrivateKey();
			PublicKey public_key = key_generator.getPublicKey();
			
			//Save Key Pair into keypair.txt
			String encodedPublicKey  = key_generator.encode(public_key.getEncoded());
			String encodedPrivateKey = key_generator.encode(private_key.getEncoded());
			key_generator.writeKeyPairFile("keypair.txt", encodedPublicKey, encodedPrivateKey);
			
			
        }catch (Exception ingored){}
    }
}