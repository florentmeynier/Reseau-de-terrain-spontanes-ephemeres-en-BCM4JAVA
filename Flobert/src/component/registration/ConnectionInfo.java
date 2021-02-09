package component.registration;

import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;

public class ConnectionInfo 
{
	private NodeAddressI addr;
	private String communicationInboundPortURI;
	private String routingInboundURI;
	private PositionI pos;
	private double portee;
	
	public ConnectionInfo(NodeAddressI addr, String communicationInboundPortURI, String routingInboundURI, PositionI initialPosition, double initialRange) 
	{
		this.addr = addr;
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.routingInboundURI = routingInboundURI;
		this.pos = initialPosition;
		this.portee = initialRange;
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


	public PositionI getPos() {
		return pos;
	}


	public double getPortee() {
		return portee;
	}
	
	
}
