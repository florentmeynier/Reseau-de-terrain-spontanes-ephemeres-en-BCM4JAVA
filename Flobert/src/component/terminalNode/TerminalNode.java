package component.terminalNode;

import component.communication.interfaces.CommunicationCI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import component.terminalNode.interfaces.NodeCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {NodeCI.class, CommunicationCI.class})
@RequiredInterfaces(required = {RegistrationCI.class, CommunicationCI.class})
public class TerminalNode extends AbstractComponent implements NodeCI {

	private TerminalNodeInbound inboundPort;
	private TerminalNodeOutbound outboundPort;
	public static final String TERMINALNODEINBOUNDPORTURI = "tnip-uri";
	public static final String TERMINALNODEOUTBOUNDPORTURI = "tnop-uri";
	
	private NodeAddressI addr;
	private PositionI pos;
	private double portee;
	
	protected TerminalNode(NodeAddressI addr, PositionI pos, double portee) throws Exception {
		super(1, 0);
		this.addr = addr;
		this.pos = pos;
		this.portee = portee;
		this.inboundPort = new TerminalNodeInbound(TERMINALNODEINBOUNDPORTURI, this);
		this.outboundPort = new TerminalNodeOutbound(TERMINALNODEOUTBOUNDPORTURI, this);
		this.inboundPort.publishPort();
		this.outboundPort.publishPort();
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
