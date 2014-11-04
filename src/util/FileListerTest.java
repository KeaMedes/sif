/**
 * FileListerTest.java
 * Copyright 2014 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FileListerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testListFilesPath() throws IOException {
        final Path root = Paths.get("D://sif");
        FileLister.listFiles(root);
    }

    @Test
    public void testListFilesPathListOfFile() throws IOException {
        final Path root = Paths.get("D://sif");
        final List<File> files = new LinkedList<File>();
        FileLister.listFiles(root, files);
    }

}
