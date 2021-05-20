package component.routingNode;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routingNode.interfaces.RoutingCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class RoutingNodeRoutInboundPortPlugin extends AbstractInboundPort implements RoutingCI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoutingNodeRoutInboundPortPlugin(ComponentI owner, String pluginURI) throws Exception
	{
		super(RoutingCI.class, owner, pluginURI, null);
		assert(owner instanceof RoutingNode);
	}

	@Override
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception {
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>(this.pluginURI) {
			@Override
			public Void call() throws Exception {
				((RoutingNodePlugin)this.getServiceProviderReference()).updateRouting(neighbour, routes);
				return null;
			}
		});

	}

	@Override
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>(this.pluginURI) {
			@Override
			public Void call() throws Exception {
				((RoutingNodePlugin)this.getServiceProviderReference()).updateAccessPoint(neighbour, numberOfHops);
				return null;
			}
		});
	}

}
