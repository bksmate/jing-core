package org.jing.core.json;

import org.jing.core.util.StringUtil;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-05-31 <br>
 */
@SuppressWarnings("unused") public class JsonFormat {
    private static final String DEFAULT_INDENT = "    ";

    private static final String DEFAULT_NEWLINE = "\r\n";

    private static final String DEFAULT_SPACE = " ";

    private String newline;

    private String indent;

    private String space = DEFAULT_SPACE;

    private int level;

    public JsonFormat(boolean needNewline, String indent) {
        this.newline = needNewline ? DEFAULT_NEWLINE : null;
        this.indent = indent;
        this.level = 0;
    }

    public JsonFormat(String newline, boolean needIndent) {
        this.newline = newline;
        this.indent = needIndent ? DEFAULT_INDENT : null;
        this.level = 0;
    }

    public JsonFormat(boolean needNewline, boolean needIndent) {
        this.newline = needNewline ? DEFAULT_NEWLINE : null;
        this.indent = needIndent ? DEFAULT_INDENT : null;
        this.level = 0;
    }

    public JsonFormat(String newline, String indent) {
        this.newline = newline;
        this.indent = indent;
        this.level = 0;
    }

    public JsonFormat() {
        this.newline = null;
        this.indent = null;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getNewline() {
        return null == newline ? "" : newline;
    }

    public void setNewline(String newline) {
        this.newline = newline;
    }

    public void setNewline(boolean needNewline) {
        this.newline = DEFAULT_NEWLINE;
    }

    public String getIndent() {
        return null == indent ? "" : StringUtil.repeat(indent, level);
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public void setIndent(boolean needIndent) {
        this.indent = needIndent ? DEFAULT_INDENT : null;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel() {
        this.level ++;
    }

    public void reduceLevel() {
        this.level --;
    }

    public String getDefaultNewline() {
        return DEFAULT_NEWLINE;
    }
}
