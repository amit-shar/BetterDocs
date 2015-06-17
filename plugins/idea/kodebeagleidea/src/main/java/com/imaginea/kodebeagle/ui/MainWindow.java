/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.imaginea.kodebeagle.ui;

import com.imaginea.kodebeagle.util.WindowEditorOps;
import com.imaginea.kodebeagle.action.CollapseProjectTreeAction;
import com.imaginea.kodebeagle.action.EditSettingsAction;
import com.imaginea.kodebeagle.action.ExpandProjectTreeAction;
import com.imaginea.kodebeagle.action.RefreshAction;
import com.imaginea.kodebeagle.object.WindowObjects;

import com.intellij.ide.actions.ActivateToolWindowAction;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.KeymapManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.treeStructure.Tree;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainWindow implements ToolWindowFactory {
    private WindowEditorOps windowEditorOps = new WindowEditorOps();
    private WindowObjects windowObjects = WindowObjects.getInstance();
    private PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();

    @Override
    public final void createToolWindowContent(final Project project, final ToolWindow toolWindow) {
        initSystemInfo();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(Constants.PROJECTS);

        JTree jTree = new Tree(root);
        jTree.setRootVisible(false);
        jTree.setAutoscrolls(true);

        if (!propertiesComponent.isValueSet(Constants.BEAGLE_ID)) {
            windowObjects.setBeagleId(UUID.randomUUID().toString());
            propertiesComponent.setValue(Constants.BEAGLE_ID, windowObjects.getBeagleId());
        } else {
            windowObjects.setBeagleId(propertiesComponent.getValue(Constants.BEAGLE_ID));
        }

        Document document = EditorFactory.getInstance().createDocument("");
        Editor windowEditor = EditorFactory.getInstance().
                createEditor(document, project, FileTypeManager.getInstance().
                        getFileTypeByExtension(Constants.JAVA), false);

        final RefreshAction refreshAction = new RefreshAction();

        addKeyBoardShortcut();
        EditSettingsAction editSettingsAction = new EditSettingsAction();
        ExpandProjectTreeAction expandProjectTreeAction = new ExpandProjectTreeAction();
        CollapseProjectTreeAction collapseProjectTreeAction = new CollapseProjectTreeAction();

        windowObjects.setTree(jTree);
        windowObjects.setWindowEditor(windowEditor);

        DefaultActionGroup group = new DefaultActionGroup();
        group.add(refreshAction);
        group.addSeparator();
        group.add(expandProjectTreeAction);
        group.add(collapseProjectTreeAction);
        group.addSeparator();
        group.add(editSettingsAction);
        final JComponent toolBar = ActionManager.getInstance().
                createActionToolbar(Constants.KODEBEAGLE, group, true).getComponent();
        toolBar.setBorder(BorderFactory.createCompoundBorder());

        toolBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, toolBar.getMinimumSize().height));
        toolBar.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (toolBar.isShowing()) {
                    try {
                        refreshAction.init();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        });



        JBScrollPane jTreeScrollPane = new JBScrollPane();
        jTreeScrollPane.getViewport().setBackground(JBColor.white);
        jTreeScrollPane.setAutoscrolls(true);
        jTreeScrollPane.setBackground(JBColor.white);
        windowObjects.setJTreeScrollPane(jTreeScrollPane);


        final JSplitPane jSplitPane = new JSplitPane(
                        JSplitPane.VERTICAL_SPLIT, windowEditor.getComponent(), jTreeScrollPane);
        jSplitPane.setResizeWeight(Constants.DIVIDER_LOCATION);

        JPanel editorPanel = new JPanel();
        editorPanel.setOpaque(true);
        editorPanel.setBackground(JBColor.white);
        editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));

        final JBScrollPane editorScrollPane = new JBScrollPane();
        editorScrollPane.getViewport().setBackground(JBColor.white);
        editorScrollPane.setViewportView(editorPanel);
        editorScrollPane.setAutoscrolls(true);
        editorScrollPane.setPreferredSize(new Dimension(Constants.EDITOR_SCROLL_PANE_WIDTH,
                Constants.EDITOR_SCROLL_PANE_HEIGHT));
        editorScrollPane.getVerticalScrollBar().setUnitIncrement(Constants.UNIT_INCREMENT);
        editorScrollPane.setHorizontalScrollBarPolicy(JBScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        windowObjects.setPanel(editorPanel);

        final JTabbedPane jTabbedPane = new JBTabbedPane();
        jTabbedPane.add(Constants.FEATURED_TAB, editorScrollPane);
        jTabbedPane.add(Constants.ALL_TAB, jSplitPane);
        refreshAction.setJTabbedPane(jTabbedPane);
        // Display initial help information here.
        refreshAction.showHelpInfo(Constants.HELP_MESSAGE);
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout((new BoxLayout(mainPanel, BoxLayout.Y_AXIS)));
        mainPanel.add(toolBar);
        mainPanel.add(jTabbedPane);

        toolWindow.getComponent().getParent().add(mainPanel);
        //Dispose the editor once it's no longer needed
        windowEditorOps.releaseEditor(project, windowEditor);
    }

    private void addKeyBoardShortcut() {
        Keymap keymap = KeymapManager.getInstance().getActiveKeymap();
        if (keymap != null) {
            KeyboardShortcut defShortcut =
                    new KeyboardShortcut(KeyStroke.
                            getKeyStroke(Constants.ALT + Constants.NUM_KEY), null);
            String actionId =
                    ActivateToolWindowAction.getActionIdForToolWindow(Constants.KODEBEAGLE);
            keymap.addShortcut(actionId, defShortcut);
        }
    }

    private void initSystemInfo() {
        windowObjects.setOsInfo(System.getProperty(Constants.OS_NAME) + "/"
                + System.getProperty(Constants.OS_VERSION));
        windowObjects.setApplicationVersion(ApplicationInfo.getInstance().getVersionName()
                + "/" + ApplicationInfo.getInstance().getBuild().toString());
        IdeaPluginDescriptor codeBeagleVersion =
                            PluginManager.getPlugin(PluginId.getId(Constants.PLUGIN_ID));

        if (codeBeagleVersion != null) {
            windowObjects.setPluginVersion(Constants.IDEA_PLUGIN + "/"
                    + codeBeagleVersion.getVersion());
        }
    }
}
