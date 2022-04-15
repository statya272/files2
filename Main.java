import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 1, 10, 100.0);
        GameProgress progress2 = new GameProgress(90, 2, 20, 200.0);
        GameProgress progress3 = new GameProgress(800, 3, 30, 300.0);

        saveGame("D:\\Games\\savegames\\save1.dat", progress1);
        saveGame("D:\\Games\\savegames\\save2.dat", progress2);
        saveGame("D:\\Games\\savegames\\save3.dat", progress3);

        File dirSavegames = new File("D:\\Games\\savegames");
        List<String> saves = new ArrayList<>();

        try {
            for (File file :
                    dirSavegames.listFiles()) {
                saves.add(file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.getMessage();
        }

        zipFiles("D:\\Games\\savegames\\zip_save.zip", saves);

        for (String fileAddress :
                saves) {
            deleteFile(fileAddress);
        }
    }

    public static void saveGame(String loc, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(loc);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String zipAddress, List<String> fileAddress) {
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipAddress));
            for (int i = 0; i < fileAddress.size(); i++) {
                FileInputStream fis = new FileInputStream(fileAddress.get(i));
                ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                zos.putNextEntry(entry);
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                zos.write(bytes);
                fis.close();
                zos.closeEntry();
            }
            zos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFile(String fileAddress) {
        File file = new File(fileAddress);
        if (file.delete()) {
            System.out.println("Файл " + fileAddress + " успешно удален");
        } else {
            System.out.println("Файл " + fileAddress + " не удален");
        }
    }
}