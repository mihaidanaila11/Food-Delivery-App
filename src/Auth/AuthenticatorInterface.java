package Auth;

import Users.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Objects;

public interface AuthenticatorInterface {
    User register(String firstName, String lastName, String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException;

    default PasswordHash passwordHash(String password, byte[] passwordSalt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random = new SecureRandom();
        byte[] salt = Objects.requireNonNullElseGet(passwordSalt, () -> {
            byte[] newSalt = new byte[16];
            random.nextBytes(newSalt);

            return newSalt;
        });
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return new PasswordHash(factory.generateSecret(spec).getEncoded(), salt);
    }

    default boolean checkPassword(String givenPassword, PasswordHash givenPasswordHash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Arrays.equals(passwordHash(givenPassword, givenPasswordHash.getPasswordSalt()).getPasswordHash(), givenPasswordHash.getPasswordHash());
    }
}
