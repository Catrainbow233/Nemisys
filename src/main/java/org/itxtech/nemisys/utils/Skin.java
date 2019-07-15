package org.itxtech.nemisys.utils;

import com.google.common.base.Preconditions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class Skin {
    private static final int PIXEL_SIZE = 4;

    public static final int SINGLE_SKIN_SIZE = 64 * 32 * PIXEL_SIZE;
    public static final int DOUBLE_SKIN_SIZE = 64 * 64 * PIXEL_SIZE;
    public static final int SKIN_128_64_SIZE = 128 * 64 * PIXEL_SIZE;
    public static final int SKIN_128_128_SIZE = 128 * 128 * PIXEL_SIZE;

    public static final String GEOMETRY_CUSTOM = "geometry.humanoid.custom";
    public static final String GEOMETRY_CUSTOM_SLIM = "geometry.humanoid.customSlim";

    private static final byte[] EMPTY = new byte[0];

    private String skinId = "Steve";
    private byte[] skinData = new byte[SINGLE_SKIN_SIZE];
    private byte[] encodedSkinData = null;
    private byte[] capeData = new byte[0];
    private byte[] encodedCapeData = null;
    private String geometryName = GEOMETRY_CUSTOM;
    private String geometryData = "";
    private byte[] encodedGeometryData = null;

    public boolean isValid() {
        return isValidSkin(skinData.length);
    }

    public byte[] getSkinData() {
        if (skinData == null) {
            if (encodedSkinData == null) {
                return EMPTY;
            }
            skinData = Base64.getDecoder().decode(encodedSkinData);
        }
        return skinData;
    }

    public byte[] getEncodedSkinData() {
        if (encodedSkinData == null) {
            if (skinData == null) {
                return EMPTY;
            }
            encodedSkinData = Base64.getEncoder().encode(skinData);
        }
        return encodedSkinData;
    }

    public String getGeometryName() {
        return geometryName;
    }

    public String getSkinId() {
        return skinId;
    }

    public void setSkinId(String skinId) {
        if (skinId == null || skinId.trim().isEmpty()) {
            return;
        }
        this.skinId = skinId;
    }

    public void setSkinData(BufferedImage image) {
        setSkinData(parseBufferedImage(image));
    }

    public void setSkinData(byte[] data) {
        if (data == null || !isValidSkin(data.length)) {
            throw new IllegalArgumentException("Invalid skin");
        }
        if (!Arrays.equals(skinData, this.skinData)) {
            this.skinData = data;
            this.encodedSkinData = null;
        }
    }

    public void setEncodedSkinData(byte[] encodedSkinData) {
        if (encodedSkinData != null && !Arrays.equals(encodedSkinData, this.encodedSkinData)) {
            skinData = null;
            this.encodedSkinData = encodedSkinData;
        }
    }

    public void setGeometryName(String model) {
        if (model == null || model.trim().isEmpty()) {
            model = GEOMETRY_CUSTOM;
        }

        this.geometryName = model;
    }

    public byte[] getCapeData() {
        if (capeData == null) {
            if (encodedCapeData == null) {
                return EMPTY;
            }
            capeData = Base64.getDecoder().decode(encodedCapeData);
        }
        return capeData;
    }

    public byte[] getEncodedCapeData() {
        if (encodedCapeData == null) {
            if (capeData == null) {
                return EMPTY;
            }
            encodedCapeData = Base64.getEncoder().encode(capeData);
        }
        return encodedCapeData;
    }

    public void setCapeData(BufferedImage image) {
        setCapeData(parseBufferedImage(image));
    }

    public void setCapeData(byte[] capeData) {
        Preconditions.checkNotNull(capeData, "capeData");
        if (!Arrays.equals(capeData, this.capeData)) {
            this.capeData = capeData;
            this.encodedCapeData = null;
        }
    }

    public void setEncodedCapeData(byte[] encodedCapeData) {
        if (encodedCapeData != null && !Arrays.equals(encodedCapeData, this.encodedCapeData)) {
            capeData = null;
            this.encodedCapeData = encodedCapeData;
        }
    }

    public String getGeometryData() {
        if (geometryData == null) {
            if (encodedGeometryData == null) {
                return "";
            }
            geometryData = new String(Base64.getDecoder().decode(encodedGeometryData), StandardCharsets.UTF_8);
        }
        return geometryData;
    }

    public byte[] getEncodedGeometryData() {
        if (encodedGeometryData == null) {
            if (geometryData == null) {
                return EMPTY;
            }
            encodedGeometryData = Base64.getEncoder().encode(geometryData.getBytes(StandardCharsets.UTF_8));
        }
        return encodedGeometryData;
    }

    public void setGeometryData(String geometryData) {
        Preconditions.checkNotNull(geometryData, "geometryData");
        if (!geometryData.equals(this.geometryData)) {
            this.geometryData = geometryData;
            this.encodedGeometryData = null;
        }
    }

    public void setEncodedGeometryData(byte[] encodedGeometryData) {
        if (encodedGeometryData != null && !Arrays.equals(encodedGeometryData, this.encodedGeometryData)) {
            geometryData = null;
            this.encodedGeometryData = encodedGeometryData;
        }
    }

    public Skin copy() {
        Skin skin = new Skin();
        skin.skinId = this.skinId;
        skin.skinData = Arrays.copyOf(this.skinData, this.skinData.length);
        skin.capeData = Arrays.copyOf(this.capeData, this.capeData.length);
        skin.geometryName = this.geometryName;
        skin.geometryData = this.geometryData;
        return skin;
    }

    private static byte[] parseBufferedImage(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y), true);
                outputStream.write(color.getRed());
                outputStream.write(color.getGreen());
                outputStream.write(color.getBlue());
                outputStream.write(color.getAlpha());
            }
        }
        image.flush();
        return outputStream.toByteArray();
    }

    private static boolean isValidSkin(int length) {
        return length == SINGLE_SKIN_SIZE ||
                length == DOUBLE_SKIN_SIZE ||
                length == SKIN_128_64_SIZE ||
                length == SKIN_128_128_SIZE;
    }
}
