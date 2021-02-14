package component.routing;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routing.interfaces.RoutingCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;

@OfferedInterfaces(offered = {RoutingCI.class})
public class Routing extends AbstractComponent {

	protected Routing() {
		super(1, 0);
	}
	
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception {
		
	}
	
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception {
		
	}

}
