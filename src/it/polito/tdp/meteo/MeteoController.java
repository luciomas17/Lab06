package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {
	
	Model model = new Model();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Integer> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		
		if(!boxMese.getSelectionModel().isEmpty()) {
			int m = boxMese.getValue();
			String best = model.trovaSequenza(m) ;
			txtResult.setText("Sequenza ottima per il mese "+m+":\n");
			txtResult.appendText(best+"\n");
		}
		else
			txtResult.setText("Seleziona un mese.");
	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		if(!this.boxMese.getSelectionModel().isEmpty()) {
			int mese = this.boxMese.getSelectionModel().getSelectedItem();
			this.txtResult.setText(model.getUmiditaMedia(mese));
			
		} else 
			this.txtResult.setText("Seleziona un mese.");
	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
	}

	public void setModel(Model model) {
		this.model = model;
		
		for(int i=1; i<=12; i++)
			boxMese.getItems().add(i);
	}
}