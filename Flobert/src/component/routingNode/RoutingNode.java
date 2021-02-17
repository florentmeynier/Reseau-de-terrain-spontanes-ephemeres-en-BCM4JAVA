package component.routingNode;

import java.util.Random;
import java.util.Set;

import component.registration.ConnectionInfo;
import component.registration.NodeAddress;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import component.routing.RouteInfo;
import component.routing.interfaces.RoutingCI;
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
		// TODO Auto-generated constructor stub
		super(addr,pos,portee);
		this.ROUTINGINBOUNDPORTURI = SAMPLESROUTINGNODEINBOUNDPORTURI + (cpt-1);
		this.rinboundPort = new RoutingNodeInboundPort(this.ROUTINGINBOUNDPORTURI,this);
		this.rinboundPort.publishPort();
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{

		voisin = address;
		this.doPortConnection(this.outboundPort.getPortURI(), communicationInboundPortURI, ConnectorRouting.class.getCanonicalName());

	}
	
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> route) throws Exception
	{
		return;
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		this.logMessage("message vivant ? " + m.stillAlive());
		if(m.getAddress().equals(this.getAddr()))
		{
			this.logMessage("message reçu " + m.getContent());
			return;
		}else
		{
			if(m.stillAlive() && this.outboundPort.connected())
			{
				m.decrementHops();
				this.outboundPort.transmitMessage(m);
			}
		}
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
			MessageI m = new Message(new NodeAddress("0.0.0.5"), "toto" ,3);
			Set<ConnectionInfo> voisins =  this.routboundPort.registerRoutingNode(this.getAddr(), this.TERMINALNODEINBOUNDPORTURI, this.getPos(), this.getPortee(), this.ROUTINGINBOUNDPORTURI);
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
			this.connectRouting(ci.getAddress(), ci.getCommunicationInboundPortURI(),ci.getRoutingInboundURI());
	

			this.transmitMessage(m);
			this.logMessage("message "+ m.getContent() +" transmis au voisin");
			
			


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
