package com.mygdx.game;

/**
 * Created by Tiago Neves on 21/04/2017.
 */

        import com.badlogic.gdx.ApplicationListener;
        import com.badlogic.gdx.Gdx;
        import com.badlogic.gdx.Input;
        import com.badlogic.gdx.InputProcessor;
        import com.badlogic.gdx.ScreenAdapter;
        import com.badlogic.gdx.graphics.GL20;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.scenes.scene2d.Stage;
        import com.badlogic.gdx.scenes.scene2d.ui.Image;
        import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;
        import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
        import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
        import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
        import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * The Menu screen.
 */
public class MenuScreen extends ScreenAdapter{
    private final SpaceBallsGame game;
    private final ImageButton startButton;
    private final ImageButton exitButton;
    private final ImageButton howtoplay;
    private final ImageButton sandbox;
    private final ImageButton credits;
    private final ImageButton title;
    private final ImageButton options;
    private final Stage menuStage;
    private final MenuBallStage MenuBallStage;



    float width = Gdx.graphics.getWidth();
    float heigth = Gdx.graphics.getHeight();


    /**
     *
     * @param game the game
     */
    MenuScreen(SpaceBallsGame game) {

        this.game = game;
        this.menuStage = new Stage();
        this.MenuBallStage = new MenuBallStage(this.game);
        menuStage.setViewport(new FitViewport(width, heigth));

        game.getAssetManager().load("credits.png", Texture.class);
        game.getAssetManager().load("play.png", Texture.class);
        game.getAssetManager().load("title.png", Texture.class);
        game.getAssetManager().load("howtoplay.png", Texture.class);
        game.getAssetManager().load("sandbox.png", Texture.class);
        game.getAssetManager().load("Exit.png", Texture.class);
        game.getAssetManager().load("howtoplay.png", Texture.class);
        game.getAssetManager().load("options.png", Texture.class);
        game.getAssetManager().finishLoading();

        // Sets the stage as its input processor
        Gdx.input.setInputProcessor(menuStage);

//        float buttonXSize = 0.3f/PIXEL_TO_METER;
        float buttonYSize =heigth/10;

        float spacing = (Gdx.graphics.getHeight() - buttonYSize*3)/6;

        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("play.png")));
        startButton = new ImageButton(buttonDrawable);
        startButton.setSize(width/5,buttonYSize*3);
        startButton.setPosition(width/2 - width/10,heigth/2+buttonYSize);
        menuStage.addActor(startButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("Exit.png")));
        exitButton = new ImageButton(buttonDrawable);
        exitButton.setSize(width/10,buttonYSize);
        exitButton.setPosition(width/2 - width/20,spacing);
        menuStage.addActor(exitButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("title.png")));
        title = new ImageButton(buttonDrawable);
        title.setSize(width/2,buttonYSize*1.1f);
        title.setPosition(width/2 - width/4,heigth - buttonYSize*1.5f);
        menuStage.addActor(title);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("howtoplay.png")));
        howtoplay = new ImageButton(buttonDrawable);
        howtoplay.setSize(width/4,buttonYSize);
        howtoplay.setPosition(width/2 - width/8,spacing*4 - buttonYSize);
        menuStage.addActor(howtoplay);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("options.png")));
        options = new ImageButton(buttonDrawable);
        options.setSize(width/20,heigth/10);
        options.setPosition(width - width/20,heigth-heigth/10);
        menuStage.addActor(options);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("sandbox.png")));
        sandbox = new ImageButton(buttonDrawable);
        sandbox.setSize(width/5,buttonYSize);
        sandbox.setPosition(width/2 - width/10,spacing*5 - buttonYSize);
        menuStage.addActor(sandbox);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("credits.png")));
        credits = new ImageButton(buttonDrawable);
        credits.setSize(width/6,buttonYSize);
        credits.setPosition(width/2 - width/12,spacing*3 - buttonYSize);
        menuStage.addActor(credits);

        Gdx.input.setCatchBackKey(true);


    }

    /**
     * Renders the screen
     *
     * @param delta time since last rendered in seconds
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor( 0f, 0f, 0f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        if(startButton.isPressed()){
            game.setScreen(new GameScreen(game));

        }

        if(exitButton.isPressed()) {
            Gdx.app.exit();
        }

        if(options.isPressed()) {
            game.setScreen(new OptionsScreen(game));
        }






        // Steps the stage
        menuStage.act(delta);
        MenuBallStage.act(delta);

        // Draws the stage
        MenuBallStage.draw();
        menuStage.draw();




    }

    /**
     * Resize the stage viewport when the screen is resized
     *
     * @param width the new screen width
     * @param height the new screen height
     */
    @Override
    public void resize(int width, int height) {
       menuStage.getViewport().update(width, height, true);
        MenuBallStage.getViewport().update(width,height, true);
    }


}