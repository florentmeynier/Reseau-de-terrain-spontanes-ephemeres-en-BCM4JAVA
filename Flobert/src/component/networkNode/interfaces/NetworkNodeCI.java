package component.networkNode.interfaces;

import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface NetworkNodeCI extends OfferedCI, RequiredCI
{
	public void connect(NetworkAddressI address, String networkNodeInboundPortURI) throws Exception;
	public void transmitMessage(MessageI m) throws Exception;

}
