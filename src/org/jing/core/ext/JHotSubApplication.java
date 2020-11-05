package org.jing.core.ext;

/**
 * Description: <br>
 *
 * @author: bks <br>
 * @createDate: 2020-11-05 <br>
 */
public abstract class JHotSubApplication {
    protected Class<? extends JHotApplication> mainApplication;

    protected JHotSubApplication(Class<? extends JHotApplication> mainApplication) throws Exception {
        this.mainApplication = mainApplication;
        startSubApp();
    }

    private void startSubApp() throws Exception {
        JClassLoader.getInstance().addRunningSubApplication(mainApplication.getName(), this);
    }

    protected void endSubApp() throws Exception {
        JClassLoader.getInstance().removeRunningSubApplication(mainApplication.getName(), this);
    }
}
