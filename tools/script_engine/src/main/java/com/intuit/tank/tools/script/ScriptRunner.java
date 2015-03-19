/**
 * Copyright 2011 Intuit Inc. All Rights Reserved
 */
package com.intuit.tank.tools.script;

/*
 * #%L
 * External Script Engine
 * %%
 * Copyright (C) 2011 - 2015 Intuit Inc.
 * %%
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * #L%
 */

import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * ScriptRunner
 * 
 * @author dangleton
 * 
 */
public class ScriptRunner {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(ScriptRunner.class);

    /**
     * 
     */
    public ScriptRunner() {
        super();
    }

    /**
     * 
     * @param script
     * @param engine
     * @param inputs
     * @param output
     * @return
     * @throws ScriptException
     */
    public ScriptIOBean runScript(@Nonnull String script, @Nonnull ScriptEngine engine,
            @Nonnull Map<String, Object> inputs, OutputLogger output) throws ScriptException {
        return runScript(null, script, engine, inputs, output);
    }

    /**
     * 
     * @param scriptName
     * @param script
     * @param engine
     * @param inputs
     * @param output
     * @return
     * @throws ScriptException
     */
    public ScriptIOBean runScript(@Nullable String scriptName, @Nonnull String script, @Nonnull ScriptEngine engine,
            @Nonnull Map<String, Object> inputs, OutputLogger output) throws ScriptException {
        Reader reader = null;
        ScriptIOBean ioBean = null;
        try {
            reader = new StringReader(script);
            ioBean = new ScriptIOBean(inputs, output);
            engine.put("ioBean", ioBean);
            ioBean.println("Starting test...");
            engine.eval(reader, engine.getContext());
            ioBean.println("Finished test...");
        } catch (ScriptException e) {
            throw new ScriptException(e.getMessage(), scriptName, e.getLineNumber(), e.getColumnNumber());
        } finally {
            IOUtils.closeQuietly(reader);
        }
        return ioBean;
    }

}