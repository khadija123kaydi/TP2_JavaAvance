package Model;

import java.sql.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import DAO.EmployeeDAOimplement;
import DAO.HolidayDAOimplement;

public class HolidayModel {
    private HolidayDAOimplement dao;

    
    
    public HolidayModel(HolidayDAOimplement dao) {
        this.dao= dao;
       
    }

    
    
    // ajouter
    
    public void addHoliday(LocalDate dateDebut, LocalDate dateFin, TypeH type, int id_employe) {
        try {
            if (ChronoUnit.DAYS.between(dateDebut, dateFin) < 0) {
                JOptionPane.showMessageDialog(null, "Erreur : la date de fin doit être après la date de début.");
                return;
            }
            
            long differenceInDays = ChronoUnit.DAYS.between(dateDebut, dateFin) ; // Inclure le dernier jour
            
//            if (dao.soldeActuel(id_employe) < differenceInDays) {
//                JOptionPane.showMessageDialog(null, "Le nombre de jours de congé est insuffisant !");
//                return;
//            }

            int overlapDays = dao.getOverlapDays(id_employe, dateDebut, dateFin);
            
            if (overlapDays == 0) {
                System.out.println("Cas 1 : L'intervalle existe déjà.");
                JOptionPane.showMessageDialog(null, "L'intervalle de congé existe déjà !");
                return;
            }

            if (overlapDays != -1) {
                if (dao.soldeActuel(id_employe) < overlapDays) {
                    JOptionPane.showMessageDialog(null, "Le nombre de jours de congé est insuffisant !");
                    return;
                }

                System.out.println("Cas 2: Aucun chevauchement trouvé.");

            	System.out.println(" nombre de jours est :"+overlapDays);
                Holiday holiday = new Holiday(dateDebut, dateFin, type, id_employe, null);
                
                if (dao.soldeActuel(id_employe) < overlapDays) {
                    JOptionPane.showMessageDialog(null, "Le nombre de jours de congé est insuffisant !");
                    return;
                }
            	 dao.addGeneric(holiday);
                 dao.ModifierSolde(id_employe, overlapDays); 
		} else  {
				if (dao.soldeActuel(id_employe) < differenceInDays) {
					JOptionPane.showMessageDialog(null, "Le nombre de jours de congé est insuffisant !");
					return;
				}

                System.out.println("Cas 3 : Aucun chevauchement trouvé.");
                Holiday holiday = new Holiday(dateDebut, dateFin, type, id_employe, null);
                dao.addGeneric(holiday);
                dao.ModifierSolde(id_employe, differenceInDays); 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur inattendue lors de l'ajout du congé.");
            e.printStackTrace();
        }
    }

    	
    	
    	
    	
    	
    	
    
    
    
    // modifier 
    
    
    public void modifyHoliday(int id, LocalDate dateDebut, LocalDate dateFin, TypeH  type,int id_employe) {
        Holiday holiday = new Holiday(id, dateDebut, dateFin,type,id_employe,null);
        
        if(!dao.allHoliday(id)) {
        	JOptionPane.showMessageDialog(null, "Conge n'existe pas ");
        	return;
        }
        
        
        long differenceInDays = ChronoUnit.DAYS.between(dateDebut, dateFin) + 1; 
        
        if (dao.soldeActuel(id_employe) < differenceInDays) {
            JOptionPane.showMessageDialog(null, "Le nombre de jours de congé est insuffisant !");
            return;
        }

        // Vérifier si l'intervalle existe déjà (renvoie 0 si l'intervalle existe déjà exactement)
        int overlapDays = dao.getOverlapDays(id_employe, dateDebut, dateFin);
        if (overlapDays == 0) {
            System.out.println("Cas 1 : L'intervalle existe déjà.");
            JOptionPane.showMessageDialog(null, "L'intervalle de congé existe déjà !");
            return;
        }

        if (overlapDays != -1) {
        	System.out.println(" nombre de jours est :"+overlapDays);
        	System.out.println("solde actuel est :"+dao.soldeActuel(id_employe));
            System.out.println("Cas 3 : Aucun chevauchement trouvé.");

        	
            if (dao.soldeActuel(id_employe) < overlapDays) {
                JOptionPane.showMessageDialog(null, "Le nombre de jours de congé est insuffisant !");
                return;
            }
            dao.modifyGeneric(holiday);
            dao.ModifierSolde(id_employe, overlapDays); 
	} else  {
            System.out.println("Cas 3 : Aucun chevauchement trouvé.");
            dao.modifyGeneric(holiday);
            dao.ModifierSolde(id_employe, differenceInDays); 
        }
      
    }
    
    
    
    
    // delete

    public void deleteHoliday(int id) {
        dao.deleteGeneric(id);
    }
    
    
    
    // afficher
    
    public Object[][] getAllHolidays() {
        List<Object[]> holidays = dao.getAllGeneric();  
        Object[][] holidayData = new Object[holidays.size()][5]; 

        for (int i = 0; i < holidays.size(); i++) {
            Object[] row = holidays.get(i);  
            holidayData[i][0] = row[0]; 
            holidayData[i][1] = row[1];  
            holidayData[i][2] = row[2];  
            holidayData[i][3] = row[3];  
            holidayData[i][4] = row[5];  
        }

        return holidayData; 
    }

    
   
    
    
    
 
    
       public List<String> getIds(){
      return  dao.FindById();
   }
       
  
    
    public  int differentInDays(LocalDate dateFin, LocalDate dateDebut) {
        return (int) ChronoUnit.DAYS.between(dateDebut, dateFin);
    }
    
    
    
    
    ////////////// Intervale de date///////////////////
    
    
    
    public int getOverlapDays(int employeId, LocalDate newStartDate, LocalDate newEndDate) {
    	return dao.getOverlapDays(employeId,newStartDate,newEndDate);
    }
    
}



