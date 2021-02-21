package component.networkNode;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class NetworkNodeInbound extends AbstractInboundPort implements NetworkNodeCI
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NetworkNodeInbound(ComponentI owner) throws Exception
	{
		super(NetworkNodeCI.class, owner);
		assert(owner instanceof NetworkNode);
	}
	
	public NetworkNodeInbound(String uri, ComponentI owner) throws Exception
	{
		super(uri, NetworkNodeCI.class, owner);
		
	}

	@Override
	public void connect(NetworkAddressI address, String networkNodeInboundPortURI) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(c -> {((NetworkNode) c).connect(address, networkNodeInboundPortURI); return null;});

	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(c -> {((NetworkNode) c).transmitMessage(m); return null;});

	}

}
