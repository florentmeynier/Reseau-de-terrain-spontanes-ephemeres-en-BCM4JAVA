package component.terminalNode;


import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * classe represnentant un connecteur entre deux noeuds du reseau ephemere.
 * @author florentmeynier
 *
 */
public class ConnectorTerminalNode extends AbstractConnector implements CommunicationCI
{

	@Override
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception 
	{
		((CommunicationCI) this.offering).connect(address, communicationInboundPortURI);
	}

	@Override
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI)
			throws Exception {
		((CommunicationCI) this.offering).connectRouting(address, communicationInboundPortURI, routingInboundPortURI);
	}

	@Override
	public void transmitMessage(MessageI m) throws Exception 
	{
		((CommunicationCI) this.offering).transmitMessage(m);

	}

	@Override
	public boolean hasRouteFor(AddressI address) throws Exception 
	{
		return ((CommunicationCI) this.offering).hasRouteFor(address);
	}

	@Override
	public void ping() throws Exception
	{
		((CommunicationCI) this.offering).ping();
	}
	
}
