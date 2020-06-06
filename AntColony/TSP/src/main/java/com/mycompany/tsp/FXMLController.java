package com.mycompany.tsp;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class FXMLController implements Initializable {

    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private Button addCityButton;
    @FXML
    private Button startACO;
    @FXML
    private Button addAllCitiesButton;
    @FXML
    private Button removeCityButton;
    @FXML
    private Button removeAllCitiesButton;
    @FXML
    private Label addInfoLabel;
    @FXML
    private Label bestLen;
    @FXML
    private Label bestOrder;
    @FXML
    private ListView<String> citiesListView;
    @FXML
    private ListView<String> finalOrderListView;
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

    private BidiMap<Integer, String> citiesMap = new DualHashBidiMap<>();
    private static BidiMap<Integer, String> selectedCitiesMap = new DualHashBidiMap<>();

    private static ObservableList<String> cities = FXCollections.observableArrayList();
    private static ObservableList<String> orderedCities=FXCollections.observableArrayList();

    private static String lengthResult;
    private static String orderResult;

    public static void setLengthResult(String res) {
        lengthResult = res;
    }

    public static void setOrderResult(String res) {
        orderResult = res;
    }

    public static BidiMap getSelectedCitiesMap() {
        return selectedCitiesMap;
    }

    @FXML
    void addCityButton(ActionEvent event) {
        if (cityComboBox.valueProperty().getValue() != null) {
            if (!cities.contains(cityComboBox.getValue())) {
                cities.add(cityComboBox.getValue());
                selectedCitiesMap.put(citiesMap.getKey(cityComboBox.getValue()), cityComboBox.getValue());
                System.out.println(selectedCitiesMap.toString());
                addInfoLabel.setText("city added");
            } else {
                addInfoLabel.setText("city already added");
            }
        } else {
            addInfoLabel.setText("select correct city");
        }
        refresh();
    }

    @FXML
    void addAllCitiesButton(ActionEvent event) {
        if (!cities.isEmpty() || !selectedCitiesMap.isEmpty()) {
            cities.clear();
            selectedCitiesMap.clear();
        }
        for (int i = 0; i <= 19; i++) {
            cities.add((String) citiesMap.get(i));
            selectedCitiesMap.put(citiesMap.getKey((String) citiesMap.get(i)), (String) citiesMap.get(i));
        }
        System.out.println(selectedCitiesMap.toString());
        addInfoLabel.setText("all cities added");
        refresh();
    }

    @FXML
    void removeCityButton(ActionEvent event) {
        if (citiesListView.getSelectionModel().getSelectedItem() != null) {
            String cityToRemove = citiesListView.getSelectionModel().getSelectedItem();
            cities.remove(cityToRemove);
            selectedCitiesMap.values().removeIf(value -> value.contains(cityToRemove));
            //selectedCitiesMap.remove(cityToRemove);
            System.out.println(selectedCitiesMap.toString());
            addInfoLabel.setText("city removed");
        } else {
            addInfoLabel.setText("Nothing to remove");
        }
        refresh();
    }
    
    @FXML
    void removeAllCitiesButton(ActionEvent event) {
        if (!cities.isEmpty() || !selectedCitiesMap.isEmpty()) {
            cities.clear();
            selectedCitiesMap.clear();
            System.out.println(selectedCitiesMap.toString());
            addInfoLabel.setText("All citites removed");
        }else{
            addInfoLabel.setText("Nothing to remove");
        }
        refresh();
    }

    @FXML
    void startAcoButton(ActionEvent event) {
        ACO antColony = new ACO(cities.size(), Integer.parseInt(colonySize.getText()));
        antColony.setIterations(Integer.parseInt(iterations.getText()));
        antColony.setAlpha(Double.parseDouble(alpha.getText()));
        antColony.setBeta(Double.parseDouble(beta.getText()));
        antColony.setEvaporation(Double.parseDouble(evaporation.getText()));
        antColony.setQ(Double.parseDouble(qVal.getText()));
        antColony.optimize(); //TO DO testing
        bestLen.setText(lengthResult);
        bestOrder.setText(orderResult);

        for (double[] row : antColony.generateCitiesMatrix(cities.size())) {
            System.out.println(Arrays.toString(row));
        }
        //todo: add ordered citites to finalOrderListView
        int[] xd=antColony.getBestOrder();
        System.out.println(xd);
    }

    void refresh() {
        citiesListView.setItems(cities);
    }

    public static ObservableList<String> getObservableList1() {
        return cities;
    }

    public void insertCitiesMap() {
        citiesMap.put(0, "KRAKOW");
        citiesMap.put(1, "WARSZAWA");
        citiesMap.put(2, "WROCLAW");
        citiesMap.put(3, "GDANSK");
        citiesMap.put(4, "BYDGOSZCZ");
        citiesMap.put(5, "POZNAN");
        citiesMap.put(6, "KATOWICE");
        citiesMap.put(7, "BIALYSTOK");
        citiesMap.put(8, "LODZ");
        citiesMap.put(9, "SANDOMIERZ");
        citiesMap.put(10, "KAZIMIERZ DOLNY");
        citiesMap.put(11, "RESZEL");
        citiesMap.put(12, "MIELNO");
        citiesMap.put(13, "KARPACZ");
        citiesMap.put(14, "OLKUSZ");
        citiesMap.put(15, "KRYNICA ZDROJ");
        citiesMap.put(16, "HEL");
        citiesMap.put(17, "ZAMOSC");
        citiesMap.put(18, "ZAKOPANE");
        citiesMap.put(19, "SZCZECIN");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insertCitiesMap();
        cityComboBox.setItems(FXCollections.observableArrayList(citiesMap.values()));
    }
}
