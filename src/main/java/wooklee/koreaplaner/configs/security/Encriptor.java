package wooklee.koreaplaner.configs.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encriptor {
        public static String sha256(String password) throws NoSuchAlgorithmException {
            StringBuffer sb = new StringBuffer();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            for (byte b : md.digest()) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }

}
