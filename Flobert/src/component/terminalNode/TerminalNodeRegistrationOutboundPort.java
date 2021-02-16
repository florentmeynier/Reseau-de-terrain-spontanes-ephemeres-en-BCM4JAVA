package component.terminalNode;

import java.util.Set;

import component.registration.ConnectionInfo;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class TerminalNodeRegistrationOutboundPort extends 
AbstractOutboundPort 
implements RegistrationCI 
{
	
	private static final long serialVersionUID = 1L;

	public TerminalNodeRegistrationOutboundPort(ComponentI owner)
			throws Exception 
	{
		super(RegistrationCI.class, owner);
		// TODO Auto-generated constructor stub
	}
	
	public TerminalNodeRegistrationOutboundPort(String uri, ComponentI owner) throws Exception 
	{
		super(uri, RegistrationCI.class, owner);
	}
	
	
	@Override
	public Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange) throws Exception {
		// TODO Auto-generated method stub
		return ((RegistrationCI)this.getConnector()).registerTerminalNode(address, connectionInboundURI, initialPosition, initialRange);
	}

	@Override
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception 
	{
		// TODO Auto-generated method stub
		return ((RegistrationCI)this.getConnector()).registerAccessPoint(address,connectionInboundURI,initialPosition,initialRange,routingInboundPortURI);
	}

	@Override
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception 
	{
		// TODO Auto-generated method stub
		return ((RegistrationCI)this.getConnector()).registerRoutingNode(address,connectionInboundURI,initialPosition,initialRange,routingInboundPortURI);
	}

	@Override
	public void unregister(NodeAddressI address) throws Exception 
	{
		// TODO Auto-generated method stub
		((RegistrationCI)this.getConnector()).unregister(address);

		
	}

}
