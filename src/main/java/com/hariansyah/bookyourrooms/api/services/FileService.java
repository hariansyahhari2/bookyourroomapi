package com.hariansyah.bookyourrooms.api.services;

import com.hariansyah.bookyourrooms.api.entities.AbstractEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileService {

    public void upload(AbstractEntity<?> entity, InputStream in) throws IOException;

    public void download(AbstractEntity<?> entity, OutputStream out) throws IOException;
}
