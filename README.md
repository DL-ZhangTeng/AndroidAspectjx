AspectJX
===========

> [原作者说明文档](./README-old.md) 
> [原作者说明文档2](./README-old2.md) 

## 如何使用

* **插件引用(未发布请使用buildSrc方式)**

在项目根目录的build.gradle里依赖**AspectJX**

```groovy
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        // 已发布到mavenCentral仓库
        mavenCentral()
    }

    dependencies {
        // aspectj插件
        classpath 'com.github.DL-ZhangTeng:AndroidAspectjx:2.1.0'
    }
}
```

* **在app项目的build.gradle里应用插件**

```groovy
apply plugin: 'android-aspectjx'
//或者这样也可以
apply plugin: 'com.hujiang.android-aspectjx'
```

* **AspectJX配置**

**AspectJX**默认会处理所有的二进制代码文件和库，为了提升编译效率及规避部分第三方库出现的编译兼容性问题，**AspectJX**提供`include`、`exclude`命令来过滤需要处理的文件及排除某些文件(包括class文件及jar文件)。

**支持包名匹配**

```groovy
aspectjx {
    // 排除所有package路径中包含`android.support`的class文件及库（jar文件）
    exclude 'android.support'
}
```

> 注意事项1：规则的描述尽量用比较具体的范围，防止exclude的范围超出预期
> 注意事项2：注意apt编译期生成的类，比如项目的某个module模块用到glide注解生成类，生成的类会匹配上com.bumptech.glide导致整个module被过滤

**支持`*`和`**`匹配单独使用**

```groovy
aspectjx {
    // 忽略所有的class文件及jar文件，相当于AspectJX不生效
    exclude '*'
}
```

**提供enabled开关**

`enabled`默认为true，即默认**AspectJX**生效

```groovy
aspectjx {
    // 关闭AspectJX功能
    enabled false
}
```


## 适配情况

| 使用依赖 | 适配版本 |
| - | - |
| Android Gradle Plugin | 7.2.2 |
| Gradle | 7.3.3 |
| org.aspectj:aspectjtools | 1.9.9.1 |

## License

```txt
Copyright 2023 Swing

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

