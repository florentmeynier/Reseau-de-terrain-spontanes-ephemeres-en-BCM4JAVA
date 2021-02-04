package component.registration.interfaces;

public interface AddressI 
{
	public boolean isNodeAddress() throws Exception;
	public boolean isNetworkAddress() throws Exception;
	public boolean equals(AddressI addr) throws Exception;
}
