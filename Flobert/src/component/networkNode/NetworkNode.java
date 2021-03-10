package component.networkNode;


import component.accessPointNode.AccessPointNode;
import component.networkNode.interfaces.NetworkNodeCI;
import component.registration.NetworkAddress;
import component.registration.interfaces.NetworkAddressI;
import component.terminalNode.Message;
import component.terminalNode.interfaces.MessageI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;


@RequiredInterfaces(required = {NetworkNodeCI.class})
public class NetworkNode extends AbstractComponent 
{
	protected NetworkNodeOutbound outboundPort;
	public static final String SAMPLESNETWORKNODEOUTBOUNDPORTURI = "nnop-uri";
	public static int cpt = 0;
	private final String NETWORKNODEOUTBOUNDPORTURI;
	private NetworkAddressI addr;
	

	protected NetworkNode(NetworkAddressI addr) throws Exception 
	{
		super(1,0);
		this.addr = addr;
		this.NETWORKNODEOUTBOUNDPORTURI = SAMPLESNETWORKNODEOUTBOUNDPORTURI + cpt;
		this.outboundPort = new NetworkNodeOutbound(this.NETWORKNODEOUTBOUNDPORTURI,this);
		this.outboundPort.publishPort();
		this.toggleLogging();
		this.toggleTracing();
		cpt++;
		
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
				this.logMessage("1");

				if(m.stillAlive())
				{
					m.decrementHops();
					this.logMessage("11");
					this.outboundPort.transmitMessage(m);
					this.logMessage("12");
					this.logMessage("message "+ m.getContent() +" transmis à l'accessPoint");
				}
			}
		}
	}
	
	public void transmitAddress (NetworkAddressI addr) throws Exception
	{
		if(this.outboundPort.connected())
		{
			this.logMessage("93");
			this.logMessage(this.outboundPort.getServerPortURI());
			this.outboundPort.transmitAddress(addr);
		}
	}
	
	@Override
	public synchronized void start() throws ComponentStartException
	{	
		try
		{
			this.doPortConnection(this.outboundPort.getPortURI(), AccessPointNode.SAMPLESACCESSPOINTNETWORKINBOUNDPORTURI+(cpt-1), ConnectorNetworkNode.class.getCanonicalName());
			
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
			MessageI m = new Message(new NetworkAddress("1.0.0.4"), "coucou" , 10);
			if(this.outboundPort.connected())
			{
				this.logMessage("73");
				transmitAddress(this.addr);
				this.logMessage("83");
				this.transmitMessage(m);
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
		try {
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
