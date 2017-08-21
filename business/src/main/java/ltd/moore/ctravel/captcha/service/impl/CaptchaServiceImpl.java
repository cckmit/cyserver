package ltd.moore.ctravel.captcha.service.impl;

import com.hdos.platform.common.util.CacheUtils;
import ltd.moore.ctravel.captcha.base.ACaptcha;
import ltd.moore.ctravel.captcha.base.CaptchaContext;
import ltd.moore.ctravel.captcha.base.ImageCode;
import ltd.moore.ctravel.captcha.service.CaptchaService;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by zzhu8 on 2017/5/14.
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    CaptchaContext captchaContext = new CaptchaContext(ACaptcha.CODEER_NAME);

    @Override
    public Map<String, Object> getCaptcha() {

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            ImageCode imageCode = captchaContext.getCaptcha().getImageCode();
            String captchaImage = ImageToBase64(imageCode.getImage());
            UUID uuid = UUID.randomUUID();
            data.put("captchaId", uuid);
            data.put("captchaImage", captchaImage.replaceAll("\r|\n", ""));
            result.put("data",data);
            result.put("resultCode", ResultCodeInfo.E000000);
            CacheUtils.put(uuid.toString(), imageCode.getCode());
            //"data:image/png;base64," + ImageToBase64(imageCode.getImage());
        } catch (Exception e) {
            logger.error(ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E999999));
            result.put("resultCode", ResultCodeInfo.E999999);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E999999));
        }
        return result;
    }


    @Override
    public boolean validateCaptcha(String codeKey, String uuid, boolean removeCache) {
        String clientCode = (String) CacheUtils.get(uuid);
        if (clientCode == null) {
            return false;
        }
        boolean result = captchaContext.checkCaptcha(codeKey, clientCode);
        //是否删除缓存中的验证码
        if (result && removeCache) {
            CacheUtils.delete(uuid);
        }
        return result;
    }


    /**
     * Image转base64
     *
     * @param image
     * @return
     */
    public String ImageToBase64(Image image) {

        // Image->bufferreImage
        BufferedImage bimg = new BufferedImage(image.getWidth(null),
                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bimg.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();

        // bufferImage->base64
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bimg, "png", outputStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(outputStream.toByteArray());
    }
}