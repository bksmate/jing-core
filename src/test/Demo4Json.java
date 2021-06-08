package test;

import org.jing.core.lang.Carrier;
import org.jing.core.util.CarrierUtil;
import org.jing.core.util.StringUtil;

import java.lang.Exception;
import java.util.LinkedHashMap;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class Demo4Json {
    private Demo4Json() throws Exception {
        LinkedHashMap map = new LinkedHashMap();
        String jsonContent = "{\"COL_LIST\":[\"ID\",\"NAME\",\"PARENT\",\"URL\",\"DESCRIPTION\",\"NOTES\"],\"PSWD\":\"sLNFQHLow+yxGScT1wrJ0FRoC1/wOP1oWswikP2zoBE3sZW7lrevHPrdoiQQzKCgTtQZZLVj/4VRvIAuW7SoiomLUxrSvIWZVqYzjSOCmyScCrsTs+U60flwUmIrwpG+R0KCsR0sjBGGIbvAofz2kVTHRcAis+L/3Bektq867ms=\",\"DRIVER\":\"com.mysql.cj.jdbc.Driver\",\"URL\":\"jdbc:mysql://localhost:3306/priv?serverTimezone=UTC&characterEncoding=gbk\",\"QRY_LIST\":[{\"NOTES\":\"基本信息\",\"DESCRIPTION\":\"PAGE_INDEX_BASE_DESC\",\"PARENT\":\"\",\"ID\":\"1\",\"URL\":\"pages/base/scripts/base.js\",\"NAME\":\"PAGE_INDEX_BASE\"},{\"NOTES\":\"主页\",\"DESCRIPTION\":\"PAGE_INDEX_HOMEPAGE_DESC\",\"PARENT\":\"1\",\"ID\":\"2\",\"URL\":\"pages/base/scripts/homepage.js\",\"NAME\":\"PAGE_INDEX_HOMEPAGE\"},{\"NOTES\":\"友情链接\",\"DESCRIPTION\":\"PAGE_INDEX_LINKS_DESC\",\"PARENT\":\"1\",\"ID\":\"3\",\"URL\":\"pages/base/scripts/links.js\",\"NAME\":\"PAGE_INDEX_LINKS\"},{\"NOTES\":\"用户信息\",\"DESCRIPTION\":\"PAGE_INDEX_PROFILE_DESC\",\"PARENT\":\"1\",\"ID\":\"4\",\"URL\":\"pages/base/scripts/profile.js\",\"NAME\":\"PAGE_INDEX_PROFILE\"},{\"NOTES\":\"管理入口\",\"DESCRIPTION\":\"PAGE_INDEX_ADMIN_DESC\",\"PARENT\":\"\",\"ID\":\"100\",\"URL\":\"pages/admin/scripts/admin.js\",\"NAME\":\"PAGE_INDEX_ADMIN\"},{\"NOTES\":\"基础信息管理\",\"DESCRIPTION\":\"PAGE_INDEX_ADMIN_BASE_DESC\",\"PARENT\":\"100\",\"ID\":\"101\",\"URL\":\"pages/admin/scripts/base.js\",\"NAME\":\"PAGE_INDEX_ADMIN_BASE\"},{\"NOTES\":\"农业银行\",\"DESCRIPTION\":\"PAGE_INDEX_ABC_DESC\",\"PARENT\":\"\",\"ID\":\"1100\",\"URL\":\"pages/abc/scripts/abc.js\",\"NAME\":\"PAGE_INDEX_ABC\"},{\"NOTES\":\"融易汇拼包\",\"DESCRIPTION\":\"PAGE_INDEX_ABC_BFC_PACK_DESC\",\"PARENT\":\"1100\",\"ID\":\"1101\",\"URL\":\"pages/abc/scripts/bfc-pack.js\",\"NAME\":\"PAGE_INDEX_ABC_BFC_PACK\"},{\"NOTES\":\"游戏专栏\",\"DESCRIPTION\":\"PAGE_INDEX_GAME_DESC\",\"PARENT\":\"\",\"ID\":\"1200\",\"URL\":\"pages/game/scripts/game.js\",\"NAME\":\"PAGE_INDEX_GAME\"},{\"NOTES\":\"新增游戏文章\",\"DESCRIPTION\":\"PAGE_INDEX_GAME_ARTICLE_ADD_DESC\",\"PARENT\":\"1200\",\"ID\":\"1201\",\"URL\":\"pages/game/scripts/article-add.js\",\"NAME\":\"PAGE_INDEX_GAME_ARTICLE_ADD\"},{\"NOTES\":\"游戏文章列表\",\"DESCRIPTION\":\"PAGE_INDEX_GAME_ARTICLE_LIST_DESC\",\"PARENT\":\"1200\",\"ID\":\"1202\",\"URL\":\"pages/game/scripts/article-list.js\",\"NAME\":\"PAGE_INDEX_GAME_ARTICLE_LIST\"},{\"NOTES\":\"游戏文章\",\"DESCRIPTION\":\"PAGE_INDEX_GAME_ARTICLE_DETAIL_DESC\",\"PARENT\":\"1202\",\"ID\":\"1203\",\"URL\":\"pages/game/scripts/article-detail.js\",\"NAME\":\"PAGE_INDEX_GAME_ARTICLE_DETAIL\"},{\"NOTES\":\"在线工具\",\"DESCRIPTION\":\"PAGE_INDEX_TOOLS_DESC\",\"PARENT\":\"\",\"ID\":\"2000\",\"URL\":\"pages/tools/scripts/tools.js\",\"NAME\":\"PAGE_INDEX_TOOLS\"},{\"NOTES\":\"格式化\",\"DESCRIPTION\":\"PAGE_INDEX_TOOLS_FORMAT_DESC\",\"PARENT\":\"2000\",\"ID\":\"2001\",\"URL\":\"pages/tools/format/scripts/format.js\",\"NAME\":\"PAGE_INDEX_TOOLS_FORMAT\"},{\"NOTES\":\"XML格式化\",\"DESCRIPTION\":\"PAGE_INDEX_TOOLS_FORMAT_XML_DESC\",\"PARENT\":\"2001\",\"ID\":\"2002\",\"URL\":\"pages/tools/format/scripts/xml.js\",\"NAME\":\"PAGE_INDEX_TOOLS_FORMAT_XML\"},{\"NOTES\":\"JSON格式化\",\"DESCRIPTION\":\"PAGE_INDEX_TOOLS_FORMAT_JSON_DESC\",\"PARENT\":\"2001\",\"ID\":\"2003\",\"URL\":\"pages/tools/format/scripts/json.js\",\"NAME\":\"PAGE_INDEX_TOOLS_FORMAT_JSON\"},{\"NOTES\":\"发包\",\"DESCRIPTION\":\"PAGE_INDEX_TOOLS_SEND_DESC\",\"PARENT\":\"2000\",\"ID\":\"2004\",\"URL\":\"pages/tools/send/scripts/send.js\",\"NAME\":\"PAGE_INDEX_TOOLS_SEND\"},{\"NOTES\":\"Socket通信\",\"DESCRIPTION\":\"PAGE_INDEX_TOOLS_SEND_SOCKET_DESC\",\"PARENT\":\"2004\",\"ID\":\"2005\",\"URL\":\"pages/tools/send/scripts/socket.js\",\"NAME\":\"PAGE_INDEX_TOOLS_SEND_SOCKET\"},{\"NOTES\":\"\",\"DESCRIPTION\":\"PAGE_INDEX_TOOLS_DATABASE_DESC\",\"PARENT\":\"2000\",\"ID\":\"2006\",\"URL\":\"pages/tools/database/scripts/database.js\",\"NAME\":\"PAGE_INDEX_TOOLS_DATABASE\"},{\"NOTES\":\"\",\"DESCRIPTION\":\"PAGE_INDEX_TOOLS_DATABASE_QRY_DESC\",\"PARENT\":\"2006\",\"ID\":\"2007\",\"URL\":\"pages/tools/database/scripts/query.js\",\"NAME\":\"PAGE_INDEX_TOOLS_DATABASE_QRY\"}],\"NAME\":\"jing\",\"SQL\":\"SELECT * FROM PAGE_LIST\"}";
        Carrier jsonC = Carrier.parseJson(jsonContent);
        String result = jsonC.asJson(StringUtil.isNotEmpty("Y"), StringUtil.repeat(" ", 4));
        System.out.println(result);
    }

    public static void main(String[] args) throws Exception {
        new Demo4Json();
    }
}
