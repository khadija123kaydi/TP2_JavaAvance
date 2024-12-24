package DAO;

import java.sql.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.*;

public class HolidayDAOimplement implements GenericDAOI<Holiday> {

    private Connect c;
    public EmployeeDAOimplement emp1=new EmployeeDAOimplement();

    public HolidayDAOimplement() {
        this.c = new Connect();
    }
    
    
    EmployeeDAOimplement emp=new EmployeeDAOimplement();
    
//////////////Ajouter holiday /////////////////////
    
    @Override
    public void addGeneric(Holiday holiday) {
        String sql = "INSERT INTO holiday(date_debut,date_fin,Type,employe_id) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement st = c.getConnect().prepareStatement(sql)) {
       
            st.setDate(1, Date.valueOf(holiday.getDateDebut()));
            st.setDate(2, Date.valueOf(holiday.getDateFin()));
            st.setString(3, holiday.getType().name());
            st.setInt(4, holiday.getId_employe());
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Congé ajouté avec succès !");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"erreur dans l'ajout d'un holiday","erreur",JOptionPane.ERROR_MESSAGE);
        }
    }
    

    
    
    //////////Modifier Holiday//////////////
    
    @Override
    public void modifyGeneric(Holiday holiday) {
        String sql = "UPDATE Holiday SET date_debut = ?, date_fin = ?, Type = ?, employe_id = ? WHERE id = ?";
        try (PreparedStatement st = c.getConnect().prepareStatement(sql)) {
            st.setDate(1, Date.valueOf(holiday.getDateDebut()));
            st.setDate(2, Date.valueOf(holiday.getDateFin()));
            st.setString(3, holiday.getType().name());
            st.setInt(4, holiday.getId_employe());
            st.setInt(5, holiday.getId());
            int rowsAffected = st.executeUpdate();
            JOptionPane.showMessageDialog(null,"Holiday est modifier aavec succe!!","message",JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
            System.err.println("Error updating holiday: " + e.getMessage());
        }
    }

    
    
   ////////// deletee holiday////////////////
    
    
    @Override
    public void deleteGeneric(int id) {
        String sql = "DELETE FROM Holiday WHERE id = ?";
        try (PreparedStatement st = c.getConnect().prepareStatement(sql)) {
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            JOptionPane.showMessageDialog(null,"Holiday est supprimer avec succe !!","message",JOptionPane.INFORMATION_MESSAGE);;
        } catch (SQLException e) {
            System.err.println("Error deleting holiday: " + e.getMessage());
        }
    }

    
   /////////// jointure entre Holiday et employe /////////
    @Override
    public List<Object[]> getAllGeneric() {
        List<Object[]> infos = new ArrayList<>();
        String sql = "SELECT h.id, h.date_debut, h.date_fin, h.Type, h.employe_id, e.solde, e.nom " +
                     "FROM Holiday h JOIN Employees e ON h.employe_id = e.id";

        try (PreparedStatement st = c.getConnect().prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                infos.add(new Object[] {
                    rs.getInt("id"),               
                    rs.getDate("date_debut"),       
                    rs.getDate("date_fin"),        
                    rs.getString("Type"),         
                    rs.getDouble("solde"),        
                    rs.getString("nom")             
                });
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des congés : " + e.getMessage());
        }

        return infos;
    }

    
   /////////// selectionne les ids des employes////////// 
    
    public List<String> FindById() {
        List<String> namesWithId = new ArrayList<>();
        String query = "SELECT nom, prenom, id FROM employees"; 

        try (PreparedStatement ps = c.getConnect().prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String fullName = rs.getString("nom") + " " + rs.getString("prenom");
                int id = rs.getInt("id");
                namesWithId.add(id + " - " + fullName); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return namesWithId; 
    }

    

  //// Verifier si un holiday est existe//////////////////
    
    public boolean allHoliday(int id) {
        String query = "SELECT * FROM holiday WHERE id = ?";
        
        try (PreparedStatement ps = c.getConnect().prepareStatement(query)) {
            ps.setInt(1, id);  
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la vérification de l'ID de congé : " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    
    
    
    
    
   public double soldeActuel(int id) {
	  return  emp1.SoldeActuel(id);
   }
   
   
   public void ModifierSolde(int id , double soldeDemande) {
	   emp1.ModifierSolde(id, soldeDemande);
   }
    
   
   
   public int getOverlapDays(int employeId, LocalDate newStartDate, LocalDate newEndDate) {
	    String query = "SELECT date_debut, date_fin FROM holiday WHERE employe_id = ?";
	    
	    try (PreparedStatement ps = c.getConnect().prepareStatement(query)) {
	        ps.setInt(1, employeId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                LocalDate existingStartDate = rs.getDate("date_debut").toLocalDate();
	                LocalDate existingEndDate = rs.getDate("date_fin").toLocalDate();

	                // Cas 1 : Le nouveau congé est exactement identique à l'existant
	                if (newStartDate.isEqual(existingStartDate) && newEndDate.isEqual(existingEndDate)) {
	                    return 0;
	                }

	                // Cas 2 : Le nouveau congé est complètement inclus dans un congé existant
	                if (!newStartDate.isBefore(existingStartDate) && !newEndDate.isAfter(existingEndDate)) {
	                    return 0;
	                }

	                // Cas 3 : Le nouveau congé s'étend au-delà de la fin d'un congé existant
	                if (newStartDate.isBefore(existingEndDate) && newEndDate.isAfter(existingEndDate)) {
	                    long additionalDays = ChronoUnit.DAYS.between(existingEndDate, newEndDate);
	                    return (int) additionalDays;
	                }

	                // Cas 4 : Les congés ne se chevauchent pas
	                if (newEndDate.isBefore(existingStartDate) || newStartDate.isAfter(existingEndDate)) {
	                    continue; // Pas de chevauchement
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de la vérification des chevauchements : " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    // Aucun chevauchement trouvé
	    return -1;
	}

}
