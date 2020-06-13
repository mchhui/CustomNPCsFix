package mchhui.customnpcsfix.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import mchhui.customnpcsfix.api.event.client.ResourcePackRepositoryGetFilesEvent;
import net.minecraftforge.common.MinecraftForge;

public class ResourcePackHelper {
    public static void onResourcePackRepositoryGetFiles(List<File> list) {
        try {
            InputStream inputStream = ResourcePackHelper.class
                    .getResourceAsStream("/assets/customnpcsfix/CustomNPCsFix.zip");
            File tempFile = new File(new File("").getAbsoluteFile(), "CustomNPCsFix.zip");
            if (tempFile.exists()) {
                tempFile.delete();
            }
            tempFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            int i;
            byte[] bytes = new byte[1024];
            while ((i = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, i);
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
            list.add(tempFile);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
