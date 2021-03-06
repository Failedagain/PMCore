package graphic;

import static org.mockito.ArgumentMatchers.anyFloat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Frustum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import textures.TextureMap;
import tools.Point;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Painter.class, TextureMap.class})
public class PainterTest {
    Painter painter;
    SpriteBatch batch;
    DungeonCamera cam;
    Frustum frustum;

    @Before
    public void setUp() {
        cam = Mockito.mock(DungeonCamera.class);
        batch = Mockito.mock(SpriteBatch.class);
        painter = Mockito.spy(new Painter(cam));
        frustum = Mockito.mock(Frustum.class);
        Mockito.when(cam.isPointInFrustum(anyFloat(), anyFloat())).thenReturn(true);
    }

    @Test
    public void test_draw_1() throws Exception {
        PowerMockito.whenNew(Sprite.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(Sprite.class));
        PowerMockito.whenNew(Texture.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(Texture.class));
        Point p = Mockito.spy(new Point(12, 13));

        painter.draw(10, 11, 1.1f, 1.2f, "texture", p, batch);
        Mockito.verify(cam).isPointInFrustum(p.x, p.y);
        Mockito.verify(painter).draw(10, 11, 1.1f, 1.2f, "texture", p, batch);
        Mockito.verify(batch).begin();
        Mockito.verify(batch).end();
        Mockito.verifyNoMoreInteractions(painter, batch, cam);
    }

    @Test
    public void test_draw_2() throws Exception {
        PowerMockito.whenNew(Sprite.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(Sprite.class));
        Texture t = Mockito.mock(Texture.class);
        PowerMockito.whenNew(Texture.class).withAnyArguments().thenReturn(t);
        Mockito.when(t.getWidth()).thenReturn(100);
        Mockito.when(t.getHeight()).thenReturn(85);
        Point p = Mockito.spy(new Point(12, 13));

        painter.draw("texture", p, batch);
        Mockito.verify(painter).draw("texture", p, batch);
        Mockito.verify(cam).isPointInFrustum(p.x, p.y);
        Mockito.verify(painter)
                .draw(
                        -0.85f,
                        -0.5f,
                        1,
                        ((float) t.getHeight() / (float) t.getWidth()),
                        "texture",
                        p,
                        batch);
        Mockito.verify(batch).begin();
        Mockito.verify(batch).end();
        Mockito.verifyNoMoreInteractions(painter, batch, cam);
    }

    @Test
    public void test_draw_3() throws Exception {
        PowerMockito.whenNew(Sprite.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(Sprite.class));
        Texture t = Mockito.mock(Texture.class);
        PowerMockito.whenNew(Texture.class).withAnyArguments().thenReturn(t);
        Mockito.when(t.getWidth()).thenReturn(110);
        Mockito.when(t.getHeight()).thenReturn(75);
        Point p = Mockito.spy(new Point(12, 13));

        painter.draw(10, 11, "texture", p, batch);
        Mockito.verify(painter).draw(10, 11, "texture", p, batch);
        Mockito.verify(cam).isPointInFrustum(p.x, p.y);
        Mockito.verify(painter)
                .draw(
                        10,
                        11,
                        1,
                        ((float) t.getHeight() / (float) t.getWidth()),
                        "texture",
                        p,
                        batch);
        Mockito.verify(batch).begin();
        Mockito.verify(batch).end();
        Mockito.verifyNoMoreInteractions(painter, batch, cam);
    }

    @Test
    public void test_drawWithScaling() throws Exception {
        PowerMockito.whenNew(Sprite.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(Sprite.class));
        PowerMockito.whenNew(Texture.class)
                .withAnyArguments()
                .thenReturn(Mockito.mock(Texture.class));
        Point p = Mockito.spy(new Point(12, 13));

        painter.drawWithScaling(1.1f, 1.2f, "texture", p, batch);
        Mockito.verify(painter).drawWithScaling(1.1f, 1.2f, "texture", p, batch);
        Mockito.verify(painter).draw(-0.85f, -0.5f, 1.1f, 1.2f, "texture", p, batch);
        Mockito.verify(cam).isPointInFrustum(p.x, p.y);
        Mockito.verify(batch).begin();
        Mockito.verify(batch).end();
        Mockito.verifyNoMoreInteractions(painter, batch, cam);
    }
}
