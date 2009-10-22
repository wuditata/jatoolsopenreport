/*
 * Created on 2004-6-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jatools.util;

import sun.misc.BASE64Decoder;

/**
 * 
 * @author java
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 */

public class Base64Util {

	// 将 s 进行 BASE64 编码
	public static String encode(byte[] ba) {
		if (ba == null) {
			return null;
		}

		return (new sun.misc.BASE64Encoder()).encode(ba);
	}

	// 将 BASE64 编码的字符串 s 进行解码
	public static byte[] decode(String s) {
		if (s == null) {
			return null;
		}

		BASE64Decoder decoder = new BASE64Decoder();

		try {
			return decoder.decodeBuffer(s);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}