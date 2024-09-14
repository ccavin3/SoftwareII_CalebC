package com.example.client_schedule.controller;

import com.example.client_schedule.MainApplication;
import com.example.client_schedule.adapters.AppointmentFXAdapter;
import com.example.client_schedule.entities.*;
import com.example.client_schedule.helper.DBContext;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * The type Appointment form controller. This class controls all the functionality for Appointment management.
 */
public class AppointmentFormController implements Initializable {
    public AppointmentFormController(DBContext db) {
        this.db = db;
    }

    private ResourceBundle _bundle;

    private DBContext db;
    private String userName;

//    private StringConverter<LocalDate> localDateStringConverter = new StringConverter<LocalDate>() {
//        @Override
//        public String toString(LocalDate d) {
//            if (d == null) return "";
//            return dformatter.format(d);
//        }
//
//        @Override
//        public LocalDate fromString(String s) {
//            if (s == null || s.trim().isEmpty()) return null;
//            return LocalDate.parse(s, dformatter);
//        }
//    };
//
//    private StringConverter<LocalTime> timeConverter = new StringConverter<LocalTime>() {
//        @Override
//        public String toString(LocalTime t) {
//            if (t == null) return "";
//            return tformatter.format(t);
//        }
//
//        @Override
//        public LocalTime fromString(String s) {
//            if (s == null || s.trim().isEmpty()) return null;
//            return LocalTime.parse(s, tformatter);
//        }
//    };
//
//    private StringConverter<LocalDateTime> dateTimeConverter = new StringConverter<LocalDateTime>() {
//        @Override
//        public String toString(LocalDateTime t) {
//            if (t == null) return "";
//            return dtformatter.format(t);
//        }
//
//        @Override
//        public LocalDateTime fromString(String s) {
//            if (s == null || s.trim().isEmpty()) return null;
//            return LocalDateTime.parse(s, dtformatter);
//        }
//    };

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

    private DateTimeFormatter dformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter tformatter = DateTimeFormatter.ofPattern("hh:mm[:ss] a");
    private DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm[:ss] a");
    private DateTimeFormatter minformatter = DateTimeFormatter.ofPattern("hh:mm");

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

//    private TextFormatter<String> sldFormatter;
//
//    private TextFormatter<String> sltFormatter;
//
//    private TextFormatter<String> eldFormatter;
//
//    private TextFormatter<String> eltFormatter;
    private ObservableList<AppointmentFXAdapter> FXAppointments;
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    @Override
    public void initialize(URL Url, ResourceBundle bundle) {
        this._bundle = bundle;
        FXAppointments = FXCollections.observableList(this.db.appointments.stream().map(item -> new AppointmentFXAdapter(item)).collect(Collectors.toList()));
        appointmentFilteredList = new FilteredList<>(db.appointments, p -> true);

//        dateStringConverter = new StringConverter<String>() {
//            @Override
//            public String toString(String localDate) {
//                return localDate;
//            }
//
//            @Override
//            public String fromString(String s) {
//                return LocalDate.parse(s).format(dformatter);
//            }
//        };
//        timeStringConverter = new StringConverter<String>() {
//            @Override
//            public String toString(String localTime) {
//                return localTime;
//            }
//
//            @Override
//            public String fromString(String s) {
//                return LocalTime.parse(s).format(tformatter);
//            }
//        };
        StringConverter<LocalDate> dateStringConverter = new LocalDateStringConverter(dformatter,dformatter);
        StringConverter<LocalTime> timeStringConverter = new LocalTimeStringConverter(tformatter,tformatter);

//        sldFormatter = new TextFormatter<>(
//                dateStringConverter,   //converter
//                LocalDate.now().format(dformatter),        //default value
//                dateValidationFormatter
//        );
//        sltFormatter = new TextFormatter<>(
//                timeStringConverter,   //converter
//                LocalTime.now().format(tformatter),        //default value
//                timeValidationFormatter
//        );
//        eldFormatter = new TextFormatter<>(
//                dateStringConverter,   //converter
//                LocalDate.now().format(dformatter),        //default value
//                dateValidationFormatter
//        );
//        eltFormatter = new TextFormatter<>(
//                timeStringConverter,   //converter
//                LocalTime.now().format(tformatter),        //default value
//                timeValidationFormatter
//        );
        onCommitAction = e -> dbCommit();
        onRevertAction = e -> dbRevert();
        onInsertAction = e -> recordAdd();
        onDeleteAction = e -> recordRemove();
        onReportAction = e -> launchReport();

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

        tableView.setEditable(true);
        addAppointmentColumns();

//        tableView.setRowFactory(tableView -> {
//            TableRow<Appointment> row = new TableRow<>();
//            ObjectProperty<Appointment> opMsg = row.itemProperty();
//            Appointment tmpMsg = opMsg.get();
//            return row;
//        });
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
    }

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
            WithinWorkingHours.removeListener(workingHourListener);
            overlapping.removeListener(overlappingApptsListener);
        }
    }

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


    private void reBind(AppointmentFXAdapter currentAppointment) {
        if (currentAppointment != null) {
//            textTitle.textProperty().addListener(titleListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.titleProperty().set(t1);
//                    tableView.refresh();
//                }
//            });
//            textAppointmentID.textProperty().addListener(idListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.idProperty().set(Integer.parseInt(t1));
//                    tableView.refresh();
//                }
//            });
//            textDescription.textProperty().addListener(descriptionListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.descriptionProperty().set(t1);
//                    tableView.refresh();
//                }
//            });
//            textType.textProperty().addListener(typeListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.typeProperty().set(t1);
//                    tableView.refresh();
//                }
//            });
//            textLocation.textProperty().addListener(locationListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.locationProperty().set(t1);
//                    tableView.refresh();
//                }
//            });
//            comboBoxCustomer.valueProperty().addListener(customerChangeListener = new ChangeListener<Customer>() {
//                @Override
//                public void changed(ObservableValue<? extends Customer> observableValue, Customer customer, Customer t1) {
//                    currentAppointment.customerProperty().set(t1);
//                    tableView.refresh();
//                }
//            });
//            comboBoxUser.valueProperty().addListener(userChangeListener = new ChangeListener<User>() {
//                @Override
//                public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {
//                    currentAppointment.userProperty().set(t1);
//                    tableView.refresh();
//                }
//            });
//            comboBoxContact.valueProperty().addListener(contactChangeListener = new ChangeListener<Contact>() {
//                @Override
//                public void changed(ObservableValue<? extends Contact> observableValue, Contact contact, Contact t1) {
//                    currentAppointment.contactProperty().set(t1);
//                    tableView.refresh();
//                }
//            });
//            textStart.textProperty().addListener(startListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.startProperty().set(LocalDateTime.parse(t1, dtformatter));
//                    tableView.refresh();
//                }
//            });
//            textEnd.textProperty().addListener(endListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.endProperty().set(LocalDateTime.parse(t1, dtformatter));
//                    tableView.refresh();
//                }
//            });
//            textStartDate.textProperty().addListener(startDateListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.startDateProperty().set(LocalDate.parse(t1, dformatter));
//                    tableView.refresh();
//                }
//            });
//            textEndDate.textProperty().addListener(endDateListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.endDateProperty().set(LocalDate.parse(t1, dformatter));
//                    tableView.refresh();
//                }
//            });
//            textStartTime.textProperty().addListener(startTimeListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.startTimeProperty().set(LocalTime.parse(t1, tformatter));
//                    tableView.refresh();
//                }
//            });
//            textEndTime.textProperty().addListener(endTimeListener = new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                    currentAppointment.endTimeProperty().set(LocalTime.parse(t1, tformatter));
//                    tableView.refresh();
//                }
//            });
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
                                alert.setHeaderText("Invalid Start or End");
                                alert.setContentText("Appointment must start at least 15 minutes before ending");
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
            WithinWorkingHours.addListener(workingHourListener);
            overlapping.addListener(overlappingApptsListener);
        }

    }

    private BooleanBinding overlapping = new BooleanBinding() {
        {
            super.bind(textStart.textProperty(), textEnd.textProperty());
        }
        @Override
        protected boolean computeValue() {
            boolean tsEmpty = textStart.getText().trim().isEmpty();
            boolean teEmpty = textEnd.getText().trim().isEmpty();
            LocalDateTime tsDT = LocalDateTime.parse(textStart.getText());
            LocalDateTime teDT = LocalDateTime.parse(textEnd.getText());
            FilteredList<Appointment> aFL = new FilteredList<Appointment>(db.appointments,  a -> a.getStartDate().isEqual(tsDT.toLocalDate()));
            boolean tsOverlap = aFL.stream().anyMatch(a ->
                tsDT.toLocalTime().isAfter(a.getStartTime()) && tsDT.toLocalTime().isBefore(a.getEndTime())
                    || teDT.toLocalTime().isAfter(a.getStartTime()) && teDT.toLocalTime().isBefore(a.getEndTime())
            );
            return tsOverlap;
        }
    };

    private BooleanBinding startEndDateValid = new BooleanBinding() {
        {
            super.bind(textStartDate.textProperty(), textEndDate.textProperty());
        }
        @Override
        protected boolean computeValue() {
            return textStartDate.getText().trim().isEmpty() || textEndDate.getText().trim().isEmpty()
                    || (LocalDate.parse(textStartDate.getText()).isBefore(LocalDate.parse(textEndDate.getText())))
                    ||  (LocalDate.parse(textStartDate.getText()).isEqual(LocalDate.parse(textEndDate.getText())));
        }
    };

    private BooleanBinding startEndTimeValid = new BooleanBinding() {
        {
            super.bind(textStartTime.textProperty(), textEndTime.textProperty());
        }
        @Override
        protected boolean computeValue() {
            return textStartTime.getText().trim().isEmpty()
                    || textEndTime.getText().trim().isEmpty()
                    || LocalDate.parse(textStartDate.getText()).atTime(LocalTime.parse(textStartTime.getText())).plusMinutes(15).isBefore(LocalDate.parse(textEndDate.getText()).atTime(LocalTime.parse(textEndTime.getText())))
                    || LocalDate.parse(textStartDate.getText()).atTime(LocalTime.parse(textStartTime.getText())).plusMinutes(15).isEqual(LocalDate.parse(textEndDate.getText()).atTime(LocalTime.parse(textEndTime.getText())));
        }
    };

    private BooleanBinding WithinWorkingHours = new BooleanBinding() {
        {
            super.bind(textStartTime.textProperty(), textEndTime.textProperty());
        }
        @Override
        protected boolean computeValue() {
            return LocalTime.parse(textStartTime.getText()).getHour() >= 8 &&
                    LocalTime.parse(textEndTime.getText()).getHour() < 17;
        }
    };

    private void addAppointmentRows() {
//        tableView.setItems(appointmentFilteredList);
        tableView.getItems().clear();
        tableView.setItems(FXAppointments);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, old, wen) -> {
            unBind(old);
//            clearfields();
            reBind(wen);
        });
        tableView.getSelectionModel().selectFirst();
    }

    private void addAppointmentColumns() {
        TableColumn<AppointmentFXAdapter, Integer> idCol = new TableColumn<>("id");
        TableColumn<AppointmentFXAdapter, String> titleCol = new TableColumn<>(_bundle.getString("label.appointment.title.text"));
        TableColumn<AppointmentFXAdapter, String> descriptionCol = new TableColumn<>(_bundle.getString("label.appointment.description.text"));
        TableColumn<AppointmentFXAdapter, String> locationCol = new TableColumn<>(_bundle.getString("label.appointment.location.text"));
        TableColumn<AppointmentFXAdapter, String> typeCol = new TableColumn<>(_bundle.getString("label.appointment.type.text"));
        TableColumn<AppointmentFXAdapter, LocalDateTime> startCol = new TableColumn<>(_bundle.getString("label.appointment.start.text"));
        TableColumn<AppointmentFXAdapter, LocalDateTime> endCol = new TableColumn<>(_bundle.getString("label.appointment.end.text"));
        TableColumn<AppointmentFXAdapter, Customer> customerCol = new TableColumn<>(_bundle.getString("label.appointment.customer.text"));
        TableColumn<AppointmentFXAdapter, User> userCol = new TableColumn<>(_bundle.getString("label.appointment.user.text"));
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
        userCol.setCellValueFactory(f -> f.getValue().userProperty());
        contactCol.setCellValueFactory(f -> f.getValue().contactProperty());

        idCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        titleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        locationCol.setCellFactory(TextFieldTableCell.forTableColumn());
        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        startCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        endCol.setCellFactory(TextFieldTableCell.forTableColumn());
        startTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter(tformatter,tformatter)));
        startDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter(dformatter,dformatter)));
        endTimeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalTimeStringConverter(tformatter,tformatter)));
        endDateCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter(dformatter,dformatter)));

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
        FXAppointments.forEach(item -> {
            unBind(item);
        });
        db.em.getTransaction().rollback();
        db.appointmentDB.fetchFromDB();
        FXAppointments.clear();
        FXAppointments.addAll(db.appointments.stream().map(item -> new AppointmentFXAdapter(item)).collect(Collectors.toList()));
        db.em.getTransaction().begin();
    }

    private void recordAdd() {
        Appointment na = new Appointment();
        na.setContactId(db.contacts.stream().findFirst().get().getId());
        na.setContact(db.contacts.stream().findFirst().orElse(null));
        na.setCustomerId(db.customers.stream().findFirst().get().getId());
        na.setCustomer(db.customers.stream().findFirst().orElse(null));
        na.setUserId(db.users.stream().findFirst().get().getId());
        na.setUser(db.users.stream().findFirst().orElse(null));
        na.setStart(LocalDateTime.now());
        na.setEnd(LocalDateTime.now());
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

    private void recordRemove() {
        AppointmentFXAdapter delAppointment = tableView.getSelectionModel().getSelectedItem();
        db.appointments.remove(delAppointment.appointment);
        db.em.remove(delAppointment.appointment);
        FXAppointments.remove(delAppointment);
    }

    private void launchReport() {
        try {
            launchReportForm();
        } catch (Exception ex) {

        }
    }

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
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("Schedule Data");
        stage.setScene(scene);
        stage.show();
    }
}
