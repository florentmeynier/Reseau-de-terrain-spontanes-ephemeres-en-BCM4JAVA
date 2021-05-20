package component.terminalNode;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;

/**
 * classe representant un greffon pour le role TerminalNode.
 * @author florentmeynier
 *
 */
public class TerminalNodePlugin extends AbstractPlugin {

	private static final long serialVersionUID = 1L;
	
	protected TerminalNodeInboundPortPlugin tnpip;
	
	public TerminalNodePlugin()
	{
		super();
	}
	
	
	
	@Override
	public void uninstall() throws Exception {
		// TODO Auto-generated method stub
		this.tnpip.unpublishPort();
		this.tnpip.destroyPort();
		this.removeOfferedInterface(CommunicationCI.class);
	}



	@Override
	public void installOn(ComponentI owner) throws Exception {
		// TODO Auto-generated method stub
		super.installOn(owner);
		assert(owner instanceof TerminalNode);
	}



	@Override
	public void initialise() throws Exception {
		// TODO Auto-generated method stub
		super.initialise();
		this.addOfferedInterface(CommunicationCI.class);
		this.tnpip = new TerminalNodeInboundPortPlugin(this.getOwner(),this.getPluginURI());
	}



	/**
	 * appelle connect sur le TerminalNode.
	 * @param address
	 * @param communicationInboundPortURI
	 * @throws Exception
	 */
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		((TerminalNode)this.getOwner()).connect(address, communicationInboundPortURI);
	}
	
	/**
	 * appelle transmitMessage sur le TerminalNode.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception
	{
		((TerminalNode)this.getOwner()).transmitMessage(m);
	}
	
	/**
	 * appelle hasRouteFor sur le TerminalNode.
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return ((TerminalNode)this.getOwner()).hasRouteFor(address);
	}
	
	/**
	 * appelle ping sur le TerminalNode.
	 * @throws Exception
	 */
	public void ping() throws Exception
	{
		((TerminalNode)this.getOwner()).ping();
	}
}
