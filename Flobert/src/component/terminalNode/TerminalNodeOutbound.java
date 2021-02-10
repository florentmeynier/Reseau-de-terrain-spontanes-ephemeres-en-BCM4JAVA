package component.terminalNode;

import component.terminalNode.interfaces.NodeCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

public class TerminalNodeOutbound extends AbstractOutboundPort implements NodeCI {

	private static final long serialVersionUID = 1L;

	public TerminalNodeOutbound(ComponentI owner) throws Exception {
		super(TerminalNode.class, owner);
	}
	
	public TerminalNodeOutbound(String uri, ComponentI owner) throws Exception {
		super(uri, TerminalNode.class, owner);
	}
	
}
