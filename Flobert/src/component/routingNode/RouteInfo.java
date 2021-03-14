package component.routingNode;

import component.registration.interfaces.AddressI;
/**
 * classe stockant les informations d'une route vers une destination.
 * @author florentmeynier
 *
 */
public class RouteInfo 
{

	private AddressI destination;
	private int numberOfHops;
	
	/**
	 * construit une RouteInfo en initialisant les parametres necessaires.
	 * @param destination
	 * @param numberOfHops
	 */
	public RouteInfo(AddressI destination, int numberOfHops) 
	{
		this.destination = destination;
		this.numberOfHops = numberOfHops;
	}
	
	/**
	 * acceseur sur la destination.
	 * @return l'addresse de la destination.
	 */
	public AddressI getDestination() 
	{
		return destination;
	}
	/**
	 * accesseur sur le nombre de sauts.
	 * @return le nombre de sauts pour atteindre la destination.
	 */
	public int getNumberOfHops() 
	{
		return numberOfHops;
	}

	
}
