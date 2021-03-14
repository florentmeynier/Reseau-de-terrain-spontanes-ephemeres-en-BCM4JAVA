package component.registration.interfaces;

/**
 * inteface representant  l'addresse d'un noeud.
 * @author habibbouchenaki
 */
public interface AddressI 
{
	/**
	 * 
	 * @return vrai si l'addresse est celle d'un noeud du reseau ephemere.
	 * @throws Exception
	 */
	public boolean isNodeAddress() throws Exception;
	/**
	 * 
	 * @return vrai si l'addresse est celle d'un noeud du reseau classique.
	 * @throws Exception
	 */
	public boolean isNetworkAddress() throws Exception;
	/**
	 * compare deux addresses
	 * @param a
	 * @return vrai si les deux addresses sont egales, faux sinon.
	 * @throws Exception
	 */
	public boolean equals(AddressI a) throws Exception;
}
