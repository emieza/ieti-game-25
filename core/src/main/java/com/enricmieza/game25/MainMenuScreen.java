package com.enricmieza.game25;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;


public class MainMenuScreen implements Screen {

    final Main game;

    public class MyInputAdapter extends InputAdapter {
        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            System.out.println("Touch up en: " + screenX + ", " + screenY);
            Gdx.input.vibrate(100);
            game.setScreen(new GameScreen1(game));
            dispose();
            return true;
        }
    }
    public MainMenuScreen(final Main game) {
        this.game = game;
        Gdx.input.setInputProcessor(new MyInputAdapter());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        //draw text. Remember that x and y are in meters
        game.font.draw(game.batch, "Welcome to Game!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.end();

        // input processor per sortir amb touchUp
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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

    }

}
