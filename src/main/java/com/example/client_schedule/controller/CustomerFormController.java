package com.example.client_schedule.controller;

import com.example.client_schedule.entities.Customer;
import com.example.client_schedule.entities.Division;
import com.example.client_schedule.helper.DBContext;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import org.hibernate.sql.ast.tree.from.TableAliasResolver;

public class CustomerFormController implements Initializable {
    public CustomerFormController(DBContext db, String userName) {
        this.db = db;
        this.userName = userName;
    }

    private ResourceBundle _bundle;

    private DBContext db;
    private String userName;

    private StringConverter<Integer> stringConverter = new StringConverter<Integer>() {
        @Override
        public String toString(Integer integer) {
            if (integer == null) return null;
            return integer.toString();
        }

        @Override
        public Integer fromString(String s) {
            if (s == null) return null;
            return Integer.parseInt(s);
        }
    };

    @FXML
    private TextField textName;

    @FXML
    private TextField textAddress;

    @FXML
    private TableView<Customer> tableView;

    @FXML
    private VBox tableArea;

    @FXML
    protected Button insertButton;

    @FXML
    private EventHandler<ActionEvent> onInsertAction;

    @FXML
    protected Button deleteButton;

    @FXML
    private EventHandler<ActionEvent> onDeleteAction;

    @FXML
    protected Button revertButton;

    @FXML
    private EventHandler<ActionEvent> onRevertAction;

    @FXML
    protected Button commitButton;

    @FXML
    private EventHandler<ActionEvent> onCommitAction;

    @FXML
    protected HBox tableViewControlButtons;

    @FXML
    protected HBox addInsert;

    @FXML
    protected HBox commitRevert;

    @FXML
    protected VBox tabContent;

    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
        onCommitAction = e -> dbCommit();
        onRevertAction = e -> dbRevert();
        onInsertAction = e -> recordAdd();
        onDeleteAction = e -> recordRemove();
        tableView.setEditable(true);
        addCustomerColumns();
//        tableView.setRowFactory(tableView -> {
//            TableRow<Customer> row = new TableRow<>();
//            ObjectProperty<Customer> opMsg = row.itemProperty();
//            Customer tmpMsg = opMsg.get();
//            return row;
//        });
        addCustomerRows();
    }

    private void addCustomerRows() {
        tableView.setItems(db.customers);
    }

    private void addCustomerColumns() {
        TableColumn<Customer, Integer> idCol = new TableColumn<>("id");
        TableColumn<Customer, String> nameCol = new TableColumn<>(_bundle.getString("label.customer.name.text"));
        TableColumn<Customer, String> addressCol = new TableColumn<>(_bundle.getString("label.customer.address.text"));
        TableColumn<Customer, String> postalCol = new TableColumn<>(_bundle.getString("label.customer.postal.text"));
        TableColumn<Customer, String> phoneCol = new TableColumn<>(_bundle.getString("label.customer.phone.text"));
        TableColumn<Customer, Integer> divisionIdCol = new TableColumn<>(_bundle.getString("label.customer.division.text"));
        TableColumn<Customer, Division> divisionCol = new TableColumn<>(_bundle.getString("label.customer.division.text"));

        PropertyValueFactory<Customer, Integer> idproperty = new PropertyValueFactory<Customer, Integer>("id");
        PropertyValueFactory<Customer, String> nameproperty = new PropertyValueFactory<Customer, String>("name");
        PropertyValueFactory<Customer, String> addressproperty = new PropertyValueFactory<Customer, String>("address");
        PropertyValueFactory<Customer, String> postalproperty = new PropertyValueFactory<Customer, String>("zip");
        PropertyValueFactory<Customer, String> phoneproperty = new PropertyValueFactory<Customer, String>("phone");
        PropertyValueFactory<Customer, Integer> divisionIdproperty = new PropertyValueFactory<Customer, Integer>("divisionId");
        PropertyValueFactory<Customer, Division> divisionproperty = new PropertyValueFactory<Customer, Division>("division");
//        idCol.setCellValueFactory(cellData ->
//                new SimpleStringProperty(Integer.toString(cellData.getValue().getId())));
        idCol.setCellValueFactory(idproperty);
        idCol.setCellFactory(TextFieldTableCell.forTableColumn(stringConverter));

//        addressCol.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getAddress()));
        addressCol.setCellValueFactory(addressproperty);
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        nameCol.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setCellValueFactory(nameproperty);
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        postalCol.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getZip()));
        postalCol.setCellValueFactory(postalproperty);
        postalCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        phoneCol.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getPhone()));
        phoneCol.setCellValueFactory(phoneproperty);
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());

//        divisionIdCol.setCellValueFactory(cellData -> cellData.getValue().getDivisionId());
////                new SimpleIntegerProperty(Integer.toString(cellData.getValue().getDivisionId())));
//        divisionIdCol.setCellFactory(ComboBoxTableCell.forTableColumn(db.divisions));

        divisionCol.setCellValueFactory(divisionproperty);
//        divisionCol.setCellFactory(ComboBoxTableCell.forTableColumn(db.divisions));

        divisionCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Division>() {
            @Override
            public String toString(Division division) {
                return division.getName();
            }

            @Override
            public Division fromString(String s) {
                return null;
            }
        }, db.divisions));

        idCol.setVisible(false);
        tableView.getColumns().addAll(idCol, nameCol, addressCol, postalCol, phoneCol, divisionCol);
    }

    public DBContext getDb() {
        return db;
    }

    public void setDb(DBContext db) {
        this.db = db;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private void dbCommit() {

    }

    private void dbRevert() {

    }

    private void recordAdd() {

    }

    private void recordRemove() {

    }

}
