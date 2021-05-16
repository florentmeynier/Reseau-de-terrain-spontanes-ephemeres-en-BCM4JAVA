package component.registration;

import java.io.Serializable;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NetworkAddressI;

/**
 * classe representant l'addresse d'un noeud du reseau classique. 
 * @author habibbouchenaki
 */
public class NetworkAddress implements NetworkAddressI, Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String addr;
	
	/**
	 * constructeur qui initialise l'addresse. 
	 * @param addr
	 */
	public NetworkAddress(String addr) 
	{
		this.addr = addr;
	}
	
	/**
	 * accesseur sur addr.
	 * @return la chaine representant une addresse d'un noeud du reseau classique.
	 */
	public String getAddr() 
	{
		return addr;
	}

	@Override
	public boolean isNodeAddress() throws Exception 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNetworkAddress() throws Exception 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean equals(AddressI a) throws Exception 
	{
		// TODO Auto-generated method stub
		if(!a.isNetworkAddress())
			return false;
		NetworkAddress address = (NetworkAddress)  a;
		if(!this.addr.equals(address.addr))
			return false;
		return true;
	}

}
