package component.terminalNode.test;

import component.accessPointNode.AccessPointNode;
import component.registration.NodeAddress;
import component.registration.Position;
import component.registration.Registration;
import component.routingNode.RoutingNode;
import component.terminalNode.ConnectorRegistration;
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

		
		String nt1 = AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.1"), new Position(0,0), 10.0});
		this.doPortConnection(nt1, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
		String nt2 = AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.2"), new Position(0,1), 10.0});		
		this.doPortConnection(nt2, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
		
		/*
		String net1 = AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NetworkAddress("0.0.0.7"), new Position(1,1), 5.5});
		this.doPortConnection(net1, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
		String net2 = AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NetworkAddress("0.0.0.8"), new Position(3,1), 5.5});
		this.doPortConnection(net2, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
		*/
		
		String rn1 = AbstractComponent.createComponent(RoutingNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.3"), new Position(0,2), 10.0});		
		this.doPortConnection(rn1, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
		String rn2 = AbstractComponent.createComponent(RoutingNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.4"), new Position(0,3), 10.0});		
		this.doPortConnection(rn2, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
	
		String apn1 = AbstractComponent.createComponent(AccessPointNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.5"), new Position(0,3), 10.0});		
		this.doPortConnection(apn1, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
		String apn2 = AbstractComponent.createComponent(AccessPointNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.6"), new Position(0,4), 10.0});		
		this.doPortConnection(apn2, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
		

		
		super.deploy();
	}
	
	public static void main(String []args) 
	{
		try 
		{
			CVM c = new CVM();
			c.startStandardLifeCycle(20000L);
			System.exit(0);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
