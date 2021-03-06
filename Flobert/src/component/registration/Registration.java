package component.registration;

import java.util.HashSet;
import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {RegistrationCI.class})
public class Registration extends AbstractComponent 
{
	protected RegistrationInbound inboundPort;
	public static final String REGISTRATIONNODEINBOUNDPORTURI = "rip-uri";
	
	private Set<ConnectionInfo> tables = new HashSet<>();
	
	protected Registration() throws Exception 
	{
		super(1,0);
		this.inboundPort = new RegistrationInbound(REGISTRATIONNODEINBOUNDPORTURI, this);
		this.inboundPort.publishPort();
	}
	
	public synchronized Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, null, initialPosition, initialRange);
		
		
		return getInPortee(ci);
		
		
	}
	
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, routingInboundPortURI, initialPosition, initialRange);
		
		return getInPortee(ci);
	}
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, routingInboundPortURI, initialPosition, initialRange);
		
		return getInPortee(ci);
	}
	
	public synchronized void unregister(NodeAddressI address) throws Exception
	{
		for(ConnectionInfo c : tables)
		{
			if(c.getAddress().equals(address))
			{
				tables.remove(c);
				break;
			}
		}
	}
	
	private synchronized Set<ConnectionInfo> getInPortee(ConnectionInfo ci) throws Exception {
		Set<ConnectionInfo> res = new HashSet<ConnectionInfo>();
		for(ConnectionInfo c : tables) 
		{
			if(ci.getPos().distance(c.getPos()) <= ci.getPortee()) {
				res.add(c);
			}
		}
		tables.add(ci);
		return res;
	}
	
	@Override
	public synchronized void shutdown() throws ComponentShutdownException
	{
		try {
			this.inboundPort.unpublishPort();
		}  catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

}
