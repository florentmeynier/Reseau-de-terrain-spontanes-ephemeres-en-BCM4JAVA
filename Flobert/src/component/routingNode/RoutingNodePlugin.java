package component.routingNode;

import java.util.Set;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.routingNode.interfaces.RoutingCI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;

/**
 * classe representant un greffon pour le role RoutingNode.
 * @author florentmeynier
 *
 */
public class RoutingNodePlugin extends AbstractPlugin 
{
	private static final long serialVersionUID = 1L;
	
	protected RoutingNodeCommInboundPortPlugin rtpcip;
	protected RoutingNodeRoutInboundPortPlugin rtprip;
	
	public RoutingNodePlugin()
	{
		super();
	}
	
	
	
	@Override
	public void uninstall() throws Exception {
		// TODO Auto-generated method stub
		this.rtpcip.unpublishPort();
		this.rtprip.unpublishPort();
		this.rtpcip.destroyPort();
		this.rtprip.destroyPort();
		this.removeOfferedInterface(CommunicationCI.class);
		this.removeOfferedInterface(RoutingCI.class);
	}



	@Override
	public void installOn(ComponentI owner) throws Exception {
		// TODO Auto-generated method stub
		super.installOn(owner);
		assert(owner instanceof RoutingNode);
	}



	@Override
	public void initialise() throws Exception {
		// TODO Auto-generated method stub
		super.initialise();
		this.addOfferedInterface(CommunicationCI.class);
		this.addOfferedInterface(RoutingCI.class);
		this.rtpcip = new RoutingNodeCommInboundPortPlugin(this.getOwner(),this.getPluginURI());
		this.rtprip = new RoutingNodeRoutInboundPortPlugin(this.getOwner(),this.getPluginURI());
	}
	
	/**
	 * appelle connect sur le RoutingNode.
	 * @param address
	 * @param communicationInboundPortURI
	 * @throws Exception
	 */
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		((RoutingNode)this.getOwner()).connect(address, communicationInboundPortURI);
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
		((RoutingNode)this.getOwner()).connectRouting(address, communicationInboundPortURI,routingInboundPortURI);
	}
	
	/**
	 * appelle updateRouting sur le RoutingNode.
	 * @param neighbour
	 * @param routes
	 * @throws Exception
	 */
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		((RoutingNode)this.getOwner()).updateRouting(neighbour, routes);
	}
	
	/**
	 * appelle transmitMessage sur le RoutingNode.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception
	{
		((RoutingNode)this.getOwner()).transmitMessage(m);
	}

	/**
	 * appelle hasRouteFor sur le RoutingNode.
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return ((RoutingNode)this.getOwner()).hasRouteFor(address);
	}
	
	/**
	 * appelle updateAccessPoint sur le RoutingNode.
	 * @param neighbour
	 * @param numberOfHops
	 * @throws Exception
	 */
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		((RoutingNode)this.getOwner()).updateAccessPoint(neighbour, numberOfHops);
	}

	public void ping() throws Exception
	{
		// TODO Auto-generated method stub
		((RoutingNode)this.getOwner()).ping();
	}
	
}
