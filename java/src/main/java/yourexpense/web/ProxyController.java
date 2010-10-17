package yourexpense.web;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;

import static org.apache.commons.io.IOUtils.closeQuietly;

@Controller
public class ProxyController {

    @RequestMapping("proxy")
    public void handleRequest(@RequestParam String url, HttpServletResponse response) {
        InputStream inputStream = null;
        ServletOutputStream output = null;
        try {
            inputStream = new URL(url).openStream();
            output = response.getOutputStream();
            IOUtils.copy(inputStream, output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(inputStream);
            closeQuietly(output);
        }
    }
}
