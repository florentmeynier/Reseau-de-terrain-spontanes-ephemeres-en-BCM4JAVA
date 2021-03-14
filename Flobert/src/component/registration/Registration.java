package component.registration;

import java.util.HashSet;
import java.util.Set;

import component.registration.interfaces.NodeAddressI;
import component.registration.interfaces.PositionI;
import component.registration.interfaces.RegistrationCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

/**
 * classe representant le composant gestionnaire du reseau ephemere.
 * @author habibbouchenaki
 */
@OfferedInterfaces(offered = {RegistrationCI.class})
public class Registration extends AbstractComponent 
{
	protected RegistrationInbound inboundPort;
	public static final String REGISTRATIONNODEINBOUNDPORTURI = "rip-uri";
	
	private Set<ConnectionInfo> tables = new HashSet<>();
	
	/**
	 * construit le gestionnaire du reseau et publie les ports necessaires.
	 * @throws Exception
	 */
	protected Registration() throws Exception 
	{
		super(1,0);
		this.inboundPort = new RegistrationInbound(REGISTRATIONNODEINBOUNDPORTURI, this);
		this.inboundPort.publishPort();
	}
	
	/**
	 * enregistre un TerminalNode au reseau ephemere.
	 * @param address
	 * @param connectionInboundURI
	 * @param initialPosition
	 * @param initialRange
	 * @return la liste des voisins du noeud terminal qui vient de s'enregistrer.
	 * @throws Exception
	 */
	public synchronized Set<ConnectionInfo> registerTerminalNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, null, initialPosition, initialRange);
		
		
		return getInPortee(ci);
		
		
	}
	
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
	public Set<ConnectionInfo> registerAccessPoint(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, routingInboundPortURI, initialPosition, initialRange);
		
		return getInPortee(ci);
	}
	
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
	public Set<ConnectionInfo> registerRoutingNode(NodeAddressI address, String connectionInboundURI, PositionI initialPosition, double initialRange, String routingInboundPortURI) throws Exception
	{
		ConnectionInfo ci = new ConnectionInfo(address, connectionInboundURI, routingInboundPortURI, initialPosition, initialRange);
		
		return getInPortee(ci);
	}
	
	/**
	 * desenregistre un noeud du reseau ephemere.
	 * @param address
	 * @throws Exception
	 */
	public synchronized void unregister(NodeAddressI address) throws Exception
	{
		for(ConnectionInfo c : tables)
		{
			if(c.getAddress().equals(address))
			{
				tables.remove(c);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param ci
	 * @return les noeuds qui sont a portee de ci.
	 * @throws Exception
	 */
	private synchronized Set<ConnectionInfo> getInPortee(ConnectionInfo ci) throws Exception {
		Set<ConnectionInfo> res = new HashSet<ConnectionInfo>();
		for(ConnectionInfo c : tables) 
		{
			if(ci.getPos().distance(c.getPos()) <= ci.getPortee()) {
				res.add(c);
			}
		}
		tables.add(ci);
		return res;
	}
	
	@Override
	public synchronized void shutdown() throws ComponentShutdownException
	{
		try {
			this.inboundPort.unpublishPort();
		}  catch (Exception e) {
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}

}
