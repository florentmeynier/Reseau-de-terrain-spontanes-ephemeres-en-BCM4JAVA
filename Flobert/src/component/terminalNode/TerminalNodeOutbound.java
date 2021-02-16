package component.terminalNode;



import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class TerminalNodeOutbound extends AbstractOutboundPort implements CommunicationCI
{

	private static final long serialVersionUID = 1L;

	public TerminalNodeOutbound(ComponentI owner) throws Exception 
	{
		super(CommunicationCI.class, owner);
	}
	
	public TerminalNodeOutbound(String uri, ComponentI owner) throws Exception 
	{
		super(uri, CommunicationCI.class, owner);
	}
	
	@Override
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception 
	{
		((CommunicationCI) this.getConnector()).connect(address, communicationInboundPortURI);
	}

	@Override
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception 
	{
		((CommunicationCI) this.getConnector()).connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		((CommunicationCI) this.getConnector()).transmitMessage(m);
	}

	@Override
	public boolean hasRouteFor(AddressI address) throws Exception 
	{
		return ((CommunicationCI) this.getConnector()).hasRouteFor(address);
	}

	@Override
	public void ping() throws Exception
	{
		((CommunicationCI) this.getConnector()).ping();
		
	}
	
}
