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
import javafx.scene.layout.StackPane;

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

/**
 * The type Customer form controller. This class controls all the functionality for Customer management.
 */
public class CustomerFormController implements Initializable {
    /**
     * Instantiates a new Customer form controller.
     *
     * @param db       the db
     * @param userName the user name
     */
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
    private StackPane tableArea;

    /**
     * The Insert button.
     */
    @FXML
    protected Button insertButton;

    @FXML
    private EventHandler<ActionEvent> onInsertAction;

    /**
     * The Delete button.
     */
    @FXML
    protected Button deleteButton;

    @FXML
    private EventHandler<ActionEvent> onDeleteAction;

    /**
     * The Revert button.
     */
    @FXML
    protected Button revertButton;

    @FXML
    private EventHandler<ActionEvent> onRevertAction;

    /**
     * The Commit button.
     */
    @FXML
    protected Button commitButton;

    @FXML
    private EventHandler<ActionEvent> onCommitAction;

    /**
     * The Table view control buttons.
     */
    @FXML
    protected HBox tableViewControlButtons;

    /**
     * The Add insert.
     */
    @FXML
    protected HBox addInsert;

    /**
     * The Commit revert.
     */
    @FXML
    protected HBox commitRevert;

    /**
     * The Tab content.
     */
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

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        idCol.setCellFactory(TextFieldTableCell.forTableColumn(stringConverter));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        postalCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        divisionIdCol.setCellFactory(ComboBoxTableCell.forTableColumn(db.divisions));

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

    /**
     * Gets db.
     *
     * @return the db
     */
    public DBContext getDb() {
        return db;
    }

    /**
     * Sets db.
     *
     * @param db the db
     */
    public void setDb(DBContext db) {
        this.db = db;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
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
