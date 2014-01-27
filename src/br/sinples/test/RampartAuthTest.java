package br.sinples.test;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.impl.dom.SOAPHeaderBlockImpl;
import org.apache.axiom.soap.impl.dom.soap11.SOAP11Factory;
import org.apache.axiom.soap.impl.dom.soap11.SOAP11HeaderBlockImpl;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;

import br.sinples.test.IdentityWSIStub.Login;
import br.sinples.test.IdentityWSIStub.LoginResponse;


public class RampartAuthTest {

	private static final String SERVICE_EPR = "http://127.0.0.1:8080/IdentityWS/services/IdentityWSI";
	
	private static final String SERVICE_RAMPART = "/home/camiloperic/Enviroment/apache-tomcat-7.0.42/webapps/IdentityWS/WEB-INF/modules/rampart-1.6.2.mar";
	
	public static void main (String[] args) {
        
        IdentityWSIStub service = null;
        Options options = null;
        ConfigurationContext configContext = null;
        
        try {

        configContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);

        service = new IdentityWSIStub(configContext, SERVICE_EPR);

        // engage rampart module

        options = service._getServiceClient().getOptions();
        
        SOAP11Factory factory = new SOAP11Factory();
        OMNamespace SecurityElementNamespace = factory.createOMNamespace("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702", "wss");
        OMElement usernameTokenEl = factory.createOMElement("UsernameToken", SecurityElementNamespace);

        OMElement usernameEl = factory.createOMElement("Username", SecurityElementNamespace);
        usernameEl.setText("123");
        usernameTokenEl.addChild(usernameEl);

        OMElement passwordEl = factory.createOMElement("Password", SecurityElementNamespace);
        passwordEl.setText("123");
        usernameTokenEl.addChild(passwordEl);

        SOAPHeaderBlockImpl block = new SOAP11HeaderBlockImpl("Security", SecurityElementNamespace, factory);
        block.addChild(usernameTokenEl);

        service._getServiceClient().addHeader(block);
        
        // add username/password credentials
        options.setUserName("bob");
        options.setPassword("bobPW");
        Login l = new Login();
        l.setPassword("Lalala");
        l.setUsername("Lerere");
//        service._getServiceClient().engageModule("rampart");
        LoginResponse lr = service.login(l);
        System.out.println("");
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
}
