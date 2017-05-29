package com.mygdx.game.tests;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.LevelType;
import com.mygdx.game.controller.LevelController;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.controller.OptionsController;
import com.mygdx.game.controller.SandBoxController;
import com.mygdx.game.model.SandBoxModel;
import com.mygdx.game.model.entities.BallModel;
import com.mygdx.game.model.entities.EnemyModel;
import com.mygdx.game.model.entities.EntityModel;
import com.mygdx.game.model.entities.StaticModel;
import com.mygdx.game.view.SandBoxView;
import com.mygdx.game.view.entities.EntityView;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        controller.setOffset(0.1f,0.1f);
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


    @Test
    public void menuController(){
        float x = 0;
        float x1 = 0;
        float x2 = 0;
        MenuController controller = MenuController.getInstance();
        Array<Body> bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x = body.getPosition().x;
            if(((EntityModel) body.getUserData()) instanceof StaticModel)
                assertEquals(body.getPosition().x,0,0);

        }
        assertTrue(bodies.size == 11);

        controller.setAccelY(1);
        controller.setAccelX(1);
        controller.update(0.1f);

        bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x1 = body.getPosition().x;
        }

        assertTrue(x != x1);


        controller.setSensitivity(0);
        controller.setOffset(0.1f,0.1f);
        controller.update(0.1f);
        bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x2 = body.getPosition().x;
        }

        assertTrue(x1 != x2);
    }

    @Test
    public void optionsTest(){
        float x = 0;
        float x1 = 0;
        float x2 = 0;
        OptionsController.getInstance().delete();
        OptionsController controller;
        controller = OptionsController.getInstance();
        controller.update(0.01f);
        Array<Body> bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  EnemyModel){
                x = body.getPosition().x;
                System.out.println(x);
            }

        }



        controller.setAccelX(-1);
        controller.setAccelY(-1);
        controller.setSensitivity(7);
        controller.update(0.01f);



        bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof EnemyModel){
                x1 = body.getPosition().x;
                System.out.println(x1);
            }

        }

        assertTrue(x != x1);


        for (int i = 0; i < 20; i++) {
            controller.setOffsetXY(controller.getReadingXY());
            controller.update(0.1f);
        }

        bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  EnemyModel){
                x2 = body.getPosition().x;
                System.out.println(x2);
                System.out.println(controller.getReadingXY()[0]);
            }

        }


        assertTrue(x == x2);


    }

    @Test
    public void levelTest(){
        LevelController.getInstance(LevelType.levelType.ONE).delete();
        LevelController controller = LevelController.getInstance(LevelType.levelType.ONE);
        boolean collided = false;
        while(!collided){
            controller.update(0.25f);
            if(controller.isColliding())
                collided = true;
        }
        controller.setColliding(false);
        assertFalse(controller.getWin());
        assertTrue(collided);
    }


    @Test
    public void ballMoves(){
        LevelController.getInstance(LevelType.levelType.ONE).delete();
        LevelController controller = LevelController.getInstance(LevelType.levelType.ONE);
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
    public void setGet2(){
        LevelController.getInstance(LevelType.levelType.ONE).delete();
        LevelController controller = LevelController.getInstance(LevelType.levelType.ONE);
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

        LevelController.getInstance(LevelType.levelType.ONE).delete();
        controller = LevelController.getInstance(LevelType.levelType.ONE);

        bodies = new Array<Body>();
        controller.getWorld().getBodies(bodies);
        for (Body body : bodies) {
            if(((EntityModel) body.getUserData()) instanceof  BallModel)
                x = body.getPosition().x;
        }
        controller.setSensitivity(1f);
        controller.setOffset(0.1f,0.1f);
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
