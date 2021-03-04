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
	private final String ROUTINGINBOUNDPORTURI;
	protected RoutingNodeInboundPort rinboundPort;
	

	
	protected RoutingNode(NodeAddressI addr, PositionI pos, double portee) throws Exception 
	{
		super(addr,pos,portee);
		this.ROUTINGINBOUNDPORTURI = SAMPLESROUTINGNODEINBOUNDPORTURI + (cpt-1);
		this.rinboundPort = new RoutingNodeInboundPort(this.ROUTINGINBOUNDPORTURI,this);
		this.rinboundPort.publishPort();
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{

		neighbours.add(new ConnectionInfo(address,communicationInboundPortURI,routingInboundPortURI,null,0));

		this.doPortConnection(this.outboundPort.getPortURI(), communicationInboundPortURI, ConnectorRouting.class.getCanonicalName());

	}
	
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception
	{
		for(RouteInfo ri : routes)
		{
			if(ri.getDestination().equals(neighbour))
			{
				ri.setNumberOfHops(ri.getNumberOfHops()+1);
			}
		}
		
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
					m.decrementHops();
					this.outboundPort.transmitMessage(m);
					this.logMessage("message "+ m.getContent() +" transmis au voisin");
				}
			}
		}
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		for(ConnectionInfo ci : neighbours)
		{
			if(ci.getAddress().equals(address))
			{
				routes.add(new RouteInfo(ci.getAddress(),1));
				return true;
			}
			if(ci.isRouting())
			{
				updateRouting(ci.getAddress(),routes);
			}
			
		}
		return false;
	}
	
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
	{
		return;
	}
	
	@Override
	public synchronized void execute() throws Exception
	{	
		super.execute();
		
		try
		{	
			MessageI m = new Message(new NodeAddress("0.0.0.6"), "toto" , 2);
			neighbours =  this.routboundPort.registerRoutingNode(this.getAddr(), this.TERMINALNODEINBOUNDPORTURI, this.getPos(), this.getPortee(), this.ROUTINGINBOUNDPORTURI);
			if(neighbours.isEmpty()) {
				this.logMessage("Pas de voisin a qui transferer le message");
				return;
			}
			int r = (new Random()).nextInt(neighbours.size());
			ConnectionInfo ci = null;
			while(ci == null)
			{
				r = (new Random()).nextInt(neighbours.size());
				ci  = (ConnectionInfo) neighbours.toArray()[r];
			}
			this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
	

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
			
		}  catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public synchronized void finalise() throws Exception
	{		
		super.finalise();
		
	}
}
