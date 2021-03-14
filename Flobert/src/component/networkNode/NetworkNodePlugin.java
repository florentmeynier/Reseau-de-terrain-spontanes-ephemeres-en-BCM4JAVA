package component.networkNode;

import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;

/**
 * classe representant un greffon pour le role NetworkNode.
 * @author habibbouchenaki
 */
public class NetworkNodePlugin extends AbstractPlugin 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private NetworkNode nn;
	
	/**
	 * instancie le NetworkNode a partir de l'addr en parametre.
	 * @param addr
	 * @throws Exception
	 */
	protected NetworkNodePlugin(NetworkAddressI addr) throws Exception 
	{
		nn = new NetworkNode(addr);
	}
	
	/**
	 * appelle transmitMessage sur le NetworkNode.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception
	{
		nn.transmitMessage(m);
	}
	
	/**
	 * appelle transmitAddresse sur le NetworkNode.
	 * @param addr
	 * @throws Exception
	 */
	public void transmitAddress (NetworkAddressI addr) throws Exception
	{
		nn.transmitAddress(addr);
	}

}
