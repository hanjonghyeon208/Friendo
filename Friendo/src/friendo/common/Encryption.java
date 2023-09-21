 //SHA512 암호화 클래스


package friendo.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class Encryption {

	// Salt 난수 생성
	public static String Salt() {

		String salt = "";

		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] bytes = new byte[16]; //길이가 16인 바이트 배열 생성
			random.nextBytes(bytes); //랜덤한 바이트를 바이트 배열에 저장
			salt = new String(Base64.getEncoder().encode(bytes)); //Base64 인코딩을 사용하여 바이트 배열을 문자열로 변환한 후, 이를 salt 변수에 할당
		} catch (NoSuchAlgorithmException e) {
			System.out.println("난수 생성 오류");
			e.printStackTrace();
		}

		return salt; //생성된 salt값 반환

	}

	// SHA512 암호화
	public static String SHA512(String password, String hash) {

		String salt = password + hash; //hash와 password를 결합하여 salt라는 문자열을 생성
		String hex = null; 

		try {
			MessageDigest msg = MessageDigest.getInstance("SHA-512"); //SHA-512" 알고리즘을 사용하는 MessageDigest 객체 생성
			msg.update(salt.getBytes());

			hex = String.format("%128x", new BigInteger(1, msg.digest())); 
			//digest() 메서드를 호출하여 암호화된 결과를 얻고, 이를 16진수로 포맷팅하여 hex 변수에 할당
		} catch (Exception e) {
			System.out.println("암호화 오류");
			e.printStackTrace();
		}

		return hex; //생성된 hex값 반환

	}

}
