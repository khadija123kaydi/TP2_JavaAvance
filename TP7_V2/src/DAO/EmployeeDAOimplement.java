package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Model.Employees;
import Model.Poste;
import Model.Role;

public class EmployeeDAOimplement implements GenericDAOI<Employees> {
    private static Connect c;

    public EmployeeDAOimplement() {
        c = new Connect();
    }

    @Override
    public void addGeneric(Employees emp) {
        String sql = "INSERT INTO Employees ( nom, prenom, email, salaire, role, poste, telephone) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = c.getConnect().prepareStatement(sql)) {
            st.setString(1, emp.getNom());
            st.setString(2, emp.getPrenom());
            st.setString(3, emp.getEmail());
            st.setDouble(4, emp.getSalaire());
            st.setString(5, emp.getRole().toString());
            st.setString(6, emp.getPoste().toString());
            st.setString(7, emp.getTelephone());
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Employee added successfully!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error adding employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void modifyGeneric(Employees emp) {
        String sql = "UPDATE Employees SET nom = ?, prenom = ?, email = ?, salaire = ?, role = ?, poste = ?, telephone = ? WHERE id = ?";
        try (PreparedStatement st = c.getConnect().prepareStatement(sql)) {
            st.setString(1, emp.getNom());
            st.setString(2, emp.getPrenom());
            st.setString(3, emp.getEmail());
            st.setDouble(4, emp.getSalaire());
            st.setString(5, emp.getRole().toString());
            st.setString(6, emp.getPoste().toString());
            st.setString(7, emp.getTelephone());
            st.setInt(8, emp.getId());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Employee updated successfully!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No employee found to update.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error updating employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteGeneric(int id_emp) {
    	
    
        String sql = "DELETE FROM Employees WHERE id = ?";
        try (PreparedStatement st = c.getConnect().prepareStatement(sql)) {
            st.setInt(1, id_emp);
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Employee deleted successfully!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No employee found with the given ID.", "Information", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error deleting employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<Object[]> getAllGeneric() {
        List<Object[]> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees";

        try (PreparedStatement st = c.getConnect().prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                employees.add(new Object[] {
                    rs.getInt("id"),                   
                    rs.getString("nom"),               
                    rs.getString("prenom"),            
                    rs.getString("email"),             
                    rs.getString("telephone"),         
                    rs.getDouble("salaire"),           
                    rs.getString("role"),              
                    rs.getString("poste"),
                    rs.getDouble("solde")
                 
                });
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Error retrieving employees: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return employees;
    }

    
    
    
	
	  public boolean  valideBlance(int valeur,int id) {
		 
		  
		  String query="select * from Employees where id=?";
		  try(PreparedStatement st=c.getConnect().prepareStatement(query)){
			  st.setInt(1, id);
			  ResultSet rst=st.executeQuery();
			  if(rst.next()) {
				 Employees em =new Employees(rst.getInt("id"),rst.getString("nom"),rst.getString("prenom"),rst.getString("email"),rst.getString("telephone"),rst.getDouble("salaire"),Role.valueOf(rst.getString("role")),Poste.valueOf(rst.getString("poste")));
				 
				 if(em.balance <valeur) {
					 return false;
				 }
				 
				 em.balance-=valeur;
				 System.out.println(" la valeur des holiday qui reste est :"+em.balance);
				
			  }
			  
		  }catch(SQLException ex) {
			  System.out.print("erreur dans la selection d'emmploye pour balance");
		  }
		   
		  return true;
	 }
	  
	  
	  
/////////Recuperer 	  

	    public double SoldeActuel(int id ) {
	    	   String query2 = "SELECT solde FROM employees WHERE id = ?";
	    	   double soldeActuel = -1;

	           try (PreparedStatement st = c.getConnect().prepareStatement(query2)) {
	               st.setInt(1, id);
	               ResultSet rs = st.executeQuery();

	               if (rs.next()) { 
	                   soldeActuel = rs.getDouble("solde");
	                   System.out.println("Le solde récupéré est : " + soldeActuel);
	               }
	           } catch (SQLException ex) {
	               ex.printStackTrace();
	               JOptionPane.showMessageDialog(null, "Erreur lors de la récupération du solde.", "Erreur", JOptionPane.ERROR_MESSAGE);
	           }
	           return soldeActuel;
	    }
    
	    
	    
	    
	    
	    
	    
	    
//////////////////Modifier Solde //////////

public void ModifierSolde(int id, double nouveauSolde) {

String query = "UPDATE employees SET solde = ? WHERE id = ?";
double soldeActuel=SoldeActuel(id);


try (PreparedStatement st = c.getConnect().prepareStatement(query)) {
   st.setDouble(1, soldeActuel-nouveauSolde);
   st.setInt(2, id);
   int rowsAffected = st.executeUpdate();

   if (rowsAffected > 0) {
       JOptionPane.showMessageDialog(null, "Le solde a été modifié avec succès !");
   } else {
       JOptionPane.showMessageDialog(null, "La mise à jour du solde a échoué.", "Erreur", JOptionPane.ERROR_MESSAGE);
   }
} catch (SQLException ex) {
   ex.printStackTrace();
   JOptionPane.showMessageDialog(null, "Erreur lors de la modification du solde.", "Erreur", JOptionPane.ERROR_MESSAGE);
}
}

    
    
    
    
}