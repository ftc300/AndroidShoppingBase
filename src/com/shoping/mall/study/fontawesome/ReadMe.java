package com.shoping.mall.study.fontawesome;

public class ReadMe {

	/**
	 * http://www.bubuko.com/infodetail-931909.html
	 * http://fortawesome.github.io/Font-Awesome/cheatsheet/
	 * http://www.iconfont.cn/ http://mux.alimama.com/posts/964
	 * 
	 * 
	 * 
	 * iconfont的优点
	 * 
	 * iconfont在web上的应用已经很广泛，如淘宝、一淘、sina、豆瓣等大网站都已经在自己的网站上应用了iconfont技术；
	 * 它能有效的解决分辨率的问题
	 * ，并且在应用的时候非常便捷，节省前端、设计很多重复改图、调色的工作。在移动设备上众多DPI设备更是一个困扰问题设计师、前端的大问题
	 * ；一个app在适配高低版本iphone手机及分辨率众多的安卓设备的时候设计师需要设计很多版本的图片
	 * ，而iconfont这种矢量图形就能很好解决这种重复设计的工作；
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * android使用font awesome替代简单的图标
	 * 
	 * 
	 * 
	 * 在android开发中，往往会有大量的小图标，可是android界面与html是不同的，比如html中，可以将大量的小图标制作成雪碧图，
	 * 这样会大量的减少http的请求次数，对于性能也是有很大的提升，而在android中，一般对于na本身tive
	 * app的小图标一般是用来做显示用的，都会内嵌到 应用
	 * ，两者也没有什么可比性，不过如果android应用中有大量的小图标，无形中就增加了apk的文件大小，这个时候就到了font awesome出场了。
	 * 
	 * 什么是font awesome
	 * 
	 * font
	 * awesome是一个专为Bootstrap设计的字体文件，我们可以通过向显示字体一样方便的显示我们想要显示的图标文件。对于android来讲
	 * ，可以使用字体来代替部分需要显示的小图片
	 * ，并且在这些字体中的图片都是矢量图，是可以放大和缩小的，这就意味着每个图标都能在所有大小的屏幕上完美呈现。好了，废话不多说，直接进入正题吧。
	 * 
	 * 在android上使用font awesome
	 * 
	 * 1.下载font awesome
	 * 
	 * 下载地址
	 * 
	 * 2.解压下载的压缩包
	 * 
	 * 将fonts目录下的fontawesome-webfont.ttf文件拷贝到asset文件夹下 技术分享
	 * 
	 * 3.编写string.xml
	 * 
	 * 首先需要编写string.xml文件，需要去http://fortawesome.github.io/Font-Awesome/
	 * cheatsheet/连接下寻找自己想要的字体图标对应的字符串。 <string name="heard">&#xf004;</string>
	 * <string name="fa_google_plus">&#xf0d5;</string> <string
	 * name="fa_save">&#xf0c7;</string> <string
	 * name="fa_thumbs_o_up">&#xf087;</string> <string
	 * name="fa_toggle_on">&#xf205;</string>
	 * 
	 * 这里每一个string中的值就是需要显示的图标对应的值，name的值可以随便给一个，不过一般都是一个有意义的名称。
	 * 
	 * 4.编写布局
	 * 
	 * 在textview中使用该字符串，就可以显示其对应的图标了，这里就替换了之前使用imageview来显示小图标了。方便了很多。
	 * <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
	 * xmlns:tools="http://schemas.android.com/tools"
	 * android:layout_width="match_parent" android:layout_height="match_parent"
	 * android:orientation="vertical" android:gravity="center_horizontal"
	 * android:padding="50dp" tools:context=".MainActivity" >
	 * 
	 * <TextView android:id="@+id/test_view" android:layout_width="wrap_content"
	 * android:layout_height="wrap_content" android:text="@string/heard"
	 * android:textAppearance="?android:attr/textAppearanceLarge"
	 * android:textColor="#Ff9834" android:textSize="30sp" /> <TextView
	 * android:id="@+id/fa_google_plus" android:layout_width="wrap_content"
	 * android:layout_height="wrap_content"
	 * android:text="@string/fa_google_plus"
	 * android:textAppearance="?android:attr/textAppearanceLarge"
	 * android:textColor="#87619a" android:textSize="50sp" /> <TextView
	 * android:id="@+id/fa_thumbs_o_up" android:layout_width="wrap_content"
	 * android:layout_height="wrap_content"
	 * android:text="@string/fa_thumbs_o_up"
	 * android:textAppearance="?android:attr/textAppearanceLarge"
	 * android:textColor="#976523" android:textSize="60sp" /> <TextView
	 * android:id="@+id/fa_save" android:layout_width="wrap_content"
	 * android:layout_height="wrap_content" android:text="@string/fa_save"
	 * android:textAppearance="?android:attr/textAppearanceLarge"
	 * android:textColor="#954278" android:textSize="40sp" /> <TextView
	 * android:id="@+id/fa_toggle_on" android:layout_width="wrap_content"
	 * android:layout_height="wrap_content" android:text="@string/fa_toggle_on"
	 * android:textAppearance="?android:attr/textAppearanceLarge"
	 * android:textColor="#273896" android:textSize="50sp" />
	 * 
	 * </LinearLayout>
	 * 
	 * 可以发现，这里我们可以自定义该图标的颜色和大小，这样在不同的屏幕适配也是极好的，很方便。
	 * 
	 * 5.代码中引用
	 * 
	 * 首先找到asset下对应的.ttf文件 Typeface font = Typeface.createFromAsset(getAssets(),
	 * "fontawesome-webfont.ttf");
	 * 
	 * 然后只需要为每一个textView setTypeface(font)即可。
	 * ((TextView)findViewById(R.id.fa_google_plus)).setTypeface(font);
	 * 
	 * 显示效果如下：
	 */
}
