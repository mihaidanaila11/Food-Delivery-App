package Auth;

public class PasswordHash {
    private final byte[] passwordHash;
    private final byte[] passwordSalt;

    public PasswordHash(byte[] passwordHash, byte[] passwordSalt) {
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public byte[] getPasswordHash() { return passwordHash; }
    public byte[] getPasswordSalt() { return passwordSalt; }
}
