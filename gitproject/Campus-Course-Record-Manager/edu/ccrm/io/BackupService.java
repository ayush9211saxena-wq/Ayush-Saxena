package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {

    // ✅ Copy files to timestamped backup folder
    public void backup(String sourceFolder, String backupRoot) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path source = Paths.get(sourceFolder);
        Path dest = Paths.get(backupRoot, "backup_" + timestamp);

        Files.createDirectories(dest);

        // Copy files
        Files.walk(source).forEach(file -> {
            try {
                Path target = dest.resolve(source.relativize(file));
                if (Files.isDirectory(file)) {
                    Files.createDirectories(target);
                } else {
                    Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("✅ Backup created at " + dest);
    }

    // ✅ Recursive utility: show directory size
    public long computeDirectorySize(Path dir) throws IOException {
        return Files.walk(dir)
                .filter(Files::isRegularFile)
                .mapToLong(p -> {
                    try { return Files.size(p); }
                    catch (IOException e) { return 0L; }
                }).sum();
    }
}