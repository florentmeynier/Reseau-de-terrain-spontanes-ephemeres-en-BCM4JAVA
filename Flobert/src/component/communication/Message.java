package component.communication;

import java.io.Serializable;

import component.communication.interfaces.MessageI;
import component.registration.interfaces.AddressI;


public class Message implements MessageI 
{
	private AddressI address;
	private Serializable content;
	private int hops;
	
	public Message(AddressI address, Serializable content, int hops) 
	{
		this.address = address;
		this.content = content;
		this.hops = hops;
	}
	
	@Override
	public AddressI getAddress() 
	{
		// TODO Auto-generated method stub
		return address;
	}

	@Override
	public Serializable getContent() 
	{
		// TODO Auto-generated method stub
		return content;
	}

	@Override
	public boolean stillAlive() 
	
	{
		// TODO Auto-generated method stub
		return (hops > 0);
	} 

	@Override
	public void decrementHops() 
	{
		// TODO Auto-generated method stub
		hops--;
	}

}
