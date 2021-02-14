package component.communication.test;

import component.communication.Communication;
import component.communication.Connector;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

public class CVM extends AbstractCVM {

	public CVM() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception
	{
		//AbstractComponent.createComponent(Communication.class.getCanonicalName(), new Object[] {});
		
		String component = AbstractComponent.createComponent(Communication.class.getCanonicalName(), new Object[] {});
		
		//this.doPortConnection(component, Communication.COMMUNICATIONOUTBOUNDPORTURI + Communication.cpt, Communication.COMMUNICATIONINBOUNDPORTURI+Communication.cpt, Connector.class.getCanonicalName());
		
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