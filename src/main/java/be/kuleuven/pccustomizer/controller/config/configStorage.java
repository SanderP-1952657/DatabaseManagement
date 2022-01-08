package be.kuleuven.pccustomizer.controller.config;

import be.kuleuven.pccustomizer.controller.Objects.Component;
import be.kuleuven.pccustomizer.controller.Objects.Storage;
import be.kuleuven.pccustomizer.ProjectMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class configStorage extends _beheerConfig {
    Component component = new Component();
    List<Storage> storages = new ArrayList<Storage>();
    //table
    @FXML
    private TableView<Storage> tableView;

    @FXML
    private TableColumn<Storage, String> nameColumn;
    @FXML
    private TableColumn<Storage, String> typeColumn;
    @FXML
    private TableColumn<Storage, Integer> priceColumn;
    @FXML
    private TableColumn<Storage, Integer> sizeColumn;
    @FXML
    private TableColumn<Storage, Integer> readSpeedColumn;
    @FXML
    private TableColumn<Storage, Integer> writeSpeedColumn;

    public void initialize() {
        initTableComponenten();
        ReadFromDB();
        initTable();
        btnAdd.setOnAction(e -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                addComponent();
                showBeheerScherm("Motherboard");
            }});
        btnClose.setOnAction(e -> {
            var stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }


    public void ReadFromDB(){
        List<String> names = readDBstring("Storage","Name");
        List<String> types =  readDBstring("Storage","Type");
        List<Integer> prices =  readDBint("Storage","Price");
        List<Integer> sizes =  readDBint("Storage","Size");
        List<Integer> readSpeeds =  readDBint("Storage","Read_speed");
        List<Integer> writeSpeeds =  readDBint("Storage","Write_speed");

        for(int i = 0; i < names.size(); i++){
            storages.add(new Storage(names.get(i), types.get(i), prices.get(i), sizes.get(i), readSpeeds.get(i), writeSpeeds.get(i)));
        }
    }
    public void initTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Storage, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Storage, String>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("price"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("size"));
        readSpeedColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("readSpeed"));
        writeSpeedColumn.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("writeSpeed"));

        ObservableList<Storage> storageList = tableView.getItems();
        storageList.addAll(storages);
        tableView.setItems(storageList);
    }

    private void addComponent(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Storage storage = tableView.getSelectionModel().getSelectedItem();
            component.setName(storage.getName());
            componenten.add(component);
        }
    }

    public void initTableComponenten() {
        componentColumn.setCellValueFactory(new PropertyValueFactory<Component, String>("name"));
        ObservableList<Component> viewComponenten = FXCollections.observableArrayList();
        viewComponenten.addAll(componenten);
        System.out.println(viewComponenten);
        componentView.setItems(viewComponenten);
    }
}
