package com.jobsavelsberg.paperplane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by s153640 on 28-2-2017.
 */
public enum Font {
    small("fonts/BebasNeue.otf",40),
    medium("fonts/BebasNeue.otf",100),
    big("fonts/BebasNeue.otf",180);

    private BitmapFont f = null;

    Font(String path, int i) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = i;
        f = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
    }

    public BitmapFont get() {
        return this.f;
    }
}
