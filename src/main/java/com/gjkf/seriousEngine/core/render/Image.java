/*
 * Created by Davide Cossu (gjkf), 8/7/2016
 */
package com.gjkf.seriousEngine.core.render;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

public class Image{

    private ByteBuffer image;
    private int width, height, id;

    private Image(int width, int height, ByteBuffer data){
        this.id = glGenTextures();
        this.width = width;
        this.height = height;
        this.image = data;

        glBindTexture(GL_TEXTURE_2D, id);

        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
    }

    public int getID(){
        return id;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ByteBuffer getImage(){
        return image;
    }

    public static Image loadImage(String path){
        InputStream in;
        BufferedImage im = null;

        try{
            in = new FileInputStream(path);
            im = ImageIO.read(in);
        }catch(IOException e){
            e.printStackTrace();
        }

        int width = im.getWidth();
        int height = im.getHeight();

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        int[] pixels = new int[width * height];
        im.getRGB(0, 0, width, height, pixels, 0, width);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                     /* Pixel as RGBA: 0xAARRGGBB */
                int pixel = pixels[y * width + x];

                    /* Red component 0xAARRGGBB >> (4 * 4) = 0x0000AARR */
                buffer.put((byte) ((pixel >> 16) & 0xFF));

                    /* Green component 0xAARRGGBB >> (2 * 4) = 0x00AARRGG */
                buffer.put((byte) ((pixel >> 8) & 0xFF));

                    /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
                buffer.put((byte) (pixel & 0xFF));

                    /* Alpha component 0xAARRGGBB >> (6 * 4) = 0x000000AA */
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

            /* Do not forget to flip the buffer! */
        buffer.flip();

        return new Image(width, height, buffer);
    }

}