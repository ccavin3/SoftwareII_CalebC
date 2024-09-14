package com.example.client_schedule.controller;

import com.example.client_schedule.adapters.AppointmentFXAdapter;
import com.example.client_schedule.adapters.CountryFXAdapter;
import com.example.client_schedule.adapters.CustomerFXAdapter;
import com.example.client_schedule.adapters.DivisionFXAdapter;
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
import javafx.collections.transformation.SortedList;
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
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * The type Customer form controller. This class controls all the functionality for Customer management.
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


    private FilteredList<Division> filteredDivisionList;
    private ObservableList<CustomerFXAdapter> FXCustomers;
    private ObservableList<DivisionFXAdapter> FXDivisions;
    private ObservableList<CountryFXAdapter> FXCountries;

    private final StringConverter<Division> divisionStringConverter = new StringConverter<Division>() {
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
    };

    private final StringConverter<Country> countryStringConverter = new StringConverter<Country>() {
        @Override
        public String toString(Country country) {
            return country != null ? country.getName() : ""; // Replace 'getName' with actual method which returns display name
        }

        @Override
        public Country fromString(String string) {
            return db.countries.stream().filter(c -> c.getName().equals(string)).findAny().orElse(null); // Replace 'getName' with actual method which returns display name
        }
    };

    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
        FXCustomers = FXCollections.observableList(this.db.customers.stream().map(item -> new CustomerFXAdapter(item, db)).collect(Collectors.toList()));
        FXDivisions = FXCollections.observableList(this.db.divisions.stream().map(item -> new DivisionFXAdapter(item, db)).collect(Collectors.toList()));
        FXCountries = FXCollections.observableList(this.db.countries.stream().map(item -> new CountryFXAdapter(item, db)).collect(Collectors.toList()));
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
        comboBoxDivision.setConverter(divisionStringConverter);
        comboBoxCountry.setItems(db.countries);
        comboBoxCountry.setConverter(countryStringConverter);

        tableView.setEditable(true);
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

    private void addCustomerRows() {
        tableView.getItems().clear();
        tableView.setItems(FXCustomers);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, old, wen) -> {
                    unBind(old);
                    reBind(wen);
                });

        tableView.getSelectionModel().selectFirst();

        filteredDivisionList.setPredicate(p -> p.getCountryId() == tableView.getSelectionModel()
                        .selectedItemProperty()
                        .get()
                        .divisionProperty()
                        .get()
                        .getCountryId()
        );
    }

    private void addCustomerColumns() {
        TableColumn<CustomerFXAdapter, Integer> idCol = new TableColumn<>("id");
        TableColumn<CustomerFXAdapter, String> nameCol = new TableColumn<>(_bundle.getString("label.customer.name.text"));
        TableColumn<CustomerFXAdapter, String> addressCol = new TableColumn<>(_bundle.getString("label.customer.address.text"));
        TableColumn<CustomerFXAdapter, String> postalCol = new TableColumn<>(_bundle.getString("label.customer.postal.text"));
        TableColumn<CustomerFXAdapter, String> phoneCol = new TableColumn<>(_bundle.getString("label.customer.phone.text"));
        TableColumn divisionHeaderCol = new TableColumn(_bundle.getString("label.customer.division.text"));
        TableColumn<CustomerFXAdapter, Integer> divisionIdCol = new TableColumn<>(_bundle.getString("label.customer.division.text"));
        TableColumn<CustomerFXAdapter, Country> countryCol = new TableColumn(_bundle.getString("label.country.text"));
        TableColumn<CustomerFXAdapter, Division> divisionCol = new TableColumn<>(_bundle.getString("label.customer.division.text"));
        divisionHeaderCol.getColumns().addAll(countryCol, divisionCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        idCol.setCellFactory(TextFieldTableCell.forTableColumn(stringConverter));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        postalCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        divisionIdCol.setCellFactory(ComboBoxTableCell.forTableColumn(db.divisions));

        countryCol.setCellFactory(ComboBoxTableCell.forTableColumn(countryStringConverter, db.countries));

        divisionCol.setCellFactory(ComboBoxTableCell.forTableColumn(divisionStringConverter, new FilteredList<Division>(db.divisions, p -> true)));

        divisionyCol.setCellFactory(col -> {
            ComboBoxTableCell<CustomerFXAdapter, Country> cell = new ComboBoxTableCell<CustomerFXAdapter, Division>() {
                @Override
                public void updateItem(Country country, boolean empty) {
                    super.updateItem(country, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        CustomerFXAdapter item = getTableView().getItems().get(getIndex());
                        ObservableList<Country> applicableCountries = item.getApplicableCountries(); // use your own method
                        FilteredList<Country> filteredCountries = new FilteredList<>(applicableCountries);

                        // get the object with which comparison will be made
                        Division division = item.getDivision(); // use actual method to get the division from the item

                        // set a predicate
                        filteredCountries.setPredicate(c ->
                                c.getName().equals(division.getName()) // Replace 'getName' with actual method of Division to compare with
                        );

                        setItems(filteredCountries);
                    }
                }
            };

            cell.setConverter(countryStringConverter);

            return cell;
        });



        idCol.setVisible(false);
        tableView.getColumns().addAll(idCol, nameCol, addressCol, postalCol, phoneCol, countryCol, divisionCol);
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
        FXCustomers.forEach(item -> unBind(item));
        db.em.getTransaction().rollback();
        db.customerDB.fetchFromDB();
        FXCustomers.clear();
        FXCustomers.addAll(db.customers.stream().map(item -> new CustomerFXAdapter(item,db)).collect(Collectors.toList()));
        db.em.getTransaction();
    }

    private void recordAdd() {
        Customer nc = new Customer();
        nc.setDivisionId(db.divisions.stream().findFirst().get().getId());
        nc.setDivision(db.divisions.stream().findFirst().orElse(null));
        nc.setCreatedBy(userName);
        nc.setCreated(LocalDateTime.now());
        db.em.persist(nc);
        db.customers.add(nc);
        CustomerFXAdapter ncfx = new CustomerFXAdapter(nc,db);
        FXCustomers.add(ncfx);
        tableView.getSelectionModel().select(ncfx);
    }

    private void recordRemove() {
        CustomerFXAdapter delCustomer = tableView.getSelectionModel().getSelectedItem();
        db.customers.remove(delCustomer.customer);
        db.em.remove(delCustomer.customer);
        FXCustomers.remove(delCustomer);
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
