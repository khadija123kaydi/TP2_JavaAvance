package View;

import Controller.EmployeeController;

import Controller.HolidayController;
import Model.EmployeeModel;
import Model.HolidayModel;
import DAO.EmployeeDAOimplement;
import DAO.HolidayDAOimplement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionEmployesConges extends JFrame {

	public  EmployeeController employeeController;
    private JTabbedPane tabbedPane;

    public GestionEmployesConges() {
        this.setSize(800, 600);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Gestion Employés et Congés");

        tabbedPane = new JTabbedPane();

        initializeEmployeeManagement();
        initializeHolidayManagement();

        add(tabbedPane);
    }

    private void initializeEmployeeManagement() {
        EmployeeDAOimplement employeeDAO = new EmployeeDAOimplement();
        EmployeeModel employeeModel = new EmployeeModel(employeeDAO);
        EmployeesView employeeView = new EmployeesView();

       employeeController = new EmployeeController(employeeView, employeeModel);
       employeeController.afficher();
        tabbedPane.addTab("Employés", employeeView.getPan());
    }

    
    private void initializeHolidayManagement() {
        HolidayDAOimplement holidayDAO = new HolidayDAOimplement();
        HolidayModel holidayModel = new HolidayModel(holidayDAO);
        HolidayView holidayView = new HolidayView();
        HolidayController holidayController = new HolidayController(holidayView, holidayModel);
        holidayController.afficher();
        tabbedPane.addTab("Congés", holidayView.getpan());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}

class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginView() {
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Login");
        this.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Nom d'utilisateur:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Se connecter");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });

        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(new JLabel());  
        this.add(loginButton);
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if ("admin".equals(username) && "password123".equals(password)) {
            this.setVisible(false); 
            GestionEmployesConges mainApp = new GestionEmployesConges();
            mainApp.setVisible(true); 
        
            
   
        } else {
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
