package net.paulem.argus.core;

import lombok.Getter;
import net.paulem.argus.core.managers.EngineManager;
import net.paulem.argus.core.managers.WindowManager;
import net.paulem.argus.test.TestGame;
import net.paulem.argus.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Argus {
    public static Argus INSTANCE = new Argus();

    @Getter
    private TestGame game;
    @Getter
    private WindowManager window;
    @Getter
    private final Logger logger = LoggerFactory.getLogger("Argus");

    public void run() {
        getLogger().info("Hello from Argus!");

        window = new WindowManager(Constants.TITLE, 1280, 720, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();

        try {
            engine.start();
        } catch (Exception e) {
            getLogger().error("An error occurred while starting the engine!", e);
        }
    }
}
