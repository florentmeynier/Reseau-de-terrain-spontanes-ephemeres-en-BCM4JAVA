package component.networkNode;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class ConnectorNetworkNode extends AbstractConnector implements NetworkNodeCI
{


	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		// TODO Auto-generated method stub
		((NetworkNodeCI) this.offering).transmitMessage(m);

		
	}

	@Override
	public void transmitAddress(NetworkAddressI addr) throws Exception 
	{
		// TODO Auto-generated method stub
		((NetworkNodeCI) this.offering).transmitAddress(addr);
	}

}
