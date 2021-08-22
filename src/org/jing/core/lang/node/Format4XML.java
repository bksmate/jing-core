package org.jing.core.lang.node;

import org.jing.core.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2021-08-21 <br>
 */
public class Format4XML {
    private String indent = "    ";

    private boolean expandEmptyNode = false;

    private String newLine = "\n";

    private int level = 0;

    private boolean needHead = true;

    private String headVersion = "1.0";

    private String headEncoding = "UTF-8";

    private boolean needNodeAttr = true;

    private LinkedHashMap<String, String> headAttrMap = null;

    public Format4XML(String indent, boolean expandEmptyNode, String newLine) {
        this.indent = indent;
        this.expandEmptyNode = expandEmptyNode;
        this.newLine = newLine;
    }

    public Format4XML(String indent, boolean expandEmptyNode, boolean newLine) {
        this.indent = indent;
        this.expandEmptyNode = expandEmptyNode;
        this.newLine = newLine ? "\n" : "";
    }

    public Format4XML() {}

    public Format4XML setIndent(String indent) {
        this.indent = indent;
        return this;
    }

    public Format4XML setExpandEmptyNode(boolean expandEmptyNode) {
        this.expandEmptyNode = expandEmptyNode;
        return this;
    }

    public Format4XML setNewLine(String newLine) {
        this.newLine = newLine;
        return this;
    }

    public Format4XML setNewLine(boolean newLine) {
        this.newLine = newLine ? "\n" : "";
        return this;
    }

    public String getIndent() {
        return indent;
    }

    public String getNewLine() {
        return newLine;
    }

    public boolean isExpandEmptyNode() {
        return expandEmptyNode;
    }

    public int getLevel() {
        return level;
    }

    public Format4XML addLevel() {
        level ++;
        return this;
    }

    public Format4XML reduceLevel() {
        level --;
        return this;
    }

    public String getCurrentIndent() {
        return StringUtil.repeat(StringUtil.ifEmpty(indent), level);
    }

    public String getHeadVersion() {
        return headVersion;
    }

    public Format4XML setHeadVersion(String headVersion) {
        this.headVersion = headVersion;
        return this;
    }

    public String getHeadEncoding() {
        return headEncoding;
    }

    public Format4XML setHeadEncoding(String headEncoding) {
        this.headEncoding = headEncoding;
        return this;
    }

    public Format4XML headAttr(String name, String value) {
        if (null == headAttrMap) {
            headAttrMap = new LinkedHashMap<>();
        }
        headAttrMap.put(name, value);
        return this;
    }

    public String headAttr(String name) {
        if (null == headAttrMap) {
            return "";
        }
        return StringUtil.getMapString(headAttrMap, name);
    }

    public Format4XML setNeedHead(boolean needHead) {
        this.needHead = needHead;
        return this;
    }

    public String getHead() {
        if (!needHead) {
            return "";
        }
        StringBuilder stbr = new StringBuilder();
        stbr.append("<?xml version=\"").append(headVersion).append("\"").append(" encoding=\"").append(headEncoding).append("\"");
        if (null != headAttrMap) {
            for (Map.Entry<String, String> attr : headAttrMap.entrySet()) {
                stbr.append(" ").append(attr.getKey()).append("=\"").append(StringUtil.ifEmpty(attr.getValue())).append("\"");
            }
        }
        stbr.append("?>").append(getNewLine()).append(getNewLine());
        return stbr.toString();
    }

    public boolean isNeedNodeAttr() {
        return needNodeAttr;
    }

    public Format4XML setNeedNodeAttr(boolean needNodeAttr) {
        this.needNodeAttr = needNodeAttr;
        return this;
    }

    public static Format4XML getZpFormat(boolean needHead) {
        Format4XML format = new Format4XML("", true, false);
        format.setNeedHead(needHead);
        format.setNeedNodeAttr(false);
        return format;
    }
}
