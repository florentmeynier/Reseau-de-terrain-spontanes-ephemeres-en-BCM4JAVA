package component.registration;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NetworkAddressI;

public class NetworkAddress implements NetworkAddressI 
{
	private String addr;
	
	
	public NetworkAddress(String addr) 
	{
		this.addr = addr;
	}

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
