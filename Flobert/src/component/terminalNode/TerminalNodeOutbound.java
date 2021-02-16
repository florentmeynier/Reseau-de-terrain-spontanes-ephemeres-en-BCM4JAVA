package component.terminalNode;


import java.util.Set;

import component.communication.interfaces.CommunicationCI;
import component.communication.interfaces.MessageI;
import component.registration.ConnectionInfo;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class TerminalNodeOutbound extends AbstractOutboundPort implements CommunicationCI, RegistrationCI {

	private static final long serialVersionUID = 1L;

	public TerminalNodeOutbound(ComponentI owner) throws Exception 
	{
		super(CommunicationCI.class, owner);
	}
	
	public TerminalNodeOutbound(String uri, ComponentI owner) throws Exception 
	{
		super(uri, CommunicationCI.class, owner);
	}
	
	@Override
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception 
	{
		((CommunicationCI) this.getConnector()).connect(address, communicationInboundPortURI);
	}

	@Override
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception 
	{
		((CommunicationCI) this.getConnector()).connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		((CommunicationCI) this.getConnector()).transmitMessage(m);
	}

	@Override
	public boolean hasRouteFor(AddressI address) throws Exception 
	{
		return ((CommunicationCI) this.getConnector()).hasRouteFor(address);
	}

	@Override
	public void ping() throws Exception
	{
		((CommunicationCI) this.getConnector()).ping();
		
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
