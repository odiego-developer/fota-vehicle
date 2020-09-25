package com.man.fotavehicle.io.internal;


import com.man.fotavehicle.io.Folder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("csvFolder")
public class CsvFolder implements Folder {

    @Value("${csv-folder-extraction}")
    private  String path;

    @Override
    public String getPath() {
        return path;
    }
}
