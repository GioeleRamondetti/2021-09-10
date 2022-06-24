/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Business;
import it.polito.tdp.yelp.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnDistante"
    private Button btnDistante; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaPercorso"
    private Button btnCalcolaPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCitta"
    private ComboBox<String> cmbCitta; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB1"
    private ComboBox<String> cmbB1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbB2"
    private ComboBox<String> cmbB2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	if(!cmbCitta.getValue().equals("")) {
	    	model.creagrafo(cmbCitta.getValue());
	    	txtResult.setText("grafo creato con "+model.getNarchi()+" archi e "+model.getNvertici()+" vertici");
	    	btnDistante.setDisable(false);
	    	cmbB1.getItems().addAll(model.listaocalicittaString(cmbCitta.getValue()));
	    	
    	}
    }

    @FXML
    void doCalcolaLocaleDistante(ActionEvent event) {
    	if(!cmbCitta.getValue().equals("")) {
    		System.out.println(model.getB(cmbB1.getValue(),cmbCitta.getValue()));
    		txtResult.appendText("LOCALE PIU' DISTANTE \n"+model.distanzaMax(model.getB(cmbB1.getValue(),cmbCitta.getValue()), cmbCitta.getValue()));
    		
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	
    	txtResult.appendText(model.getpercorsomax(model.getB(cmbB1.getValue(),cmbCitta.getValue()), model.getB(cmbB2.getValue(),cmbCitta.getValue()), Integer.parseInt(txtX2.getText())).toString());
    	txtResult.appendText("distanza tot "+model.getDistanzatot());
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDistante != null : "fx:id=\"btnDistante\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCitta != null : "fx:id=\"cmbCitta\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB1 != null : "fx:id=\"cmbB1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbB2 != null : "fx:id=\"cmbB2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        btnDistante.setDisable(true);

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbCitta.getItems().addAll(this.model.listacitta());
    }
}
