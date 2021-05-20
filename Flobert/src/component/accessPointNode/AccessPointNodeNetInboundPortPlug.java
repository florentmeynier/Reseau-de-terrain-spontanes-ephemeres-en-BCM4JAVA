package component.accessPointNode;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.routingNode.interfaces.RoutingCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class AccessPointNodeNetInboundPortPlug extends AbstractInboundPort implements NetworkNodeCI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccessPointNodeNetInboundPortPlug(ComponentI owner, String pluginURI) throws Exception
	{
		super(RoutingCI.class, owner, pluginURI, null);
		assert(owner instanceof AccessPointNode);
	}

	@Override
	public void transmitAddress(NetworkAddressI addr) throws Exception {
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>(this.pluginURI) {
			@Override
			public Void call() throws Exception {
				((AccessPointNodePlugin)this.getServiceProviderReference()).transmitAddress(addr);
				return null;
			}
		});
	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>(this.pluginURI) {
			@Override
			public Void call() throws Exception {
				((AccessPointNodePlugin)this.getServiceProviderReference()).transmitMessage(m);
				return null;
			}
		});

	}

}
