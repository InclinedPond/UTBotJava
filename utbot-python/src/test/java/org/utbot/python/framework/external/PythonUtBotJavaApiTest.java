package org.utbot.python.framework.external;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.utbot.python.PythonTestSet;
import org.utbot.python.framework.codegen.model.Unittest;
import org.utbot.python.utils.Cleaner;
import org.utbot.python.utils.TemporaryFileManager;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PythonUtBotJavaApiTest {
    private final String pythonPath = "<Your path to Python executable file>";  // Set your path to Python before testing

    @BeforeEach
    public void setUp() {
        TemporaryFileManager.INSTANCE.initialize();
        Cleaner.INSTANCE.restart();
    }

    @AfterEach
    public void tearDown() {
        Cleaner.INSTANCE.doCleaning();
    }
    private File loadResource(String name) {
        URL resource = getClass().getClassLoader().getResource(name);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
