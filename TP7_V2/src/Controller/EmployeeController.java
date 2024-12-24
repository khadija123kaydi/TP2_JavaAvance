package Controller;

import Model.EmployeeModel;
import Model.Poste;
import Model.Role;
import View.EmployeesView;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class EmployeeController {
    private EmployeesView view;
    private EmployeeModel model;
    private int selectedEmployeeId = -1;  // Variable globale pour l'ID sélectionné

    public EmployeeController(EmployeesView view, EmployeeModel model) {
        this.view = view;
        this.model = model;

        // ActionListener pour ajouter un employé
        this.view.btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = view.text1.getText();
                String prenom = view.text2.getText();
                String telephone = view.text3.getText();
                String email = view.text4.getText();
                double salaire = Double.parseDouble(view.text5.getText());
                Role role = (Role) view.roles.getSelectedItem();
                Poste poste = (Poste) view.postes.getSelectedItem();
                model.addEmployee(nom, prenom, email, telephone, salaire, role, poste);
                afficher();
            }
        });

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.table.getSelectedRow();

                if (selectedRow != -1) {
                    selectedEmployeeId = (int) view.model.getValueAt(selectedRow, 0);  
                    String nom = (String) view.model.getValueAt(selectedRow, 1);
                    String prenom = (String) view.model.getValueAt(selectedRow, 2);
                    String tele = (String) view.model.getValueAt(selectedRow, 3);
                    String email = (String) view.model.getValueAt(selectedRow, 4);
                    Double salaire = (Double) view.model.getValueAt(selectedRow, 5);
                    String role = (String) view.model.getValueAt(selectedRow, 6).toString();
                    String poste = (String) view.model.getValueAt(selectedRow, 7).toString();

                    view.text1.setText(nom);
                    view.text2.setText(prenom);
                    view.text4.setText(tele);
                    view.text3.setText(email);
                    view.text5.setText(salaire.toString());
                    view.roles.setSelectedItem(role);
                    view.postes.setSelectedItem(poste);
                }
            }
        });

        // ActionListener pour modifier un employé
        this.view.btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedEmployeeId == -1) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé à modifier.");
                    return;
                }
                String nom = view.text1.getText();
                String prenom = view.text2.getText();
                String tele = view.text3.getText();
                String email = view.text4.getText();
                Double salaire = Double.parseDouble(view.text5.getText());
                Role role = (Role) view.roles.getSelectedItem();
                Poste poste = (Poste) view.postes.getSelectedItem();

                if (!model.getIds().contains(selectedEmployeeId)) {
                    JOptionPane.showMessageDialog(null, "Employé introuvable.");
                } else {
                    model.modifyEmployee(selectedEmployeeId, nom, prenom, email, tele, salaire, role, poste);
                    afficher();
                }
            }
        });

        this.view.btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) view.model.getValueAt(selectedRow, 0);
                    model.deleteEmployee(id);
                    view.model.removeRow(selectedRow);
                    afficher();
                } else {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un employé à supprimer.");
                }
            }
        });

        this.view.btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficher();
            }
        });
    }

    public void afficher() {
        Object[][] allEmployees = model.getAllEmployees();
        view.model.setRowCount(0);
        for (Object[] employee : allEmployees) {
            view.model.addRow(employee);
        }
    }
}
