package sudols.ecopercent.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Slf4j
@Component
public class ImageUtil {

    public byte[] getBytesFromMultipartFile(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (Exception e) {
            log.debug("MultipartFile 에서 getBytes() 실패: " + e);
            return null;
        }
    }

    public String byteaToBase64(byte[] bytes) {
        try {
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return null;
        }
    }
}
