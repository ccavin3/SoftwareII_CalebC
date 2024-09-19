package com.example.client_schedule.controller;

import com.example.client_schedule.MainApplication;
import com.example.client_schedule.adapters.AppointmentFXAdapter;
import com.example.client_schedule.entities.*;
import com.example.client_schedule.helper.DBContext;
import com.example.client_schedule.helper.ZonedDates;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import com.example.client_schedule.helper.AppConfig;

/**
 * The AppointmentFormController class is responsible for managing the appointment form view and its behavior.
 */
public class AppointmentFormController implements Initializable {
    /**
     * This class represents a controller for the AppointmentFormController.
     */
    public AppointmentFormController(DBContext db) {
        this.db = db;
    }

    private ResourceBundle _bundle;

    private DBContext db;
    private String userName;

    /**
     * This variable is a StringConverter object used to convert between Integer and String representations.
     * It is implemented as an anonymous class that overrides the methods of the StringConverter interface.
     */
    private StringConverter<Integer> IntStringConverter = new StringConverter<>() {
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
    private TableView<AppointmentFXAdapter> tableView;

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


    @FXML
    private RadioButton weekRadioButton, monthRadioButton, allRadioButton;

    @FXML
    private ToggleGroup filterGroup;



    private DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("hh:mm[:ss] a");
    private DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm[:ss] a");
    private DateTimeFormatter minformatter = DateTimeFormatter.ofPattern("hh:mm");

    private FilteredList<AppointmentFXAdapter> appointmentFilteredList;

    /**
     * Represents a formatter for validating and formatting date input in a text field.
     * The formatter performs the following tasks:
     * - Validates that the input matches the format "dd-MM-yyyy" (day-month-year) for dates.
     * - Validates that the input consists only of digits for numbers.
     * - Clears the text if it does not match the required format or is neither a date nor a number.
     * - Ensures that no selected text is removed when input is cleared.
     *
     * The formatter is implemented as a UnaryOperator<TextFormatter.Change> which takes a Change object as input and returns a modified Change object as output.
     *
     * Example usage:
     *
     * TextField dateTextField = new TextField();
     * TextFormatter<Change> formatter = new TextFormatter<>(dateValidationFormatter);
     * dateTextField.setTextFormatter(formatter);
     *
     */
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
    /**
     * Represents a UnaryOperator that formats and validates time input.
     *
     * The timeValidationFormatter is used as a TextFormatter.Change transformation function.
     * It checks if the new text matches a valid time format (1-12 hours followed by minutes and am/pm),
     * or if the new text is a number. If the new text does not match any of these conditions,
     * it clears the text and sets the range to not remove any selected text.
     *
     * This formatter is primarily used for time input validation in a UI form.
     *
     * @see TextFormatter
     * @see LocalTimeStringConverter
     */
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

//    private TextFormatter<String> sldFormatter;
//
//    private TextFormatter<String> sltFormatter;
//
//    private TextFormatter<String> eldFormatter;
//
//    private TextFormatter<String> eltFormatter;
    public ObservableList<AppointmentFXAdapter> FXAppointments;
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * Initializes the controller by setting up the UI components and event handlers.
     *
     * @param url       The location used to resolve relative paths for the root object.
     * @param bundle    The resource bundle containing the localized strings.
     *
     * @lambda initialize lines 309-313 the ActionEvent e can be sent to the local method if needed otherwise the method will be called without parameter. e.g.  onFooAction = e -> fooHandler(e);  // parameter required or onFooAction = e -> fooHandler();  // no parameter required
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        this._bundle = bundle;
        FXAppointments = FXCollections.observableList(this.db.appointments.stream().map(item -> new AppointmentFXAdapter(item)).collect(Collectors.toList()));
        appointmentFilteredList = new FilteredList<AppointmentFXAdapter>(FXAppointments, p -> true);

        StringConverter<LocalDate> dateStringConverter = new LocalDateStringConverter(dformatter,dformatter);
        StringConverter<LocalTime> timeStringConverter = new LocalTimeStringConverter(tformatter,tformatter);

        /**
         * redirect onaction handlers to local method
         * @lambda ActionEvent  the ActionEvent e can be sent to the local method if needed  otherwise the method will be called without parameter. e.g.  onFooAction = e -> fooHandler(e);  // parameter required or onFooAction = e -> fooHandler();  // no parameter required
         */

        onCommitAction = e -> dbCommit();
        onRevertAction = e -> dbRevert();
        onInsertAction = e -> recordAdd();
        onDeleteAction = e -> recordRemove();
        onReportAction = e -> launchReport();

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
        comboBoxContact.getSelectionModel().selectFirst();

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
        comboBoxUser.getSelectionModel().selectFirst();

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
                return db.customers.stream().filter(c -> c.getName().equals(s)).findFirst().orElse(null);
            }
        });
        comboBoxCustomer.getSelectionModel().selectFirst();

        deleteButton.setOnAction(onDeleteAction);
        insertButton.setOnAction(onInsertAction);
        commitButton.setOnAction(onCommitAction);
        revertButton.setOnAction(onRevertAction);
        reportButton.setOnAction(onReportAction);

        tableView.setEditable(false);
        addAppointmentColumns();

        addAppointmentRows();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                final LocalDate ld = LocalDate.now();
                final LocalTime lt = LocalTime.now();
                Comparator<AppointmentFXAdapter> comparator = Comparator.comparing(AppointmentFXAdapter::getStart).reversed();
                if (db.appointments.stream().anyMatch(ap -> ap.getStart().toLocalDate().equals(ld) && lt.isBefore(ap.getStart().toLocalTime()) && lt.plusMinutes(15).isAfter(ap.getStart().toLocalTime()))) {
                    int result =
                        db.appointments
                            .stream()
                            .filter(ap -> ap.getStart().toLocalDate().equals(ld) && lt.isBefore(ap.getStart().toLocalTime()) && lt.plusMinutes(15).isAfter(ap.getStart().toLocalTime()))
                            .map(ap -> ((int)Duration.between(ap.getStart().toLocalTime(), lt ).toMinutes()))
                            .min(Integer::compare).get();
                    Platform.runLater(() -> {
                        alert.setAlertType(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Appointment alert");
                        alert.setContentText(String.format("Appointmenet upcoming in %d minutes", result));
                        alert.showAndWait();
                        //update UI thread from here.
                    });
                }
            }
        };
        Timer t = new Timer();
        t.scheduleAtFixedRate(
                task ,
                0,      //delay before first execution
                120000L); //time between executions


        filterGroup = new ToggleGroup();
        weekRadioButton.setToggleGroup(filterGroup);
        monthRadioButton.setToggleGroup(filterGroup);
        allRadioButton.setToggleGroup(filterGroup);

        filterGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            System.out.println("New Toggle Selected: " + (newToggle != null ? ((RadioButton) newToggle).getText() : "null"));

            if (newToggle != null) {
                tableView.refresh(); // Refresh the TableView if needed
                if (newToggle == weekRadioButton) {
                    System.out.println("Week filter applied");
                    applyDateFilter(filterByWeek());
                } else if (newToggle == monthRadioButton) {
                    System.out.println("Month filter applied");
                    applyDateFilter(filterByMonth());
                } else if (newToggle == allRadioButton) {
                    System.out.println("All filter applied");
                    resetFilter();
                }
            }
        });

    }



    /**
     * Unbinds the properties of the given AppointmentFXAdapter object from the corresponding UI elements.
     * The UI will no longer be updated when the properties of the AppointmentFXAdapter change.
     *
     * @param currentAppointment The AppointmentFXAdapter object to unbind.
     */
    private void unBind(AppointmentFXAdapter currentAppointment) {
        if (currentAppointment != null) {
            textTitle.textProperty().unbindBidirectional(currentAppointment.titleProperty());
            textAppointmentID.textProperty().unbindBidirectional(currentAppointment.idProperty());
            textDescription.textProperty().unbindBidirectional(currentAppointment.descriptionProperty());
            textType.textProperty().unbindBidirectional(currentAppointment.typeProperty());
            textLocation.textProperty().unbindBidirectional(currentAppointment.locationProperty());
            comboBoxCustomer.valueProperty().unbindBidirectional(currentAppointment.customerProperty());
            comboBoxContact.valueProperty().unbindBidirectional(currentAppointment.contactProperty());
            comboBoxUser.valueProperty().unbindBidirectional(currentAppointment.userProperty());
            textStart.textProperty().unbindBidirectional(currentAppointment.startProperty());
            textEnd.textProperty().unbindBidirectional(currentAppointment.endProperty());
            textStartDate.textProperty().unbindBidirectional(currentAppointment.startDateProperty());
            textEndDate.textProperty().unbindBidirectional(currentAppointment.endDateProperty());
            textStartTime.textProperty().unbindBidirectional(currentAppointment.startTimeProperty());
            textEndTime.textProperty().unbindBidirectional(currentAppointment.endTimeProperty());
            startEndDateValid.removeListener(apptDateListener);
            startEndTimeValid.removeListener(apptListener);
            withinWorkingHours.removeListener(workingHourListener);
            overlapping.removeListener(overlappingApptsListener);
        }
    }

    /**
     * Clears the fields in the UI form.
     * The following fields are cleared:
     *     - textTitle
     *     - textAppointmentID
     *     - textType
     *     - textLocation
     *     - comboBoxCustomer
     *     - comboBoxContact
     *     - comboBoxUser
     *     - textStart
     *     - textEnd
     *     - textStartDate
     *     - textStartTime
     *     - textEndDate
     *     - textEndTime
     */
    private void clearfields() {
        textTitle.clear();
        textAppointmentID.clear();
        textAppointmentID.clear();
        textType.clear();
        textLocation.clear();
        comboBoxCustomer.getSelectionModel().selectFirst();
        comboBoxContact.getSelectionModel().selectFirst();
        comboBoxUser.getSelectionModel().selectFirst();
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

    private ChangeListener<Boolean> apptDateListener;
    private ChangeListener<Boolean> apptListener;
    private ChangeListener<Boolean> workingHourListener;
    private ChangeListener<Boolean> overlappingApptsListener;




    private BooleanBinding overlapping;
    private BooleanBinding startEndDateValid;
    private BooleanBinding startEndTimeValid;
    private BooleanBinding withinWorkingHours;
    /**
     * Binds the properties of the given AppointmentFXAdapter to the corresponding UI elements.
     * Updates the UI when the properties of the AppointmentFXAdapter change.
     *
     * @param currentAppointment The AppointmentFXAdapter object to bind
     */
    private void reBind(AppointmentFXAdapter currentAppointment) {
        if (currentAppointment != null) {
            textTitle.textProperty().bindBidirectional(currentAppointment.titleProperty());
            textAppointmentID.textProperty().bindBidirectional(currentAppointment.idProperty(), new NumberStringConverter());
            textDescription.textProperty().bindBidirectional(currentAppointment.descriptionProperty());
            textType.textProperty().bindBidirectional(currentAppointment.typeProperty());

            textLocation.textProperty().bindBidirectional(currentAppointment.locationProperty());
            comboBoxCustomer.valueProperty().bindBidirectional(currentAppointment.customerProperty());
            comboBoxContact.valueProperty().bindBidirectional(currentAppointment.contactProperty());
            comboBoxUser.valueProperty().bindBidirectional(currentAppointment.userProperty());
            textStart.textProperty().bindBidirectional(currentAppointment.startProperty(), new LocalDateTimeStringConverter(dtformatter,dtformatter));
            textEnd.textProperty().bindBidirectional(currentAppointment.endProperty(), new LocalDateTimeStringConverter(dtformatter,dtformatter));
            textStartDate.textProperty().bindBidirectional(currentAppointment.startDateProperty(), new LocalDateStringConverter(dformatter, dformatter));
            textEndDate.textProperty().bindBidirectional(currentAppointment.endDateProperty(), new LocalDateStringConverter(dformatter,dformatter));
            textStartTime.textProperty().bindBidirectional(currentAppointment.startTimeProperty(), new LocalTimeStringConverter(tformatter, tformatter));
            textEndTime.textProperty().bindBidirectional(currentAppointment.endTimeProperty(), new LocalTimeStringConverter(tformatter,tformatter));

            overlapping = new BooleanBinding() {
                {
                    super.bind(textStart.textProperty(), textEnd.textProperty());
                }
                @Override
                protected boolean computeValue() {
                    try {
                        boolean tsEmpty = textStart.getText().trim().isEmpty();
                        boolean teEmpty = textEnd.getText().trim().isEmpty();
                        LocalDateTime tsDT = LocalDateTime.parse(textStart.getText(), dtformatter);
                        LocalDateTime teDT = LocalDateTime.parse(textEnd.getText(), dtformatter);
                        FilteredList<Appointment> aFL = new FilteredList<Appointment>(db.appointments, a -> a.getStart().toLocalDate().isEqual(tsDT.toLocalDate()));
                        boolean tsOverlap = aFL.stream().anyMatch(a ->
                                tsDT.toLocalTime().isAfter(a.getStart().toLocalTime()) && tsDT.toLocalTime().isBefore(a.getEnd().toLocalTime())
                                        || teDT.toLocalTime().isAfter(a.getStart().toLocalTime()) && teDT.toLocalTime().isBefore(a.getEnd().toLocalTime())
                        );
                        return tsOverlap;
                    } catch (DateTimeParseException dtp) {
                        return false;
                    }
                }
            };

            startEndDateValid = new BooleanBinding() {
                {
                    super.bind(textStartDate.textProperty(), textEndDate.textProperty());
                }
                @Override
                protected boolean computeValue() {
                    try {
                        return textStartDate.getText().trim().isEmpty() || textEndDate.getText().trim().isEmpty()
                                || (LocalDate.parse(textStartDate.getText(), dformatter).isBefore(LocalDate.parse(textEndDate.getText(), dformatter)))
                                ||  (LocalDate.parse(textStartDate.getText(), dformatter).isEqual(LocalDate.parse(textEndDate.getText(), dformatter)));
                    } catch (DateTimeParseException dte) {
                        return true;
                    }
                }
            };

            startEndTimeValid = new BooleanBinding() {
                {
                    super.bind(textStartTime.textProperty(), textEndTime.textProperty());
                }
                @Override
                protected boolean computeValue() {
                    try {
                        return textStartTime.getText().trim().isEmpty()
                                || textEndTime.getText().trim().isEmpty()
                                || LocalDate.parse(textStartDate.getText(), dformatter).atTime(LocalTime.parse(textStartTime.getText(), tformatter)).plusMinutes(15).isBefore(LocalDate.parse(textEndDate.getText(), dformatter).atTime(LocalTime.parse(textEndTime.getText(), tformatter)))
                                || LocalDate.parse(textStartDate.getText(), dformatter).atTime(LocalTime.parse(textStartTime.getText(), tformatter)).plusMinutes(15).isEqual(LocalDate.parse(textEndDate.getText(), dformatter).atTime(LocalTime.parse(textEndTime.getText(), tformatter)));
                    } catch (DateTimeParseException dte) {
                        return true;
                    }
                }
            };

            withinWorkingHours = new BooleanBinding() {
                {
                    super.bind(textStartTime.textProperty(), textEndTime.textProperty());
                }
                @Override

                protected boolean computeValue() {
                    try {
                        AppConfig cfg = new AppConfig();
                        ZonedDates ZD = cfg.getZonedDateTime();

                        return LocalTime.parse(textStartTime.getText(), tformatter).getHour() >= ZD.start.toLocalTime().getHour() &&
                                LocalTime.parse(textEndTime.getText(), tformatter).getHour() < ZD.end.toLocalTime().getHour();
                    } catch (DateTimeParseException dte) {
                        return true;
                    }
                }
            };

            apptDateListener = new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean wen) {
                    if (wen) {

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setHeaderText("Invalid Start or End Date");
                                alert.setContentText("Start Date must be before or same as End Date");
                                alert.show();
                            }
                        });
                    }
                }
            };
            apptListener = new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean wen) {
                    if (wen) {

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setHeaderText("Invalid Start or End");
                                alert.setContentText("Appointment must start at least 15 minutes before ending");
                                alert.show();
                            }
                        });
                    }
                }
            };
            workingHourListener = new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean wen) {
                    if (wen) {

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setHeaderText("Outside of Business Hours");
                                alert.setContentText("Appointment must be within business hours (8:00 a.m. to 10:00 p.m. ET, including weekends)");
                                alert.show();
                            }
                        });
                    }
                }
            };
            overlappingApptsListener = new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean wen) {
                    if (wen) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Dialog");
                                alert.setHeaderText("Overlapping appointments");
                                alert.setContentText("Appointments cannot overlap with other appointments");
                                alert.show();
                            }
                        });
                    }
                }
            };


            startEndDateValid.addListener(apptDateListener);
            startEndTimeValid.addListener(apptListener);
            withinWorkingHours.addListener(workingHourListener);
            overlapping.addListener(overlappingApptsListener);
        }

    }


    private ChangeListener<AppointmentFXAdapter> rowSelectionListener = new ChangeListener<AppointmentFXAdapter>() {
        @Override
        public void changed(ObservableValue<? extends AppointmentFXAdapter> observableValue, AppointmentFXAdapter old, AppointmentFXAdapter wen) {
            unBind(old);
            //            clearfields();
            reBind(wen);
        }
    };

    /**
     * Clears the existing rows in the table view and adds new rows based on FXAppointments list.
     * The table view is then bound to the selected appointment item.
     */
    private void addAppointmentRows() {
        tableView.setItems(appointmentFilteredList);
        tableView.getSelectionModel().selectedItemProperty().removeListener(rowSelectionListener);
//        tableView.getItems().clear();
        //tableView.setItems(FXAppointments);
        tableView.getSelectionModel().selectedItemProperty().addListener(rowSelectionListener);
        tableView.getSelectionModel().selectFirst();
    }

    /**
     * Adds columns to the appointment table view.
     * Columns include id, title, description, location, type, start date and time, end date and time,
     * customer, user, and contact.
     * The cell factories and value factories are also set for each column.
     * ComboBoxTableCell is used for the customer, user, and contact columns.
     */
    private void addAppointmentColumns() {
        TableColumn<AppointmentFXAdapter, Integer> idCol = new TableColumn<>(_bundle.getString("label.appointment.id.text"));
        TableColumn<AppointmentFXAdapter, String> titleCol = new TableColumn<>(_bundle.getString("label.appointment.title.text"));
        TableColumn<AppointmentFXAdapter, String> descriptionCol = new TableColumn<>(_bundle.getString("label.appointment.description.text"));
        TableColumn<AppointmentFXAdapter, String> locationCol = new TableColumn<>(_bundle.getString("label.appointment.location.text"));
        TableColumn<AppointmentFXAdapter, String> typeCol = new TableColumn<>(_bundle.getString("label.appointment.type.text"));
        TableColumn<AppointmentFXAdapter, LocalDateTime> startCol = new TableColumn<>(_bundle.getString("label.appointment.start.text"));
        TableColumn<AppointmentFXAdapter, LocalDateTime> endCol = new TableColumn<>(_bundle.getString("label.appointment.end.text"));

        TableColumn<AppointmentFXAdapter, Customer> customerCol = new TableColumn<>(_bundle.getString("label.appointment.customer.text"));
        TableColumn<AppointmentFXAdapter, Integer> customerIdCol = new TableColumn<>(_bundle.getString("label.customer.id.text"));

        TableColumn<AppointmentFXAdapter, User> userCol = new TableColumn<>(_bundle.getString("label.appointment.user.text"));
        TableColumn<AppointmentFXAdapter, Integer> userIdCol = new TableColumn<>(_bundle.getString("label.user.id.text"));

        TableColumn<AppointmentFXAdapter, Contact> contactCol = new TableColumn<>(_bundle.getString("label.appointment.contact.text"));
        TableColumn appointmentCol = new TableColumn(_bundle.getString("label.appointment.appointment.text"));
        TableColumn<AppointmentFXAdapter, LocalDate> startDateCol = new TableColumn(_bundle.getString("label.appointment.date.text"));
        TableColumn<AppointmentFXAdapter, LocalTime> startTimeCol = new TableColumn(_bundle.getString("label.appointment.time.text"));
        TableColumn<AppointmentFXAdapter, LocalDate> endDateCol = new TableColumn(_bundle.getString("label.appointment.date.text"));
        TableColumn<AppointmentFXAdapter, LocalTime> endTimeCol = new TableColumn(_bundle.getString("label.appointment.time.text"));

        startCol.getColumns().addAll(startDateCol, startTimeCol);
        endCol.getColumns().addAll(endDateCol, endTimeCol);
        appointmentCol.getColumns().addAll(startCol, endCol);

        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        titleCol.setCellValueFactory(f -> f.getValue().titleProperty());
        descriptionCol.setCellValueFactory(f -> f.getValue().descriptionProperty());
        locationCol.setCellValueFactory(f -> f.getValue().locationProperty());
        typeCol.setCellValueFactory(f -> f.getValue().typeProperty());
        startDateCol.setCellValueFactory(f -> f.getValue().startDateProperty());
        startTimeCol.setCellValueFactory(f -> f.getValue().startTimeProperty());
        endDateCol.setCellValueFactory(f -> f.getValue().endDateProperty());
        endTimeCol.setCellValueFactory(f -> f.getValue().endTimeProperty());
        startCol.setCellValueFactory(f -> f.getValue().startProperty());
        endCol.setCellValueFactory(f -> f.getValue().endProperty());

        customerCol.setCellValueFactory(f -> f.getValue().customerProperty());
        customerIdCol.setCellValueFactory(new PropertyValueFactory("customerId"));

        userCol.setCellValueFactory(f -> f.getValue().userProperty());
        userIdCol.setCellValueFactory(new PropertyValueFactory("userId"));

        contactCol.setCellValueFactory(f -> f.getValue().contactProperty());


        idCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        locationCol.setCellFactory(TextFieldTableCell.forTableColumn());
        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        startTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter(tformatter,tformatter)));
        startDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter(dformatter,dformatter)));
        endTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter(tformatter,tformatter)));
        endDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter(dformatter,dformatter)));
        customerIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        userIdCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        customerCol.setCellValueFactory(f -> f.getValue().customerProperty());
        userCol.setCellValueFactory(f -> f.getValue().userProperty());
        contactCol.setCellValueFactory(f -> f.getValue().contactProperty());

        customerCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                if (customer == null) {
                    return db.customers.stream().findFirst().get().getName();
                }
                return customer.getName();
            }

            @Override
            public Customer fromString(String s) {
                return db.customers.stream().filter(c -> c.getName().equals(s)).findFirst().orElse(null);
            }
        }, db.customers));

        contactCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                if (contact == null) {
                    return db.contacts.stream().findFirst().get().getName();
                }
                return contact.getName();
            }

            @Override
            public Contact fromString(String s) {
                return db.contacts.stream().filter(c -> c.getName().equals(s)).findFirst().orElse(null);
            }
        }, db.contacts));

        userCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                if (user == null) {
                    return db.users.stream().findFirst().get().getUserName();
                }
                return user.getUserName();
            }

            @Override
            public User fromString(String s) {
                return db.users.stream().filter(u -> u.getUserName().equals(s)).findFirst().orElse(null);
            }
        }, db.users));

        customerCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                if (customer == null) {
                    return db.customers.stream().findFirst().get().getName();
                }
                return customer.getName();
            }

            @Override
            public Customer fromString(String s) {
                return db.customers.stream().filter(c -> c.getName().equals(s)).findFirst().orElse(null);
            }
        }, db.customers));



        contactCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                if (contact == null) {
                    return db.contacts.stream().findFirst().get().getName();
                }
                return contact.getName();
            }

            @Override
            public Contact fromString(String s) {
                return db.contacts.stream().filter(c -> c.getName().equals(s)).findFirst().orElse(null);
            }
        }, db.contacts));


        idCol.setVisible(true);
        tableView.getColumns().addAll(idCol, titleCol, descriptionCol, locationCol, typeCol, appointmentCol, customerCol, customerIdCol, userCol, userIdCol, contactCol);
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

    /**
     * Commits changes to tables.
     *
     */
    private void dbCommit() {
        db.em.getTransaction().commit();
        db.em.getTransaction().begin();
        //tableView.refresh();
    }

    /**
     * Reverts changes
     *
     */
    public void dbRevert() {
        FXAppointments.forEach(item -> {
            unBind(item);
        });
        db.em.getTransaction().rollback();
        db.appointmentDB.fetchFromDB();
        FXAppointments.clear();
        FXAppointments.addAll(db.appointments.stream().map(item -> new AppointmentFXAdapter(item)).collect(Collectors.toList()));
        addAppointmentRows();
        db.em.getTransaction().begin();
    }

    /**
     * add new appointment record
     */
    private void recordAdd() {
        Appointment na = new Appointment();
        na.setContactId(db.contacts.stream().findFirst().get().getId());
        na.setContact(db.contacts.stream().findFirst().orElse(null));
        na.setCustomerId(db.customers.stream().findFirst().get().getId());
        na.setCustomer(db.customers.stream().findFirst().orElse(null));
        na.setUserId(db.users.stream().findFirst().get().getId());
        na.setUser(db.users.stream().findFirst().orElse(null));
        na.setStart(LocalDateTime.now());
        na.setEnd(LocalDateTime.now().plusMinutes(15));
        na.setStartDate(LocalDate.now());
        na.setEndDate(LocalDate.now());
        na.setStartTime(LocalTime.now());
        na.setEndTime(LocalTime.now().plusMinutes(15));
        db.em.persist(na);
        db.appointments.add(na);
        AppointmentFXAdapter nafx = new AppointmentFXAdapter(na);
        FXAppointments.add(nafx);
        tableView.getSelectionModel().select(nafx);
    }

    /**
     * Removes the selected appointment record from the table view, database, and FXAppointments list.
     */
    private void recordRemove() {
        AppointmentFXAdapter delAppointment = tableView.getSelectionModel().getSelectedItem();
        final Integer apptId = delAppointment.getId();
        final String apptType = delAppointment.getType();

        recordRemove(delAppointment);
        //alert to show customer was removed
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Removed");
            alert.setHeaderText(null);
            alert.setContentText(String.format("Appointment ID %d (%s) has been successfully removed.", apptId, apptType));

            alert.showAndWait();
        });
    }

    public void recordRemove(AppointmentFXAdapter delAppointment) {
        db.appointments.remove(delAppointment.appointment);
        db.em.remove(delAppointment.appointment);
        FXAppointments.remove(delAppointment);
    }

    /**
     * Launches the report form.
     * If an exception occurs during the launching of the report form, the exception message is printed to the console.
     */
    private void launchReport() {
        try {
            launchReportForm();
        } catch (Exception ex) {
            System.console().writer().println(ex.getMessage());
        }
    }




    // Method to apply filter
    private void applyDateFilter(Predicate<AppointmentFXAdapter> dateFilter) {
        appointmentFilteredList.setPredicate(dateFilter);
    }

    private Predicate<AppointmentFXAdapter> filterByWeek() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return appointment -> {
            LocalDate appointmentDate = appointment.getStartDate();
            return !appointmentDate.isBefore(startOfWeek) && !appointmentDate.isAfter(endOfWeek);
        };
    }

    private Predicate<AppointmentFXAdapter> filterByMonth() {
        LocalDate now = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(now);

        return appointment -> {
            LocalDate appointmentDate = appointment.getStartDate();
            YearMonth appointmentMonth = YearMonth.from(appointmentDate);
            return appointmentMonth.equals(currentMonth);
        };
    }

    // Reset the date filters (only apply the additional filters)
    private void resetFilter() {
        appointmentFilteredList.setPredicate(appointment -> true);
    }


    // Helper method to create a date range filter
    private Predicate<AppointmentFXAdapter> createDateFilterPredicate(LocalDate startDate, LocalDate endDate) {
        return appointment -> {
            LocalDate appointmentDate = appointment.getStart().toLocalDate(); // Assuming getStart() returns LocalDateTime
            boolean isInRange = (appointmentDate != null) &&
                    (!appointmentDate.isBefore(startDate) && !appointmentDate.isAfter(endDate));
            System.out.println("Filtering appointment on date " + appointmentDate + ": " + isInRange);
            return isInRange;
        };
    }

    /**
     * Launches the report form.
     *
     * This method is responsible for launching the report form. It creates an instance of the ReportsController class
     * with the provided DBContext object. It then loads the fxml file "reportsForm.fxml" using an FXMLLoader and sets
     * the controller to the ReportsController instance. The loaded root node is then set as the scene of a new stage
     * and the stage is displayed.
     *
     * @throws IOException if an I/O error occurs while loading the fxml file
     */
    private void launchReportForm() throws IOException {
        Stage thiswindow = (Stage)reportButton.getScene().getWindow();
//        CustomerFormController controller = new CustomerFormController(db, userName);
//        AppointmentFormController controller = new AppointmentFormController(db, userName);
        ReportsController controller = new ReportsController(db);
//
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("reportsForm.fxml"), _bundle);

//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("appointmentForm.fxml"), _bundle);
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("customerForm.fxml"), _bundle);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Schedule Data");
        stage.setScene(scene);
        stage.show();
    }
}
