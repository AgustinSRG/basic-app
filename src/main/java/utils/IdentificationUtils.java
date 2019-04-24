package utils;

import java.security.SecureRandom;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Utils for IDs.
 *
 */
public class IdentificationUtils {
	
	/**
	 * Gets the user ID by the user name
	 * @param userName The user name
	 * @return The user ID
	 */
	public static String getUserId(String userName) {
		return userName.toLowerCase().replaceAll("[^a-z0-9_]", "");
	}
	
	/**
	 * Generates a new random ID.
	 * @return The new ID.
	 */
	public static String getRandomId() {
		byte[] bytes = new byte[10];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return Base64.encodeBase64URLSafeString(bytes);
	}
}
