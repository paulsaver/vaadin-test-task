package main;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class PatientEditor extends VerticalLayout implements KeyNotifier {

    private PatientRepository patientRepository;

    private Patient patient;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField patronic = new TextField("Patronic name");
    TextField phone = new TextField("Phone");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Patient> binder = new Binder<>(Patient.class);
    private ChangeHandler changeHandler;

    @Autowired
    public PatientEditor(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;

        add(firstName, lastName, patronic, phone, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editPatient(null));
        setVisible(false);
    }

    void delete() {
        patientRepository.delete(patient);
        changeHandler.onChange();
    }

    void save() {
        patientRepository.save(patient);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editPatient(Patient p) {
        if (p == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = p.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            patient = patientRepository.findById(p.getId()).get();
        }
        else {
            patient = p;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(patient);

        setVisible(true);

        // Focus first name initially
        firstName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }
}
