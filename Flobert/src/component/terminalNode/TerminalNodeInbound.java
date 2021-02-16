package component.terminalNode;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class TerminalNodeInbound extends AbstractInboundPort implements CommunicationCI {

	private static final long serialVersionUID = 1L;

	public TerminalNodeInbound(ComponentI owner) throws Exception
	{
		super(CommunicationCI.class, owner);
		assert(owner instanceof TerminalNode);
	}
	
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
		((TerminalNode)this.getOwner()).transmitMessage(m);
		//this.getOwner().handleRequest(c -> {System.out.println("5");((TerminalNode) c).transmitMessage(m);System.out.println("6"); return null;});

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
