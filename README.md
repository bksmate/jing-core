# jing-core

Lowest JDK `1.8`
---

Email
---
357166513@qq.com

Dependencies
---

Optional
---
* `log4j-1.2.17.jar`
* `log4j-api-2.3.jar`
* `log4j-core-2.3.jar`

Functions
---

* 文件的基本操作, 读/写/删/压缩;
* 日期/字符串/类/转换等一些基本的工具;
* 配置文件读取/刷新配置文件缓存/动态独立配置文件读取;
* 日志框架;
* 利用注解实现的服务映射;
* 初始化时根据配置自动加载指定的类;
* 随心所欲地为某个类分配一个多线程进行异步运行
* 独特的载体类, 可以和XML/JSON进行互相转换;
* 良好的Socket服务端父类.

Attention
---

* 升级到1.30版本后, 不再使用dom4j的依赖
* 配置文件读取顺序为JING_HOME/config/jing-system.xml, classpath:jing-system.xml, 最后是内部常量类Const里的默认配置