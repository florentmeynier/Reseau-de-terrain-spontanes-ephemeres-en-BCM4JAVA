package component.accessPointNode;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routingNode.RouteInfo;
import component.routingNode.interfaces.RoutingCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * classe representant le port entrant routeur d'un AccessPoint.
 * @author habibbouchenaki
 */
public class AccessPointNodeInboundPort extends AbstractInboundPort implements RoutingCI 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructeur a partir d'un composant.
	 * @param owner
	 * @throws Exception
	 */
	public AccessPointNodeInboundPort(ComponentI owner) throws Exception
	{
		super(RoutingCI.class, owner);
		assert(owner instanceof AccessPointNode);
	}
	
	/**
	 * constructeur a partir d'une URI et d'un composant.
	 * @param uri
	 * @param owner
	 * @throws Exception
	 */
	public AccessPointNodeInboundPort(String uri, ComponentI owner) throws Exception
	{
		super(uri, RoutingCI.class, owner);
		assert(owner instanceof AccessPointNode);
		
	}

	@Override
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().runTask("routage-uri",c -> {
			try {
				((AccessPointNode) c).updateRouting(neighbour, route);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().runTask("routage-uri",c -> {
			try {
				((AccessPointNode) c).updateAccessPoint(neighbour, numberOfHops);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

}
