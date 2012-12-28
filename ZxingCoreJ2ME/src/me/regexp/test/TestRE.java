/*
 * TestRE.java
 * JMUnit based test
 *
 * Created on Dec 27, 2012, 5:46:15 PM
 */
package me.regexp.test;

import me.regexp.*;
import jmunit.framework.cldc10.*;

/**
 * @author makeit
 */
public class TestRE extends TestCase {

    RE searchRegex;
    RE grepRegex;
    RE splitRegex;
    RE substRegex;
    final static String searchString = "abc123xyz";
    final static String searchPattern = "\\d+";
    final static String grepPattern = "a*b";
    final static String[] strs = new String[]{"foo", "aab", "zzz", "aaaab"};
    final static String splitSource = "xyzzyababbayyzabbbab123";
    final static String splitPattern = "[ab]+";
    final static String substin = "aaaabfooaaabgarplyaaabwackyb";
    final static String subst = "-";

    public TestRE() {
        //The first parameter of inherited constructor is the number of test cases
        super(4, "TestRE");

        searchRegex = new RE(searchPattern);
        grepRegex = new RE(grepPattern);
        splitRegex = new RE(splitPattern);
        substRegex = new RE(grepPattern);
    }

    public void test(int testNumber) throws Throwable {
        switch (testNumber) {
            case 0:
                matchtest();
                break;
            case 1:
                greptest();
                break;
            case 2:
                splittest();
                break;
            case 3:
                substtest();
                break;
            default:
                break;
        }
    }

//     @Test
    public void matchtest() {
        log("matchtest");
        log("match: " + searchRegex.match(searchString));
        log("ParenCount: " + searchRegex.getParenCount());
        for (int i = 0; i < searchRegex.getParenCount(); i++) {
            log("getParen(" + i + "): " + searchRegex.getParen(i));
        }
        log("MatchFlags: " + searchRegex.getMatchFlags());        
        log("");
        
        assertTrue(searchRegex.match(searchString));
    }

    public void substtest() {
        log("substtest");
        assertTrue(substRegex.match(substin));

        String result = substRegex.subst(substin, subst);
        log("subst result: " + result);
        assertNotNull(result);
    }

//    @Test
    public void splittest() {
        log("splittest");
        assertTrue(splitRegex.match(splitSource));
        log("ParenCount: " + splitRegex.getParenCount());
        for (int i = 0; i < splitRegex.getParenCount(); i++) {
            log("getParen(" + i + "): " + splitRegex.getParen(i));
        }
        String[] splitResult = splitRegex.split(splitSource);
        log("split length: " + splitResult.length);
        assertNotNull(splitResult);
        assertTrue(splitResult.length > 0);
        assertTrue(splitResult.length == 3);
        for (int i = 0; i < splitResult.length; i++) {
            log("splitResult[" + i + "]: " + splitResult[i]);
        }
    }

//    @Test
    public void greptest() {
        log("greptest");
        String[] result = grepRegex.grep(strs);
        assertNotNull(result);
        assertTrue(result.length > 0);
        assertTrue(result.length == 2);
        for (int i = 0; i < result.length; i++) {
            log("result[" + i + "]: " + result[i]);
        }
        assertTrue(true);
    }

    void log(String msg) {
        System.out.println(msg);
    }

    void log2(String msg) {
        System.out.print(msg);
    }
}
