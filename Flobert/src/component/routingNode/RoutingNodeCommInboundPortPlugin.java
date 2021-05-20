package component.routingNode;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class RoutingNodeCommInboundPortPlugin extends AbstractInboundPort implements CommunicationCI 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoutingNodeCommInboundPortPlugin(ComponentI owner, String pluginURI) throws Exception
	{
		super(CommunicationCI.class, owner, pluginURI, null);
		assert(owner instanceof RoutingNode);
	}


	@Override
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception 
	{
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>(this.pluginURI) {
			@Override
			public Void call() throws Exception {
				((RoutingNodePlugin)this.getServiceProviderReference()).connect(address, communicationInboundPortURI);
				return null;
			}
		});
	}


	@Override
	public void transmitMessage(MessageI m) throws Exception
	{
		
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>(this.pluginURI) 
		{
			@Override
			public Void call() throws Exception {
				((RoutingNodePlugin)this.getServiceProviderReference()).transmitMessage(m);
				return null;
			}
		});

	}

	@Override
	public boolean hasRouteFor(AddressI address) throws Exception {
		// TODO Auto-generated method stub
		return this.getOwner().handleRequest(new AbstractComponent.AbstractService<Boolean>(this.pluginURI) 
		{
			@Override
			public Boolean call() throws Exception {
				return ((RoutingNodePlugin)this.getServiceProviderReference()).hasRouteFor(address);
			}
		});
	}

	@Override
	public void ping() throws Exception {
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>(this.pluginURI) 
		{
			@Override
			public Void call() throws Exception {
				((RoutingNodePlugin)this.getServiceProviderReference()).ping();
				return null;
			}
		});
	}


	@Override
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception {
		// TODO Auto-generated method stub
		this.getOwner().handleRequest(new AbstractComponent.AbstractService<Void>(this.pluginURI) 
		{
			@Override
			public Void call() throws Exception {
				((RoutingNodePlugin)this.getServiceProviderReference()).connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
				return null;
			}
		});
	}

}
