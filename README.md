# jing-core

Lowest JDK `1.6`
---

Email
---
357166513@qq.com

Dependencies
---

* `log4j-1.2.17.jar`
* `log4j-api-2.3.jar`
* `log4j-core-2.3.jar`
* `dom4j-2.0.2.jar`

Functions
---

* 文件的基本操作, 读/写/删/压缩;
* 日期/字符串/类/转换等一些基本的工具;
* 配置文件读取/刷新配置文件缓存;
* 日志框架;
* 利用注解实现的服务映射;
* 初始化时根据配置自动加载指定的类;
* 随心所欲地为某个类分配一个多线程进行异步运行
* 独特的载体类, 可以和XML/JSON进行互相转换.

Attention
---

* 在一些情况下配置文件可能不能如愿的在执行脚本所在目录, 在`1.06`的版本里已经更新了`JING_HOME`的使用, 可以在`JING_HOME`指定的目录下读取`config`子目录里的`system.xml`进行初始化.
