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

import com.android.build.api.transform.TransformInvocation
import com.hujiang.gradle.plugin.android.aspectjx.internal.cache.VariantCache
import com.hujiang.gradle.plugin.android.aspectjx.internal.model.AJXExtensionConfig
import org.gradle.api.Project

/**
 * class description here
 * @author simon
 * @version 1.0.0
 * @since 2018-04-23
 */
abstract class AbsProcedure {

    Project project
    AJXExtensionConfig ajxExtensionConfig
    VariantCache variantCache
    TransformInvocation transformInvocation

    AbsProcedure(Project project, VariantCache variantCache, TransformInvocation transformInvocation) {
        this.project = project
        if (transformInvocation != null) {
            this.transformInvocation = transformInvocation
        }

        if (variantCache != null) {
            this.variantCache = variantCache
            this.ajxExtensionConfig = variantCache.ajxCache.ajxExtensionConfig
        }
    }

    abstract boolean doWorkContinuously()
}
