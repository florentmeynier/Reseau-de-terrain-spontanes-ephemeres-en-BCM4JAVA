package component.registration;

import component.registration.interfaces.PositionI;

/**
 * classe representant la position d'un noeud.
 * @author habibbouchenaki
 */
public class Position implements PositionI 
{
	private double x;
	private double y;
	
	/**
	 * initialise les coordonnees de la position.
	 * @param x
	 * @param y
	 */
	public Position(double x, double y) 
	{
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * accesseur sur X
	 * @return l'abscisse de la position.
	 */
	public double getX() 
	{
		return x;
	}

	/**
	 * accesseur sur Y
	 * @return l'ordonnee de la position.
	 */
	public double getY() 
	{
		return y;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (this.x != other.x)
			return false;
		if (this.y != other.y)
			return false;
		return true;
	}



	@Override
	public double distance(PositionI other) throws Exception 
	{
		// TODO Auto-generated method stub
		if(!(other instanceof Position))
			return -1;
		Position p = (Position)other;
		return Math.sqrt(Math.pow(p.x-this.x, 2)+Math.pow(p.y - this.y, 2));
	}

}
