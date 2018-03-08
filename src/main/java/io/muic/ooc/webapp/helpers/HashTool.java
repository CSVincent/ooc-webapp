package io.muic.ooc.webapp.helpers;

import org.mindrot.jbcrypt.BCrypt;

public class HashTool {
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static  boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
