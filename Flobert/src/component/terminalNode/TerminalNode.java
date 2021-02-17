package component.terminalNode;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import component.accessPointNode.AccessPointNode;
import component.registration.ConnectionInfo;
import component.registration.NodeAddress;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import component.routingNode.RoutingNode;
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
	protected final String TERMINALNODEINBOUNDPORTURI;
	private final String TERMINALNODEOUTBOUNDPORTURI;
	private final String TERMINALNODEROUTBOUNDPORTURI;
	
	private NodeAddressI addr;
	private PositionI pos;
	private double portee;
	
	protected Set<ConnectionInfo> neighbours = new HashSet<>();
	
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
		neighbours.add(new ConnectionInfo(address,communicationInboundPortURI,null,null,0));
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
			this.logMessage("message reçu " + m.getContent());
			return;
		}else
		{
			if(m.stillAlive() && this.outboundPort.connected())
			{
				this.logMessage("message vivant ? " + m.stillAlive());

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
			
			if(!(this instanceof RoutingNode) && !(this instanceof AccessPointNode))
			{
				MessageI m = new Message(new NodeAddress("0.0.0.3"), "coco" ,2);
				Set<ConnectionInfo> voisins = this.routboundPort.registerTerminalNode(this.addr, this.TERMINALNODEINBOUNDPORTURI, this.pos, this.portee);
				if(voisins.isEmpty()) {
					this.logMessage("Pas de voisin a qui transferer le message");
					return;
				}
				int r = (new Random()).nextInt(voisins.size());
				ConnectionInfo ci = null;
				while(ci == null)
				{
					r = (new Random()).nextInt(voisins.size());
					ci  = (ConnectionInfo) voisins.toArray()[r];
				}
				this.connect(ci.getAddress(), ci.getCommunicationInboundPortURI());
				
				this.transmitMessage(m);
				this.logMessage("message "+ m.getContent() +" transmis au voisin");

			}
			
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
		if(this.outboundPort.connected()) 
		{
			this.doPortDisconnection(this.outboundPort.getPortURI());
		}
		this.doPortDisconnection(this.routboundPort.getPortURI());	
		
		super.finalise();
		
	}
}
