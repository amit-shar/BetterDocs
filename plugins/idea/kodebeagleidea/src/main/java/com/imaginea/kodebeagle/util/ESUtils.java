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

package com.imaginea.kodebeagle.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.imaginea.kodebeagle.object.WindowObjects;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ESUtils {

    private static WindowObjects windowObjects = WindowObjects.getInstance();
    private JSONUtils jsonUtils = new JSONUtils();

     public final void putContentsForFileInMap(final List<String> fileNames) {
        String esFileQueryJson = jsonUtils.getJsonForFileContent(fileNames);
        String esFileResultJson;
        esFileResultJson = getESResultJson(esFileQueryJson,
                windowObjects.getEsURL() + Constants.SOURCEFILE_SEARCH);
        JsonArray hitsArray = getJsonElements(esFileResultJson);

        for (JsonElement hits : hitsArray) {
            JsonObject hitObject = hits.getAsJsonObject();
            JsonObject sourceObject = hitObject.getAsJsonObject(Constants.SOURCE);
            //Replacing \r as it's treated as bad end of line character
            String fileContent = sourceObject.getAsJsonPrimitive(Constants.FILE_CONTENT).
                    getAsString().replaceAll("\r", "");
            String fileName = sourceObject.getAsJsonPrimitive(Constants.FILE_NAME).getAsString();
            windowObjects.getFileNameContentsMap().put(fileName, fileContent);
        }
    }

    public final String getContentsForFile(final String fileName) {
        Map<String, String> fileNameContentsMap =
                windowObjects.getFileNameContentsMap();

        if (!fileNameContentsMap.containsKey(fileName)) {
            putContentsForFileInMap(Arrays.asList(fileName));
        }
        String fileContent = fileNameContentsMap.get(fileName);

        return fileContent;
    }

    public final Map<String, String> getFileTokens(final String esResultJson) {
        Map<String, String> fileTokenMap = new HashMap<String, String>();
        JsonArray hitsArray = getJsonElements(esResultJson);

        for (JsonElement hits : hitsArray) {
            JsonObject hitObject = hits.getAsJsonObject();
            JsonObject sourceObject = hitObject.getAsJsonObject(Constants.SOURCE);
            String fileName = sourceObject.getAsJsonPrimitive(Constants.FILE).getAsString();
            //Extracting repoIds for future use
            int repoId = sourceObject.getAsJsonPrimitive(Constants.REPO_ID).getAsInt();
            String project = getProjectName(fileName);
            if (!windowObjects.getRepoNameIdMap().containsKey(project)) {
                windowObjects.getRepoNameIdMap().put(project, repoId);
            }

            String tokens = sourceObject.get(Constants.TOKENS).toString();
            fileTokenMap.put(fileName, tokens);
        }
        return fileTokenMap;
    }

    protected final JsonArray getJsonElements(final String esResultJson) {
        JsonReader reader = new JsonReader(new StringReader(esResultJson));
        reader.setLenient(true);
        JsonElement jsonElement = new JsonParser().parse(reader);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject hitsObject = jsonObject.getAsJsonObject(Constants.HITS);
        return hitsObject.getAsJsonArray(Constants.HITS);
    }


    public final String getESResultJson(final String esQueryJson, final String url) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            String encodedJson = URLEncoder.encode(esQueryJson, Constants.UTF_8);

            String esGetURL = url + encodedJson + Constants.UID + windowObjects.getBeagleId();
            String versionInfo = windowObjects.getOsInfo() + "  "
                    + windowObjects.getApplicationVersion() + "  "
                    + windowObjects.getPluginVersion();

            HttpGet getRequest = new HttpGet(esGetURL);
            getRequest.setHeader(Constants.USER_AGENT, versionInfo);

            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != Constants.HTTP_OK_STATUS) {
                throw new RuntimeException(Constants.FAILED_HTTP_ERROR
                        + response.getStatusLine().getStatusCode() + "  "
                        + response.getStatusLine().getReasonPhrase());
            }

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent()), Constants.UTF_8));
            String output;
            while ((output = bufferedReader.readLine()) != null) {
                stringBuilder.append(output);
            }
            bufferedReader.close();
            httpClient.getConnectionManager().shutdown();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return Constants.EMPTY_ES_URL;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Constants.EMPTY_ES_URL;
        } catch (IOException e) {
            e.printStackTrace();
            return Constants.EMPTY_ES_URL;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Constants.EMPTY_ES_URL;
        }
        return stringBuilder.toString();
    }

    public final String getRepoStars(final String repoStarsJson) {
        String repoStarResultJson;
        String stars = null;
        repoStarResultJson = getESResultJson(repoStarsJson,
                windowObjects.getEsURL() + Constants.REPOSITORY_SEARCH);
        JsonArray hitsArray = getJsonElements(repoStarResultJson);

        JsonObject hitObject = hitsArray.get(0).getAsJsonObject();
        JsonObject sourceObject = hitObject.getAsJsonObject(Constants.SOURCE);
        //Replacing \r as it's treated as bad end of line character
        stars = sourceObject.getAsJsonPrimitive(Constants.STARGAZERS_COUNT).getAsString();
        return stars;
    }

    public final String getProjectName(final String fileName) {
        //Project name is till 2nd '/'
        int startIndex = fileName.indexOf('/');
        int endIndex = fileName.indexOf('/', startIndex + 1);
        return fileName.substring(0, endIndex);
    }

    public final String extractRepoStars(final String repoName, final int repoId) {
        String stars;
        if (windowObjects.getRepoStarsMap().containsKey(repoName)) {
            stars = windowObjects.getRepoStarsMap().get(repoName).toString();
        } else {
            String repoStarsJson = jsonUtils.getRepoStarsJSON(repoId);
            stars = getRepoStars(repoStarsJson);
            windowObjects.getRepoStarsMap().put(repoName, stars);
        }
        return stars;
    }
}
