package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author xiaosongChen
 * @create 2022-11-04 11:33
 * @description :
 */
public class HelloController {
    @FXML
    private Button butaction;
    @FXML
    void onbutton(ActionEvent event){
        System.out.println("I am button");
    }

}
