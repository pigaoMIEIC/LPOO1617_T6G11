package com.mygdx.game.tests;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.view.SandBoxView;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Tiago Neves on 28/05/2017.
 */

public class Tests {

    @Test
    public void testBallDead(){
        SandBoxController.getInstance().delete();
        SandBoxController controller = SandBoxController.getInstance();
        boolean collided = false;
        while(!collided){
            controller.update(0.25f);
            if(controller.isColliding())
                collided = true;
            if(controller.getSeconds()>5)
                assertTrue(false);
        }
        controller.setColliding(false);
        assertTrue(collided);
    }

    @Test
    public void ballMovesRight(){
        SandBoxController.getInstance().delete();
        SandBoxController controller = SandBoxController.getInstance();
        float x = 0;
        Array<Body> bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x = body.getPosition().x;
        }
        controller.accelerate(1,1);
        controller.update(0.1f);
        controller.getWorld().getBodies(bodies);

        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                assertTrue(body.getPosition().x > x);
        }
    }

    @Test
    public void nextLevel(){
        SandBoxController.getInstance().delete();
        SandBoxController controller = SandBoxController.getInstance();
        SandBoxModel model = SandBoxModel.getInstance();
        Array<Body> bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        System.out.println(bodies.size);
        assertTrue(bodies.size == 3);
        model.nextLevel();
        bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        assertTrue(bodies.size == 4);
    }

    @Test
    public void setGet(){
        SandBoxController.getInstance().delete();
        SandBoxController controller = SandBoxController.getInstance();
        float x = 0;
        float x1 = 0;
        float x2 = 0;
        Array<Body> bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x = body.getPosition().x;
        }
        controller.setSensitivity(0.01f);
        controller.accelerate(1,1);
        controller.update(0.1f);
        controller.getWorld().getBodies(bodies);

        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x1 = - x - body.getPosition().x;
        }

        SandBoxController.getInstance().delete();
        controller = SandBoxController.getInstance();

        bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x = body.getPosition().x;
        }
        controller.setSensitivity(1f);
        controller.setOffsetY(0.1f);
        controller.setOffsetX(0.1f);
        controller.setJoystick(true);
        controller.accelerate(1,1);
        controller.update(0.1f);
        controller.getWorld().getBodies(bodies);

        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x2 = - x - body.getPosition().x;
        }

        assertTrue(x1 > x2);
    }




}
