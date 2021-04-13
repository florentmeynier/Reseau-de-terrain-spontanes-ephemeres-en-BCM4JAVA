package component.networkNode;


import component.accessPointNode.AccessPointNode;
import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.NetworkAddress;
import component.registration.NodeAddress;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.Message;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

/**
 * classe representant un composant noeud du reseau.
 * @author habibbouchenaki
 */
@RequiredInterfaces(required = {NetworkNodeCI.class})
public class NetworkNode extends AbstractComponent 
{
	protected NetworkNodeOutbound outboundPort;
	public static final String SAMPLESNETWORKNODEOUTBOUNDPORTURI = "nnop-uri";
	public static int cpt = 0;
	private final String NETWORKNODEOUTBOUNDPORTURI;
	private NetworkAddressI addr;
	public static int cpt2 = 0;
	
	/**
	 * construit le NetwordNode et publie les noeuds necessaires.
	 * @param addr
	 * @throws Exception
	 */
	protected NetworkNode(NetworkAddressI addr) throws Exception 
	{
		super(1,0);
		this.addr = addr;
		this.NETWORKNODEOUTBOUNDPORTURI = SAMPLESNETWORKNODEOUTBOUNDPORTURI + cpt;
		this.outboundPort = new NetworkNodeOutbound(this.NETWORKNODEOUTBOUNDPORTURI,this);
		this.outboundPort.publishPort();
		this.toggleLogging();
		this.toggleTracing();
		createNewExecutorService("message-uri",1,false);
		createNewExecutorService("addr-uri",1,false);
		cpt++;
		
	}

	/**
	 * si le noeud du reseau est le destinataire du message alors il le recoit sinon si il est connecte a un AccessPoint, alors on lui transmet le message.
	 * @param m
	 * @throws Exception
	 */
	public void transmitMessage(MessageI m) throws Exception
	{
		// TODO Auto-generated method stub
		if(m.getAddress().equals(addr))
		{
			this.logMessage("message recu " + m.getContent());
			
		}else
		{
			if(this.outboundPort.connected())
			{
				this.logMessage("message " + m.getContent() +" vivant ? " + m.stillAlive());

				if(m.stillAlive())
				{
					m.decrementHops();
					this.outboundPort.transmitMessage(m);
					this.logMessage("message "+ m.getContent() +" transmis à l'accessPoint");
				}
			}else
			{
				this.logMessage("pas d'accesspoint connecte pour transferer le message "+m.getContent());
			}
		}
	}
	
	/**
	 * transmet l'addresse de ce noeud a son AccessPoint.
	 * @param addr
	 * @throws Exception
	 */
	public void transmitAddress (NetworkAddressI addr) throws Exception
	{
		if(this.outboundPort.connected())
		{
			this.outboundPort.transmitAddress(addr);
		}
	}
	
	@Override
	public synchronized void start() throws ComponentStartException
	{	
		try
		{
			this.doPortConnection(this.outboundPort.getPortURI(), AccessPointNode.SAMPLESACCESSPOINTNETWORKINBOUNDPORTURI+cpt2, ConnectorNetworkNode.class.getCanonicalName());
			cpt2++;
		}catch (Exception e)
		{
			throw new ComponentStartException(e);
		}
		super.start();
	}
	
	@Override
	public synchronized void execute() throws Exception
	{
		super.execute();
		try
		{
			MessageI m = new Message(new NodeAddress("0.0.0.2"), "coucou" , 10);
			MessageI m2 = new Message(new NetworkAddress("1.0.0.0"), "kiki" , 10);
			if(this.outboundPort.connected())
			{
				this.transmitAddress(this.addr);
				this.transmitMessage(m);
				this.transmitMessage(m2);
			}else
			{
				this.logMessage("pas d'accessPoint à qui relayer le message " + m.getContent());
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	@Override
	public synchronized void shutdown() throws ComponentShutdownException
	{
		try 
		{
			this.outboundPort.unpublishPort();
		}  catch (Exception e) 
		{
			throw new ComponentShutdownException(e);
		}
		super.shutdown();
	}
	
	@Override
	public synchronized void finalise() throws Exception
	{
		if(this.outboundPort.connected()) 
		{
			this.doPortDisconnection(this.outboundPort.getPortURI());
		}		
		super.finalise();

	}
	
}
