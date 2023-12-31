# Change Log

> 版本号定义遵循：[语义化版本 2.0.0](https://semver.org/lang/zh-CN/)

## 2.0.14 (2021-12-07)

- 修复：Dump类不支持多线程可能导致的多线程错误问题

## 2.0.13 (2021-11-18)

- 修复：在增量构建场景下，打开被删除的文件导致构建异常的问题
- 优化：构建结束后关闭所有已打开的jar文件
- 优化：线程池使用结束后关闭

## 2.0.12 (2021-11-09)

- 修复：在增量构建场景下，打开被删除的文件导致构建异常的问题

## 2.0.11 (2021-10-12)

- 修复：多线程构建错误问题（最常见就是产生异常`java.util.zip.ZipException: zip file is empty`）

    > 相关issue：[324](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx/issues/324)、[327](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx/issues/327)

- 修复：多变种构建时间随着变种数量增多暴涨问题

    > 相关issue：[305](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx/issues/305)

- 修复：aspectj织入发生错误时未终止构建

- 拷贝TABLESWITCH.java，修复循环i++后超过int最大值导致索引为负数的错误问题

## 更多历史版本

[原作者版本信息](CHANGELOG-old.md)

