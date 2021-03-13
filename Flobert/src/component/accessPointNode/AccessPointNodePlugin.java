package component.accessPointNode;

import java.util.Set;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NetworkAddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.routingNode.RouteInfo;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;

public class AccessPointNodePlugin extends AbstractPlugin 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AccessPointNode apn;
	
	protected AccessPointNodePlugin(NodeAddressI addr, PositionI pos, double portee) throws Exception
	{
		apn = new AccessPointNode(addr, pos, portee);
	
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		apn.connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}
	
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		apn.updateRouting(neighbour, routes);
	}
	
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		apn.updateAccessPoint(neighbour, numberOfHops);
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return apn.hasRouteFor(address);
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		apn.transmitMessage(m);
	}
	
	public void transmitAddress(NetworkAddressI addr) throws Exception
	{
		apn.transmitAddress(addr);
	}
}
