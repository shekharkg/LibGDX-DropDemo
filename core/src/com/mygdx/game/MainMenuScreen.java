package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;


/**
 * Created by shekhar on 6/12/17.
 */

public class MainMenuScreen implements Screen {

  private final MyGdxGame game;
  private OrthographicCamera camera;

  private Texture playImage;
  private Texture dropImage;

  MainMenuScreen(final MyGdxGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    playImage = new Texture(Gdx.files.internal("play.png"));
    dropImage = new Texture(Gdx.files.internal("droplet.png"));
  }


  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    camera.update();

    game.batch.begin();
    game.batch.draw(playImage, camera.position.x - (playImage.getWidth() / 2),
        camera.position.y - (playImage.getHeight() / 2));
    game.batch.end();

    if (Gdx.input.isTouched()) {
      game.setScreen(new GameScreen(game));
      dispose();
    }
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    playImage.dispose();
  }

}
