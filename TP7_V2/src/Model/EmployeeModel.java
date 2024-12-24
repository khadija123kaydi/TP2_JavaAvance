package Model;

import DAO.EmployeeDAOimplement;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class EmployeeModel {
    private EmployeeDAOimplement dao;


    public EmployeeModel(EmployeeDAOimplement dao) {
        this.dao = dao;
    }

    public boolean addEmployee(String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {

        if (telephone.length() != 10) {
        	JOptionPane.showMessageDialog(null,"The phone number must contain exactly 10 digits.","message",JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        Employees emp = new Employees(nom, prenom, email, telephone, salaire, role, poste);
        dao.addGeneric(emp);
        return true;
    }

    public boolean modifyEmployee(int id,String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste) {

        if (telephone.length() != 11) {
        	JOptionPane.showMessageDialog(null,"The phone number must contain exactly 10 digits.","message",JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        Employees emp = new Employees(id,nom, prenom, email, telephone, salaire, role, poste);
        dao.modifyGeneric(emp);
        return true;
    }

    public boolean deleteEmployee(int id){
    	dao.deleteGeneric(id);
    	return true;
    }
    
   
    public Object[][] getAllEmployees() {
        List<Object[]> employees = dao.getAllGeneric(); 
        Object[][] employeeData = new Object[employees.size()][9]; 

        for (int i = 0; i < employees.size(); i++) {
            Object[] emp = employees.get(i); 
            employeeData[i][0] = emp[0]; 
            employeeData[i][1] = emp[1]; 
            employeeData[i][2] = emp[2]; 
            employeeData[i][3] = emp[3];
            employeeData[i][4] = emp[4]; 
            employeeData[i][5] = emp[5]; 
            employeeData[i][6] = emp[6];
            employeeData[i][7] = emp[7]; 
            employeeData[i][8] = emp[8]; 
        }

        return employeeData; 
    }


    public List<Integer> getIds() {
        List<Object[]> employees = dao.getAllGeneric(); 
        List<Integer> ids = new ArrayList<>(); 

        for (Object[] employee : employees) {
            ids.add((Integer) employee[0]); 
        }

        return ids; 
    }

    
    
    


   
  

    
}
