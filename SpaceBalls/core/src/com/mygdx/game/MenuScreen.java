package com.mygdx.game;

/**
 * Created by Tiago Neves on 21/04/2017.
 */

        import com.badlogic.gdx.Gdx;
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
public class MenuScreen extends ScreenAdapter {
    private final SpaceBallsGame game;
    private final ImageButton startButton;
    private final ImageButton exitButton;
    //private final ImageButton options;
    private final ImageButton howtoplay;
//    private final ImageButton sandbox;
//    private final ImageButton credits;
    private final Stage menuStage;
    private final GameStage gameStage;
    private final Skin skinbutton;

    static final float PIXEL_TO_METER = 0.22f / 200;


    /**
     *
     * @param game the game
     */
    MenuScreen(SpaceBallsGame game) {

        this.game = game;
        this.menuStage = new Stage();
        this.gameStage = new GameStage(this.game);
        this.skinbutton = new Skin(Gdx.files.internal("SkinMainMenu/glassy-ui.json"));
        menuStage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        game.getAssetManager().load("credits.png", Texture.class);
        game.getAssetManager().load("play.png", Texture.class);
        game.getAssetManager().load("title.png", Texture.class);
        game.getAssetManager().load("howtoplay.png", Texture.class);
        game.getAssetManager().load("sandbox.png", Texture.class);
        game.getAssetManager().load("Exit.png", Texture.class);
        game.getAssetManager().load("howtoplay.png", Texture.class);
        game.getAssetManager().finishLoading();

        // Sets the stage as its input processor
        Gdx.input.setInputProcessor(menuStage);

        float buttonXSize = 0.3f/PIXEL_TO_METER;
        float buttonYSize = 0.3f/PIXEL_TO_METER;

        float spacing = (Gdx.graphics.getHeight() - buttonYSize*3)/4;

        Drawable buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("play.png")));
        startButton = new ImageButton(buttonDrawable);
        startButton.setSize(buttonXSize,buttonYSize);
        startButton.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        menuStage.addActor(startButton);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("Exit.png")));
        exitButton = new ImageButton(buttonDrawable);
        exitButton.setSize(buttonXSize,buttonYSize);
        exitButton.setPosition(0.5f/PIXEL_TO_METER,spacing);
        menuStage.addActor(exitButton);

//        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("options.png")));
//        options = new ImageButton(buttonDrawable);
//        options.setSize(buttonXSize,buttonYSize);
//        options.setPosition(0.5f/PIXEL_TO_METER,spacing*2 + buttonYSize);
//        menuStage.addActor(options);

        buttonDrawable = new TextureRegionDrawable(new TextureRegion((Texture)game.getAssetManager().get("howtoplay.png")));
        howtoplay = new ImageButton(buttonDrawable);
        howtoplay.setSize(buttonXSize,buttonYSize);
        howtoplay.setPosition(0.5f/PIXEL_TO_METER,spacing*2 + buttonYSize);
        menuStage.addActor(howtoplay);

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
            System.out.print("exit\n");
            Gdx.app.exit();
        }

//        if(options.isPressed()) {
//            System.out.print("options");
//        }


        // Steps the stage
        menuStage.act(delta);
        gameStage.act(delta);

        // Draws the stage
        gameStage.draw();
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
        gameStage.getViewport().update(width,height, true);
    }
}