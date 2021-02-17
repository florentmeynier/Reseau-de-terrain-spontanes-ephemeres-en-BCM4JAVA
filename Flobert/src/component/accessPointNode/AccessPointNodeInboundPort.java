package component.accessPointNode;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routing.RouteInfo;
import component.routing.interfaces.RoutingCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class AccessPointNodeInboundPort extends AbstractInboundPort implements RoutingCI 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccessPointNodeInboundPort(ComponentI owner) throws Exception
	{
		super(RoutingCI.class, owner);
		assert(owner instanceof AccessPointNode);
	}
	
	public AccessPointNodeInboundPort(String uri, ComponentI owner) throws Exception
	{
		super(uri, RoutingCI.class, owner);
		
	}

	@Override
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(c -> {((AccessPointNode) c).updateRouting(neighbour, route); return null;});
	}

	@Override
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(c -> {((AccessPointNode) c).updateAccessPoint(neighbour, numberOfHops); return null;});

	}

}
