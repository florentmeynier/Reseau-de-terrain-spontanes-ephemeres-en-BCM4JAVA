package component.routingNode;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routing.RouteInfo;
import component.routing.interfaces.RoutingCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class RoutingNodeInboundPort extends AbstractInboundPort implements RoutingCI
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RoutingNodeInboundPort(ComponentI owner) throws Exception
	{
		super(RoutingCI.class, owner);
		assert(owner instanceof RoutingNode);
	}
	
	public RoutingNodeInboundPort(String uri, ComponentI owner) throws Exception
	{
		super(uri, RoutingCI.class, owner);
		
	}
	
	@Override
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(c -> {((RoutingNode) c).updateRouting(neighbour, route); return null;});

	}

	@Override
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(c -> {((RoutingNode) c).updateAccessPoint(neighbour, numberOfHops); return null;});

	}

}
