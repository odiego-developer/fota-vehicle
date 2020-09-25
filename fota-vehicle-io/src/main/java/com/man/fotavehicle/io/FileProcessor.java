package com.man.fotavehicle.io;

import java.io.IOException;
import java.nio.file.Path;

public interface FileProcessor {
    void process(Path path) throws IOException;
}
