package component.communication;

import java.util.HashSet;
import java.util.Set;

import component.communication.interfaces.CommunicationCI;
import component.communication.interfaces.MessageI;
import component.registration.NodeAddress;
import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;

@OfferedInterfaces(offered = {CommunicationCI.class})

public class Communication extends AbstractComponent 
{
	private NodeAddress address;
	private final String communicationInboundPortURI = "cmip-uri";
	
	private Set<Communication> voisins = new HashSet<>();
	
	protected Communication()
	{
		super(1,0);
	}
	
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception
	{

	}
	
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception
	{
		
	}
	
	public void transmitMessage(MessageI m) throws Exception
	{
		
	}
	
	public boolean HasRouteFor(AddressI address) throws Exception
	{
		
	}
	
	public void ping()
	{
		
	}
}
