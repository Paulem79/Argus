package net.paulem.argus;

import lombok.Getter;
import net.paulem.argus.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Argus {
    public static Argus INSTANCE = new Argus();

    @Getter
    private WindowManager window;
    @Getter
    private EngineManager engine;
    @Getter
    private final Logger logger = LoggerFactory.getLogger("Argus");

    public void run() {
        getLogger().info("Hello from Argus!");

        window = new WindowManager(Constants.TITLE, 1280, 720, false);
        engine = new EngineManager();

        try {
            engine.start();
        } catch (Exception e) {
            getLogger().error("An error occurred while starting the engine!", e);
        }
    }
}
