package test;

import org.jing.core.lang.JingException;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-07-17 <br>
 */
public class CommonDemo {
    private CommonDemo() throws Exception {
        try {
            Integer.parseInt("s");
        }
        catch (Exception e) {
            JingException je = new JingException(e, e.getMessage());
            System.out.println(je.getMessage());
            throw je;
        }
    }

    public static void main(String[] args) throws Exception {
        new CommonDemo();
    }
}
