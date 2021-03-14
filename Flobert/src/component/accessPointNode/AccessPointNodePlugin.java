package component.accessPointNode;

import java.util.Set;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.routingNode.RouteInfo;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;

/**
 * classe representant un greffon pour le role AccessPoint.
 * @author habibbouchenaki
 */
public class AccessPointNodePlugin extends AbstractPlugin 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AccessPointNode apn;
	
	/**
	 * construit l'AccessPoint necessaire au plugin.
	 * @param addr
	 * @param pos
	 * @param portee
	 * @throws Exception
	 */
	protected AccessPointNodePlugin(NodeAddressI addr, PositionI pos, double portee) throws Exception
	{
		apn = new AccessPointNode(addr, pos, portee);
	
	}
	
	/**
	 * appelle connect sur l'AccessPoint.
	 * @param address
	 * @param communicationInboundPortURI
	 * @throws Exception
	 */
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		apn.connect(address, communicationInboundPortURI);
	}
	
	/**
	 * appelle connectRouting sur l'AccessPoint.
	 * @param address
	 * @param communicationInboundPortURI
	 * @param routingInboundPortURI
	 * @throws Exception
	 */
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		apn.connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}
	
	/**
	 * appelle updateRouting sur l'AccessPoint.
	 * @param neighbour
	 * @param routes
	 * @throws Exception
	 */
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		apn.updateRouting(neighbour, routes);
	}
	
	/**
	 * appelle updateAccessPoint sur l'AccessPoint.
	 * @param neighbour
	 * @param numberOfHops
	 * @throws Exception
	 */
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		apn.updateAccessPoint(neighbour, numberOfHops);
	}
	
	/**
	 * appelle hasRouteFor sur l'AccessPoint.
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return apn.hasRouteFor(address);
	}
	
	/**
	 * appelle transmitMessage sur l'AccessPoint.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception
	{
		apn.transmitMessage(m);
	}
	
}
