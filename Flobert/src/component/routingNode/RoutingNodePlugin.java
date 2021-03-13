package component.routingNode;

import java.util.Set;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;

public class RoutingNodePlugin extends AbstractPlugin 
{
	private static final long serialVersionUID = 1L;
	
	private RoutingNode node;
	
	public RoutingNodePlugin(NodeAddressI addr, PositionI pos, double portee) throws Exception 
	{
		node = new RoutingNode(addr, pos, portee);
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		node.connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}
	
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		node.updateRouting(neighbour, routes);
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		node.transmitMessage(m);
	}

	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return node.hasRouteFor(address);
	}
	
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		updateAccessPoint(neighbour, numberOfHops);
	}
	
}
