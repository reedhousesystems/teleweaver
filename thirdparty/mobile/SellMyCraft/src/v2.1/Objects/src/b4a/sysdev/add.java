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

public class add extends Activity implements B4AActivity{
	public static add mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.sysdev", "b4a.sysdev.add");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (add).");
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
		activityBA = new BA(this, layout, processBA, "b4a.sysdev", "b4a.sysdev.add");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (add) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (add) Resume **");
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
		return add.class;
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
        BA.LogInfo("** Activity (add) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (add) Resume **");
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
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public b4a.sysdev.custommsgbox _mymsgbox = null;
public static boolean _picselected = false;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagelogo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgselected = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageviewtemp = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblheader = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblprice = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbconnection = null;
public static String _pictureanswer = "";
public anywheresoftware.b4a.objects.EditTextWrapper _txtprodname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtprice = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtproddes = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _sptype = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageaddprod = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgcancel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladdprod = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcancel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldimensions = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblprodname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblprodprice = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblprodtype = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagecancel = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _curs = null;
public anywheresoftware.b4a.sql.SQL _s = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtsize = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltype = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblemptyname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblemptyprice = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpricenumber = null;
public static String _hexapicture = "";
public anywheresoftware.b4a.objects.StringUtils _su = null;
public static byte[] _buffer = null;
public static String _viewornot = "";
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public static String _sss = "";
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public static int _p_id = 0;
public static String _dir2 = "";
public static String _filename2 = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblwrongsize = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladdtitle = null;
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.editartist _editartist = null;
public b4a.sysdev.help _help = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 64;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 67;BA.debugLine="Activity.LoadLayout(\"AddProduct\")";
Debug.ShouldStop(4);
mostCurrent._activity.LoadLayout("AddProduct",mostCurrent.activityBA);
 BA.debugLineNum = 69;BA.debugLine="cc.Initialize (\"chooser\")";
Debug.ShouldStop(16);
Debug.DebugWarningEngine.CheckInitialize(_cc);_cc.Initialize("chooser");
 BA.debugLineNum = 71;BA.debugLine="changeLanguage";
Debug.ShouldStop(64);
_changelanguage();
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 127;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 129;BA.debugLine="End Sub";
Debug.ShouldStop(1);
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
		Debug.PushSubsStack("Activity_Resume (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 123;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(67108864);
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
public static String  _add_click() throws Exception{
		Debug.PushSubsStack("Add_Click (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 469;BA.debugLine="Sub Add_Click";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 470;BA.debugLine="If myMsgBox.ButtonSelected = \"yes\" Then";
Debug.ShouldStop(2097152);
if ((mostCurrent._mymsgbox._buttonselected).equals("yes")) { 
 BA.debugLineNum = 471;BA.debugLine="txtProdName.Text = \"\"";
Debug.ShouldStop(4194304);
mostCurrent._txtprodname.setText((Object)(""));
 BA.debugLineNum = 472;BA.debugLine="txtSize.Text = \"\"";
Debug.ShouldStop(8388608);
mostCurrent._txtsize.setText((Object)(""));
 BA.debugLineNum = 473;BA.debugLine="txtPrice.Text= \"\"";
Debug.ShouldStop(16777216);
mostCurrent._txtprice.setText((Object)(""));
 BA.debugLineNum = 474;BA.debugLine="txtProdDes.Text= \"\"";
Debug.ShouldStop(33554432);
mostCurrent._txtproddes.setText((Object)(""));
 BA.debugLineNum = 475;BA.debugLine="picSelected = False";
Debug.ShouldStop(67108864);
_picselected = anywheresoftware.b4a.keywords.Common.False;
 BA.debugLineNum = 476;BA.debugLine="imgSelected.Bitmap = LoadBitmapSample(File.DirAssets, \"click to add image.png\",imgSelected.Width,imgSelected.Height)";
Debug.ShouldStop(134217728);
mostCurrent._imgselected.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"click to add image.png",mostCurrent._imgselected.getWidth(),mostCurrent._imgselected.getHeight()).getObject()));
 BA.debugLineNum = 477;BA.debugLine="txtProdName.RequestFocus";
Debug.ShouldStop(268435456);
mostCurrent._txtprodname.RequestFocus();
 }else {
 BA.debugLineNum = 479;BA.debugLine="Activity.Finish";
Debug.ShouldStop(1073741824);
mostCurrent._activity.Finish();
 BA.debugLineNum = 480;BA.debugLine="Activity.LoadLayout(\"theTab\")";
Debug.ShouldStop(-2147483648);
mostCurrent._activity.LoadLayout("theTab",mostCurrent.activityBA);
 };
 BA.debugLineNum = 483;BA.debugLine="End Sub";
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
public static String  _addprodtotw() throws Exception{
		Debug.PushSubsStack("addProdToTW (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.samples.httputils2.httpjob _postprodjob = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _postprodurl = "";
anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _jsongenerator = null;
 BA.debugLineNum = 207;BA.debugLine="Sub addProdToTW";
Debug.ShouldStop(16384);
 BA.debugLineNum = 209;BA.debugLine="Dim postProdJob As HttpJob";
Debug.ShouldStop(65536);
_postprodjob = new anywheresoftware.b4a.samples.httputils2.httpjob();Debug.locals.put("postProdJob", _postprodjob);
 BA.debugLineNum = 210;BA.debugLine="Dim m As Map";
Debug.ShouldStop(131072);
_m = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("m", _m);
 BA.debugLineNum = 211;BA.debugLine="Dim postProdUrl As String";
Debug.ShouldStop(262144);
_postprodurl = "";Debug.locals.put("postProdUrl", _postprodurl);
 BA.debugLineNum = 213;BA.debugLine="postProdUrl = \"http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/add-product/\"";
Debug.ShouldStop(1048576);
_postprodurl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/add-product/";Debug.locals.put("postProdUrl", _postprodurl);
 BA.debugLineNum = 215;BA.debugLine="m.Initialize";
Debug.ShouldStop(4194304);
_m.Initialize();
 BA.debugLineNum = 216;BA.debugLine="m.Put(\"id\", p_ID)";
Debug.ShouldStop(8388608);
_m.Put((Object)("id"),(Object)(_p_id));
 BA.debugLineNum = 217;BA.debugLine="m.Put(\"profile\", Main.m.Get(\"id\"))";
Debug.ShouldStop(16777216);
_m.Put((Object)("profile"),mostCurrent._main._m.Get((Object)("id")));
 BA.debugLineNum = 218;BA.debugLine="m.Put(\"name\", txtProdName.Text)";
Debug.ShouldStop(33554432);
_m.Put((Object)("name"),(Object)(mostCurrent._txtprodname.getText()));
 BA.debugLineNum = 219;BA.debugLine="m.Put(\"dimension\", txtSize.Text)";
Debug.ShouldStop(67108864);
_m.Put((Object)("dimension"),(Object)(mostCurrent._txtsize.getText()));
 BA.debugLineNum = 220;BA.debugLine="m.Put(\"price\", txtPrice.Text)";
Debug.ShouldStop(134217728);
_m.Put((Object)("price"),(Object)(mostCurrent._txtprice.getText()));
 BA.debugLineNum = 221;BA.debugLine="m.Put(\"description\", txtProdDes.Text)";
Debug.ShouldStop(268435456);
_m.Put((Object)("description"),(Object)(mostCurrent._txtproddes.getText()));
 BA.debugLineNum = 222;BA.debugLine="m.Put(\"type\", spType.SelectedItem)";
Debug.ShouldStop(536870912);
_m.Put((Object)("type"),(Object)(mostCurrent._sptype.getSelectedItem()));
 BA.debugLineNum = 223;BA.debugLine="m.Put(\"quantity\", 18)";
Debug.ShouldStop(1073741824);
_m.Put((Object)("quantity"),(Object)(18));
 BA.debugLineNum = 224;BA.debugLine="retrieveANDconvert     ' retrieves the image from SQLite or uses default";
Debug.ShouldStop(-2147483648);
_retrieveandconvert();
 BA.debugLineNum = 225;BA.debugLine="m.Put(\"picture\", hexaPicture)";
Debug.ShouldStop(1);
_m.Put((Object)("picture"),(Object)(mostCurrent._hexapicture));
 BA.debugLineNum = 228;BA.debugLine="Dim JSONGenerator As JSONGenerator";
Debug.ShouldStop(8);
_jsongenerator = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();Debug.locals.put("JSONGenerator", _jsongenerator);
 BA.debugLineNum = 229;BA.debugLine="JSONGenerator.Initialize(m)";
Debug.ShouldStop(16);
_jsongenerator.Initialize(_m);
 BA.debugLineNum = 230;BA.debugLine="postProdJob.Initialize(\"PostProd\", Me)";
Debug.ShouldStop(32);
_postprodjob._initialize(processBA,"PostProd",add.getObject());
 BA.debugLineNum = 231;BA.debugLine="postProdJob.PostString(postProdUrl, JSONGenerator.ToString())";
Debug.ShouldStop(64);
_postprodjob._poststring(_postprodurl,_jsongenerator.ToString());
 BA.debugLineNum = 232;BA.debugLine="postProdJob.GetRequest.SetContentType(\"application/json\")";
Debug.ShouldStop(128);
_postprodjob._getrequest().SetContentType("application/json");
 BA.debugLineNum = 235;BA.debugLine="End Sub";
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
public static String  _cam_click() throws Exception{
		Debug.PushSubsStack("Cam_Click (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 BA.debugLineNum = 449;BA.debugLine="Sub Cam_Click";
Debug.ShouldStop(1);
 BA.debugLineNum = 451;BA.debugLine="If  myMsgBox.ButtonSelected = \"yes\" Then";
Debug.ShouldStop(4);
if ((mostCurrent._mymsgbox._buttonselected).equals("yes")) { 
 BA.debugLineNum = 453;BA.debugLine="Dim i As Intent";
Debug.ShouldStop(16);
_i = new anywheresoftware.b4a.objects.IntentWrapper();Debug.locals.put("i", _i);
 BA.debugLineNum = 454;BA.debugLine="i.Initialize(\"android.media.action.IMAGE_CAPTURE\", \"\")";
Debug.ShouldStop(32);
_i.Initialize("android.media.action.IMAGE_CAPTURE","");
 BA.debugLineNum = 456;BA.debugLine="StartActivity(i)";
Debug.ShouldStop(128);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_i.getObject()));
 }else 
{ BA.debugLineNum = 457;BA.debugLine="Else If myMsgBox.ButtonSelected = \"no\" Then";
Debug.ShouldStop(256);
if ((mostCurrent._mymsgbox._buttonselected).equals("no")) { 
 BA.debugLineNum = 459;BA.debugLine="cc.show(\"image/*\", \"Choose a Picture\")";
Debug.ShouldStop(1024);
_cc.Show(processBA,"image/*","Choose a Picture");
 }};
 BA.debugLineNum = 467;BA.debugLine="End Sub";
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
public static String  _changelanguage() throws Exception{
		Debug.PushSubsStack("changeLanguage (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 75;BA.debugLine="Sub changeLanguage";
Debug.ShouldStop(1024);
 BA.debugLineNum = 77;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(4096);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 78;BA.debugLine="lblAddtitle.text = \"Faka Imveliso\"";
Debug.ShouldStop(8192);
mostCurrent._lbladdtitle.setText((Object)("Faka Imveliso"));
 BA.debugLineNum = 79;BA.debugLine="lblAddtitle.TextSize = 20";
Debug.ShouldStop(16384);
mostCurrent._lbladdtitle.setTextSize((float)(20));
 BA.debugLineNum = 80;BA.debugLine="txtProdName.Hint = \"Faka igama apha\"";
Debug.ShouldStop(32768);
mostCurrent._txtprodname.setHint("Faka igama apha");
 BA.debugLineNum = 81;BA.debugLine="lblprodName.Text = \"Imveliso igama\"";
Debug.ShouldStop(65536);
mostCurrent._lblprodname.setText((Object)("Imveliso igama"));
 BA.debugLineNum = 82;BA.debugLine="lblprodPrice.Text = \"Imveliso ixabiso\"";
Debug.ShouldStop(131072);
mostCurrent._lblprodprice.setText((Object)("Imveliso ixabiso"));
 BA.debugLineNum = 83;BA.debugLine="txtPrice.Hint = \"Faka ixabiso apha\"";
Debug.ShouldStop(262144);
mostCurrent._txtprice.setHint("Faka ixabiso apha");
 BA.debugLineNum = 84;BA.debugLine="lblprodType.Text = \"Umhlobo wemveliso\"";
Debug.ShouldStop(524288);
mostCurrent._lblprodtype.setText((Object)("Umhlobo wemveliso"));
 BA.debugLineNum = 85;BA.debugLine="spType.Prompt= \"Faka umhlobo wemveliso\"";
Debug.ShouldStop(1048576);
mostCurrent._sptype.setPrompt("Faka umhlobo wemveliso");
 BA.debugLineNum = 86;BA.debugLine="spType.AddAll(Array As String(\"Umzobo\",\"Umkhubeko\"))";
Debug.ShouldStop(2097152);
mostCurrent._sptype.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Umzobo","Umkhubeko"}));
 BA.debugLineNum = 87;BA.debugLine="lblDimensions.Text = \"Umlinganiselo wobude(LxBxH)\"";
Debug.ShouldStop(4194304);
mostCurrent._lbldimensions.setText((Object)("Umlinganiselo wobude(LxBxH)"));
 BA.debugLineNum = 88;BA.debugLine="txtSize.Hint = \"Faka ubude apha\"";
Debug.ShouldStop(8388608);
mostCurrent._txtsize.setHint("Faka ubude apha");
 BA.debugLineNum = 89;BA.debugLine="txtProdDes.Hint = \"Cacisa umveliso apha\"";
Debug.ShouldStop(16777216);
mostCurrent._txtproddes.setHint("Cacisa umveliso apha");
 BA.debugLineNum = 91;BA.debugLine="lblAddProd.Text = \"Qcina\"";
Debug.ShouldStop(67108864);
mostCurrent._lbladdprod.setText((Object)("Qcina"));
 BA.debugLineNum = 92;BA.debugLine="lblCancel.Text = \"Cima\"";
Debug.ShouldStop(134217728);
mostCurrent._lblcancel.setText((Object)("Cima"));
 BA.debugLineNum = 94;BA.debugLine="lblType.Text = \"Ndicela ufake umveliso\"";
Debug.ShouldStop(536870912);
mostCurrent._lbltype.setText((Object)("Ndicela ufake umveliso"));
 BA.debugLineNum = 95;BA.debugLine="lblemptyName.Text = \"Ndicela ufake igama lumveliso\"";
Debug.ShouldStop(1073741824);
mostCurrent._lblemptyname.setText((Object)("Ndicela ufake igama lumveliso"));
 BA.debugLineNum = 96;BA.debugLine="lblEmptyPrice.Text = \"Ndicela ufake ixabiso lomveliso\"";
Debug.ShouldStop(-2147483648);
mostCurrent._lblemptyprice.setText((Object)("Ndicela ufake ixabiso lomveliso"));
 BA.debugLineNum = 97;BA.debugLine="lblpricenumber.Text = \"Ixabiso fanenele libe yinombolo\"";
Debug.ShouldStop(1);
mostCurrent._lblpricenumber.setText((Object)("Ixabiso fanenele libe yinombolo"));
 BA.debugLineNum = 98;BA.debugLine="lblwrongsize.Text = \"Ubude fanenele bube yinombolo\"";
Debug.ShouldStop(2);
mostCurrent._lblwrongsize.setText((Object)("Ubude fanenele bube yinombolo"));
 }else {
 BA.debugLineNum = 100;BA.debugLine="txtProdName.Hint = \"Enter name here\"";
Debug.ShouldStop(8);
mostCurrent._txtprodname.setHint("Enter name here");
 BA.debugLineNum = 101;BA.debugLine="lblprodName.Text = \"Product name\"";
Debug.ShouldStop(16);
mostCurrent._lblprodname.setText((Object)("Product name"));
 BA.debugLineNum = 102;BA.debugLine="lblprodPrice.Text = \"Product price\"";
Debug.ShouldStop(32);
mostCurrent._lblprodprice.setText((Object)("Product price"));
 BA.debugLineNum = 103;BA.debugLine="txtPrice.Hint = \"Enter price here\"";
Debug.ShouldStop(64);
mostCurrent._txtprice.setHint("Enter price here");
 BA.debugLineNum = 104;BA.debugLine="lblprodType.Text = \"Product type\"";
Debug.ShouldStop(128);
mostCurrent._lblprodtype.setText((Object)("Product type"));
 BA.debugLineNum = 105;BA.debugLine="spType.Prompt= \"Enter product type\"";
Debug.ShouldStop(256);
mostCurrent._sptype.setPrompt("Enter product type");
 BA.debugLineNum = 106;BA.debugLine="spType.AddAll(Array As String(\"Art\",\"Craft\"))";
Debug.ShouldStop(512);
mostCurrent._sptype.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Art","Craft"}));
 BA.debugLineNum = 107;BA.debugLine="lblDimensions.Text = \"Enter size (LxBxH) [cm]\"";
Debug.ShouldStop(1024);
mostCurrent._lbldimensions.setText((Object)("Enter size (LxBxH) [cm]"));
 BA.debugLineNum = 108;BA.debugLine="txtSize.Hint = \"Enter size here\"";
Debug.ShouldStop(2048);
mostCurrent._txtsize.setHint("Enter size here");
 BA.debugLineNum = 109;BA.debugLine="txtProdDes.Hint = \"Enter a brief description of the product.\"";
Debug.ShouldStop(4096);
mostCurrent._txtproddes.setHint("Enter a brief description of the product.");
 BA.debugLineNum = 111;BA.debugLine="lblAddProd.Text = \"Save\"";
Debug.ShouldStop(16384);
mostCurrent._lbladdprod.setText((Object)("Save"));
 BA.debugLineNum = 112;BA.debugLine="lblCancel.Text = \"Cancel\"";
Debug.ShouldStop(32768);
mostCurrent._lblcancel.setText((Object)("Cancel"));
 BA.debugLineNum = 114;BA.debugLine="lblType.Text = \"Please enter the Product Type.\"";
Debug.ShouldStop(131072);
mostCurrent._lbltype.setText((Object)("Please enter the Product Type."));
 BA.debugLineNum = 115;BA.debugLine="lblemptyName.Text = \"Please enter the Product Name.\"";
Debug.ShouldStop(262144);
mostCurrent._lblemptyname.setText((Object)("Please enter the Product Name."));
 BA.debugLineNum = 116;BA.debugLine="lblEmptyPrice.Text = \"Please enter the Product Price.\"";
Debug.ShouldStop(524288);
mostCurrent._lblemptyprice.setText((Object)("Please enter the Product Price."));
 BA.debugLineNum = 117;BA.debugLine="lblpricenumber.Text = \"Product Price should be a number.\"";
Debug.ShouldStop(1048576);
mostCurrent._lblpricenumber.setText((Object)("Product Price should be a number."));
 BA.debugLineNum = 118;BA.debugLine="lblwrongsize.Text = \"The Size should be a number.\"";
Debug.ShouldStop(2097152);
mostCurrent._lblwrongsize.setText((Object)("The Size should be a number."));
 };
 BA.debugLineNum = 122;BA.debugLine="End Sub";
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
public static String  _chooser_result(boolean _success,String _dir,String _filename) throws Exception{
		Debug.PushSubsStack("chooser_Result (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Success", _success);
Debug.locals.put("Dir", _dir);
Debug.locals.put("FileName", _filename);
 BA.debugLineNum = 263;BA.debugLine="Sub chooser_Result (Success As Boolean, Dir As String, FileName As String)";
Debug.ShouldStop(64);
 BA.debugLineNum = 265;BA.debugLine="If Success Then";
Debug.ShouldStop(256);
if (_success) { 
 BA.debugLineNum = 266;BA.debugLine="imgSelected.Bitmap = LoadBitmapSample  (Dir, FileName,imgSelected.Width,imgSelected.Height)";
Debug.ShouldStop(512);
mostCurrent._imgselected.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(_dir,_filename,mostCurrent._imgselected.getWidth(),mostCurrent._imgselected.getHeight()).getObject()));
 BA.debugLineNum = 267;BA.debugLine="picSelected = Success";
Debug.ShouldStop(1024);
_picselected = _success;
 BA.debugLineNum = 268;BA.debugLine="Dir2 = Dir";
Debug.ShouldStop(2048);
mostCurrent._dir2 = _dir;
 BA.debugLineNum = 269;BA.debugLine="FileName2 = FileName";
Debug.ShouldStop(4096);
mostCurrent._filename2 = _filename;
 };
 BA.debugLineNum = 272;BA.debugLine="End Sub";
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim myMsgBox As CustomMsgBox";
mostCurrent._mymsgbox = new b4a.sysdev.custommsgbox();
 //BA.debugLineNum = 19;BA.debugLine="Dim picSelected As Boolean";
_picselected = false;
 //BA.debugLineNum = 20;BA.debugLine="Dim imageLogo As ImageView";
mostCurrent._imagelogo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim imgSelected As ImageView";
mostCurrent._imgselected = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim ImageViewTemp As ImageView";
mostCurrent._imageviewtemp = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim lblHeader As Label";
mostCurrent._lblheader = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim lblPrice As Label";
mostCurrent._lblprice = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim tbConnection As ToggleButton";
mostCurrent._tbconnection = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim pictureAnswer As String";
mostCurrent._pictureanswer = "";
 //BA.debugLineNum = 28;BA.debugLine="Dim txtProdName As EditText";
mostCurrent._txtprodname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim txtPrice As EditText";
mostCurrent._txtprice = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim txtProdDes As EditText";
mostCurrent._txtproddes = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim spType As Spinner";
mostCurrent._sptype = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim imageAddProd As ImageView";
mostCurrent._imageaddprod = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim imgCancel As ImageView";
mostCurrent._imgcancel = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim lblAddProd As Label";
mostCurrent._lbladdprod = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim lblCancel As Label";
mostCurrent._lblcancel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim lblDimensions As Label";
mostCurrent._lbldimensions = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim lblprodName As Label";
mostCurrent._lblprodname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim lblprodPrice As Label";
mostCurrent._lblprodprice = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim lblprodType As Label";
mostCurrent._lblprodtype = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim imageCancel As ImageView";
mostCurrent._imagecancel = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim curs As Cursor";
mostCurrent._curs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim s As SQL";
mostCurrent._s = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 45;BA.debugLine="Dim txtSize As EditText";
mostCurrent._txtsize = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim lblType As Label";
mostCurrent._lbltype = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim lblemptyName As Label";
mostCurrent._lblemptyname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim lblEmptyPrice As Label";
mostCurrent._lblemptyprice = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim lblpricenumber As Label";
mostCurrent._lblpricenumber = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Dim hexaPicture As String";
mostCurrent._hexapicture = "";
 //BA.debugLineNum = 51;BA.debugLine="Dim su As StringUtils";
mostCurrent._su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 52;BA.debugLine="Dim Buffer() As Byte 'declares an empty array";
_buffer = new byte[(int)(0)];
;
 //BA.debugLineNum = 53;BA.debugLine="Dim viewornot As String";
mostCurrent._viewornot = "";
 //BA.debugLineNum = 54;BA.debugLine="Dim WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim sss As String";
mostCurrent._sss = "";
 //BA.debugLineNum = 56;BA.debugLine="Dim b As Bitmap";
mostCurrent._b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim p_ID As Int";
_p_id = 0;
 //BA.debugLineNum = 59;BA.debugLine="Dim Dir2, FileName2 As String";
mostCurrent._dir2 = "";
mostCurrent._filename2 = "";
 //BA.debugLineNum = 60;BA.debugLine="Dim lblwrongsize As Label";
mostCurrent._lblwrongsize = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Dim lblAddtitle As Label";
mostCurrent._lbladdtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _imageaddprod_click() throws Exception{
		Debug.PushSubsStack("imageAddProd_Click (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
int _i = 0;
int _newprodid = 0;
 BA.debugLineNum = 147;BA.debugLine="Sub imageAddProd_Click";
Debug.ShouldStop(262144);
 BA.debugLineNum = 148;BA.debugLine="lblemptyName.Visible = False";
Debug.ShouldStop(524288);
mostCurrent._lblemptyname.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 149;BA.debugLine="lblEmptyPrice.Visible = False";
Debug.ShouldStop(1048576);
mostCurrent._lblemptyprice.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 150;BA.debugLine="lblType.Visible = False";
Debug.ShouldStop(2097152);
mostCurrent._lbltype.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 151;BA.debugLine="lblwrongsize.Visible = False";
Debug.ShouldStop(4194304);
mostCurrent._lblwrongsize.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 163;BA.debugLine="If txtProdName.Text = \"\" Then";
Debug.ShouldStop(4);
if ((mostCurrent._txtprodname.getText()).equals("")) { 
 BA.debugLineNum = 164;BA.debugLine="lblemptyName.Visible = True";
Debug.ShouldStop(8);
mostCurrent._lblemptyname.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 165;BA.debugLine="txtProdName.RequestFocus";
Debug.ShouldStop(16);
mostCurrent._txtprodname.RequestFocus();
 }else 
{ BA.debugLineNum = 166;BA.debugLine="Else If txtPrice.Text = \"\" Then";
Debug.ShouldStop(32);
if ((mostCurrent._txtprice.getText()).equals("")) { 
 BA.debugLineNum = 167;BA.debugLine="lblEmptyPrice.Visible = True";
Debug.ShouldStop(64);
mostCurrent._lblemptyprice.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 168;BA.debugLine="txtPrice.RequestFocus";
Debug.ShouldStop(128);
mostCurrent._txtprice.RequestFocus();
 }else 
{ BA.debugLineNum = 169;BA.debugLine="Else If Not (IsNumber (txtPrice.Text)) Then";
Debug.ShouldStop(256);
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._txtprice.getText()))) { 
 BA.debugLineNum = 170;BA.debugLine="lblpricenumber.Visible = True";
Debug.ShouldStop(512);
mostCurrent._lblpricenumber.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 171;BA.debugLine="txtPrice.RequestFocus";
Debug.ShouldStop(1024);
mostCurrent._txtprice.RequestFocus();
 }else {
 BA.debugLineNum = 180;BA.debugLine="curs = Main.aSQL.ExecQuery(\"SELECT ProductID FROM \" & Main.DBTableProduct)";
Debug.ShouldStop(524288);
mostCurrent._curs.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery("SELECT ProductID FROM "+mostCurrent._main._dbtableproduct)));
 BA.debugLineNum = 182;BA.debugLine="If curs.RowCount > 0 Then";
Debug.ShouldStop(2097152);
if (mostCurrent._curs.getRowCount()>0) { 
 BA.debugLineNum = 183;BA.debugLine="For i = 0 To curs.RowCount - 1";
Debug.ShouldStop(4194304);
{
final double step113 = 1;
final double limit113 = (int)(mostCurrent._curs.getRowCount()-1);
for (_i = (int)(0); (step113 > 0 && _i <= limit113) || (step113 < 0 && _i >= limit113); _i += step113) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 184;BA.debugLine="curs.Position = i";
Debug.ShouldStop(8388608);
mostCurrent._curs.setPosition(_i);
 BA.debugLineNum = 185;BA.debugLine="Dim NewProdID As Int";
Debug.ShouldStop(16777216);
_newprodid = 0;Debug.locals.put("NewProdID", _newprodid);
 BA.debugLineNum = 186;BA.debugLine="NewProdID = curs.GetInt(\"ProductID\")";
Debug.ShouldStop(33554432);
_newprodid = mostCurrent._curs.GetInt("ProductID");Debug.locals.put("NewProdID", _newprodid);
 }
}Debug.locals.put("i", _i);
;
 };
 BA.debugLineNum = 190;BA.debugLine="NewProdID = NewProdID + 1 ' add 1 to the ID number to make a new ID field";
Debug.ShouldStop(536870912);
_newprodid = (int)(_newprodid+1);Debug.locals.put("NewProdID", _newprodid);
 BA.debugLineNum = 191;BA.debugLine="p_ID = NewProdID";
Debug.ShouldStop(1073741824);
_p_id = _newprodid;
 BA.debugLineNum = 194;BA.debugLine="Insert_Image";
Debug.ShouldStop(2);
_insert_image();
 BA.debugLineNum = 195;BA.debugLine="Main.aSQL.ExecNonQuery2(\"INSERT INTO Product VALUES(?,?,?,?,?,?,?,?,?,?)\", Array As Object(NewProdID,txtProdName.Text,txtSize.Text, txtPrice.Text,txtProdDes.Text,spType.SelectedItem,Main.globID,Buffer,\"yes\",\"none\"))";
Debug.ShouldStop(4);
mostCurrent._main._asql.ExecNonQuery2("INSERT INTO Product VALUES(?,?,?,?,?,?,?,?,?,?)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_newprodid),(Object)(mostCurrent._txtprodname.getText()),(Object)(mostCurrent._txtsize.getText()),(Object)(mostCurrent._txtprice.getText()),(Object)(mostCurrent._txtproddes.getText()),(Object)(mostCurrent._sptype.getSelectedItem()),(Object)(mostCurrent._main._globid),(Object)(_buffer),(Object)("yes"),(Object)("none")}));
 BA.debugLineNum = 199;BA.debugLine="ProgressDialogShow(\"Adding Product to TeleWeaver...\")";
Debug.ShouldStop(64);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Adding Product to TeleWeaver...");
 BA.debugLineNum = 200;BA.debugLine="addProdToTW";
Debug.ShouldStop(128);
_addprodtotw();
 }}};
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
public static String  _imagecancel_click() throws Exception{
		Debug.PushSubsStack("imageCancel_Click (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 439;BA.debugLine="Sub imageCancel_Click";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 440;BA.debugLine="Activity.Finish";
Debug.ShouldStop(8388608);
mostCurrent._activity.Finish();
 BA.debugLineNum = 441;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imgselected_click() throws Exception{
		Debug.PushSubsStack("imgSelected_Click (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 274;BA.debugLine="Sub imgSelected_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 275;BA.debugLine="showMsgBoxCamera";
Debug.ShouldStop(262144);
_showmsgboxcamera();
 BA.debugLineNum = 276;BA.debugLine="End Sub";
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
public static String  _insert_image() throws Exception{
		Debug.PushSubsStack("Insert_Image (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _inputstream1 = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _outputstream1 = null;
 BA.debugLineNum = 307;BA.debugLine="Sub Insert_Image";
Debug.ShouldStop(262144);
 BA.debugLineNum = 308;BA.debugLine="Dim InputStream1 As InputStream";
Debug.ShouldStop(524288);
_inputstream1 = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("InputStream1", _inputstream1);
 BA.debugLineNum = 310;BA.debugLine="If picSelected Then";
Debug.ShouldStop(2097152);
if (_picselected) { 
 BA.debugLineNum = 311;BA.debugLine="InputStream1 = File.OpenInput(Dir2, FileName2)";
Debug.ShouldStop(4194304);
_inputstream1 = anywheresoftware.b4a.keywords.Common.File.OpenInput(mostCurrent._dir2,mostCurrent._filename2);Debug.locals.put("InputStream1", _inputstream1);
 }else {
 BA.debugLineNum = 313;BA.debugLine="InputStream1 = File.OpenInput(File.DirAssets, \"no product image.png\") '****";
Debug.ShouldStop(16777216);
_inputstream1 = anywheresoftware.b4a.keywords.Common.File.OpenInput(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"no product image.png");Debug.locals.put("InputStream1", _inputstream1);
 };
 BA.debugLineNum = 316;BA.debugLine="Dim OutputStream1 As OutputStream";
Debug.ShouldStop(134217728);
_outputstream1 = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();Debug.locals.put("OutputStream1", _outputstream1);
 BA.debugLineNum = 317;BA.debugLine="OutputStream1.InitializeToBytesArray(1000)";
Debug.ShouldStop(268435456);
_outputstream1.InitializeToBytesArray((int)(1000));
 BA.debugLineNum = 318;BA.debugLine="File.Copy2(InputStream1, OutputStream1)";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_inputstream1.getObject()),(java.io.OutputStream)(_outputstream1.getObject()));
 BA.debugLineNum = 320;BA.debugLine="Buffer = OutputStream1.ToBytesArray";
Debug.ShouldStop(-2147483648);
_buffer = _outputstream1.ToBytesArray();
 BA.debugLineNum = 321;BA.debugLine="InputStream1.Close";
Debug.ShouldStop(1);
_inputstream1.Close();
 BA.debugLineNum = 322;BA.debugLine="OutputStream1.Close";
Debug.ShouldStop(2);
_outputstream1.Close();
 BA.debugLineNum = 324;BA.debugLine="End Sub";
Debug.ShouldStop(8);
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
		Debug.PushSubsStack("JobDone (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
b4a.sysdev.ctoast _myt = null;
Debug.locals.put("Job", _job);
 BA.debugLineNum = 244;BA.debugLine="Sub JobDone (Job As HttpJob)";
Debug.ShouldStop(524288);
 BA.debugLineNum = 245;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(1048576);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 246;BA.debugLine="Dim MyT As CToast";
Debug.ShouldStop(2097152);
_myt = new b4a.sysdev.ctoast();Debug.locals.put("MyT", _myt);
 BA.debugLineNum = 247;BA.debugLine="MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)";
Debug.ShouldStop(4194304);
_myt._initialize(mostCurrent.activityBA,mostCurrent._activity,add.getObject(),mostCurrent._activity.getHeight(),mostCurrent._activity.getWidth());
 BA.debugLineNum = 248;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" & Job.Success)";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._jobname+", Success = "+String.valueOf(_job._success));
 BA.debugLineNum = 249;BA.debugLine="If Job.Success = True Then";
Debug.ShouldStop(16777216);
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 250;BA.debugLine="Select Job.JobName";
Debug.ShouldStop(33554432);
switch (BA.switchObjectToInt(_job._jobname,"PostProd")) {
case 0:
 BA.debugLineNum = 252;BA.debugLine="Log(Job.GetString)";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 BA.debugLineNum = 253;BA.debugLine="showMsgBoxAllGood";
Debug.ShouldStop(268435456);
_showmsgboxallgood();
 break;
}
;
 }else {
 BA.debugLineNum = 256;BA.debugLine="Log(\"Error: \" & Job.ErrorMessage)";
Debug.ShouldStop(-2147483648);
anywheresoftware.b4a.keywords.Common.Log("Error: "+_job._errormessage);
 BA.debugLineNum = 257;BA.debugLine="showMsgBoxHalfGood";
Debug.ShouldStop(1);
_showmsgboxhalfgood();
 };
 BA.debugLineNum = 259;BA.debugLine="Job.Release";
Debug.ShouldStop(4);
_job._release();
 BA.debugLineNum = 261;BA.debugLine="End Sub";
Debug.ShouldStop(16);
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
		Debug.PushSubsStack("lblAddProd_Click (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 445;BA.debugLine="Sub lblAddProd_Click";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 446;BA.debugLine="imageAddProd_Click";
Debug.ShouldStop(536870912);
_imageaddprod_click();
 BA.debugLineNum = 447;BA.debugLine="End Sub";
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
public static String  _lblcancel_click() throws Exception{
		Debug.PushSubsStack("lblCancel_Click (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 442;BA.debugLine="Sub lblCancel_Click";
Debug.ShouldStop(33554432);
 BA.debugLineNum = 443;BA.debugLine="imageCancel_Click";
Debug.ShouldStop(67108864);
_imagecancel_click();
 BA.debugLineNum = 444;BA.debugLine="End Sub";
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim cc As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _retrieveandconvert() throws Exception{
		Debug.PushSubsStack("retrieveANDconvert (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 237;BA.debugLine="Sub retrieveANDconvert";
Debug.ShouldStop(4096);
 BA.debugLineNum = 239;BA.debugLine="hexaPicture = su.EncodeBase64(Buffer)";
Debug.ShouldStop(16384);
mostCurrent._hexapicture = mostCurrent._su.EncodeBase64(_buffer);
 BA.debugLineNum = 242;BA.debugLine="End Sub";
Debug.ShouldStop(131072);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _showmsgboxallgood() throws Exception{
		Debug.PushSubsStack("showMsgBoxAllGood (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 326;BA.debugLine="Sub showMsgBoxAllGood";
Debug.ShouldStop(32);
 BA.debugLineNum = 328;BA.debugLine="myMsgBox.Initialize(Activity, Me, \"Add\", \"H\", 2, 95%x, 200dip, LoadBitmap(File.DirAssets, \"OKAddIcon.png\"))";
Debug.ShouldStop(128);
mostCurrent._mymsgbox._initialize(mostCurrent.activityBA,mostCurrent._activity,add.getObject(),"Add","H",(int)(2),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(200)),anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"OKAddIcon.png"));
 BA.debugLineNum = 331;BA.debugLine="myMsgBox.Title.textColor = Colors.white";
Debug.ShouldStop(1024);
mostCurrent._mymsgbox._title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 332;BA.debugLine="myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD";
Debug.ShouldStop(2048);
mostCurrent._mymsgbox._title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 BA.debugLineNum = 333;BA.debugLine="myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, \"B.png\"))";
Debug.ShouldStop(4096);
mostCurrent._mymsgbox._panel.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"B.png").getObject()));
 BA.debugLineNum = 335;BA.debugLine="myMsgBox.ShowSeparators(Colors.black, Colors.black)";
Debug.ShouldStop(16384);
mostCurrent._mymsgbox._showseparators(anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 337;BA.debugLine="myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))";
Debug.ShouldStop(65536);
mostCurrent._mymsgbox._showshadow(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int)(80),(int)(8),(int)(180),(int)(206)));
 BA.debugLineNum = 339;BA.debugLine="myMsgBox.YesButtonPanel.Color = Colors.white";
Debug.ShouldStop(262144);
mostCurrent._mymsgbox._yesbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 340;BA.debugLine="myMsgBox.NoButtonPanel.Color = Colors.white";
Debug.ShouldStop(524288);
mostCurrent._mymsgbox._nobuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 342;BA.debugLine="myMsgBox.NoButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(2097152);
mostCurrent._mymsgbox._nobuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 343;BA.debugLine="myMsgBox.YesButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(4194304);
mostCurrent._mymsgbox._yesbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 345;BA.debugLine="myMsgBox.Message.TextColor = Colors.white";
Debug.ShouldStop(16777216);
mostCurrent._mymsgbox._message.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 346;BA.debugLine="myMsgBox.Message.TextSize = 13";
Debug.ShouldStop(33554432);
mostCurrent._mymsgbox._message.setTextSize((float)(13));
 BA.debugLineNum = 349;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(268435456);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 351;BA.debugLine="myMsgBox.NoButtonCaption.Text = \"Xha\"";
Debug.ShouldStop(1073741824);
mostCurrent._mymsgbox._nobuttoncaption.setText((Object)("Xha"));
 BA.debugLineNum = 352;BA.debugLine="myMsgBox.YesButtonCaption.Text = \"Ewe\"";
Debug.ShouldStop(-2147483648);
mostCurrent._mymsgbox._yesbuttoncaption.setText((Object)("Ewe"));
 }else {
 BA.debugLineNum = 354;BA.debugLine="myMsgBox.Title.Text = \"Product Added\"";
Debug.ShouldStop(2);
mostCurrent._mymsgbox._title.setText((Object)("Product Added"));
 BA.debugLineNum = 355;BA.debugLine="myMsgBox.Title.TextSize = 20";
Debug.ShouldStop(4);
mostCurrent._mymsgbox._title.setTextSize((float)(20));
 BA.debugLineNum = 356;BA.debugLine="myMsgBox.ShowMessage(\"Product has been successfully synchronized and added!\" & CRLF & \"Would you like to add more Products now?\",\"C\")";
Debug.ShouldStop(8);
mostCurrent._mymsgbox._showmessage("Product has been successfully synchronized and added!"+anywheresoftware.b4a.keywords.Common.CRLF+"Would you like to add more Products now?","C");
 };
 BA.debugLineNum = 359;BA.debugLine="End Sub";
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
public static String  _showmsgboxcamera() throws Exception{
		Debug.PushSubsStack("showMsgBoxCamera (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 398;BA.debugLine="Sub showMsgBoxCamera";
Debug.ShouldStop(8192);
 BA.debugLineNum = 400;BA.debugLine="myMsgBox.Initialize(Activity, Me, \"Cam\", \"H\", 3, 95%x, 200dip, LoadBitmap(File.DirAssets, \"cameraMsg.png\"))";
Debug.ShouldStop(32768);
mostCurrent._mymsgbox._initialize(mostCurrent.activityBA,mostCurrent._activity,add.getObject(),"Cam","H",(int)(3),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(200)),anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cameraMsg.png"));
 BA.debugLineNum = 403;BA.debugLine="myMsgBox.Title.textColor = Colors.white";
Debug.ShouldStop(262144);
mostCurrent._mymsgbox._title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 404;BA.debugLine="myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD";
Debug.ShouldStop(524288);
mostCurrent._mymsgbox._title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 BA.debugLineNum = 405;BA.debugLine="myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, \"B.png\"))";
Debug.ShouldStop(1048576);
mostCurrent._mymsgbox._panel.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"B.png").getObject()));
 BA.debugLineNum = 407;BA.debugLine="myMsgBox.ShowSeparators(Colors.black, Colors.black)";
Debug.ShouldStop(4194304);
mostCurrent._mymsgbox._showseparators(anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 408;BA.debugLine="myMsgBox.Message.Height = 10dip";
Debug.ShouldStop(8388608);
mostCurrent._mymsgbox._message.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(10)));
 BA.debugLineNum = 409;BA.debugLine="myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))";
Debug.ShouldStop(16777216);
mostCurrent._mymsgbox._showshadow(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int)(80),(int)(8),(int)(180),(int)(206)));
 BA.debugLineNum = 411;BA.debugLine="myMsgBox.YesButtonPanel.Color = Colors.white";
Debug.ShouldStop(67108864);
mostCurrent._mymsgbox._yesbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 412;BA.debugLine="myMsgBox.NoButtonPanel.Color = Colors.white";
Debug.ShouldStop(134217728);
mostCurrent._mymsgbox._nobuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 413;BA.debugLine="myMsgBox.CancelButtonPanel.Color = Colors.white";
Debug.ShouldStop(268435456);
mostCurrent._mymsgbox._cancelbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 415;BA.debugLine="myMsgBox.NoButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(1073741824);
mostCurrent._mymsgbox._nobuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 416;BA.debugLine="myMsgBox.CancelButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(-2147483648);
mostCurrent._mymsgbox._cancelbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 417;BA.debugLine="myMsgBox.YesButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(1);
mostCurrent._mymsgbox._yesbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 419;BA.debugLine="myMsgBox.Message.TextColor = Colors.white";
Debug.ShouldStop(4);
mostCurrent._mymsgbox._message.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 420;BA.debugLine="myMsgBox.Message.TextSize = 13";
Debug.ShouldStop(8);
mostCurrent._mymsgbox._message.setTextSize((float)(13));
 BA.debugLineNum = 422;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(32);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 424;BA.debugLine="myMsgBox.NoButtonCaption.Text = \"Xha\"";
Debug.ShouldStop(128);
mostCurrent._mymsgbox._nobuttoncaption.setText((Object)("Xha"));
 BA.debugLineNum = 425;BA.debugLine="myMsgBox.YesButtonCaption.Text = \"Ewe\"";
Debug.ShouldStop(256);
mostCurrent._mymsgbox._yesbuttoncaption.setText((Object)("Ewe"));
 BA.debugLineNum = 426;BA.debugLine="myMsgBox.CancelButtonCaption.Text = \"Ewe\"";
Debug.ShouldStop(512);
mostCurrent._mymsgbox._cancelbuttoncaption.setText((Object)("Ewe"));
 }else {
 BA.debugLineNum = 428;BA.debugLine="myMsgBox.NoButtonCaption.Text = \"From Gallery\"";
Debug.ShouldStop(2048);
mostCurrent._mymsgbox._nobuttoncaption.setText((Object)("From Gallery"));
 BA.debugLineNum = 429;BA.debugLine="myMsgBox.YesButtonCaption.Text = \"Take Picture\"";
Debug.ShouldStop(4096);
mostCurrent._mymsgbox._yesbuttoncaption.setText((Object)("Take Picture"));
 BA.debugLineNum = 430;BA.debugLine="myMsgBox.CancelButtonCaption.Text = \"Cancel\"";
Debug.ShouldStop(8192);
mostCurrent._mymsgbox._cancelbuttoncaption.setText((Object)("Cancel"));
 BA.debugLineNum = 432;BA.debugLine="myMsgBox.Title.Text = \"Picture\"";
Debug.ShouldStop(32768);
mostCurrent._mymsgbox._title.setText((Object)("Picture"));
 BA.debugLineNum = 433;BA.debugLine="myMsgBox.Title.TextSize = 20";
Debug.ShouldStop(65536);
mostCurrent._mymsgbox._title.setTextSize((float)(20));
 BA.debugLineNum = 434;BA.debugLine="myMsgBox.ShowMessage(\"Please select an option\",\"C\")";
Debug.ShouldStop(131072);
mostCurrent._mymsgbox._showmessage("Please select an option","C");
 };
 BA.debugLineNum = 437;BA.debugLine="End Sub";
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
public static String  _showmsgboxhalfgood() throws Exception{
		Debug.PushSubsStack("showMsgBoxHalfGood (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 361;BA.debugLine="Sub showMsgBoxHalfGood";
Debug.ShouldStop(256);
 BA.debugLineNum = 363;BA.debugLine="myMsgBox.Initialize(Activity, Me, \"Add\", \"H\", 2, 95%x, 200dip, LoadBitmap(File.DirAssets, \"OKAddIcon.png\"))";
Debug.ShouldStop(1024);
mostCurrent._mymsgbox._initialize(mostCurrent.activityBA,mostCurrent._activity,add.getObject(),"Add","H",(int)(2),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(200)),anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"OKAddIcon.png"));
 BA.debugLineNum = 364;BA.debugLine="myMsgBox.Title.textColor = Colors.white";
Debug.ShouldStop(2048);
mostCurrent._mymsgbox._title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 365;BA.debugLine="myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD";
Debug.ShouldStop(4096);
mostCurrent._mymsgbox._title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 BA.debugLineNum = 366;BA.debugLine="myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, \"B.png\"))";
Debug.ShouldStop(8192);
mostCurrent._mymsgbox._panel.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"B.png").getObject()));
 BA.debugLineNum = 368;BA.debugLine="myMsgBox.ShowSeparators(Colors.black, Colors.black)";
Debug.ShouldStop(32768);
mostCurrent._mymsgbox._showseparators(anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 370;BA.debugLine="myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))";
Debug.ShouldStop(131072);
mostCurrent._mymsgbox._showshadow(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int)(80),(int)(8),(int)(180),(int)(206)));
 BA.debugLineNum = 372;BA.debugLine="myMsgBox.YesButtonPanel.Color = Colors.white";
Debug.ShouldStop(524288);
mostCurrent._mymsgbox._yesbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 373;BA.debugLine="myMsgBox.NoButtonPanel.Color = Colors.white";
Debug.ShouldStop(1048576);
mostCurrent._mymsgbox._nobuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 375;BA.debugLine="myMsgBox.NoButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(4194304);
mostCurrent._mymsgbox._nobuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 376;BA.debugLine="myMsgBox.YesButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(8388608);
mostCurrent._mymsgbox._yesbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 378;BA.debugLine="myMsgBox.Message.TextColor = Colors.white";
Debug.ShouldStop(33554432);
mostCurrent._mymsgbox._message.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 379;BA.debugLine="myMsgBox.Message.TextSize = 13";
Debug.ShouldStop(67108864);
mostCurrent._mymsgbox._message.setTextSize((float)(13));
 BA.debugLineNum = 381;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(268435456);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 383;BA.debugLine="myMsgBox.NoButtonCaption.Text = \"Xha\"";
Debug.ShouldStop(1073741824);
mostCurrent._mymsgbox._nobuttoncaption.setText((Object)("Xha"));
 BA.debugLineNum = 384;BA.debugLine="myMsgBox.YesButtonCaption.Text = \"Ewe\"";
Debug.ShouldStop(-2147483648);
mostCurrent._mymsgbox._yesbuttoncaption.setText((Object)("Ewe"));
 }else {
 BA.debugLineNum = 386;BA.debugLine="myMsgBox.Title.Text = \"Product Added Locally\"";
Debug.ShouldStop(2);
mostCurrent._mymsgbox._title.setText((Object)("Product Added Locally"));
 BA.debugLineNum = 387;BA.debugLine="myMsgBox.Title.TextSize = 20";
Debug.ShouldStop(4);
mostCurrent._mymsgbox._title.setTextSize((float)(20));
 BA.debugLineNum = 388;BA.debugLine="myMsgBox.ShowMessage(\"Sorry could not sync with Teleweaver at this time. Add was only local.\" & CRLF & \"Would you like to add more Products now?\",\"C\")";
Debug.ShouldStop(8);
mostCurrent._mymsgbox._showmessage("Sorry could not sync with Teleweaver at this time. Add was only local."+anywheresoftware.b4a.keywords.Common.CRLF+"Would you like to add more Products now?","C");
 BA.debugLineNum = 390;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE Product SET Status=? WHERE ProductID = \" & p_ID , Array As Object(\"add\"))  ' remember it needs to be synced. LATERRR";
Debug.ShouldStop(32);
mostCurrent._main._asql.ExecNonQuery2("UPDATE Product SET Status=? WHERE ProductID = "+BA.NumberToString(_p_id),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("add")}));
 };
 BA.debugLineNum = 395;BA.debugLine="End Sub";
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
public static String  _view_image() throws Exception{
		Debug.PushSubsStack("view_Image (add) ","add",4,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 278;BA.debugLine="Sub view_Image";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 300;BA.debugLine="End Sub";
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
  public Object[] GetGlobals() {
		return new Object[] {"Activity",_activity,"cc",_cc,"myMsgBox",_mymsgbox,"picSelected",_picselected,"imageLogo",_imagelogo,"imgSelected",_imgselected,"ImageViewTemp",_imageviewtemp,"lblHeader",_lblheader,"lblPrice",_lblprice,"tbConnection",_tbconnection,"pictureAnswer",_pictureanswer,"txtProdName",_txtprodname,"txtPrice",_txtprice,"txtProdDes",_txtproddes,"spType",_sptype,"imageAddProd",_imageaddprod,"imgCancel",_imgcancel,"lblAddProd",_lbladdprod,"lblCancel",_lblcancel,"lblDimensions",_lbldimensions,"lblprodName",_lblprodname,"lblprodPrice",_lblprodprice,"lblprodType",_lblprodtype,"imageCancel",_imagecancel,"curs",_curs,"s",_s,"txtSize",_txtsize,"lblType",_lbltype,"lblemptyName",_lblemptyname,"lblEmptyPrice",_lblemptyprice,"lblpricenumber",_lblpricenumber,"hexaPicture",_hexapicture,"su",_su,"Buffer",_buffer,"viewornot",_viewornot,"WebView1",_webview1,"sss",_sss,"b",_b,"Button1",_button1,"p_ID",_p_id,"Dir2",_dir2,"FileName2",_filename2,"lblwrongsize",_lblwrongsize,"lblAddtitle",_lbladdtitle,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
