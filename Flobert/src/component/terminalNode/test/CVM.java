package component.terminalNode.test;

import component.registration.NodeAddress;
import component.registration.Position;
import component.registration.Registration;
import component.terminalNode.ConnectorRegistration;
import component.terminalNode.TerminalNode;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM {

	public CVM() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception
	{
				
		String nt1 = AbstractComponent.createComponent(TerminalNode.class.getCanonicalName(), new Object[] {new NodeAddress("0.0.0.1"), new Position(0,0), 10.0});		
		
		AbstractComponent.createComponent(Registration.class.getCanonicalName(), new Object[] {});
		
		this.doPortConnection(nt1, (TerminalNode.cpt-1)+TerminalNode.SAMPLESTERMINALNODEOUTBOUNDPORTURI+(TerminalNode.cpt-1), Registration.REGISTRATIONNODEINBOUNDPORTURI , ConnectorRegistration.class.getCanonicalName());
		
		super.deploy();
	}
	
	public static void main(String []args) 
	{
		try {
			CVM c = new CVM();
			c.startStandardLifeCycle(2000L);
			System.exit(0);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
