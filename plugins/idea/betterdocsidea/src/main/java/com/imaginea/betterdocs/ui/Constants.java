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

package com.imaginea.betterdocs.ui;

import com.intellij.icons.AllIcons;

final class Constants {
    static final String PROJECTS = "Projects";
    static final String JAVA = "java";
    static final double DIVIDER_LOCATION = 0.2;
    static final String ALL_TAB = "All";
    static final String FEATURED_TAB = "Featured";
    static final int EDITOR_SCROLL_PANE_WIDTH = 200;
    static final int EDITOR_SCROLL_PANE_HEIGHT = 300;
    static final String BETTERDOCS = "BetterDocs";
    static final String IDEA_PLUGIN = "Idea-Plugin";
    static final String PLUGIN_ID = "betterdocsidea";
    static final String OS_NAME = "os.name";
    static final String OS_VERSION = "os.version";
    static final int UNIT_INCREMENT = 16;
    static final String ALT = "alt ";
    static final int NUM_KEY = 8;
    static final String BEAGLE_ID = "Beagle Id";
    static final String HELP_MESSAGE =
            "<html>Got nothing to search. To begin using, "
                    + "<br /> please select some code and hit <img src='"
                    + AllIcons.Actions.Refresh + "' /> <br/> "
                    + "<br/><b>Please Note:</b> We ignore import statements <br/>"
                    + "while searching - as part of our "
                    + "internal optimization. <br/> <i>So "
                    + "selecting import statements has no effect. </i></html>";
    static final String BETTER_DOCS_SETTINGS = "BetterDocs Settings";
    static final String COLUMN_SPECS = "pref, pref:grow";
    static final String ROW_SPECS = "pref, pref, pref, pref, pref, pref, pref";
    static final String ELASTIC_SEARCH_URL = "Elastic Search URL";
    static final String RESULTS_SIZE = "Results size";
    static final String DISTANCE_FROM_CURSOR = "Distance from cursor";
    static final String EXCLUDE_IMPORT_LIST = "Exclude imports";
    static final String HELP_TEXT =
            "Please enter comma separated regex"
                    + "(e.g. java.util.[A-Z][a-z0-9]*, org.slf4j.Logger)";
    static final String MAX_TINY_EDITORS = "Featured Count";
    static final Integer HELPTEXT_FONTSIZE = 12;
    static final String ES_URL = "esURL";
    static final String DISTANCE = "distance";
    static final String SIZE = "size";
    static final String ES_URL_DEFAULT = "http://labs.imaginea.com/betterdocs";
    static final int DISTANCE_DEFAULT_VALUE = 10;
    static final int SIZE_DEFAULT_VALUE = 30;
    static final int MAX_EDITORS_DEFAULT_VALUE = 10;
    static final String GITHUB_LINK = "https://github.com/";
    static final String GITHUB_ICON = "icons/github_icon.png";
    static final String RIGHT_CLICK_MENU_ITEM_TEXT =
            "<html><img src='%s'/>Go to GitHub";
    static final String OPEN_IN_NEW_TAB = "Open in New Tab";
    static final String BETTER_DOCS = "BetterDocs";
    static final String FETCHING_FILE_CONTENT = "Fetching file content ...";
    static final String REPO_STARS = "Repo Stars: ";

    private Constants() {
    }
}
