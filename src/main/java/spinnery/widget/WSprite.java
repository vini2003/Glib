package spinnery.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import spinnery.client.utility.SpriteSheet;

/**
 * Allows you to render a sprite that's been packed into a texture atlas.
 * Specifically useful for rendering icons and other bits from the Minecraft resources.
 */
@Environment(EnvType.CLIENT)
public class WSprite extends WAbstractWidget {
    private SpriteSheet.Sprite sprite;

    public SpriteSheet.Sprite getSprite() {
        return sprite;
    }

    public <W extends WSprite> W setSprite(SpriteSheet.Sprite sprite) {
        this.sprite = sprite;
        return (W) this;
    }

    @Override
    public void draw() {
        if (isHidden()) {
            return;
        }

        getSprite().draw(getX(), getY(), getZ(), getWidth(), getHeight(), false);
    }
}
