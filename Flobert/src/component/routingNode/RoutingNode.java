package component.routingNode;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import component.registration.ConnectionInfo;
import component.registration.NodeAddress;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import component.routingNode.interfaces.RoutingCI;
import component.terminalNode.Message;
import component.terminalNode.TerminalNode;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

@OfferedInterfaces(offered = {CommunicationCI.class,RoutingCI.class})
@RequiredInterfaces(required = {RegistrationCI.class, CommunicationCI.class, RoutingCI.class})

public class RoutingNode extends TerminalNode  
{
	
	
	
	public static final String SAMPLESROUTINGNODEINBOUNDPORTURI = "rip-uri";
	public static final String SAMPLESROUTINGNODEOUTBOUNDPORTURI = "rop-uri";
	private final String ROUTINGINBOUNDPORTURI;
	private final String ROUTINGOUTBOUNDPORTURI;
	protected RoutingNodeInboundPort rinboundPort;
	protected RoutingOutboundPort rtoutboundPort;
	

	
	protected RoutingNode(NodeAddressI addr, PositionI pos, double portee) throws Exception 
	{
		super(addr,pos,portee);
		this.ROUTINGINBOUNDPORTURI = SAMPLESROUTINGNODEINBOUNDPORTURI + (cpt-1);
		this.ROUTINGOUTBOUNDPORTURI = SAMPLESROUTINGNODEOUTBOUNDPORTURI + (cpt-1);
		this.rinboundPort = new RoutingNodeInboundPort(this.ROUTINGINBOUNDPORTURI,this);
		this.rtoutboundPort = new RoutingOutboundPort(this.ROUTINGOUTBOUNDPORTURI,this);
		this.rinboundPort.publishPort();
		this.rtoutboundPort.publishPort();
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		if(routingInboundPortURI == null)
		{
			super.connect(address, communicationInboundPortURI);
		}else
		{
			if(this.rtoutboundPort.connected())
			{
				this.doPortDisconnection(this.rtoutboundPort.getPortURI());
			}
			this.doPortConnection(this.rtoutboundPort.getPortURI(), routingInboundPortURI, ConnectorRouting.class.getCanonicalName());
			if(this.outboundPort.connected())
			{
				this.doPortDisconnection(this.outboundPort.getPortURI());
			}
			super.connect(address, communicationInboundPortURI);
			this.rtoutboundPort.updateRouting(this.getAddr(), this.tables.get(this.getAddr()));
		}
		
	}
	
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		tables.putIfAbsent(neighbour, routes);
	
		for(RouteInfo ri : routes)
		{
			for(RouteInfo mri : tables.get(neighbour))
			{
				if(ri.getDestination().equals(mri.getDestination()) && mri.getNumberOfHops()>ri.getNumberOfHops())
				{
					tables.get(neighbour).remove(mri);
					tables.get(neighbour).add(ri);
				}
			}
		}
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		if(m.getAddress().equals(this.getAddr()))
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
						while(ci == null)
						{
							r = (new Random()).nextInt(voisinsRouteur.size());
							ci  = (ConnectionInfo) voisinsRouteur.toArray()[r];
						}
						if(m.stillAlive())
						{
							this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
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
					if(ri.getDestination().equals(address) && ri.getDestination().equals(ci.getAddress()))
					{
						this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(), ci.getRoutingInboundURI());
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
					if(ci.getAddress().equals(sri) && ri.getDestination().equals(address))
					{
						this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(), ci.getRoutingInboundURI());
						return true;
					}else
					{
						if(ci.getAddress().equals(sri) && ri.getDestination().equals(address) && ci.isRouting() && minHops > ri.getNumberOfHops())
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
				this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
				return true;
			}
		}
		return false;
	}
	
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		tables.putIfAbsent(this.getAddr(), new HashSet<>());
		for(RouteInfo ri : tables.get(this.getAddr()))
		{
			if(ri.getDestination().equals(neighbour))
			{
				tables.get(this.getAddr()).remove(ri);
				tables.get(this.getAddr()).add(new RouteInfo(neighbour, numberOfHops));
			}
				
		}
	}
	
	@Override
	public synchronized void start() throws ComponentStartException
	{
		super.start();
		
	}
	
	@Override
	public synchronized void execute() throws Exception
	{	
		super.execute();
		
		try
		{	
			MessageI m = new Message(new NodeAddress("0.0.0.6"), "toto" , 10);
			neighbours = this.routboundPort.registerRoutingNode(this.getAddr(), this.TERMINALNODEINBOUNDPORTURI, this.getPos(), this.getPortee(), this.ROUTINGINBOUNDPORTURI);

			for(ConnectionInfo ci : neighbours)
			{
				this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
				
			}
			this.transmitMessage(m);

		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void shutdown() throws ComponentShutdownException
	{
		try 
		{
			this.rinboundPort.unpublishPort();
			this.rtoutboundPort.unpublishPort();
			
		}  catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public synchronized void finalise() throws Exception
	{
		if(this.rtoutboundPort.connected())
		{
			this.doPortDisconnection(this.rtoutboundPort.getPortURI());
		}
		super.finalise();
		
	}
}
