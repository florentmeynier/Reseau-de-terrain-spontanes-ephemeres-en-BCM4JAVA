package component.registration;

import java.util.HashSet;
import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;

@OfferedInterfaces(offered = {RegistrationCI.class})
public class Registration extends AbstractComponent 
{
	private Set<ConnectionInfo> tables;
	
	protected Registration() 
	{
		super(1,0);
	}
	
	public Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange) throws Exception
	{
		return registerRoutingNode(address,connectionInboundURI,initialPosition,initialRange, null);
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
		Set<ConnectionInfo> res = new HashSet<>();
		
		for(ConnectionInfo c : tables)
		{
			if(ci.getPos().distance(c.getPos()) > -1 && ci.getPos().distance(c.getPos()) <= ci.getPortee())
			{
				if(c.isRouting())
				{
					res.add(c);
				}
			}
		}
		if(tables.add(ci))
		{
			return res;
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

}
