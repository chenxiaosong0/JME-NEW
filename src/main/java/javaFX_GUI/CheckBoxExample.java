package javaFX_GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author xiaosongChen
 * @create 2022-11-07 22:10
 * @description :
 */
public class CheckBoxExample extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Tooltip Sample");
        primaryStage.setWidth(300);
        primaryStage.setHeight(150);
        CheckBox checkBox = new CheckBox("CheckBox");
        checkBox.setAllowIndeterminate(true);//3个状态：选中，取消选择和未定义。
        //在鼠标悬停节点时在场景中显示关于节点的附加信息。
        Tooltip tooltip = new Tooltip("$ tooltip");
        tooltip.setFont(new Font("Arial",16));
        checkBox.setTooltip(tooltip);
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println(checkBox.isSelected());
            }
        });
        root.getChildren().add(checkBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
