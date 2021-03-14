package component.routingNode;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routingNode.interfaces.RoutingCI;
import component.terminalNode.ConnectorTerminalNode;

/**
 * Connecteur du composant RoutingNode 
 * @author florentmeynier
 *
 */
public class ConnectorRouting extends ConnectorTerminalNode implements RoutingCI 
{


	@Override
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception 
	{
		((RoutingCI)this.offering).updateRouting(neighbour, route);
	}

	@Override
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception 
	{
		((RoutingCI)this.offering).updateAccessPoint(neighbour, numberOfHops);
	}

}
