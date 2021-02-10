package component.terminalNode;

import component.terminalNode.interfaces.NodeCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

public class TerminalNodeInbound extends AbstractInboundPort implements NodeCI {

	private static final long serialVersionUID = 1L;

	public TerminalNodeInbound(ComponentI owner) throws Exception
	{
		super(NodeCI.class, owner);
		assert(owner instanceof TerminalNode);
	}
	
	public TerminalNodeInbound(String uri, ComponentI owner) throws Exception
	{
		super(uri, NodeCI.class, owner);
		assert(owner instanceof TerminalNode);
	}
	
}
