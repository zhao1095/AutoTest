package com.course.config;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestngReporterListerner implements ITestListener {

    // 1. 定义报告对象 (注意：这里需要导入 com.aventstack.extentreports.ExtentReports)
    private static ExtentReports extent;

    // 2. 定义测试节点对象，使用 ThreadLocal 保证多线程安全
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    // 3. 测试开始前执行：初始化报告配置
    @Override
    public void onStart(ITestContext context) {
        // 设置报告生成的路径
        String reportPath = "test-output/ExtentReport.html";

        // 初始化 SparkReporter (负责外观)
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("自动化测试报告");
        sparkReporter.config().setReportName("功能测试执行结果");
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);

        // 初始化 ExtentReports (负责写入数据)
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // 添加系统环境信息（可选）
        extent.setSystemInfo("Tester", "YourName");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    // 4. 测试用例成功时执行
    @Override
    public void onTestSuccess(ITestResult result) {
        // 从 ThreadLocal 中获取当前测试节点
        ExtentTest test = extentTest.get();
        // 记录日志
        test.pass("测试用例执行成功");
    }

    // 5. 测试用例失败时执行
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        // 记录失败原因
        test.fail(result.getThrowable());
    }

    // 6. 测试用例跳过时执行
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        test.skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    // 7. 每个测试方法开始前：创建节点
    // (注意：ITestListener 接口本身没有这个方法，但我们可以利用 onTestStart)
    @Override
    public void onTestStart(ITestResult result) {
        // 创建测试节点，名字为当前方法名
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); // 存入 ThreadLocal
    }

    // 8. 测试全部结束后执行：刷新报告
    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush(); // 这一步非常重要，否则报告文件可能为空
        }
    }
}