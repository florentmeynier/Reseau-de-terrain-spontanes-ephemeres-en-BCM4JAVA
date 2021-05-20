package component.terminalNode.test;

import component.accessPointNode.AccessPointNode;
import component.networkNode.NetworkNode;
import component.registration.NetworkAddress;
import component.registration.NodeAddress;
import component.registration.Position;
import component.registration.Registration;
import component.routingNode.RoutingNode;
import component.terminalNode.TerminalNode;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;

public class DistributedCVM extends AbstractDistributedCVM 
{

	protected static final String REGISTRATION_JVM_URI = "registration";
	protected static final String NETWORK1_JVM_URI = "network";
	public DistributedCVM(String[] args) throws Exception 
	{
		super(args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void instantiateAndPublish() throws Exception 
	{
		// TODO Auto-generated method stub
		if(AbstractCVM.getThisJVMURI().equals(REGISTRATION_JVM_URI))
		{
			AbstractComponent.createComponent(Registration.class.getCanonicalName(), new Object[] {});
			
		}else if(AbstractCVM.getThisJVMURI().equals(NETWORK1_JVM_URI))
		{
			AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.1"), new Position(1,1), 10.0});
			AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.2"), new Position(1,2), 20.0});		
			
			AbstractComponent.createComponent(RoutingNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.3"), new Position(1,4), 20.0});		
			AbstractComponent.createComponent(RoutingNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.4"), new Position(1,5), 20.0});		
			
			AbstractComponent.createComponent(AccessPointNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.5"), new Position(1,6), 10.0});		
			AbstractComponent.createComponent(AccessPointNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.6"), new Position(1,7), 9.0});		
			
			AbstractComponent.createComponent(NetworkNode.class.getCanonicalName(), new Object[] {new NetworkAddress("1.0.0.0")});
			AbstractComponent.createComponent(NetworkNode.class.getCanonicalName(), new Object[] {new NetworkAddress("1.0.0.4")});	
		}else 
		{
			System.out.println("Unknown JVM URI: " + AbstractCVM.getThisJVMURI());
		}
		super.instantiateAndPublish();
	}


	@Override
	public void interconnect() throws Exception 
	{
		// TODO Auto-generated method stub
		super.interconnect();
	}

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		try 
		{
			DistributedCVM dcvm = new DistributedCVM(args);
			dcvm.startStandardLifeCycle(10000L);
			Thread.sleep(20000L);
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
