package component.communication.interfaces;

import java.io.Serializable;

import component.registration.interfaces.AddressI;

public interface MessageI
{
	public AddressI getAddress();
	public Serializable getContent();
	public boolean stillAlive();
	public void decrementHops();
}
