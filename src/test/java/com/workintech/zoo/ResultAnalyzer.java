package com.workintech.zoo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResultAnalyzer implements TestWatcher, AfterAllCallback {
    private final List<TestResultStatus> testResultsStatus = new ArrayList<>();
    private static final String taskId = "156";

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        testResultsStatus.add(TestResultStatus.DISABLED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        testResultsStatus.add(TestResultStatus.FAILED);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Map<TestResultStatus, Long> summary = testResultsStatus.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long success = summary.getOrDefault(TestResultStatus.SUCCESSFUL, 0L);
        long failure = summary.getOrDefault(TestResultStatus.FAILED, 0L);

        double score = (success + failure) == 0 ? 0.0 : (double) success / (success + failure);

        String userId = "213994";

        JSONObject json = new JSONObject();
        json.put("score", score);
        json.put("taskId", taskId);
        json.put("userId", userId);
        sendTestResult(json.toString());
    }

    private void sendTestResult(String result) throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("https://coursey-gpt-backend.herokuapp.com/nextgen/taskLog/saveJavaTasks");
            StringEntity params = new StringEntity(result);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
