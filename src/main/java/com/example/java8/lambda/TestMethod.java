package com.example.java8.lambda;

public class TestMethod {
    private String testStr;
    public TestMethod() {
        this.testStr = "new no has param test";
    }
    public TestMethod(String str) {
        this.testStr = str;
    }
    public static String test(String str) {
        return str;
    }
    public void voidTest(String str) {
        System.out.println(str);
    }
    public String stringTest(String str) {
        return str;
    }
    public String getTestStr() {
        return testStr;
    }
}
