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

package com.imaginea.betterdocs.util;

import com.intellij.icons.AllIcons;

import java.awt.Color;

final class Constants {
     static final String JAVA_IO_TMP_DIR = "java.io.tmpdir";
     static final Color HIGHLIGHTING_COLOR = new Color(255, 250, 205);
     static final char DOT = '.';
     static final String IMPORT_LIST = "IMPORT_LIST";
     static final String IMPORT_STATEMENT = "IMPORT_STATEMENT";
     static final String IMPORT_VALUE = "JAVA_CODE_REFERENCE";
     static final String FILE_EXTENSION = "java";
     static final String FILE_CONTENT = "fileContent";
     static final String HITS = "hits";
     static final String SOURCE = "_source";
     static final String FILE = "file";
     static final String TOKENS = "tokens";
     static final String SOURCEFILE_SEARCH = "/sourcefile/_search?source=";
     static final String REPOSITORY_SEARCH = "/repository/_search?source=";
     static final String FAILED_HTTP_ERROR = "Connection Error: ";
     static final String USER_AGENT = "USER-AGENT";
     static final String UTF_8 = "UTF-8";
     static final int HTTP_OK_STATUS = 200;
     static final String REPO_ID = "repoId";
     static final String STARGAZERS_COUNT = "stargazersCount";
     static final String FILE_NAME = "fileName";
     static final String UID = "&uid=";
     static final String EMPTY_ES_URL =
            "<html>Elastic Search URL <br> %s <br> in idea settings is incorrect.<br> See "
                    + "<img src='" + AllIcons.General.Settings + "'/></html>";
     static final String CUSTOM_TOKENS_IMPORT_NAME = "custom.tokens.importName";
     static final String IMPORT_NAME = "importName";
     static final String LINE_NUMBERS = "lineNumbers";
     static final String SORT_ORDER = "desc";
     static final String ID = "id";
     static final String TYPEREPOSITORY_ID = "typerepository.id";
     static final String TYPESOURCEFILENAME_FILENAME = "typesourcefile.fileName";

     private Constants() {
    }
}
