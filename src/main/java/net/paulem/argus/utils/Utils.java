package net.paulem.argus.utils;

import net.paulem.argus.core.Argus;
import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Utils {
    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public static String loadResource(String filename) throws Exception {
        return new String(Utils.class.getResourceAsStream(filename).readAllBytes());
    }

    public static ByteBuffer ioResourceToByteBuffer(String resource) throws Exception {
        try (InputStream source = Utils.class.getResourceAsStream(resource)) {
            if (source == null) throw new Exception("Resource not found: " + resource);
            byte[] bytes = source.readAllBytes();
            ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            return buffer;
        }
    }

    /**
     * Read all lines from a file from resource
     */
    public static List<String> readAllLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(Utils.class.getResource(filename).toURI()));
        } catch (Exception e) {
            Argus.INSTANCE.getLogger().error("Failed to read file: {}", filename, e);
        }
        return null;
    }
}
