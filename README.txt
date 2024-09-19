SoftwareIIb appointment scheduling

Author information:
Caleb Caviness, ccavin3@wgu.edu, ver 2, 9/15/2024

IDE, JDK and JavaFX info:
IntelliJ IDEA 2024.1.4 (Community Edition)
Build #IC-241.18034.62, built on June 20, 2024
Runtime version: 17.0.11+1-b1207.24 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 11.0
GC: G1 Young Generation, G1 Old Generation
Memory: 4060M
Cores: 8
Registry:
  ide.experimental.ui=true
Non-Bundled Plugins:
  com.intellij.properties.bundle.editor (241.14494.150)
  com.intellij.ml.llm (241.18034.12)
  com.posidev.applicationInspector (2.2.1)
  mobi.hsz.idea.gitignore (4.5.3)
Kotlin: 241.18034.62-IJ
javaFX: v 17


Directions:
1. Navigate to project folder (SoftwareII_CalebC\src\main\java\com\example\client_schedule)
2. run Software (MainApplication.java)
3. Using the program
    a.Login using test credentials
    b.Main form has two tabs, Customers and Appointments, with respective tableviews.
    c.Tabs provide tableviews for navigation
    d.Tabs provide add and edit forms for Customer or Application
    e.To add a new record, press the add button. Complete the fields in the add/edit area
    f.To edit a record, select the record from tableview and make changes in the add/edit area
    g.To delete a record, select the record from tableview and press delete button
    h.To commit changes to the database, press the commit button
    i.To undo all changes and revert, press the revert button
    j.To navigate to reports, select the appointments tab and click the reports button
    k.On the reports form, there are three tabs, one for each type of report. Use respective comboBoxes to change filters and fill each report.
    l.Press home button to close the reports form and return to the Customers and Appointments form.

Third report
Customers filtered by country

SQL Driver and version
com.mysql.cj.jdbc.Driver
Maven: mysql:mysql-connector-java:8.0.26

Lambda locations
AppointmentFormController initialize
ReportsController filterContacts