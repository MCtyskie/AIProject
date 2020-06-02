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

    @FXML
    void addCityButton(ActionEvent event) {
        if (cityComboBox != null) {
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
        ACO antColony = new ACO(cities.size());
        //antColony.optimize();
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
