package component.routingNode;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routingNode.interfaces.RoutingCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * classe representant l'inboundPort d'un noeud routeur.
 * @author florentmeynier
 *
 */
public class RoutingNodeInboundPort extends AbstractInboundPort implements RoutingCI
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
	public RoutingNodeInboundPort(ComponentI owner) throws Exception
	{
		super(RoutingCI.class, owner);
		assert(owner instanceof RoutingNode);
	}
	
	/**
	 * constructeur a partir d'une URI et d'un composant.
	 * @param uri
	 * @param owner
	 * @throws Exception
	 */
	public RoutingNodeInboundPort(String uri, ComponentI owner) throws Exception
	{
		super(uri, RoutingCI.class, owner);
		
	}
	
	@Override
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().runTask("routage-uri",c -> {
			try {
				((RoutingNode) c).updateRouting(neighbour, route);
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
				((RoutingNode) c).updateAccessPoint(neighbour, numberOfHops);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

}
