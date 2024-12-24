package Controller;

import Model.HolidayModel;

import View.HolidayView;
import Model.Employees; 
import Model.TypeH;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class HolidayController {
    private HolidayView view;
    private HolidayModel model;
    private int selectedEmployeeId=-1;
    
    public HolidayController(HolidayView view, HolidayModel model) {
        this.view = view;
        this.model = model;
       
        fillEmployeeIdsComboBox();

        
        
 ///////////////////////////// Button Ajouter ////////////////////////

        this.view.btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	Integer idEmploye=RetournIds();
                    TypeH typeConges = (TypeH) view.cmbType.getSelectedItem();
                    String dateDebutStr = view.txtDateDebut.getText().trim();
                    String dateFinStr = view.txtDateFin.getText().trim();

                    
                    if (!isValidDate(dateDebutStr, "yyyy-MM-dd")) {
                    	JOptionPane.showMessageDialog(null,"La date de début est invalide. Veuillez utiliser le format yyyy-MM-dd.");
                        return;
                    }
                    
                    if (!isValidDate(dateFinStr, "yyyy-MM-dd")) {
                        JOptionPane.showMessageDialog(null, "La date de fin est invalide. Veuillez utiliser le format yyyy-MM-dd.");
                        return;
                    }
                    
                    LocalDate dateDebut = LocalDate.parse(dateDebutStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate dateFin = LocalDate.parse(dateFinStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    
                    if (!dateFin.isAfter(dateDebut)) {
                        JOptionPane.showMessageDialog(null, "La date de fin doit être strictement supérieure à la date de début.");
                        return;
                    }
                    model.addHoliday(dateDebut, dateFin, typeConges, idEmploye);
                    
                    afficher();
                    view.txtDateDebut.setText("");
                    view.txtDateFin.setText("");
                    
                }catch (Exception ex) {
                	JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage());
                }
            }
        });

        
        
  ////////////////////////// Selection  /////////////////////////////////////
        
        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.table.getSelectedRow();

                if (selectedRow != -1) {
                    selectedEmployeeId = (int) view.model.getValueAt(selectedRow, 0);  

                    Date dateDebut = (Date) view.model.getValueAt(selectedRow, 1);
                    Date dateFin = (Date) view.model.getValueAt(selectedRow, 2);    

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
                    String dateDebutStr = (dateDebut != null) ? dateFormat.format(dateDebut) : "";
                    String dateFinStr = (dateFin != null) ? dateFormat.format(dateFin) : "";

                    view.txtDateDebut.setText(dateDebutStr); 
                    view.txtDateFin.setText(dateFinStr);      
                }
            }
        });


        //////////// Button Modifier ////////////////////////


        this.view.btnModifier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	
                    int idEmploye = RetournIds();
                    TypeH typeConges = (TypeH) view.cmbType.getSelectedItem();
                    String dateDebutStr = view.txtDateDebut.getText().trim();
                    String dateFinStr = view.txtDateFin.getText().trim();

                    if (!isValidDate(dateDebutStr) || !isValidDate(dateFinStr)) {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer des dates valides au format yyyy-MM-dd.");
                        return;
                    }

                    LocalDate dateDebut = LocalDate.parse(dateDebutStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate dateFin = LocalDate.parse(dateFinStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        model.modifyHoliday(selectedEmployeeId, dateDebut, dateFin, typeConges, idEmploye);
                        afficher();
                       
                  
              
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite : " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        
       //////////// Button Suprimer////////////////////////

        this.view.btnSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = view.table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = Integer.parseInt(view.model.getValueAt(selectedRow,0).toString());
                    model.deleteHoliday(id);
                    view.model.removeRow(selectedRow);
                    afficher();
                } else {
                    JOptionPane.showInputDialog(view, "Please select a row to modify.");
                }
            }
        });
        
        this.view.btnAfficher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        
            		afficher();
    }
});
    }

    
    
    
    
    private boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    
    
    
    
    private void fillEmployeeIdsComboBox() {
        List<String> employeeIds = model.getIds();
        
        view.cmbIdEmploye.removeAllItems();
        
        for (String id : employeeIds) {
            view.cmbIdEmploye.addItem(id);  
        }
    }

    
    
    public void afficher() {
        Object[][] holidayData = model.getAllHolidays();  
        view.model = new DefaultTableModel(holidayData, view.getColumnNames());
        view.getTable().setModel(view.model);
        fillEmployeeIdsComboBox();
    }

    
    private boolean isValidDate(String dateStr, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
        
        
    }
    
    
    
    ///////////Retourn Id///////////////////////////
public Integer RetournIds() {
	int idEmploye = -1;  
	String selectedItem = view.cmbIdEmploye.getSelectedItem().toString().trim();

	if (selectedItem != null && !selectedItem.isEmpty()) {
	    try {
	        String[] parts = selectedItem.split(" - ");
	        if (parts.length == 2) {
	            idEmploye = Integer.parseInt(parts[0].trim());
	           
	        }
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	    }
	}
	
	 return  idEmploye;
}
	
}
