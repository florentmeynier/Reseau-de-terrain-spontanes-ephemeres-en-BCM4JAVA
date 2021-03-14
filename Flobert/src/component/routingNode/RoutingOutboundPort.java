package component.routingNode;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routingNode.interfaces.RoutingCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * classe representant l'outboundPort d'un noeud routeur.
 * @author florentmeynier
 *
 */
public class RoutingOutboundPort extends AbstractOutboundPort implements RoutingCI 
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
	public RoutingOutboundPort(ComponentI owner) throws Exception 
	{
		super(RoutingCI.class, owner);
	}
	
	/**
	 * constructeur a partir d'une URI et d'un composant.
	 * @param uri
	 * @param owner
	 * @throws Exception
	 */
	public RoutingOutboundPort(String uri, ComponentI owner) throws Exception 
	{
		super(uri, RoutingCI.class, owner);
	}
	
	@Override
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception {
		// TODO Auto-generated method stub
		((RoutingCI) this.getConnector()).updateRouting(neighbour, routes);

	}

	@Override
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception {
		// TODO Auto-generated method stub
		((RoutingCI) this.getConnector()).updateAccessPoint(neighbour, numberOfHops);
	}

}
