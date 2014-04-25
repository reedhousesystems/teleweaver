package b4a.sysdev;

import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class help extends Activity implements B4AActivity{
	public static help mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.sysdev", "b4a.sysdev.help");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (help).");
				p.finish();
			}
		}
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.sysdev", "b4a.sysdev.help");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (help) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (help) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
		return true;
	}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return help.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true)
				return true;
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
		this.setIntent(intent);
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (help) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (help) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scvimages = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imvimage = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmpimage = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblimage = null;
public static int _imgwidth = 0;
public static int _imgheight = 0;
public static int _imgspace = 0;
public static int _nbrimage = 0;
public static int _lfmimage = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btnback = null;
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.editartist _editartist = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (help) ","help",12,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 23;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 24;BA.debugLine="Activity.LoadLayout(\"ImageScrollView\")			' loads the layout file";
Debug.ShouldStop(8388608);
mostCurrent._activity.LoadLayout("ImageScrollView",mostCurrent.activityBA);
 BA.debugLineNum = 25;BA.debugLine="lfmImage=(scvImages.Width-imgWidth)/2			' calculates the left margin";
Debug.ShouldStop(16777216);
_lfmimage = (int)((mostCurrent._scvimages.getWidth()-_imgwidth)/(double)2);
 BA.debugLineNum = 26;BA.debugLine="InitScrollView															' inits the ScrollView";
Debug.ShouldStop(33554432);
_initscrollview();
 BA.debugLineNum = 27;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (help) ","help",12,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 33;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(1);
 BA.debugLineNum = 35;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (help) ","help",12,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 29;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 31;BA.debugLine="End Sub";
Debug.ShouldStop(1073741824);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _btnback_click() throws Exception{
		Debug.PushSubsStack("btnBack_Click (help) ","help",12,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 72;BA.debugLine="Sub btnBack_Click";
Debug.ShouldStop(128);
 BA.debugLineNum = 73;BA.debugLine="Activity.Finish";
Debug.ShouldStop(256);
mostCurrent._activity.Finish();
 BA.debugLineNum = 74;BA.debugLine="End Sub";
Debug.ShouldStop(512);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim scvImages As ScrollView";
mostCurrent._scvimages = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Dim imvImage As ImageView";
mostCurrent._imvimage = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim bmpImage As Bitmap";
mostCurrent._bmpimage = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim lblImage As Label";
mostCurrent._lblimage = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim imgWidth As Int			: imgWidth=280dip		' image width";
_imgwidth = 0;
 //BA.debugLineNum = 15;BA.debugLine="Dim imgWidth As Int			: imgWidth=280dip		' image width";
_imgwidth = anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(280));
 //BA.debugLineNum = 16;BA.debugLine="Dim imgHeight As Int		: imgHeight=210dip	' image height";
_imgheight = 0;
 //BA.debugLineNum = 16;BA.debugLine="Dim imgHeight As Int		: imgHeight=210dip	' image height";
_imgheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(210));
 //BA.debugLineNum = 17;BA.debugLine="Dim imgSpace As Int			: imgSpace=5dip			' space between images";
_imgspace = 0;
 //BA.debugLineNum = 17;BA.debugLine="Dim imgSpace As Int			: imgSpace=5dip			' space between images";
_imgspace = anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(5));
 //BA.debugLineNum = 18;BA.debugLine="Dim nbrImage As Int			: nbrImage=3				' number of images beginning with 0";
_nbrimage = 0;
 //BA.debugLineNum = 18;BA.debugLine="Dim nbrImage As Int			: nbrImage=3				' number of images beginning with 0";
_nbrimage = (int)(3);
 //BA.debugLineNum = 19;BA.debugLine="Dim lfmImage As Int													' left margin of images";
_lfmimage = 0;
 //BA.debugLineNum = 20;BA.debugLine="Dim btnBack As Button";
mostCurrent._btnback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public static String  _initscrollview() throws Exception{
		Debug.PushSubsStack("InitScrollView (help) ","help",12,mostCurrent.activityBA,mostCurrent);
try {
int _i = 0;
 BA.debugLineNum = 37;BA.debugLine="Sub InitScrollView";
Debug.ShouldStop(16);
 BA.debugLineNum = 38;BA.debugLine="Dim i As Int";
Debug.ShouldStop(32);
_i = 0;Debug.locals.put("i", _i);
 BA.debugLineNum = 39;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(64);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 40;BA.debugLine="For i=0 To 3";
Debug.ShouldStop(128);
{
final double step30 = 1;
final double limit30 = (int)(3);
for (_i = (int)(0); (step30 > 0 && _i <= limit30) || (step30 < 0 && _i >= limit30); _i += step30) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 41;BA.debugLine="btnBack.Text = \"Cima\"";
Debug.ShouldStop(256);
mostCurrent._btnback.setText((Object)("Cima"));
 BA.debugLineNum = 42;BA.debugLine="Dim imvImage As ImageView";
Debug.ShouldStop(512);
mostCurrent._imvimage = new anywheresoftware.b4a.objects.ImageViewWrapper();
 BA.debugLineNum = 43;BA.debugLine="Dim bmpImage As Bitmap";
Debug.ShouldStop(1024);
mostCurrent._bmpimage = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 BA.debugLineNum = 44;BA.debugLine="bmpImage.Initialize(File.DirAssets,\"xhosahelp\"&i&\".png\")";
Debug.ShouldStop(2048);
mostCurrent._bmpimage.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"xhosahelp"+BA.NumberToString(_i)+".png");
 BA.debugLineNum = 47;BA.debugLine="imvImage.Initialize(\"imvImage\")";
Debug.ShouldStop(16384);
mostCurrent._imvimage.Initialize(mostCurrent.activityBA,"imvImage");
 BA.debugLineNum = 48;BA.debugLine="imvImage.Gravity=Gravity.FILL";
Debug.ShouldStop(32768);
mostCurrent._imvimage.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 BA.debugLineNum = 49;BA.debugLine="imvImage.Tag=i";
Debug.ShouldStop(65536);
mostCurrent._imvimage.setTag((Object)(_i));
 BA.debugLineNum = 50;BA.debugLine="imvImage.Bitmap=bmpImage";
Debug.ShouldStop(131072);
mostCurrent._imvimage.setBitmap((android.graphics.Bitmap)(mostCurrent._bmpimage.getObject()));
 BA.debugLineNum = 51;BA.debugLine="scvImages.Panel.AddView(imvImage,lfmImage,i*(imgHeight+imgSpace),imgWidth,imgHeight)";
Debug.ShouldStop(262144);
mostCurrent._scvimages.getPanel().AddView((android.view.View)(mostCurrent._imvimage.getObject()),_lfmimage,(int)(_i*(_imgheight+_imgspace)),_imgwidth,_imgheight);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 53;BA.debugLine="scvImages.Panel.Height=(nbrImage+1)*(imgHeight+imgSpace)";
Debug.ShouldStop(1048576);
mostCurrent._scvimages.getPanel().setHeight((int)((_nbrimage+1)*(_imgheight+_imgspace)));
 }else {
 BA.debugLineNum = 55;BA.debugLine="For i=0 To 3";
Debug.ShouldStop(4194304);
{
final double step43 = 1;
final double limit43 = (int)(3);
for (_i = (int)(0); (step43 > 0 && _i <= limit43) || (step43 < 0 && _i >= limit43); _i += step43) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 56;BA.debugLine="btnBack.Text = \"Back\"";
Debug.ShouldStop(8388608);
mostCurrent._btnback.setText((Object)("Back"));
 BA.debugLineNum = 57;BA.debugLine="Dim imvImage As ImageView";
Debug.ShouldStop(16777216);
mostCurrent._imvimage = new anywheresoftware.b4a.objects.ImageViewWrapper();
 BA.debugLineNum = 58;BA.debugLine="Dim bmpImage As Bitmap";
Debug.ShouldStop(33554432);
mostCurrent._bmpimage = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 BA.debugLineNum = 59;BA.debugLine="bmpImage.Initialize(File.DirAssets,\"englishHelp\"&i&\".png\")";
Debug.ShouldStop(67108864);
mostCurrent._bmpimage.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"englishHelp"+BA.NumberToString(_i)+".png");
 BA.debugLineNum = 62;BA.debugLine="imvImage.Initialize(\"imvImage\")";
Debug.ShouldStop(536870912);
mostCurrent._imvimage.Initialize(mostCurrent.activityBA,"imvImage");
 BA.debugLineNum = 63;BA.debugLine="imvImage.Gravity=Gravity.FILL";
Debug.ShouldStop(1073741824);
mostCurrent._imvimage.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 BA.debugLineNum = 64;BA.debugLine="imvImage.Tag=i";
Debug.ShouldStop(-2147483648);
mostCurrent._imvimage.setTag((Object)(_i));
 BA.debugLineNum = 65;BA.debugLine="imvImage.Bitmap=bmpImage";
Debug.ShouldStop(1);
mostCurrent._imvimage.setBitmap((android.graphics.Bitmap)(mostCurrent._bmpimage.getObject()));
 BA.debugLineNum = 66;BA.debugLine="scvImages.Panel.AddView(imvImage,lfmImage,i*(imgHeight+imgSpace),imgWidth,imgHeight)";
Debug.ShouldStop(2);
mostCurrent._scvimages.getPanel().AddView((android.view.View)(mostCurrent._imvimage.getObject()),_lfmimage,(int)(_i*(_imgheight+_imgspace)),_imgwidth,_imgheight);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 68;BA.debugLine="scvImages.Panel.Height=(nbrImage+1)*(imgHeight+imgSpace)";
Debug.ShouldStop(8);
mostCurrent._scvimages.getPanel().setHeight((int)((_nbrimage+1)*(_imgheight+_imgspace)));
 };
 BA.debugLineNum = 70;BA.debugLine="End Sub";
Debug.ShouldStop(32);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="End Sub";
return "";
}
  public Object[] GetGlobals() {
		return new Object[] {"Activity",_activity,"scvImages",_scvimages,"imvImage",_imvimage,"bmpImage",_bmpimage,"lblImage",_lblimage,"imgWidth",_imgwidth,"imgHeight",_imgheight,"imgSpace",_imgspace,"nbrImage",_nbrimage,"lfmImage",_lfmimage,"btnBack",_btnback,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class)};
}
}
