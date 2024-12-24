package Model;

import java.awt.Window.Type;
import java.time.LocalDate;

public class Holiday {

    private int id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private TypeH type;
    private String type_h;
    private String nom_employe;
    private int id_employe;

    public Holiday(LocalDate dateDebut, LocalDate dateFin, TypeH type,int id_employe, String nom) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.id_employe = id_employe;
        this.nom_employe=nom;
    }

    

    public Holiday(int id,LocalDate dateDebut, LocalDate dateFin, TypeH type,int id_employe, String nom) {
    	this.id=id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.id_employe = id_employe;
        this.nom_employe=nom;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public TypeH getType() {
        return type;
    }



    public int getId_employe() {
		return id_employe;
	}

	public void setId_employe(int id_employe) {
		this.id_employe = id_employe;
	}

	public String getNom_employe() {
		return nom_employe;
	}

	public void setNom_employe(String nom_employe) {
		this.nom_employe = nom_employe;
	}
	
}
