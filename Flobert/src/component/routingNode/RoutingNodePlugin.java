package component.routingNode;

import java.util.Set;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;

/**
 * classe representant un greffon pour le role RoutingNode.
 * @author florentmeynier
 *
 */
public class RoutingNodePlugin extends AbstractPlugin 
{
	private static final long serialVersionUID = 1L;
	
	
	private RoutingNode node;
	
	/**
	 * construit le RoutingNode necessaire au plugin.
	 * @param addr
	 * @param pos
	 * @param portee
	 * @throws Exception
	 */
	public RoutingNodePlugin(NodeAddressI addr, PositionI pos, double portee) throws Exception 
	{
		node = new RoutingNode(addr, pos, portee);
	}
	
	/**
	 * appelle connect sur le RoutingNode.
	 * @param address
	 * @param communicationInboundPortURI
	 * @throws Exception
	 */
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		node.connect(address, communicationInboundPortURI);
	}
		
	/**
	 * appelle connectRouting sur le RoutingNode.
	 * @param address
	 * @param communicationInboundPortURI
	 * @param routingInboundPortURI
	 * @throws Exception
	 */
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		node.connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}
	
	/**
	 * appelle updateRouting sur le RoutingNode.
	 * @param neighbour
	 * @param routes
	 * @throws Exception
	 */
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		node.updateRouting(neighbour, routes);
	}
	
	/**
	 * appelle transmitMessage sur le RoutingNode.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception
	{
		node.transmitMessage(m);
	}

	/**
	 * appelle hasRouteFor sur le RoutingNode.
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return node.hasRouteFor(address);
	}
	
	/**
	 * appelle updateAccessPoint sur le RoutingNode.
	 * @param neighbour
	 * @param numberOfHops
	 * @throws Exception
	 */
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		updateAccessPoint(neighbour, numberOfHops);
	}
	
}
