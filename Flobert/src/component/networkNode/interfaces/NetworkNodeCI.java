package component.networkNode.interfaces;

import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * Interface a la fois requise par les NetworkNode et offerte par les AccessPoint.
 * @author habibbouchenaki
 */
public interface NetworkNodeCI extends OfferedCI, RequiredCI
{
	/**
	 * permet de transmettre son addresse a l'AccePoint auquel on est connecte.
	 * @param addr
	 * @throws Exception
	 */
	public void transmitAddress(NetworkAddressI addr) throws Exception;
	/**
	 * permet de transmettre un message entre les noeuds du reseau classique et ceux du reseau ephemere.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception;

}
