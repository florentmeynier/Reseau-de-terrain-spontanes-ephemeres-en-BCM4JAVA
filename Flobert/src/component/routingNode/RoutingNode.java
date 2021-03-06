package component.routingNode;

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
			this.doPortConnection(this.rtoutboundPort.getPortURI(), routingInboundPortURI, ConnectorRouting.class.getCanonicalName());
			this.rtoutboundPort.updateRouting(this.getAddr(), this.routes);
		}
		
	}
	
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		boolean parcouru = false;
		for (RouteInfo ri : routes)
		{
			for(RouteInfo mri : this.routes)
			{
				if(ri.getDestination().equals(mri.getDestination()))
				{
					if(ri.getNumberOfHops()+1 < mri.getNumberOfHops())
					{
						this.routes.remove(mri);
						this.routes.add(ri);
					}
					parcouru = false;
					break;
				}
				parcouru = true;
			}
			if(parcouru)
			{
				this.routes.add(new RouteInfo(ri.getDestination(),ri.getNumberOfHops()+1));
				parcouru = false;
			}
		}
		this.routes.add(new RouteInfo(neighbour,1));
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		if(m.getAddress().equals(this.getAddr()))
		{
			this.logMessage("message recu " + m.getContent());
			return;
		}else
		{
			if(this.outboundPort.connected())
			{
				this.logMessage("message " + m.getContent() +" vivant ? " + m.stillAlive());

				if(m.stillAlive())
				{
					if(this.hasRouteFor(m.getAddress()))
					{
						m.decrementHops();
						this.outboundPort.transmitMessage(m);
						this.logMessage("message "+ m.getContent() +" transmis au noeud routeur");
						return;
					}
					m.decrementHops();
					this.outboundPort.transmitMessage(m);
					this.logMessage("message "+ m.getContent() +" transmis au voisin par innondation");
				}
			}
		}
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		int minHops = Integer.MAX_VALUE;
		ConnectionInfo tmp = null;
		for(RouteInfo ri : routes)
		{
			if(ri.getDestination().equals(address))
			{
				for(ConnectionInfo ci : neighbours)
				{
					if(ci.getAddress().equals(ri.getDestination()))
					{
						this.doPortDisconnection(this.outboundPort.getPortURI());
						this.connect(ci.getAddress(), ci.getCommunicationInboundPortURI());
						return true;
					}else
					{
						if(ci.isRouting())
						{
							if(ri.getNumberOfHops() < minHops)
							{
								tmp = ci;
								minHops = ri.getNumberOfHops();
							}
						}
					}
				}
				
			}
		}
		if(tmp != null)
		{
			
			if(this.rtoutboundPort.connected())
			{
				this.doPortDisconnection(this.rtoutboundPort.getPortURI());
			}
			this.connectRouting(tmp.getAddress(), tmp.getCommunicationInboundPortURI(),tmp.getCommunicationInboundPortURI());
			if(this.outboundPort.connected())
			{
				this.doPortDisconnection(this.outboundPort.getPortURI());

			}else 
			{
				this.connect(tmp.getAddress(), tmp.getCommunicationInboundPortURI());
			}
			return true;
		}
		return false;
	}
	
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		for(RouteInfo ri : routes)
		{
			if(ri.getDestination().equals(neighbour) && ri.getNumberOfHops() < numberOfHops)
			{
				routes.remove(ri);
				routes.add(new RouteInfo(neighbour, numberOfHops));
			}
				
		}
	}
	
	@Override
	public synchronized void execute() throws Exception
	{	
		super.execute();
		
		try
		{	
			MessageI m = new Message(new NodeAddress("0.0.0.6"), "toto" , 10);
			this.neighbours =  this.routboundPort.registerRoutingNode(this.getAddr(), this.TERMINALNODEINBOUNDPORTURI, this.getPos(), this.getPortee(), this.ROUTINGINBOUNDPORTURI);
			if(neighbours.isEmpty()) {
				this.logMessage("Pas de voisin a qui transferer le message");
				return;
			}
			for(ConnectionInfo ci : this.neighbours)
			{
				this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
				if(this.hasRouteFor(m.getAddress()))
				{
					this.transmitMessage(m);
					return;
				}else
				{
					if(ci.isRouting())
					{
						this.doPortDisconnection(this.rtoutboundPort.getPortURI());
					}else
					{
						this.doPortDisconnection(this.outboundPort.getPortURI());
					}
				}
			}
			int r = (new Random()).nextInt(this.neighbours.size());
			ConnectionInfo ci = null;
			while(ci == null)
			{
				r = (new Random()).nextInt(neighbours.size());
				ci  = (ConnectionInfo) neighbours.toArray()[r];
			}
			this.connect(ci.getAddress(), ci.getCommunicationInboundPortURI());
	

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
