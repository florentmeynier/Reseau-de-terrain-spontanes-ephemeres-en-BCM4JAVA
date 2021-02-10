package component.registration;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class RegistrationInbound extends AbstractInboundPort implements RegistrationCI {

	
	private static final long serialVersionUID = 1L;

	public RegistrationInbound(ComponentI owner) throws Exception
	{
		super(RegistrationCI.class, owner);
		assert(owner instanceof Registration);
	}
	
	public RegistrationInbound(String uri, ComponentI owner) throws Exception
	{
		super(uri, RegistrationCI.class, owner);
		assert(owner instanceof Registration);
	}
	
	@Override
	public Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange) throws Exception {
		return ((Registration) this.getOwner()).registerTerminalNode(address, connectionInboundURI, initialPosition, initialRange);
	}

	@Override
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		return ((Registration) this.getOwner()).registerAccessPoint(address, connectionInboundURI, initialPosition, initialRange, routingInboundPortURI);
	}

	@Override
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		return ((Registration) this.getOwner()).registerRoutingNode(address, connectionInboundURI, initialPosition, initialRange, routingInboundPortURI);
	}

	@Override
	public void unregister(NodeAddressI address) throws Exception {
		((Registration) this.getOwner()).unregister(address);
	}

}
