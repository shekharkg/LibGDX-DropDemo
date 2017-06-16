package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MainMenuScreen;

public class MyGdxGame extends Game {

  public SpriteBatch batch;
  public BitmapFont font;
  private Music rainMusic;

  @Override
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont();
    this.setScreen(new MainMenuScreen(this));

    //Set the rain music in loop and play
    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    rainMusic.setLooping(true);
    rainMusic.play();
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    batch.dispose();
    font.dispose();
    rainMusic.dispose();
  }
}