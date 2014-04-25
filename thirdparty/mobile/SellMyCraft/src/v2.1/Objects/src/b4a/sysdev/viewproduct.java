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

public class viewproduct extends Activity implements B4AActivity{
	public static viewproduct mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.sysdev", "b4a.sysdev.viewproduct");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (viewproduct).");
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
		activityBA = new BA(this, layout, processBA, "b4a.sysdev", "b4a.sysdev.viewproduct");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (viewproduct) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (viewproduct) Resume **");
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
		return viewproduct.class;
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
        BA.LogInfo("** Activity (viewproduct) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (viewproduct) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static boolean _picselected = false;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scvmain = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnledit_products = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btneditback = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageviewonedit = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagevtemp = null;
public static String _pictureanswer = "";
public anywheresoftware.b4a.objects.EditTextWrapper _txteditname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txteditprice = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnupdate = null;
public static int _theprodid = 0;
public anywheresoftware.b4a.objects.SpinnerWrapper _spedittype = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtsize = null;
public static String _hexapicture = "";
public anywheresoftware.b4a.objects.StringUtils _su = null;
public static String _dir2 = "";
public static String _filename2 = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _imagevweditprodpic = null;
public static int _icounter = 0;
public static byte[] _buff = null;
public static int _i2 = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lblemptyprice = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblemptyprod = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblwrongsize = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txttype = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpricenumber = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txteditdesc = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bitt = null;
public static boolean _bool_view = false;
public anywheresoftware.b4a.objects.LabelWrapper _alabel1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageupdate = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txteditsize = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbleditprodname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbleditprodprice = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbleditprodtype = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbleditprodsize = null;
public static boolean _errordelontw = false;
public anywheresoftware.b4a.objects.LabelWrapper _lbleditproddes = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblupdate = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcancel = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public b4a.sysdev.custommsgbox _mymsgbox = null;
public static int _btndelidtag = 0;
public anywheresoftware.b4a.http.HttpClientWrapper _hc = null;
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.editartist _editartist = null;
public b4a.sysdev.help _help = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 63;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 64;BA.debugLine="scvMain.Initialize(50)";
Debug.ShouldStop(-2147483648);
mostCurrent._scvmain.Initialize(mostCurrent.activityBA,(int)(50));
 BA.debugLineNum = 65;BA.debugLine="Activity.AddView(scvMain,0,120,100%x,100%y)";
Debug.ShouldStop(1);
mostCurrent._activity.AddView((android.view.View)(mostCurrent._scvmain.getObject()),(int)(0),(int)(120),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float)(100),mostCurrent.activityBA));
 BA.debugLineNum = 67;BA.debugLine="pnlEdit_Products.Initialize(\"\")";
Debug.ShouldStop(4);
mostCurrent._pnledit_products.Initialize(mostCurrent.activityBA,"");
 BA.debugLineNum = 68;BA.debugLine="Activity.AddView(pnlEdit_Products, 0, 10, 100%x, 100%y)";
Debug.ShouldStop(8);
mostCurrent._activity.AddView((android.view.View)(mostCurrent._pnledit_products.getObject()),(int)(0),(int)(10),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float)(100),mostCurrent.activityBA));
 BA.debugLineNum = 69;BA.debugLine="pnlEdit_Products.LoadLayout(\"pnlEdit_products\")";
Debug.ShouldStop(16);
mostCurrent._pnledit_products.LoadLayout("pnlEdit_products",mostCurrent.activityBA);
 BA.debugLineNum = 70;BA.debugLine="spEDITtype.AddAll(Array As String(\"Art\",\"Craft\"))";
Debug.ShouldStop(32);
mostCurrent._spedittype.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"Art","Craft"}));
 BA.debugLineNum = 71;BA.debugLine="pnlEdit_Products.Visible = False";
Debug.ShouldStop(64);
mostCurrent._pnledit_products.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 72;BA.debugLine="CC.Initialize(\"chooser2\")";
Debug.ShouldStop(128);
Debug.DebugWarningEngine.CheckInitialize(_cc);_cc.Initialize("chooser2");
 BA.debugLineNum = 73;BA.debugLine="getProducts";
Debug.ShouldStop(256);
_getproducts();
 BA.debugLineNum = 74;BA.debugLine="lblemptyprod.Visible = False";
Debug.ShouldStop(512);
mostCurrent._lblemptyprod.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 75;BA.debugLine="lblemptyprice.Visible = False";
Debug.ShouldStop(1024);
mostCurrent._lblemptyprice.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 76;BA.debugLine="lblWrongSize.Visible = False";
Debug.ShouldStop(2048);
mostCurrent._lblwrongsize.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 77;BA.debugLine="lblpricenumber.Visible = False";
Debug.ShouldStop(4096);
mostCurrent._lblpricenumber.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 78;BA.debugLine="changeLanguage";
Debug.ShouldStop(8192);
_changelanguage();
 BA.debugLineNum = 82;BA.debugLine="hc.Initialize(\"hc\")";
Debug.ShouldStop(131072);
Debug.DebugWarningEngine.CheckInitialize(mostCurrent._hc);mostCurrent._hc.Initialize("hc");
 BA.debugLineNum = 83;BA.debugLine="End Sub";
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
public static boolean  _activity_keypress(int _keycode) throws Exception{
		Debug.PushSubsStack("Activity_KeyPress (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("KeyCode", _keycode);
 BA.debugLineNum = 278;BA.debugLine="Sub Activity_KeyPress(KeyCode As Int) As Boolean";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 281;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
Debug.ShouldStop(16777216);
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 BA.debugLineNum = 282;BA.debugLine="If pnlEdit_Products.Visible = True Then";
Debug.ShouldStop(33554432);
if (mostCurrent._pnledit_products.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 283;BA.debugLine="pnlEdit_Products.Visible = False";
Debug.ShouldStop(67108864);
mostCurrent._pnledit_products.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 284;BA.debugLine="aLabel1.TextColor = Colors.black";
Debug.ShouldStop(134217728);
mostCurrent._alabel1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 285;BA.debugLine="Return True";
Debug.ShouldStop(268435456);
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 BA.debugLineNum = 287;BA.debugLine="Return False";
Debug.ShouldStop(1073741824);
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 };
 BA.debugLineNum = 291;BA.debugLine="End Sub";
Debug.ShouldStop(4);
return false;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 89;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 91;BA.debugLine="End Sub";
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 85;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 86;BA.debugLine="getProducts";
Debug.ShouldStop(2097152);
_getproducts();
 BA.debugLineNum = 87;BA.debugLine="End Sub";
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
public static String  _btnupdate_click() throws Exception{
		Debug.PushSubsStack("btnUpdate_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 331;BA.debugLine="Sub btnUpdate_Click";
Debug.ShouldStop(1024);
 BA.debugLineNum = 333;BA.debugLine="lblemptyprod.Visible = False";
Debug.ShouldStop(4096);
mostCurrent._lblemptyprod.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 334;BA.debugLine="lblemptyprice.Visible = False";
Debug.ShouldStop(8192);
mostCurrent._lblemptyprice.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 335;BA.debugLine="lblpricenumber.Visible = False";
Debug.ShouldStop(16384);
mostCurrent._lblpricenumber.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 336;BA.debugLine="lblWrongSize.Visible = False";
Debug.ShouldStop(32768);
mostCurrent._lblwrongsize.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 338;BA.debugLine="If txtEDITName.Text.Trim.Length = 0 Then";
Debug.ShouldStop(131072);
if (mostCurrent._txteditname.getText().trim().length()==0) { 
 BA.debugLineNum = 339;BA.debugLine="lblemptyprod.Visible = True";
Debug.ShouldStop(262144);
mostCurrent._lblemptyprod.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 340;BA.debugLine="txtEDITprice.RequestFocus";
Debug.ShouldStop(524288);
mostCurrent._txteditprice.RequestFocus();
 }else 
{ BA.debugLineNum = 341;BA.debugLine="Else If txtEDITprice.Text.Trim.Length = 0 Then";
Debug.ShouldStop(1048576);
if (mostCurrent._txteditprice.getText().trim().length()==0) { 
 BA.debugLineNum = 342;BA.debugLine="lblemptyprice.Visible = True";
Debug.ShouldStop(2097152);
mostCurrent._lblemptyprice.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 343;BA.debugLine="txtEDITprice.RequestFocus";
Debug.ShouldStop(4194304);
mostCurrent._txteditprice.RequestFocus();
 }else 
{ BA.debugLineNum = 344;BA.debugLine="Else If Not (IsNumber (txtEDITprice.Text)) Then";
Debug.ShouldStop(8388608);
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._txteditprice.getText()))) { 
 BA.debugLineNum = 345;BA.debugLine="lblpricenumber.Visible = True";
Debug.ShouldStop(16777216);
mostCurrent._lblpricenumber.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 346;BA.debugLine="txtEDITprice.RequestFocus";
Debug.ShouldStop(33554432);
mostCurrent._txteditprice.RequestFocus();
 }else {
 BA.debugLineNum = 349;BA.debugLine="Main.cur = Main.aSQL.ExecQuery(\"SELECT * FROM Product WHERE ProductID = \" & theProdID)";
Debug.ShouldStop(268435456);
mostCurrent._main._cur.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery("SELECT * FROM Product WHERE ProductID = "+BA.NumberToString(_theprodid))));
 BA.debugLineNum = 350;BA.debugLine="Main.cur.Position = 0";
Debug.ShouldStop(536870912);
mostCurrent._main._cur.setPosition((int)(0));
 BA.debugLineNum = 355;BA.debugLine="ProgressDialogShow(\"Modifying Product on TeleWeaver...\")";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Modifying Product on TeleWeaver...");
 BA.debugLineNum = 356;BA.debugLine="modProdOnTW";
Debug.ShouldStop(8);
_modprodontw();
 BA.debugLineNum = 360;BA.debugLine="If picSelected Then 'check if a different image was actually selected from gallery";
Debug.ShouldStop(128);
if (_picselected) { 
 BA.debugLineNum = 361;BA.debugLine="Insert_Image";
Debug.ShouldStop(256);
_insert_image();
 BA.debugLineNum = 362;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE product SET Name=?, Price=?, Size=?, Type=?, Description=?, Picture=? WHERE ProductID=\" & theProdID , Array As Object(txtEDITName.Text,txtEDITprice.Text,txtEditSize.Text, spEDITtype.SelectedItem,txtEDITDesc.Text, Buff))";
Debug.ShouldStop(512);
mostCurrent._main._asql.ExecNonQuery2("UPDATE product SET Name=?, Price=?, Size=?, Type=?, Description=?, Picture=? WHERE ProductID="+BA.NumberToString(_theprodid),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._txteditname.getText()),(Object)(mostCurrent._txteditprice.getText()),(Object)(mostCurrent._txteditsize.getText()),(Object)(mostCurrent._spedittype.getSelectedItem()),(Object)(mostCurrent._txteditdesc.getText()),(Object)(_buff)}));
 }else {
 BA.debugLineNum = 364;BA.debugLine="Main.aSQL.ExecNonQuery(\"UPDATE product SET Name = '\" & txtEDITName.Text & \"' , Price = \" & txtEDITprice.Text &  \", Description = '\" & txtEDITDesc.Text & \"' , Size = '\" & txtEditSize.Text & \"' , Type = '\" & spEDITtype.SelectedItem & \"' WHERE ProductID = \" & theProdID)";
Debug.ShouldStop(2048);
mostCurrent._main._asql.ExecNonQuery("UPDATE product SET Name = '"+mostCurrent._txteditname.getText()+"' , Price = "+mostCurrent._txteditprice.getText()+", Description = '"+mostCurrent._txteditdesc.getText()+"' , Size = '"+mostCurrent._txteditsize.getText()+"' , Type = '"+mostCurrent._spedittype.getSelectedItem()+"' WHERE ProductID = "+BA.NumberToString(_theprodid));
 };
 BA.debugLineNum = 368;BA.debugLine="getProducts  ' refresh page";
Debug.ShouldStop(32768);
_getproducts();
 BA.debugLineNum = 371;BA.debugLine="pnlEdit_Products.Visible = False";
Debug.ShouldStop(262144);
mostCurrent._pnledit_products.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 372;BA.debugLine="picSelected = False";
Debug.ShouldStop(524288);
_picselected = anywheresoftware.b4a.keywords.Common.False;
 }}};
 BA.debugLineNum = 376;BA.debugLine="End Sub";
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
public static String  _cam_click() throws Exception{
		Debug.PushSubsStack("Cam_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 BA.debugLineNum = 631;BA.debugLine="Sub Cam_Click";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 633;BA.debugLine="If  myMsgBox.ButtonSelected = \"yes\" Then";
Debug.ShouldStop(16777216);
if ((mostCurrent._mymsgbox._buttonselected).equals("yes")) { 
 BA.debugLineNum = 635;BA.debugLine="Dim i As Intent";
Debug.ShouldStop(67108864);
_i = new anywheresoftware.b4a.objects.IntentWrapper();Debug.locals.put("i", _i);
 BA.debugLineNum = 636;BA.debugLine="i.Initialize(\"android.media.action.IMAGE_CAPTURE\", \"\")";
Debug.ShouldStop(134217728);
_i.Initialize("android.media.action.IMAGE_CAPTURE","");
 BA.debugLineNum = 638;BA.debugLine="StartActivity(i)";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_i.getObject()));
 }else 
{ BA.debugLineNum = 639;BA.debugLine="Else If myMsgBox.ButtonSelected = \"no\" Then";
Debug.ShouldStop(1073741824);
if ((mostCurrent._mymsgbox._buttonselected).equals("no")) { 
 BA.debugLineNum = 641;BA.debugLine="CC.show(\"image/*\", \"Choose a Picture\")";
Debug.ShouldStop(1);
_cc.Show(processBA,"image/*","Choose a Picture");
 }};
 BA.debugLineNum = 648;BA.debugLine="End Sub";
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
public static String  _changelanguage() throws Exception{
		Debug.PushSubsStack("changeLanguage (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 93;BA.debugLine="Sub changeLanguage";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 94;BA.debugLine="If Main.globlang = \"English\" Then";
Debug.ShouldStop(536870912);
if ((mostCurrent._main._globlang).equals("English")) { 
 BA.debugLineNum = 95;BA.debugLine="Label1.text = \"Edit Product Information\"";
Debug.ShouldStop(1073741824);
mostCurrent._label1.setText((Object)("Edit Product Information"));
 BA.debugLineNum = 96;BA.debugLine="lblEditProdName.text = \"Product name\"";
Debug.ShouldStop(-2147483648);
mostCurrent._lbleditprodname.setText((Object)("Product name"));
 BA.debugLineNum = 97;BA.debugLine="txtEDITName.hint = \"Edit name here\"";
Debug.ShouldStop(1);
mostCurrent._txteditname.setHint("Edit name here");
 BA.debugLineNum = 98;BA.debugLine="lblEditProdPrice.text = \"Product price\"";
Debug.ShouldStop(2);
mostCurrent._lbleditprodprice.setText((Object)("Product price"));
 BA.debugLineNum = 99;BA.debugLine="txtEDITprice.Hint = \"Edit price here\"";
Debug.ShouldStop(4);
mostCurrent._txteditprice.setHint("Edit price here");
 BA.debugLineNum = 100;BA.debugLine="lblEditProdType.text = \"Product type\"";
Debug.ShouldStop(8);
mostCurrent._lbleditprodtype.setText((Object)("Product type"));
 BA.debugLineNum = 101;BA.debugLine="spEDITtype.Prompt = \"Select product type\"";
Debug.ShouldStop(16);
mostCurrent._spedittype.setPrompt("Select product type");
 BA.debugLineNum = 102;BA.debugLine="lblEditProdSize.text = \"Product dimensions\"";
Debug.ShouldStop(32);
mostCurrent._lbleditprodsize.setText((Object)("Product dimensions"));
 BA.debugLineNum = 103;BA.debugLine="txtEditSize.Hint = \"Edit dimensions here\"";
Debug.ShouldStop(64);
mostCurrent._txteditsize.setHint("Edit dimensions here");
 BA.debugLineNum = 104;BA.debugLine="lblEditProdDes.text = \"Product description\"";
Debug.ShouldStop(128);
mostCurrent._lbleditproddes.setText((Object)("Product description"));
 BA.debugLineNum = 105;BA.debugLine="txtEDITDesc.Hint = \"Edit description here\"";
Debug.ShouldStop(256);
mostCurrent._txteditdesc.setHint("Edit description here");
 BA.debugLineNum = 106;BA.debugLine="lblUpdate.text = \"Save\"";
Debug.ShouldStop(512);
mostCurrent._lblupdate.setText((Object)("Save"));
 BA.debugLineNum = 107;BA.debugLine="lblCancel.text = \"Cancel\"";
Debug.ShouldStop(1024);
mostCurrent._lblcancel.setText((Object)("Cancel"));
 BA.debugLineNum = 109;BA.debugLine="lblpricenumber.Text = \"Product Price should be a number\"";
Debug.ShouldStop(4096);
mostCurrent._lblpricenumber.setText((Object)("Product Price should be a number"));
 BA.debugLineNum = 110;BA.debugLine="lblemptyprice.Text = \"Please enter a Product Price\"";
Debug.ShouldStop(8192);
mostCurrent._lblemptyprice.setText((Object)("Please enter a Product Price"));
 BA.debugLineNum = 111;BA.debugLine="lblemptyprod.Text = \"Please enter a Product Name\"";
Debug.ShouldStop(16384);
mostCurrent._lblemptyprod.setText((Object)("Please enter a Product Name"));
 BA.debugLineNum = 112;BA.debugLine="lblWrongSize.Text = \"Product size should be a number\"";
Debug.ShouldStop(32768);
mostCurrent._lblwrongsize.setText((Object)("Product size should be a number"));
 }else 
{ BA.debugLineNum = 114;BA.debugLine="Else If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(131072);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 115;BA.debugLine="Label1.text = \"Hlela umveliso linkcukacha\"";
Debug.ShouldStop(262144);
mostCurrent._label1.setText((Object)("Hlela umveliso linkcukacha"));
 BA.debugLineNum = 116;BA.debugLine="lblEditProdName.text = \"Imveliso igama\"";
Debug.ShouldStop(524288);
mostCurrent._lbleditprodname.setText((Object)("Imveliso igama"));
 BA.debugLineNum = 117;BA.debugLine="txtEDITName.hint = \"Hlela igama apha\"";
Debug.ShouldStop(1048576);
mostCurrent._txteditname.setHint("Hlela igama apha");
 BA.debugLineNum = 118;BA.debugLine="lblEditProdPrice.text = \"Imveliso ixabiso\"";
Debug.ShouldStop(2097152);
mostCurrent._lbleditprodprice.setText((Object)("Imveliso ixabiso"));
 BA.debugLineNum = 119;BA.debugLine="txtEDITprice.Hint = \"Hlela ixabiso apha\"";
Debug.ShouldStop(4194304);
mostCurrent._txteditprice.setHint("Hlela ixabiso apha");
 BA.debugLineNum = 120;BA.debugLine="lblEditProdType.text = \"Umhlobo wemveliso\"";
Debug.ShouldStop(8388608);
mostCurrent._lbleditprodtype.setText((Object)("Umhlobo wemveliso"));
 BA.debugLineNum = 121;BA.debugLine="spEDITtype.Prompt = \"Khetha umhlobo wemveliso\"";
Debug.ShouldStop(16777216);
mostCurrent._spedittype.setPrompt("Khetha umhlobo wemveliso");
 BA.debugLineNum = 122;BA.debugLine="lblEditProdSize.text = \"Umlinganiselo wobude\"";
Debug.ShouldStop(33554432);
mostCurrent._lbleditprodsize.setText((Object)("Umlinganiselo wobude"));
 BA.debugLineNum = 123;BA.debugLine="txtEditSize.Hint = \"Hlela umlinganiselo wobude\"";
Debug.ShouldStop(67108864);
mostCurrent._txteditsize.setHint("Hlela umlinganiselo wobude");
 BA.debugLineNum = 124;BA.debugLine="lblEditProdDes.text = \"Cacisa umveliso\"";
Debug.ShouldStop(134217728);
mostCurrent._lbleditproddes.setText((Object)("Cacisa umveliso"));
 BA.debugLineNum = 125;BA.debugLine="txtEDITDesc.Hint = \"Hlela cacisa umveliso\"";
Debug.ShouldStop(268435456);
mostCurrent._txteditdesc.setHint("Hlela cacisa umveliso");
 BA.debugLineNum = 126;BA.debugLine="lblUpdate.text = \"Gcina\"";
Debug.ShouldStop(536870912);
mostCurrent._lblupdate.setText((Object)("Gcina"));
 BA.debugLineNum = 127;BA.debugLine="lblCancel.text = \"Cima\"";
Debug.ShouldStop(1073741824);
mostCurrent._lblcancel.setText((Object)("Cima"));
 }};
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
public static String  _chooser2_result(boolean _success,String _dir,String _filename) throws Exception{
		Debug.PushSubsStack("chooser2_Result (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Success", _success);
Debug.locals.put("Dir", _dir);
Debug.locals.put("FileName", _filename);
 BA.debugLineNum = 536;BA.debugLine="Sub chooser2_Result (Success As Boolean, Dir As String, FileName As String)";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 538;BA.debugLine="If Success Then";
Debug.ShouldStop(33554432);
if (_success) { 
 BA.debugLineNum = 539;BA.debugLine="ImagevwEDITProdPic.Bitmap = LoadBitmapSample  (Dir, FileName,ImagevwEDITProdPic.Width,ImagevwEDITProdPic.Height)";
Debug.ShouldStop(67108864);
mostCurrent._imagevweditprodpic.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(_dir,_filename,mostCurrent._imagevweditprodpic.getWidth(),mostCurrent._imagevweditprodpic.getHeight()).getObject()));
 BA.debugLineNum = 540;BA.debugLine="picSelected = Success";
Debug.ShouldStop(134217728);
_picselected = _success;
 BA.debugLineNum = 541;BA.debugLine="Dir2 = Dir";
Debug.ShouldStop(268435456);
mostCurrent._dir2 = _dir;
 BA.debugLineNum = 542;BA.debugLine="FileName2 = FileName";
Debug.ShouldStop(536870912);
mostCurrent._filename2 = _filename;
 };
 BA.debugLineNum = 546;BA.debugLine="End Sub";
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
public static String  _del_click() throws Exception{
		Debug.PushSubsStack("Del_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
b4a.sysdev.ctoast _myt = null;
 BA.debugLineNum = 684;BA.debugLine="Sub Del_Click";
Debug.ShouldStop(2048);
 BA.debugLineNum = 686;BA.debugLine="Dim MyT As CToast";
Debug.ShouldStop(8192);
_myt = new b4a.sysdev.ctoast();Debug.locals.put("MyT", _myt);
 BA.debugLineNum = 687;BA.debugLine="MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)";
Debug.ShouldStop(16384);
_myt._initialize(mostCurrent.activityBA,mostCurrent._activity,viewproduct.getObject(),mostCurrent._activity.getHeight(),mostCurrent._activity.getWidth());
 BA.debugLineNum = 688;BA.debugLine="If myMsgBox.ButtonSelected = \"yes\" Then";
Debug.ShouldStop(32768);
if ((mostCurrent._mymsgbox._buttonselected).equals("yes")) { 
 BA.debugLineNum = 691;BA.debugLine="RemProdOnTW";
Debug.ShouldStop(262144);
_remprodontw();
 };
 BA.debugLineNum = 695;BA.debugLine="getProducts  ' refresh page";
Debug.ShouldStop(4194304);
_getproducts();
 BA.debugLineNum = 697;BA.debugLine="End Sub";
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
public static String  _getimage() throws Exception{
		Debug.PushSubsStack("getImage (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _inputstrea1 = null;
 BA.debugLineNum = 500;BA.debugLine="Sub getImage";
Debug.ShouldStop(524288);
 BA.debugLineNum = 502;BA.debugLine="Dim InputStrea1 As InputStream";
Debug.ShouldStop(2097152);
_inputstrea1 = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("InputStrea1", _inputstrea1);
 BA.debugLineNum = 503;BA.debugLine="Buff = Null";
Debug.ShouldStop(4194304);
_buff = (byte[])(anywheresoftware.b4a.keywords.Common.Null);
 BA.debugLineNum = 504;BA.debugLine="If Main.cur.GetString(\"PicExist\") = \"yes\" Then";
Debug.ShouldStop(8388608);
if ((mostCurrent._main._cur.GetString("PicExist")).equals("yes")) { 
 BA.debugLineNum = 505;BA.debugLine="Buff = Main.cur.GetBlob(\"Picture\")";
Debug.ShouldStop(16777216);
_buff = mostCurrent._main._cur.GetBlob("Picture");
 BA.debugLineNum = 506;BA.debugLine="InputStrea1.InitializeFromBytesArray(Buff, 0, Buff.Length)";
Debug.ShouldStop(33554432);
_inputstrea1.InitializeFromBytesArray(_buff,(int)(0),_buff.length);
 BA.debugLineNum = 508;BA.debugLine="Bitt.Initialize2(InputStrea1)";
Debug.ShouldStop(134217728);
mostCurrent._bitt.Initialize2((java.io.InputStream)(_inputstrea1.getObject()));
 BA.debugLineNum = 509;BA.debugLine="InputStrea1.Close";
Debug.ShouldStop(268435456);
_inputstrea1.Close();
 BA.debugLineNum = 511;BA.debugLine="ImageView1.SetBackgroundImage(Bitt)";
Debug.ShouldStop(1073741824);
mostCurrent._imageview1.SetBackgroundImage((android.graphics.Bitmap)(mostCurrent._bitt.getObject()));
 }else {
 BA.debugLineNum = 513;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample  (File.DirAssets, \"empty_gallery.png\",ImageView1.Width,ImageView1.Height)";
Debug.ShouldStop(1);
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"empty_gallery.png",mostCurrent._imageview1.getWidth(),mostCurrent._imageview1.getHeight()).getObject()));
 };
 BA.debugLineNum = 515;BA.debugLine="End Sub";
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
public static String  _getimage_foredit() throws Exception{
		Debug.PushSubsStack("getImage_forEdit (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _inputstrea1 = null;
 BA.debugLineNum = 518;BA.debugLine="Sub getImage_forEdit";
Debug.ShouldStop(32);
 BA.debugLineNum = 520;BA.debugLine="Dim InputStrea1 As InputStream";
Debug.ShouldStop(128);
_inputstrea1 = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("InputStrea1", _inputstrea1);
 BA.debugLineNum = 521;BA.debugLine="Buff = Null";
Debug.ShouldStop(256);
_buff = (byte[])(anywheresoftware.b4a.keywords.Common.Null);
 BA.debugLineNum = 523;BA.debugLine="If Main.cur.GetString(\"PicExist\") = \"yes\" Then";
Debug.ShouldStop(1024);
if ((mostCurrent._main._cur.GetString("PicExist")).equals("yes")) { 
 BA.debugLineNum = 524;BA.debugLine="Buff = Main.cur.GetBlob(\"Picture\")";
Debug.ShouldStop(2048);
_buff = mostCurrent._main._cur.GetBlob("Picture");
 BA.debugLineNum = 525;BA.debugLine="InputStrea1.InitializeFromBytesArray(Buff, 0, Buff.Length)";
Debug.ShouldStop(4096);
_inputstrea1.InitializeFromBytesArray(_buff,(int)(0),_buff.length);
 BA.debugLineNum = 526;BA.debugLine="Bitt.Initialize2(InputStrea1)";
Debug.ShouldStop(8192);
mostCurrent._bitt.Initialize2((java.io.InputStream)(_inputstrea1.getObject()));
 BA.debugLineNum = 527;BA.debugLine="InputStrea1.Close";
Debug.ShouldStop(16384);
_inputstrea1.Close();
 BA.debugLineNum = 528;BA.debugLine="ImagevwEDITProdPic.SetBackgroundImage(Bitt)";
Debug.ShouldStop(32768);
mostCurrent._imagevweditprodpic.SetBackgroundImage((android.graphics.Bitmap)(mostCurrent._bitt.getObject()));
 }else {
 BA.debugLineNum = 531;BA.debugLine="ImagevwEDITProdPic.Bitmap = LoadBitmapSample  (File.DirAssets, \"empty_gallery.png\",ImagevwEDITProdPic.Width,ImagevwEDITProdPic.Height)";
Debug.ShouldStop(262144);
mostCurrent._imagevweditprodpic.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"empty_gallery.png",mostCurrent._imagevweditprodpic.getWidth(),mostCurrent._imagevweditprodpic.getHeight()).getObject()));
 };
 BA.debugLineNum = 534;BA.debugLine="End Sub";
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
public static String  _getproducts() throws Exception{
		Debug.PushSubsStack("getProducts (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.PanelWrapper _panel0 = null;
int _paneltop = 0;
int _panelheight = 0;
int _alabelel2top = 0;
anywheresoftware.b4a.objects.LabelWrapper _lbltitle = null;
int _fisrttime = 0;
int _totintable = 0;
anywheresoftware.b4a.objects.ImageViewWrapper _imageviewlogo = null;
int _i = 0;
int _id = 0;
anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
anywheresoftware.b4a.objects.LabelWrapper _alab = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imageview3 = null;
anywheresoftware.b4a.objects.LabelWrapper _alabel = null;
 BA.debugLineNum = 131;BA.debugLine="Public Sub getProducts";
Debug.ShouldStop(4);
 BA.debugLineNum = 133;BA.debugLine="Dim Panel0 As Panel";
Debug.ShouldStop(16);
_panel0 = new anywheresoftware.b4a.objects.PanelWrapper();Debug.locals.put("Panel0", _panel0);
 BA.debugLineNum = 134;BA.debugLine="Dim PanelTop, PanelHeight, aLabelel2Top As Int";
Debug.ShouldStop(32);
_paneltop = 0;Debug.locals.put("PanelTop", _paneltop);
_panelheight = 0;Debug.locals.put("PanelHeight", _panelheight);
_alabelel2top = 0;Debug.locals.put("aLabelel2Top", _alabelel2top);
 BA.debugLineNum = 135;BA.debugLine="Dim lblTitle As Label";
Debug.ShouldStop(64);
_lbltitle = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("lblTitle", _lbltitle);
 BA.debugLineNum = 136;BA.debugLine="Dim fisrtTime, totInTable As Int";
Debug.ShouldStop(128);
_fisrttime = 0;Debug.locals.put("fisrtTime", _fisrttime);
_totintable = 0;Debug.locals.put("totInTable", _totintable);
 BA.debugLineNum = 137;BA.debugLine="totInTable = 0";
Debug.ShouldStop(256);
_totintable = (int)(0);Debug.locals.put("totInTable", _totintable);
 BA.debugLineNum = 138;BA.debugLine="Activity.Title = \"View Products Details\"";
Debug.ShouldStop(512);
mostCurrent._activity.setTitle((Object)("View Products Details"));
 BA.debugLineNum = 139;BA.debugLine="Activity.Color = Colors.RGB(255, 250, 240)";
Debug.ShouldStop(1024);
mostCurrent._activity.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int)(255),(int)(250),(int)(240)));
 BA.debugLineNum = 141;BA.debugLine="Dim ImageViewLogo As ImageView";
Debug.ShouldStop(4096);
_imageviewlogo = new anywheresoftware.b4a.objects.ImageViewWrapper();Debug.locals.put("ImageViewLogo", _imageviewlogo);
 BA.debugLineNum = 142;BA.debugLine="ImageViewLogo.Initialize(\"Im\")";
Debug.ShouldStop(8192);
_imageviewlogo.Initialize(mostCurrent.activityBA,"Im");
 BA.debugLineNum = 143;BA.debugLine="ImageViewLogo.Bitmap = LoadBitmapSample  (File.DirAssets, \"sellmycraft.png\",45dip,45dip)";
Debug.ShouldStop(16384);
_imageviewlogo.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sellmycraft.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(45)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(45))).getObject()));
 BA.debugLineNum = 144;BA.debugLine="Activity.AddView(ImageViewLogo,0dip,0dip,90dip,80dip)";
Debug.ShouldStop(32768);
mostCurrent._activity.AddView((android.view.View)(_imageviewlogo.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(80)));
 BA.debugLineNum = 148;BA.debugLine="Main.cur = Main.aSQL.ExecQuery2(\"SELECT * FROM product WHERE ArtistID_fk = ?\", Array As String(Main.m.Get(\"id\")))";
Debug.ShouldStop(524288);
mostCurrent._main._cur.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery2("SELECT * FROM product WHERE ArtistID_fk = ?",new String[]{String.valueOf(mostCurrent._main._m.Get((Object)("id")))})));
 BA.debugLineNum = 152;BA.debugLine="PanelTop = 5dip";
Debug.ShouldStop(8388608);
_paneltop = anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(5));Debug.locals.put("PanelTop", _paneltop);
 BA.debugLineNum = 153;BA.debugLine="Panel0 = scvMain.Panel";
Debug.ShouldStop(16777216);
_panel0 = mostCurrent._scvmain.getPanel();Debug.locals.put("Panel0", _panel0);
 BA.debugLineNum = 154;BA.debugLine="Panel0.Color = Colors.black";
Debug.ShouldStop(33554432);
_panel0.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 156;BA.debugLine="PanelHeight = 140dip";
Debug.ShouldStop(134217728);
_panelheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(140));Debug.locals.put("PanelHeight", _panelheight);
 BA.debugLineNum = 158;BA.debugLine="For i = 0 To Main.cur.RowCount - 1";
Debug.ShouldStop(536870912);
{
final double step123 = 1;
final double limit123 = (int)(mostCurrent._main._cur.getRowCount()-1);
for (_i = (int)(0); (step123 > 0 && _i <= limit123) || (step123 < 0 && _i >= limit123); _i += step123) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 159;BA.debugLine="Main.cur.Position = i";
Debug.ShouldStop(1073741824);
mostCurrent._main._cur.setPosition(_i);
 BA.debugLineNum = 160;BA.debugLine="Dim ID As Int";
Debug.ShouldStop(-2147483648);
_id = 0;Debug.locals.put("ID", _id);
 BA.debugLineNum = 161;BA.debugLine="totInTable = totInTable + 1";
Debug.ShouldStop(1);
_totintable = (int)(_totintable+1);Debug.locals.put("totInTable", _totintable);
 BA.debugLineNum = 163;BA.debugLine="Dim Panel1 As Panel";
Debug.ShouldStop(4);
_panel1 = new anywheresoftware.b4a.objects.PanelWrapper();Debug.locals.put("Panel1", _panel1);
 BA.debugLineNum = 164;BA.debugLine="ID = Main.cur.GetInt(\"ProductID\")";
Debug.ShouldStop(8);
_id = mostCurrent._main._cur.GetInt("ProductID");Debug.locals.put("ID", _id);
 BA.debugLineNum = 165;BA.debugLine="theProdID = ID";
Debug.ShouldStop(16);
_theprodid = _id;
 BA.debugLineNum = 167;BA.debugLine="Panel1.Initialize(\"View\")";
Debug.ShouldStop(64);
_panel1.Initialize(mostCurrent.activityBA,"View");
 BA.debugLineNum = 170;BA.debugLine="Panel1.Tag = ID";
Debug.ShouldStop(512);
_panel1.setTag((Object)(_id));
 BA.debugLineNum = 172;BA.debugLine="Panel0.AddView(Panel1,0, PanelTop,scvMain.Width,PanelHeight)";
Debug.ShouldStop(2048);
_panel0.AddView((android.view.View)(_panel1.getObject()),(int)(0),_paneltop,mostCurrent._scvmain.getWidth(),_panelheight);
 BA.debugLineNum = 173;BA.debugLine="Panel1.Color = Colors.RGB(255, 250, 240)";
Debug.ShouldStop(4096);
_panel1.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int)(255),(int)(250),(int)(240)));
 BA.debugLineNum = 177;BA.debugLine="Dim aLab As Label";
Debug.ShouldStop(65536);
_alab = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("aLab", _alab);
 BA.debugLineNum = 178;BA.debugLine="aLab.Initialize(\"aLab\")";
Debug.ShouldStop(131072);
_alab.Initialize(mostCurrent.activityBA,"aLab");
 BA.debugLineNum = 179;BA.debugLine="Activity.AddView(aLab,92dip,15dip,240dip,40dip)";
Debug.ShouldStop(262144);
mostCurrent._activity.AddView((android.view.View)(_alab.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(92)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(15)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(240)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)));
 BA.debugLineNum = 180;BA.debugLine="aLab.TextColor = Colors.black";
Debug.ShouldStop(524288);
_alab.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 181;BA.debugLine="aLab.TextSize = 23";
Debug.ShouldStop(1048576);
_alab.setTextSize((float)(23));
 BA.debugLineNum = 182;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(2097152);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 184;BA.debugLine="aLab.text = \"Umveliso linkcukacha\"";
Debug.ShouldStop(8388608);
_alab.setText((Object)("Umveliso linkcukacha"));
 }else {
 BA.debugLineNum = 188;BA.debugLine="aLab.text = \"Saved Products \"";
Debug.ShouldStop(134217728);
_alab.setText((Object)("Saved Products "));
 };
 BA.debugLineNum = 192;BA.debugLine="Dim ImageView2 As ImageView";
Debug.ShouldStop(-2147483648);
_imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();Debug.locals.put("ImageView2", _imageview2);
 BA.debugLineNum = 193;BA.debugLine="ImageView2.Initialize(\"ImageView2\")";
Debug.ShouldStop(1);
_imageview2.Initialize(mostCurrent.activityBA,"ImageView2");
 BA.debugLineNum = 194;BA.debugLine="ImageView2.Tag = ID";
Debug.ShouldStop(2);
_imageview2.setTag((Object)(_id));
 BA.debugLineNum = 195;BA.debugLine="ImageView2.Bitmap = LoadBitmapSample(File.DirAssets, \"garbage.png\",40dip,32dip)";
Debug.ShouldStop(4);
_imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"garbage.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(32))).getObject()));
 BA.debugLineNum = 197;BA.debugLine="Panel1.AddView(ImageView2,270dip,7dip,40dip,32dip)";
Debug.ShouldStop(16);
_panel1.AddView((android.view.View)(_imageview2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(270)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(7)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(32)));
 BA.debugLineNum = 199;BA.debugLine="Dim ImageView3 As ImageView";
Debug.ShouldStop(64);
_imageview3 = new anywheresoftware.b4a.objects.ImageViewWrapper();Debug.locals.put("ImageView3", _imageview3);
 BA.debugLineNum = 200;BA.debugLine="ImageView3.Initialize(\"ImageView3\")";
Debug.ShouldStop(128);
_imageview3.Initialize(mostCurrent.activityBA,"ImageView3");
 BA.debugLineNum = 201;BA.debugLine="ImageView3.Tag = ID";
Debug.ShouldStop(256);
_imageview3.setTag((Object)(_id));
 BA.debugLineNum = 202;BA.debugLine="ImageView3.Bitmap = LoadBitmapSample(File.DirAssets, \"edit.png\",40dip,32dip)";
Debug.ShouldStop(512);
_imageview3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"edit.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(32))).getObject()));
 BA.debugLineNum = 204;BA.debugLine="Panel1.AddView(ImageView3,215dip,7dip,40dip,32dip)";
Debug.ShouldStop(2048);
_panel1.AddView((android.view.View)(_imageview3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(215)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(7)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(32)));
 BA.debugLineNum = 209;BA.debugLine="Dim aLabel As Label";
Debug.ShouldStop(65536);
_alabel = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("aLabel", _alabel);
 BA.debugLineNum = 210;BA.debugLine="aLabel.Initialize(\"aLabel\")";
Debug.ShouldStop(131072);
_alabel.Initialize(mostCurrent.activityBA,"aLabel");
 BA.debugLineNum = 211;BA.debugLine="Panel1.AddView(aLabel,90dip,5dip,240dip,40dip)";
Debug.ShouldStop(262144);
_panel1.AddView((android.view.View)(_alabel.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(240)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)));
 BA.debugLineNum = 212;BA.debugLine="aLabel.TextColor = Colors.black";
Debug.ShouldStop(524288);
_alabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 213;BA.debugLine="aLabel.TextSize = 20";
Debug.ShouldStop(1048576);
_alabel.setTextSize((float)(20));
 BA.debugLineNum = 214;BA.debugLine="aLabel.Tag=ID";
Debug.ShouldStop(2097152);
_alabel.setTag((Object)(_id));
 BA.debugLineNum = 215;BA.debugLine="aLabel.text = Main.cur.GetString(\"Name\")";
Debug.ShouldStop(4194304);
_alabel.setText((Object)(mostCurrent._main._cur.GetString("Name")));
 BA.debugLineNum = 218;BA.debugLine="Dim aLabel As Label";
Debug.ShouldStop(33554432);
_alabel = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("aLabel", _alabel);
 BA.debugLineNum = 219;BA.debugLine="aLabel.Initialize(\"aLabel\")";
Debug.ShouldStop(67108864);
_alabel.Initialize(mostCurrent.activityBA,"aLabel");
 BA.debugLineNum = 220;BA.debugLine="Panel1.AddView(aLabel,90dip,40dip,50dip,30dip)";
Debug.ShouldStop(134217728);
_panel1.AddView((android.view.View)(_alabel.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(50)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(30)));
 BA.debugLineNum = 221;BA.debugLine="aLabel.TextColor = Colors.black";
Debug.ShouldStop(268435456);
_alabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 222;BA.debugLine="aLabel.Tag = ID";
Debug.ShouldStop(536870912);
_alabel.setTag((Object)(_id));
 BA.debugLineNum = 223;BA.debugLine="aLabel.Width = 95dip";
Debug.ShouldStop(1073741824);
_alabel.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(95)));
 BA.debugLineNum = 224;BA.debugLine="aLabel.text = Main.cur.GetString(\"Size\") & aLabel.text  & \" (cm)\"";
Debug.ShouldStop(-2147483648);
_alabel.setText((Object)(mostCurrent._main._cur.GetString("Size")+_alabel.getText()+" (cm)"));
 BA.debugLineNum = 227;BA.debugLine="Dim aLabel As Label";
Debug.ShouldStop(4);
_alabel = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("aLabel", _alabel);
 BA.debugLineNum = 228;BA.debugLine="aLabel.Initialize(\"aLabel\")";
Debug.ShouldStop(8);
_alabel.Initialize(mostCurrent.activityBA,"aLabel");
 BA.debugLineNum = 229;BA.debugLine="Panel1.AddView(aLabel,190dip,40dip,50dip,30dip)";
Debug.ShouldStop(16);
_panel1.AddView((android.view.View)(_alabel.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(190)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(50)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(30)));
 BA.debugLineNum = 230;BA.debugLine="aLabel.TextColor = Colors.black";
Debug.ShouldStop(32);
_alabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 231;BA.debugLine="aLabel.Tag = ID";
Debug.ShouldStop(64);
_alabel.setTag((Object)(_id));
 BA.debugLineNum = 232;BA.debugLine="aLabel.Width = 95dip";
Debug.ShouldStop(128);
_alabel.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(95)));
 BA.debugLineNum = 233;BA.debugLine="aLabel.text =aLabel.text  & \"R\" & Main.cur.GetString(\"Price\") & \".00\"";
Debug.ShouldStop(256);
_alabel.setText((Object)(_alabel.getText()+"R"+mostCurrent._main._cur.GetString("Price")+".00"));
 BA.debugLineNum = 236;BA.debugLine="Dim aLabel As Label";
Debug.ShouldStop(2048);
_alabel = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("aLabel", _alabel);
 BA.debugLineNum = 237;BA.debugLine="aLabel.Initialize(\"aLabel\")";
Debug.ShouldStop(4096);
_alabel.Initialize(mostCurrent.activityBA,"aLabel");
 BA.debugLineNum = 238;BA.debugLine="Panel1.AddView(aLabel,90dip,80dip,300dip,150dip)";
Debug.ShouldStop(8192);
_panel1.AddView((android.view.View)(_alabel.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(90)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(150)));
 BA.debugLineNum = 239;BA.debugLine="aLabel.TextColor = Colors.black";
Debug.ShouldStop(16384);
_alabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 240;BA.debugLine="aLabel.Tag = ID";
Debug.ShouldStop(32768);
_alabel.setTag((Object)(_id));
 BA.debugLineNum = 241;BA.debugLine="aLabel.Height = 100dip";
Debug.ShouldStop(65536);
_alabel.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(100)));
 BA.debugLineNum = 242;BA.debugLine="aLabel.Width = 230dip";
Debug.ShouldStop(131072);
_alabel.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(230)));
 BA.debugLineNum = 243;BA.debugLine="aLabel.text = Main.cur.GetString(\"Description\") & ID";
Debug.ShouldStop(262144);
_alabel.setText((Object)(mostCurrent._main._cur.GetString("Description")+BA.NumberToString(_id)));
 BA.debugLineNum = 246;BA.debugLine="Dim aLabel As Label";
Debug.ShouldStop(2097152);
_alabel = new anywheresoftware.b4a.objects.LabelWrapper();Debug.locals.put("aLabel", _alabel);
 BA.debugLineNum = 247;BA.debugLine="aLabel.Initialize(\"aLabel\")";
Debug.ShouldStop(4194304);
_alabel.Initialize(mostCurrent.activityBA,"aLabel");
 BA.debugLineNum = 248;BA.debugLine="Panel1.AddView(aLabel,270dip,40dip,50dip,30dip)";
Debug.ShouldStop(8388608);
_panel1.AddView((android.view.View)(_alabel.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(270)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(50)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(30)));
 BA.debugLineNum = 249;BA.debugLine="aLabel.TextColor = Colors.black";
Debug.ShouldStop(16777216);
_alabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 250;BA.debugLine="aLabel.Tag = ID";
Debug.ShouldStop(33554432);
_alabel.setTag((Object)(_id));
 BA.debugLineNum = 251;BA.debugLine="aLabel.Width = 95dip";
Debug.ShouldStop(67108864);
_alabel.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(95)));
 BA.debugLineNum = 252;BA.debugLine="aLabel.text = Main.cur.GetString(\"Type\")";
Debug.ShouldStop(134217728);
_alabel.setText((Object)(mostCurrent._main._cur.GetString("Type")));
 BA.debugLineNum = 256;BA.debugLine="ImageView1.Initialize(\"ImageView1\")";
Debug.ShouldStop(-2147483648);
mostCurrent._imageview1.Initialize(mostCurrent.activityBA,"ImageView1");
 BA.debugLineNum = 257;BA.debugLine="ImageView1.Tag = ID";
Debug.ShouldStop(1);
mostCurrent._imageview1.setTag((Object)(_id));
 BA.debugLineNum = 258;BA.debugLine="bool_view = True";
Debug.ShouldStop(2);
_bool_view = anywheresoftware.b4a.keywords.Common.True;
 BA.debugLineNum = 259;BA.debugLine="getImage  ' collect the image from DB";
Debug.ShouldStop(4);
_getimage();
 BA.debugLineNum = 261;BA.debugLine="Panel1.AddView(ImageView1,3dip,5dip,80dip,80dip)";
Debug.ShouldStop(16);
_panel1.AddView((android.view.View)(mostCurrent._imageview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(3)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(80)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(80)));
 BA.debugLineNum = 263;BA.debugLine="PanelTop = PanelTop + PanelHeight + 1dip";
Debug.ShouldStop(64);
_paneltop = (int)(_paneltop+_panelheight+anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(1)));Debug.locals.put("PanelTop", _paneltop);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 273;BA.debugLine="Panel0.Height = PanelTop";
Debug.ShouldStop(65536);
_panel0.setHeight(_paneltop);
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim picSelected As Boolean";
_picselected = false;
 //BA.debugLineNum = 18;BA.debugLine="Dim scvMain As ScrollView";
mostCurrent._scvmain = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim pnlEdit_Products As Panel";
mostCurrent._pnledit_products = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim btnEditBack As Button";
mostCurrent._btneditback = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim imageviewOnEdit, imagevTEMP As ImageView";
mostCurrent._imageviewonedit = new anywheresoftware.b4a.objects.ImageViewWrapper();
mostCurrent._imagevtemp = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim pictureAnswer As String";
mostCurrent._pictureanswer = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim txtEDITName As EditText";
mostCurrent._txteditname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim txtEDITprice As EditText";
mostCurrent._txteditprice = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim btnUpdate As Button";
mostCurrent._btnupdate = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim theProdID As Int";
_theprodid = 0;
 //BA.debugLineNum = 27;BA.debugLine="Dim spEDITtype As Spinner";
mostCurrent._spedittype = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim txtSize As EditText";
mostCurrent._txtsize = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim hexaPicture As String";
mostCurrent._hexapicture = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim su As StringUtils";
mostCurrent._su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 31;BA.debugLine="Dim Dir2, FileName2 As String";
mostCurrent._dir2 = "";
mostCurrent._filename2 = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim ImagevwEDITProdPic As ImageView";
mostCurrent._imagevweditprodpic = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim iCounter As Int";
_icounter = 0;
 //BA.debugLineNum = 34;BA.debugLine="Dim Buff() As Byte 'declare an empty byte array";
_buff = new byte[(int)(0)];
;
 //BA.debugLineNum = 35;BA.debugLine="Dim i2 As Int";
_i2 = 0;
 //BA.debugLineNum = 36;BA.debugLine="Dim lblemptyprice As Label";
mostCurrent._lblemptyprice = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim lblemptyprod As Label";
mostCurrent._lblemptyprod = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim lblWrongSize As Label";
mostCurrent._lblwrongsize = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim txtType As EditText";
mostCurrent._txttype = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim lblpricenumber As Label";
mostCurrent._lblpricenumber = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim txtEDITDesc As EditText";
mostCurrent._txteditdesc = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim Bitt As Bitmap";
mostCurrent._bitt = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim bool_view As Boolean";
_bool_view = false;
 //BA.debugLineNum = 44;BA.debugLine="Dim aLabel1 As Label";
mostCurrent._alabel1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim imageUpdate As ImageView";
mostCurrent._imageupdate = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim txtEditSize As EditText";
mostCurrent._txteditsize = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim lblEditProdName As Label";
mostCurrent._lbleditprodname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Dim lblEditProdPrice As Label";
mostCurrent._lbleditprodprice = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim lblEditProdType As Label";
mostCurrent._lbleditprodtype = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim lblEditProdSize As Label";
mostCurrent._lbleditprodsize = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim errorDelOnTW As Boolean";
_errordelontw = false;
 //BA.debugLineNum = 54;BA.debugLine="Dim lblEditProdDes As Label";
mostCurrent._lbleditproddes = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim lblUpdate As Label";
mostCurrent._lblupdate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Dim lblCancel As Label";
mostCurrent._lblcancel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim myMsgBox As CustomMsgBox";
mostCurrent._mymsgbox = new b4a.sysdev.custommsgbox();
 //BA.debugLineNum = 59;BA.debugLine="Dim btnDelIDTag As Int";
_btndelidtag = 0;
 //BA.debugLineNum = 60;BA.debugLine="Dim hc As HttpClient";
mostCurrent._hc = new anywheresoftware.b4a.http.HttpClientWrapper();
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,String _reason,int _statuscode,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseError (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Response", _response);
Debug.locals.put("Reason", _reason);
Debug.locals.put("StatusCode", _statuscode);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 450;BA.debugLine="Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)		'No connection :-(";
Debug.ShouldStop(2);
 BA.debugLineNum = 451;BA.debugLine="Log(\"Error connecting: \" & Reason & \" \" & StatusCode)";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.Log("Error connecting: "+_reason+" "+BA.NumberToString(_statuscode));
 BA.debugLineNum = 452;BA.debugLine="If Response <> Null Then";
Debug.ShouldStop(8);
if (_response!= null) { 
 BA.debugLineNum = 453;BA.debugLine="Log(Response.GetString(\"UTF8\"))";
Debug.ShouldStop(16);
anywheresoftware.b4a.keywords.Common.Log(_response.GetString("UTF8"));
 BA.debugLineNum = 454;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(32);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 455;BA.debugLine="Response.Release";
Debug.ShouldStop(64);
_response.Release();
 };
 BA.debugLineNum = 457;BA.debugLine="End Sub";
Debug.ShouldStop(256);
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
		Debug.PushSubsStack("hc_ResponseSuccess (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
b4a.sysdev.ctoast _myt = null;
String _resultstring = "";
Debug.locals.put("Response", _response);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 426;BA.debugLine="Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)			'We got connection and data !!";
Debug.ShouldStop(512);
 BA.debugLineNum = 427;BA.debugLine="Dim MyT As CToast";
Debug.ShouldStop(1024);
_myt = new b4a.sysdev.ctoast();Debug.locals.put("MyT", _myt);
 BA.debugLineNum = 428;BA.debugLine="MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)";
Debug.ShouldStop(2048);
_myt._initialize(mostCurrent.activityBA,mostCurrent._activity,viewproduct.getObject(),mostCurrent._activity.getHeight(),mostCurrent._activity.getWidth());
 BA.debugLineNum = 429;BA.debugLine="ProgressDialogHide	'Close the waiting message..";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 431;BA.debugLine="Dim resultString As String";
Debug.ShouldStop(16384);
_resultstring = "";Debug.locals.put("resultString", _resultstring);
 BA.debugLineNum = 432;BA.debugLine="resultString = Response.GetString(\"UTF8\")			'This holds the returned data";
Debug.ShouldStop(32768);
_resultstring = _response.GetString("UTF8");Debug.locals.put("resultString", _resultstring);
 BA.debugLineNum = 433;BA.debugLine="If resultString = \"product removal unsuccessful\" Then";
Debug.ShouldStop(65536);
if ((_resultstring).equals("product removal unsuccessful")) { 
 BA.debugLineNum = 435;BA.debugLine="MyT.ToastMessageShow2(\"Sorry could not sync with Teleweaver at this time. Delete will be made when TeleWeaver is available.\",8,60,50, \"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(262144);
_myt._toastmessageshow2("Sorry could not sync with Teleweaver at this time. Delete will be made when TeleWeaver is available.",(int)(8),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 438;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE product SET Status=? WHERE ProductID = \" & theProdID , Array As Object(\"del\")) ' remember it needs to be re-synced. LATERRR";
Debug.ShouldStop(2097152);
mostCurrent._main._asql.ExecNonQuery2("UPDATE product SET Status=? WHERE ProductID = "+BA.NumberToString(_theprodid),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("del")}));
 }else {
 BA.debugLineNum = 441;BA.debugLine="MyT.ToastMessageShow2(\"Product has been successfully deleted!\",5,60,50,\"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(16777216);
_myt._toastmessageshow2("Product has been successfully deleted!",(int)(5),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 442;BA.debugLine="Main.aSQL.ExecNonQuery(\"DELETE FROM product WHERE ProductID = \" & btnDelIDTag)";
Debug.ShouldStop(33554432);
mostCurrent._main._asql.ExecNonQuery("DELETE FROM product WHERE ProductID = "+BA.NumberToString(_btndelidtag));
 };
 BA.debugLineNum = 448;BA.debugLine="End Sub";
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
public static String  _imagecancel_click() throws Exception{
		Debug.PushSubsStack("imageCancel_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 553;BA.debugLine="Sub imageCancel_Click";
Debug.ShouldStop(256);
 BA.debugLineNum = 554;BA.debugLine="pnlEdit_Products.Visible = False";
Debug.ShouldStop(512);
mostCurrent._pnledit_products.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 555;BA.debugLine="aLabel1.TextColor = Colors.white";
Debug.ShouldStop(1024);
mostCurrent._alabel1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 556;BA.debugLine="Return True";
Debug.ShouldStop(2048);
if (true) return String.valueOf(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 557;BA.debugLine="End Sub";
Debug.ShouldStop(4096);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imageupdate_click() throws Exception{
		Debug.PushSubsStack("imageUpdate_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 562;BA.debugLine="Sub imageUpdate_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 563;BA.debugLine="btnUpdate_Click";
Debug.ShouldStop(262144);
_btnupdate_click();
 BA.debugLineNum = 564;BA.debugLine="End Sub";
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
public static String  _imageview2_click() throws Exception{
		Debug.PushSubsStack("ImageView2_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.ImageViewWrapper _send = null;
 BA.debugLineNum = 319;BA.debugLine="Sub ImageView2_Click";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 321;BA.debugLine="Dim Send As ImageView";
Debug.ShouldStop(1);
_send = new anywheresoftware.b4a.objects.ImageViewWrapper();Debug.locals.put("Send", _send);
 BA.debugLineNum = 323;BA.debugLine="Send = Sender";
Debug.ShouldStop(4);
_send.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 BA.debugLineNum = 324;BA.debugLine="btnDelIDTag =Send.Tag";
Debug.ShouldStop(8);
_btndelidtag = (int)(BA.ObjectToNumber(_send.getTag()));
 BA.debugLineNum = 327;BA.debugLine="showDelMsgBox";
Debug.ShouldStop(64);
_showdelmsgbox();
 BA.debugLineNum = 329;BA.debugLine="End Sub";
Debug.ShouldStop(256);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imageview3_click() throws Exception{
		Debug.PushSubsStack("ImageView3_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.ImageViewWrapper _send = null;
int _btnidtag = 0;
 BA.debugLineNum = 569;BA.debugLine="Sub ImageView3_Click";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 571;BA.debugLine="Dim Send As ImageView";
Debug.ShouldStop(67108864);
_send = new anywheresoftware.b4a.objects.ImageViewWrapper();Debug.locals.put("Send", _send);
 BA.debugLineNum = 572;BA.debugLine="Dim btnIDTag As Int";
Debug.ShouldStop(134217728);
_btnidtag = 0;Debug.locals.put("btnIDTag", _btnidtag);
 BA.debugLineNum = 573;BA.debugLine="Send = Sender";
Debug.ShouldStop(268435456);
_send.setObject((android.widget.ImageView)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 BA.debugLineNum = 574;BA.debugLine="btnIDTag =Send.Tag";
Debug.ShouldStop(536870912);
_btnidtag = (int)(BA.ObjectToNumber(_send.getTag()));Debug.locals.put("btnIDTag", _btnidtag);
 BA.debugLineNum = 576;BA.debugLine="theProdID = Send.Tag";
Debug.ShouldStop(-2147483648);
_theprodid = (int)(BA.ObjectToNumber(_send.getTag()));
 BA.debugLineNum = 578;BA.debugLine="Main.cur = Main.asql.ExecQuery(\"SELECT * FROM Product WHERE ProductID = \" & Send.Tag)";
Debug.ShouldStop(2);
mostCurrent._main._cur.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery("SELECT * FROM Product WHERE ProductID = "+String.valueOf(_send.getTag()))));
 BA.debugLineNum = 579;BA.debugLine="Main.cur.Position = 0";
Debug.ShouldStop(4);
mostCurrent._main._cur.setPosition((int)(0));
 BA.debugLineNum = 581;BA.debugLine="txtEDITName.Text = Main.cur.GetString(\"Name\")";
Debug.ShouldStop(16);
mostCurrent._txteditname.setText((Object)(mostCurrent._main._cur.GetString("Name")));
 BA.debugLineNum = 582;BA.debugLine="txtEDITprice.Text = Main.cur.GetString(\"Price\")";
Debug.ShouldStop(32);
mostCurrent._txteditprice.setText((Object)(mostCurrent._main._cur.GetString("Price")));
 BA.debugLineNum = 583;BA.debugLine="txtEditSize.Text = Main.cur.GetString(\"Size\")";
Debug.ShouldStop(64);
mostCurrent._txteditsize.setText((Object)(mostCurrent._main._cur.GetString("Size")));
 BA.debugLineNum = 585;BA.debugLine="txtEDITDesc.Text = Main.cur.GetString(\"Description\")";
Debug.ShouldStop(256);
mostCurrent._txteditdesc.setText((Object)(mostCurrent._main._cur.GetString("Description")));
 BA.debugLineNum = 586;BA.debugLine="getImage_forEdit";
Debug.ShouldStop(512);
_getimage_foredit();
 BA.debugLineNum = 587;BA.debugLine="pnlEdit_Products.Visible = True";
Debug.ShouldStop(1024);
mostCurrent._pnledit_products.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 589;BA.debugLine="End Sub";
Debug.ShouldStop(4096);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _imagevweditprodpic_click() throws Exception{
		Debug.PushSubsStack("ImagevwEDITProdPic_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 548;BA.debugLine="Sub ImagevwEDITProdPic_Click";
Debug.ShouldStop(8);
 BA.debugLineNum = 549;BA.debugLine="showMsgBoxCamera";
Debug.ShouldStop(16);
_showmsgboxcamera();
 BA.debugLineNum = 550;BA.debugLine="End Sub";
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
public static String  _insert_image() throws Exception{
		Debug.PushSubsStack("Insert_Image (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _inputstream1 = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _outputstream1 = null;
 BA.debugLineNum = 484;BA.debugLine="Sub Insert_Image";
Debug.ShouldStop(8);
 BA.debugLineNum = 486;BA.debugLine="Dim InputStream1 As InputStream";
Debug.ShouldStop(32);
_inputstream1 = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("InputStream1", _inputstream1);
 BA.debugLineNum = 487;BA.debugLine="If picSelected Then 'check if a different image was actually selected from gallery";
Debug.ShouldStop(64);
if (_picselected) { 
 BA.debugLineNum = 488;BA.debugLine="InputStream1 = File.OpenInput(Dir2, FileName2)";
Debug.ShouldStop(128);
_inputstream1 = anywheresoftware.b4a.keywords.Common.File.OpenInput(mostCurrent._dir2,mostCurrent._filename2);Debug.locals.put("InputStream1", _inputstream1);
 };
 BA.debugLineNum = 491;BA.debugLine="Dim OutputStream1 As OutputStream";
Debug.ShouldStop(1024);
_outputstream1 = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();Debug.locals.put("OutputStream1", _outputstream1);
 BA.debugLineNum = 492;BA.debugLine="OutputStream1.InitializeToBytesArray(1000)";
Debug.ShouldStop(2048);
_outputstream1.InitializeToBytesArray((int)(1000));
 BA.debugLineNum = 493;BA.debugLine="File.Copy2(InputStream1, OutputStream1)";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_inputstream1.getObject()),(java.io.OutputStream)(_outputstream1.getObject()));
 BA.debugLineNum = 494;BA.debugLine="Buff = OutputStream1.ToBytesArray";
Debug.ShouldStop(8192);
_buff = _outputstream1.ToBytesArray();
 BA.debugLineNum = 497;BA.debugLine="End Sub";
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
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
		Debug.PushSubsStack("JobDone (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
b4a.sysdev.ctoast _myt = null;
Debug.locals.put("Job", _job);
 BA.debugLineNum = 460;BA.debugLine="Sub JobDone (Job As HttpJob)";
Debug.ShouldStop(2048);
 BA.debugLineNum = 461;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 462;BA.debugLine="Dim MyT As CToast";
Debug.ShouldStop(8192);
_myt = new b4a.sysdev.ctoast();Debug.locals.put("MyT", _myt);
 BA.debugLineNum = 463;BA.debugLine="MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)";
Debug.ShouldStop(16384);
_myt._initialize(mostCurrent.activityBA,mostCurrent._activity,viewproduct.getObject(),mostCurrent._activity.getHeight(),mostCurrent._activity.getWidth());
 BA.debugLineNum = 464;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" & Job.Success)";
Debug.ShouldStop(32768);
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._jobname+", Success = "+String.valueOf(_job._success));
 BA.debugLineNum = 465;BA.debugLine="If Job.Success = True Then";
Debug.ShouldStop(65536);
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 BA.debugLineNum = 466;BA.debugLine="Select Job.JobName";
Debug.ShouldStop(131072);
switch (BA.switchObjectToInt(_job._jobname,"ModProd")) {
case 0:
 BA.debugLineNum = 468;BA.debugLine="Log(Job.GetString)";
Debug.ShouldStop(524288);
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 BA.debugLineNum = 469;BA.debugLine="MyT.ToastMessageShow2(\"Product has been successfully synchronized and updated!\",6,60,50,\"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(1048576);
_myt._toastmessageshow2("Product has been successfully synchronized and updated!",(int)(6),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 break;
}
;
 }else {
 BA.debugLineNum = 472;BA.debugLine="Log(\"Error: \" & Job.ErrorMessage)";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.Log("Error: "+_job._errormessage);
 BA.debugLineNum = 473;BA.debugLine="MyT.ToastMessageShow2(\"Sorry could not sync with Teleweaver at this time. Changes were only local.\",5,60,50, \"\", Colors.white, Colors.black,20, True)";
Debug.ShouldStop(16777216);
_myt._toastmessageshow2("Sorry could not sync with Teleweaver at this time. Changes were only local.",(int)(5),(int)(60),(int)(50),"",(long)(anywheresoftware.b4a.keywords.Common.Colors.White),(long)(anywheresoftware.b4a.keywords.Common.Colors.Black),(int)(20),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 475;BA.debugLine="If Job.JobName = \"ModProd\" Then  ' no need to update later for delete";
Debug.ShouldStop(67108864);
if ((_job._jobname).equals("ModProd")) { 
 BA.debugLineNum = 476;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE product SET Status=? WHERE ProductID = \" & theProdID , Array As Object(\"mod\"))  ' remember it needs to be re-synced. LATERRR";
Debug.ShouldStop(134217728);
mostCurrent._main._asql.ExecNonQuery2("UPDATE product SET Status=? WHERE ProductID = "+BA.NumberToString(_theprodid),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)("mod")}));
 };
 };
 BA.debugLineNum = 481;BA.debugLine="Job.Release";
Debug.ShouldStop(1);
_job._release();
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
public static String  _lblcancel_click() throws Exception{
		Debug.PushSubsStack("lblCancel_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 565;BA.debugLine="Sub lblCancel_Click";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 567;BA.debugLine="End Sub";
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
public static String  _modprodontw() throws Exception{
		Debug.PushSubsStack("modProdOnTW (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.samples.httputils2.httpjob _modprodjob = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _modprodurl = "";
anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _jsongenerator = null;
 BA.debugLineNum = 377;BA.debugLine="Sub modProdOnTW";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 379;BA.debugLine="Dim modProdJob As HttpJob";
Debug.ShouldStop(67108864);
_modprodjob = new anywheresoftware.b4a.samples.httputils2.httpjob();Debug.locals.put("modProdJob", _modprodjob);
 BA.debugLineNum = 380;BA.debugLine="Dim m As Map";
Debug.ShouldStop(134217728);
_m = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("m", _m);
 BA.debugLineNum = 381;BA.debugLine="Dim modProdUrl As String";
Debug.ShouldStop(268435456);
_modprodurl = "";Debug.locals.put("modProdUrl", _modprodurl);
 BA.debugLineNum = 382;BA.debugLine="modProdUrl = \"http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/modify-product/\"";
Debug.ShouldStop(536870912);
_modprodurl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/modify-product/";Debug.locals.put("modProdUrl", _modprodurl);
 BA.debugLineNum = 384;BA.debugLine="m.Initialize";
Debug.ShouldStop(-2147483648);
_m.Initialize();
 BA.debugLineNum = 385;BA.debugLine="m.Put(\"id\", theProdID)";
Debug.ShouldStop(1);
_m.Put((Object)("id"),(Object)(_theprodid));
 BA.debugLineNum = 386;BA.debugLine="m.Put(\"profile\", Main.m.Get(\"id\"))";
Debug.ShouldStop(2);
_m.Put((Object)("profile"),mostCurrent._main._m.Get((Object)("id")));
 BA.debugLineNum = 387;BA.debugLine="m.Put(\"name\", txtEDITName.Text)";
Debug.ShouldStop(4);
_m.Put((Object)("name"),(Object)(mostCurrent._txteditname.getText()));
 BA.debugLineNum = 388;BA.debugLine="m.Put(\"dimension\", txtEditSize.Text)";
Debug.ShouldStop(8);
_m.Put((Object)("dimension"),(Object)(mostCurrent._txteditsize.getText()));
 BA.debugLineNum = 389;BA.debugLine="m.Put(\"price\", txtEDITprice.Text)";
Debug.ShouldStop(16);
_m.Put((Object)("price"),(Object)(mostCurrent._txteditprice.getText()));
 BA.debugLineNum = 390;BA.debugLine="m.Put(\"description\", txtEDITDesc.Text)";
Debug.ShouldStop(32);
_m.Put((Object)("description"),(Object)(mostCurrent._txteditdesc.getText()));
 BA.debugLineNum = 391;BA.debugLine="m.Put(\"type\", spEDITtype.SelectedItem)";
Debug.ShouldStop(64);
_m.Put((Object)("type"),(Object)(mostCurrent._spedittype.getSelectedItem()));
 BA.debugLineNum = 392;BA.debugLine="m.Put(\"quantity\", 2)";
Debug.ShouldStop(128);
_m.Put((Object)("quantity"),(Object)(2));
 BA.debugLineNum = 393;BA.debugLine="If picSelected Then";
Debug.ShouldStop(256);
if (_picselected) { 
 BA.debugLineNum = 394;BA.debugLine="retrieveANDconvert     ' retrieves the image from SQLite";
Debug.ShouldStop(512);
_retrieveandconvert();
 BA.debugLineNum = 395;BA.debugLine="m.Put(\"picture\", hexaPicture)";
Debug.ShouldStop(1024);
_m.Put((Object)("picture"),(Object)(mostCurrent._hexapicture));
 };
 BA.debugLineNum = 398;BA.debugLine="Dim JSONGenerator As JSONGenerator";
Debug.ShouldStop(8192);
_jsongenerator = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();Debug.locals.put("JSONGenerator", _jsongenerator);
 BA.debugLineNum = 399;BA.debugLine="JSONGenerator.Initialize(m)";
Debug.ShouldStop(16384);
_jsongenerator.Initialize(_m);
 BA.debugLineNum = 401;BA.debugLine="modProdJob.Initialize(\"ModProd\", Me)";
Debug.ShouldStop(65536);
_modprodjob._initialize(processBA,"ModProd",viewproduct.getObject());
 BA.debugLineNum = 402;BA.debugLine="modProdJob.PostString(modProdUrl, JSONGenerator.ToString())";
Debug.ShouldStop(131072);
_modprodjob._poststring(_modprodurl,_jsongenerator.ToString());
 BA.debugLineNum = 403;BA.debugLine="modProdJob.GetRequest.SetContentType(\"application/json\")";
Debug.ShouldStop(262144);
_modprodjob._getrequest().SetContentType("application/json");
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim CC As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 10;BA.debugLine="Dim Timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _remprodontw() throws Exception{
		Debug.PushSubsStack("RemProdOnTW (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper _req = null;
String _remprodurl = "";
 BA.debugLineNum = 414;BA.debugLine="Sub RemProdOnTW   'Connect to the remote server and get the messages.";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 416;BA.debugLine="Dim req As HttpRequest				'Set up an http request connection";
Debug.ShouldStop(-2147483648);
_req = new anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper();Debug.locals.put("req", _req);
 BA.debugLineNum = 417;BA.debugLine="Dim remProdUrl As String";
Debug.ShouldStop(1);
_remprodurl = "";Debug.locals.put("remProdUrl", _remprodurl);
 BA.debugLineNum = 419;BA.debugLine="remProdUrl = \"http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/remove-product/\" & btnDelIDTag";
Debug.ShouldStop(4);
_remprodurl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/remove-product/"+BA.NumberToString(_btndelidtag);Debug.locals.put("remProdUrl", _remprodurl);
 BA.debugLineNum = 420;BA.debugLine="req.InitializeGet(remProdUrl)	 'Initialize the http get request";
Debug.ShouldStop(8);
_req.InitializeGet(_remprodurl);
 BA.debugLineNum = 421;BA.debugLine="ProgressDialogShow(\"Deleting Product on TeleWeaver...\")";
Debug.ShouldStop(16);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Deleting Product on TeleWeaver...");
 BA.debugLineNum = 422;BA.debugLine="hc.Execute(req, 1)						' And the execute it.";
Debug.ShouldStop(32);
mostCurrent._hc.Execute(processBA,_req,(int)(1));
 BA.debugLineNum = 425;BA.debugLine="End Sub";
Debug.ShouldStop(256);
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
		Debug.PushSubsStack("retrieveANDconvert (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 407;BA.debugLine="Sub retrieveANDconvert";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 409;BA.debugLine="hexaPicture = su.EncodeBase64(Buff)";
Debug.ShouldStop(16777216);
mostCurrent._hexapicture = mostCurrent._su.EncodeBase64(_buff);
 BA.debugLineNum = 412;BA.debugLine="End Sub";
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
public static String  _showdelmsgbox() throws Exception{
		Debug.PushSubsStack("showDelMsgBox (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 650;BA.debugLine="Sub showDelMsgBox";
Debug.ShouldStop(512);
 BA.debugLineNum = 651;BA.debugLine="myMsgBox.Initialize(Activity, Me, \"Del\", \"H\", 2, 95%x, 200dip, LoadBitmap(File.DirAssets, \"warningicon.png\"))";
Debug.ShouldStop(1024);
mostCurrent._mymsgbox._initialize(mostCurrent.activityBA,mostCurrent._activity,viewproduct.getObject(),"Del","H",(int)(2),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(200)),anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"warningicon.png"));
 BA.debugLineNum = 654;BA.debugLine="myMsgBox.Title.textColor = Colors.white";
Debug.ShouldStop(8192);
mostCurrent._mymsgbox._title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 655;BA.debugLine="myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD";
Debug.ShouldStop(16384);
mostCurrent._mymsgbox._title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 BA.debugLineNum = 656;BA.debugLine="myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, \"B.png\"))";
Debug.ShouldStop(32768);
mostCurrent._mymsgbox._panel.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"B.png").getObject()));
 BA.debugLineNum = 658;BA.debugLine="myMsgBox.ShowSeparators(Colors.black, Colors.black)";
Debug.ShouldStop(131072);
mostCurrent._mymsgbox._showseparators(anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 660;BA.debugLine="myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))";
Debug.ShouldStop(524288);
mostCurrent._mymsgbox._showshadow(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int)(80),(int)(8),(int)(180),(int)(206)));
 BA.debugLineNum = 662;BA.debugLine="myMsgBox.YesButtonPanel.Color = Colors.white";
Debug.ShouldStop(2097152);
mostCurrent._mymsgbox._yesbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 663;BA.debugLine="myMsgBox.NoButtonPanel.Color = Colors.white";
Debug.ShouldStop(4194304);
mostCurrent._mymsgbox._nobuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 665;BA.debugLine="myMsgBox.NoButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(16777216);
mostCurrent._mymsgbox._nobuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 666;BA.debugLine="myMsgBox.YesButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(33554432);
mostCurrent._mymsgbox._yesbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 667;BA.debugLine="myMsgBox.Message.TextColor = Colors.white";
Debug.ShouldStop(67108864);
mostCurrent._mymsgbox._message.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 668;BA.debugLine="myMsgBox.Message.TextSize = 16";
Debug.ShouldStop(134217728);
mostCurrent._mymsgbox._message.setTextSize((float)(16));
 BA.debugLineNum = 669;BA.debugLine="myMsgBox.Title.TextSize = 23";
Debug.ShouldStop(268435456);
mostCurrent._mymsgbox._title.setTextSize((float)(23));
 BA.debugLineNum = 671;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(1073741824);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 }else {
 BA.debugLineNum = 678;BA.debugLine="myMsgBox.Title.Text = \"Delete Product\"";
Debug.ShouldStop(32);
mostCurrent._mymsgbox._title.setText((Object)("Delete Product"));
 BA.debugLineNum = 679;BA.debugLine="myMsgBox.ShowMessage(\"Are you sure you wish to remove this product?\",\"C\")";
Debug.ShouldStop(64);
mostCurrent._mymsgbox._showmessage("Are you sure you wish to remove this product?","C");
 };
 BA.debugLineNum = 682;BA.debugLine="End Sub";
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
public static String  _showmsgboxcamera() throws Exception{
		Debug.PushSubsStack("showMsgBoxCamera (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 592;BA.debugLine="Sub showMsgBoxCamera";
Debug.ShouldStop(32768);
 BA.debugLineNum = 594;BA.debugLine="myMsgBox.Initialize(Activity, Me, \"Cam\", \"H\", 3, 95%x, 200dip, LoadBitmap(File.DirAssets, \"cameraMsg.png\"))";
Debug.ShouldStop(131072);
mostCurrent._mymsgbox._initialize(mostCurrent.activityBA,mostCurrent._activity,viewproduct.getObject(),"Cam","H",(int)(3),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(200)),anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cameraMsg.png"));
 BA.debugLineNum = 597;BA.debugLine="myMsgBox.Title.textColor = Colors.white";
Debug.ShouldStop(1048576);
mostCurrent._mymsgbox._title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 598;BA.debugLine="myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD";
Debug.ShouldStop(2097152);
mostCurrent._mymsgbox._title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 BA.debugLineNum = 599;BA.debugLine="myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, \"B.png\"))";
Debug.ShouldStop(4194304);
mostCurrent._mymsgbox._panel.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"B.png").getObject()));
 BA.debugLineNum = 601;BA.debugLine="myMsgBox.ShowSeparators(Colors.black, Colors.black)";
Debug.ShouldStop(16777216);
mostCurrent._mymsgbox._showseparators(anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 602;BA.debugLine="myMsgBox.Message.Height = 10dip";
Debug.ShouldStop(33554432);
mostCurrent._mymsgbox._message.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(10)));
 BA.debugLineNum = 603;BA.debugLine="myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))";
Debug.ShouldStop(67108864);
mostCurrent._mymsgbox._showshadow(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int)(80),(int)(8),(int)(180),(int)(206)));
 BA.debugLineNum = 605;BA.debugLine="myMsgBox.YesButtonPanel.Color = Colors.white";
Debug.ShouldStop(268435456);
mostCurrent._mymsgbox._yesbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 606;BA.debugLine="myMsgBox.NoButtonPanel.Color = Colors.white";
Debug.ShouldStop(536870912);
mostCurrent._mymsgbox._nobuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 607;BA.debugLine="myMsgBox.CancelButtonPanel.Color = Colors.white";
Debug.ShouldStop(1073741824);
mostCurrent._mymsgbox._cancelbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 609;BA.debugLine="myMsgBox.NoButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(1);
mostCurrent._mymsgbox._nobuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 610;BA.debugLine="myMsgBox.CancelButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(2);
mostCurrent._mymsgbox._cancelbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 611;BA.debugLine="myMsgBox.YesButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(4);
mostCurrent._mymsgbox._yesbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 613;BA.debugLine="myMsgBox.Message.TextColor = Colors.white";
Debug.ShouldStop(16);
mostCurrent._mymsgbox._message.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 614;BA.debugLine="myMsgBox.Message.TextSize = 23";
Debug.ShouldStop(32);
mostCurrent._mymsgbox._message.setTextSize((float)(23));
 BA.debugLineNum = 616;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(128);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 }else {
 BA.debugLineNum = 622;BA.debugLine="myMsgBox.NoButtonCaption.Text = \"From Gallery\"";
Debug.ShouldStop(8192);
mostCurrent._mymsgbox._nobuttoncaption.setText((Object)("From Gallery"));
 BA.debugLineNum = 623;BA.debugLine="myMsgBox.YesButtonCaption.Text = \"Take Picture\"";
Debug.ShouldStop(16384);
mostCurrent._mymsgbox._yesbuttoncaption.setText((Object)("Take Picture"));
 BA.debugLineNum = 624;BA.debugLine="myMsgBox.CancelButtonCaption.Text = \"Cancel\"";
Debug.ShouldStop(32768);
mostCurrent._mymsgbox._cancelbuttoncaption.setText((Object)("Cancel"));
 };
 BA.debugLineNum = 626;BA.debugLine="myMsgBox.Title.Text = \"Select Picture\"";
Debug.ShouldStop(131072);
mostCurrent._mymsgbox._title.setText((Object)("Select Picture"));
 BA.debugLineNum = 627;BA.debugLine="myMsgBox.Title.TextSize = 25";
Debug.ShouldStop(262144);
mostCurrent._mymsgbox._title.setTextSize((float)(25));
 BA.debugLineNum = 628;BA.debugLine="myMsgBox.ShowMessage(\"Please select an option\",\"C\")";
Debug.ShouldStop(524288);
mostCurrent._mymsgbox._showmessage("Please select an option","C");
 BA.debugLineNum = 629;BA.debugLine="End Sub";
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
public static String  _spedittype_itemclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("spEDITtype_ItemClick (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 559;BA.debugLine="Sub spEDITtype_ItemClick (Position As Int, Value As Object)";
Debug.ShouldStop(16384);
 BA.debugLineNum = 561;BA.debugLine="End Sub";
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
public static String  _view_click() throws Exception{
		Debug.PushSubsStack("View_Click (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 315;BA.debugLine="Sub View_Click";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 316;BA.debugLine="getProducts";
Debug.ShouldStop(134217728);
_getproducts();
 BA.debugLineNum = 317;BA.debugLine="End Sub";
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
public static String  _view_longclick() throws Exception{
		Debug.PushSubsStack("View_LongClick (viewproduct) ","viewproduct",2,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.ConcreteViewWrapper _send = null;
 BA.debugLineNum = 293;BA.debugLine="Sub View_LongClick";
Debug.ShouldStop(16);
 BA.debugLineNum = 294;BA.debugLine="Dim Send As View";
Debug.ShouldStop(32);
_send = new anywheresoftware.b4a.objects.ConcreteViewWrapper();Debug.locals.put("Send", _send);
 BA.debugLineNum = 295;BA.debugLine="bool_view = False";
Debug.ShouldStop(64);
_bool_view = anywheresoftware.b4a.keywords.Common.False;
 BA.debugLineNum = 296;BA.debugLine="Send = Sender";
Debug.ShouldStop(128);
_send.setObject((android.view.View)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA)));
 BA.debugLineNum = 297;BA.debugLine="theProdID = Send.Tag";
Debug.ShouldStop(256);
_theprodid = (int)(BA.ObjectToNumber(_send.getTag()));
 BA.debugLineNum = 299;BA.debugLine="Main.cur = Main.asql.ExecQuery(\"SELECT * FROM Product WHERE ProductID = \" & Send.Tag)";
Debug.ShouldStop(1024);
mostCurrent._main._cur.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery("SELECT * FROM Product WHERE ProductID = "+String.valueOf(_send.getTag()))));
 BA.debugLineNum = 300;BA.debugLine="Main.cur.Position = 0";
Debug.ShouldStop(2048);
mostCurrent._main._cur.setPosition((int)(0));
 BA.debugLineNum = 302;BA.debugLine="txtEDITName.Text = Main.cur.GetString(\"Name\")";
Debug.ShouldStop(8192);
mostCurrent._txteditname.setText((Object)(mostCurrent._main._cur.GetString("Name")));
 BA.debugLineNum = 303;BA.debugLine="txtEDITprice.Text = Main.cur.GetString(\"Price\")";
Debug.ShouldStop(16384);
mostCurrent._txteditprice.setText((Object)(mostCurrent._main._cur.GetString("Price")));
 BA.debugLineNum = 304;BA.debugLine="txtEditSize.Text = Main.cur.GetString(\"Size\")";
Debug.ShouldStop(32768);
mostCurrent._txteditsize.setText((Object)(mostCurrent._main._cur.GetString("Size")));
 BA.debugLineNum = 306;BA.debugLine="txtEDITDesc.Text = Main.cur.GetString(\"Description\")";
Debug.ShouldStop(131072);
mostCurrent._txteditdesc.setText((Object)(mostCurrent._main._cur.GetString("Description")));
 BA.debugLineNum = 308;BA.debugLine="getImage_forEdit";
Debug.ShouldStop(524288);
_getimage_foredit();
 BA.debugLineNum = 310;BA.debugLine="aLabel1.TextColor = Colors.black";
Debug.ShouldStop(2097152);
mostCurrent._alabel1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 311;BA.debugLine="pnlEdit_Products.Visible = True";
Debug.ShouldStop(4194304);
mostCurrent._pnledit_products.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 314;BA.debugLine="End Sub";
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
		return new Object[] {"Activity",_activity,"CC",_cc,"Timer1",_timer1,"picSelected",_picselected,"scvMain",_scvmain,"pnlEdit_Products",_pnledit_products,"btnEditBack",_btneditback,"imageviewOnEdit",_imageviewonedit,"imagevTEMP",_imagevtemp,"pictureAnswer",_pictureanswer,"txtEDITName",_txteditname,"txtEDITprice",_txteditprice,"btnUpdate",_btnupdate,"theProdID",_theprodid,"spEDITtype",_spedittype,"txtSize",_txtsize,"hexaPicture",_hexapicture,"su",_su,"Dir2",_dir2,"FileName2",_filename2,"ImagevwEDITProdPic",_imagevweditprodpic,"iCounter",_icounter,"Buff",_buff,"i2",_i2,"lblemptyprice",_lblemptyprice,"lblemptyprod",_lblemptyprod,"lblWrongSize",_lblwrongsize,"txtType",_txttype,"lblpricenumber",_lblpricenumber,"txtEDITDesc",_txteditdesc,"Bitt",_bitt,"bool_view",_bool_view,"aLabel1",_alabel1,"imageUpdate",_imageupdate,"txtEditSize",_txteditsize,"ImageView1",_imageview1,"lblEditProdName",_lbleditprodname,"lblEditProdPrice",_lbleditprodprice,"lblEditProdType",_lbleditprodtype,"lblEditProdSize",_lbleditprodsize,"errorDelOnTW",_errordelontw,"lblEditProdDes",_lbleditproddes,"lblUpdate",_lblupdate,"lblCancel",_lblcancel,"Label1",_label1,"myMsgBox",_mymsgbox,"btnDelIDTag",_btndelidtag,"hc",_hc,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
