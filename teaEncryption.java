/*
TEA Encryption and Decryption Assignment for CS492
By: Taylor Somma
Long integers used because they are enough bits to hold any 63 Bit message. Also when shifting left during the encryption and decryption there will be no overflow error and any bits over 32 can be masked with a bitwise AND operation.
*/

public class teaEncryption {

	public static Long[] convertStringToArray(String keyString) {
		//int foo = Integer.parseInt("1234");
		Long[] returnArray = new Long[4];
		returnArray[0] = Long.parseLong(keyString.substring(2,10),16);
		returnArray[1] = Long.parseLong(keyString.substring(10,18),16);
		returnArray[2] = Long.parseLong(keyString.substring(18,26),16);
		returnArray[3] = Long.parseLong(keyString.substring(26,34),16);
		return returnArray;
	}

	public static Long encrypt(Long[] keyArray,Long delta,Long plainText) {

		Long bitMask = 0x00000000FFFFFFFFL;
		Long sum = 0L;
		Long left =  plainText>>>32;
		Long right = plainText & bitMask;

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

		left = left << 32;
		Long cipherLong = left | right;
		return cipherLong;
	}

	public static Long decrypt(Long[] keyArray,Long delta, Long encryptedText) {

		Long bitMask = 0x00000000FFFFFFFFL;
		Long sum  = delta << 5;
		Long left =  encryptedText >>> 32;
		Long right = encryptedText & bitMask;

		for (int i=0; i<32; i++) {
			sum = sum & bitMask;
			right -= ((left<<4)+keyArray[2]) ^ (left+sum) ^ ((left>>>5)+keyArray[3]);
			right = right & bitMask;
			left -= ((right<<4)+keyArray[0]) ^ (right+sum) ^ ((right>>>5)+keyArray[1]);
			left = left & bitMask;
			sum -= delta;
			sum = sum & bitMask;
		}

		right = right & bitMask;
		left = left & bitMask;
		left = left << 32;
		Long decryptedText = left | right;
		Long decryptMask = 0x7FFFFFFFFFFFFFFFL;
		decryptedText = decryptedText & decryptMask;
		return decryptedText;
	}


	public static void main(String args[]) {
		//Key passed as string because it is too large for any of the built in java types
		String keyString1 = "0xA56BABCD0000F000FFFFFFFFABCDEF01";
		Long delta = 0x9e3779b9L;
		Long[] keyArray = convertStringToArray(keyString1);
		Long plain = 0x0123456789ABCDEFL;

		Long encryptedText = encrypt(keyArray,delta,plain);
		Long decryptedText = decrypt(keyArray,delta,encryptedText);

		//Print plain text
		System.out.println("Plain Text: " + Long.toHexString(plain));
		//Print encrypted text
		System.out.println("Encrypted: "+Long.toHexString(encryptedText));
		//Print decryption
		System.out.println("Decrypted: "+Long.toHexString(decryptedText));
	}
}