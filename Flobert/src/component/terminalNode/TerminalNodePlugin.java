package component.terminalNode;

import component.registration.NodeAddress;
import component.registration.Position;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;

/**
 * classe representant un greffon pour le role TerminalNode.
 * @author florentmeynier
 *
 */
public class TerminalNodePlugin extends AbstractPlugin {

	private static final long serialVersionUID = 1L;
	
	private TerminalNode node;
	
	public TerminalNodePlugin(NodeAddress addr, Position pos, double portee) throws Exception
	{
		node = new TerminalNode(addr, pos, portee);
	}
	
	/**
	 * appelle connect sur le TerminalNode.
	 * @param address
	 * @param communicationInboundPortURI
	 * @throws Exception
	 */
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		node.connect(address, communicationInboundPortURI);
	}
	
	/**
	 * appelle transmitMessage sur le TerminalNode.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception
	{
		node.transmitMessage(m);
	}
	
	/**
	 * appelle hasRouteFor sur le TerminalNode.
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return node.hasRouteFor(address);
	}
	
	/**
	 * appelle ping sur le TerminalNode.
	 * @throws Exception
	 */
	public void ping() throws Exception
	{
		node.ping();
	}
}
