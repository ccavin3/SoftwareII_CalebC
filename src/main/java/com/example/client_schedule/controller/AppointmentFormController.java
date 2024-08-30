package com.example.client_schedule.controller;

import com.example.client_schedule.entities.*;
import com.example.client_schedule.helper.DBContext;
import jakarta.persistence.Table;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * The type Appointment form controller. This class controls all the functionality for Appointment management.
 */
public class AppointmentFormController extends Appointment implements Initializable {
    /**
     * Instantiates a new Appointment form controller.
     *
     * @param db       the db
     * @param userName the user name
     */
    public AppointmentFormController(DBContext db, String userName) {
        this.db = db;
        this.userName = userName;
    }

    private ResourceBundle _bundle;

    private DBContext db;
    private String userName;

    private StringConverter<LocalDate> dateConverter = new StringConverter<LocalDate>() {
        @Override
        public String toString(LocalDate d) {
            if (d == null) return null;
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return d.format(parser);
        }

        @Override
        public LocalDate fromString(String s) {
            if (s == null) return null;
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(s, parser);
        }
    };

    private StringConverter<LocalTime> timeConverter = new StringConverter<LocalTime>() {
        @Override
        public String toString(LocalTime t) {
            if (t == null) return null;
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("h[:mm][:ss] a");
            return t.format(parser);
        }

        @Override
        public LocalTime fromString(String s) {
            if (s == null) return null;
            DateTimeFormatter parser = DateTimeFormatter.ofPattern("h[:mm][:ss] a");
            return LocalTime.parse(s, parser);
        }
    };

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
    private TextField textAppointmentID;

    @FXML
    private TextField textUserID;

    @FXML
    private TextField textCustomerID;

    @FXML
    private TextField textTitle;

    @FXML
    private TextField textDescription;

    @FXML
    private TextField textLocation;

    @FXML
    private ComboBox contactDropdown;

    @FXML
    private TextField textType;

    @FXML
    private TextField textDate;

    @FXML
    private TextField textStart;

    @FXML
    private TextField textEnd;


    @FXML
    private TableView<Appointment> tableView;

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
    private Appointment currentAppointment;


    private DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("hh:mm[:ss] a");
    private DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm[:ss] a");

    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
        onCommitAction = e -> dbCommit();
        onRevertAction = e -> dbRevert();
        onInsertAction = e -> recordAdd();
        onDeleteAction = e -> recordRemove();

        deleteButton.setOnAction(onDeleteAction);
        insertButton.setOnAction(onInsertAction);
        commitButton.setOnAction(onCommitAction);
        revertButton.setOnAction(onRevertAction);

        tableView.setEditable(true);
        addAppointmentColumns();
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, old, wen) -> {
            if (wen != null) {
                textTitle.setText(wen.getTitle());
                textType.setText(wen.getType());
                textStart.setText(wen.getStart().format(dtformatter));
                textEnd.setText(wen.getEnd().format(dtformatter));

            }
        });
//        tableView.setRowFactory(tableView -> {
//            TableRow<Appointment> row = new TableRow<>();
//            ObjectProperty<Appointment> opMsg = row.itemProperty();
//            Appointment tmpMsg = opMsg.get();
//            return row;
//        });
        addAppointmentRows();

        textTitle.textProperty().bindBidirectional(currentAppointment.title);
        textAppointmentID.textProperty().bindBidirectional(currentAppointment.id, new NumberStringConverter());
        textDescription.textProperty().bindBidirectional(currentAppointment.description);
        textType.textProperty().bindBidirectional(currentAppointment.type);
        textLocation.textProperty().bindBidirectional(currentAppointment.location);
        contactDropdown.valueProperty().bindBidirectional(currentAppointment.contact);
        textStart.textProperty().bindBidirectional(currentAppointment.start);
        textEnd.textProperty().bindBidirectional(currentAppointment.end);


    }

    private void addAppointmentRows() {
        tableView.setItems(db.appointments);
    }

    private void addAppointmentColumns() {

        TableColumn<Appointment, Integer> idCol = new TableColumn<>("id");
        TableColumn<Appointment, String> titleCol = new TableColumn<>(_bundle.getString("label.appointment.title.text"));
        TableColumn<Appointment, String> descriptionCol = new TableColumn<>(_bundle.getString("label.appointment.description.text"));
        TableColumn<Appointment, String> locationCol = new TableColumn<>(_bundle.getString("label.appointment.location.text"));
        TableColumn<Appointment, String> typeCol = new TableColumn<>(_bundle.getString("label.appointment.type.text"));
        TableColumn<Appointment, LocalDateTime> startCol = new TableColumn<>(_bundle.getString("label.appointment.start.text"));
        TableColumn<Appointment, LocalDateTime> endCol = new TableColumn<>(_bundle.getString("label.appointment.end.text"));
        TableColumn<Appointment, Customer> customerCol = new TableColumn<>(_bundle.getString("label.appointment.customer.text"));
        TableColumn<Appointment, User> userCol = new TableColumn<>(_bundle.getString("label.appointment.user.text"));
        TableColumn<Appointment, Contact> contactCol = new TableColumn<>(_bundle.getString("label.appointment.contact.text"));
        TableColumn appointmentCol = new TableColumn(_bundle.getString("label.appointment.appointment.text"));
        TableColumn<Appointment, LocalDate> startDateCol = new TableColumn(_bundle.getString("label.appointment.date.text"));
        TableColumn<Appointment, LocalTime> startTimeCol = new TableColumn(_bundle.getString("label.appointment.time.text"));
        TableColumn<Appointment, LocalDate> endDateCol = new TableColumn(_bundle.getString("label.appointment.date.text"));
        TableColumn<Appointment, LocalTime> endTimeCol = new TableColumn(_bundle.getString("label.appointment.time.text"));
        startCol.getColumns().addAll(startDateCol, startTimeCol);
        endCol.getColumns().addAll(endDateCol, endTimeCol);
        appointmentCol.getColumns().addAll(startCol, endCol);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));

        idCol.setCellFactory(TextFieldTableCell.forTableColumn(stringConverter));
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        locationCol.setCellFactory(TextFieldTableCell.forTableColumn());
        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //startCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //endCol.setCellFactory(TextFieldTableCell.forTableColumn());
        startTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(timeConverter));
        startDateCol.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        endTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(timeConverter));
        endDateCol.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));

        customerCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer.getName();
            }

            @Override
            public Customer fromString(String s) {
                return null;
            }
        }, db.customers));

        contactCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                return contact.getName();
            }

            @Override
            public Contact fromString(String s) {
                return null;
            }
        }, db.contacts));

        userCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getUserName();
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        }, db.users));

        idCol.setVisible(false);
        tableView.getColumns().addAll(idCol, titleCol, descriptionCol, locationCol, typeCol, appointmentCol, customerCol, userCol, contactCol);
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
        db.appointments.add(new Appointment());
    }

    private void recordRemove() {
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
    }
}
