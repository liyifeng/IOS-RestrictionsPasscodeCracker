import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;


public class CrackTools {
	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	// 生成密文的长度
	private static final int HASH_SIZE = 40;

	// 迭代次数
	private static final int PBKDF2_ITERATIONS = 1000;

	/**
	 * 根据password和salt生成密文
	 * 
	 */
	public static String getPBKDF2(String password, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 将16进制字符串形式的salt转换成byte数组
		byte[] bytes = DatatypeConverter.parseHexBinary(salt);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, PBKDF2_ITERATIONS, HASH_SIZE * 4);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
		// 将byte数组转换为16进制的字符串
		return DatatypeConverter.printHexBinary(hash);
	}

	public static String creak(String key, String salt) {
		String decodeKey = decode(key);

		for (int i = 9999; i > 0; i--) {
			String password = String.valueOf(i < 10 ? "000" + i : (i < 100 ? "00" + i : (i < 1000 ? "0" + i : i)));

			try {
				String code = PBKDF2.getPBKDF2(password, decode(salt));

				if (decodeKey.equalsIgnoreCase(code)) {
					return password;
				}

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String decode(String str) {
		return bytesToHexString(Base64.getDecoder().decode(str));
	}

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		String RestrictionsPasswordKey = "";
		String RestrictionsPasswordSalt = "";
		String password = creak(RestrictionsPasswordKey, RestrictionsPasswordSalt);
		System.out.println("破解完成，密码是：" + password);
	}
}
