package spinnery.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import spinnery.widget.WColor;

public class BaseRenderer {
	public enum Font {
		DEFAULT,
		ENCHANTMENT,
	}

	public static void drawRectangle(double x, double y, double z, double sX, double sY, WColor color) {
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		RenderSystem.color4f(color.R, color.G, color.B, color.A);

		getBufferBuilder().begin(GL11.GL_TRIANGLES, VertexFormats.POSITION);

		getBufferBuilder().vertex(x, y, z).next();
		getBufferBuilder().vertex(x, y + sY, z).next();
		getBufferBuilder().vertex(x + sX, y, z).next();

		getBufferBuilder().vertex(x, y + sY, z).next();
		getBufferBuilder().vertex(x + sX, y + sY, z).next();
		getBufferBuilder().vertex(x + sX, y, z).next();

		getTesselator().draw();

		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	public static void drawPanel(double x, double y, double z, double sX, double sY, WColor shadow, WColor panel, WColor hilight, WColor outline) {
		drawRectangle(x + 3, y + 3, z, sX - 6, sY - 6, panel);

		drawRectangle(x + 2, y + 1, z, sX - 4, 2, hilight);
		drawRectangle(x + 2, y + sY - 3, z, sX - 4, 2, shadow);
		drawRectangle(x + 1, y + 2, z, 2, sY - 4, hilight);
		drawRectangle(x + sX - 3, y + 2, z, 2, sY - 4, shadow);
		drawRectangle(x + sX - 3, y + 2, z, 1, 1, panel);
		drawRectangle(x + 2, y + sY - 3, z, 1, 1, panel);
		drawRectangle(x + 3, y + 3, z, 1, 1, hilight);
		drawRectangle(x + sX - 4, y + sY - 4, z, 1, 1, shadow);

		drawRectangle(x + 2, y, z, sX - 4, 1, outline);
		drawRectangle(x, y + 2, z, 1, sY - 4, outline);
		drawRectangle(x + sX - 1, y + 2, z, 1, sY - 4, outline);
		drawRectangle(x + 2, y + sY - 1, z, sX - 4, 1, outline);
		drawRectangle(x + 1, y + 1, z, 1, 1, outline);
		drawRectangle(x + 1, y + sY - 2, z, 1, 1, outline);
		drawRectangle(x + sX - 2, y + 1, z, 1, 1, outline);
		drawRectangle(x + sX - 2, y + sY - 2, z, 1, 1, outline);
	}

	public static void drawBeveledPanel(double x, double y, double z, double sX, double sY, WColor topleft, WColor panel, WColor bottomright) {
		drawRectangle(x, y, z, sX, sY, panel);
		drawRectangle(x, y, z, sX - 1, 1, topleft);
		drawRectangle(x, y + 1, z, 1, sY - 2, topleft);
		drawRectangle(x + sX - 1, y + 1, z, 1, sY - 1, bottomright);
		drawRectangle(x + 1, y + sY - 1, z, sX - 1, 1, bottomright);
	}

	public static void drawText(String text, int x, int y, int color) {
		drawTextTrimmed(text, x, y, null, color);
	}

	public static void drawText(boolean isShadowed, String text, int x, int y, int color) {
		drawText(isShadowed, text, x, y, color, 0x3E3E3E);
	}

	public static void drawText(boolean isShadowed, String text, int x, int y, int color, int shadowColor) {
		if (isShadowed) {
			drawTextTrimmed(text, x + 1, y + 1, null, shadowColor);
		}
		drawTextTrimmed(text, x, y, null, color);
	}

	public static void drawTextTrimmed(String text, int x, int y, Integer maxWidth, int color) {
		drawTextTrimmed(text, x, y, maxWidth, color, Font.DEFAULT);
	}

	public static void drawText(String text, int x, int y, int color, Font font) {
		drawTextTrimmed(text, x, y, null, color, font);
	}

	public static void drawTextTrimmed(String text, int x, int y, Integer maxWidth, int color, Font font) {
		if (maxWidth != null) {
			getTextRenderer(font).drawTrimmed(text, x, y, maxWidth, color);
		} else {
			getTextRenderer(font).draw(text, x, y, color);
		}
	}

	public static void drawImage(double x, double y, double z, double sX, double sY, Identifier texture) {
		getTextureManager().bindTexture(texture);

		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		RenderSystem.color4f(255, 255, 255, 255);

		getBufferBuilder().begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);

		getBufferBuilder().vertex(x, y + sY, z).texture(0, 1).next();
		getBufferBuilder().vertex(x + sX, y + sY, z).texture(1, 1).next();
		getBufferBuilder().vertex(x + sX, y, z).texture(1, 0).next();
		getBufferBuilder().vertex(x, y, z).texture(0, 0).next();

		getTesselator().draw();

		RenderSystem.disableBlend();
	}

	public static void drawPixel(double x, double y, double z, WColor color) {
		drawRectangle(x, y, z, 1, 1, color);
	}

	public static TextRenderer getTextRenderer() {
		return getTextRenderer(Font.DEFAULT);
	}

	public static TextRenderer getTextRenderer(Font font) {
		switch (font) {
			case ENCHANTMENT:
				return MinecraftClient.getInstance().getFontManager()
						.getTextRenderer(MinecraftClient.ALT_TEXT_RENDERER_ID);
			case DEFAULT:
			default:
				return MinecraftClient.getInstance().textRenderer;
		}
	}

	public static ItemRenderer getItemRenderer() {
		return MinecraftClient.getInstance().getItemRenderer();
	}

	public static Tessellator getTesselator() {
		return Tessellator.getInstance();
	}

	public static BufferBuilder getBufferBuilder() {
		return getTesselator().getBuffer();
	}

	public static TextureManager getTextureManager() {
		return MinecraftClient.getInstance().getTextureManager();
	}
}
