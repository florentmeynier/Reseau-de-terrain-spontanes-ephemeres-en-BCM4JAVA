package component.registration;

import java.io.Serializable;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;

/**
 * classe representant l'addresse d'un noeud du reseau ephemere.
 * @author habibbouchenaki
 */
public class NodeAddress implements NodeAddressI, Serializable
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
	public NodeAddress(String addr) 
	{
		this.addr = addr;
	}

	@Override
	public boolean isNodeAddress() throws Exception 
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isNetworkAddress() throws Exception 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(AddressI a) throws Exception 
	{
		if(a == null)
		{
			return false;
		}
		if(!a.isNodeAddress())
			return false;
		NodeAddress address = (NodeAddress)  a;
		if(!this.addr.equals(address.addr))
			return false;
		return true;
	}

	/**
	 * accesseur sur addr.
	 * @return la chaine representant une addresse d'un noeud du reseau ephemere.
	 */
	public String getAddr() {
		return addr;
	}

}
