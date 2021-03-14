package component.registration.interfaces;

import java.util.Set;

import component.registration.ConnectionInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;
/**
 * interface representant un gestionnaire du reseau ephemere. 
 * @author habibbouchenaki
 */
public interface RegistrationCI extends OfferedCI, RequiredCI
{
	/**
	 * enregistre un terminalNode au reseau ephemere.
	 * @param address
	 * @param connectionInboundURI
	 * @param initialPosition
	 * @param initialRange
	 * @return la liste des voisins du noeud terminal qui vient de s'enregistrer.
	 * @throws Exception
	 */
	public Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange) throws Exception;
	/**
	 * enregistre un AccessPointNode au reseau ephemere.
	 * @param address
	 * @param connectionInboundURI
	 * @param initialPosition
	 * @param initialRange
	 * @param routingInboundPortURI
	 * @return la liste des voisins du point d'acces qui vient de s'enregistrer.
	 * @throws Exception
	 */
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception;
	/**
	 * enregistre un RoutingNode au reseau ephemere.
	 * @param address
	 * @param connectionInboundURI
	 * @param initialPosition
	 * @param initialRange
	 * @param routingInboundPortURI
	 * @return la liste des voisins du noeud routeur qui vient de s'enregistrer.
	 * @throws Exception
	 */
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception;
	/**
	 * desenregistre un noeud du reseau ephemere.
	 * @param address
	 * @throws Exception
	 */
	public void unregister(NodeAddressI address) throws Exception;
}
