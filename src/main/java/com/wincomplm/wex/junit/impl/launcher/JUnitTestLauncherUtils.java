/*
 * Copyright (c) 2022 Wincom Consulting S.L.
 * All Rights Reserved.
 * This source is subject to the terms of a software license agreement.
 * You shall not disclose such confidential information and shall use it only in accordance with the terms and conditions of the license agreement.
 */

package com.wincomplm.wex.junit.impl.launcher;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wincomplm.wex.kernel.impl.annotations.WexComponent;
import com.wincomplm.wex.kernel.impl.annotations.WexMethod;
import com.wincomplm.wex.security.commons.impl.WexAdminCheckAccess;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.openqa.selenium.net.HostIdentifier;

/**
 *
 * @author SimonHeath
 */
@WexComponent(uid = "junit-methods", description = "Test Methods")
public class JUnitTestLauncherUtils {
    
    @WexMethod(name = "runTest", description = "Run junit tests")
    public void runTest(HttpServletRequest httprequest, HttpServletResponse httpresponse,Class testClass) throws Exception {
        WexAdminCheckAccess.instance.checkAccess();
        String json = runTest(testClass);
        returnTest(httprequest, httpresponse, json);
    }
    
    public void returnTest(HttpServletRequest httprequest, HttpServletResponse httpresponse, String json) throws IOException {
        boolean pretty = httprequest.getParameter("pretty")!=null;
        if (pretty) httpresponse.getOutputStream().print( "<pre>");
        httpresponse.getOutputStream().print( json);
        if (pretty) httpresponse.getOutputStream().print( "</pre>");
    }

    protected String runTest(Class testClass) throws JsonProcessingException {
        HostIdentifier.getHostAddress();
        PrintStream ps = new PrintStream(System.out);
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(ps));
        Result result =  junit.run(testClass);
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result);
        return json;
    }
}
