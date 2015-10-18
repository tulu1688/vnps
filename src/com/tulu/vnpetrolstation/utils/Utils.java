package com.tulu.vnpetrolstation.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Utils {
	private static final String TAG = Utils.class.getSimpleName();

	private static List<String> splitSlice(String str, int pieceSize) {
		List<String> ret = new LinkedList<String>();
		for (int i = 0, len = str.length(); i < len; i += pieceSize) {
			if (pieceSize + i > len - 1)
				ret.add(str.substring(i, len - 1));
			else
				ret.add(str.substring(i, pieceSize + i));
		}
		return ret;
	}

	public static String decypher(String str) {
		String uriEncodedString = decodeURIComponent(str);
		List<String> slices = splitSlice(uriEncodedString, 5);
		String firstStr = slices.remove(slices.size() - 1);
		Collections.reverse(slices);
		slices.add(firstStr);

		String tmp = "";
		for (int i = 0; i < slices.size(); i++)
			tmp += slices.get(i);
		return decodeBase64(tmp);
	}

	private static String decodeURIComponent(String encodedURI) {
		char actualChar;

		StringBuffer buffer = new StringBuffer();

		int bytePattern, sumb = 0;

		for (int i = 0, more = -1; i < encodedURI.length(); i++) {
			actualChar = encodedURI.charAt(i);

			switch (actualChar) {
			case '%': {
				actualChar = encodedURI.charAt(++i);
				int hb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				actualChar = encodedURI.charAt(++i);
				int lb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character.toLowerCase(actualChar) - 'a') & 0xF;
				bytePattern = (hb << 4) | lb;
				break;
			}
			case '+': {
				bytePattern = ' ';
				break;
			}
			default: {
				bytePattern = actualChar;
			}
			}

			if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
				sumb = (sumb << 6) | (bytePattern & 0x3f);
				if (--more == 0)
					buffer.append((char) sumb);
			} else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
				buffer.append((char) bytePattern);
			} else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
				sumb = bytePattern & 0x1f;
				more = 1;
			} else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
				sumb = bytePattern & 0x0f;
				more = 2;
			} else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
				sumb = bytePattern & 0x07;
				more = 3;
			} else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
				sumb = bytePattern & 0x03;
				more = 4;
			} else { // 1111110x
				sumb = bytePattern & 0x01;
				more = 5;
			}
		}
		return buffer.toString();
	}

	private static String decodeBase64(String s) {
		int[] e = new int[1024];
		int i, b = 0, c, x, l = 0;
		int len = s.length();

		String r = "";
		String A = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

		for (i = 0; i < 64; i++) {
			e[A.charAt(i)] = i;
		}

		for (x = 0; x < len; x++) {
			c = e[s.charAt(x)];
			b = (b << 6) + c;
			l += 6;

			while (l >= 8) {
				r += fromCharCode((b >>> (l -= 8)) % 256);
			}
		}
		return r;
	}

	private static String fromCharCode(int... codePoints) {
		StringBuilder builder = new StringBuilder(codePoints.length);
		for (int codePoint : codePoints) {
			builder.append(Character.toChars(codePoint));
		}
		return builder.toString();
	}
}