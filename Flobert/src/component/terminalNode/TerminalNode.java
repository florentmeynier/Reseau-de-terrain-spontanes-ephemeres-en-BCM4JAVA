package component.terminalNode;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import component.accessPointNode.AccessPointNode;
import component.registration.ConnectionInfo;
import component.registration.NodeAddress;
import component.registration.Registration;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import component.routingNode.RouteInfo;
import component.routingNode.RoutingNode;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

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
	protected Map<NodeAddressI,Set<RouteInfo>> tables = new HashMap<>();
	
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
		tables.putIfAbsent(getAddr(), new HashSet<>());
		tables.get(getAddr()).add(new RouteInfo(address,1));
		if(this.outboundPort.connected())
		{
			this.doPortDisconnection(this.outboundPort.getPortURI());
		}
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
			this.logMessage("message recu " + m.getContent());
		}else
		{
			if(this.hasRouteFor(m.getAddress()))
			{
				this.logMessage("message " + m.getContent() +" vivant ? " + m.stillAlive());

				if(m.stillAlive())
				{
					m.decrementHops();
					this.outboundPort.transmitMessage(m);
					this.logMessage("message "+ m.getContent() +" transmis au noeud routeur");
					
				}else
				{
					this.logMessage("message "+ m.getContent() +" est mort");
				}
			}else
			{
				if(this.neighbours.isEmpty())
				{
					this.logMessage("Pas de voisin a qui transferer le message " + m.getContent());
				}else
				{
					int r = 0;
					ConnectionInfo ci = null;
					Set<ConnectionInfo> voisinsRouteur = new HashSet<>();
					for(ConnectionInfo c : neighbours)
					{
						if(c.isRouting())
						{
							voisinsRouteur.add(c);
						}
					}
					if(voisinsRouteur.isEmpty())
					{
						this.logMessage("Pas de voisins routeurs a qui innonder le message " + m.getContent());
					}else
					{
						
						r = (new Random()).nextInt(voisinsRouteur.size());
						ci  = (ConnectionInfo) voisinsRouteur.toArray()[r];
						
						if(m.stillAlive())
						{
							this.connect(ci.getAddress(), ci.getCommunicationInboundPortURI());
							m.decrementHops();
							this.outboundPort.transmitMessage(m);
							this.logMessage("message "+ m.getContent() +" transmis par innondation au noeud routeur");
						}else
						{
							this.logMessage("message "+ m.getContent() +" est mort");
						}
					}
				}
			}
		}
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		int minHops = Integer.MAX_VALUE;
		NodeAddressI tmp = null;
		
		if(tables.get(this.getAddr()) != null)
		{
			for(RouteInfo ri : tables.get(this.getAddr()))
			{
				for(ConnectionInfo ci : neighbours)
				{
					if(ri.getDestination().equals(address) && ri.getDestination().equals(ci.getAddress()) && ci.isRouting())
					{
						this.connect(ci.getAddress(), ci.getCommunicationInboundPortURI());
						return true;
					}
				}
			}
		}
		for(NodeAddressI sri : tables.keySet())
		{
			for(RouteInfo ri : tables.get(sri))
			{
				for(ConnectionInfo ci : neighbours)
				{
					if(ci.isRouting() && ci.getAddress().equals(sri) && ri.getDestination().equals(address) && ci.getAddress().equals(ri.getDestination()))
					{
						this.connect(ci.getAddress(), ci.getCommunicationInboundPortURI());
						return true;
					}else
					{
						if(ci.isRouting() && ci.getAddress().equals(sri) && ri.getDestination().equals(address) && ri.getNumberOfHops() < minHops)
						{
							minHops = ri.getNumberOfHops();
							tmp = sri;
						}
					}
				}
			}
		}
		for(ConnectionInfo ci : neighbours)
		{
			if(ci.getAddress().equals(tmp))
			{
				this.connect(ci.getAddress(), ci.getCommunicationInboundPortURI());
				return true;
			}
		}
		return false;
	}
	
	public void ping() throws Exception
	{
		return;
	}
	
	@Override
	public synchronized void start() throws ComponentStartException
	{
		
		try
		{
			this.doPortConnection(this.routboundPort.getPortURI(), Registration.REGISTRATIONNODEINBOUNDPORTURI, ConnectorRegistration.class.getCanonicalName());
			
		}catch (Exception e)
		{
			throw new ComponentStartException(e);
		}
		
		super.start();
		
	}
	
	@Override
	public synchronized void execute() throws Exception
	{
		super.execute();
		
		try 
		{
			
			if(!(this instanceof RoutingNode) && !(this instanceof AccessPointNode))
			{
				MessageI m = new Message(new NodeAddress("0.0.0.4"), "coco" ,10);
				neighbours = this.routboundPort.registerTerminalNode(this.addr, this.TERMINALNODEINBOUNDPORTURI, this.pos, this.portee);
				for(ConnectionInfo ci : neighbours)
				{
					
					this.connect(ci.getAddress(), ci.getCommunicationInboundPortURI());
					
					
				}
				this.transmitMessage(m);

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
