package utils;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ReportUtils extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        System.out.println("Test FAILED: " + tr.getName());
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        System.out.println("Test PASSED: " + tr.getName());
    }
}

