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

public class FXMLController implements Initializable {

    @FXML
    private ComboBox<CitiesEnum> cityComboBox;
    @FXML
    private Button addCityButton;
    @FXML
    private Label addInfoLabel;
    @FXML
    private ListView<CitiesEnum> citiesListView;

    private ObservableList<CitiesEnum> observableList1 = FXCollections.observableArrayList();

    @FXML
    void addCityButton(ActionEvent event) {
        if (cityComboBox != null) {
            if (!observableList1.contains(cityComboBox.getValue())) {
                observableList1.add(cityComboBox.getValue());
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
    
    void refresh(){
        citiesListView.setItems(observableList1);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cityComboBox.setItems(FXCollections.observableArrayList(CitiesEnum.values()));
    }
}
