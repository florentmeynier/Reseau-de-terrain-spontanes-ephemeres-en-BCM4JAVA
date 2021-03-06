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
		return this.getOwner().handleRequest(c -> {return ((Registration)c).registerTerminalNode(address,connectionInboundURI,initialPosition,initialRange);});
	}

	@Override
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		return this.getOwner().handleRequest(c -> {return ((Registration)c).registerAccessPoint(address,connectionInboundURI,initialPosition,initialRange,routingInboundPortURI);});
	}

	@Override
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		return this.getOwner().handleRequest(c -> {return ((Registration)c).registerRoutingNode(address,connectionInboundURI,initialPosition,initialRange,routingInboundPortURI);});
	}

	@Override
	public void unregister(NodeAddressI address) throws Exception 
	{
		this.getOwner().handleRequest(c -> {((Registration) c).unregister(address); return null;});
	}

}
