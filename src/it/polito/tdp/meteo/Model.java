package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	MeteoDAO dao;
	private List<Citta> leCitta;
	private List<Citta> best;

	public Model() {
		this.dao = new MeteoDAO();
		this.leCitta = dao.getCittaPresentiNelDB();
	}

	public String getUmiditaMedia(int mese) {
		
		String s = "";
		
		for(Citta c : leCitta) {
			double avg = dao.getAvgRilevamentiLocalitaMese(mese, c.getNome());
			s += "Città: " + c.getNome() + ", Umidità media: " + avg + "\n";
		}
		
		return s;
	}

	public String trovaSequenza(int mese) {
		
		List<Citta> parziale = new ArrayList<Citta>();
		this.best = null;
		
		for (Citta c : leCitta) {
			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		}
		
		recursive(parziale, 0);

		return best.toString();
	}
	
	private void recursive(List<Citta> parziale, int L) {
		
		if(L == NUMERO_GIORNI_TOTALI) {
			Double costo = punteggioSoluzione(parziale);
			
			if (best == null || costo < punteggioSoluzione(best))
				best = new ArrayList<>(parziale);
			
		} else {
			
			for(Citta prova : leCitta) {
				
				if(controllaParziale(prova, parziale)) {
					parziale.add(prova);
					recursive(parziale, L+1);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	}

	private Double punteggioSoluzione(List<Citta> parziale) {

		double score = 0.0;
		
		for(int i=1; i<=NUMERO_GIORNI_TOTALI; i++) {
			Citta c = parziale.get(i-1);
			double umidita = c.getRilevamenti().get(i-1).getUmidita();
			score += umidita;
		}
		
		for(int i=2; i<=NUMERO_GIORNI_TOTALI; i++) {
			if(!parziale.get(i-1).equals(parziale.get(i-2)))
				score += COST;
		}
		
		return score;
	}

	private boolean controllaParziale(Citta prova, List<Citta> parziale) {
		
		// verifica giorni max
		int counter = 0;
		for(Citta precedente : parziale) {
			if(precedente.equals(prova))
				counter++;
		}
		if(counter >= NUMERO_GIORNI_CITTA_MAX)
			return false;

		// verifica giorni min
		if(parziale.size() == 0) // primo giorno
			return true;
		if(parziale.size() == 1 || parziale.size() == 2) // secondo o terzo giorno: non posso cambiare città
			return parziale.get(parziale.size()-1).equals(prova);
		if(parziale.get(parziale.size()-1).equals(prova)) // giorni successivi
			return true;
		
		if(parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) && 
				parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
			return true;
		
		return false;
	}
}