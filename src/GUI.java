import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private JTextField regUsernameField, regEmailField, loginUsernameField;
    private JPasswordField regPasswordField, loginPasswordField;
    private JLabel dashboardLabel;

    // contractor
    public GUI() {
        setTitle("Swing Auth");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // init views
        createRegisterPanel();
        createLoginPanel();
        createDashboardPanel();

        add(cardPanel);
        cardLayout.show(cardPanel, "Login");
    }

    // register panel
    private void createRegisterPanel() {
        JPanel panel = new JPanel(null);

        // username
        JLabel usernameLabel = new JLabel("Lietotājvārds: ");
        usernameLabel.setBounds(50, 20, 80, 25);
        panel.add(usernameLabel);

        regUsernameField = new JTextField();
        regUsernameField.setBounds(140, 20, 200, 25);
        panel.add(regUsernameField);

        // email
        JLabel emailLabel = new JLabel("E-pasts: ");
        emailLabel.setBounds(50, 60, 80, 25);
        panel.add(emailLabel);

        regEmailField = new JTextField();
        regEmailField.setBounds(140, 60, 200, 25);
        panel.add(regEmailField);

        // password
        JLabel passwordLabel = new JLabel("Parole: ");
        passwordLabel.setBounds(50, 100, 80, 25);
        panel.add(passwordLabel);

        regPasswordField = new JPasswordField();
        regPasswordField.setBounds(140, 100, 200, 25);
        panel.add(regPasswordField);


        // register btn
        JButton registerButton = new JButton("Reģistrēties");
        registerButton.setBounds(140, 140, 100, 25);
        registerButton.addActionListener(e -> registerUser());
        panel.add(registerButton);

        // back btn
        JButton backToLoginButton = new JButton("Atpakaļ uz pieteikšanos");
        backToLoginButton.setBounds(140, 180, 180, 25);
        backToLoginButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));

        panel.add(backToLoginButton);

        cardPanel.add(panel, "Registration");

    }

    // login panel
    private void createLoginPanel() {
        JPanel panel = new JPanel(null);

        // username
        JLabel usernameLabel = new JLabel("Lietotājvārds: ");
        usernameLabel.setBounds(50, 40, 80, 25);
        panel.add(usernameLabel);

        loginUsernameField = new JTextField();
        loginUsernameField.setBounds(140, 40, 200, 25);
        panel.add(loginUsernameField);

        // password
        JLabel passwordLabel = new JLabel("Parole: ");
        passwordLabel.setBounds(50, 80, 80, 25);
        panel.add(passwordLabel);

        loginPasswordField = new JPasswordField();
        loginPasswordField.setBounds(140, 80, 200, 25);
        panel.add(loginPasswordField);

        // login btn
        JButton loginButton = new JButton("Pieteikties");
        loginButton.setBounds(140, 120, 100, 25);
        loginButton.addActionListener(e -> loginUser());
        panel.add(loginButton);

        // go to register view
        JButton goToRegisterButton = new JButton("Uz reģistrāciju");
        goToRegisterButton.setBounds(140, 160, 100, 25);
        goToRegisterButton.addActionListener(e -> cardLayout.show(cardPanel, "Registration"));
        panel.add(goToRegisterButton);

        cardPanel.add(panel, "Login");

    }

    // dashboard panel
    private void createDashboardPanel() {
        JPanel panel = new JPanel();

        dashboardLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        dashboardLabel.setBounds(50, 50, 300, 25);
        panel.add(dashboardLabel);

        JButton logoutButton = new JButton("Izrakstīties");
        logoutButton.setBounds(150, 100, 100, 25);
        logoutButton.addActionListener(e -> logout());
        panel.add(logoutButton);

        cardPanel.add(panel, "Dashboard");
    }

    // user register
    private void registerUser() {

        String username = regUsernameField.getText();
        String email = regEmailField.getText();
        String password = new String(regPasswordField.getPassword());

        // validate
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lūdzu aizpildiet visus laukus", "Reģistrācījas kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // try to register
        if (UserManager.register(username, email, password)) {
            JOptionPane.showMessageDialog(this, "Reģistrācija veiksmīga!", "Veiksmīgi", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "Login");
        } else {
            JOptionPane.showMessageDialog(this, "Reģistrācija neizdevās!", "Kļūda", JOptionPane.ERROR_MESSAGE);

        }


    }

    // login func
    private void loginUser() {

        String username = loginUsernameField.getText();
        String password = new String(loginPasswordField.getPassword());

        // validate
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lūdzu, ievadiet gan lietotājvārdu, gan paroli", "Pieteikšanās kļūda", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println("Mēģina pieteikties lietotājs ar vārdu: " + username);

        // try to login
        if (UserManager.login(username, password)) {
            System.out.println("Pieteikšanās veiksmīga lietotājam: " + username);
            dashboardLabel.setText("Laipni lūgti informācījas panelī: " + username + "!");
            cardLayout.show(cardPanel, "Dashboard");
        } else {
            System.out.println("Pieteikšanās neizdevās lietotājam: " + username);
            JOptionPane.showMessageDialog(this, "Nepareizs lietotājvārd vai parole", "Pieteikšanās kļūda", JOptionPane.ERROR_MESSAGE);
        }
    }

    // logout
    private void logout() {
        loginUsernameField.setText("");
        loginPasswordField.setText("");
        cardLayout.show(cardPanel, "Login");
    }
}

