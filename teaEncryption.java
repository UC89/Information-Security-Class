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

	public static void main(String args[]) {
		String key = "A56D";
		String keyString1 = "0xA56BABCD0000F000FFFFFFFFABCDEF01";

		Long[] keyArray = convertStringToArray(keyString1);
		Long keyStringNew = Long.parseLong(key,16);
		System.out.println("Int to string: "+keyStringNew);
		System.out.println("String[0]: "+keyArray[0]);
		System.out.println("Print entire key: "+keyArray[0]+","+keyArray[1]+","+keyArray[2]+","+keyArray[3]);

		System.out.println("In TEA");
	}
}