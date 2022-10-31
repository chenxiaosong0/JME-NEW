package LemurTest;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.BorderLayout.Position;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import com.simsilica.lemur.style.Styles;

/**
 * @author xiaosongChen
 * @create 2022-10-22 21:51
 * @description :
 */
public class BorderLayout extends SimpleApplication {
    public static void main(String[] args) {
        BorderLayout layout = new BorderLayout();
        layout.start();
    }
    @Override
    public void simpleInitApp() {
        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        Styles styles = GuiGlobals.getInstance().getStyles();
        styles.getSelector( "header", "glass" ).set( "background",
                new QuadBackgroundComponent(new ColorRGBA(0, 0.75f, 0.75f, 0.5f)) );
        styles.getSelector( "header", "glass" ).set( "shadowColor",
                new ColorRGBA(1, 0f, 0f, 1) );

        Container container = new Container("glass");
        container.setLocalTranslation(0,cam.getHeight(),0);
        container.addChild(new Label("Top" ,new ElementId("header"),"glass"));//通过ElementID设置背景
        container.addChild( new Panel( 2, 2, ColorRGBA.Cyan, "glass" ) ).setUserData( LayerComparator.LAYER, 2 );//高亮度的下划线
        container.addChild(new Label("Bottom" ));
        container.setBackground(new QuadBackgroundComponent(new ColorRGBA(0,0.5f,0.5f,0.5f),5,5, 0.02f, false));//背景设置

        Checkbox tools = container.addChild(new Checkbox("Tools"));
        tools.setColor(ColorRGBA.Yellow);
        tools.setChecked(false);
        guiNode.attachChild(container);


    }

}
