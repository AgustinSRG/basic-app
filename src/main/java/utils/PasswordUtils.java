package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Utils for secure passwords.
 *
 */
public class PasswordUtils {
	/**
	 * Generates a new random salt.
	 * @return The salt.
	 */
	public static String randomSalt() {
		byte[] bytes = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return Base64.encodeBase64URLSafeString(bytes);
	}
	
	/**
	 * Computes a password hash (SHA-256)
	 * @param password The password
	 * @param salt The salt
	 * @return The hash
	 */
	public static String hashPassword(String password, String salt) {
		MessageDigest digest;
		
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		digest.update(password.getBytes());
		digest.update(salt.getBytes());
		
		return Base64.encodeBase64URLSafeString(digest.digest());
	}
}
