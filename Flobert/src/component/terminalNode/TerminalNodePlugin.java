package component.terminalNode;

import component.registration.NodeAddress;
import component.registration.Position;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;

public class TerminalNodePlugin extends AbstractPlugin {

	private static final long serialVersionUID = 1L;
	
	private TerminalNode node;
	
	public TerminalNodePlugin(NodeAddress addr, Position pos, double portee) throws Exception
	{
		node = new TerminalNode(addr, pos, portee);
	}
	
	public NodeAddressI getAddr() 
	{
		return node.getAddr();
	}
	
	public PositionI getPos() 
	{
		return node.getPos();
	}
	
	public double getPortee()
	{
		return node.getPortee();
	}
	
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		node.connect(address, communicationInboundPortURI);
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		node.connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		node.transmitMessage(m);
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return node.hasRouteFor(address);
	}
	
	public void ping() throws Exception
	{
		node.ping();
	}
}
