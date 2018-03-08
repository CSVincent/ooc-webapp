package io.muic.ooc.webapp.helpers;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailValidator {
    public static boolean validate(String email){
        boolean result = true;
        try{
            InternetAddress emailAddress = new InternetAddress(email);
            emailAddress.validate();
        }catch (AddressException e){
            return false;
        }
        return result;
    }
}
