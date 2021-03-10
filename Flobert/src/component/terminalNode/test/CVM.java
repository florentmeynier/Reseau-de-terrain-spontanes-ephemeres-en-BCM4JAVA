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

public class CVM extends AbstractCVM 
{

	public CVM() throws Exception 
	{
		super();
	}

	@Override
	public void deploy() throws Exception
	{
		
		AbstractComponent.createComponent(Registration.class.getCanonicalName(), new Object[] {});
		
		AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.1"), new Position(1,1), 10.0});
		AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.2"), new Position(1, 2), 20.0});		
		
		AbstractComponent.createComponent(RoutingNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.3"), new Position(1,4), 20.0});		
		AbstractComponent.createComponent(RoutingNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.4"), new Position(1,5), 20.0});		
		
		AbstractComponent.createComponent(AccessPointNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.5"), new Position(1,6), 10.0});		
		AbstractComponent.createComponent(AccessPointNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.6"), new Position(1,7), 9.0});		
		
		AbstractComponent.createComponent(NetworkNode.class.getCanonicalName(), new Object[] {new NetworkAddress("1.0.0.0")});
		AbstractComponent.createComponent(NetworkNode.class.getCanonicalName(), new Object[] {new NetworkAddress("1.0.0.4")});
		
		super.deploy();
	}
	
	public static void main(String []args) 
	{
		try 
		{
			CVM c = new CVM();
			c.startStandardLifeCycle(10000L);
			Thread.sleep(5000L);
			System.exit(0);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
