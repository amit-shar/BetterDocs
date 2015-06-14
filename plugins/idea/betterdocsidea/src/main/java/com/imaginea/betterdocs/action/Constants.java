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

package com.imaginea.betterdocs.action;

import com.intellij.icons.AllIcons;

 final class Constants {
     static final String COLLAPSE_TREE = "Collapse Tree";
     static final String OPEN_SETTINGS = "Open Settings";
     static final String BETTER_DOCS_SETTINGS = "BetterDocs Settings";
     static final String EXPAND_TREE = "Expand Tree";
     static final String BETTER_DOCS = "BetterDocs";
     static final String EMPTY_ES_URL =
            "<html>Elastic Search URL <br> %s <br> in idea settings is incorrect.<br> See "
                    + "<img src='" + AllIcons.General.Settings + "'/></html>";
     static final String ES_URL = "esURL";
     static final String DISTANCE = "distance";
     static final String SIZE = "size";
     static final String BETTERDOCS_SEARCH = "/betterdocs/_search?source=";
     static final String ES_URL_DEFAULT = "http://labs.imaginea.com/betterdocs";
     static final int DISTANCE_DEFAULT_VALUE = 10;
     static final int SIZE_DEFAULT_VALUE = 30;
     static final String EDITOR_ERROR = "Could not get any active editor";
     static final String FORMAT = "%s %s %s";
     static final String QUERYING = "Querying";
     static final String FOR = "for";
     static final String EXCLUDE_IMPORT_LIST = "Exclude imports";
     static final String HELP_MESSAGE =
            "<html>Got nothing to search. To begin using, "
                    + "<br /> please select some code and hit <img src='"
                    + AllIcons.Actions.Refresh + "' /> <br/> "
                    + "<br/><b>Please Note:</b> We ignore import statements <br/>"
                    + "while searching - as part of our "
                    + "internal optimization. <br/> <i>So "
                    + "selecting import statements has no effect. </i></html>";
     static final String QUERY_HELP_MESSAGE =
            "<html><body> <p> <i><b>We tried querying our servers with : </b></i> <br /> %s </p>"
                    + "<i><b>but found no results in response.</i></b>"
                    + "<p> <br/><b>Tip:</b> Try narrowing your selection to fewer lines. "
                    + "<br/>Alternatively, setup \"Exclude imports\" in settings <img src='"
                    + AllIcons.General.Settings + "'/> "
                    + "</p></body></html>";
     static final String REPO_SCORE = "Score: ";
     static final String BANNER_FORMAT = "%s %s %s";
     static final String HTML_U = "<html><u>";
     static final String U_HTML = "</u></html>";
     static final String FILETYPE_HELP = "<html><center>Currently BetterDocs supports "
            + "\"java\" files only.</center></html>";
     static final String REPO_BANNER_FORMAT = "%s %s";
     static final String GITHUB_LINK = "https://github.com/";
     static final String GOTO_GITHUB = "Go to GitHub";
     static final String FETCHING_PROJECTS = "Fetching projects...";
     static final String FETCHING_FILE_CONTENTS = "Fetching file contents...";
     static final String BETTERDOCS = "Betterdocs";
     static final double INDICATOR_FRACTION = 0.5;
     static final int MAX_EDITORS_DEFAULT_VALUE = 10;
     static final String MAX_TINY_EDITORS = "maxTinyEditors";
     static final String PROJECT_ERROR = "Unable to get Project. Please Try again";
     static final String JAVA = "java";

    private Constants() {
    }

}
