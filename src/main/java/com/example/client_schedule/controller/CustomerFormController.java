package com.example.client_schedule.controller;

import com.example.client_schedule.entities.Country;
import com.example.client_schedule.entities.Customer;
import com.example.client_schedule.entities.Division;
import com.example.client_schedule.helper.DBContext;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * The type Customer form controller. This class controls all the functionality for Customer management.
 */
public class CustomerFormController extends Customer implements Initializable {
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
    private TextField textID;

    @FXML
    private TextField textName;

    @FXML
    private TextField textAddress;

    @FXML
    private ComboBox<Country> comboBoxCountry;

    @FXML
    private ComboBox<Division> comboBoxDivision;

    @FXML
    private TextField textPostal;

    @FXML
    private TextField textPhone;

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

    @FXML
    private EventHandler<ActionEvent> onDivisionSelectionAction;

    @FXML
    private EventHandler<ActionEvent> onCountrySelectionAction;

    private FilteredList<Division> filteredDivisionList;
    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;

        onCommitAction = e -> dbCommit();
        onRevertAction = e -> dbRevert();
        onInsertAction = e -> recordAdd();
        onDeleteAction = e -> recordRemove();
        onDivisionSelectionAction = this::getComboDivisionId;
        onCountrySelectionAction = this::getComboCountryId;

        deleteButton.setOnAction(onDeleteAction);
        insertButton.setOnAction(onInsertAction);
        commitButton.setOnAction(onCommitAction);
        revertButton.setOnAction(onRevertAction);
        comboBoxCountry.setOnAction(onCountrySelectionAction);
        filteredDivisionList = new FilteredList<>(db.divisions, p -> true);
        comboBoxDivision.setItems(filteredDivisionList);
        comboBoxDivision.setConverter(new StringConverter<Division>() {
              @Override
              public String toString(Division division) {
                  if (division == null) {
                      return "";
                  } else {
                      return division.getName();
                  }
              }

              @Override
              public Division fromString(String s) {
                  return comboBoxDivision.getItems().stream().filter(ap -> ap.getName().equals(s)).findFirst().orElse(null);
              }
          }

        );
        comboBoxCountry.setItems(db.countries);
        comboBoxCountry.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                if (country == null) {
                    return "";
                } else {
                    return country.getName();
                }
            }

            @Override
            public Country fromString(String s) {
                return comboBoxCountry.getItems().stream().filter(ap -> ap.getName().equals(s)).findFirst().orElse(null);
            }
        });
        tableView.setEditable(true);
        addCustomerColumns();
//        lambda expression
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, old, wen) -> {
                unBind(old);
                clearfields();
                reBind(wen);
        });

        addCustomerRows();
    }

    private void reBind(Customer currentCustomer) {
        if (currentCustomer != null) {
            textID.textProperty().bindBidirectional(currentCustomer.getIdProperty(), new NumberStringConverter());
            textName.textProperty().bindBidirectional(currentCustomer.getNameProperty());
            textAddress.textProperty().bindBidirectional(currentCustomer.getAddressProperty());
            textPhone.textProperty().bindBidirectional(currentCustomer.getPhoneProperty());
            textPostal.textProperty().bindBidirectional(currentCustomer.getZipProperty());
            comboBoxDivision.valueProperty().bindBidirectional(currentCustomer.getDivisionProperty());
        }
    }
    private void unBind(Customer currentCustomer) {
        if (currentCustomer != null) {
            textID.textProperty().unbindBidirectional(currentCustomer.getIdProperty());
            textName.textProperty().unbindBidirectional(currentCustomer.getNameProperty());
            textAddress.textProperty().unbindBidirectional(currentCustomer.getAddressProperty());
            textPhone.textProperty().unbindBidirectional(currentCustomer.getPhoneProperty());
            textPostal.textProperty().unbindBidirectional(currentCustomer.getZipProperty());
            comboBoxDivision.valueProperty().unbindBidirectional(currentCustomer.getDivisionProperty());
        }
    }

    private void clearfields() {
        textID.clear();
        textName.clear();
        textAddress.clear();
        textPhone.clear();
        textPostal.clear();
        comboBoxDivision.setValue(null);
        comboBoxCountry.setValue(null);
    }

    private void addCustomerRows() {
        tableView.setItems(db.customers);
        tableView.getSelectionModel().select(tableView.getItems().stream().findFirst().orElse(null));
//        comboBoxCountry.setValue(tableView.getSelectionModel().selectedItemProperty().get().getDivisionProperty().get().getCountry());
        filteredDivisionList.setPredicate(p -> p.getCountryId() == tableView.getSelectionModel().selectedItemProperty().get().getDivision().getCountryId());
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
        db.em.getTransaction().commit();
        db.em.getTransaction();
    }

    private void dbRevert() {
        db.em.getTransaction().rollback();
        db.em.getTransaction();
    }

    private void recordAdd() {
        Customer nc = new Customer();
        nc.setDivisionId(1);
        nc.setCreatedBy(userName);
        nc.setCreated(LocalDateTime.now());
        db.em.persist(nc);
        db.customers.add(nc);
        reBind(nc);
    }

    private void recordRemove() {
        db.customers.remove(tableView.getSelectionModel().getSelectedItem());;
    }

    private void getComboDivisionId(ActionEvent e) {
        Division i = ((Division)((ComboBox)e.getSource()).getSelectionModel().getSelectedItem());
        setDivisionId(i.getId());
    }

    private void getComboCountryId(ActionEvent e) {
        final Country i = ((Country)((ComboBox)e.getSource()).getSelectionModel().getSelectedItem());
        Platform.runLater(() -> {
            filteredDivisionList.setPredicate(item -> item.getCountryId() == i.getId());
        });
    }
}
