package component.registration;

import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;

/**
 * classe stockant les informations des noeuds connectes au reseau ephemere.
 * @author habibbouchenaki
 */
public class ConnectionInfo 
{
	private NodeAddressI addr;
	private String communicationInboundPortURI;
	private String routingInboundURI;
	private PositionI pos;
	private double portee;
	
	/**
	 * contructeur d'un ConnectionInfo qui initialise tous les parametres necessaires. 
	 * @param addr
	 * @param communicationInboundPortURI
	 * @param routingInboundURI
	 * @param initialPosition
	 * @param initialRange
	 */
	public ConnectionInfo(NodeAddressI addr, String communicationInboundPortURI, String routingInboundURI, PositionI initialPosition, double initialRange) 
	{
		this.addr = addr;
		this.communicationInboundPortURI = communicationInboundPortURI;
		this.routingInboundURI = routingInboundURI;
		this.pos = initialPosition;
		this.portee = initialRange;
	}
	
	/**
	 * verifie si un noeud est routeur.
	 * @return vrai si le noeud est routeur, faux sinon.
	 */
	public boolean isRouting()
	{
		if(routingInboundURI != null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * accesseur sur addr.
	 * @return l'addresse du noeud.
	 */
	public NodeAddressI getAddress() 
	{
		return addr;
	}
	
	/**
	 * accesseur sur communicationInboundPortURI.
	 * @return L'URI du CommunicationInboundPort du noeud.
	 */
	public String getCommunicationInboundPortURI() 
	{
		return communicationInboundPortURI;
	}
	
	/**
	 * accesseur sur routingInboundURI
	 * @return L'URI du RoutingInboundPort du noeud routeur.
	 */
	public String getRoutingInboundURI() 
	{
		return routingInboundURI;
	}

	/**
	 * accesseur sur la position.
	 * @return la position du noeud.
	 */
	public PositionI getPos() {
		return pos;
	}

	/**
	 * accesseur sur la portee.
	 * @return la portee du noeud.
	 */
	public double getPortee() {
		return portee;
	}
	
	
}
