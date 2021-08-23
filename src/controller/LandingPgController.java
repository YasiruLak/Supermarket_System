package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

public class LandingPgController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public AnchorPane loginContextOne;
    public Label lblDate;
    public Label lblTime;
    public Label lblDate1;
    public Label lblDate11;
    public FontAwesomeIconView btnExit;
    private int hour;
    public AnchorPane loginContext;

    public void initialize(){
        loadDateAndTime();
    }
    public void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            String state = null;
            hour = currentTime.getHour();
            if (hour < 12) {
                state = "AM";
            } else {
                state = "PM";
            }
            lblTime.setText(
                    currentTime.getHour()+ ": "+currentTime.getMinute()+ ": "+currentTime.getSecond()+ " " +state
            );
        }),
                new KeyFrame(Duration.seconds(1))
                );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void logInOnAction(ActionEvent actionEvent) throws IOException {

        if (txtUserName.getText().equals("Admin")) {
            URL resource = getClass().getResource("../view/AdminPg.fxml");
            Parent load = FXMLLoader.load(resource);
            loginContextOne.getChildren().clear();
            loginContextOne.getChildren().add(load);
        }else if (txtUserName.getText().equals("Cashier")){
            URL resource = getClass().getResource("../view/CashierPg.fxml");
            Parent load = FXMLLoader.load(resource);
            loginContextOne.getChildren().clear();
            loginContextOne.getChildren().add(load);
        }else{
            new Alert(Alert.AlertType.WARNING, "User Name or Password Incorrect", ButtonType.CLOSE).show();
        }

    }

    public void closeWindow(ActionEvent actionEvent) throws InterruptedException {
        Thread.sleep(5);
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING,"Are you suer you want to Log out?",yes,no);
        alert.setTitle("Confirmation alert");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(no)==yes){
            Platform.exit();
            System.exit(0);
        }else{

        }
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/LandingPg.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) loginContext.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void logOut(ActionEvent actionEvent) {
        Stage close = (Stage) btnExit.getScene().getWindow();
        close.close();

        try {
            Thread.sleep(5);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("| SUPER MARKET | ");
            alert.setHeaderText(null);
            alert.setContentText("Good Bye..! Welcome Back...!");
            alert.showAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
