/*
 * Copyright 2018 firefly1126, Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.gradle_plugin_android_aspectjx
 */
package com.hujiang.gradle.plugin.android.aspectjx.internal.procedure

import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.hujiang.gradle.plugin.android.aspectjx.internal.AJXTask
import com.hujiang.gradle.plugin.android.aspectjx.internal.AJXTaskManager
import com.hujiang.gradle.plugin.android.aspectjx.internal.cache.AJXCache
import com.hujiang.gradle.plugin.android.aspectjx.internal.cache.VariantCache
import org.gradle.api.Project

/**
 * class description here
 * @author simon* @version 1.0.0* @since 2018-04-23
 */
class UpdateAspectOutputProcedure extends AbsProcedure {
    AJXTaskManager ajxTaskManager

    UpdateAspectOutputProcedure(Project project, VariantCache variantCache, TransformInvocation transformInvocation) {
        super(project, variantCache, transformInvocation)
        AJXCache ajxCache = variantCache.ajxCache
        ajxTaskManager = new AJXTaskManager(encoding: ajxCache.encoding,
                ajcArgs: ajxCache.ajxExtensionConfig.ajcArgs,
                bootClassPath: ajxCache.bootClassPath,
                sourceCompatibility: ajxCache.sourceCompatibility,
                targetCompatibility: ajxCache.targetCompatibility)
    }

    @Override
    boolean doWorkContinuously() {
        project.logger.debug("~~~~~~~~~~~~~~~~~~~~update aspect output")
        ajxTaskManager.aspectPath << variantCache.aspectDir
        ajxTaskManager.classPath << variantCache.includeFileDir
        ajxTaskManager.classPath << variantCache.excludeFileDir

        def isAspectChanged = variantCache.incrementalStatus.isAspectChanged
        def isIncludeFileChanged = variantCache.incrementalStatus.isIncludeFileChanged
        if (isAspectChanged) {
            project.logger.warn("[ajx][$variantCache.variantName] aspect file changed, need rerun.")
        }
        if (isAspectChanged || isIncludeFileChanged) {
            //process class files
            AJXTask ajxTask = new AJXTask(project)
            File outputJar = transformInvocation.getOutputProvider().getContentLocation(
                    "include",
                    variantCache.contentTypes,
                    variantCache.scopes,
                    Format.JAR)
            FileUtils.deleteQuietly(outputJar)

            ajxTask.outputJar = outputJar.absolutePath
            ajxTask.inPath << variantCache.includeFileDir

            ajxTaskManager.addTask(ajxTask)
        }

        transformInvocation.inputs.each { TransformInput input ->
            input.jarInputs.each { JarInput jarInput ->
                if (!jarInput.file.exists()) {
                    // 文件不存在，忽略处理
                    return
                }
                ajxTaskManager.classPath << jarInput.file
                File outputJar = transformInvocation.getOutputProvider().getContentLocation(
                        jarInput.name,
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR)

                if (!outputJar.getParentFile()?.exists()) {
                    outputJar.getParentFile()?.mkdirs()
                }

                if (variantCache.isIncludeJar(jarInput.file.absolutePath)) {
                    // 规则文件变更，删除重新处理
                    if (isAspectChanged) {
                        FileUtils.deleteQuietly(outputJar)
                    }
                    if (!outputJar.exists()) {
                        AJXTask jarTask = new AJXTask(project)
                        jarTask.inPath << jarInput.file

                        jarTask.outputJar = outputJar.absolutePath

                        ajxTaskManager.addTask(jarTask)
                    }
                } else {
                    // 将不需要做AOP处理的文件原样copy到输出目录
                    FileUtils.copyFile(jarInput.file, outputJar)
                }
            }
        }

        ajxTaskManager.batchExecute()

        return true
    }
}
