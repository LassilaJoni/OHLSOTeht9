package org.example;

public interface IHinnoittelija {
	public abstract float getAlennusProsentti(Asiakas asiakas, Tuote tuote);

	void aloita();
	void lopeta();

	void setAlennusProsentti(Asiakas asiakas, float v);
}
