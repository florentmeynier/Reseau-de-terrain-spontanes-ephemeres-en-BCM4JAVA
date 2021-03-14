package component.terminalNode.interfaces;

import java.io.Serializable;

import component.registration.interfaces.AddressI;

/**
 * interface representant les messages echanges sur le reseau.
 * @author florentmeynier
 *
 */
public interface MessageI
{
	/**
	 * accesseur sur la destination.
	 * @return le destinataire du message.
	 */
	public AddressI getAddress();
	/**
	 * accesseur sur le contenu.
	 * @return le contenu du message.
	 */
	public Serializable getContent();
	/**
	 * accesseur sur le nombre de sauts restants.
	 * @return le nombre de sauts restants du message.
	 */
	public int getHops();
	/**
	 * verifie que le message est encore vivant.
	 * @return vrai si le nombre de sauts restants est positif, faux sinon.
	 */
	public boolean stillAlive();
	/**
	 * decremente le nombre de sauts restants.
	 */
	public void decrementHops();
}
