package component.networkNode.interfaces;

import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

public interface NetworkNodeCI extends OfferedCI, RequiredCI
{
	public void transmitAddress(NetworkAddressI addr) throws Exception;
	public void transmitMessage(MessageI m) throws Exception;

}
