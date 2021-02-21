package component.networkNode;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

public class ConnectorNetworkNode extends AbstractConnector implements NetworkNodeCI
{

	@Override
	public void connect(NetworkAddressI address, String networkNodeInboundPortURI) throws Exception 
	{
		// TODO Auto-generated method stub
		((NetworkNodeCI) this.offering).connect(address, networkNodeInboundPortURI);

		
	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		// TODO Auto-generated method stub
		((NetworkNodeCI) this.offering).transmitMessage(m);

		
	}

}
