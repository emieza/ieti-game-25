package com.enricmieza.game25;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen1 implements Screen {
    final Main game;
    private Stage stage;
    private Skin skin;

    public GameScreen1(Main game) {
        this.game = game;
        // Crear un Stage i un Skin
        stage = new Stage(game.viewport);
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Carregar un Skin per defecte

        // Crear un Label (TextView)
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(); // Font per defecte
        Label label = new Label("Hola, això és un TextView", labelStyle);
        label.setPosition(200, 100); // Posició del Label

        // Crear un Button
        Button button = new TextButton("Clica'm!", skin);
        button.setPosition(120 ,300); // Posició del Button
        button.setSize(200, 100); // Mida del Button
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Botó clicat!");
                Gdx.input.vibrate(100);
                game.setScreen(new GameScreen2(game));
                dispose();
            }
        });

        // Afegir els actors al Stage
        stage.addActor(label);
        stage.addActor(button);

        // Configurar l'Stage com a gestor d'entrada
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        render1(delta);
    }
    public void render1(float delta) {
        ScreenUtils.clear(Color.GREEN);

        // Actualitzar i dibuixar l'Stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //game.viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
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
