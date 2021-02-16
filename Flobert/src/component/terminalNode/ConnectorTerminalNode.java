package component.terminalNode;

import java.util.Set;

import component.communication.interfaces.CommunicationCI;
import component.communication.interfaces.MessageI;
import component.registration.ConnectionInfo;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class ConnectorTerminalNode extends AbstractConnector implements CommunicationCI, RegistrationCI
{

	@Override
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception {
		((CommunicationCI) this.offering).connect(address, communicationInboundPortURI);
	}

	@Override
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception {
		((CommunicationCI) this.offering).connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
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

	@Override
	public Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange) throws Exception {
		// TODO Auto-generated method stub
		return ((RegistrationCI)this.requiring).registerTerminalNode(address, connectionInboundURI, initialPosition, initialRange);
	}

	@Override
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI,
			PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unregister(NodeAddressI address) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
