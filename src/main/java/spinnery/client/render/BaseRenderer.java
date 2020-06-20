package spinnery.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.lwjgl.opengl.GL11;
import spinnery.widget.api.Color;
import spinnery.widget.api.WLayoutElement;

import java.util.Stack;

public class BaseRenderer {
	public static void drawPanel(double x, double y, double z, double sX, double sY, Color shadow, Color panel, Color hilight, Color outline) {
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

	public static void drawRectangle(double x, double y, double z, double sX, double sY, Color color) {
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		RenderSystem.color4f(color.R, color.G, color.B, color.A);

		getBufferBuilder().begin(GL11.GL_TRIANGLES, VertexFormats.POSITION);

		getBufferBuilder().vertex(x, y, 0).next();
		getBufferBuilder().vertex(x, y + sY, 0).next();
		getBufferBuilder().vertex(x + sX, y, 0).next();

		getBufferBuilder().vertex(x, y + sY, 0).next();
		getBufferBuilder().vertex(x + sX, y + sY, 0).next();
		getBufferBuilder().vertex(x + sX, y, 0).next();

		getTesselator().draw();

		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	public static BufferBuilder getBufferBuilder() {
		return getTesselator().getBuffer();
	}

	public static Tessellator getTesselator() {
		return Tessellator.getInstance();
	}

	public static void drawGradient(double startX, double startY, double endX, double endY, double z, Color colorStart, Color colorEnd) {
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		RenderSystem.defaultBlendFunc();
		RenderSystem.shadeModel(7425);
		getBufferBuilder().begin(7, VertexFormats.POSITION_COLOR);
		getBufferBuilder().vertex(endX, startY, 0).color(colorStart.R, colorStart.G, colorStart.B, colorStart.A).next();
		getBufferBuilder().vertex(startX, startY, 0).color(colorStart.R, colorStart.G, colorStart.B, colorStart.A).next();
		getBufferBuilder().vertex(startX, endY, 0).color(colorEnd.R, colorEnd.G, colorEnd.B, colorEnd.A).next();
		getBufferBuilder().vertex(endX, endY, 0).color(colorEnd.R, colorEnd.G, colorEnd.B, colorEnd.A).next();
		getTesselator().draw();
		RenderSystem.shadeModel(7424);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}

	public static void drawBeveledPanel(double x, double y, double z, double sX, double sY, Color topleft, Color panel, Color bottomright) {
		drawRectangle(x, y, z, sX, sY, panel);
		drawRectangle(x, y, z, sX, 1, topleft);
		drawRectangle(x, y + 1, z, 1, sY - 1, topleft);
		drawRectangle(x + sX - 1, y + 1, z, 1, sY - 1, bottomright);
		drawRectangle(x, y + sY - 1, z, sX - 1, 1, bottomright);
	}

	public static void drawImage(double x, double y, double z, double sX, double sY, Identifier texture) {
		drawSprite(x, y, z, sX, sY, texture, 0, 0, 1, 1);
	}

	public static void drawSprite(double x, double y, double z, double sX, double sY, Identifier texture, float u1, float v1, float u2, float v2) {
		BaseRenderer.getTextureManager().bindTexture(texture);

		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		RenderSystem.color4f(255, 255, 255, 255);

		BaseRenderer.getBufferBuilder().begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);

		BaseRenderer.getBufferBuilder().vertex(x, y + sY, 0).texture(u1, v2).next();
		BaseRenderer.getBufferBuilder().vertex(x + sX, y + sY, 0).texture(u2, v2).next();
		BaseRenderer.getBufferBuilder().vertex(x + sX, y, 0).texture(u2, v1).next();
		BaseRenderer.getBufferBuilder().vertex(x, y, 0).texture(u1, v1).next();

		BaseRenderer.getTesselator().draw();

		RenderSystem.disableBlend();
	}

	public static TextureManager getTextureManager() {
		return MinecraftClient.getInstance().getTextureManager();
	}

	public static ItemRenderer getItemRenderer() {
		return MinecraftClient.getInstance().getItemRenderer();
	}

	public static TextRenderer getTextRenderer() {
		return MinecraftClient.getInstance().textRenderer;
	}
}
