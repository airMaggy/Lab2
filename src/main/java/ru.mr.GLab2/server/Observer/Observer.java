package ru.mr.GLab2.server.Observer;

import java.io.IOException;

public interface Observer {
    void createAuthorData(Long id) throws IOException;
    void changeAuthorData(Long id) throws IOException;
    void deleteAuthorData(Long id) throws IOException;
    void createBookData(Long id) throws IOException;
    void changeBookData(Long id) throws IOException;
    void deleteBookData(Long id) throws IOException;

}

