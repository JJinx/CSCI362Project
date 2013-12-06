import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Vector;
import org.apache.xmlrpc.XmlRpcException;
import org.martus.clientside.ClientSideNetworkHandlerUsingXmlRpc;
import org.martus.common.crypto.MockMartusSecurity;
import org.martus.common.MartusLogger;

public class testSSLPortSelect
{
	public static void main(String[] args)
	{
		try
		{
			MartusLogger.disableLogging();
			new testSSLPortSelect(args);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	static class MockHandlerForSSL extends ClientSideNetworkHandlerUsingXmlRpc
	{
		MockHandlerForSSL() throws Exception
		{
			super("", testPorts);
		}
		
		public Object executeXmlRpc(String serverName, String method,
			Vector params, int port)
					throws MalformedURLException, XmlRpcException, IOException 
		{
			triedPorts.add(new Integer(port));
			if(failAll || port != goodPort)
				throw new IOException("Blah blah Connection refused yada yada");
			return null;
		}

		boolean failAll;
		Vector<Integer> triedPorts = new Vector<Integer>(); 
		int goodPort;
		static int[] testPorts = new int[5];
	}

	public testSSLPortSelect(String[] input) throws Exception
	{
		String server = "127.0.0.1";
		boolean checkTriedContains = false;
		
		MockMartusSecurity mockSecurity = MockMartusSecurity.createServer();
		MockHandlerForSSL handler = new MockHandlerForSSL();

		for (int i = 0; i < input.length; i++)
		{
			if( input[i].startsWith("--fail-all") )
			{
				handler.failAll = new Boolean(input[i].substring(input[i].indexOf("=")+1));
			}
			
			if( input[i].startsWith("--good-port-middle") )
			{
				handler.goodPort = new Integer(input[i].substring(input[i].indexOf("=")+1));
				handler.testPorts[0] = 80;
				handler.testPorts[2] = handler.goodPort;
				checkTriedContains = true;
			}
			
			if( input[i].startsWith("--good-port-first") )
			{
				handler.goodPort = new Integer(input[i].substring(input[i].indexOf("=")+1));
				handler.testPorts[0] = handler.goodPort;
				handler.testPorts[2] = 80;
			}
		}
		handler.testPorts[1] = 443;
		handler.testPorts[3] = 986;
		handler.testPorts[4] = 999;

		handler.getSimpleX509TrustManager().setExpectedPublicKey(mockSecurity.getPublicKeyString());
		handler.callServer(server, "method", null);
		
		System.out.println("Number of tried ports = " + handler.triedPorts.size());
		
	}
}

