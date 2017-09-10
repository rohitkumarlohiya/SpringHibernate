package com.mcloud.utils;

public class StringToHex {

	public static String convertStringToHex(String str) {

		char[] chars = str.toCharArray();

		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString(chars[i]));
		}

		return hex.toString();
	}

	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}
		// System.out.println("Decimal : " + temp.toString());

		return sb.toString();
	}

	public static void main(String[] args) {

		System.out.println("\n***** Convert ASCII to Hex *****");
		String str = "I Love Java!";
		System.out.println("Original input : " + str);

		String hex = StringToHex.convertStringToHex(str);

		System.out.println("Hex : " + hex);

		System.out.println("\n***** Convert Hex to ASCII *****");
		System.out.println("Hex : " + hex);
		System.out.println("ASCII : " + StringToHex.convertHexToString(hex));
	}
}
