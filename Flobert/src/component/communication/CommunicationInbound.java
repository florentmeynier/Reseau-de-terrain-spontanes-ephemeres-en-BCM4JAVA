package component.communication;

import component.communication.interfaces.CommunicationCI;
import component.communication.interfaces.MessageI;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class CommunicationInbound extends AbstractInboundPort implements CommunicationCI {

	private static final long serialVersionUID = 1L;

	public CommunicationInbound(ComponentI owner) throws Exception
	{
		super(CommunicationCI.class, owner);
		assert(owner instanceof Communication);
	}
	
	public CommunicationInbound(String uri, ComponentI owner) throws Exception
	{
		super(uri, CommunicationCI.class, owner);
		assert(owner instanceof Communication);
	}

	@Override
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception {
		((Communication) this.getOwner()).connect(address, communicationInboundPortURI);
	}

	@Override
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception {
		((Communication) this.getOwner()).connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}

	@Override
	public void transmitMessage(MessageI m) throws Exception {
		
	}

	@Override
	public boolean hasRouteFor(AddressI address) throws Exception {
		return false;
	}

	@Override
	public void ping() {
		
	}

}
