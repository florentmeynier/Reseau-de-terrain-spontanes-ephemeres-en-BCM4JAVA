package component.routingNode;

import component.registration.interfaces.AddressI;

public class RouteInfo 
{

	private AddressI destination;
	private int numberOfHops;
	
	
	public RouteInfo(AddressI destination, int numberOfHops) 
	{
		this.destination = destination;
		this.numberOfHops = numberOfHops;
	}
	
	public AddressI getDestination() 
	{
		return destination;
	}
	
	public int getNumberOfHops() 
	{
		return numberOfHops;
	}

	public void setNumberOfHops(int nb) 
	{
		// TODO Auto-generated method stub
		this.numberOfHops = nb;
	}
	
}
