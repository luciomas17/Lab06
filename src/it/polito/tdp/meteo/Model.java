package it.polito.tdp.meteo;

import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	MeteoDAO dao = new MeteoDAO();

	public Model() {

	}

	public String getUmiditaMedia(int mese) {
		String s = "";
		
		List<Citta> citta = dao.getCittaPresentiNelDB();
		
		for(Citta c : citta) {
			double avg = dao.getAvgRilevamentiLocalitaMese(mese, c.getNome());
			s += "Città: " + c.getNome() + ", Umidità media: " + avg + "\n";
		}
		
		return s;
	}

	public String trovaSequenza(int mese) {

		return "TODO!";
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		return true;
	}

}
