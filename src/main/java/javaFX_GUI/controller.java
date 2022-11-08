package javaFX_GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author xiaosongChen
 * @create 2022-11-06 16:37
 * @description :
 */
public class controller {

    @FXML private Label label1;
    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private void reactToClick(){
        button1.setText(" clicked.....");
        label1.setText("Button1 clicked....");
        System.out.println("clicked");
    }
    @FXML private  void Button(){
        label1.setText("Button2 clicked....");
        button2.setText("Button2 Clicked..");
        System.out.println("Button2 Clicked..");
    }
}
