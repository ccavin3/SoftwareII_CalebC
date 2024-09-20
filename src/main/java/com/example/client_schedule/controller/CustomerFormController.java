package com.example.client_schedule.controller;

import com.example.client_schedule.adapters.AppointmentFXAdapter;
import com.example.client_schedule.adapters.CustomerFXAdapter;
import com.example.client_schedule.entities.Country;
import com.example.client_schedule.entities.Customer;
import com.example.client_schedule.entities.Division;
import com.example.client_schedule.helper.DBContext;
import com.example.client_schedule.helper.JPAListener;
import jakarta.persistence.Query;
import jakarta.persistence.metamodel.EntityType;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.stream.Collectors;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * The Customer form controller. This class controls all the functionality for Customer management.
 */
public class CustomerFormController implements Initializable {
    public CustomerFormController(DBContext db) {
        this.db = db;
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
    private TableView<CustomerFXAdapter> tableView;

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

    private ChangeListener<String> idListener;
    private ChangeListener<String> nameListener;
    private ChangeListener<String> addressListener;
    private ChangeListener<String> phoneListener;
    private ChangeListener<String> zipListener;
    private ChangeListener<Division> divisionChangeListener;
    private AppointmentFormController appointmentFormController;


    private FilteredList<Division> filteredDivisionList;
    private ObservableList<CustomerFXAdapter> FXCustomers;

    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
        appointmentFormController = tabsController.getInstance().getAppointmentFormController();
        FXCustomers = FXCollections.observableList(this.db.customers.stream().map(item -> new CustomerFXAdapter(item, this.db)).collect(Collectors.toList()));

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
        tableView.setEditable(false);
        addCustomerColumns();
        addCustomerRows();
    }

    private void reBind(CustomerFXAdapter currentCustomer) {
        if (currentCustomer != null) {
            textID.textProperty().bindBidirectional(currentCustomer.idProperty(), new NumberStringConverter());
            textName.textProperty().bindBidirectional(currentCustomer.nameProperty());
            textAddress.textProperty().bindBidirectional(currentCustomer.addressProperty());
            textPhone.textProperty().bindBidirectional(currentCustomer.phoneProperty());
            textPostal.textProperty().bindBidirectional(currentCustomer.zipProperty());
            comboBoxDivision.valueProperty().bindBidirectional(currentCustomer.divisionProperty());
            comboBoxCountry.valueProperty().bindBidirectional(currentCustomer.countryProperty());
        }
    }
    private void unBind(CustomerFXAdapter currentCustomer) {
        if (currentCustomer != null) {
            textID.textProperty().unbindBidirectional(currentCustomer.idProperty());
            textName.textProperty().unbindBidirectional(currentCustomer.nameProperty());
            textAddress.textProperty().unbindBidirectional(currentCustomer.addressProperty());
            textPhone.textProperty().unbindBidirectional(currentCustomer.phoneProperty());
            textPostal.textProperty().unbindBidirectional(currentCustomer.zipProperty());
            comboBoxDivision.valueProperty().unbindBidirectional(currentCustomer.divisionProperty());
        }
    }

    private void clearfields() {
        textID.clear();
        textName.clear();
        textAddress.clear();
        textPhone.clear();
        textPostal.clear();
        comboBoxDivision.getSelectionModel().selectFirst();
        comboBoxCountry.getSelectionModel().selectFirst();
    }

    private ChangeListener<CustomerFXAdapter> rowChangeListener = new ChangeListener<CustomerFXAdapter>() {
        @Override
        public void changed(ObservableValue<? extends CustomerFXAdapter> observableValue, CustomerFXAdapter old, CustomerFXAdapter wen) {
            unBind(old);
            reBind(wen);
        }
    };

    private void addCustomerRows() {
//        tableView.getItems().clear();
        tableView.getSelectionModel().selectedItemProperty().removeListener(rowChangeListener);
        tableView.setItems(FXCustomers);

        tableView.getSelectionModel().selectedItemProperty().addListener(rowChangeListener);
        tableView.getSelectionModel().selectFirst();
        filteredDivisionList.setPredicate(p -> p.getCountryId() == tableView.getSelectionModel().selectedItemProperty().get().divisionProperty().get().getCountryId());
    }

    private void addCustomerColumns() {
        TableColumn<CustomerFXAdapter, Integer> idCol = new TableColumn<>(_bundle.getString("label.customer.id.text"));
        TableColumn<CustomerFXAdapter, String> nameCol = new TableColumn<>(_bundle.getString("label.customer.name.text"));
        TableColumn<CustomerFXAdapter, String> addressCol = new TableColumn<>(_bundle.getString("label.customer.address.text"));
        TableColumn<CustomerFXAdapter, String> postalCol = new TableColumn<>(_bundle.getString("label.customer.postal.text"));
        TableColumn<CustomerFXAdapter, String> phoneCol = new TableColumn<>(_bundle.getString("label.customer.phone.text"));
        TableColumn<CustomerFXAdapter, Integer> divisionIdCol = new TableColumn<>(_bundle.getString("label.customer.division.text"));
        TableColumn<CustomerFXAdapter, Division> divisionCol = new TableColumn<>(_bundle.getString("label.customer.division.text"));

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
                if (division != null) {
                    return division.getName();
                } else {
                    return "";
                }
            }

            @Override
            public Division fromString(String s) {
                return null;
            }
        }, db.divisions));
        idCol.setVisible(true);
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
        db.em.getTransaction().begin();
    }

    private void dbRevert() {
        FXCustomers.forEach(item -> unBind(item));
        db.em.getTransaction().rollback();
        db.customerDB.fetchFromDB();
        FXCustomers.clear();
        FXCustomers.addAll(db.customers.stream().map(item -> new CustomerFXAdapter(item, this.db)).collect(Collectors.toList()));
        addCustomerRows();
        db.em.getTransaction();
        appointmentFormController.dbRevert();
    }

    private void recordAdd() {
        Customer nc = new Customer();
        nc.setDivisionId(db.divisions.stream().findFirst().get().getId());
        nc.setDivision(db.divisions.stream().findFirst().orElse(null));
        nc.setCreatedBy(userName);
        nc.setCreated(LocalDateTime.now());
        db.em.persist(nc);
        db.customerDB.add(nc);
        CustomerFXAdapter ncfx = new CustomerFXAdapter(nc, this.db);
        FXCustomers.add(ncfx);
        tableView.getSelectionModel().select(ncfx);
    }

    private void recordRemove() {
        CustomerFXAdapter delCustomer = tableView.getSelectionModel().getSelectedItem();
        delCustomer.getAppointments().forEach(a -> {
            AppointmentFXAdapter fxa = appointmentFormController.FXAppointments.stream().filter(ap -> ap.getId() == a.getId()).findFirst().orElse(null);
            appointmentFormController.recordRemove(fxa);
        });
        db.customerDB.delete(delCustomer.customer);
        db.em.remove(delCustomer.customer);
        FXCustomers.remove(delCustomer);
        //alert to show customer was removed
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(_bundle.getString("alert.customerremoved.title"));
            alert.setHeaderText(null);
            alert.setContentText(String.format(_bundle.getString("alert.customerremoved.content"), delCustomer.getName()));
            alert.showAndWait();
        });
    }

    private void getComboDivisionId(ActionEvent e) {
        Division i = ((Division)((ComboBox)e.getSource()).getSelectionModel().getSelectedItem());
        tableView.getSelectionModel().getSelectedItem().divisionIdProperty().set(i.getId());
        tableView.getSelectionModel().getSelectedItem().divisionProperty().set(i);
    }

    private void getComboCountryId(ActionEvent e) {
        final Country i = ((Country)((ComboBox)e.getSource()).getSelectionModel().getSelectedItem());
        Platform.runLater(() -> {
            filteredDivisionList.setPredicate(item -> item.getCountryId() == i.getId());
        });
    }
}
