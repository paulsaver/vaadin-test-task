package main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

@Route("patient")
public class PatientView extends VerticalLayout {

    private final PatientRepository patientRepository;
    private final PatientEditor patientEditor;

    final Grid<Patient> grid;

    final TextField filter;

    private final Button addNewBtn;

    public PatientView(PatientRepository patientRepository, PatientEditor patientEditor) {
        this.patientRepository = patientRepository;
        this.patientEditor = patientEditor;
        this.grid = new Grid<>(Patient.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New patient", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, patientEditor);

        grid.setHeight("300px");
        grid.setColumns("id", "firstName", "lastName", "patronic", "phone");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by last name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listPatient(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            patientEditor.editPatient(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> patientEditor.editPatient(new Patient("", "", "", "")));

        // Listen changes made by the editor, refresh data from backend
        patientEditor.setChangeHandler(() -> {
            patientEditor.setVisible(false);
            listPatient(filter.getValue());
        });

        // Initialize listing
        listPatient(null);
    }

    private void listPatient(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(patientRepository.findAll());
        }
        else {
            grid.setItems(patientRepository.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}
