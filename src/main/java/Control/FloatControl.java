package Control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * @author xiaosongChen
 * @create 2022-10-21 21:19
 * @description :
 */
public class FloatControl extends AbstractControl {
    float tmp = 0;
    boolean raise = true;

    float dist;// 上下浮动的距离
    float speed;// 浮动的速度

    public FloatControl() {
        this.dist = 0.5f;
        this.speed = 1f;
    }

    public FloatControl(float dist, float speed) {
        this.dist = dist;
        this.speed = speed;
    }

    @Override
    protected void controlUpdate(float v) {
        if (speed == 0)
            return;

        float delta = v * speed;
        if (tmp < dist && raise) {
            tmp += delta;
            spatial.move(0, delta, 0);
        } else {
            raise = false;
        }
        if (tmp > -dist && !raise) {
            tmp -= delta;
            spatial.move(0, -delta, 0);
        } else {
            raise = true;
        }
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
