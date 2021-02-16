package component.communication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import component.communication.interfaces.CommunicationCI;
import component.communication.interfaces.MessageI;
import component.registration.NodeAddress;
import component.registration.Position;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.routing.RouteInfo;
import component.terminalNode.interfaces.NodeCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {CommunicationCI.class})
@RequiredInterfaces(required = {CommunicationCI.class, NodeCI.class})
public class Communication extends AbstractComponent
{
	
	protected CommunicationInbound inboundPort;
	protected CommunicationOutbound outboundPort;
	public static final String COMMUNICATIONINBOUNDPORTURI = "cip-uri";
	public static final String COMMUNICATIONOUTBOUNDPORTURI = "cop-uri";
	public static int cpt = 0;
	private final String INBOUNDPORTURI;
	private final String OUTBOUNDPORTURI;
	
	private List<NodeAddressI> voisins = new ArrayList<>();
	private List<String> voisinsURI = new ArrayList<>();
	private Set<RouteInfo> routes = new HashSet<>();
	
	protected Communication() throws Exception {
		super(1, 0);
		this.INBOUNDPORTURI = COMMUNICATIONINBOUNDPORTURI + cpt;
		this.OUTBOUNDPORTURI = COMMUNICATIONOUTBOUNDPORTURI + cpt;
		this.inboundPort = new CommunicationInbound(INBOUNDPORTURI, this);
		this.outboundPort = new CommunicationOutbound(OUTBOUNDPORTURI, this);
		this.inboundPort.publishPort();
		this.outboundPort.publishPort();
		cpt++;
		this.toggleLogging();
		this.toggleTracing();
	}
	
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		voisins.add(address);
		voisinsURI.add(communicationInboundPortURI);
		this.doPortConnection(this.outboundPort.getPortURI(), communicationInboundPortURI, Connector.class.getCanonicalName());
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		voisins.add(address);
		RouteInfo r = new RouteInfo(address, 1);
		routes.add(r);
		//this.doPortConnection(communicationInboundPortURI, routingInboundPortURI, Communication.class.getCanonicalName());
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		/*Random rand = new Random();
		int n = rand.nextInt(voisins.size());
		m.decrementHops();
		MessageI m2 = new Message (m.getAddress(),m.getContent(),m.getHops());
		
		for(int i = 0; i < n; i++)
		{ 
			int index = rand.nextInt(voisins.size());
			
			this.outboundPort.getClientPortURI();
		}*/
		if(m.stillAlive())
		{
			m.decrementHops();
			this.outboundPort.transmitMessage(m);
		}
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		for(RouteInfo r : routes) {
			if(r.getDestination().equals(address)) {
				return true;
			}
		}
		return false;
	}
	
	public void ping()
	{
		
	}
	
	@Override
	public synchronized void execute() throws Exception
	{
		super.execute();
		try {
			this.outboundPort.connect(new NodeAddress("0.0.0.1", new Position(0, 0), 0), INBOUNDPORTURI);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void shutdown() throws ComponentShutdownException
	{
		try {
			this.inboundPort.unpublishPort();
			this.outboundPort.unpublishPort();
		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public synchronized void finalise() throws Exception
	{
		this.doPortDisconnection(this.outboundPort.getPortURI());
		super.finalise();
	}
}
