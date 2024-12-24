package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Model.TypeH;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Types;
import java.util.Date;

public class HolidayView  {
    public JPanel panel1;
    public JPanel panel2;
    public JPanel panel3;
    String[] columnNames={"ID", "Date de début", "Date de fin","Type","nom_employe"};

    public JLabel lblId, lblDateDebut, lblDateFin, lblType, lblIdEmploye;
    public JTextField idField;
    public JTextField txtDateDebut, txtDateFin;
    public JComboBox<TypeH> cmbType;
    public JComboBox<String> cmbIdEmploye; 
    public JTable table;
    public DefaultTableModel model;
    public JScrollPane scrollPane;
    public JButton btnAjouter, btnModifier, btnSupprimer, btnAfficher;

    public HolidayView() {        
        panel1 = new JPanel(new BorderLayout());
        panel2 = new JPanel(new GridLayout(6, 2));
        panel3 = new JPanel(new FlowLayout());
        lblDateDebut = new JLabel("Date de début (yyyy-MM-dd):");
        txtDateDebut = new JTextField(10);
        lblDateFin = new JLabel("Date de fin (yyyy-MM-dd):");
        txtDateFin = new JTextField(10);
        lblType = new JLabel("Type de congé:");
        cmbType = new JComboBox<>(TypeH.values()); 
        lblIdEmploye = new JLabel("ID Employé:");
        
     
        cmbIdEmploye = new JComboBox<>();

   
        panel2.add(lblDateDebut);
        panel2.add(txtDateDebut);
        panel2.add(lblDateFin);
        panel2.add(txtDateFin);
        panel2.add(lblType);
        panel2.add(cmbType);
        panel2.add(lblIdEmploye);
        panel2.add(cmbIdEmploye);

 
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);

        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");
        btnAfficher = new JButton("Afficher");

        panel3.add(btnAjouter);
        panel3.add(btnModifier);
        panel3.add(btnSupprimer);
        panel3.add(btnAfficher);

        panel1.add(panel2, BorderLayout.NORTH);
        panel1.add(scrollPane, BorderLayout.CENTER);
        panel1.add(panel3, BorderLayout.SOUTH);

    }
    
    
    
    
    public JTable getTable() {
		return table;
	}
    
    public JPanel getpan() {
    	return  panel1;
    }




	public void setTable(JTable table) {
		this.table = table;
	}




	public String[] getColumnNames() {
    	return columnNames;
    }
    
 



	public void setCmbType(JComboBox<TypeH> cmbType) {
		this.cmbType = cmbType;
	}





	public void getAjouterListener(ActionListener e) {
    	btnAjouter.addActionListener(e);
    }
    
    
    
}
