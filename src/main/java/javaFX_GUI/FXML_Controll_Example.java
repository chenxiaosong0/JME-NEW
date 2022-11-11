package javaFX_GUI;

import Control.HelloControl;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.IfNode;

/**
 * @author xiaosongChen
 * @create 2022-11-08 22:08
 * @description :
 */
public class FXML_Controll_Example {
    @FXML
    private AnchorPane Center;
    private void printmes(AnchorPane pane){
        if (pane !=  null){
            double centerWidth = pane.getPrefWidth();
            double centerHeight = pane.getPrefHeight();
            System.out.println(pane.getId() + " Width: " +  centerWidth + " Height: " + centerHeight);
        }else {
            System.out.println("获取不到Center!");
        }
    }


    @FXML private void  Center_Click(){
//        System.out.println( "width: " + centerWidth);
//        System.out.println("height: " + centerHeight);
//        System.out.println("Center_Clicked");
        printmes(Center);

    };
}
