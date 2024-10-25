import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import acsse.csc03a3.Blockchain;
import acsse.csc03a3.Transaction;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VoterGUI extends Application{
	private Stage primaryStage;
	private StackPane root;
	private Blockchain<String> blockchain;
	private String email = "";
	private String password = "";
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		 // Initialize
	    this.primaryStage = primaryStage;

	    // Set the title 
	    this.primaryStage.setTitle("User");

	    // Initialize root
	    root = new StackPane();
	    
	    blockchain = new Blockchain<>();
	    
	    registerStakesAutomatically(); // Register stakes
	    
	    //Set the root 
	    Scene scene = new Scene(root, 800, 700);
	    primaryStage.setScene(scene);

	    // Display login page
	    loginPage(primaryStage, root);

	    primaryStage.show();
	}
	
	 private void registerStakesAutomatically() {
	        blockchain.registerStake("Node1", 100);
	    }
	 
	protected void loginPage(Stage primaryStage, StackPane root) {
        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
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

        // Add action for login button
        btnLogin.setOnAction(e -> {
        		email = userTextField.getText();
        		password = pwBox.getText();
         
            if (authenticateUser(email, password)) {
                // Navigate to Add Election page
            	
            	selectElection();
            	
            } else {
            	System.out.println(authenticateUser(email, password));
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password. Please try again.");
            }
        });
        
        btnRegister.setOnAction(e -> {
        	registerPage(primaryStage, root);
        });

        root.getChildren().clear();
        root.getChildren().add(grid);
	}
	
	// Method to authenticate user
	private boolean authenticateUser(String email, String password) {
		    String path = "data/Users.txt";

		    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		        String line;
		        while ((line = br.readLine()) != null) {
		           
		            String[] userInfo = line.split(",");

		           
		            String storedEmail = userInfo[2].trim().replace("Email: ", "");
		            String storedPassword = userInfo[5].trim().replace("Password: ", "");

		           
		            if (email.trim().equals(storedEmail) && password.trim().equals(storedPassword)) {
		                return true;
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return false;
		}
	
	
	//Display register page
	protected void registerPage(Stage primaryStage, StackPane root) {		    
			primaryStage.setTitle("User Registration");

	        // Create a GridPane 
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

	        // Add action for register button
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

	               
	                String path = "data/Users.txt";

	                try {
	                   
	                    PrintWriter pw = new PrintWriter(new FileWriter(path, true));

	                    
	                    pw.println(userInfo);

	                   
	                    pw.close();

	                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "User has been registered successfully!");
	                    //Navigate to login page
	                    loginPage(primaryStage, root);

	                } catch (IOException ex) {
	                    // Handle any IOException that may occur during file writing
	                    System.err.println("Error writing user information to the file: " + ex.getMessage());
	                    showAlert(Alert.AlertType.ERROR, "File Error", "Error writing user information to the file.");
	                }
	            }
	        });
	        
	        //  Button
	        Button loginButton = new Button("Go to Login Page");
	        grid.add(loginButton, 1, 7);

	        loginButton.setOnAction(e -> {
	        	loginPage(primaryStage, root);
	        	
	        });

	        root.getChildren().clear();
	        root.getChildren().add(grid);
		}
		

	//Select election page
	private void selectElection() {
	    primaryStage.setTitle("Election Selection");

	  
	    ObservableList<String> elections = readElectionsFromFile(); // Implement this method to read elections from file

	    // Create a GridPane 
	    GridPane grid = new GridPane();
	    grid.setAlignment(Pos.CENTER);
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(25, 25, 25, 25));

	    // Title
	    Label title = new Label("Select an Election");
	    grid.add(title, 0, 0, 2, 1);

	    //  Election List
	    ListView<String> electionListView = new ListView<>(elections);
	    electionListView.setPrefSize(600, 500);
	    grid.add(electionListView, 0, 1, 2, 1);

	    // Add Select Button
	    Button selectButton = new Button("Select");
	    grid.add(selectButton, 0, 2);

	    //select button
	    selectButton.setOnAction(e -> {
	        String selectedElection = electionListView.getSelectionModel().getSelectedItem();
	        if (selectedElection != null) {
	            System.out.println("Selected Election: " + selectedElection);
	            // Navigate to selectCandidatePage() with the selected election
	            selectCandidatePage(selectedElection);
	        } else {
	            System.out.println("Please select an election.");
	        }
	    });

	    Scene scene = new Scene(grid, 800, 700);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
	
	private ObservableList<String> readElectionsFromFile() {
	    ObservableList<String> elections = FXCollections.observableArrayList();

	    // Path to the file containing elections
	    String filePath = "data/Elections.txt";

	    // Read elections from the file
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            // Add each election to the list
	            elections.add(line);
	        }
	    } catch (IOException e) {
	        // Handle any IOException that may occur
	        e.printStackTrace();
	    }

	    return elections;
	}

	// Method to read candidates from the candidates.txt file for the selected election
    private ObservableList<String> readCandidatesForElection(String selectedElection) {
        ObservableList<String> candidates = FXCollections.observableArrayList();
        try (BufferedReader brCandidates = new BufferedReader(new FileReader("data/Candidates.txt"));
             BufferedReader brElections = new BufferedReader(new FileReader("data/Elections.txt"))) {

            String selectedElectionName = null;
            String selectedElectionDate = null;

            // Find the selected election's name and date
            String line;
            while ((line = brElections.readLine()) != null) {
                if (line.contains(selectedElection)) {
                    String[] electionInfo = line.split(",");
                    selectedElectionName = electionInfo[0].trim().replace("Election Name: ", "");
                    selectedElectionDate = electionInfo[1].trim().replace("Election Date: ", "");
                    break;
                }
            }

            // Read candidates for the selected election
            if (selectedElectionName != null && selectedElectionDate != null) {
                String candidateLine;
                while ((candidateLine = brCandidates.readLine()) != null) {
                    if (candidateLine.contains(selectedElectionName) && candidateLine.contains(selectedElectionDate)) {
                        String[] candidateInfo = candidateLine.split(",");
                        String candidateName = candidateInfo[0].trim().replace("Name: ", "");
                        String candidateSurname = candidateInfo[1].trim().replace("Surname: ", "");
                        String partyAffiliation = candidateInfo[4].trim().replace("Party Affiliation: ", "");
                        candidates.add(candidateName + " " + candidateSurname + " (" + partyAffiliation + ")");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    // Method to display the select candidate page
    public void selectCandidatePage(String selectedElection) {
        primaryStage.setTitle("Select Candidate");

        // Read candidates for the selected election from candidates.txt
        ObservableList<String> candidates = readCandidatesForElection(selectedElection);

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add Title
        Label title = new Label("Select a Candidate to Vote For");
        grid.add(title, 0, 0, 2, 1);

        // Add Radio Buttons for Candidates
        ToggleGroup candidateGroup = new ToggleGroup();
        int row = 1;
        for (String candidate : candidates) {
            RadioButton radioButton = new RadioButton(candidate);
            radioButton.setToggleGroup(candidateGroup);
            grid.add(radioButton, 0, row);
            row++;
        }

        // Add Cast Vote Button
        Button castVoteButton = new Button("Cast Vote");
        grid.add(castVoteButton, 0, row);
        
        // Add Cast Vote Button
        Button goToVoteHistory = new Button("Go To Vote History");
        grid.add(goToVoteHistory, 1, row);

     // Add action for cast vote button
        castVoteButton.setOnAction(e -> {
            RadioButton selectedRadioButton = (RadioButton) candidateGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String selectedCandidate = selectedRadioButton.getText();
                System.out.println("Voted for: " + selectedCandidate);
                
                //Write votes to file
                writeVote(selectedCandidate, selectedElection, getUserName(email, password)); 
                
                // Write the vote to the blockchain
                writeVoteToBlockchain(selectedCandidate, selectedElection);
                writeBlockchainToFile();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Vote casted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a candidate.");
            }
        });
        
        goToVoteHistory.setOnAction(e->{
        	displayVoteHistory(getUserName(email, password));
        });
        
        // Show the scene
        Scene scene = new Scene(grid, 800, 700);
	    primaryStage.setScene(scene);
	    primaryStage.show();
    }
    
    private void writeBlockchainToFile() {
        String blockchainFilePath = "data/Blockchain.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(blockchainFilePath))) {
            pw.println(blockchain.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
    private void displayVoteHistory(String voterName) {
        primaryStage.setTitle("Vote History");

        // Read vote history for the specified voter from the Votes.txt file
        ObservableList<String> voteHistory = readVoteHistoryForVoter(voterName);

        // Create a GridPane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add Title
        Label title = new Label("Vote History for " + voterName);
        grid.add(title, 0, 0, 2, 1);

        // Add Vote History List
        ListView<String> voteHistoryListView = new ListView<>(voteHistory);
        voteHistoryListView.setPrefSize(600, 500);
        grid.add(voteHistoryListView, 0, 1, 2, 1);

        // Add Compare Votes Button
        Button compareVotesButton = new Button("Compare with Blockchain");
        grid.add(compareVotesButton, 0, 2);

        // Add action for compare votes button
        compareVotesButton.setOnAction(e -> {
            compareVotesWithBlockchain(voteHistory);
        });

        // Show the scene
        Scene scene = new Scene(grid, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<String> readVoteHistoryForVoter(String voterName) {
        ObservableList<String> voteHistory = FXCollections.observableArrayList();

        // Path to the file containing vote history
        String filePath = "data/Votes.txt";

        // Read vote history from the file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Check if the vote belongs to the specified voter
                if (line.contains("Voter: " + voterName)) {
                    // Add the vote to the vote history list
                    voteHistory.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return voteHistory;
    }

    private void compareVotesWithBlockchain(ObservableList<String> voteHistory) {
        // Convert the vote history to a list of strings
        List<String> votes = new ArrayList<>(voteHistory);

        // Read blockchain data from the Blockchain.txt file
        List<String> blockchainData = readBlockchainFromFile();

        // Check if the blockchain data is not empty
        if (blockchainData != null && !blockchainData.isEmpty()) {
            // Extract relevant transaction data from blockchain data
            List<String> blockchainTransactions = extractTransactionsFromBlockchain(blockchainData);

            // Check if the extracted transactions match the vote history
            if (blockchainTransactions.equals(votes)) {
                showAlert(Alert.AlertType.INFORMATION, "Vote Integrity", "The vote history matches the blockchain data.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Vote Integrity", "The vote history does not match the blockchain data.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Blockchain Error", "Failed to read blockchain data.");
        }
    }

    
    private List<String> readBlockchainFromFile() {
        
        String filePath = "data/Blockchain.txt";
        List<String> blockchainData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
               
                blockchainData.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return blockchainData;
    }
    
    private List<String> extractTransactionsFromBlockchain(List<String> blockchainData) {
        List<String> transactions = new ArrayList<>();

        // Iterate over each block in the blockchain data
        for (String block : blockchainData) {
            // Extract transactions from the block and add them to the list
            String[] parts = block.split(", ");
            for (String part : parts) {
                if (part.startsWith("Voter:")) {
                    transactions.add(part.trim());
                }
            }
        }

        return transactions;
    }
    
    // Method to write  to the blockchain
    private void writeVoteToBlockchain(String selectedCandidate, String selectedElection) {
        String voter = getUserName(email, password);
        if (voter != null) {
            String transactionData = "Voter: " + voter + ", Election: " + selectedElection + ", Candidate: " + selectedCandidate;
            Transaction<String> transaction = new Transaction<>("", "", transactionData);
            List<Transaction<String>> transactions = new ArrayList<>();
            transactions.add(transaction);
            blockchain.addBlock(transactions);
        } else {
            System.out.println("Authentication failed!");
        }
    }
    
    //Gtting the name
    private String getUserName(String email, String password) {
        
        if (authenticateUser(email, password)) {
            String path = "data/Users.txt";

            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    
                    String[] userInfo = line.split(",");

                 
                    String storedEmail = userInfo[2].trim().replace("Email: ", "");
                    String storedPassword = userInfo[5].trim().replace("Password: ", "");
                    String name = userInfo[0].trim().replace("Name: ", ""); // Assuming name is the first field

                   
                    if (email.trim().equals(storedEmail) && password.trim().equals(storedPassword)) {
                        return name; 
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; 
    }

      //Writing the votes
    private void writeVote(String selectedCandidate, String selectedElection, String voterName) {
        String timeStamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        String voteData = "Election: " + selectedElection + ", Candidate: " + selectedCandidate + ", Voter: " + voterName + ", Time: " + timeStamp;

        try (PrintWriter pw = new PrintWriter(new FileWriter("data/Votes.txt", true))) {
            pw.println(voteData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // show an alert
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
       
	
	public static void main(String[] args) {
		launch(args);
	}

}
