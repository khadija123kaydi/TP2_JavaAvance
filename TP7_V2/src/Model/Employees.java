package Model;
public class Employees {
	private String nom,prenom,email;
	String telephone;
	private double salaire;
	Role role;
	Poste poste;
	private int id;
	public  static int balance=25;
	
	public Employees(int id,String nom, String prenom, String email, String telephone, double salaire, Role role,
			Poste poste) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.salaire = salaire;
		this.role = role;
		this.poste = poste;
	}
	
	
	public Employees(String nom, String prenom, String email, String telephone, double salaire, Role role,
			Poste poste) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.salaire = salaire;
		this.role = role;
		this.poste = poste;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}

	public double getSalaire() {
		return salaire;
	}
	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}
	public String getRole() {
		return role.name();
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getPoste() {
		return poste.name();
	}
	public void setPoste(Poste poste) {
		this.poste = poste;
	}
	public int getId() {
		return id;
	}


	public static int getBalance() {
		return balance;
	}


	public static void setBalance(int balance) {
		Employees.balance = balance;
	}
	
	
	

	
}
	

