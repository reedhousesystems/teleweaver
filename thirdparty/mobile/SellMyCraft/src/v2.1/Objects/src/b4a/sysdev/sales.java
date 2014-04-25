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

public class sales extends Activity implements B4AActivity{
	public static sales mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.sysdev", "b4a.sysdev.sales");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (sales).");
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
		activityBA = new BA(this, layout, processBA, "b4a.sysdev", "b4a.sysdev.sales");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (sales) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (sales) Resume **");
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
		return sales.class;
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
        BA.LogInfo("** Activity (sales) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (sales) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _lblhome = null;
public anywheresoftware.b4a.http.HttpClientWrapper _hc = null;
public anywheresoftware.b4a.objects.WebViewWrapper _wb = null;
public anywheresoftware.b4a.objects.collections.Map _mm = null;
public static String _sd = "";
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.editartist _editartist = null;
public b4a.sysdev.help _help = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 44;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(2048);
 BA.debugLineNum = 47;BA.debugLine="Activity.LoadLayout(\"Sales\")";
Debug.ShouldStop(16384);
mostCurrent._activity.LoadLayout("Sales",mostCurrent.activityBA);
 BA.debugLineNum = 48;BA.debugLine="changeLanguage";
Debug.ShouldStop(32768);
_changelanguage();
 BA.debugLineNum = 49;BA.debugLine="hc.Initialize(\"hc\")";
Debug.ShouldStop(65536);
Debug.DebugWarningEngine.CheckInitialize(mostCurrent._hc);mostCurrent._hc.Initialize("hc");
 BA.debugLineNum = 50;BA.debugLine="fetchSales";
Debug.ShouldStop(131072);
_fetchsales();
 BA.debugLineNum = 52;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
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
		Debug.PushSubsStack("Activity_Pause (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 58;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 60;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
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
		Debug.PushSubsStack("Activity_Resume (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 54;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 56;BA.debugLine="End Sub";
Debug.ShouldStop(8388608);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _changelanguage() throws Exception{
		Debug.PushSubsStack("changeLanguage (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 130;BA.debugLine="Sub changeLanguage";
Debug.ShouldStop(2);
 BA.debugLineNum = 131;BA.debugLine="If Main.globlang = \"English\" Then";
Debug.ShouldStop(4);
if ((mostCurrent._main._globlang).equals("English")) { 
 BA.debugLineNum = 138;BA.debugLine="lblHome.Text = \"Home\"";
Debug.ShouldStop(512);
mostCurrent._lblhome.setText((Object)("Home"));
 }else 
{ BA.debugLineNum = 140;BA.debugLine="Else If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(2048);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 147;BA.debugLine="lblHome.Text = \"Ekhaya\"";
Debug.ShouldStop(262144);
mostCurrent._lblhome.setText((Object)("Ekhaya"));
 }};
 BA.debugLineNum = 151;BA.debugLine="End Sub";
Debug.ShouldStop(4194304);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _fetchsales() throws Exception{
		Debug.PushSubsStack("fetchSales (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
String _remprodurl = "";
anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper _req = null;
 BA.debugLineNum = 62;BA.debugLine="Sub fetchSales";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 63;BA.debugLine="Dim remProdUrl As String";
Debug.ShouldStop(1073741824);
_remprodurl = "";Debug.locals.put("remProdUrl", _remprodurl);
 BA.debugLineNum = 64;BA.debugLine="Dim req As HttpRequest				'Set up an http request connection";
Debug.ShouldStop(-2147483648);
_req = new anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper();Debug.locals.put("req", _req);
 BA.debugLineNum = 65;BA.debugLine="remProdUrl = \"http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/get-sales-by-product/Shield/\"";
Debug.ShouldStop(1);
_remprodurl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/get-sales-by-product/Shield/";Debug.locals.put("remProdUrl", _remprodurl);
 BA.debugLineNum = 66;BA.debugLine="req.InitializeGet(remProdUrl)	 'Initialize the http get request";
Debug.ShouldStop(2);
_req.InitializeGet(_remprodurl);
 BA.debugLineNum = 68;BA.debugLine="ProgressDialogShow(\"Fetching Sales data from TeleWeaver...\")";
Debug.ShouldStop(8);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Fetching Sales data from TeleWeaver...");
 BA.debugLineNum = 69;BA.debugLine="hc.Execute(req,2)";
Debug.ShouldStop(16);
mostCurrent._hc.Execute(processBA,_req,(int)(2));
 BA.debugLineNum = 72;BA.debugLine="End Sub";
Debug.ShouldStop(128);
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
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 31;BA.debugLine="Dim lblHome As Label";
mostCurrent._lblhome = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim hc As HttpClient";
mostCurrent._hc = new anywheresoftware.b4a.http.HttpClientWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim wb As WebView";
mostCurrent._wb = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim mm As Map";
mostCurrent._mm = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 41;BA.debugLine="Dim sd As String";
mostCurrent._sd = "";
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,String _reason,int _statuscode,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseError (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Response", _response);
Debug.locals.put("Reason", _reason);
Debug.locals.put("StatusCode", _statuscode);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 121;BA.debugLine="Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)		'No connection :-(";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 122;BA.debugLine="Log(\"Error connecting: \" & Reason & \" \" & StatusCode)";
Debug.ShouldStop(33554432);
anywheresoftware.b4a.keywords.Common.Log("Error connecting: "+_reason+" "+BA.NumberToString(_statuscode));
 BA.debugLineNum = 123;BA.debugLine="If Response <> Null Then";
Debug.ShouldStop(67108864);
if (_response!= null) { 
 BA.debugLineNum = 124;BA.debugLine="Log(Response.GetString(\"UTF8\"))";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.Log(_response.GetString("UTF8"));
 BA.debugLineNum = 125;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(268435456);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 126;BA.debugLine="Response.Release";
Debug.ShouldStop(536870912);
_response.Release();
 };
 BA.debugLineNum = 128;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _hc_responsesuccess(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseSuccess (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
String _resultstring = "";
anywheresoftware.b4a.objects.collections.JSONParser _jp = null;
anywheresoftware.b4a.objects.collections.List _alist = null;
long _sometime = 0L;
String _d = "";
String _m = "";
String _y = "";
Debug.locals.put("Response", _response);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 75;BA.debugLine="Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)			'We got connection and data !!";
Debug.ShouldStop(1024);
 BA.debugLineNum = 77;BA.debugLine="ProgressDialogHide	'Close the waiting message..";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 79;BA.debugLine="Dim resultString As String";
Debug.ShouldStop(16384);
_resultstring = "";Debug.locals.put("resultString", _resultstring);
 BA.debugLineNum = 80;BA.debugLine="resultString = Response.GetString(\"UTF8\")			'This holds the returned data";
Debug.ShouldStop(32768);
_resultstring = _response.GetString("UTF8");Debug.locals.put("resultString", _resultstring);
 BA.debugLineNum = 82;BA.debugLine="Log(resultString)";
Debug.ShouldStop(131072);
anywheresoftware.b4a.keywords.Common.Log(_resultstring);
 BA.debugLineNum = 85;BA.debugLine="Dim Jp As JSONParser";
Debug.ShouldStop(1048576);
_jp = new anywheresoftware.b4a.objects.collections.JSONParser();Debug.locals.put("Jp", _jp);
 BA.debugLineNum = 86;BA.debugLine="Dim aList As List";
Debug.ShouldStop(2097152);
_alist = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("aList", _alist);
 BA.debugLineNum = 87;BA.debugLine="Jp.Initialize(resultString)";
Debug.ShouldStop(4194304);
_jp.Initialize(_resultstring);
 BA.debugLineNum = 88;BA.debugLine="aList = Jp.NextArray";
Debug.ShouldStop(8388608);
_alist = _jp.NextArray();Debug.locals.put("aList", _alist);
 BA.debugLineNum = 91;BA.debugLine="mm = aList.Get(0)";
Debug.ShouldStop(67108864);
mostCurrent._mm.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_alist.Get((int)(0))));
 BA.debugLineNum = 92;BA.debugLine="Dim SomeTime As Long";
Debug.ShouldStop(134217728);
_sometime = 0L;Debug.locals.put("SomeTime", _sometime);
 BA.debugLineNum = 93;BA.debugLine="Dim d,m, y As String";
Debug.ShouldStop(268435456);
_d = "";Debug.locals.put("d", _d);
_m = "";Debug.locals.put("m", _m);
_y = "";Debug.locals.put("y", _y);
 BA.debugLineNum = 95;BA.debugLine="SomeTime = mm.Get(\"saleDate\")";
Debug.ShouldStop(1073741824);
_sometime = BA.ObjectToLongNumber(mostCurrent._mm.Get((Object)("saleDate")));Debug.locals.put("SomeTime", _sometime);
 BA.debugLineNum = 96;BA.debugLine="y = DateTime.GetYear(SomeTime)";
Debug.ShouldStop(-2147483648);
_y = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(_sometime));Debug.locals.put("y", _y);
 BA.debugLineNum = 97;BA.debugLine="m = DateTime.GetMonth(SomeTime)";
Debug.ShouldStop(1);
_m = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(_sometime));Debug.locals.put("m", _m);
 BA.debugLineNum = 98;BA.debugLine="d = DateTime.GetDayOfMonth(SomeTime)";
Debug.ShouldStop(2);
_d = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(_sometime));Debug.locals.put("d", _d);
 BA.debugLineNum = 99;BA.debugLine="y = d & \"/\" & m & \"/\" & y";
Debug.ShouldStop(4);
_y = _d+"/"+_m+"/"+_y;Debug.locals.put("y", _y);
 BA.debugLineNum = 101;BA.debugLine="Main.aSQL.ExecNonQuery(\"DELETE FROM Sales\")";
Debug.ShouldStop(16);
mostCurrent._main._asql.ExecNonQuery("DELETE FROM Sales");
 BA.debugLineNum = 102;BA.debugLine="Main.aSQL.ExecNonQuery2(\"INSERT INTO Sales VALUES(?,?,?,?,?)\", Array As Object(mm.Get(\"id\"),mm.Get(\"buyer\"),mm.Get(\"quantity\"),mm.Get(\"totalAmount\"),y))";
Debug.ShouldStop(32);
mostCurrent._main._asql.ExecNonQuery2("INSERT INTO Sales VALUES(?,?,?,?,?)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{mostCurrent._mm.Get((Object)("id")),mostCurrent._mm.Get((Object)("buyer")),mostCurrent._mm.Get((Object)("quantity")),mostCurrent._mm.Get((Object)("totalAmount")),(Object)(_y)}));
 BA.debugLineNum = 105;BA.debugLine="mm = aList.Get(1)";
Debug.ShouldStop(256);
mostCurrent._mm.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_alist.Get((int)(1))));
 BA.debugLineNum = 106;BA.debugLine="SomeTime = mm.Get(\"saleDate\")";
Debug.ShouldStop(512);
_sometime = BA.ObjectToLongNumber(mostCurrent._mm.Get((Object)("saleDate")));Debug.locals.put("SomeTime", _sometime);
 BA.debugLineNum = 107;BA.debugLine="y = DateTime.GetYear(SomeTime)";
Debug.ShouldStop(1024);
_y = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(_sometime));Debug.locals.put("y", _y);
 BA.debugLineNum = 108;BA.debugLine="m = DateTime.GetMonth(SomeTime)";
Debug.ShouldStop(2048);
_m = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(_sometime));Debug.locals.put("m", _m);
 BA.debugLineNum = 109;BA.debugLine="d = DateTime.GetDayOfMonth(SomeTime)";
Debug.ShouldStop(4096);
_d = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(_sometime));Debug.locals.put("d", _d);
 BA.debugLineNum = 110;BA.debugLine="y = d & \"/\" & m & \"/\" & y";
Debug.ShouldStop(8192);
_y = _d+"/"+_m+"/"+_y;Debug.locals.put("y", _y);
 BA.debugLineNum = 112;BA.debugLine="Main.aSQL.ExecNonQuery2(\"INSERT INTO Sales VALUES(?,?,?,?,?)\", Array As Object(mm.Get(\"id\"),mm.Get(\"buyer\"),mm.Get(\"quantity\"),mm.Get(\"totalAmount\"),y))";
Debug.ShouldStop(32768);
mostCurrent._main._asql.ExecNonQuery2("INSERT INTO Sales VALUES(?,?,?,?,?)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{mostCurrent._mm.Get((Object)("id")),mostCurrent._mm.Get((Object)("buyer")),mostCurrent._mm.Get((Object)("quantity")),mostCurrent._mm.Get((Object)("totalAmount")),(Object)(_y)}));
 BA.debugLineNum = 114;BA.debugLine="sd = \"SELECT * FROM Sales\"";
Debug.ShouldStop(131072);
mostCurrent._sd = "SELECT * FROM Sales";
 BA.debugLineNum = 115;BA.debugLine="wb.LoadHtml(DBUtils.ExecuteHtml(Main.aSQL,sd,Null, 0, True))";
Debug.ShouldStop(262144);
mostCurrent._wb.LoadHtml(mostCurrent._dbutils._executehtml(mostCurrent.activityBA,mostCurrent._main._asql,mostCurrent._sd,(String[])(anywheresoftware.b4a.keywords.Common.Null),(int)(0),anywheresoftware.b4a.keywords.Common.True));
 BA.debugLineNum = 118;BA.debugLine="End Sub";
Debug.ShouldStop(2097152);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imagehome_click() throws Exception{
		Debug.PushSubsStack("imageHome_Click (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 160;BA.debugLine="Sub imageHome_Click";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 161;BA.debugLine="Activity.Finish";
Debug.ShouldStop(1);
mostCurrent._activity.Finish();
 BA.debugLineNum = 162;BA.debugLine="End Sub";
Debug.ShouldStop(2);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
		Debug.PushSubsStack("JobDone (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Job", _job);
 BA.debugLineNum = 166;BA.debugLine="Sub JobDone(Job As HttpJob)";
Debug.ShouldStop(32);
 BA.debugLineNum = 204;BA.debugLine="End Sub";
Debug.ShouldStop(2048);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lblhome_click() throws Exception{
		Debug.PushSubsStack("lblHome_Click (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 156;BA.debugLine="Sub lblHome_Click";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 157;BA.debugLine="imageHome_Click";
Debug.ShouldStop(268435456);
_imagehome_click();
 BA.debugLineNum = 158;BA.debugLine="End Sub";
Debug.ShouldStop(536870912);
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
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _spinner1_itemclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("Spinner1_ItemClick (sales) ","sales",5,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 152;BA.debugLine="Sub Spinner1_ItemClick (Position As Int, Value As Object)";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 154;BA.debugLine="End Sub";
Debug.ShouldStop(33554432);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
  public Object[] GetGlobals() {
		return new Object[] {"Activity",_activity,"lblHome",_lblhome,"hc",_hc,"wb",_wb,"mm",_mm,"sd",_sd,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
