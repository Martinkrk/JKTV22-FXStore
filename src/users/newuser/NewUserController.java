package users.newuser;

import entities.User;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import jktv22.fxstore.HomeController;
import tools.PassEncrypt;


public class NewUserController implements Initializable {
    
    private HomeController homeController;
    
    @FXML private Button addNewUser;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;

    
    @FXML private void clickAddNewUser(){
        if(tfLogin.getText().isEmpty() || tfPassword.getText().isEmpty()){
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("infoError");
            homeController.getLbInfo().setText("Fill in all input fields");
            return;
        }
        User user = new User();
        user.setLogin(tfLogin.getText());
        PassEncrypt pe = new PassEncrypt();
        try {
            user.setPassword(pe.getEncryptPassword(tfPassword.getText(),pe.getSalt()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(NewUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        user.getRoles().add(jktv22.fxstore.JKTV22FXStore.ROLES.CUSTOMER.toString());
        try {
            homeController.getApp().getEntityManager().getTransaction().begin();
            homeController.getApp().getEntityManager().persist(user);
            homeController.getApp().getEntityManager().getTransaction().commit();
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("info");
            homeController.getLbInfo().setText("User is successfully added");
            tfLogin.setText("");
            tfPassword.setText("");
        } catch (Exception e) {
            homeController.getLbInfo().getStyleClass().clear();
            homeController.getLbInfo().getStyleClass().add("infoError");
            homeController.getLbInfo().setText("Error while creating an user");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    } 
    
}
