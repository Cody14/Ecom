/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

/**
 *
 * @author Gasana
 */
public class SendSMS {
    private final String from_number="+18563910106";
    private static final String ACCOUNT_SID = "ACd41a133cb7e5d241f25c106bcaa6a538";
    private static final String AUTH_TOKEN = "1356c2d34d5b48e094031a976dfacbe6";

    
    public void sendSMS(String to_number,String from_number, String body){
    
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(to_number), // TO
                new com.twilio.type.PhoneNumber(from_number), // FROM
                body)
            .create();
//        System.out.println("\n TO :"+to_number +"\n"
//                + "YOU ORDER HAVE BEEN ACCEPTED SUCCESSFULLY");
    }
}
