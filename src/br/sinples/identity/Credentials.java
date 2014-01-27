package br.sinples.identity;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class Credentials implements CallbackHandler{

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		// TODO Auto-generated method stub
		System.out.println("################################################\n################################################");
		WSPasswordCallback pwcb = (WSPasswordCallback)callbacks[0];
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pwcbi = (WSPasswordCallback)callbacks[i];
			System.out.println("usage is " + pwcbi.getUsage());
			System.out.println("identifier is " + pwcbi.getIdentifier());
			System.out.println("password is " + pwcbi.getPassword());
		}
        
        String id = pwcb.getIdentifier();
        System.out.println("password is " + pwcb.getPassword());
        int usage = pwcb.getUsage();
        System.out.println("usage is " + usage);
        if(usage == WSPasswordCallback.USERNAME_TOKEN ) {
            // Logic to get the password to build the username token
        	if ("Administrator".equals(id)) {
                pwcb.setPassword("secured");
                System.out.println("set passwd!");
                return;
            }
        } else if (usage == WSPasswordCallback.SIGNATURE || usage == WSPasswordCallback.DECRYPT ) {
            // Logic to get the private key password for signature or decryption
        	if ("client".equals(id)) {
                if (pwcb.getPassword().equals("pass"))
                	System.out.println("client passwd nice!");
            }
        } 
	}
	
}
