import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {
    private final List<String> pathFiles;

    FileFinder() {
        pathFiles = new ArrayList<>();
    }

    public List<String> fileSearch(String path, String[] matchMask) {
        File folder = new File(path);
        File[] filesList = folder.listFiles();
        for (File file : filesList) {
            if (file.isDirectory()) {
                String folderPath = path + file.getName() + "/";
                fileSearch(folderPath, matchMask);
                continue;
            }
            String fileName = file.getPath();
            for (String mask : matchMask) {
                if (FileFinder.getTypeFile(fileName).equals(mask)) {
                    pathFiles.add(fileName);
                }
            }
        }
        return pathFiles;
    }
    public static String getFileLines(String fileName, boolean lineSeporator) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            lines.forEach(line -> {
                builder.append(line);
                if (lineSeporator) {
                    builder.append("\n");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }
    public static String getTypeFile(String fileName) {

        return fileName.substring(fileName.indexOf(".") + 1);
    }
}
