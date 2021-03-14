package component.accessPointNode;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * classe representant l'inbound port par le quel se connecte le NetworkNode a l'accessPoint.
 * @author habibbouchenaki
 */

public class AccessPointNetworkInboundPort extends AbstractInboundPort implements NetworkNodeCI 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructeur a partir d'un composant.
	 * @param owner
	 * @throws Exception
	 */
	public AccessPointNetworkInboundPort(ComponentI owner) throws Exception
	{
		super(NetworkNodeCI.class, owner);
		assert(owner instanceof AccessPointNode);
	}
	
	/**
	 * constructeur a partir d'une URI et d'un composant.
	 * @param uri
	 * @param owner
	 * @throws Exception
	 */
	public AccessPointNetworkInboundPort(String uri, ComponentI owner) throws Exception
	{
		super(uri, NetworkNodeCI.class, owner);
		assert(owner instanceof AccessPointNode);
		
	}
	
	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		// TODO Auto-generated method stub
		
		this.getOwner().handleRequest(c -> {((AccessPointNode) c).transmitMessage(m); return null;});
	}

	@Override
	public void transmitAddress(NetworkAddressI addr) throws Exception
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(c -> {((AccessPointNode) c).transmitAddress(addr); return null;});

	}

}
