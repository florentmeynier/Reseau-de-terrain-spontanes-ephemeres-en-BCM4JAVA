package component.terminalNode;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * classe representant un InboundPort d'un noeud terminal.
 * @author florentmeynier
 *
 */
public class TerminalNodeInbound extends AbstractInboundPort implements CommunicationCI {

	private static final long serialVersionUID = 1L;

	/**
	 * constructeur a partir d'un composant.
	 * @param owner
	 * @throws Exception
	 */
	public TerminalNodeInbound(ComponentI owner) throws Exception
	{
		super(CommunicationCI.class, owner);
		assert(owner instanceof TerminalNode);
	}
	
	/**
	 * constructeur a partir d'une URI et d'un composant.
	 * @param uri
	 * @param owner
	 * @throws Exception
	 */
	public TerminalNodeInbound(String uri, ComponentI owner) throws Exception
	{
		super(uri, CommunicationCI.class, owner);
		assert(owner instanceof TerminalNode);
	}
	
	@Override
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception 
	{
		this.getOwner().handleRequest(c -> {((TerminalNode) c).connect(address, communicationInboundPortURI); return null;});

	}

	@Override
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception 
	{
		this.getOwner().handleRequest(c -> {((TerminalNode) c).connectRouting(address, communicationInboundPortURI,routingInboundPortURI); return null;});

	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		this.getOwner().handleRequest(c -> {((TerminalNode) c).transmitMessage(m); return null;});

	}

	@Override
	public boolean hasRouteFor(AddressI address) throws Exception 
	{
		return this.getOwner().handleRequest(c -> ((TerminalNode) c).hasRouteFor(address));
	}

	@Override
	public void ping() throws Exception 
	{
		this.getOwner().handleRequest(c -> {((TerminalNode) c).ping(); return null;});
	}
	
}
