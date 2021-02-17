package component.routingNode;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routingNode.interfaces.RoutingCI;
import component.terminalNode.ConnectorTerminalNode;

public class ConnectorRouting extends ConnectorTerminalNode implements RoutingCI 
{
	
	
	@Override
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception 
	{
		// TODO Auto-generated method stub
		((RoutingCI)this.offering).updateRouting(neighbour, route);
	}

	@Override
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception 
	{
		// TODO Auto-generated method stub
		((RoutingCI)this.offering).updateAccessPoint(neighbour, numberOfHops);
	}

}
