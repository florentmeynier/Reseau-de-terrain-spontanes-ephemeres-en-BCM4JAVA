package component.accessPointNode;


import java.util.Random;
import java.util.Set;

import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.ConnectionInfo;
import component.registration.NodeAddress;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NetworkAddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import component.routingNode.ConnectorRouting;
import component.routingNode.RouteInfo;
import component.routingNode.RoutingOutboundPort;
import component.routingNode.interfaces.RoutingCI;
import component.terminalNode.Message;
import component.terminalNode.TerminalNode;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {CommunicationCI.class,RoutingCI.class,NetworkNodeCI.class})
@RequiredInterfaces(required = {RegistrationCI.class, CommunicationCI.class, RoutingCI.class})
public class AccessPointNode extends TerminalNode 
{
	public static final String SAMPLESACCESSPOINTNODEINBOUNDPORTURI = "apip-uri";
	public static final String SAMPLESACCESSPOINTNETWORKINBOUNDPORTURI = "netip-uri";
	public static final String SAMPLESROUTINGNODEOUTBOUNDPORTURI = "rop-uri";
	private final String ROUTINGOUTBOUNDPORTURI;
	private final String ACCESSPOINTINBOUNDPORTURI;
	private final String ACCESSPOINTINBOUNDNETWORKPORTURI;
	protected AccessPointNodeInboundPort apinboundPort;
	protected AccessPointNetworkInboundPort apninboundPort;
	protected RoutingOutboundPort rtoutboundPort;


	protected AccessPointNode(NodeAddressI addr, PositionI pos, double portee) throws Exception 
	{
		super(addr, pos, portee);
		this.ACCESSPOINTINBOUNDPORTURI = SAMPLESACCESSPOINTNODEINBOUNDPORTURI + (cpt-1);
		this.ROUTINGOUTBOUNDPORTURI = SAMPLESROUTINGNODEOUTBOUNDPORTURI + (cpt-1);
		this.ACCESSPOINTINBOUNDNETWORKPORTURI = SAMPLESACCESSPOINTNETWORKINBOUNDPORTURI + (cpt-1);

		this.apinboundPort = new AccessPointNodeInboundPort(this.ACCESSPOINTINBOUNDPORTURI,this);
		this.rtoutboundPort = new RoutingOutboundPort(this.ROUTINGOUTBOUNDPORTURI,this);
		this.apninboundPort = new AccessPointNetworkInboundPort(this.ACCESSPOINTINBOUNDNETWORKPORTURI,this);
		this.apinboundPort.publishPort();
		this.rtoutboundPort.publishPort();
		this.apninboundPort.publishPort();
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
			this.rtoutboundPort.updateRouting(this.getAddr(), this.routes);
			this.rtoutboundPort.updateAccessPoint(this.getAddr(), 1);

		}	
	}
	
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception
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
	
	
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		for(RouteInfo ri : routes)
		{
			if(ri.getDestination().equals(neighbour) && ri.getNumberOfHops() > numberOfHops)
			{
				routes.remove(ri);
				routes.add(new RouteInfo(neighbour, numberOfHops));
			}
				
		}
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		int minHops = Integer.MAX_VALUE;
		ConnectionInfo tmp = null;
		for(RouteInfo ri : routes)
		{
			if(!address.isNetworkAddress() && ri.getDestination().equals(address))
			{
				for(ConnectionInfo ci : neighbours)
				{
					if(ci.getAddress().equals(ri.getDestination()))
					{
						this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
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
			this.connectRouting(tmp.getAddress(), tmp.getCommunicationInboundPortURI(), tmp.getRoutingInboundURI());
			return true;
		}
		return false;
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		if(m.getAddress().equals(this.getAddr()))
		{
			this.logMessage("message recu " + m.getContent());
			return;
		}else
		{
			if(this.apninboundPort.connected())
			{
				for(RouteInfo ri : routes)
				{
					
					if(m.getAddress().isNetworkAddress() && ri.getDestination().equals(m.getAddress()) && ri.getNumberOfHops() == 1)
					{
						m.decrementHops();
						this.logMessage("message " + m.getContent() +" transmis au noeud du reseau");
						this.apninboundPort.transmitMessage(m);
						return;
					}
				}
				
			}
			
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
					this.logMessage("Pas de voisin a qui transferer le message");
				}else
				{
					int r = 0;
					ConnectionInfo ci = null;
					while(ci == null)
					{
						r = (new Random()).nextInt(neighbours.size());
						ci  = (ConnectionInfo) neighbours.toArray()[r];
					}
					this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
					m.decrementHops();
					this.outboundPort.transmitMessage(m);
					this.logMessage("message "+ m.getContent() +" transmis par innondation");
				}
			}
		}
	}
	
	public void transmitAddress(NetworkAddressI addr) throws Exception
	{
		routes.add(new RouteInfo(addr,1));
	}
	
	@Override
	public synchronized void execute() throws Exception
	{
		super.execute();
		
		try
		{	
			MessageI m = new Message(new NodeAddress("0.0.0.1"), "tata" , 10);
			neighbours = this.routboundPort.registerAccessPoint(this.getAddr(), this.TERMINALNODEINBOUNDPORTURI, this.getPos(), this.getPortee(), this.ACCESSPOINTINBOUNDPORTURI);
			if(neighbours.isEmpty()) {
				this.logMessage("Pas de voisin a qui transferer le message");
				return;
			}
			for(ConnectionInfo ci : neighbours)
			{
				this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
				this.doPortDisconnection(this.outboundPort.getPortURI());
				if(this.rtoutboundPort.connected())
				{
					this.doPortDisconnection(this.rtoutboundPort.getPortURI());
				}
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
			this.apinboundPort.unpublishPort();
			this.apninboundPort.unpublishPort();
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
