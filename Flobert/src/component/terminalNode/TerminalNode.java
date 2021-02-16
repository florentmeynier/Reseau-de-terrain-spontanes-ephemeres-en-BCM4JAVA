package component.terminalNode;


import component.registration.NodeAddress;
import component.registration.Position;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {CommunicationCI.class})
@RequiredInterfaces(required = {RegistrationCI.class, CommunicationCI.class})
public class TerminalNode extends AbstractComponent 
{

	protected TerminalNodeInbound inboundPort;
	protected TerminalNodeOutbound outboundPort;
	protected TerminalNodeRegistrationOutboundPort routboundPort;
	public static final String SAMPLESTERMINALNODEINBOUNDPORTURI = "tnip-uri";
	public static final String SAMPLESTERMINALNODEOUTBOUNDPORTURI = "tnop-uri";
	public static int cpt = 0;
	private final String TERMINALNODEINBOUNDPORTURI;
	private final String TERMINALNODEOUTBOUNDPORTURI;
	private final String TERMINALNODEROUTBOUNDPORTURI;
	
	private NodeAddressI addr;
	private PositionI pos;
	private double portee;
	
	@SuppressWarnings("unused")
	private NodeAddressI voisin;
	
	protected TerminalNode(NodeAddressI addr, PositionI pos, double portee) throws Exception 
	{
		super(1, 0);
		this.addr = addr;
		this.pos = pos;
		this.portee = portee;
		TERMINALNODEINBOUNDPORTURI = SAMPLESTERMINALNODEINBOUNDPORTURI + cpt ;
		TERMINALNODEOUTBOUNDPORTURI = SAMPLESTERMINALNODEOUTBOUNDPORTURI + cpt;
		TERMINALNODEROUTBOUNDPORTURI = cpt + SAMPLESTERMINALNODEOUTBOUNDPORTURI + cpt;
		this.inboundPort = new TerminalNodeInbound(TERMINALNODEINBOUNDPORTURI, this);
		this.outboundPort = new TerminalNodeOutbound(TERMINALNODEOUTBOUNDPORTURI, this);
		this.routboundPort = new TerminalNodeRegistrationOutboundPort(TERMINALNODEROUTBOUNDPORTURI,this);
		this.inboundPort.publishPort();
		this.outboundPort.publishPort();
		this.routboundPort.publishPort();
		this.toggleLogging();
		this.toggleTracing();
		
		cpt++;
	}

	public NodeAddressI getAddr() 
	{
		return addr;
	}

	public PositionI getPos() 
	{
		return pos;
	}

	public double getPortee() 
	{
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
	public synchronized void execute() throws Exception
	{
		super.execute();
		try 
		{
			this.routboundPort.registerTerminalNode(this.addr, this.TERMINALNODEINBOUNDPORTURI, this.pos, this.portee);
			NodeAddressI addr = new NodeAddress("0.0.0.2");
			PositionI pos = new Position(0,1);
			double range = 10.0;
			AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {addr, pos, range});
			this.routboundPort.registerTerminalNode(addr, SAMPLESTERMINALNODEINBOUNDPORTURI+(cpt-1), pos, portee);
			this.connect(addr, SAMPLESTERMINALNODEINBOUNDPORTURI+(cpt-1));
		
			MessageI m = new Message(addr, "coco" ,2);
			
			this.logMessage("nb hops " + m.getHops());
			
			this.transmitMessage(m);
		
			this.logMessage("nb hops " + m.getHops());
			
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void shutdown() throws ComponentShutdownException
	{
		try {
			this.inboundPort.unpublishPort();
			this.outboundPort.unpublishPort();
			this.routboundPort.unpublishPort();
		}  catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public synchronized void finalise() throws Exception
	{
		if(this.isPortConnected(this.outboundPort.getPortURI()))
		{
			this.doPortDisconnection(this.outboundPort.getPortURI());
		}
		
		if(this.isPortConnected(this.routboundPort.getPortURI()))
		{
			this.doPortDisconnection(this.routboundPort.getPortURI());
		}		
		
		super.finalise();
		
	}
}
