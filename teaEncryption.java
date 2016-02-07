import java.io.*;



public class teaEncryption {

	public String openFile(String path) {
		// FileReader fileReader = new FileReader(path);
		return "Return String";
	}

	public static Long[] convertStringToArray(String keyString) {
		//int foo = Integer.parseInt("1234");
		Long[] returnArray = new Long[4];
		returnArray[0] = Long.parseLong(keyString.substring(2,10),16);
		returnArray[1] = Long.parseLong(keyString.substring(10,18),16);
		returnArray[2] = Long.parseLong(keyString.substring(18,26),16);
		returnArray[3] = Long.parseLong(keyString.substring(26,34),16);
		return returnArray;
	}

	public static String encrypt(Long[] keyArray,Long delta,String plainText) {
		Long sum = 0L;
		Long left =  Long.parseLong(plainText.substring(2,10),16);
		Long right = Long.parseLong(plainText.substring(10,18),16);
		Long bitMask = 0x00000000FFFFFFFFL;

		for (int i=0; i<32; i++) {
			sum += delta;
			sum = sum & bitMask;
			left += ((right<<4)+keyArray[0]) ^ (right+sum) ^ ((right>>>5)+keyArray[1]);
			left = left & bitMask;
			right += ((left<<4)+keyArray[2]) ^ (left+sum) ^ ((left>>>5)+keyArray[3]);
			right = right & bitMask;

			sum = sum & bitMask;
		}

		right = right & bitMask;
		left = left & bitMask;

		String cipherText = left+""+right;
		System.out.println("Encrypted: "+Long.toHexString(left)+""+Long.toHexString(right));
		return cipherText;
	}

	public static String decrypt(Long[] keyArray,Long delta, String encryptedText) {

		Long sum = 0L;
		Long left =  Long.parseLong(encryptedText.substring(2,10),16);
		Long right = Long.parseLong(encryptedText.substring(10,18),16);
		Long bitMask = 0x00000000FFFFFFFFL;
		for (int i=0; i<32; i++) {
			sum += delta;
			sum = sum & bitMask;
			right -= ((left<<4)+keyArray[2]) ^ (left+sum) ^ ((left>>>5)+keyArray[3]);
			right = right & bitMask;
			left -= ((right<<4)+keyArray[0]) ^ (right+sum) ^ ((right>>>5)+keyArray[1]);
			left = left & bitMask;
			sum = sum & bitMask;
		}

		right = right & bitMask;
		left = left & bitMask;
		String unencryptedText = Long.toHexString(left)+""+Long.toHexString(right);
		System.out.println("Decrypted: "+Long.toHexString(left)+""+Long.toHexString(right));
		return unencryptedText;
	}


	public static void main(String args[]) {
		String keyString1 = "0xA56BABCD0000F000FFFFFFFFABCDEF01";
		Long delta = Long.parseLong("9e3779b9",16);

		Long[] keyArray = convertStringToArray(keyString1);

		String plain = "0x0123456789ABCDEF";

		String encryptedText = encrypt(keyArray,delta,plain);
		System.out.println("Encrypted: "+encryptedText);
		String decryptedText = decrypt(keyArray,delta,encryptedText);
		System.out.println("Decrypted: "+decryptedText);


	}
}