package org.jing.core.socket;

import org.jing.core.logger.JingLogger;
import org.jing.core.util.StringUtil;

import java.io.InputStream;
import java.io.OutputStream;

public class SocketServer4Demo extends BaseSocketServer {
    private static final JingLogger LOGGER = JingLogger.getLogger(SocketServer4Demo.class);

    @Override
    protected void operation(InputStream reader, OutputStream writer) throws Exception {
        String content = StringUtil.readFromInputStream(reader, "gbk");
        LOGGER.imp(content);
        writer.write(content.getBytes("gbk"));
        writer.flush();
    }
}
