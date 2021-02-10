package component.registration.interfaces;

import java.util.Set;

import component.registration.ConnectionInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface RegistrationCI extends OfferedCI, RequiredCI
{
	public Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange) throws Exception;
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception;
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception;
	public void unregister(NodeAddressI address) throws Exception;
}
