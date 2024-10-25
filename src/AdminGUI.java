import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import acsse.csc03a3.Blockchain;
import acsse.csc03a3.Transaction;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminGUI extends Application{
	
	//Attributes
	private Stage primaryStage;
	private StackPane root;
	private Blockchain<String> blockchain;
	
	@Override
    public void start(Stage primaryStage) throws Exception {
		 // Initialize primaryStage
	    this.primaryStage = primaryStage;

	    // Set the title
	    this.primaryStage.setTitle("Admin");

	    // Initialize StackPane
	    root = new StackPane();
	    
	    //Instantiate the blockchain
	    blockchain = new Blockchain<>(); 
	    
	    //Register stakes 
	    registerStakesAutomatically(); 
	    
	    //Set the root 
	    Scene scene = new Scene(root, 800, 700);
	    primaryStage.setScene(scene);
	    
	   

	    // Display login page
	    loginPage(primaryStage, root);
	    
	    //Show the stage
	    primaryStage.show();
	   
	}
	
	
	protected void loginPage(Stage primaryStage, StackPane root) {
        // Create a GridPane
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add Title
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // Add Username Label
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        // Add Username TextField
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        // Add Password Label
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        // Add Password Field
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        // Add Login Button
        Button btnLogin = new Button("Sign in");
        grid.add(btnLogin, 0, 4);
        
        // Add Register Button
        Button btnRegister = new Button("Go to Register Page");
        grid.add(btnRegister, 1, 4);

        // Add login for login button
        btnLogin.setOnAction(e -> {
        	String email = userTextField.getText();
            String password = pwBox.getText();

            if (authenticateUser(email, password)) {
                // Navigate to Add Election page
            	
            	addElectionPage();
            	
            } else {
            	System.out.println(authenticateUser(email, password));
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password. Please try again.");
            }
        });
        
        // Add Register button
        btnRegister.setOnAction(e -> {
        	registerPage(primaryStage, root);
        });
        
        //Get the root children
        root.getChildren().clear();
        root.getChildren().add(grid);
	}
	
	// Method to authenticating the user
	private boolean authenticateUser(String email, String password) {
	    String path = "data/Users.txt";

	    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            // Split the line into fields based on comma separation
	            String[] userInfo = line.split(",");

	            // Extract email and password directly from userInfo array
	            String storedEmail = userInfo[2].trim().replace("Email: ", "");
	            String storedPassword = userInfo[5].trim().replace("Password: ", "");

	            // Check if the provided email and password match the stored values
	            if (email.trim().equals(storedEmail) && password.trim().equals(storedPassword)) {
	                return true;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	//Method for validating users
	private boolean validateUser(String name, String surname, String email, String phone, String id) {
	    String path = "data/Users.txt";

	    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            // Split the line into fields based on comma separation
	            String[] userInfo = line.split(",");

	            // Extract user information
	            String storedName = userInfo[0].trim().replace("Name: ", "");
	            String storedSurname = userInfo[1].trim().replace("Surname: ", "");
	            String storedEmail = userInfo[2].trim().replace("Email: ", "");
	            String storedPhone = userInfo[3].trim().replace("Phone: ", "");
	            String storedID = userInfo[4].trim().replace("ID: ", "");

	            // Check if the provided information matches the stored values
	            if (name.trim().equals(storedName) && surname.trim().equals(storedSurname) &&
	                email.trim().equals(storedEmail) && phone.trim().equals(storedPhone) &&
	                id.trim().equals(storedID)) {
	                // User found, return true
	                return true;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // User not found
	    return false;
	}

	
	//Method for registering stakes
	 private void registerStakesAutomatically() {
		 blockchain.registerStake("Node1", 100);
		 blockchain.registerStake("Node2", 150);
	    }
	
	 
	 // Display register page
	protected void registerPage(Stage primaryStage, StackPane root) {		    
		primaryStage.setTitle("User Registration");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add Title
        Text scenetitle = new Text("User Registration");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // Add Name Label
        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 1);

        // Add Name TextField
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        // Add Surname Label
        Label surnameLabel = new Label("Surname:");
        grid.add(surnameLabel, 0, 2);

        // Add Surname TextField
        TextField surnameTextField = new TextField();
        grid.add(surnameTextField, 1, 2);

        // Add Email Label
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 3);

        // Add Email TextField
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 3);

        // Add Phone Number Label
        Label phoneLabel = new Label("Phone Number:");
        grid.add(phoneLabel, 0, 4);

        // Add Phone Number TextField
        TextField phoneTextField = new TextField();
        grid.add(phoneTextField, 1, 4);

        // Add ID Number Label
        Label idLabel = new Label("ID Number:");
        grid.add(idLabel, 0, 5);

        // Add ID Number TextField
        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 5);

        // Add Password Label
        Label pwLabel = new Label("Password:");
        grid.add(pwLabel, 0, 6);

        // Add Password Field
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 6);

        // Add Register Button
        Button registerButton = new Button("Register");
        grid.add(registerButton, 0, 7);

        // Add register button event
        registerButton.setOnAction(e -> {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String email = emailTextField.getText();
            String phone = phoneTextField.getText();
            String id = idTextField.getText();
            String password = pwBox.getText();

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || id.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Registration Error", "All fields are required.");
            } else {
                String userInfo = "Name: " + name + ", Surname: " + surname + ", Email: " + email + ", Phone: " + phone + ", ID: " + id + ", Password: " + password;

                // The file path
                String path = "data/Users.txt";

                try {
                    //Write to file
                    PrintWriter pw = new PrintWriter(new FileWriter(path, true));
                    pw.println(userInfo);

                    pw.close();

                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User has been registered successfully!");
                    //Navigate to login page
                    loginPage(primaryStage, root);

                } catch (IOException ex) {
                    System.err.println("Error writing user information to the file: " + ex.getMessage());
                    showAlert(Alert.AlertType.ERROR, "File Error", "Error writing user information to the file.");
                }
            }
        });
        
        // Add Login Button
        Button loginButton = new Button("Go to Login Page");
        grid.add(loginButton, 1, 7);

        loginButton.setOnAction(e -> {
        	loginPage(primaryStage, root);
        	
        });

        root.getChildren().clear();
        root.getChildren().add(grid);
	}
	
	//Write blockchain to file
	 private void writeBlockchainToFile() {
	        String blockchainFilePath = "data/ResultsBlock.txt"; // Adjust the file path as needed
	        try (PrintWriter pw = new PrintWriter(new FileWriter(blockchainFilePath))) {
	            pw.println(blockchain.toString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	// Method to show alerts
	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	//Information page
	private void infoPage() {
		primaryStage.setTitle("Voter Information & Validation");

        // Create a GridPane
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add Name Label
        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 1);

        // Add Name TextField
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        // Add Surname Label
        Label surnameLabel = new Label("Surname:");
        grid.add(surnameLabel, 0, 2);

        // Add Surname TextField
        TextField surnameTextField = new TextField();
        grid.add(surnameTextField, 1, 2);

        // Add Email Label
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 3);

        // Add Email TextField
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 3);

        // Add Phone Number Label
        Label phoneLabel = new Label("Phone Number:");
        grid.add(phoneLabel, 0, 4);

        // Add Phone Number TextField
        TextField phoneTextField = new TextField();
        grid.add(phoneTextField, 1, 4);

        // Add ID Number Label
        Label idLabel = new Label("ID Number:");
        grid.add(idLabel, 0, 5);

        // Add ID Number TextField
        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 5);

        // Add Validate Button
        Button validateButton = new Button("Validate");
        grid.add(validateButton, 0, 6);
        
        Button goToResultsPage = new Button("Go To Results Page");
        grid.add(goToResultsPage, 1, 6);

        // Add action for validate button
        validateButton.setOnAction(e -> {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String email = emailTextField.getText();
            String phone = phoneTextField.getText();
            String id = idTextField.getText();

            // Validatation
            if (validateUser(name, surname, email, phone, id)) {
               
                showAlert(Alert.AlertType.INFORMATION, "User Found", "User information validated successfully.");
            } else {
               
                showAlert(Alert.AlertType.ERROR, "User Not Found", "User information not found.");
            }
        });
        
        //Navigate to results page
        goToResultsPage.setOnAction(e->{
        	resultsPage();
        });

        //Setting the scene
        Scene scene = new Scene(grid, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	
	//Results page
	public void resultsPage() {
        primaryStage.setTitle("Election Results");

        // Create a GridPane
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add Election Selection Label
        Label electionLabel = new Label("Select Election:");
        grid.add(electionLabel, 0, 0);

        // Add Election Selection ComboBox
        ComboBox<String> electionComboBox = new ComboBox<>();
        // Populate electionComboBox with available elections
        populateElectionComboBox(electionComboBox); // You need to implement this method
        grid.add(electionComboBox, 1, 0);

        // Add Show Results Button
        Button showResultsButton = new Button("Show Results");
        grid.add(showResultsButton, 0, 1, 2, 1);

        // Add action for show results button
        showResultsButton.setOnAction(e -> {
            String selectedElection = electionComboBox.getValue();
            if (selectedElection != null && !selectedElection.isEmpty()) {
                // Retrieve results
                Map<String, Integer> candidateVotes = getResultsForElection(selectedElection); // You need to implement this method

                // Display results
                writeBlockchainToFile();
                displayResults(grid, candidateVotes);
            } else {
                showAlert(Alert.AlertType.ERROR, "No Election Selected", "Please select an election.");
            }
        });

        Scene scene = new Scene(grid, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
	
	// Method to populate the election
    private void populateElectionComboBox(ComboBox<String> comboBox) {
        
        try (BufferedReader br = new BufferedReader(new FileReader("data/Elections.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming each line contains only the election name
                comboBox.getItems().add(line);
            }
        } catch (IOException ex) {
           
            ex.printStackTrace();
        }
    }

	
	
 // Method to display results
    private void displayResults(GridPane grid, Map<String, Integer> candidateVotes) {
        
        grid.getChildren().clear();

        // Results Graph
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Election Results");
        xAxis.setLabel("Candidates");
        yAxis.setLabel("Votes");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChart.getData().add(series);

        grid.add(barChart, 0, 0);

        // Go Back Button
        Button goBackButton = new Button("Go Back");
        grid.add(goBackButton, 0, 1);

        //back button
        goBackButton.setOnAction(e -> primaryStage.close());

        // Transactions for the blockchain
        List<Transaction<String>> transactions = createTransactions(candidateVotes);

        // Add blocks
        blockchain.addBlock(transactions);
    }
	
	
 // Method to retrieve results
    private Map<String, Integer> getResultsForElection(String electionName) {
        
        Map<String, Integer> candidateVotes = new HashMap<>();

        // Read data 
        try (BufferedReader br = new BufferedReader(new FileReader("data/Votes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                if (line.contains("Election Name:") && line.contains(electionName)) {
                  
                    Pattern pattern = Pattern.compile("Candidate: (.*?),");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String candidateName = matcher.group(1).trim();

                       
                        candidateVotes.put(candidateName, candidateVotes.getOrDefault(candidateName, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return candidateVotes;
    }
	
    
 // Creation of  transactions
    private List<Transaction<String>> createTransactions(Map<String, Integer> candidateVotes) {
        List<Transaction<String>> transactions = new ArrayList<>(); 
        for (Map.Entry<String, Integer> entry : candidateVotes.entrySet()) {
            Transaction<String> transaction = new Transaction<>("Election Results", entry.getKey(), entry.getValue() + " votes");
            transactions.add(transaction);
        }

        return transactions;
    }

    //Add candinate page
	private void addCandidatePage() {
		primaryStage.setTitle("Add Candidate");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add Name Label
        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 1);

        // Add Name TextField
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        // Add Surname Label
        Label surnameLabel = new Label("Surname:");
        grid.add(surnameLabel, 0, 2);

        // Add Surname TextField
        TextField surnameTextField = new TextField();
        grid.add(surnameTextField, 1, 2);

        // Add Email Label
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 3);

        // Add Email TextField
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 3);

        // Add Phone Number Label
        Label phoneLabel = new Label("Phone Number:");
        grid.add(phoneLabel, 0, 4);

        // Add Phone Number TextField
        TextField phoneTextField = new TextField();
        grid.add(phoneTextField, 1, 4);

        // Add Party Affiliation Label
        Label partyLabel = new Label("Party Affiliation:");
        grid.add(partyLabel, 0, 5);

        // Add Party Affiliation TextField
        TextField partyTextField = new TextField();
        grid.add(partyTextField, 1, 5);
        
        //Add Add Candidate Button
        Button btnAddCandidate = new Button("Add Candidate");
        grid.add(btnAddCandidate, 0, 7);
        
        //Add Add Candidate Button
        Button btnGoToVoterInformation = new Button("Go to voter information");
        grid.add(btnGoToVoterInformation, 1, 7);
        
       
     // Read existing elections
     ComboBox<String> electionComboBox = new ComboBox<>();
    
     try (BufferedReader br = new BufferedReader(new FileReader("data/Elections.txt"))) {
         String line;
         while ((line = br.readLine()) != null) {
             
             electionComboBox.getItems().add(line);
         }
     } catch (IOException ex) {
        
         ex.printStackTrace();
     }
     
    
     Label electionLabel = new Label("Election: ");

     grid.add(electionLabel, 0, 6);
     grid.add(electionComboBox, 1, 6);

     //add button
     btnAddCandidate.setOnAction(e -> {
         String name = nameTextField.getText();
         String surname = surnameTextField.getText();
         String email = emailTextField.getText();
         String phone = phoneTextField.getText();
         String party = partyTextField.getText();
         String selectedElection = electionComboBox.getValue(); // Get selected election from ComboBox

         //validation
         if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() || party.isEmpty() || selectedElection == null) {
             showAlert(Alert.AlertType.ERROR, "Invalid Information", "Please fill in all fields and select an election.");
         } else {
       
             String candidateInfo = "Name: " + name + ", Surname: " + surname + ", Email: " + email + ", Phone: " + phone + ", Party Affiliation: " + party + ", Election: " + selectedElection;

          
             String path = "data/Candidates.txt";

             try {
              
                 PrintWriter pw = new PrintWriter(new FileWriter(path, true));

                
                 pw.println(candidateInfo);

               
                 pw.close();

                 showAlert(Alert.AlertType.INFORMATION, "Candidate Added", "Candidate added successfully!");

             } catch (IOException ex) {
                 // Handle any IOException that may occur during file writing
                 System.err.println("Error writing candidate information to the file: " + ex.getMessage());
                 showAlert(Alert.AlertType.ERROR, "File Error", "Error writing candidate information to the file.");
             }
         }
        });
        
        btnGoToVoterInformation.setOnAction(e->{
        	infoPage();
        });
            

        Scene scene = new Scene(grid, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	

	private void addElectionPage() {
	 	primaryStage.setTitle("Election Management");

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add Election Name Label
        Label nameLabel = new Label("Election Name:");
        grid.add(nameLabel, 0, 1);

        // Add Election Name TextField
        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        // Add Election Date Label
        Label dateLabel = new Label("Election Date:");
        grid.add(dateLabel, 0, 2);

        // Add Election Date DatePicker
        DatePicker datePicker = new DatePicker();
        grid.add(datePicker, 1, 2);

        // Add Create Election Button
        Button createElection = new Button("Create Election");
        grid.add(createElection, 0, 3);

        // Add action for create button
        createElection.setOnAction(e -> {
            String name = nameTextField.getText();
            LocalDate date = datePicker.getValue();

            if (name.isEmpty() || date == null) {
                showAlert(Alert.AlertType.ERROR, "Invalid Information", "Please fill in all fields.");
            } else {
              
                String electionInfo = "Election Name: " + name + ", Election Date: " + date;

               
                String path = "data/Elections.txt";

                try {
                
                    PrintWriter pw = new PrintWriter(new FileWriter(path, true));

               
                    pw.println(electionInfo);

                  
                    pw.close();

                    showAlert(Alert.AlertType.INFORMATION, "Election Created", "Election created successfully!");

                } catch (IOException ex) {             
                    System.err.println("Error writing election information to the file: " + ex.getMessage());
                    showAlert(Alert.AlertType.ERROR, "File Error", "Error writing election information to the file.");
                }
            }     
        });
        
        Button btnAddCandidate = new Button("Go to Add Candidate Page");
        grid.add(btnAddCandidate, 1, 3);
        
        btnAddCandidate.setOnAction(e->{
        	addCandidatePage();
        });

        Scene scene = new Scene(grid, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}