/**
 * FileLister.java
 * Copyright 2014 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileLister{
    /**
     * @author Kea
     * @param root
     * @return
     * @throws IOException
     */
    public static List<File>listFiles(final Path root) throws IOException{
        final List<File> files = new ArrayList<File>();
        list(root, files);
        return files;
    }
    /**
     * @author Kea
     * @param root
     * @param files
     * @throws IOException
     */
    public static void listFiles(final Path root, final List<File> files) throws IOException{
        list(root, files);
        return ;
    }
    /**
     * @author Kea
     * @param root
     * @param files
     * @throws IOException
     */
    private static void list(final Path root, final List<File>files) throws IOException{
        final SimpleFileVisitor<Path> finder = new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException{
                files.add(file.toFile());
                return super.visitFile(file, attrs);
            }
        };
        java.nio.file.Files.walkFileTree(root, finder);
    }
}
