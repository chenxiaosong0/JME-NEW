package GUI;

import com.jme3.app.SimpleApplication;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.BaseStyles;

/**
 * @author xiaosongChen
 * @create 2022-10-20 10:42
 * @description :
 */
public class MyGUI extends SimpleApplication {
    public static void main(String[] args) {
        MyGUI gui = new MyGUI();
        gui.start();
    }
    @Override
    public void simpleInitApp() {
        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        Container myWindow = new Container();


        myWindow.setLocalTranslation(100,300,0);
        myWindow.addChild(new Label("xxx  www"));
        Button click = myWindow.addChild(new Button("aaa"));
        click.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button button) {
                System.out.println("width: " + cam.getWidth() + ",height:" + cam.getHeight());
            }
        });
        guiNode.attachChild(myWindow);
    }

}
