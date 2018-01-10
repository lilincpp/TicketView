# TicketView
通过简单配置，合成常见的票、卷样式。

### 原理
通过继承`Drawable`及利用`Xfermode`来达到相关效果。

### 效果展示
![](https://github.com/lilincpp/TicketView/blob/master/picture/demo_effect.png)

### 注意事项
如果是在`XML`中配置，必须要设置`android:background`属性。

## 配置

1. gradle
```
<dependency>
  <groupId>com.github.lilincpp</groupId>
  <artifactId>ticketview</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

2. android
```
compile 'com.github.lilincpp:ticketview:1.0.0'
```

### 未来计划

- [ ] 边缘描边
- [ ] 边缘阴影
- [ ] 更多修饰形状的实现
- [x] 圆形修饰形状的实现
- [x] 动态配置虚线的实现
- [x] 绘制形状与背景相交后，使得背景透明
- [x] 可以给任何View使用

### License

TicketView binaries and source code can be used according to the [Apache License, Version 2.0](https://github.com/lilincpp/TicketView/blob/master/LICENSE).

