/**
 * Copyright 2021 Shulie Technology, Co.Ltd
 * Email: shulie@shulie.io
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shulie.instrument.simulator.compatible.jdk9.module;

import com.shulie.instrument.simulator.api.listener.EventListener;
import com.shulie.instrument.simulator.api.listener.ext.BuildingForListeners;
import com.shulie.instrument.simulator.compatible.module.ClassFileTransformerModuleAdaptor;
import com.shulie.instrument.simulator.compatible.transformer.AffectStatistic;
import com.shulie.instrument.simulator.compatible.transformer.SimulatorClassFileTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.Map;

/**
 * 模块类文件转换器
 *
 * @author xiaobin.zfb|xiaobin@shulie.io
 * @since 2020/12/9 8:07 下午
 */
public class ModuleClassFileTransformer extends SimulatorClassFileTransformer implements ClassFileTransformer {
    private final SimulatorClassFileTransformer delegate;
    private final ClassFileTransformerModuleAdaptor classFileTransformerModuleAdaptor;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public ModuleClassFileTransformer(SimulatorClassFileTransformer delegate, ClassFileTransformerModuleAdaptor classFileTransformerModuleAdaptor) {
        if (delegate == null) {
            throw new NullPointerException("delegate");
        }
        this.delegate = delegate;
        this.classFileTransformerModuleAdaptor = classFileTransformerModuleAdaptor;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return classFileTransformerModuleAdaptor.transform(null, loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
    }

    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return classFileTransformerModuleAdaptor.transform(module, loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
    }

    @Override
    public int getWatchId() {
        return delegate.getWatchId();
    }

    @Override
    public Map<Integer, EventListener> getEventListeners() {
        return delegate.getEventListeners();
    }

    @Override
    public List<BuildingForListeners> getAllListeners() {
        return delegate.getAllListeners();
    }

    @Override
    public Object getMatcher() {
        return delegate.getMatcher();
    }

    @Override
    public AffectStatistic getAffectStatistic() {
        return delegate.getAffectStatistic();
    }

    @Override
    public Map<String, File> getDumpResult() {
        return delegate.getDumpResult();
    }
}