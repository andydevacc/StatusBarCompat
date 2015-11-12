#StatusBarCompat  
StatusBarCompat 能让Android 4.4实现Material Design风格的状态栏（包括状态栏变色，Drawer能在状态栏绘制）

##效果图

- 4.4  

<img src="https://github.com/luckyandyzhang/StatusBarCompat/blob/master/art/s_common_4.png" width="300">  

**Style.FILL**  
<img src="https://github.com/luckyandyzhang/StatusBarCompat/blob/master/art/s_fill_4.png" width="300">  

**Style.NORMAL**  
<img src="https://github.com/luckyandyzhang/StatusBarCompat/blob/master/art/s_normal_4.png" width="300">  

- 5.0  

<img src="https://github.com/luckyandyzhang/StatusBarCompat/blob/master/art/s_common_5.png" width="300">  

**Style.FILL**  
<img src="https://github.com/luckyandyzhang/StatusBarCompat/blob/master/art/s_fill_5.png" width="300">  

**Style.NORMAL**  
<img src="https://github.com/luckyandyzhang/StatusBarCompat/blob/master/art/s_normal_5.png" width="300">  

##使用方法

在 `build.gradle` 加入如下依赖：

```groovy
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.android.support:appcompat-v7:23.1.0'
    compile 'com.android.support:design:23.1.0'
    compile 'com.github.luckyandyzhang:StatusBarCompat:1.0.1'
}	
```

在 `setContentView` 后调用 `StatusBarCompact.init`即可

```java
setContentView(R.layout.activity_main);
StatusBarCompact.init(this,Color.parseColor("#303F9F");
```

##样式
- Style.NORMAL (默认选项)
- Style.FILL (请配合DrawerLayout和NavigationView使用)

如果是带有Drawer的布局，请使用官方的 DrawerLayout + NavigationView（ScrimInsetsFrameLayout）  
使用的时候需要注意：  

1. DrawerLayout必须是`android:fitsSystemWindows="false"`  
2. DrawerLayout的子视图必须是`android:fitsSystemWindows="true"`

以下是用Android Studio的模板生成的带有Drawer的布局  

**`activity_main.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.DrawerLayout 
	...
    android:fitsSystemWindows="false">

    <include
        layout="@layout/app_bar_main"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <android.support.design.widget.NavigationView  
        ...
        android:fitsSystemWindows="true"/>

</android.support.v4.widget.DrawerLayout>
```
**`app_bar_main.xml`**

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.design.widget.CoordinatorLayout   
    ...
    android:fitsSystemWindows="true">
    ...
    ...
</android.support.design.widget.CoordinatorLayout>

```














