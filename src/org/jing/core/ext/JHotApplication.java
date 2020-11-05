package org.jing.core.ext;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-11-05 <br>
 */
public abstract class JHotApplication implements Runnable {
    @Override
    public void run() {
        try {
            execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void execute() throws Exception;
}
