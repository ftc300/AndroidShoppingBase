package com.shoping.mall.study.alarm;

public class ReadMe {

	/**
	 * 
	 * http://blog.csdn.net/fengyuzhengfan/article/details/38417935?utm_source=tuicool
	 * http://www.androidchina.net/2972.html#rd
	 * 
	 * 该DEMO将会通过AlarmManager来周期的调用ChangeService，从而让系统实现定时更换壁纸的功能。
	 * 
	 * 更换壁纸的API为android.app.WallpaperManager，它提供了clear()方法来清除壁纸，还提供了如下方法来设置壁纸。
	 * 
	 * setResource(int resid)将壁纸设置为resid资源所代表的图片
	 * 
	 * setBitmap(Bitmap bitmap)将壁纸设置为bitmap所代表的位图
	 * 
	 * setStream(InputStream data)将壁纸设置为data数据所代表的图片
	 * 
	 * Android中的AlarmManager实质上是一个全局的定时器，是Android中常用的一种系统级
	 * 
	 * 别的提示服务，在指定时间或周期性启动其它组件
	 * 
	 * （包括Activity,Service,BroadcastReceiver）。
	 * 
	 * 一、概述：
	 * 
	 * 该类提供一种访问系统闹钟服务的方式，允许你去设置在将来的某个时间点去执行你的应用程序。当你的闹钟响起（时间到）时，在它上面注册的一个意图(
	 * Intent)将会被系统以广播发出，然后自动启动目标程序，如果它没有正在运行。注册的闹钟会被保留即使设备处于休眠中(
	 * 如果闹钟在给定时间响起可以选择是否唤醒设备)。如果闹钟关闭或者重启，闹钟将被清除。
	 * 
	 * 只要广播的onReceive()方法正在执行，这闹钟管理者(AlarmManager)会持有一个CPU唤醒锁，
	 * 这是为了保证手机不会休眠直到处理完该广播
	 * ，一旦onReceive()返回，那么闹钟管理者将会释放唤醒锁。这意味着只要OnReceive()方法完成，
	 * 你的手机可能在某些情况下进入休眠，如果你的闹钟广播接收者调用的是Context
	 * .startService()，那么手机有可能在被请求的服务执行之前进入休眠
	 * ，为了防止这种情况，你的BroadcastReceiver和服务需要实现一个单独的唤醒锁策略以确保手机继续运行，直到服务可用。
	 * 
	 * 此处注意：该类适用于你想让应用程序在将来某个指定时间点执行的情况，即使你的应用程序现在没有运行。对一般的时间操作，
	 * 使用Handler是更容易和更有效率的。
	 * 
	 * 二、公有方法(Public Methods)：
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * void cancel(PendingIntent operation)
	 * 
	 * 
	 * 取消AlarmManager的定时服务。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * void set(inttype,longtriggerAtTime, PendingIntent operation)
	 * 
	 * 
	 * 设置在triggerAtTime时间启动由operation参数指定的组件。（该方法用于设置一次性闹钟）
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * void setInexactRepeating(inttype,longtriggerAtTime,longinterval,
	 * PendingIntent operation)
	 * 
	 * 
	 * 设置一个非精确的周期性任务。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * void setRepeating(inttype,longtriggerAtTime,longinterval, PendingIntent
	 * operation)
	 * 
	 * 
	 * 设置一个周期性执行的定时服务。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * void setTime(longmillis)
	 * 
	 * 
	 * 设置系统“墙”时钟。需要android.permission.SET_TIME.权限。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * void setTimeZone(String timeZone)
	 * 
	 * 
	 * 设置系统的默认时区。需要android.permission.SET_TIME_ZONE.权限。
	 * 
	 * 三、常用方法说明：
	 * 
	 * AlarmManager的常用方法有三个：
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * set(inttype,longstartTime,PendingIntent pi)
	 * 
	 * 
	 * 该方法用于设置一次性闹钟。
	 * 
	 * 第一个参数int type指定定时服务的类型，该参数接受如下值：
	 * 
	 * ELAPSED_REALTIME：
	 * 在指定的延时过后，发送广播，但不唤醒设备（闹钟在睡眠状态下不可用）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒。
	 * 
	 * ELAPSED_REALTIME_WAKEUP： 在指定的延时过后，发送广播，并唤醒设备（即使关机也会执行operation所对应的组件） 。
	 * 延时是要把系统启动的时间SystemClock.elapsedRealtime()算进去的，具体用法看代码。
	 * 
	 * RTC： 指定当系统调用System.currentTimeMillis()
	 * 方法返回的值与triggerAtTime相等时启动operation所对应的设备
	 * （在指定的时刻，发送广播，但不唤醒设备）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒（闹钟在睡眠状态下不可用）。
	 * 
	 * RTC_WAKEUP： 指定当系统调用System.currentTimeMillis()
	 * 方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，并唤醒设备）。即使系统关机也会执行
	 * operation所对应的组件。
	 * 
	 * 第二个参数表示闹钟执行时间。
	 * 
	 * 第三个参数PendingIntent pi表示闹钟响应动作：
	 * 
	 * PendingIntent
	 * pi：是闹钟的执行动作，比如发送一个广播、给出提示等等。PendingIntent是Intent的封装类。需要注意的是，
	 * 如果是通过启动服务来实现闹钟提示的话，PendingIntent对象的获取就应该采用Pending.getService(Context
	 * c,int i,Intentintent,int
	 * j)方法；如果是通过广播来实现闹钟提示的话，PendingIntent对象的获取就应该采用PendingIntent
	 * .getBroadcast(Context c,inti,Intent intent,int
	 * j)方法；如果是采用Activity的方式来实现闹钟提示的话
	 * ，PendingIntent对象的获取就应该采用PendingIntent.getActivity(Context c,inti,Intent
	 * intent,int j)方法。如果这三种方法错用了的话，虽然不会报错，但是看不到闹钟提示效果。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * setRepeating(inttype,longstartTime,longintervalTime,PendingIntent pi)
	 * 
	 * 
	 * 设置一个周期性执行的定时服务。第一个参数表示闹钟类型，第二个参数表示闹钟首次执行时间，第三个参数表示闹钟两次执行的间隔时间，
	 * 第三个参数表示闹钟响应动作。
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 1
	 * 
	 * setInexactRepeating(int type, long triggerAtMillis,long
	 * intervalMillis,PendingIntent operation)
	 * 
	 * 
	 * 该方法也用于设置重复闹钟，与第二个方法相似，不过其两个闹钟执行的间隔时间不是固定的而已。它相对而言更省电（power-efficient）一些，
	 * 因为系统可能会将几个差不多的闹钟合并为一个来执行，减少设备的唤醒次数。第三个参数intervalTime为闹钟间隔，内置的几个变量如下：
	 * 
	 * INTERVAL_DAY： 设置闹钟，间隔一天 INTERVAL_HALF_DAY： 设置闹钟，间隔半天
	 * INTERVAL_FIFTEEN_MINUTES：设置闹钟，间隔15分钟 INTERVAL_HALF_HOUR： 设置闹钟，间隔半个小时
	 * INTERVAL_HOUR： 设置闹钟，间隔一个小时
	 */
}
