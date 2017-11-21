# FlexButton
基于Google开源的流式布局框架FlexLayout，封装的一款流式布局标签库





一、引用方式
APP  gradle文件中配置


compile 'com.github.ywcai:FlexButton:v0.0.1'

Project gradle文件中配置

    repositories {
    
        jcenter()
        
        maven { url 'https://jitpack.io' }
        
    }
    
    
二、具体使用方式
    
在布局文件中直接引用组件，可在试图中看见组件的示列的样式

            <ywcai.ls.control.flex.FlexButtonLayout
            
            android:id="@+id/flex_button_test"
            
            android:layout_width="wrap_content"
            
            android:layout_height="wrap_content"
            
            app:flexWrap="wrap"
            
            app:flexDirection="row"
            
            app:justifyContent="space_between"
            
            app:btnBgColor="@color/LBlue"
            
            app:btnBorderColor="@color/LBlue"
            
            app:btnTextSize="10"></ywcai.ls.control.flex.FlexButtonLayout>
            
  
布局中标签的基础属性需要在布局文件中设置，

 

具体属性如下:

多选、单选模式，默认false，多选


 attr name="isSelectOnlyOne"  format="boolean"

多选模式下有效,默认true

 attr name="isShowSelectAll" format="boolean"
           
Layout中动态创建BUFFTON之间的MARGIN属性.   

 attr name="btnMarginTop" format="integer"
 
 attr name="btnMarginLeft" format="integer"
 
 attr name="btnMarginBottom" format="integer"
 
attr name="btnMarginRight" format="integer"


Layout中动态创建BUFFTON之间的长宽属性，可设置为match_parent|wrap_content 或者具体数值

attr name="btnWidth" format="integer"

attr name="btnHeight" format="integer"

Layout中动态创建BUFFTON的边框属性

attr name="btnBorderSize" format="integer"

Layout中动态创建BUFFTON的字体大小属性

attr name="btnTextSize" format="integer"

Layout中动态创建BUFFTON的边框弧度大小属性

attr name="btnRadius" format="integer"

Layout中动态创建BUFFTON的相关颜色属性

按钮边框颜色，选中或未选中均不会变

attr name="btnBorderColor" format="color"

被选择时按钮背景颜色

attr name="btnBgColor" format="color"

被选择时按钮文本颜色

attr name="btnTextColor" format="color"

点击聚焦时的按钮背景颜色

attr name="btnfocusColor" format="color"

禁用时的按钮背景颜色

attr name="btnDisplayBgColor" format="color"

禁用时的文本颜色

attr name="btnDisplayTextColor" format="color"

未选中情况下的文本颜色

attr name="btnUnSelectTextColor" format="color" 
    
   三 、activity中代码引用及主要常用方法
   
   
   
  FlexButtonLayout flex_button_test = (FlexButtonLayout) findViewById(R.id.flex_button_test);
        List<String> list = new ArrayList<>();
        list.add("标签一");
        list.add("标签一");
        list.add("标签N");

        int[] select = new int[list.size()];



//根据list的数据来动态生成所需要创建的标签 

flex_button_test.setDataAdapter(list);        

//多选模式下有效，设置默认被选择的标签


flex_button_test.setSelectIndex(select); 

        //单击标签的事件
        
        flex_button_test.setOnFlexButtonClickListener(new OnFlexButtonClickListener() {
            @Override
            
            
            public void clickItem(int i, boolean b) {
          
          //i代表当前点击的标签位置，b代表被点击后，当前是否处于被选择状态
           
           //true：被选择

//false：未被选择
          
          }
          
//单击全选、取消按钮的事件
          
          @Override
           
           public void clickAllBtn(int[] ints, boolean b) {
            //ints 所有标签的状态。被选择数组全为1，未被选择数组全为0；
            //若需要获取所有当前被选择标签的信息，在下面的方法中介绍
            //true：当前是全选状态
//false：当前是全不选状态
          
          }
       
       });
  
   
//如果想对标签分类，部分标签使用其他的颜色，可以通过该方法单独在配置数据源后设置。

//方法仅重绘需要该个按钮，不会全部重绘。

 void setBtnSecondaryBgColor(int pos, int secondaryColor)
 

//多选状态下，获取当前选择的标签返回int数组

//数组的索引和标签的索引对应，每个位置的值为1表示被选择、为0时标识未被选择

 int[] getSelectIndex() 

//单选状态下，获取当前选择的标签pos位置

int getCurrentIndex() 



四、

该组件完全继承自GOOGLE的FlexLayout组件，因此使用时对内部BUTTON的流式布局属性调整完成与FlexLayout使用方式相同，相关使用方法自行移步查看。
依赖的第三方包:


    compile 'com.google.android:flexbox:0.2.6'
    compile 'com.github.medyo:fancybuttons:1.8.3'
