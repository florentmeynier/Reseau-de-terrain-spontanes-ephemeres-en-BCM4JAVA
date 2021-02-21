package component.networkNode;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class NetworkNodeOutbound extends AbstractOutboundPort implements NetworkNodeCI 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NetworkNodeOutbound(ComponentI owner) throws Exception 
	{
		super(NetworkNodeCI.class, owner);
	}
	
	public NetworkNodeOutbound(String uri, ComponentI owner) throws Exception 
	{
		super(uri, NetworkNodeCI.class, owner);
	}
	
	@Override
	public void connect(NetworkAddressI address, String networkNodeInboundPortURI) throws Exception 
	{
		// TODO Auto-generated method stub
		((NetworkNodeCI) this.getConnector()).connect(address, networkNodeInboundPortURI);

	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		// TODO Auto-generated method stub
		((NetworkNodeCI) this.getConnector()).transmitMessage(m);


	}

}
