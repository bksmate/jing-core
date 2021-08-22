package org.jing.core.lang.node;

import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-22 <br>
 */
@SuppressWarnings("unused") public class Format4Json {
    private String newline = "\n";

    private String indent = "    ";

    private String space = " ";

    private boolean needRootNode = false;

    private int level = 0;

    private StringBuilder stbr = new StringBuilder();

    public Format4Json() {
    }

    public Format4Json setNewline(String newline) {
        this.newline = StringUtil.ifEmpty(newline);
        return this;
    }

    public Format4Json setNewline(boolean needNewLine) {
        this.newline = needNewLine ? "\n" : "";
        return this;
    }

    public String getNewline() {
        return newline;
    }

    public Format4Json setIndent(String indent) {
        this.indent = StringUtil.ifEmpty(indent);
        return this;
    }

    public Format4Json setIndent(boolean needIndent) {
        this.indent = needIndent ? "    " : "";
        return this;
    }

    public String getIndent() {
        return indent;
    }

    public String getCurrentIndent() {
        return StringUtil.repeat(indent, level);
    }

    public Format4Json setSpace(String space) {
        this.space = StringUtil.ifEmpty(space);
        return this;
    }

    public Format4Json setSpace(boolean needSpace) {
        this.space = needSpace ? " " : "";
        return this;
    }

    public String getSpace() {
        return space;
    }

    public Format4Json addLevel() {
        level ++;
        return this;
    }

    public Format4Json reduceLevel() {
        level --;
        return this;
    }

    public Format4Json setNeedRootNode(boolean needRootNode) {
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

    public Format4Json append(String content) {
        stbr.append(content);
        return this;
    }

    public Format4Json clearStringBuilder() {
        stbr.setLength(0);
        return this;
    }

    public StringBuilder getStringBuilder() {
        return stbr;
    }
}
