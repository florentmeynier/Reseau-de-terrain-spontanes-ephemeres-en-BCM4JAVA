package component.networkNode;


import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;

@OfferedInterfaces(offered = {NetworkNodeCI.class})
@RequiredInterfaces(required = {NetworkNodeCI.class})
public class NetworkNode extends AbstractComponent 
{
	protected NetworkNodeInbound inboundPort;
	protected NetworkNodeOutbound outboundPort;
	public static final String SAMPLESNETWORKNODEINBOUNDPORTURI = "nnip-uri";
	public static final String SAMPLESNETWORKNODEOUTBOUNDPORTURI = "nnop-uri";
	public static int cpt = 0;
	protected final String NETWORKNODEINBOUNDPORTURI;
	private final String NETWORKNODEOUTBOUNDPORTURI;
	private NetworkAddressI addr;
	

	protected NetworkNode(NetworkAddressI addr) throws Exception 
	{
		super(1,0);
		this.addr = addr;
		this.NETWORKNODEINBOUNDPORTURI = SAMPLESNETWORKNODEINBOUNDPORTURI + cpt;
		this.NETWORKNODEOUTBOUNDPORTURI = SAMPLESNETWORKNODEOUTBOUNDPORTURI + cpt;
		this.inboundPort = new NetworkNodeInbound(this.NETWORKNODEINBOUNDPORTURI,this);
		this.outboundPort = new NetworkNodeOutbound(this.NETWORKNODEOUTBOUNDPORTURI,this);
		this.inboundPort.publishPort();
		this.outboundPort.publishPort();
		this.toggleLogging();
		this.toggleTracing();
		
	}


	public void connect(NetworkAddressI address, String networkNodeInboundPortURI) throws Exception
	{
		// TODO Auto-generated method stub
		
		this.doPortConnection(this.outboundPort.getPortURI(), networkNodeInboundPortURI, ConnectorNetworkNode.class.getCanonicalName());
	}


	public void transmitMessage(MessageI m) throws Exception
	{
		// TODO Auto-generated method stub
		if(m.getAddress().equals(addr))
		{
			this.logMessage("message recu " + m.getContent());
			return;
		}else
		{
			if(this.outboundPort.connected())
			{
				this.logMessage("message " + m.getContent() +" vivant ? " + m.stillAlive());

				if(m.stillAlive())
				{
					m.decrementHops();
					this.outboundPort.transmitMessage(m);
					this.logMessage("message "+ m.getContent() +" transmis au voisin");
				}
			}
		}
	}
	
	@Override
	public synchronized void execute() throws Exception
	{
		super.execute();
		
		
		
		
	}
	@Override
	public synchronized void shutdown() throws ComponentShutdownException
	{
		try {
			this.inboundPort.unpublishPort();
			this.outboundPort.unpublishPort();
		}  catch (Exception e) {
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
