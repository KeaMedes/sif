/**
 * SifClassParserTest.java
 * Copyright 2014 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.ClassFile;

public class SifClassParserTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException {
        final Path path = Paths.get("D:\\sif");
        final List<File> files = FileLister.listFiles(path);
        final ClassFile file = SifClassParser.parse(files.get(2));
        System.out.println(file.getJavaClass().getClassName());
    }
}
