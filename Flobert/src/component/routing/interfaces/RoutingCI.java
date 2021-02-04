package component.routing.interfaces;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routing.RouteInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;

public interface RoutingCI extends OfferedCI 
{
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception;
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception;
	
}
