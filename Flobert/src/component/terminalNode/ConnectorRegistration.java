package component.terminalNode;

import java.util.Set;

import component.registration.ConnectionInfo;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class ConnectorRegistration extends AbstractConnector implements RegistrationCI 
{

	@Override
	public Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange) throws Exception 
	{
		// TODO Auto-generated method stub
		return ((RegistrationCI)this.offering).registerTerminalNode(address, connectionInboundURI, initialPosition, initialRange);
	}

	@Override
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception 
	{
		// TODO Auto-generated method stub
		return ((RegistrationCI)this.offering).registerAccessPoint(address, connectionInboundURI, initialPosition, initialRange, routingInboundPortURI);
	}

	@Override
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception 
	{
		// TODO Auto-generated method stub
		return ((RegistrationCI)this.offering).registerRoutingNode(address, connectionInboundURI, initialPosition, initialRange, routingInboundPortURI);

	}

	@Override
	public void unregister(NodeAddressI address) throws Exception 
	{
		// TODO Auto-generated method stub
		((RegistrationCI)this.offering).unregister(address);

	}
}
