package main;

import Entity.Patient;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import repository.PatientRepository;

import java.awt.*;
import java.util.List;

@Route("/patient")
public class PatientView extends VerticalLayout {

    public PatientView(PatientRepository patientRepository) {
        Grid<Patient> grid = new Grid<>();
        grid.addColumn(Patient::getName).setHeader("First Name");
        grid.addColumn(Patient::getSurName).setHeader("Last Name");
        grid.addColumn(Patient::getPatronic).setHeader("Patronic");
        grid.addColumn(Patient::getPhone).setHeader("Phone");

        List<Patient> everyone = patientRepository.findAll();
        grid.setItems(everyone);
        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField patronic = new TextField();
        TextField phone = new TextField();
        Button addButton = new Button("Add patient");

        addButton.addClickListener(click-> {
            
        });

        add(grid);
    }
}
