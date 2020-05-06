package Interviews.Amazon.Find_Command;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.*;

/**
 * https://docs.oracle.com/javase/tutorial/essential/io/find.html
 *
 * Use SimpleFileVisitor.
 */
public class Find {
    public static class Finder extends SimpleFileVisitor<Path> {
        private final PathMatcher matcher;
        private int numMatches = 0;
        private List<Path> res = new ArrayList<>();

        public Finder(String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher("regex:" + pattern);
//        final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("regex:.*Correct_\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}\\.txt");
//        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        }


        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                numMatches++;
                res.add(file);
                System.out.println(file);
            }
        }

        // Prints the total number of
        // matches to standard out.
        void done() {
            System.out.println("Matched: " + numMatches);
        }

        /**
         * Invoke the pattern matching, method on each file.
         **/
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }

        /**
         * Invoke the pattern matching method on each directory.
         **/
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            find(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
    }

    static void usage() {
        System.err.println("java Find <path>" + " -name \"<glob_pattern>\"");
        System.exit(-1);
    }

    public static void main(String[] args) throws IOException {
//        if (args.length < 3 || !args[1].equals("-name"))
//            usage();
//
//        Path startingDir = Paths.get(args[0]);
//        String pattern = args[2];

        Path startingDir = Paths.get("/Users/yuank/Documents/Benefit");
        String pattern = "^.*\\.(rar|txt|pdf)$";

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.done();
    }
}


