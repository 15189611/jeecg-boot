package org.jeecg.common.util;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


public class FileHelper{
    public static File getUploadFile(MultipartFile file, String tmpSaveDir) throws IllegalStateException, IOException {
        String path = tmpSaveDir + File.separator + UUID.randomUUID();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String fileName = dir + File.separator  + UUID.randomUUID() + file.getOriginalFilename();
        File attachmentFile = new File(fileName);
        file.transferTo(attachmentFile);
        return attachmentFile;
    }
    public static File writeFile(String filePath, byte[] bytes) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes, 0, bytes.length);
        fos.flush();
        fos.close();
        return file;
    }
}