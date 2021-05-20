package component.terminalNode.interfaces;

import component.registration.interfaces.AddressI;
import component.registration.interfaces.NodeAddressI;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * interface de communication a la fois offerte et requises par les noeuds du reseau ephemere.
 * @author florentmeynier
 *
 */
public interface CommunicationCI extends OfferedCI, RequiredCI
{
	/**
	 * connecte deux noeuds du reseau ephemere.
	 * @param address
	 * @param communicationInboundPortURI
	 * @throws Exception
	 */
	public void connect(NodeAddressI address, String communicationInboundPortURI) throws Exception;
	/**
	 * connecte un noeud avec un noeud routeur du reseau ephemere.
	 * @param address
	 * @param communicationInboundPortURI
	 * @param routingInboundPortURI
	 * @throws Exception
	 */
	public void connectRouting(NodeAddressI address, String communicationInboundPortURI, String routingInboundPortURI) throws Exception;
	/**
	 * si le noeud du reseau ephemere est le destinataire du message alors il le recoit sinon il transmet le message sur le reseau.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception;
	/**
	 * cherche une route pour atteindre une destination dans les tables de routage.
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public boolean hasRouteFor(AddressI address) throws Exception;
	
	/**
	 * throw une ConnectException si le noeud est deconnecte.
	 */
	public void ping() throws Exception;
}
