package component.terminalNode;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

/**
 * classe representant un composant noeud terminal.
 * @author florentmeynier
 *
 */
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
	protected ConcurrentMap<NodeAddressI,Set<RouteInfo>> tables = new ConcurrentHashMap<>();
	
	private Object mutex = new Object();
	
	/**
	 * constructeur utilisant l'addresse, la position et la portee du noeud. Il instancie et publie les ports necessaires a celui-ci.
	 * @param addr
	 * @param pos
	 * @param portee
	 * @throws Exception
	 */
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
		createNewExecutorService("mess-uri",2,false);
		createNewExecutorService("connect-uri",1,false);
		cpt++;
	}

	/**
	 * accesseur sur l'addresse.
	 * @return l'addresse du noeud.
	 */
	public NodeAddressI getAddr() 
	{
		return addr;
	}
	
	/**
	 * accesseur sur la position.
	 * @return la position du noeud.
	 */
	public PositionI getPos() 
	{
		return pos;
	}

	/**
	 * accesseur sur la portee.
	 * @return la portee du noeud.
	 */
	public double getPortee() 
	{
		return portee;
	}
	
	/**
	 * connecte deux noeuds du reseau ephemere.
	 * @param address
	 * @param communicationInboundPortURI
	 * @throws Exception
	 */
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		synchronized(mutex)
		{
			tables.putIfAbsent(getAddr(), new HashSet<>());
			tables.get(getAddr()).add(new RouteInfo(address,1));
		}
		if(this.outboundPort.connected())
		{
			this.doPortDisconnection(this.outboundPort.getPortURI());
		}
		this.doPortConnection(this.outboundPort.getPortURI(), communicationInboundPortURI, ConnectorTerminalNode.class.getCanonicalName());
	}
	
	/**
	 * connecte un noeud avec un noeud routeur du reseau ephemere.
	 * @param address
	 * @param communicationInboundPortURI
	 * @param routingInboundPortURI
	 * @throws Exception
	 */
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		return;
	}
	
	/**
	 * si le noeud du reseau ephemere est le destinataire du message alors il le recoit sinon il transmet le message sur le reseau.
	 * @param m
	 * @throws Exception
	 */
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
	
	/**
	 * cherche une route pour atteindre une destination dans les tables de routage.
	 * @param address
	 * @return vrai si une route existe, faux sinon.
	 * @throws Exception
	 */
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		int minHops = Integer.MAX_VALUE;
		NodeAddressI tmp = null;
		ConnectionInfo cc = null;
		synchronized(this)
		{
			if(tables.get(this.getAddr()) != null)
			{
				for(RouteInfo ri : tables.get(this.getAddr()))
				{
					if(cc != null)
					{
						break;
					}
					for(ConnectionInfo ci : neighbours)
					{
						if(ri.getDestination().equals(address) && ri.getDestination().equals(ci.getAddress()) && ci.isRouting())
						{
							cc = ci;
							break;
						}
					}
				}
				
				if(cc != null)
				{
					this.connect(cc.getAddress(), cc.getCommunicationInboundPortURI());
					return true;
				}
				
			}
			for(NodeAddressI sri : tables.keySet())
			{
				if(cc != null)
				{
					break;
				}
				for(RouteInfo ri : tables.get(sri))
				{
					if(cc != null)
					{
						break;
					}
					for(ConnectionInfo ci : neighbours)
					{
						if(ci.isRouting() && ci.getAddress().equals(sri) && ri.getDestination().equals(address) && ci.getAddress().equals(ri.getDestination()))
						{
							cc = ci;
							break;
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
			if(cc != null)
			{
				this.connect(cc.getAddress(), cc.getCommunicationInboundPortURI());
				return true;
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
