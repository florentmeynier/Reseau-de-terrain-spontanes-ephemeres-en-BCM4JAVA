package component.registration;

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
		
	}
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception
	{
		
	}
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception
	{
		
	}
	public void unregister(NodeAddressI address) throws Exception
	{
		
	}

}
