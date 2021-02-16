package component.terminalNode;

import component.communication.interfaces.CommunicationCI;
import component.communication.interfaces.MessageI;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {CommunicationCI.class})
@RequiredInterfaces(required = {RegistrationCI.class, CommunicationCI.class})
public class TerminalNode extends AbstractComponent 
{

	private TerminalNodeInbound inboundPort;
	private TerminalNodeOutbound outboundPort;
	public static final String TERMINALNODEINBOUNDPORTURI = "tnip-uri";
	public static final String TERMINALNODEOUTBOUNDPORTURI = "tnop-uri";
	
	private NodeAddressI addr;
	private PositionI pos;
	private double portee;
	
	private NodeAddressI voisin;
	
	protected TerminalNode(NodeAddressI addr, PositionI pos, double portee) throws Exception {
		super(1, 0);
		this.addr = addr;
		this.pos = pos;
		this.portee = portee;
		this.inboundPort = new TerminalNodeInbound(TERMINALNODEINBOUNDPORTURI, this);
		this.outboundPort = new TerminalNodeOutbound(TERMINALNODEOUTBOUNDPORTURI, this);
		this.inboundPort.publishPort();
		this.outboundPort.publishPort();
		this.outboundPort.registerTerminalNode(addr, inboundPort.getPortURI(), pos, portee);
	}

	public NodeAddressI getAddr() {
		return addr;
	}

	public PositionI getPos() {
		return pos;
	}

	public double getPortee() {
		return portee;
	}
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		voisin = address;
		this.doPortConnection(this.outboundPort.getPortURI(), communicationInboundPortURI, ConnectorTerminalNode.class.getCanonicalName());
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		return;
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		if(m.getAddress().equals(addr))
		{
			return;
		}else
		{
			if(m.stillAlive())
			{
				m.decrementHops();
				this.outboundPort.transmitMessage(m);
			}
		}
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return true;
	}
	
	public void ping() throws Exception
	{
		return;
	}
	
	
	@Override
	public synchronized void shutdown() throws ComponentShutdownException
	{
		try {
			this.inboundPort.unpublishPort();
			this.outboundPort.unpublishPort();
		}  catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public synchronized void finalise() throws Exception
	{
		this.doPortDisconnection(TERMINALNODEOUTBOUNDPORTURI);
		super.finalise();
	}
}
