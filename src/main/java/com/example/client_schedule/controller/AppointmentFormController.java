package com.example.client_schedule.controller;

import com.example.client_schedule.entities.*;
import com.example.client_schedule.helper.DBContext;
import jakarta.persistence.Table;
import javafx.beans.InvalidationListener;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.*;

import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

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
    private TextField textContactID;

    @FXML
    private TextField textTitle;

    @FXML
    private TextField textDescription;

    @FXML
    private TextField textLocation;

    @FXML
    private TextField textType;

    @FXML
    private TextField textDate;

    @FXML
    private TextField textStart;

    @FXML
    private TextField textEnd;

    @FXML
    private TextField textStartDate;

    @FXML
    private TextField textStartTime;

    @FXML
    private TextField textEndDate;

    @FXML
    private TextField textEndTime;

    @FXML
    private TableView<Appointment> tableView;

    @FXML
    private StackPane tableArea;

    @FXML
    private ComboBox<Contact> comboBoxContact;

    @FXML
    private ComboBox<User> comboBoxUser;

    @FXML
    private ComboBox<Customer> comboBoxCustomer;

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

    private DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("hh:mm[:ss] a");
    private DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm[:ss] a");
    private StringConverter<String> dateStringConverter;
    private StringConverter<String> timeStringConverter;

    private FilteredList<Appointment> appointmentFilteredList;

    private UnaryOperator<TextFormatter.Change> dateValidationFormatter = change -> {
        if (change.getControlNewText().matches("(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((19|2[0-9])[0-9]{2})")) {
            return change;  // if change is a date
        } else if (change.getText().matches("\\d+")){
            return change; //if change is a number
        } else {
            change.setText(""); //else make no change
            change.setRange(    //don't remove any selected text either.
                    change.getRangeStart(),
                    change.getRangeStart()
            );
            return change;
        }
    };

    private UnaryOperator<TextFormatter.Change> timeValidationFormatter = change -> {
        if (change.getControlNewText().matches("/^ *(1[0-2]|[1-9]):[0-5][0-9] *(a|p|A|P)(m|M) *$/")) {
            return change; //if change is a LocalTime
        } else if (change.getText().matches("\\d+")) {
            return change; //if change is a number
        } else {
            change.setText(""); //else make no change
            change.setRange(    //don't remove any selected text either.
                    change.getRangeStart(),
                    change.getRangeStart()
            );
            return change;
        }
    };

    private TextFormatter<String> sldFormatter;

    private TextFormatter<String> sltFormatter;

    private TextFormatter<String> eldFormatter;

    private TextFormatter<String> eltFormatter;

    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
        appointmentFilteredList = new FilteredList<>(db.appointments, p -> true);
        dateStringConverter = new StringConverter<String>() {
            @Override
            public String toString(String localDate) {
                return localDate;
            }

            @Override
            public String fromString(String s) {
                return LocalDate.parse(s).format(dformatter);
            }
        };
        timeStringConverter = new StringConverter<String>() {
            @Override
            public String toString(String localTime) {
                return localTime;
            }

            @Override
            public String fromString(String s) {
                return LocalTime.parse(s).format(tformatter);
            }
        };
        sldFormatter = new TextFormatter<>(
                dateStringConverter,   //converter
                LocalDate.now().format(dformatter),        //default value
                dateValidationFormatter
        );
        sltFormatter = new TextFormatter<>(
                timeStringConverter,   //converter
                LocalTime.now().format(tformatter),        //default value
                timeValidationFormatter
        );
        eldFormatter = new TextFormatter<>(
                dateStringConverter,   //converter
                LocalDate.now().format(dformatter),        //default value
                dateValidationFormatter
        );
        eltFormatter = new TextFormatter<>(
                timeStringConverter,   //converter
                LocalTime.now().format(tformatter),        //default value
                timeValidationFormatter
        );
        onCommitAction = e -> dbCommit();
        onRevertAction = e -> dbRevert();
        onInsertAction = e -> recordAdd();
        onDeleteAction = e -> recordRemove();

//        textStartDate.setTextFormatter(sldFormatter);
//        textEndDate.setTextFormatter(eldFormatter);
//        textStartTime.setTextFormatter(sltFormatter);
//        textEndTime.setTextFormatter(eltFormatter);

        comboBoxContact.setItems(db.contacts);
        comboBoxContact.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                if (contact != null) {
                    return contact.getName();
                } else {
                    return "";
                }
            }

            @Override
            public Contact fromString(String s) {
                return comboBoxContact.getItems().stream().filter(ap -> ap.getName().equals(s)).findFirst().orElse(null);
            }
        });
        comboBoxUser.setItems(db.users);
        comboBoxUser.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                if (user != null) {
                    return user.getUserName();
                } else {
                    return "";
                }
            }

            @Override
            public User fromString(String s) {
                return comboBoxUser.getItems().stream().filter(ap -> ap.getUserName().equals(s)).findFirst().orElse(null);
            }
        });

        comboBoxCustomer.setItems(db.customers);
        comboBoxCustomer.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                if (customer != null) {
                    return customer.getName();
                } else {
                    return "";
                }
            }

            @Override
            public Customer fromString(String s) {
                return comboBoxCustomer.getItems().stream().filter(ap -> ap.getName().equals(s)).findFirst().orElse(null);
            }
        });

        deleteButton.setOnAction(onDeleteAction);
        insertButton.setOnAction(onInsertAction);
        commitButton.setOnAction(onCommitAction);
        revertButton.setOnAction(onRevertAction);

        tableView.setEditable(true);
        addAppointmentColumns();
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, old, wen) -> {
                unBind(old);
                clearfields();
                reBind(wen);
        });

//        tableView.setRowFactory(tableView -> {
//            TableRow<Appointment> row = new TableRow<>();
//            ObjectProperty<Appointment> opMsg = row.itemProperty();
//            Appointment tmpMsg = opMsg.get();
//            return row;
//        });
        addAppointmentRows();
    }

    private void unBind(Appointment currentAppointment) {
        if (currentAppointment != null) {
            textTitle.textProperty().unbindBidirectional(currentAppointment.getTitleProperty());
            textAppointmentID.textProperty().unbindBidirectional(currentAppointment.getIdProperty());
            textDescription.textProperty().unbindBidirectional(currentAppointment.getDescriptionProperty());
            textType.textProperty().unbindBidirectional(currentAppointment.getTypeProperty());
            textLocation.textProperty().unbindBidirectional(currentAppointment.getLocationProperty());
            comboBoxCustomer.valueProperty().unbindBidirectional(currentAppointment.getCustomerProperty());
            comboBoxContact.valueProperty().unbindBidirectional(currentAppointment.getContactProperty());
            comboBoxUser.valueProperty().unbindBidirectional(currentAppointment.getUserProperty());
            textStart.textProperty().unbindBidirectional(currentAppointment.getStartProperty());
            textEnd.textProperty().unbindBidirectional(currentAppointment.getEndProperty());
            textStartDate.textProperty().unbindBidirectional(getStartDateProperty());
            textEndDate.textProperty().unbindBidirectional(getEndDateProperty());
            textStartTime.textProperty().unbindBidirectional(getStartTimeProperty());
            textEndTime.textProperty().unbindBidirectional(getEndTimeProperty());
        }
    }

    private void clearfields() {
        textTitle.clear();
        textAppointmentID.clear();
        textAppointmentID.clear();
        textType.clear();
        textLocation.clear();
        comboBoxCustomer.setValue(null);
        comboBoxContact.setValue(null);
        comboBoxUser.setValue(null);
        textStart.clear();
        textEnd.clear();
        textStartDate.clear();
        textStartTime.clear();
        textEndDate.clear();
        textEndTime.clear();
    }
    private void reBind(Appointment currentAppointment) {
        if (currentAppointment != null) {
            textTitle.textProperty().bindBidirectional(currentAppointment.getTitleProperty());
            textAppointmentID.textProperty().bindBidirectional(currentAppointment.getIdProperty(), new NumberStringConverter());
            textDescription.textProperty().bindBidirectional(currentAppointment.getDescriptionProperty());
            textType.textProperty().bindBidirectional(currentAppointment.getTypeProperty());
            textLocation.textProperty().bindBidirectional(currentAppointment.getLocationProperty());
            comboBoxCustomer.valueProperty().bindBidirectional(currentAppointment.getCustomerProperty());
            comboBoxContact.valueProperty().bindBidirectional(currentAppointment.getContactProperty());
            comboBoxUser.valueProperty().bindBidirectional(currentAppointment.getUserProperty());
            textStart.textProperty().bindBidirectional(currentAppointment.getStartProperty(), new LocalDateTimeStringConverter(dtformatter,dtformatter));
            textEnd.textProperty().bindBidirectional(currentAppointment.getEndProperty(), new LocalDateTimeStringConverter(dtformatter,dtformatter));
            textStartDate.textProperty().bindBidirectional(currentAppointment.getStartDateProperty(), new LocalDateStringConverter(dformatter,dformatter));
            textEndDate.textProperty().bindBidirectional(currentAppointment.getEndDateProperty(), new LocalDateStringConverter(dformatter,dformatter));
            textStartTime.textProperty().bindBidirectional(currentAppointment.getStartTimeProperty(), new LocalTimeStringConverter(tformatter,tformatter));
            textEndTime.textProperty().bindBidirectional(currentAppointment.getEndTimeProperty(), new LocalTimeStringConverter(tformatter,tformatter));
//        Bindings.bindBidirectional(textStartDate.textProperty(), currentAppointment.get().getStartDateProperty(), new LocalDateStringConverter(dformatter, null));
//        Bindings.bindBidirectional(textEndDate.textProperty(), currentAppointment.get().getEndDateProperty(), new LocalDateStringConverter(dformatter, null));
//        Bindings.bindBidirectional(textStartTime.textProperty(), currentAppointment.get().getStartTimeProperty(), new LocalTimeStringConverter(tformatter, null));
//        Bindings.bindBidirectional(textEndTime.textProperty(), currentAppointment.get().getEndTimeProperty(), new LocalTimeStringConverter(tformatter, null));
        }
    }

    private void addAppointmentRows() {
        tableView.setItems(appointmentFilteredList);
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
                if (customer == null) {
                    return comboBoxCustomer.getItems().stream().findFirst().get().getName();
                }
                return customer.getName();
            }

            @Override
            public Customer fromString(String s) {
                return comboBoxCustomer.getItems().stream().filter(x -> x.getName().equals(s)).findFirst().orElse(null);
            }
        }, db.customers));

        contactCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                if (contact == null) {
                    return comboBoxContact.getItems().stream().findFirst().get().getName();
                }
                return contact.getName();
            }

            @Override
            public Contact fromString(String s) {
                return comboBoxContact.getItems().stream().filter(x -> x.getName().equals(s)).findFirst().orElse(null);
            }
        }, db.contacts));

        userCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                if (user == null) {
                    return comboBoxUser.getItems().stream().findFirst().get().getUserName();
                }
                return user.getUserName();
            }

            @Override
            public User fromString(String s) {
                return comboBoxUser.getItems().stream().filter(x -> x.getUserName().equals(s)).findFirst().orElse(null);
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
