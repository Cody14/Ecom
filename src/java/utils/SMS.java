package utils;

// Install the Java helper library from twilio.com/docs/java/install

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SMS {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACd41a133cb7e5d241f25c106bcaa6a538";
    public static final String AUTH_TOKEN = "1356c2d34d5b48e094031a976dfacbe6";

    public static void main(String[] args) {
        
       String to_number = "+250781440051";
       String from_number = "+18563910106";
       String messageBody = "Account \n Hello order hava been accepted successfuly\n pay code : *182*23*23#";
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(to_number), // TO
                new com.twilio.type.PhoneNumber(from_number), // FROM
                messageBody)
            .create();

        System.out.println(message.getSid());
            
        } catch (Exception e) {
            System.out.println("Errrror "+e);
        }
        
        
    }
    
}
