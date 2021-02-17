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
	
	public Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, null, initialPosition, initialRange);
		
		if(tables.add(ci))
		{
			return tables;
		}
		return null;
		
	}
	
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, routingInboundPortURI, initialPosition, initialRange);
		if(tables.add(ci))
		{
			return tables;
		}
		return null;	
	}
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, routingInboundPortURI, initialPosition, initialRange);
		/*Set<ConnectionInfo> res = new HashSet<>();
		
		for(ConnectionInfo c : tables)
		{
			if(ci.getPos().distance(c.getPos()) > -1 && ci.getPos().distance(c.getPos()) <= ci.getPortee())
			{
				if(c.isRouting())
				{
					res.add(c);
				}
			}
		}*/
		if(tables.add(ci))
		{
			return tables;
		}
		return null;
	}
	
	public void unregister(NodeAddressI address) throws Exception
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
