package component.registration;

import component.registration.interfaces.NodeAddressI;

public class ConnectionInfo 
{
	private NodeAddressI addr;
	private String communicationInboundPortURI;
	private String routingInboundURI;
	
	public ConnectionInfo(NodeAddressI addr, String communicationInboundPortURI, String routingInboundURI) 
	{
		this.addr = addr;
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.routingInboundURI = routingInboundURI;
	}
	
	
	public boolean isRouting()
	{
		return true;
	}
	
	public NodeAddressI getAddress() 
	{
		return addr;
	}
	public String getCommunicationInboundPortURI() 
	{
		return communicationInboundPortURI;
	}

	public String getRoutingInboundURI() 
	{
		return routingInboundURI;
	}
}
