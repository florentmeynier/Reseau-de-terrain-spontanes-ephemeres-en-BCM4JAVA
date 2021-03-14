package component.networkNode;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * classe representant le port sortant d'un NetworkNode.
 * @author habibbouchenaki
 */
public class NetworkNodeOutbound extends AbstractOutboundPort implements NetworkNodeCI 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * constructeur à partir d'un composant.
	 * @param owner
	 * @throws Exception
	 */
	public NetworkNodeOutbound(ComponentI owner) throws Exception 
	{
		super(NetworkNodeCI.class, owner);
	}
	
	/**
	 * constructeur a partir d'une URI et d'un composant.
	 * @param uri
	 * @param owner
	 * @throws Exception
	 */
	public NetworkNodeOutbound(String uri, ComponentI owner) throws Exception 
	{
		super(uri, NetworkNodeCI.class, owner);
	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		// TODO Auto-generated method stub
		((NetworkNodeCI) this.getConnector()).transmitMessage(m);


	}

	@Override
	public void transmitAddress(NetworkAddressI addr) throws Exception {
		// TODO Auto-generated method stub
		((NetworkNodeCI) this.getConnector()).transmitAddress(addr);
	}

}
