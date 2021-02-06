package component.communication.interfaces;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import fr.sorbonne_u.components.interfaces.OfferedCI;

public interface CommunicationCI extends OfferedCI
{
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception;
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception;
	public void transmitMessage(MessageI m) throws Exception;
	public boolean HasRouteFor(AddressI address) throws Exception;
	public void ping();
}
