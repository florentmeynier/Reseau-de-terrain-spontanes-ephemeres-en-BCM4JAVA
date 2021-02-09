package component.registration;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;

public class NodeAddress implements NodeAddressI 
{
	private String addr;
	
	public NodeAddress(String addr, PositionI pos, double portee) 
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
		// TODO Auto-generated method stub
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