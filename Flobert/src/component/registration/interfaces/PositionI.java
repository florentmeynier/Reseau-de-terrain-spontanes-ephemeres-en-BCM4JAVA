package component.registration.interfaces;

/**
 * interface representant la position d'un noeud.
 * @author habibbouchenaki
 */
public interface PositionI 
{
	/**
	 * calcule la distante entre deux positions.
	 * @param other
	 * @return la distance entre deux positions.
	 * @throws Exception
	 */
	public double distance (PositionI other) throws Exception; 
}
