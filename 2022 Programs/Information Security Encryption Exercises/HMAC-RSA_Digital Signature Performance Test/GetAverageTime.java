import java.util.Scanner;
import java.io.*;

import java.lang.*;

public class GetAverageTime {
	
	public void writeFile(String filename, String text) throws Exception {
		FileWriter fw = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(text+"\n");
		bw.close();
	}
	
	public static void main(String[] args) {
		try {
			//Initialization
			GetAverageTime program = new GetAverageTime();
			final int repetitions = 100;
			
			//Accept Plaintext from User
			Scanner myReader = new Scanner(System.in);
			System.out.println("Enter plaintext to encrypt: ");
			String plaintext = myReader.nextLine();
			myReader.close();
			
			//Save Plain Text into plaintext.txt
			program.writeFile("plaintext.txt", plaintext);
			
			//HMAC_SHA_256 Run Loop
			//************************************************************
			//Encryption
			long startTimer1 = System.nanoTime();
			
			for(int i=0; i<repetitions; i++){
				Alice_HMAC.main(args);
			}
			
			long endTimer1  = System.nanoTime();
			long totalTime1 = endTimer1 - startTimer1;
			double totalTime1_Sec = (double) totalTime1 / 1_000_000_000;
			double averageTime1 = totalTime1_Sec / (double) repetitions;
			System.out.println("");
			System.out.println("Average HMAC Encryption Time: " + averageTime1 + " seconds");
			
			//Verification Check
			long startTimer2 = System.nanoTime();
			
			for(int i=0; i<repetitions; i++){
				Bob_HMAC.main(args);
			}
			
			long endTimer2  = System.nanoTime();
			long totalTime2 = endTimer2 - startTimer2;
			double totalTime2_Sec = (double) totalTime2 / 1_000_000_000;
			double averageTime2 = totalTime2_Sec / (double) repetitions;
			System.out.println("");
			System.out.println("Average HMAC Verification Time: " + averageTime2 + " seconds");
			//************************************************************
			
			//RSA Digital Signature Run Loop
			//************************************************************
			//Encryption
			long startTimer3 = System.nanoTime();
			
			GenerateKeyPair.main(args);
			for(int i=0; i<repetitions; i++){
				Alice_RSA_DSig.main(args);
			}
			
			long endTimer3  = System.nanoTime();
			long totalTime3 = endTimer3 - startTimer3;
			double totalTime3_Sec = (double) totalTime3 / 1_000_000_000;
			double averageTime3 = totalTime3_Sec / (double) repetitions;
			System.out.println("");
			System.out.println("Average RSA with Digital Signature Encryption Time: " + averageTime3 + " seconds");
			
			//Verification Check
			long startTimer4 = System.nanoTime();
			
			for(int i=0; i<repetitions; i++){
				Bob_RSA_DSig.main(args);
			}
			
			long endTimer4  = System.nanoTime();
			long totalTime4 = endTimer4 - startTimer4;
			double totalTime4_Sec = (double) totalTime4 / 1_000_000_000;
			double averageTime4 = totalTime4_Sec / (double) repetitions;
			System.out.println("");
			System.out.println("Average RSA With Digital Signature Verification Time: " + averageTime4 + " seconds");
			//************************************************************
		} catch (Exception ignored) {
        }
	}
}