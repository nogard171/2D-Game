package utils;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import core.RawSprite;
import core.SpriteFrame;
import core.SpriteSheet;
import game.Data;

public class Loader {
	public static void loadSettings(String file) throws IOException {
		Properties newSettings = new Properties();
		InputStream is = null;
		is = new FileInputStream(file);
		newSettings.load(is);
		Data.settings = newSettings;
	}

	public static Texture loadTexture(String file) throws IOException {
		return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(file));
	}

	public static void loadSprites(String file) throws Exception {

		File fXmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList spritesList = doc.getElementsByTagName("sprites");
		Node spritedValue = spritesList.item(0);
		if (spritedValue.getNodeType() == Node.ELEMENT_NODE) {
			Element spritesElement = (Element) spritedValue;
			Data.texture = Loader.loadTexture(spritesElement.getAttribute("file"));
		}

		NodeList spriteList = doc.getElementsByTagName("sprite");

		for (int spriteIndex = 0; spriteIndex < spriteList.getLength(); spriteIndex++) {

			Node spriteValue = spriteList.item(spriteIndex);
			if (spriteValue.getNodeType() == Node.ELEMENT_NODE) {

				RawSprite raw = new RawSprite();

				Element spriteElement = (Element) spriteValue;

				NodeList dataList = spriteElement.getElementsByTagName("data");
				if (dataList.getLength() == 1) {
					Node dataValue = dataList.item(0);
					if (dataValue.getNodeType() == Node.ELEMENT_NODE) {
						Element dataElement = (Element) dataValue;

						NodeList textureList = dataElement.getElementsByTagName("texture");

						if (textureList.getLength() == 1) {
							Node textureValue = textureList.item(0);
							if (textureValue.getNodeType() == Node.ELEMENT_NODE) {
								Element textureElement = (Element) textureValue;
								raw.textureData = new Vector4f(Float.parseFloat(textureElement.getAttribute("x")),
										Float.parseFloat(textureElement.getAttribute("y")),
										Float.parseFloat(textureElement.getAttribute("h")),
										Float.parseFloat(textureElement.getAttribute("w")));
								System.out.println("texture: " + raw.textureData);
							}
						}

						NodeList boundList = dataElement.getElementsByTagName("bound");

						if (boundList.getLength() == 1) {
							Node boundValue = boundList.item(0);
							if (boundValue.getNodeType() == Node.ELEMENT_NODE) {
								Element boundElement = (Element) boundValue;
								raw.boundData = new Rectangle(Integer.parseInt(boundElement.getAttribute("x")),
										Integer.parseInt(boundElement.getAttribute("y")),
										Integer.parseInt(boundElement.getAttribute("w")),
										Integer.parseInt(boundElement.getAttribute("h")));
							}
						}
					}
				}
				System.out.println("name: " + spriteElement.getAttribute("name"));
				Data.sprites.put(spriteElement.getAttribute("name"), raw);
			}
		}
	}

	public static void loadSpriteSheets(String file) throws Exception {
		File fXmlFile = new File(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList spriteSheetsList = doc.getElementsByTagName("spritesheet");
		for (int spriteSheetsIndex = 0; spriteSheetsIndex < spriteSheetsList.getLength(); spriteSheetsIndex++) {

			Node spriteSheetValue = spriteSheetsList.item(spriteSheetsIndex);
			if (spriteSheetValue.getNodeType() == Node.ELEMENT_NODE) {
				Element spriteSheetElement = (Element) spriteSheetValue;
				SpriteSheet spriteSheet = new SpriteSheet();
				spriteSheet.setTexture(Loader.loadTexture(spriteSheetElement.getAttribute("file")));

				NodeList dataList = spriteSheetElement.getElementsByTagName("data");
				if (dataList.getLength() == 1) {
					Node dataValue = dataList.item(0);
					if (dataValue.getNodeType() == Node.ELEMENT_NODE) {
						Element dataElement = (Element) dataValue;

						NodeList textureList = dataElement.getElementsByTagName("sprite");

						if (textureList.getLength() == 1) {
							Node textureValue = textureList.item(0);
							if (textureValue.getNodeType() == Node.ELEMENT_NODE) {
								Element textureElement = (Element) textureValue;
								spriteSheet
										.setSpriteSize(new Dimension(Integer.parseInt(textureElement.getAttribute("w")),
												Integer.parseInt(textureElement.getAttribute("h"))));

							}
						}
					}
				}

				loadSpriteFrames(spriteSheet);

				Data.spriteSheets.put(spriteSheetElement.getAttribute("name"), spriteSheet);
			}
		}
	}

	public static void loadSpriteFrames(SpriteSheet spriteSheet) {

		int sizeW = (int) ((float) (spriteSheet.getTexture().getImageWidth()
				/ (float) spriteSheet.getSpriteSize().getWidth()));
		int sizeH = (int) ((float) (spriteSheet.getTexture().getImageHeight()
				/ (float) spriteSheet.getSpriteSize().getHeight()));
		SpriteFrame[][] frames = new SpriteFrame[sizeW][sizeH];

		for (int x = 0; x < sizeW; x++) {
			for (int y = 0; y < sizeH; y++) {
				RawSprite raw = new RawSprite();

				raw.textureData = new Vector4f(x * 32, y * 64, 64, 32);

				int displayList = GL11.glGenLists(1);

				GL11.glNewList(displayList, GL11.GL_COMPILE);
				GL11.glBegin(GL11.GL_QUADS);

				Renderer.renderSpriteFrame(spriteSheet.getTexture(), raw);

				GL11.glEnd();
				GL11.glEndList();

				frames[x][y] = new SpriteFrame(displayList);
			}
		}

		spriteSheet.setFrames(frames);
	}
}
