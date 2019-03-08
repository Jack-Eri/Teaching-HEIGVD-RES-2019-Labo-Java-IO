package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override @SuppressWarnings("Duplicates")
  public void explore(File rootDirectory, IFileVisitor visitor) {

    visitor.visit(rootDirectory);

    // Continue exploration
    if (rootDirectory.isDirectory()) {

      // Visits the directories
      File[] dirs = rootDirectory.listFiles(File::isDirectory);
      Arrays.sort(dirs);

      for (File dir : dirs) {
        explore(dir, visitor);
      }

      // Visits the files
      File[] files = rootDirectory.listFiles(File::isFile);
      Arrays.sort(files);

      for (File file : files) {
        explore(file, visitor);
      }
    }
  }
}
