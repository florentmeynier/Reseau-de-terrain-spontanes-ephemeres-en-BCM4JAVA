package component.terminalNode;

import java.io.Serializable;

import component.registration.interfaces.AddressI;
import component.terminalNode.interfaces.MessageI;

/**
 * classe stockant les informations d'un message.
 * @author florentmeynier
 *
 */
public class Message implements MessageI 
{
	private AddressI address;
	private Serializable content;
	private int hops;
	
	/**
	 * constructeur qui initialise les parametres necessaires aux messages.
	 * @param address
	 * @param content
	 * @param hops
	 */
	public Message(AddressI address, Serializable content, int hops) 
	{
		this.address = address;
		this.content = content;
		this.hops = hops;
	}
	
	@Override
	public AddressI getAddress() 
	{
		return address;
	}

	@Override
	public Serializable getContent() 
	{
		return content;
	}

	@Override
	public boolean stillAlive() 
	
	{
		return (hops > 0);
	} 

	@Override
	public void decrementHops() 
	{
		hops--;
	}

	@Override
	public int getHops() {
		// TODO Auto-generated method stub
		return hops;
	}

}
