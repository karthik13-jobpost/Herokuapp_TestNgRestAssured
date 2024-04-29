package com.sample.reports;

import com.sample.utils.DateUtils;
import com.sample.constants.Constants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public final class ExtentReportManager {

    private static ExtentReports extentReports;
    private static String reportPath = "";

    public static void initReports() {
        if (Objects.isNull(extentReports)) {
            extentReports = new ExtentReports();

            reportPath = createExtentReportPath();

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            extentReports.attachReporter(spark);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle(Constants.REPORT_TITLE);
            spark.config().setReportName(Constants.REPORT_TITLE);
            extentReports.setSystemInfo("Framework Name", Constants.REPORT_TITLE);
            extentReports.setSystemInfo("Author", "Karthik Sivaraj");

            System.out.println("Extent Reports is installed.");
        }
    }

    public static String createExtentReportPath() {
        String link = "";
        if (Constants.OVERRIDE_REPORTS.trim().equals(Constants.NO)) {
            System.out.println("OVERRIDE_REPORTS = " + Constants.OVERRIDE_REPORTS);
            link = Constants.EXTENT_REPORT_FOLDER_PATH + File.separator + DateUtils.getCurrentDateTimeCustom("_") + "_"
                    + Constants.EXTENT_REPORT_FILE_NAME;
            System.out.println("Created link report: " + link);
            return link;
        } else {
            link = Constants.EXTENT_REPORT_FILE_PATH;
            System.out.println("Created link report: " + link);
            return link;
        }
    }

    public static void openReports(String reportPath) {
        if (Constants.OPEN_REPORTS_AFTER_EXECUTION.trim().equalsIgnoreCase(Constants.YES)) {
            try {
                Desktop.getDesktop().browse(new File(reportPath).toURI());
            } catch (FileNotFoundException fileNotFoundException) {
                try {
                    throw new IOException("Extent Report file you are trying to reach is not found", fileNotFoundException.fillInStackTrace());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException ioException) {
                try {
                    throw new IOException("Extent Report file you are trying to reach got IOException while reading the file", ioException.fillInStackTrace());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void flushReports() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
        ExtentTestManager.unload();
        openReports(reportPath);
    }

    public static void createTest(String testCaseName) {
        // ExtentManager.setExtentTest(extent.createTest(testCaseName));
        ExtentTestManager.setExtentTest(extentReports.createTest(testCaseName));
    }

    public static void createTest(String testCaseName, String description) {
        // ExtentManager.setExtentTest(extent.createTest(testCaseName));
        ExtentTestManager.setExtentTest(extentReports.createTest(testCaseName, description));
    }

    public static void removeTest(String testCaseName) {
        // ExtentManager.setExtentTest(extent.createTest(testCaseName));
        extentReports.removeTest(testCaseName);
    }

    public static void logMessage(String message) {
        ExtentTestManager.getExtentTest().log(Status.INFO, message);
    }

    public static void logMessage(Status status, String message) {
        ExtentTestManager.getExtentTest().log(status, message);
    }

    public static void logMessage(Status status, Object message) {
        ExtentTestManager.getExtentTest().log(status, (Throwable) message);
    }

    public static void pass(String message) {
        //System.out.println("ExtentReportManager class: " + ExtentTestManager.getExtentTest());
        ExtentTestManager.getExtentTest().pass(message);
    }

    public static void pass(Markup message) {
        ExtentTestManager.getExtentTest().pass(message);
    }

    public static void fail(String message) {
        ExtentTestManager.getExtentTest().fail(message);
    }

    public static void fail(Object message) {
        ExtentTestManager.getExtentTest().fail((String) message);
    }

    public static void fail(Markup message) {
        ExtentTestManager.getExtentTest().fail(message);
    }

    public static void skip(String message) {
        ExtentTestManager.getExtentTest().skip(message);
    }

    public static void skip(Markup message) {
        ExtentTestManager.getExtentTest().skip(message);
    }

    public static void info(Markup message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    public static void info(String message) {
        ExtentTestManager.getExtentTest().info(message);
    }

    public static void warning(String message) {
        ExtentTestManager.getExtentTest().log(Status.WARNING, message);
    }

}
