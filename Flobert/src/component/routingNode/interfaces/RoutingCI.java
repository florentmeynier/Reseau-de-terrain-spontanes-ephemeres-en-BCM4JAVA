package component.routingNode.interfaces;

import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.routingNode.RouteInfo;
import fr.sorbonne_u.components.interfaces.OfferedCI;
import fr.sorbonne_u.components.interfaces.RequiredCI;

/**
 * interface a la fois requise et offerte par les noeuds routeurs.
 * @author florentmeynier
 *
 */
public interface RoutingCI extends OfferedCI, RequiredCI 
{
	/**
	 * met a jour les tables de routages d'un noeud routeur.
	 * @param neighbour
	 * @param routes
	 * @throws Exception
	 */
	public void updateRouting(NodeAddressI neighbour, Set<RouteInfo> routes) throws Exception;
	/**
	 * met a jour les nouvelles informations sur un accessPoint.
	 * @param neighbour
	 * @param numberOfHops
	 * @throws Exception
	 */
	public void updateAccessPoint(NodeAddressI neighbour, int numberOfHops) throws Exception;
	
}
