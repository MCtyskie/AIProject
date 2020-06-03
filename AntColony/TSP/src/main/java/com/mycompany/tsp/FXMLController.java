package com.mycompany.tsp;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {

    @FXML
    private ComboBox<CitiesEnum> cityComboBox;
    @FXML
    private Button addCityButton;
    @FXML
    private Button startACO;
    @FXML
    private Label addInfoLabel;
    @FXML
    private Label bestLen;
    @FXML
    private Label bestOrder;
    @FXML
    private ListView<CitiesEnum> citiesListView;
    @FXML
    private TextField colonySize;
    @FXML
    private TextField iterations;
    @FXML
    private TextField alpha;
    @FXML
    private TextField beta;
    @FXML
    private TextField evaporation;
    @FXML
    private TextField qVal;
    
    private static ObservableList<CitiesEnum> cities = FXCollections.observableArrayList();
    
    private static String lengthResult;
    private static String orderResult;

    public static void setLengthResult(String res){
        lengthResult=res;
    }
    
    public static void setOrderResult(String res){
        orderResult=res;
    }
    
    @FXML
    void addCityButton(ActionEvent event) {
        if (cityComboBox.valueProperty().getValue() != null) {
            if (!cities.contains(cityComboBox.getValue())) {
                cities.add(cityComboBox.getValue());
                addInfoLabel.setText("city added");
            }
            else{
                addInfoLabel.setText("city already added");
            }
        } else {
            addInfoLabel.setText("select correct city");
        }
        refresh();
    }
    
    @FXML
    void startAcoButton(ActionEvent event){
        ACO antColony = new ACO(cities.size(),Integer.parseInt(colonySize.getText()));
        antColony.setIterations(Integer.parseInt(iterations.getText()));
        antColony.setAlpha(Double.parseDouble(alpha.getText()));
        antColony.setBeta(Double.parseDouble(beta.getText()));
        antColony.setEvaporation(Double.parseDouble(evaporation.getText()));
        antColony.setQ(Double.parseDouble(qVal.getText()));
        antColony.optimize(); //TO DO testing
        bestLen.setText(lengthResult);
        bestOrder.setText(orderResult);
    }
    
    void refresh(){
        citiesListView.setItems(cities);
    }

    public static ObservableList<CitiesEnum> getObservableList1() {
        return cities;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cityComboBox.setItems(FXCollections.observableArrayList(CitiesEnum.values()));
    }
}
