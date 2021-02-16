package component.registration;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;

public class NodeAddress implements NodeAddressI 
{
	private String addr;
	
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
		if(!a.isNodeAddress())
			return false;
		NodeAddress address = (NodeAddress)  a;
		if(!this.addr.equals(address.addr))
			return false;
		return true;
	}

	public String getAddr() {
		return addr;
	}

}
