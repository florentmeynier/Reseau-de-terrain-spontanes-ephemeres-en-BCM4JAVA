package component.networkNode;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * classe representant le port entrant d'un NetworkNode.
 * @author habibbouchenaki
 */
public class NetworkNodeInbound extends AbstractInboundPort implements NetworkNodeCI
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
	public NetworkNodeInbound(ComponentI owner) throws Exception
	{
		super(NetworkNodeCI.class, owner);
		assert(owner instanceof NetworkNode);
	}
	
	/**
	 * constructeur a partir d'une URI et d'un composant.
	 * @param uri
	 * @param owner
	 * @throws Exception
	 */
	public NetworkNodeInbound(String uri, ComponentI owner) throws Exception
	{
		super(uri, NetworkNodeCI.class, owner);
		
	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().runTask(c -> {
			try {
				((NetworkNode) c).transmitMessage(m);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	@Override
	public void transmitAddress(NetworkAddressI addr) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().runTask(c -> {
			try {
				((NetworkNode) c).transmitAddress(addr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

}
