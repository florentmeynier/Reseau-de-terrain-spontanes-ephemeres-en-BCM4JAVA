package component.networkNode;

import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;

public class NetworkNodePlugin extends AbstractPlugin 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private NetworkNode nn;
	
	protected NetworkNodePlugin(NetworkAddressI addr) throws Exception 
	{
		nn = new NetworkNode(addr);
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		nn.transmitMessage(m);
	}
	
	public void transmitAddress (NetworkAddressI addr) throws Exception
	{
		nn.transmitAddress(addr);
	}

}
