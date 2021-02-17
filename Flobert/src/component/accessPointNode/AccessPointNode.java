package component.accessPointNode;


import java.util.Random;
import java.util.Set;

import component.registration.ConnectionInfo;
import component.registration.NodeAddress;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import component.routing.RouteInfo;
import component.routing.interfaces.RoutingCI;
import component.routingNode.ConnectorRouting;
import component.terminalNode.Message;
import component.terminalNode.TerminalNode;
import component.terminalNode.interfaces.CommunicationCI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {CommunicationCI.class,RoutingCI.class})
@RequiredInterfaces(required = {RegistrationCI.class, CommunicationCI.class, RoutingCI.class})
public class AccessPointNode extends TerminalNode 
{
	public static final String SAMPLESACCESSPOINTNODEINBOUNDPORTURI = "apip-uri";
	private final String ACCESSPOINTINBOUNDPORTURI;
	protected AccessPointNodeInboundPort apinboundPort;

	protected AccessPointNode(NodeAddressI addr, PositionI pos, double portee) throws Exception 
	{
		super(addr, pos, portee);
		// TODO Auto-generated constructor stub
		this.ACCESSPOINTINBOUNDPORTURI = SAMPLESACCESSPOINTNODEINBOUNDPORTURI + (cpt-1);
		this.apinboundPort = new AccessPointNodeInboundPort(this.ACCESSPOINTINBOUNDPORTURI,this);
		this.apinboundPort.publishPort();
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
	
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception
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
			if(m.stillAlive())
			{
				m.decrementHops();
				this.outboundPort.transmitMessage(m);
			}
		}
	}
	
	@Override
	public synchronized void execute() throws Exception
	{
		super.execute();
		
		try
		{	
			MessageI m = new Message(new NodeAddress("0.0.0.2"), "tata" ,2);
			Set<ConnectionInfo> voisins = this.routboundPort.registerAccessPoint(this.getAddr(), this.TERMINALNODEINBOUNDPORTURI, this.getPos(), this.getPortee(), this.ACCESSPOINTINBOUNDPORTURI);
			int r = (new Random()).nextInt(voisins.size());
			ConnectionInfo ci = (ConnectionInfo) voisins.toArray()[r];
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
			this.apinboundPort.unpublishPort();
			
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
