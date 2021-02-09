package component.communication;

import java.util.HashSet;
import java.util.Set;

import component.communication.interfaces.CommunicationCI;
import component.communication.interfaces.MessageI;
import component.registration.NodeAddress;
import component.registration.Position;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {CommunicationCI.class})
@RequiredInterfaces(required = {CommunicationCI.class})

public class Communication extends AbstractComponent
{
	
	private CommunicationInbound inboundPort;
	private CommunicationOutbound outboundPort;
	public static final String COMMUNICATIONINBOUNDPORTURI = "cip-uri";
	public static final String COMMUNICATIONOUTBOUNDPORTURI = "cop-uri";
	
	private Set<NodeAddressI> voisins = new HashSet<>();
	
	protected Communication() throws Exception {
		super(1, 0);
		this.inboundPort = new CommunicationInbound(COMMUNICATIONINBOUNDPORTURI, this);
		this.outboundPort = new CommunicationOutbound(COMMUNICATIONOUTBOUNDPORTURI, this);
		this.inboundPort.publishPort();
		this.outboundPort.publishPort();
		this.toggleLogging();
		this.toggleTracing();
	}
	
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{
		voisins.add(address);
		//this.doPortConnection(communicationInboundPortURI, COMMUNICATIONOUTBOUNDPORTURI, Connector.class.getCanonicalName());
	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		voisins.add(address);
		this.doPortConnection(communicationInboundPortURI, routingInboundPortURI, Communication.class.getCanonicalName());
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		
	}
	
	public boolean hasRouteFor(AddressI address) throws Exception
	{
		return true;
	}
	
	public void ping()
	{
		
	}
	
	@Override
	public synchronized void execute() throws Exception
	{
		this.logMessage("test1");
		super.execute();
		this.logMessage("test2");
		try {
			connect(new NodeAddress("0.0.0.1", new Position(0, 0), 5), "cip-02");
		} catch(Exception e) {
			e.printStackTrace();
		}
		this.logMessage("test3");
		this.logMessage("nb voisins " + voisins.size());
		
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
		this.doPortDisconnection(COMMUNICATIONOUTBOUNDPORTURI);
		super.finalise();
	}
}
