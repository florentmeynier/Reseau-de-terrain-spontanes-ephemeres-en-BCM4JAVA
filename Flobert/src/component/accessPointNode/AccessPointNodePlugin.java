package component.accessPointNode;

import java.util.Set;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NetworkAddressI;
import component.registration.interfaces.NodeAddressI;
import component.routingNode.RouteInfo;
import component.routingNode.interfaces.RoutingCI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;

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
	
	protected AccessPointCommInboundPortPlugin appcip;
	protected AccessPointNodeRoutInboundPortPlugin apprip;
	protected AccessPointNodeNetInboundPortPlug appnip;
	
	public AccessPointNodePlugin()
	{
		super();
	}
	
	@Override
	public void uninstall() throws Exception {
		// TODO Auto-generated method stub
		this.appcip.unpublishPort();
		this.apprip.unpublishPort();
		this.appnip.unpublishPort();
		this.appcip.destroyPort();
		this.apprip.destroyPort();
		this.appnip.destroyPort();
		this.removeOfferedInterface(CommunicationCI.class);
		this.removeOfferedInterface(RoutingCI.class);
		this.removeOfferedInterface(NetworkNodeCI.class);
	}



	@Override
	public void installOn(ComponentI owner) throws Exception {
		// TODO Auto-generated method stub
		super.installOn(owner);
		assert(owner instanceof AccessPointNode);
	}



	@Override
	public void initialise() throws Exception {
		// TODO Auto-generated method stub
		super.initialise();
		this.addOfferedInterface(CommunicationCI.class);
		this.addOfferedInterface(RoutingCI.class);
		this.appcip = new AccessPointCommInboundPortPlugin(this.getOwner(),this.getPluginURI());
		this.apprip = new AccessPointNodeRoutInboundPortPlugin(this.getOwner(),this.getPluginURI());
		this.appnip = new AccessPointNodeNetInboundPortPlug(this.getOwner(),this.getPluginURI());
	}
	
	
	/**
	 * appelle connect sur l'AccessPoint.
	 * @param address
	 * @param communicationInboundPortURI
	 * @throws Exception
	 */
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		((AccessPointNode)this.getOwner()).connect(address, communicationInboundPortURI);
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
		((AccessPointNode)this.getOwner()).connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}
	
	/**
	 * appelle updateRouting sur l'AccessPoint.
	 * @param neighbour
	 * @param routes
	 * @throws Exception
	 */
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		((AccessPointNode)this.getOwner()).updateRouting(neighbour, routes);
	}
	
	/**
	 * appelle updateAccessPoint sur l'AccessPoint.
	 * @param neighbour
	 * @param numberOfHops
	 * @throws Exception
	 */
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		((AccessPointNode)this.getOwner()).updateAccessPoint(neighbour, numberOfHops);
	}
	
	/**
	 * appelle hasRouteFor sur l'AccessPoint.
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return ((AccessPointNode)this.getOwner()).hasRouteFor(address);
	}
	
	/**
	 * appelle transmitMessage sur l'AccessPoint.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception
	{
		((AccessPointNode)this.getOwner()).transmitMessage(m);
	}

	public void ping() throws Exception
	{
		// TODO Auto-generated method stub
		((AccessPointNode)this.getOwner()).ping();
	}

	public void transmitAddress(NetworkAddressI addr) throws Exception
	{
		// TODO Auto-generated method stub
		((AccessPointNode)this.getOwner()).transmitAddress(addr);
		
	}
	
}
