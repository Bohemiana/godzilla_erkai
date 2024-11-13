/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package core.ui;

import core.ApplicationConfig;
import core.ApplicationContext;
import core.Db;
import core.EasyI18N;
import core.shell.ShellEntity;
import core.ui.ShellManage;
import core.ui.component.DataView;
import core.ui.component.ShellGroup;
import core.ui.component.dialog.AppSeting;
import core.ui.component.dialog.GOptionPane;
import core.ui.component.dialog.GenerateShellLoder;
import core.ui.component.dialog.PluginManage;
import core.ui.component.frame.LiveScan;
import core.ui.component.frame.ShellSetting;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import util.Log;
import util.automaticBindClick;
import util.functions;

public class MainActivity
extends JFrame {
    private static MainActivity mainActivityFrame;
    private static JMenuBar menuBar;
    private JMenu targetMenu;
    private JMenu aboutMenu;
    private JMenu attackMenu;
    private JMenu configMenu;
    private static JMenu pluginMenu;
    private DataView shellView;
    private JScrollPane shellViewScrollPane;
    private static JPopupMenu shellViewPopupMenu;
    private Vector<String> columnVector;
    private JSplitPane splitPane;
    private ShellGroup shellGroupTree;
    private String currentGroup;
    private JLabel statusLabel;

    private static void initStatic() {
        menuBar = new JMenuBar();
        pluginMenu = new JMenu("\u63d2\u4ef6");
        shellViewPopupMenu = new JPopupMenu();
    }

    public MainActivity() {
        ApplicationContext.init();
        this.initVariable();
        EasyI18N.installObject(this);
    }

    private void initVariable() {

        this.setTitle(EasyI18N.getI18nString("哥斯拉   V%s by: BeichenDream Github:https://github.com/BeichenDream/Godzilla   godzillaerkai by bohemian", "4.01"));
        this.setLayout(new BorderLayout(2, 2));
        this.currentGroup = "/";
        this.statusLabel = new JLabel("status");
        Vector<Vector<String>> rows = Db.getAllShell();
        this.columnVector = rows.get(0);
        rows.remove(0);
        this.shellView = new DataView(null, this.columnVector, -1, -1);
        this.refreshShellView();
        this.shellView.setSelectionMode(2);
        this.splitPane = new JSplitPane(1);
        this.shellGroupTree = new ShellGroup();
        this.splitPane.setLeftComponent(new JScrollPane(this.shellGroupTree));
        this.shellViewScrollPane = new JScrollPane(this.shellView);
        this.splitPane.setRightComponent(this.shellViewScrollPane);
        this.add(this.splitPane);
        this.add((Component)this.statusLabel, "South");
        this.targetMenu = new JMenu("\u76ee\u6807");
        JMenuItem addShellMenuItem = new JMenuItem("\u6dfb\u52a0");
        addShellMenuItem.setActionCommand("addShell");
        this.targetMenu.add(addShellMenuItem);
        this.attackMenu = new JMenu("\u7ba1\u7406");
        JMenuItem shellLiveScanMenuItem = new JMenuItem("\u5b58\u6d3b\u626b\u63cf");
        shellLiveScanMenuItem.setActionCommand("shellLiveScan");
        JMenuItem generateShellMenuItem = new JMenuItem("\u751f\u6210");
        generateShellMenuItem.setActionCommand("generateShell");
        this.attackMenu.add(generateShellMenuItem);
        this.attackMenu.add(shellLiveScanMenuItem);
        this.configMenu = new JMenu("\u914d\u7f6e");
        JMenuItem pluginConfigMenuItem = new JMenuItem("\u63d2\u4ef6\u914d\u7f6e");
        pluginConfigMenuItem.setActionCommand("pluginConfig");
        JMenuItem appConfigMenuItem = new JMenuItem("\u7a0b\u5e8f\u914d\u7f6e");
        appConfigMenuItem.setActionCommand("appConfig");
        this.configMenu.add(appConfigMenuItem);
        this.configMenu.add(pluginConfigMenuItem);
        this.aboutMenu = new JMenu("\u5173\u4e8e");
        JMenuItem aboutMenuItem = new JMenuItem("\u5173\u4e8e");
        aboutMenuItem.setActionCommand("about");
        this.aboutMenu.add(aboutMenuItem);
        automaticBindClick.bindMenuItemClick(this.targetMenu, null, this);
        automaticBindClick.bindMenuItemClick(this.attackMenu, null, this);
        automaticBindClick.bindMenuItemClick(this.configMenu, null, this);
        automaticBindClick.bindMenuItemClick(this.aboutMenu, null, this);
        this.shellGroupTree.setActionDbclick(e -> {
            this.currentGroup = this.shellGroupTree.GetSelectFile().trim();
            this.refreshShellView();
        });
        menuBar.add(this.targetMenu);
        menuBar.add(this.attackMenu);
        menuBar.add(this.configMenu);
        menuBar.add(this.aboutMenu);
        menuBar.add(pluginMenu);
        this.setJMenuBar(menuBar);
        JMenuItem copyselectItem = new JMenuItem("\u590d\u5236\u9009\u4e2d");
        copyselectItem.setActionCommand("copyShellViewSelected");
        JMenuItem interactMenuItem = new JMenuItem("\u8fdb\u5165");
        interactMenuItem.setActionCommand("interact");
        JMenuItem interactCacheMenuItem = new JMenuItem("\u8fdb\u5165\u7f13\u5b58");
        interactCacheMenuItem.setActionCommand("interactCache");
        JMenuItem removeShell = new JMenuItem("\u79fb\u9664");
        removeShell.setActionCommand("removeShell");
        JMenuItem editShell = new JMenuItem("\u7f16\u8f91");
        editShell.setActionCommand("editShell");
        JMenuItem refreshShell = new JMenuItem("\u5237\u65b0");
        refreshShell.setActionCommand("refreshShellView");
        shellViewPopupMenu.add(interactMenuItem);
        shellViewPopupMenu.add(interactCacheMenuItem);
        shellViewPopupMenu.add(copyselectItem);
        shellViewPopupMenu.add(removeShell);
        shellViewPopupMenu.add(editShell);
        shellViewPopupMenu.add(refreshShell);
        this.shellView.setRightClickMenu(shellViewPopupMenu);
        automaticBindClick.bindMenuItemClick(shellViewPopupMenu, null, this);
        this.addEasterEgg();
        functions.setWindowSize(this, 1500, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
    }

    private void addEasterEgg() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == 112 && ApplicationContext.easterEgg) {
                    ApplicationContext.easterEgg = false;
                    GOptionPane.showMessageDialog(MainActivity.getFrame(), EasyI18N.getI18nString("Hacker\u6280\u672f\u5b66\u7684\u518d\u597d, \u5374\u65e0\u6cd5\u5165\u4fb5\u4f60\u7684\u5fc3,\n\u670d\u52a1\u5668\u5165\u4fb5\u7684\u518d\u591a,\u5bf9\u4f60\u53ea\u6709Guest,\n\u662f\u6211\u7684DDOS\u9020\u6210\u4e86\u4f60\u7684\u62d2\u7edd\u670d\u52a1\uff1f\n\u8fd8\u662f\u6211\u7684WebShell\u518d\u6b21\u88ab\u4f60\u67e5\u6740\uff1f\n\u4f60\u603b\u6709\u9632\u706b\u5899\n\u6211\u59cb\u7ec8\u505c\u4e0d\u6389\n\u60f3\u63d0\u6743\n\u65e0\u5948JSP+MYSQL\u6210\u4e3a\u6211\u4eec\u7684\u969c\u788d\n\u627e\u4e0d\u5230\u4f60\u7684\u6ce8\u5165\u70b9\n\u626b\u4e0d\u51fa\u4f60\u7684\u7a7a\u53e3\u4ee4\n\u6240\u6709\u5bf9\u6211\u7684\u56de\u5e94\u90fd\u75283DES\u52a0\u5bc6\n\u4f60\u603b\u662f\u81ea\u5b9a\u4e49\u6587\u4ef6\u683c\u5f0f\n\u6211\u6c38\u8fdc\u627e\u4e0d\u5230\u4f60\u7684\u5165\u53e3\u70b9\n\u5ffd\u7565\u6240\u6709\u5f02\u5e38\n\u5374\u8fd8\u662f\u8ddf\u8e2a\u4e0d\u5230\u4f60\u7684\u6ce8\u518c\u7801\n\u662f\u4f60\u592a\u8fc7\u5b8c\u7f8e,\u8fd8\u662f\u6211\u592a\u83dc?\n\u867d\u7136\u6211\u4eec\u662f\u4e0d\u540c\u7684\u5bf9\u8c61,\u90fd\u6709\u9690\u79c1\u7684\u4e00\u9762,\n\u4f46\u6211\u76f8\u4fe1\u603b\u6709\u4e00\u5929\u6211\u4f1a\u627e\u5230\u4f60\u7684\u63a5\u53e3,\u628a\u6211\u7684\u6700\u771f\u7ed9\u4f60\u770b!\n\u56e0\u4e3a\u6211\u662f\u4f60\u7684\u6307\u9488,\u5728\u832b\u832b\u5185\u5b58\u7684\u5806\u6808\u4e2d, \u6c38\u8fdc\u6307\u5411\u4f60\u90a3\u7247\u5929\u7a7a,\u4e0d\u5b5c\u4e0d\u5026!\n\u6211\u613f\u505a\u4f60\u7684\u5185\u8054,\u4f9b\u4f60\u65e0\u9650\u6b21\u7684\u8c03\u7528,\u76f4\u5230\u6d77\u67af\u77f3\u70c2!\n\u6211\u613f\u505a\u4f60\u7684\u5f15\u7528,\u548c\u4f60\u540c\u8fdb\u9000\u5171\u751f\u6b7b,\u4e00\u8d77\u7ecf\u53d7\u8003\u9a8c!\n\u53ea\u662f\u6211\u4e0d\u613f\u82e6\u82e6\u5730\u8c03\u8bd5\u4f60\u7684\u5fc3\u60c5,\u6700\u7ec8\u6ca6\u4e3a\u4f60\u7684\u53cb\u5143!\n\u5982\u4eca\u6211\u4eec\u5df2\u88abMFC\u5c01\u88c5--\u4e8b\u4e8b\u53d8\u8fc1!\n\u5982\u4eca\u6211\u4eec\u5df2\u5411COM\u8d70\u53bb--\u53ef\u60f3\u5f53\u5e74!\n\u6ca1\u4efb\u4f55\u5962\u6c42,\u53ea\u613f\u505a\u4f60\u6700\u540e\u7684System!\n\u6e17\u900f\u73a9\u7684\u518d\u5f3a,\u6211\u4e5f\u4e0d\u80fd\u63d0\u6743\u8fdb\u4f60\u7684\u5fc3\n\u514d\u6740\u73a9\u7684\u518d\u72e0,\u6211\u4e5f\u8fc7\u4e0d\u4e86\u4f60\u7684\u4e3b\u9632\u5fa1\n\u5916\u6302\u5199\u7684\u518d\u53fc,\u6211\u4e5f\u4e0d\u80fd\u64cd\u63a7\u4f60\u5bf9\u6211\u7684\u7231\n\u7f16\u7a0b\u73a9\u7684\u518d\u597d,\u6211\u4e5f\u4e0d\u80fd\u5199\u51fa\u5b8c\u7f8e\u7684\u7231\u60c5\n\u7eb5\u4f7f\u6211\u591a\u4e48\u7684\u4e0d\u53ef\u4e00\u4e16,\u4e5f\u4e0d\u662f\u4f60\u7684System\n\u63d0\u6743\u4e86\u518d\u591a\u7684\u670d\u52a1\u5668\uff0c\u5374\u6c38\u8fdc\u6210\u4e0d\u4e86\u4f60\u7684Root\n**But...... **\n\u90a3\u6015\u4f60\u7684\u5fc3\u518d\u5f3a\u5927\uff0c\u6211\u67090day\u5728\u624b\n\u4e3b\u52a8\u9632\u5fa1\u518d\u725b\uff0c\u6211\u6709R0\n\u51fb\u8d25\u4f60\u53ea\u662f\u65f6\u95f4\u95ee\u9898, \u5c31\u7b97\u80fd\u64cd\u63a7\uff0c\u4f60\u7684\u5fc3\u65e9\u5df2\u7ecf\u4e0d\u5c5e\u4e8e\u6211\n\u5df2\u88ab\u5343\u4ebaDownLoad\n\u5b8c\u7f8e\u7684\u7231\u60c5\u5199\u51fa\u6765\u80fd\u600e\u6837\uff0c\u7ec8\u7a76\u4f1a\u50cf\u6e38\u620f\u4e00\u6837\u7ed3\u675f\n\u4e0d\u662f\u4f60\u7684System\u4e5f\u7f62\uff0c\u4f46\u6211\u6709Guest\u7528\u6237\uff0c\u65e9\u665a\u63d0\u6743\u8fdb\u5165\u4f60\u7684\u7ba1\u7406\u5458\u7ec4\n\n\u4e5f\u8bb8\uff0c\u50cf\u4f60\u8bf4\u7684\u90a3\u6837\uff0c\u6211\u4eec\u662f\u4e0d\u540c\u4e16\u754c\u7684\u4eba\uff0c\u56e0\u4e3a\u6211\u662f\u4e5e\u4e10\u800c\u4e0d\u662f\u9a91\u58eb\n\u4eba\u53d8\u4e86\uff0c\u662f\u56e0\u4e3a\u5fc3\u8ddf\u7740\u751f\u6d3b\u5728\u53d8\n\u4eba\u751f\u6709\u68a6\uff0c\u5404\u81ea\u7cbe\u5f69\n\u71d5\u96c0\u5b89\u77e5\u9e3f\u9e44\u4e4b\u5fd7!"), "\u63d0\u793a", -1);
                    return true;
                }
                return false;
            }
        });
    }

    private void addShellMenuItemClick(ActionEvent e) {
        ShellSetting manage = new ShellSetting(null, this.currentGroup);
        this.refreshShellView();
    }

    private void generateShellMenuItemClick(ActionEvent e) {
        GenerateShellLoder generateShellLoder = new GenerateShellLoder();
    }

    private void shellLiveScanMenuItemClick(ActionEvent e) {
        LiveScan liveScan = new LiveScan(this.currentGroup);
    }

    private void pluginConfigMenuItemClick(ActionEvent e) {
        PluginManage pluginManage = new PluginManage();
    }

    private void appConfigMenuItemClick(ActionEvent e) {
        AppSeting appSeting = new AppSeting();
    }

    private void aboutMenuItemClick(ActionEvent e) {
        GOptionPane.showMessageDialog(MainActivity.getFrame(), EasyI18N.getI18nString("\u7531BeichenDream\u5f3a\u529b\u9a71\u52a8\n"), "About", -1);
    }

    private void copyShellViewSelectedMenuItemClick(ActionEvent e) {
        int columnIndex = this.shellView.getSelectedColumn();
        if (columnIndex != -1) {
            Object o = this.shellView.getValueAt(this.shellView.getSelectedRow(), this.shellView.getSelectedColumn());
            if (o != null) {
                String value = (String)o;
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
                GOptionPane.showMessageDialog(MainActivity.getMainActivityFrame(), "\u590d\u5236\u6210\u529f", "\u63d0\u793a", 1);
            } else {
                GOptionPane.showMessageDialog(MainActivity.getMainActivityFrame(), "\u9009\u4e2d\u5217\u662f\u7a7a\u7684", "\u63d0\u793a", 2);
            }
        } else {
            GOptionPane.showMessageDialog(MainActivity.getMainActivityFrame(), "\u672a\u9009\u4e2d\u5217", "\u63d0\u793a", 2);
        }
    }

    private void removeShellMenuItemClick(ActionEvent e) {
        Object[] shellIds = this.getSlectedShellId();
        if (shellIds.length > 0) {
            int n = GOptionPane.showConfirmDialog(MainActivity.getMainActivityFrame(), String.format(EasyI18N.getI18nString("\u786e\u5b9a\u5220\u9664id\u5728 %s \u7684shell\u5417?"), Arrays.toString(shellIds)), "\u8b66\u544a", 0);
            if (n == 0) {
                for (int i = 0; i < shellIds.length; ++i) {
                    Object shellId = shellIds[i];
                    String shshellInfo = Db.getOneShell((String)shellId).toString();
                    Log.log("removeShell status:%s  -> %s", Db.removeShell((String)shellId) > 0, shshellInfo);
                }
                GOptionPane.showMessageDialog(MainActivity.getMainActivityFrame(), "\u5220\u9664\u6210\u529f", "\u63d0\u793a", 1);
                this.refreshShellView();
            } else if (n == 1) {
                GOptionPane.showMessageDialog(MainActivity.getMainActivityFrame(), "\u5df2\u53d6\u6d88");
            }
        }
    }

    private String[] getSlectedShellId() {
        int[] rows = this.shellView.getSelectedRows();
        String[] shellIds = new String[rows.length];
        for (int i = 0; i < shellIds.length; ++i) {
            shellIds[i] = (String)this.shellView.getValueAt(rows[i], 0);
        }
        return shellIds;
    }

    private void editShellMenuItemClick(ActionEvent e) {
        String[] shellIds = this.getSlectedShellId();
        if (shellIds.length > 0) {
            for (int i = 0; i < shellIds.length; ++i) {
                String shellId = shellIds[i];
                ShellSetting shellSetting = new ShellSetting(shellId, this.currentGroup);
            }
        }
    }

    private void interactMenuItemClick(ActionEvent e) {
        try {
            String shellId = (String)this.shellView.getValueAt(this.shellView.getSelectedRow(), 0);
            ShellManage shellManage = new ShellManage(Db.getOneShell(shellId));
        } catch (Throwable err) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(stream);
            err.printStackTrace(printStream);
            printStream.flush();
            printStream.close();
            GOptionPane.showMessageDialog(MainActivity.getMainActivityFrame(), new String(stream.toByteArray()));
        }
    }

    private void interactCacheMenuItemClick(ActionEvent e) {
        String shellId = (String)this.shellView.getValueAt(this.shellView.getSelectedRow(), 0);
        try {
            if (new File(String.format("%s/%s/cache.db", "GodzillaCache", shellId)).isFile()) {
                ShellEntity shellEntity = Db.getOneShell(shellId);
                shellEntity.setUseCache(true);
                ShellManage shellManage = new ShellManage(shellEntity);
            } else {
                GOptionPane.showMessageDialog(MainActivity.getMainActivityFrame(), "\u7f13\u5b58\u6587\u4ef6\u4e0d\u5b58\u5728");
            }
        } catch (Throwable err) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(stream);
            err.printStackTrace(printStream);
            printStream.flush();
            printStream.close();
            GOptionPane.showMessageDialog(MainActivity.getMainActivityFrame(), new String(stream.toByteArray()));
        }
    }

    public void refreshShellView() {
        Vector<Vector<String>> rowsVector = null;
        rowsVector = this.currentGroup.equals("/") ? Db.getAllShell() : Db.getAllShell(this.currentGroup);
        rowsVector.remove(0);
        this.shellView.AddRows(rowsVector);
        this.shellView.getModel().fireTableDataChanged();
        this.statusLabel.setText(String.format(EasyI18N.getI18nString("\u5171\u6709%d\u7ec4 \u6240\u6709\u6210\u5458\u6570:%d \u5f53\u524d\u7ec4\u662f:%s \u5f53\u524d\u7ec4\u6210\u5458\u6570:%d "), Db.getAllGroup().size(), Db.getAllShell().size() - 1, this.currentGroup, rowsVector.size()));
    }

    private void refreshShellViewMenuItemClick(ActionEvent e) {
        this.refreshShellView();
    }

    public MainActivity getJFrame() {
        return this;
    }

    public static MainActivity getFrame() {
        return mainActivityFrame;
    }

    public static JMenuItem registerPluginJMenuItem(JMenuItem menuItem) {
        return pluginMenu.add(menuItem);
    }

    public static void registerPluginPopMenu(PopupMenu popupMenu) {
        pluginMenu.add(popupMenu);
    }

    public static JMenu registerJMenu(JMenu menu) {
        return menuBar.add(menu);
    }

    public static JMenuItem registerShellViewJMenuItem(JMenuItem menuItem) {
        return shellViewPopupMenu.add(menuItem);
    }

    public static void registerShellViewPopupMenu(PopupMenu popupMenu) {
        shellViewPopupMenu.add(popupMenu);
    }

    public static MainActivity getMainActivityFrame() {
        return mainActivityFrame;
    }

    public static void main(String[] args) {
        try {
            ApplicationContext.initUi();
        } catch (Exception e) {
            Log.error(e);
        }
        MainActivity.initStatic();
        ApplicationConfig.invoke();
        MainActivity activity = new MainActivity();
        mainActivityFrame = activity.getJFrame();
    }
}

