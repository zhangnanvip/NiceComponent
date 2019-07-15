#### NiceBottomSheet(底部弹窗)

<img src="nicebottomsheet/images/bottomsheet.gif" width="400"/>

使用方法：

```
fun showBottomSheet() {
    NiceBottomSheet.newInstance()
        // default title view
        .configDefaultTitleView { titleView ->
            // do something with titleView
            return@configDefaultTitleView "文字"
        }
        .setUpdateUIListener {
              // custom title view
              it.setTitleView(TextView(this).apply {
                  text = "文字"
                  gravity = Gravity.CENTER
                  setBackgroundColor(Color.YELLOW)
              }).setContentView(ll)
        }
        .showBySelfGrowthTag(supportFragmentManager)
}
```

可配置项：

1.最小高度（setMinHeight）

2.顶部间距（setMarginTop）

3.拖动开关（banDrop）

4.默认头部（configDefaultTitleView）与自定义头部（setTitleLayoutResId or setTitleView）