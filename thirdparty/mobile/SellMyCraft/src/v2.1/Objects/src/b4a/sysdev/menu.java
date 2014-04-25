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

public class menu extends Activity implements B4AActivity{
	public static menu mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.sysdev", "b4a.sysdev.menu");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (menu).");
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
		activityBA = new BA(this, layout, processBA, "b4a.sysdev", "b4a.sysdev.menu");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (menu) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (menu) Resume **");
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
		return menu.class;
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
        BA.LogInfo("** Activity (menu) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (menu) Resume **");
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
public anywheresoftware.b4a.http.HttpClientWrapper _hc = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblheader = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluser = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagelogo = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _btnconnected = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageuser = null;
public b4a.sysdev.custommsgbox _mymsgbox = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsynch = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pbsynch = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpage1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpage2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpage3 = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _curs = null;
public anywheresoftware.b4a.objects.TabHostWrapper _tbhpages = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public static byte[] _b = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageaddprod = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageeditprofile = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagesales = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageviewprod = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagesync = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagelogout = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladdprod = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbleditprofile = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblviewsales = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblviewproduct = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsync = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllogout = null;
public static String _hexapicture = "";
public b4a.sysdev.ctoast _myt = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _language = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imghelpbtn = null;
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.editartist _editartist = null;
public b4a.sysdev.help _help = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 49;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(65536);
 BA.debugLineNum = 52;BA.debugLine="Activity.LoadLayout(\"central\")";
Debug.ShouldStop(524288);
mostCurrent._activity.LoadLayout("central",mostCurrent.activityBA);
 BA.debugLineNum = 53;BA.debugLine="Language.AddAll(Array As String(\"English\",\"isiXhosa\"))";
Debug.ShouldStop(1048576);
mostCurrent._language.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"English","isiXhosa"}));
 BA.debugLineNum = 57;BA.debugLine="lblUser.Text =  Main.globName";
Debug.ShouldStop(16777216);
mostCurrent._lbluser.setText((Object)(mostCurrent._main._globname));
 BA.debugLineNum = 58;BA.debugLine="getImage_forEdit";
Debug.ShouldStop(33554432);
_getimage_foredit();
 BA.debugLineNum = 59;BA.debugLine="changeLanguage";
Debug.ShouldStop(67108864);
_changelanguage();
 BA.debugLineNum = 60;BA.debugLine="hc.Initialize(\"hc\")";
Debug.ShouldStop(134217728);
Debug.DebugWarningEngine.CheckInitialize(mostCurrent._hc);mostCurrent._hc.Initialize("hc");
 BA.debugLineNum = 62;BA.debugLine="MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)";
Debug.ShouldStop(536870912);
mostCurrent._myt._initialize(mostCurrent.activityBA,mostCurrent._activity,menu.getObject(),mostCurrent._activity.getHeight(),mostCurrent._activity.getWidth());
 BA.debugLineNum = 63;BA.debugLine="End Sub";
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 142;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(8192);
 BA.debugLineNum = 144;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
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
		Debug.PushSubsStack("Activity_Resume (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 127;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 128;BA.debugLine="getImage_forEdit";
Debug.ShouldStop(-2147483648);
_getimage_foredit();
 BA.debugLineNum = 130;BA.debugLine="lblUser.Text =  Main.globName";
Debug.ShouldStop(2);
mostCurrent._lbluser.setText((Object)(mostCurrent._main._globname));
 BA.debugLineNum = 132;BA.debugLine="If EditArtist.ArtistUpdated Then";
Debug.ShouldStop(8);
if (mostCurrent._editartist._artistupdated) { 
 BA.debugLineNum = 133;BA.debugLine="Dim MyT As CToast";
Debug.ShouldStop(16);
mostCurrent._myt = new b4a.sysdev.ctoast();
 BA.debugLineNum = 134;BA.debugLine="MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)";
Debug.ShouldStop(32);
mostCurrent._myt._initialize(mostCurrent.activityBA,mostCurrent._activity,menu.getObject(),mostCurrent._activity.getHeight(),mostCurrent._activity.getWidth());
 BA.debugLineNum = 136;BA.debugLine="MyT.ToastMessageShow2(\"Your Artist information has been successfully updated\",4,60,50, \"\", Colors.white, Colors.black, 20, True)";
Debug.ShouldStop(128);
mostCurrent._myt._toastmessageshow2("Your Artist information has been successfully updated",(int)(4),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 };
 BA.debugLineNum = 139;BA.debugLine="EditArtist.ArtistUpdated = False";
Debug.ShouldStop(1024);
mostCurrent._editartist._artistupdated = anywheresoftware.b4a.keywords.Common.False;
 BA.debugLineNum = 140;BA.debugLine="End Sub";
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
public static String  _addprodtotw() throws Exception{
		Debug.PushSubsStack("addProdToTW (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.samples.httputils2.httpjob _postprodjob = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _postprodurl = "";
anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _jsongenerator = null;
 BA.debugLineNum = 277;BA.debugLine="Sub addProdToTW";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 279;BA.debugLine="Dim postProdJob As HttpJob";
Debug.ShouldStop(4194304);
_postprodjob = new anywheresoftware.b4a.samples.httputils2.httpjob();Debug.locals.put("postProdJob", _postprodjob);
 BA.debugLineNum = 280;BA.debugLine="Dim m As Map";
Debug.ShouldStop(8388608);
_m = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("m", _m);
 BA.debugLineNum = 281;BA.debugLine="Dim postProdUrl As String";
Debug.ShouldStop(16777216);
_postprodurl = "";Debug.locals.put("postProdUrl", _postprodurl);
 BA.debugLineNum = 283;BA.debugLine="postProdUrl = \"http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/add-product/\"";
Debug.ShouldStop(67108864);
_postprodurl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/add-product/";Debug.locals.put("postProdUrl", _postprodurl);
 BA.debugLineNum = 285;BA.debugLine="m.Initialize";
Debug.ShouldStop(268435456);
_m.Initialize();
 BA.debugLineNum = 286;BA.debugLine="m.Put(\"id\", Main.cur.GetInt(\"ProductID\"))";
Debug.ShouldStop(536870912);
_m.Put((Object)("id"),(Object)(mostCurrent._main._cur.GetInt("ProductID")));
 BA.debugLineNum = 287;BA.debugLine="m.Put(\"profile\", Main.globID)";
Debug.ShouldStop(1073741824);
_m.Put((Object)("profile"),(Object)(mostCurrent._main._globid));
 BA.debugLineNum = 288;BA.debugLine="m.Put(\"name\", Main.cur.GetString(\"Name\"))";
Debug.ShouldStop(-2147483648);
_m.Put((Object)("name"),(Object)(mostCurrent._main._cur.GetString("Name")));
 BA.debugLineNum = 289;BA.debugLine="m.Put(\"dimension\", Main.cur.GetString(\"Size\"))";
Debug.ShouldStop(1);
_m.Put((Object)("dimension"),(Object)(mostCurrent._main._cur.GetString("Size")));
 BA.debugLineNum = 290;BA.debugLine="m.Put(\"price\", Main.cur.GetString(\"Price\"))";
Debug.ShouldStop(2);
_m.Put((Object)("price"),(Object)(mostCurrent._main._cur.GetString("Price")));
 BA.debugLineNum = 291;BA.debugLine="m.Put(\"description\", Main.cur.GetString(\"Description\"))";
Debug.ShouldStop(4);
_m.Put((Object)("description"),(Object)(mostCurrent._main._cur.GetString("Description")));
 BA.debugLineNum = 292;BA.debugLine="m.Put(\"type\",Main.cur.GetString(\"Type\"))";
Debug.ShouldStop(8);
_m.Put((Object)("type"),(Object)(mostCurrent._main._cur.GetString("Type")));
 BA.debugLineNum = 293;BA.debugLine="m.Put(\"quantity\", 4)";
Debug.ShouldStop(16);
_m.Put((Object)("quantity"),(Object)(4));
 BA.debugLineNum = 294;BA.debugLine="retrieveANDconvert     ' retrieves the image from SQLite";
Debug.ShouldStop(32);
_retrieveandconvert();
 BA.debugLineNum = 295;BA.debugLine="m.Put(\"picture\", hexaPicture)";
Debug.ShouldStop(64);
_m.Put((Object)("picture"),(Object)(mostCurrent._hexapicture));
 BA.debugLineNum = 297;BA.debugLine="Dim JSONGenerator As JSONGenerator";
Debug.ShouldStop(256);
_jsongenerator = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();Debug.locals.put("JSONGenerator", _jsongenerator);
 BA.debugLineNum = 298;BA.debugLine="JSONGenerator.Initialize(m)";
Debug.ShouldStop(512);
_jsongenerator.Initialize(_m);
 BA.debugLineNum = 299;BA.debugLine="postProdJob.Initialize(\"PostProd\", Me)";
Debug.ShouldStop(1024);
_postprodjob._initialize(processBA,"PostProd",menu.getObject());
 BA.debugLineNum = 300;BA.debugLine="postProdJob.PostString(postProdUrl, JSONGenerator.ToString())";
Debug.ShouldStop(2048);
_postprodjob._poststring(_postprodurl,_jsongenerator.ToString());
 BA.debugLineNum = 301;BA.debugLine="postProdJob.GetRequest.SetContentType(\"application/json\")";
Debug.ShouldStop(4096);
_postprodjob._getrequest().SetContentType("application/json");
 BA.debugLineNum = 304;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
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
		Debug.PushSubsStack("changeLanguage (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 65;BA.debugLine="Sub changeLanguage";
Debug.ShouldStop(1);
 BA.debugLineNum = 66;BA.debugLine="If Main.globlang = \"English\" Then";
Debug.ShouldStop(2);
if ((mostCurrent._main._globlang).equals("English")) { 
 BA.debugLineNum = 67;BA.debugLine="lblHeader.Text = \"Welcome\"";
Debug.ShouldStop(4);
mostCurrent._lblheader.setText((Object)("Welcome"));
 BA.debugLineNum = 68;BA.debugLine="lblAddProd.Text = \"Add Product\"";
Debug.ShouldStop(8);
mostCurrent._lbladdprod.setText((Object)("Add Product"));
 BA.debugLineNum = 69;BA.debugLine="lblEditProfile.Text = \"Edit My Info\"";
Debug.ShouldStop(16);
mostCurrent._lbleditprofile.setText((Object)("Edit My Info"));
 BA.debugLineNum = 70;BA.debugLine="lblViewSales.Text = \"View Sales\"";
Debug.ShouldStop(32);
mostCurrent._lblviewsales.setText((Object)("View Sales"));
 BA.debugLineNum = 71;BA.debugLine="lblViewProduct.Text = \"View Product\"";
Debug.ShouldStop(64);
mostCurrent._lblviewproduct.setText((Object)("View Product"));
 BA.debugLineNum = 72;BA.debugLine="lblSync.Text = \"Synchronize\"";
Debug.ShouldStop(128);
mostCurrent._lblsync.setText((Object)("Synchronize"));
 BA.debugLineNum = 73;BA.debugLine="lblSynch.Text = \"Synchronizing...\"";
Debug.ShouldStop(256);
mostCurrent._lblsynch.setText((Object)("Synchronizing..."));
 BA.debugLineNum = 74;BA.debugLine="lblLogout.Text = \"Exit\"";
Debug.ShouldStop(512);
mostCurrent._lbllogout.setText((Object)("Exit"));
 }else 
{ BA.debugLineNum = 75;BA.debugLine="Else If  Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(1024);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 76;BA.debugLine="lblHeader.Text = \"Wamnkelekile\"";
Debug.ShouldStop(2048);
mostCurrent._lblheader.setText((Object)("Wamnkelekile"));
 BA.debugLineNum = 77;BA.debugLine="lblAddProd.Text = \"Faka imveliso\"";
Debug.ShouldStop(4096);
mostCurrent._lbladdprod.setText((Object)("Faka imveliso"));
 BA.debugLineNum = 78;BA.debugLine="lblEditProfile.Text = \"Inkcukacha zam\"";
Debug.ShouldStop(8192);
mostCurrent._lbleditprofile.setText((Object)("Inkcukacha zam"));
 BA.debugLineNum = 79;BA.debugLine="lblViewSales.Text = \"Jonga intengiso\"";
Debug.ShouldStop(16384);
mostCurrent._lblviewsales.setText((Object)("Jonga intengiso"));
 BA.debugLineNum = 80;BA.debugLine="lblViewProduct.Text = \"Jonga imveliso\"";
Debug.ShouldStop(32768);
mostCurrent._lblviewproduct.setText((Object)("Jonga imveliso"));
 BA.debugLineNum = 81;BA.debugLine="lblSync.Text = \"Ngqamanisa\"";
Debug.ShouldStop(65536);
mostCurrent._lblsync.setText((Object)("Ngqamanisa"));
 BA.debugLineNum = 82;BA.debugLine="lblLogout.Text = \"Phuma\"";
Debug.ShouldStop(131072);
mostCurrent._lbllogout.setText((Object)("Phuma"));
 }};
 BA.debugLineNum = 84;BA.debugLine="End Sub";
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
public static String  _getimage_foredit() throws Exception{
		Debug.PushSubsStack("getImage_forEdit (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _inputstrea = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bitt = null;
 BA.debugLineNum = 85;BA.debugLine="Sub getImage_forEdit";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 87;BA.debugLine="Main.cur = Main.aSQL.ExecQuery2(\"SELECT * FROM Artist WHERE ArtistID = ?\", Array As String(Main.globID))";
Debug.ShouldStop(4194304);
mostCurrent._main._cur.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery2("SELECT * FROM Artist WHERE ArtistID = ?",new String[]{mostCurrent._main._globid})));
 BA.debugLineNum = 88;BA.debugLine="Main.cur.Position = 0";
Debug.ShouldStop(8388608);
mostCurrent._main._cur.setPosition((int)(0));
 BA.debugLineNum = 89;BA.debugLine="Dim InputStrea As InputStream";
Debug.ShouldStop(16777216);
_inputstrea = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("InputStrea", _inputstrea);
 BA.debugLineNum = 90;BA.debugLine="Dim Bitt As Bitmap";
Debug.ShouldStop(33554432);
_bitt = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();Debug.locals.put("Bitt", _bitt);
 BA.debugLineNum = 92;BA.debugLine="b = Null";
Debug.ShouldStop(134217728);
_b = (byte[])(anywheresoftware.b4a.keywords.Common.Null);
 BA.debugLineNum = 93;BA.debugLine="If Main.cur.GetString(\"PicExist\") = \"yes\" Then";
Debug.ShouldStop(268435456);
if ((mostCurrent._main._cur.GetString("PicExist")).equals("yes")) { 
 BA.debugLineNum = 94;BA.debugLine="b = Main.cur.GetBlob(\"ArtistPic\")";
Debug.ShouldStop(536870912);
_b = mostCurrent._main._cur.GetBlob("ArtistPic");
 BA.debugLineNum = 95;BA.debugLine="InputStrea.InitializeFromBytesArray(b, 0, b.Length)";
Debug.ShouldStop(1073741824);
_inputstrea.InitializeFromBytesArray(_b,(int)(0),_b.length);
 BA.debugLineNum = 97;BA.debugLine="Bitt.Initialize2(InputStrea)";
Debug.ShouldStop(1);
_bitt.Initialize2((java.io.InputStream)(_inputstrea.getObject()));
 BA.debugLineNum = 98;BA.debugLine="InputStrea.Close";
Debug.ShouldStop(2);
_inputstrea.Close();
 BA.debugLineNum = 99;BA.debugLine="imageUser.SetBackgroundImage(Bitt)";
Debug.ShouldStop(4);
mostCurrent._imageuser.SetBackgroundImage((android.graphics.Bitmap)(_bitt.getObject()));
 }else {
 BA.debugLineNum = 101;BA.debugLine="imageUser.Bitmap = LoadBitmapSample  (File.DirAssets, \"empty_gallery.png\",imageUser.Width,imageUser.Height)";
Debug.ShouldStop(16);
mostCurrent._imageuser.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"empty_gallery.png",mostCurrent._imageuser.getWidth(),mostCurrent._imageuser.getHeight()).getObject()));
 };
 BA.debugLineNum = 125;BA.debugLine="End Sub";
Debug.ShouldStop(268435456);
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
 //BA.debugLineNum = 15;BA.debugLine="Dim hc As HttpClient";
mostCurrent._hc = new anywheresoftware.b4a.http.HttpClientWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Dim lblHeader As Label";
mostCurrent._lblheader = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim lblUser As Label";
mostCurrent._lbluser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim imageLogo As ImageView";
mostCurrent._imagelogo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim btnConnected As ToggleButton";
mostCurrent._btnconnected = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim imageUser As ImageView";
mostCurrent._imageuser = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim myMsgBox As CustomMsgBox";
mostCurrent._mymsgbox = new b4a.sysdev.custommsgbox();
 //BA.debugLineNum = 22;BA.debugLine="Dim lblSynch As Label";
mostCurrent._lblsynch = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim pbSynch As ProgressBar";
mostCurrent._pbsynch = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim pnlPage1, pnlPage2, pnlPage3 As Panel";
mostCurrent._pnlpage1 = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._pnlpage2 = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._pnlpage3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim curs As Cursor";
mostCurrent._curs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim tbhPages As TabHost";
mostCurrent._tbhpages = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim b() As Byte";
_b = new byte[(int)(0)];
;
 //BA.debugLineNum = 30;BA.debugLine="Dim imageAddProd As ImageView";
mostCurrent._imageaddprod = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim imageEditProfile As ImageView";
mostCurrent._imageeditprofile = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim imageSales As ImageView";
mostCurrent._imagesales = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim imageViewProd As ImageView";
mostCurrent._imageviewprod = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim imageSync As ImageView";
mostCurrent._imagesync = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim imageLogOut As ImageView";
mostCurrent._imagelogout = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim lblAddProd As Label";
mostCurrent._lbladdprod = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim lblEditProfile As Label";
mostCurrent._lbleditprofile = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim lblViewSales As Label";
mostCurrent._lblviewsales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim lblViewProduct As Label";
mostCurrent._lblviewproduct = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim lblSync As Label";
mostCurrent._lblsync = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim lblLogout As Label";
mostCurrent._lbllogout = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim hexaPicture As String";
mostCurrent._hexapicture = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim MyT As CToast";
mostCurrent._myt = new b4a.sysdev.ctoast();
 //BA.debugLineNum = 45;BA.debugLine="Dim Language As Spinner";
mostCurrent._language = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim imgHelpBtn As ImageView";
mostCurrent._imghelpbtn = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,String _reason,int _statuscode,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseError (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Response", _response);
Debug.locals.put("Reason", _reason);
Debug.locals.put("StatusCode", _statuscode);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 408;BA.debugLine="Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)		'No connection :-(";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 409;BA.debugLine="Log(\"Error connecting: \" & Reason & \" \" & StatusCode)";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.Log("Error connecting: "+_reason+" "+BA.NumberToString(_statuscode));
 BA.debugLineNum = 410;BA.debugLine="If Response <> Null Then";
Debug.ShouldStop(33554432);
if (_response!= null) { 
 BA.debugLineNum = 411;BA.debugLine="Log(Response.GetString(\"UTF8\"))";
Debug.ShouldStop(67108864);
anywheresoftware.b4a.keywords.Common.Log(_response.GetString("UTF8"));
 BA.debugLineNum = 412;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 413;BA.debugLine="Response.Release";
Debug.ShouldStop(268435456);
_response.Release();
 };
 BA.debugLineNum = 415;BA.debugLine="End Sub";
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
public static String  _hc_responsesuccess(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseSuccess (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
String _resultstring = "";
Debug.locals.put("Response", _response);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 385;BA.debugLine="Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)			'We got connection and data !!";
Debug.ShouldStop(1);
 BA.debugLineNum = 386;BA.debugLine="ProgressDialogHide	'Close the waiting message.";
Debug.ShouldStop(2);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 388;BA.debugLine="Dim MyT As CToast";
Debug.ShouldStop(8);
mostCurrent._myt = new b4a.sysdev.ctoast();
 BA.debugLineNum = 389;BA.debugLine="MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)";
Debug.ShouldStop(16);
mostCurrent._myt._initialize(mostCurrent.activityBA,mostCurrent._activity,menu.getObject(),mostCurrent._activity.getHeight(),mostCurrent._activity.getWidth());
 BA.debugLineNum = 391;BA.debugLine="Dim resultString As String";
Debug.ShouldStop(64);
_resultstring = "";Debug.locals.put("resultString", _resultstring);
 BA.debugLineNum = 392;BA.debugLine="resultString = Response.GetString(\"UTF8\")			'This holds the returned data";
Debug.ShouldStop(128);
_resultstring = _response.GetString("UTF8");Debug.locals.put("resultString", _resultstring);
 BA.debugLineNum = 393;BA.debugLine="If resultString = \"product removal unsuccessful\" Then";
Debug.ShouldStop(256);
if ((_resultstring).equals("product removal unsuccessful")) { 
 BA.debugLineNum = 395;BA.debugLine="MyT.ToastMessageShow2(\"Sorry could not sync with Teleweaver at this time. Delete will be made when TeleWeaver is available.\",8,60,50, \"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(1024);
mostCurrent._myt._toastmessageshow2("Sorry could not sync with Teleweaver at this time. Delete will be made when TeleWeaver is available.",(int)(8),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 }else {
 BA.debugLineNum = 398;BA.debugLine="MyT.ToastMessageShow2(\"Product has been successfully deleted!\",7,60,50,\"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(8192);
mostCurrent._myt._toastmessageshow2("Product has been successfully deleted!",(int)(7),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 399;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE product SET Status=? WHERE ProductID = \" & Main.globID , Array As Object(\"none\"))";
Debug.ShouldStop(16384);
mostCurrent._main._asql.ExecNonQuery2("UPDATE product SET Status=? WHERE ProductID = "+mostCurrent._main._globid,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("none")}));
 };
 BA.debugLineNum = 404;BA.debugLine="Log(resultString)";
Debug.ShouldStop(524288);
anywheresoftware.b4a.keywords.Common.Log(_resultstring);
 BA.debugLineNum = 406;BA.debugLine="End Sub";
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
public static String  _imageaddprod_click() throws Exception{
		Debug.PushSubsStack("imageAddProd_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 177;BA.debugLine="Sub imageAddProd_Click";
Debug.ShouldStop(65536);
 BA.debugLineNum = 179;BA.debugLine="StartActivity(\"add\")";
Debug.ShouldStop(262144);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("add"));
 BA.debugLineNum = 180;BA.debugLine="End Sub";
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
public static String  _imageeditprofile_click() throws Exception{
		Debug.PushSubsStack("imageEditProfile_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 173;BA.debugLine="Sub imageEditProfile_Click";
Debug.ShouldStop(4096);
 BA.debugLineNum = 174;BA.debugLine="StartActivity(\"editArtist\")";
Debug.ShouldStop(8192);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("editArtist"));
 BA.debugLineNum = 175;BA.debugLine="End Sub";
Debug.ShouldStop(16384);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imagelogout_click() throws Exception{
		Debug.PushSubsStack("imageLogOut_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 184;BA.debugLine="Sub imageLogOut_Click";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 185;BA.debugLine="Activity.Finish";
Debug.ShouldStop(16777216);
mostCurrent._activity.Finish();
 BA.debugLineNum = 186;BA.debugLine="End Sub";
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
public static String  _imagesales_click() throws Exception{
		Debug.PushSubsStack("imageSales_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 169;BA.debugLine="Sub imageSales_Click";
Debug.ShouldStop(256);
 BA.debugLineNum = 170;BA.debugLine="StartActivity(\"sales\")";
Debug.ShouldStop(512);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("sales"));
 BA.debugLineNum = 171;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imagesync_click() throws Exception{
		Debug.PushSubsStack("imageSync_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
int _counttosync = 0;
int _addd = 0;
int _moodd = 0;
int _i = 0;
 BA.debugLineNum = 225;BA.debugLine="Sub imageSync_Click";
Debug.ShouldStop(1);
 BA.debugLineNum = 226;BA.debugLine="Dim  countToSync,addd,moodd As Int";
Debug.ShouldStop(2);
_counttosync = 0;Debug.locals.put("countToSync", _counttosync);
_addd = 0;Debug.locals.put("addd", _addd);
_moodd = 0;Debug.locals.put("moodd", _moodd);
 BA.debugLineNum = 227;BA.debugLine="countToSync = 0";
Debug.ShouldStop(4);
_counttosync = (int)(0);Debug.locals.put("countToSync", _counttosync);
 BA.debugLineNum = 230;BA.debugLine="Main.cur = Main.aSQL.ExecQuery2(\"SELECT * FROM product WHERE ArtistID_fk = ?\", Array As String(Main.m.Get(\"id\")))";
Debug.ShouldStop(32);
mostCurrent._main._cur.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery2("SELECT * FROM product WHERE ArtistID_fk = ?",new String[]{String.valueOf(mostCurrent._main._m.Get((Object)("id")))})));
 BA.debugLineNum = 234;BA.debugLine="For i = 0 To Main.cur.RowCount - 1";
Debug.ShouldStop(512);
{
final double step154 = 1;
final double limit154 = (int)(mostCurrent._main._cur.getRowCount()-1);
for (_i = (int)(0); (step154 > 0 && _i <= limit154) || (step154 < 0 && _i >= limit154); _i += step154) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 236;BA.debugLine="Main.cur.Position = i";
Debug.ShouldStop(2048);
mostCurrent._main._cur.setPosition(_i);
 BA.debugLineNum = 237;BA.debugLine="If Main.cur.GetString(\"Status\") = \"add\" Then";
Debug.ShouldStop(4096);
if ((mostCurrent._main._cur.GetString("Status")).equals("add")) { 
 BA.debugLineNum = 238;BA.debugLine="ProgressDialogShow(\"Adding Product(s) to TeleWeaver...\")";
Debug.ShouldStop(8192);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Adding Product(s) to TeleWeaver...");
 BA.debugLineNum = 239;BA.debugLine="addProdToTW";
Debug.ShouldStop(16384);
_addprodtotw();
 BA.debugLineNum = 240;BA.debugLine="countToSync = countToSync + 1";
Debug.ShouldStop(32768);
_counttosync = (int)(_counttosync+1);Debug.locals.put("countToSync", _counttosync);
 BA.debugLineNum = 241;BA.debugLine="addd = addd + 1";
Debug.ShouldStop(65536);
_addd = (int)(_addd+1);Debug.locals.put("addd", _addd);
 };
 BA.debugLineNum = 248;BA.debugLine="Main.cur.Position = i";
Debug.ShouldStop(8388608);
mostCurrent._main._cur.setPosition(_i);
 BA.debugLineNum = 249;BA.debugLine="If Main.cur.GetString(\"Status\") = \"mod\" Then";
Debug.ShouldStop(16777216);
if ((mostCurrent._main._cur.GetString("Status")).equals("mod")) { 
 BA.debugLineNum = 250;BA.debugLine="ProgressDialogShow(\"Updating Product(s) to TeleWeaver...\")";
Debug.ShouldStop(33554432);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Updating Product(s) to TeleWeaver...");
 BA.debugLineNum = 251;BA.debugLine="modProdOnTW";
Debug.ShouldStop(67108864);
_modprodontw();
 BA.debugLineNum = 252;BA.debugLine="countToSync = countToSync + 1";
Debug.ShouldStop(134217728);
_counttosync = (int)(_counttosync+1);Debug.locals.put("countToSync", _counttosync);
 BA.debugLineNum = 253;BA.debugLine="moodd = moodd + 1";
Debug.ShouldStop(268435456);
_moodd = (int)(_moodd+1);Debug.locals.put("moodd", _moodd);
 };
 BA.debugLineNum = 261;BA.debugLine="Main.cur.Position = i";
Debug.ShouldStop(16);
mostCurrent._main._cur.setPosition(_i);
 BA.debugLineNum = 262;BA.debugLine="If Main.cur.GetString(\"Status\") = \"del\" Then";
Debug.ShouldStop(32);
if ((mostCurrent._main._cur.GetString("Status")).equals("del")) { 
 BA.debugLineNum = 263;BA.debugLine="ProgressDialogShow(\"Deleting Product(s) from TeleWeaver...\")";
Debug.ShouldStop(64);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Deleting Product(s) from TeleWeaver...");
 BA.debugLineNum = 264;BA.debugLine="RemProdOnTW";
Debug.ShouldStop(128);
_remprodontw();
 BA.debugLineNum = 266;BA.debugLine="countToSync = countToSync + 1";
Debug.ShouldStop(512);
_counttosync = (int)(_counttosync+1);Debug.locals.put("countToSync", _counttosync);
 };
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 270;BA.debugLine="If  countToSync = 0 Then";
Debug.ShouldStop(8192);
if (_counttosync==0) { 
 BA.debugLineNum = 271;BA.debugLine="MyT.ToastMessageShow2(\"No Products need to be synchronized at this time.\",6,60,50,\"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(16384);
mostCurrent._myt._toastmessageshow2("No Products need to be synchronized at this time.",(int)(6),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 };
 BA.debugLineNum = 275;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imageuser_click() throws Exception{
		Debug.PushSubsStack("imageUser_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 161;BA.debugLine="Sub imageUser_Click";
Debug.ShouldStop(1);
 BA.debugLineNum = 162;BA.debugLine="StartActivity(\"editArtist\")";
Debug.ShouldStop(2);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("editArtist"));
 BA.debugLineNum = 163;BA.debugLine="End Sub";
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
public static String  _imageviewprod_click() throws Exception{
		Debug.PushSubsStack("imageViewProd_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 165;BA.debugLine="Sub imageViewProd_Click";
Debug.ShouldStop(16);
 BA.debugLineNum = 166;BA.debugLine="StartActivity(\"viewproduct\")";
Debug.ShouldStop(32);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("viewproduct"));
 BA.debugLineNum = 167;BA.debugLine="End Sub";
Debug.ShouldStop(64);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imghelpbtn_click() throws Exception{
		Debug.PushSubsStack("imgHelpBtn_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 417;BA.debugLine="Sub imgHelpBtn_Click";
Debug.ShouldStop(1);
 BA.debugLineNum = 418;BA.debugLine="StartActivity(\"Help\")";
Debug.ShouldStop(2);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Help"));
 BA.debugLineNum = 419;BA.debugLine="End Sub";
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
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
		Debug.PushSubsStack("JobDone (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Job", _job);
 BA.debugLineNum = 343;BA.debugLine="Sub JobDone (Job As HttpJob)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 344;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 346;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" & Job.Success)";
Debug.ShouldStop(33554432);
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._jobname+", Success = "+String.valueOf(_job._success));
 BA.debugLineNum = 347;BA.debugLine="If Job.Success = True Then";
Debug.ShouldStop(67108864);
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 348;BA.debugLine="Select Job.JobName";
Debug.ShouldStop(134217728);
switch (BA.switchObjectToInt(_job._jobname,"PostProd","ModProd")) {
case 0:
 BA.debugLineNum = 350;BA.debugLine="Log(Job.GetString)";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 BA.debugLineNum = 352;BA.debugLine="MyT.ToastMessageShow2(\"Product(s) have been successfully synchronized and added!\",6,60,50,\"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(-2147483648);
mostCurrent._myt._toastmessageshow2("Product(s) have been successfully synchronized and added!",(int)(6),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 353;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE Product SET Status=? WHERE ProductID = \" & Main.cur.GetInt(\"ProductID\") , Array As Object(\"none\"))";
Debug.ShouldStop(1);
mostCurrent._main._asql.ExecNonQuery2("UPDATE Product SET Status=? WHERE ProductID = "+BA.NumberToString(mostCurrent._main._cur.GetInt("ProductID")),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("none")}));
 break;
case 1:
 BA.debugLineNum = 355;BA.debugLine="Log(Job.GetString)";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 BA.debugLineNum = 357;BA.debugLine="MyT.ToastMessageShow2(\"Products have been successfully synchronized and updated!\",6,60,50,\"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(16);
mostCurrent._myt._toastmessageshow2("Products have been successfully synchronized and updated!",(int)(6),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 358;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE Product SET Status=? WHERE ProductID = \" & Main.cur.GetInt(\"ProductID\") , Array As Object(\"none\"))";
Debug.ShouldStop(32);
mostCurrent._main._asql.ExecNonQuery2("UPDATE Product SET Status=? WHERE ProductID = "+BA.NumberToString(mostCurrent._main._cur.GetInt("ProductID")),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("none")}));
 break;
}
;
 }else {
 BA.debugLineNum = 362;BA.debugLine="Log(\"Error: \" & Job.ErrorMessage)";
Debug.ShouldStop(512);
anywheresoftware.b4a.keywords.Common.Log("Error: "+_job._errormessage);
 BA.debugLineNum = 363;BA.debugLine="MyT.ToastMessageShow2(\"Sorry could not sync with Teleweaver at this time. Try again later.\",5,60,50, \"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(1024);
mostCurrent._myt._toastmessageshow2("Sorry could not sync with Teleweaver at this time. Try again later.",(int)(5),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 };
 BA.debugLineNum = 366;BA.debugLine="Job.Release";
Debug.ShouldStop(8192);
_job._release();
 BA.debugLineNum = 368;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _language_itemclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("Language_ItemClick (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 215;BA.debugLine="Sub Language_ItemClick (Position As Int, Value As Object)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 216;BA.debugLine="If Language.SelectedItem = \"English\" Then";
Debug.ShouldStop(8388608);
if ((mostCurrent._language.getSelectedItem()).equals("English")) { 
 BA.debugLineNum = 217;BA.debugLine="Main.globLang = \"English\"";
Debug.ShouldStop(16777216);
mostCurrent._main._globlang = "English";
 }else 
{ BA.debugLineNum = 218;BA.debugLine="Else If Language.SelectedItem = \"isiXhosa\" Then";
Debug.ShouldStop(33554432);
if ((mostCurrent._language.getSelectedItem()).equals("isiXhosa")) { 
 BA.debugLineNum = 219;BA.debugLine="Main.globLang = \"isiXhosa\"";
Debug.ShouldStop(67108864);
mostCurrent._main._globlang = "isiXhosa";
 }};
 BA.debugLineNum = 221;BA.debugLine="changeLanguage";
Debug.ShouldStop(268435456);
_changelanguage();
 BA.debugLineNum = 222;BA.debugLine="End Sub";
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
public static String  _lbladdprod_click() throws Exception{
		Debug.PushSubsStack("lblAddProd_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 211;BA.debugLine="Sub lblAddProd_Click";
Debug.ShouldStop(262144);
 BA.debugLineNum = 212;BA.debugLine="imageAddProd_Click";
Debug.ShouldStop(524288);
_imageaddprod_click();
 BA.debugLineNum = 213;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lbleditprofile_click() throws Exception{
		Debug.PushSubsStack("lblEditProfile_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 207;BA.debugLine="Sub lblEditProfile_Click";
Debug.ShouldStop(16384);
 BA.debugLineNum = 208;BA.debugLine="imageEditProfile_Click";
Debug.ShouldStop(32768);
_imageeditprofile_click();
 BA.debugLineNum = 209;BA.debugLine="End Sub";
Debug.ShouldStop(65536);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lbllogout_click() throws Exception{
		Debug.PushSubsStack("lblLogout_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 204;BA.debugLine="Sub lblLogout_Click";
Debug.ShouldStop(2048);
 BA.debugLineNum = 205;BA.debugLine="imageLogOut_Click";
Debug.ShouldStop(4096);
_imagelogout_click();
 BA.debugLineNum = 206;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _lblsync_click() throws Exception{
		Debug.PushSubsStack("lblSync_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 200;BA.debugLine="Sub lblSync_Click";
Debug.ShouldStop(128);
 BA.debugLineNum = 201;BA.debugLine="imageSync_Click";
Debug.ShouldStop(256);
_imagesync_click();
 BA.debugLineNum = 202;BA.debugLine="End Sub";
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
public static String  _lbluser_click() throws Exception{
		Debug.PushSubsStack("lblUser_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 196;BA.debugLine="Sub lblUser_Click";
Debug.ShouldStop(8);
 BA.debugLineNum = 197;BA.debugLine="imageUser_Click";
Debug.ShouldStop(16);
_imageuser_click();
 BA.debugLineNum = 198;BA.debugLine="End Sub";
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
public static String  _lblviewproduct_click() throws Exception{
		Debug.PushSubsStack("lblViewProduct_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 192;BA.debugLine="Sub lblViewProduct_Click";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 193;BA.debugLine="imageViewProd_Click";
Debug.ShouldStop(1);
_imageviewprod_click();
 BA.debugLineNum = 194;BA.debugLine="End Sub";
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
public static String  _lblviewsales_click() throws Exception{
		Debug.PushSubsStack("lblViewSales_Click (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 188;BA.debugLine="Sub lblViewSales_Click";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 189;BA.debugLine="imageSales_Click";
Debug.ShouldStop(268435456);
_imagesales_click();
 BA.debugLineNum = 190;BA.debugLine="End Sub";
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
public static String  _modprodontw() throws Exception{
		Debug.PushSubsStack("modProdOnTW (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.samples.httputils2.httpjob _modprodjob = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _modprodurl = "";
anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _jsongenerator = null;
 BA.debugLineNum = 307;BA.debugLine="Sub modProdOnTW";
Debug.ShouldStop(262144);
 BA.debugLineNum = 309;BA.debugLine="Dim modProdJob As HttpJob";
Debug.ShouldStop(1048576);
_modprodjob = new anywheresoftware.b4a.samples.httputils2.httpjob();Debug.locals.put("modProdJob", _modprodjob);
 BA.debugLineNum = 310;BA.debugLine="Dim m As Map";
Debug.ShouldStop(2097152);
_m = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("m", _m);
 BA.debugLineNum = 311;BA.debugLine="Dim modProdUrl As String";
Debug.ShouldStop(4194304);
_modprodurl = "";Debug.locals.put("modProdUrl", _modprodurl);
 BA.debugLineNum = 312;BA.debugLine="modProdUrl = \"http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/modify-product/\"";
Debug.ShouldStop(8388608);
_modprodurl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/modify-product/";Debug.locals.put("modProdUrl", _modprodurl);
 BA.debugLineNum = 314;BA.debugLine="m.Initialize";
Debug.ShouldStop(33554432);
_m.Initialize();
 BA.debugLineNum = 315;BA.debugLine="m.Put(\"id\", Main.cur.GetInt(\"ProductID\"))";
Debug.ShouldStop(67108864);
_m.Put((Object)("id"),(Object)(mostCurrent._main._cur.GetInt("ProductID")));
 BA.debugLineNum = 316;BA.debugLine="m.Put(\"profile\", Main.globID)";
Debug.ShouldStop(134217728);
_m.Put((Object)("profile"),(Object)(mostCurrent._main._globid));
 BA.debugLineNum = 317;BA.debugLine="m.Put(\"name\", Main.cur.GetString(\"Name\"))";
Debug.ShouldStop(268435456);
_m.Put((Object)("name"),(Object)(mostCurrent._main._cur.GetString("Name")));
 BA.debugLineNum = 318;BA.debugLine="m.Put(\"dimension\", Main.cur.GetString(\"Size\"))";
Debug.ShouldStop(536870912);
_m.Put((Object)("dimension"),(Object)(mostCurrent._main._cur.GetString("Size")));
 BA.debugLineNum = 319;BA.debugLine="m.Put(\"price\", Main.cur.GetString(\"Price\"))";
Debug.ShouldStop(1073741824);
_m.Put((Object)("price"),(Object)(mostCurrent._main._cur.GetString("Price")));
 BA.debugLineNum = 320;BA.debugLine="m.Put(\"description\", Main.cur.GetString(\"Description\"))";
Debug.ShouldStop(-2147483648);
_m.Put((Object)("description"),(Object)(mostCurrent._main._cur.GetString("Description")));
 BA.debugLineNum = 321;BA.debugLine="m.Put(\"type\",Main.cur.GetString(\"Type\"))";
Debug.ShouldStop(1);
_m.Put((Object)("type"),(Object)(mostCurrent._main._cur.GetString("Type")));
 BA.debugLineNum = 322;BA.debugLine="m.Put(\"quantity\", 23)";
Debug.ShouldStop(2);
_m.Put((Object)("quantity"),(Object)(23));
 BA.debugLineNum = 323;BA.debugLine="retrieveANDconvert     ' retrieves the image from SQLite";
Debug.ShouldStop(4);
_retrieveandconvert();
 BA.debugLineNum = 324;BA.debugLine="m.Put(\"picture\", hexaPicture)";
Debug.ShouldStop(8);
_m.Put((Object)("picture"),(Object)(mostCurrent._hexapicture));
 BA.debugLineNum = 326;BA.debugLine="Dim JSONGenerator As JSONGenerator";
Debug.ShouldStop(32);
_jsongenerator = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();Debug.locals.put("JSONGenerator", _jsongenerator);
 BA.debugLineNum = 327;BA.debugLine="JSONGenerator.Initialize(m)";
Debug.ShouldStop(64);
_jsongenerator.Initialize(_m);
 BA.debugLineNum = 329;BA.debugLine="modProdJob.Initialize(\"ModProd\", Me)";
Debug.ShouldStop(256);
_modprodjob._initialize(processBA,"ModProd",menu.getObject());
 BA.debugLineNum = 330;BA.debugLine="modProdJob.PostString(modProdUrl, JSONGenerator.ToString())";
Debug.ShouldStop(512);
_modprodjob._poststring(_modprodurl,_jsongenerator.ToString());
 BA.debugLineNum = 331;BA.debugLine="modProdJob.GetRequest.SetContentType(\"application/json\")";
Debug.ShouldStop(1024);
_modprodjob._getrequest().SetContentType("application/json");
 BA.debugLineNum = 334;BA.debugLine="End Sub";
Debug.ShouldStop(8192);
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
public static String  _remprodontw() throws Exception{
		Debug.PushSubsStack("RemProdOnTW (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper _req = null;
String _remprodurl = "";
 BA.debugLineNum = 372;BA.debugLine="Sub RemProdOnTW   'Connect to the remote server and get the messages.";
Debug.ShouldStop(524288);
 BA.debugLineNum = 374;BA.debugLine="Dim req As HttpRequest				'Set up an http request connection";
Debug.ShouldStop(2097152);
_req = new anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper();Debug.locals.put("req", _req);
 BA.debugLineNum = 375;BA.debugLine="Dim remProdUrl As String";
Debug.ShouldStop(4194304);
_remprodurl = "";Debug.locals.put("remProdUrl", _remprodurl);
 BA.debugLineNum = 377;BA.debugLine="remProdUrl = \"http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/remove-product/\" & Main.globID";
Debug.ShouldStop(16777216);
_remprodurl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/remove-product/"+mostCurrent._main._globid;Debug.locals.put("remProdUrl", _remprodurl);
 BA.debugLineNum = 378;BA.debugLine="req.InitializeGet(remProdUrl)	 'Initialize the http get request";
Debug.ShouldStop(33554432);
_req.InitializeGet(_remprodurl);
 BA.debugLineNum = 380;BA.debugLine="hc.Execute(req, 1)						' And the execute it.";
Debug.ShouldStop(134217728);
mostCurrent._hc.Execute(processBA,_req,(int)(1));
 BA.debugLineNum = 383;BA.debugLine="End Sub";
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
public static String  _retrieveandconvert() throws Exception{
		Debug.PushSubsStack("retrieveANDconvert (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.StringUtils _su = null;
 BA.debugLineNum = 335;BA.debugLine="Sub retrieveANDconvert";
Debug.ShouldStop(16384);
 BA.debugLineNum = 336;BA.debugLine="Dim su As StringUtils";
Debug.ShouldStop(32768);
_su = new anywheresoftware.b4a.objects.StringUtils();Debug.locals.put("su", _su);
 BA.debugLineNum = 337;BA.debugLine="hexaPicture = su.EncodeBase64(Main.cur.Getblob(\"Picture\"))";
Debug.ShouldStop(65536);
mostCurrent._hexapicture = _su.EncodeBase64(mostCurrent._main._cur.GetBlob("Picture"));
 BA.debugLineNum = 340;BA.debugLine="End Sub";
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
public static String  _tbhpages_tabchanged() throws Exception{
		Debug.PushSubsStack("tbhPages_TabChanged (menu) ","menu",1,mostCurrent.activityBA,mostCurrent);
try {
int _tabidx = 0;
 BA.debugLineNum = 146;BA.debugLine="Sub tbhPages_TabChanged";
Debug.ShouldStop(131072);
 BA.debugLineNum = 147;BA.debugLine="Dim TabIdx As Int";
Debug.ShouldStop(262144);
_tabidx = 0;Debug.locals.put("TabIdx", _tabidx);
 BA.debugLineNum = 148;BA.debugLine="TabIdx = tbhPages.CurrentTab    ' Get the tab just activated";
Debug.ShouldStop(524288);
_tabidx = mostCurrent._tbhpages.getCurrentTab();Debug.locals.put("TabIdx", _tabidx);
 BA.debugLineNum = 149;BA.debugLine="Select TabIdx";
Debug.ShouldStop(1048576);
switch (_tabidx) {
case 0:
 break;
case 1:
 BA.debugLineNum = 153;BA.debugLine="StartActivity(\"viewproduct\")";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("viewproduct"));
 break;
case 2:
 break;
default:
 BA.debugLineNum = 157;BA.debugLine="Msgbox(\"Something is badly wrong! We have only three tabs\", \"HEY\")";
Debug.ShouldStop(268435456);
anywheresoftware.b4a.keywords.Common.Msgbox("Something is badly wrong! We have only three tabs","HEY",mostCurrent.activityBA);
 break;
}
;
 BA.debugLineNum = 159;BA.debugLine="End Sub";
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
  public Object[] GetGlobals() {
		return new Object[] {"Activity",_activity,"hc",_hc,"lblHeader",_lblheader,"lblUser",_lbluser,"imageLogo",_imagelogo,"btnConnected",_btnconnected,"imageUser",_imageuser,"myMsgBox",_mymsgbox,"lblSynch",_lblsynch,"pbSynch",_pbsynch,"pnlPage1",_pnlpage1,"pnlPage2",_pnlpage2,"pnlPage3",_pnlpage3,"curs",_curs,"tbhPages",_tbhpages,"Button1",_button1,"b",_b,"imageAddProd",_imageaddprod,"imageEditProfile",_imageeditprofile,"imageSales",_imagesales,"imageViewProd",_imageviewprod,"imageSync",_imagesync,"imageLogOut",_imagelogout,"lblAddProd",_lbladdprod,"lblEditProfile",_lbleditprofile,"lblViewSales",_lblviewsales,"lblViewProduct",_lblviewproduct,"lblSync",_lblsync,"lblLogout",_lbllogout,"hexaPicture",_hexapicture,"MyT",_myt,"Language",_language,"imgHelpBtn",_imghelpbtn,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
