package org.jing.core.format;

import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-22 <br>
 */
@SuppressWarnings("unused") public class Carrier2Json {
    private String newline = "\n";

    private String indent = "    ";

    private String space = " ";

    private boolean needRootNode = false;

    private int level = 0;

    private StringBuilder stbr = new StringBuilder();

    public Carrier2Json() {
    }

    public Carrier2Json setNewline(String newline) {
        this.newline = StringUtil.ifEmpty(newline);
        return this;
    }

    public Carrier2Json setNewline(boolean needNewLine) {
        this.newline = needNewLine ? "\n" : "";
        return this;
    }

    public String getNewline() {
        return newline;
    }

    public Carrier2Json setIndent(String indent) {
        this.indent = StringUtil.ifEmpty(indent);
        return this;
    }

    public Carrier2Json setIndent(boolean needIndent) {
        this.indent = needIndent ? "    " : "";
        return this;
    }

    public String getIndent() {
        return indent;
    }

    public String getCurrentIndent() {
        return StringUtil.repeat(indent, level);
    }

    public Carrier2Json setSpace(String space) {
        this.space = StringUtil.ifEmpty(space);
        return this;
    }

    public Carrier2Json setSpace(boolean needSpace) {
        this.space = needSpace ? " " : "";
        return this;
    }

    public String getSpace() {
        return space;
    }

    public Carrier2Json addLevel() {
        level ++;
        return this;
    }

    public Carrier2Json reduceLevel() {
        level --;
        return this;
    }

    public Carrier2Json setNeedRootNode(boolean needRootNode) {
        this.needRootNode = needRootNode;
        return this;
    }

    public boolean isNeedRootNode() {
        return needRootNode;
    }

    public static String escape(String content) {
        return content
            .replace("\\", "\\\\")
            .replaceAll("\\r", "\\\\r")
            .replaceAll("\\n", "\\\\n")
            .replaceAll("\"", "\\\\\"")
            .replaceAll("\\t", "\\\\t");
    }

    public static String unescape(String content) {
        return content
            .replace("\\\\", "\\")
            .replaceAll("\\\\r", "\\r")
            .replaceAll("\\\\n", "\\n")
            .replaceAll("\\\\\"", "\"")
            .replaceAll("\\\\t", "\\t");
    }

    public Carrier2Json append(String content) {
        stbr.append(content);
        return this;
    }

    public Carrier2Json clearStringBuilder() {
        stbr.setLength(0);
        return this;
    }

    public StringBuilder getStringBuilder() {
        return stbr;
    }
}
