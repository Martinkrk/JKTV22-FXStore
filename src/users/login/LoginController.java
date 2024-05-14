package users.login;

import entities.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javax.persistence.EntityManager;
import jktv22.fxstore.JKTV22FXStore;
import tools.PassEncrypt;

public class LoginController implements Initializable {
    private EntityManager em;
    @FXML private TextField tf_Login;
    @FXML private PasswordField pf_Password;
    @FXML private Label l_Info;
    @FXML private Button b_Login;
    
    @FXML void authenticate() {
        PassEncrypt pe = new PassEncrypt();
        try {
            User user = (User) em.createQuery("SELECT u FROM User u WHERE u.login = :login")
                    .setParameter("login", tf_Login.getText())
                    .getSingleResult();
            if(user.getPassword().equals(pe.getEncryptPassword(pf_Password.getText(), pe.getSalt()))){
                if(l_Info.getStyleClass().contains("error-text")){
                    l_Info.getStyleClass().remove("error-text");
                }
                if(!l_Info.getStyleClass().contains("info-text")){
                    l_Info.getStyleClass().add("info-text");
                }
                l_Info.setText(String.format("Hello, %s, welcome!", user.getLogin()));
                jktv22.fxstore.JKTV22FXStore.user = user;
                tf_Login.setText("");
                pf_Password.setText("");
            }else{
                throw new Exception();
            }
            
        } catch (Exception e) {
            if(!l_Info.getStyleClass().contains("error-text")){
                    l_Info.getStyleClass().add("error-text");
                }
            if(!l_Info.getStyleClass().contains("info-text")){
                l_Info.getStyleClass().remove("info-text");
            }
            l_Info.setText("No such user or incorrect password");
            tf_Login.setText("");
            pf_Password.setText("");
        }
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public TextField getTf_Login() {
        return tf_Login;
    }

    public void setTf_Login(TextField tf_Login) {
        this.tf_Login = tf_Login;
    }

    public PasswordField getPf_Password() {
        return pf_Password;
    }

    public void setPf_Password(PasswordField pf_Password) {
        this.pf_Password = pf_Password;
    }

    public Label getL_Info() {
        return l_Info;
    }

    public void setL_Info(Label l_Info) {
        this.l_Info = l_Info;
    }

    public Button getB_Login() {
        return b_Login;
    }

    public void setB_Login(Button b_Login) {
        this.b_Login = b_Login;
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
    public void setInfo(String message){
        this.l_Info.setText(message);
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Обработчик события для TextField
        pf_Password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                b_Login.fire();
            }
        });

        // Обработчик события для Button
        b_Login.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               b_Login.fire();
            }
        });

    }     
}
