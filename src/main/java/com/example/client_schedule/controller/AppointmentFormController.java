package com.example.client_schedule.controller;

import com.example.client_schedule.MainApplication;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.*;

import java.io.IOException;
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
            if (d == null) return "";
            return dformatter.format(d);
        }

        @Override
        public LocalDate fromString(String s) {
            if (s == null || s.trim().isEmpty()) return null;
            return LocalDate.parse(s, dformatter);
        }
    };

    private StringConverter<LocalTime> timeConverter = new StringConverter<LocalTime>() {
        @Override
        public String toString(LocalTime t) {
            if (t == null) return "";
            return tformatter.format(t);
        }

        @Override
        public LocalTime fromString(String s) {
            if (s == null || s.trim().isEmpty()) return null;
            return LocalTime.parse(s, tformatter);
        }
    };

    private StringConverter<LocalDateTime> dateTimeConverter = new StringConverter<LocalDateTime>() {
        @Override
        public String toString(LocalDateTime t) {
            if (t == null) return "";
            return dtformatter.format(t);
        }

        @Override
        public LocalDateTime fromString(String s) {
            if (s == null || s.trim().isEmpty()) return null;
            return LocalDateTime.parse(s, dtformatter);
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
    private Button reportButton;

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
    private EventHandler<ActionEvent> onReportAction;

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
        onReportAction = e -> lauchReport();

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
        reportButton.setOnAction(onReportAction);

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
            textTitle.textProperty().removeListener(titleListener);
            textAppointmentID.textProperty().removeListener(idListener);
            textDescription.textProperty().removeListener(descriptionListener);
            textType.textProperty().removeListener(typeListener);
            textLocation.textProperty().removeListener(locationListener);
            comboBoxContact.valueProperty().removeListener(contactChangeListener);
            comboBoxUser.valueProperty().removeListener(userChangeListener);
            comboBoxCustomer.valueProperty().removeListener(customerChangeListener);
            textStart.textProperty().removeListener(startListener);
            textEnd.textProperty().removeListener(endListener);
            textStartDate.textProperty().removeListener(startDateListener);
            textEndDate.textProperty().removeListener(endDateListener);
            textStartTime.textProperty().removeListener(startTimeListener);
            textEndTime.textProperty().removeListener(endTimeListener);

//            textTitle.textProperty().unbindBidirectional(currentAppointment.getTitleProperty());
//            textAppointmentID.textProperty().unbindBidirectional(currentAppointment.getIdProperty());
//            textDescription.textProperty().unbindBidirectional(currentAppointment.getDescriptionProperty());
//            textType.textProperty().unbindBidirectional(currentAppointment.getTypeProperty());
//            textLocation.textProperty().unbindBidirectional(currentAppointment.getLocationProperty());
//            comboBoxCustomer.valueProperty().unbindBidirectional(currentAppointment.getCustomerProperty());
//            comboBoxContact.valueProperty().unbindBidirectional(currentAppointment.getContactProperty());
//            comboBoxUser.valueProperty().unbindBidirectional(currentAppointment.getUserProperty());
//            Bindings.unbindBidirectional(textStart.textProperty(), currentAppointment.getStartProperty());
//            Bindings.unbindBidirectional(textEnd.textProperty(), currentAppointment.getEndProperty());
//            Bindings.unbindBidirectional(textStartDate.textProperty(), currentAppointment.getStartDateProperty());
//            Bindings.unbindBidirectional(textEndDate.textProperty(), currentAppointment.getEndDateProperty());
//            Bindings.unbindBidirectional(textStartTime.textProperty(), currentAppointment.getStartTimeProperty());
//            Bindings.unbindBidirectional(textEndTime.textProperty(), currentAppointment.getEndTimeProperty());
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
    private ChangeListener<String> titleListener;
    private ChangeListener<String> idListener;
    private ChangeListener<String> descriptionListener;
    private ChangeListener<String> typeListener;
    private ChangeListener<String> locationListener;
    private ChangeListener<Customer> customerChangeListener;
    private ChangeListener<User> userChangeListener;
    private ChangeListener<Contact> contactChangeListener;
    private ChangeListener<String> startListener;
    private ChangeListener<String> endListener;
    private ChangeListener<String> startDateListener;
    private ChangeListener<String> endDateListener;
    private ChangeListener<String> startTimeListener;
    private ChangeListener<String> endTimeListener;

    private void reBind(Appointment currentAppointment) {
        if (currentAppointment != null) {
            textTitle.textProperty().addListener(titleListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setTitle(t1);
                    tableView.refresh();
                }
            });
            textAppointmentID.textProperty().addListener(idListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setId(Integer.parseInt(t1));
                    tableView.refresh();
                }
            });
            textDescription.textProperty().addListener(descriptionListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setDescription(t1);
                    tableView.refresh();
                }
            });
            textType.textProperty().addListener(typeListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setType(t1);
                    tableView.refresh();
                }
            });
            textLocation.textProperty().addListener(locationListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setLocation(t1);
                    tableView.refresh();
                }
            });
            comboBoxCustomer.valueProperty().addListener(customerChangeListener = new ChangeListener<Customer>() {
                @Override
                public void changed(ObservableValue<? extends Customer> observableValue, Customer customer, Customer t1) {
                    currentAppointment.setCustomer(t1);
                    tableView.refresh();
                }
            });
            comboBoxUser.valueProperty().addListener(userChangeListener = new ChangeListener<User>() {
                @Override
                public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {
                    currentAppointment.setUser(t1);
                    tableView.refresh();
                }
            });
            comboBoxContact.valueProperty().addListener(contactChangeListener = new ChangeListener<Contact>() {
                @Override
                public void changed(ObservableValue<? extends Contact> observableValue, Contact contact, Contact t1) {
                    currentAppointment.setContact(t1);
                    tableView.refresh();
                }
            });
            textStart.textProperty().addListener(startListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setStart(LocalDateTime.parse(t1, dtformatter));
                    tableView.refresh();
                }
            });
            textEnd.textProperty().addListener(endListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setEnd(LocalDateTime.parse(t1, dtformatter));
                    tableView.refresh();
                }
            });
            textStartDate.textProperty().addListener(startDateListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setStartDate(LocalDate.parse(t1, dformatter));
                    tableView.refresh();
                }
            });
            textEndDate.textProperty().addListener(endDateListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setEndDate(LocalDate.parse(t1, dformatter));
                    tableView.refresh();
                }
            });
            textStartTime.textProperty().addListener(startTimeListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setStartTime(LocalTime.parse(t1, tformatter));
                    tableView.refresh();
                }
            });
            textEndTime.textProperty().addListener(endTimeListener = new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    currentAppointment.setEndTime(LocalTime.parse(t1, tformatter));
                    tableView.refresh();
                }
            });
//            textTitle.textProperty().bindBidirectional(currentAppointment.getTitleProperty());
//            textAppointmentID.textProperty().bindBidirectional(currentAppointment.getIdProperty(), new NumberStringConverter());
//            textDescription.textProperty().bindBidirectional(currentAppointment.getDescriptionProperty());
//            textType.textProperty().bindBidirectional(currentAppointment.getTypeProperty());

//            textLocation.textProperty().bindBidirectional(currentAppointment.getLocationProperty());
//            comboBoxCustomer.valueProperty().bindBidirectional(currentAppointment.getCustomerProperty());
//            comboBoxContact.valueProperty().bindBidirectional(currentAppointment.getContactProperty());
//            comboBoxUser.valueProperty().bindBidirectional(currentAppointment.getUserProperty());
//            Bindings.bindBidirectional(textStart.textProperty(), currentAppointment.getStartProperty(), dateTimeConverter);
//            Bindings.bindBidirectional(textEnd.textProperty(), currentAppointment.getEndProperty(), dateTimeConverter);
//            Bindings.bindBidirectional(textStartDate.textProperty(), currentAppointment.getStartDateProperty(), dateConverter);
//            Bindings.bindBidirectional(textEndDate.textProperty(), currentAppointment.getEndDateProperty(), dateConverter);
//            Bindings.bindBidirectional(textStartTime.textProperty(), currentAppointment.getStartTimeProperty(), timeConverter);
//            Bindings.bindBidirectional(textEndTime.textProperty(), currentAppointment.getEndTimeProperty(), timeConverter);
        }
    }

    private void addAppointmentRows() {
//        tableView.setItems(appointmentFilteredList);
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
        db.em.getTransaction().commit();
        db.em.getTransaction().begin();
    }

    private void dbRevert() {
        db.em.getTransaction().rollback();
        db.em.getTransaction().begin();
    }

    private void recordAdd() {
        Appointment na = new Appointment();
        na.setContactId(comboBoxContact.getItems().stream().findFirst().get().getId());
        na.setCustomerId(comboBoxCustomer.getItems().stream().findFirst().get().getId());
        na.setUserId(comboBoxUser.getItems().stream().findFirst().get().getId());
        na.setStart(LocalDateTime.now());
        na.setEnd(LocalDateTime.now());
        na.setStartDate(LocalDate.now());
        na.setEndDate(LocalDate.now());
        na.setStartTime(LocalTime.now());
        na.setEndTime(LocalTime.now().plusMinutes(15));
        na.createdBy = this.userName;
        na.created = LocalDateTime.now();
        db.em.persist(na);
        db.appointments.add(na);
        reBind(na);
    }

    private void recordRemove() {
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
    }

    private void lauchReport() {
        try {
            launchReportForm();
        } catch (Exception ex) {

        }
    }

    private void launchReportForm() throws IOException {
        Stage thiswindow = (Stage)reportButton.getScene().getWindow();
//        CustomerFormController controller = new CustomerFormController(db, userName);
//        AppointmentFormController controller = new AppointmentFormController(db, userName);
        ReportsController controller = new ReportsController(db, userName);
//
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("reportsForm.fxml"), _bundle);

//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("appointmentForm.fxml"), _bundle);
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("customerForm.fxml"), _bundle);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("Schedule Data");
        stage.setScene(scene);
        stage.show();
    }
}
