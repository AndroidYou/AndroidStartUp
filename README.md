# AndroidStartUp 安卓启动框架
## 背景
Android应用程序在启动过程中，一般会在Application中初始化大量第三方SDK,大多数使用子线程去初始化，但是如果有些SDK初始化具体前后关联性，那么仅仅使用子线程去初始化肯定是不够的。
当不同初始化任务之间具有依赖性，需要使用根据任务之间的关联性进行排序，依次初始化。
## 使用说明

## 版本迭代
### 1.0.0 

1.区分子线程与主线程。

2.具有依赖性的任务能够按顺序初始化。

3.主线程与子线程之间能够互相切换执行。
