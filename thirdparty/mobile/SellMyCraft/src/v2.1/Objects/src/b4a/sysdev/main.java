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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.sysdev", "b4a.sysdev.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "b4a.sysdev", "b4a.sysdev.main");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static String _dbfilename = "";
public static anywheresoftware.b4a.sql.SQL _asql = null;
public static anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
public static String _dbtablename = "";
public static String _dbtableproduct = "";
public static String _dbtableartist = "";
public static String _globlang = "";
public static String _globusername = "";
public static String _globpass = "";
public static String _globsurn = "";
public static String _globid = "";
public static String _globname = "";
public static anywheresoftware.b4a.http.HttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.collections.Map _m = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtusername = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpass = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblwrongdetails = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txteditdesc = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglogin = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _splanguage = null;
public static boolean _boolcorrectcredentials = false;
public static boolean _oktoparse = false;
public anywheresoftware.b4a.objects.LabelWrapper _login = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladdtitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluser = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblemptypass = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.editartist _editartist = null;
public b4a.sysdev.help _help = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 50;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(131072);
 BA.debugLineNum = 53;BA.debugLine="Activity.LoadLayout(\"logscreen\")";
Debug.ShouldStop(1048576);
mostCurrent._activity.LoadLayout("logscreen",mostCurrent.activityBA);
 BA.debugLineNum = 54;BA.debugLine="spLanguage.AddAll(Array As String(\"English\",\"isiXhosa\"))";
Debug.ShouldStop(2097152);
mostCurrent._splanguage.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"English","isiXhosa"}));
 BA.debugLineNum = 55;BA.debugLine="If File.Exists(File.DirInternal,\"y.db\") = False Then";
Debug.ShouldStop(4194304);
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"y.db")==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 56;BA.debugLine="File.Copy(File.DirAssets,\"y.db\",File.DirInternal,\"y.db\")";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"y.db",anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"y.db");
 };
 BA.debugLineNum = 58;BA.debugLine="hc.Initialize(\"hc\")";
Debug.ShouldStop(33554432);
Debug.DebugWarningEngine.CheckInitialize(_hc);_hc.Initialize("hc");
 BA.debugLineNum = 59;BA.debugLine="If aSQL.IsInitialized = False Then";
Debug.ShouldStop(67108864);
if (_asql.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 60;BA.debugLine="aSQL.Initialize(File.DirInternal, \"y.db\", False)";
Debug.ShouldStop(134217728);
Debug.DebugWarningEngine.CheckInitialize(_asql);_asql.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"y.db",anywheresoftware.b4a.keywords.Common.False);
 };
 BA.debugLineNum = 64;BA.debugLine="End Sub";
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 70;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(32);
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 66;BA.debugLine="Sub Activity_Resume";
Debug.ShouldStop(2);
 BA.debugLineNum = 68;BA.debugLine="End Sub";
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
public static String  _btneditprofile_click() throws Exception{
		Debug.PushSubsStack("btnEditProfile_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 239;BA.debugLine="Sub btnEditProfile_Click";
Debug.ShouldStop(16384);
 BA.debugLineNum = 240;BA.debugLine="ImageView2_Click";
Debug.ShouldStop(32768);
_imageview2_click();
 BA.debugLineNum = 241;BA.debugLine="End Sub";
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
public static String  _changelanguage() throws Exception{
		Debug.PushSubsStack("changeLanguage (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 74;BA.debugLine="Sub changeLanguage";
Debug.ShouldStop(512);
 BA.debugLineNum = 75;BA.debugLine="If globLang = \"English\" Then";
Debug.ShouldStop(1024);
if ((_globlang).equals("English")) { 
 BA.debugLineNum = 76;BA.debugLine="txtUsername.Hint = \"Enter username\"";
Debug.ShouldStop(2048);
mostCurrent._txtusername.setHint("Enter username");
 BA.debugLineNum = 77;BA.debugLine="txtPass.Hint = \"Enter password\"";
Debug.ShouldStop(4096);
mostCurrent._txtpass.setHint("Enter password");
 BA.debugLineNum = 78;BA.debugLine="login.Text = \"Login\"";
Debug.ShouldStop(8192);
mostCurrent._login.setText((Object)("Login"));
 BA.debugLineNum = 79;BA.debugLine="lblWrongDetails.Text = \"The username or password you entered is incorrect.\"";
Debug.ShouldStop(16384);
mostCurrent._lblwrongdetails.setText((Object)("The username or password you entered is incorrect."));
 BA.debugLineNum = 80;BA.debugLine="Label1.text = \"Language:\"";
Debug.ShouldStop(32768);
mostCurrent._label1.setText((Object)("Language:"));
 BA.debugLineNum = 81;BA.debugLine="lblEmptypass.Text = \"Please enter your password.\"";
Debug.ShouldStop(65536);
mostCurrent._lblemptypass.setText((Object)("Please enter your password."));
 BA.debugLineNum = 82;BA.debugLine="lbluser.Text = \"Please enter your username.\"";
Debug.ShouldStop(131072);
mostCurrent._lbluser.setText((Object)("Please enter your username."));
 }else 
{ BA.debugLineNum = 83;BA.debugLine="Else If globLang = \"isiXhosa\" Then";
Debug.ShouldStop(262144);
if ((_globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 84;BA.debugLine="txtUsername.Hint = \"Faka igama lam\"";
Debug.ShouldStop(524288);
mostCurrent._txtusername.setHint("Faka igama lam");
 BA.debugLineNum = 85;BA.debugLine="txtPass.Hint = \"Faka igama elimfihlo\"";
Debug.ShouldStop(1048576);
mostCurrent._txtpass.setHint("Faka igama elimfihlo");
 BA.debugLineNum = 86;BA.debugLine="login.Text = \"Ngena\"";
Debug.ShouldStop(2097152);
mostCurrent._login.setText((Object)("Ngena"));
 BA.debugLineNum = 87;BA.debugLine="lblWrongDetails.Text = \"Inkcukacha ozikafileyo aziluganga.\"";
Debug.ShouldStop(4194304);
mostCurrent._lblwrongdetails.setText((Object)("Inkcukacha ozikafileyo aziluganga."));
 BA.debugLineNum = 88;BA.debugLine="Label1.Text = \"Ulwimi:\"";
Debug.ShouldStop(8388608);
mostCurrent._label1.setText((Object)("Ulwimi:"));
 BA.debugLineNum = 89;BA.debugLine="lblEmptypass.Text = \"Faka igama elimfihlo.\"";
Debug.ShouldStop(16777216);
mostCurrent._lblemptypass.setText((Object)("Faka igama elimfihlo."));
 BA.debugLineNum = 90;BA.debugLine="lbluser.Text = \"Faka igama lam.\"";
Debug.ShouldStop(33554432);
mostCurrent._lbluser.setText((Object)("Faka igama lam."));
 }};
 BA.debugLineNum = 92;BA.debugLine="End Sub";
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
public static String  _check_creds() throws Exception{
		Debug.PushSubsStack("check_creds (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
int _i = 0;
 BA.debugLineNum = 93;BA.debugLine="Sub check_creds";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 97;BA.debugLine="cur = aSQL.ExecQuery(\"SELECT Name, Surname, ArtistID FROM Artist\")";
Debug.ShouldStop(1);
_cur.setObject((android.database.Cursor)(_asql.ExecQuery("SELECT Name, Surname, ArtistID FROM Artist")));
 BA.debugLineNum = 99;BA.debugLine="For i = 0 To cur.RowCount - 1";
Debug.ShouldStop(4);
{
final double step66 = 1;
final double limit66 = (int)(_cur.getRowCount()-1);
for (_i = (int)(0); (step66 > 0 && _i <= limit66) || (step66 < 0 && _i >= limit66); _i += step66) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 100;BA.debugLine="cur.Position = i";
Debug.ShouldStop(8);
_cur.setPosition(_i);
 BA.debugLineNum = 101;BA.debugLine="If txtUsername.Text.EqualsIgnoreCase(m.Get(\"username\")) AND txtPass.Text.EqualsIgnoreCase(m.Get(\"password\")) Then";
Debug.ShouldStop(16);
if (mostCurrent._txtusername.getText().equalsIgnoreCase(String.valueOf(_m.Get((Object)("username")))) && mostCurrent._txtpass.getText().equalsIgnoreCase(String.valueOf(_m.Get((Object)("password"))))) { 
 BA.debugLineNum = 103;BA.debugLine="aSQL.ExecNonQuery(\"UPDATE Artist SET Name = '\" & m.Get(\"firstName\") & \"' , Surname = '\" & m.Get(\"lastName\") & \"' WHERE ArtistID = \" & m.Get(\"id\"))";
Debug.ShouldStop(64);
_asql.ExecNonQuery("UPDATE Artist SET Name = '"+String.valueOf(_m.Get((Object)("firstName")))+"' , Surname = '"+String.valueOf(_m.Get((Object)("lastName")))+"' WHERE ArtistID = "+String.valueOf(_m.Get((Object)("id"))));
 BA.debugLineNum = 111;BA.debugLine="globID = m.Get(\"id\")";
Debug.ShouldStop(16384);
_globid = String.valueOf(_m.Get((Object)("id")));
 BA.debugLineNum = 113;BA.debugLine="globName = m.Get(\"firstName\")  & \" \" & m.Get(\"lastName\")";
Debug.ShouldStop(65536);
_globname = String.valueOf(_m.Get((Object)("firstName")))+" "+String.valueOf(_m.Get((Object)("lastName")));
 BA.debugLineNum = 117;BA.debugLine="StartActivity(\"menu\")";
Debug.ShouldStop(1048576);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("menu"));
 BA.debugLineNum = 118;BA.debugLine="Activity.Finish";
Debug.ShouldStop(2097152);
mostCurrent._activity.Finish();
 BA.debugLineNum = 119;BA.debugLine="BoolcorrectCredentials = True";
Debug.ShouldStop(4194304);
_boolcorrectcredentials = anywheresoftware.b4a.keywords.Common.True;
 }else {
 BA.debugLineNum = 125;BA.debugLine="lblWrongDetails.Visible = True";
Debug.ShouldStop(268435456);
mostCurrent._lblwrongdetails.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 126;BA.debugLine="txtPass.Text = \"\"";
Debug.ShouldStop(536870912);
mostCurrent._txtpass.setText((Object)(""));
 BA.debugLineNum = 127;BA.debugLine="txtUsername.Text = \"\"";
Debug.ShouldStop(1073741824);
mostCurrent._txtusername.setText((Object)(""));
 BA.debugLineNum = 128;BA.debugLine="txtUsername.RequestFocus";
Debug.ShouldStop(-2147483648);
mostCurrent._txtusername.RequestFocus();
 };
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 134;BA.debugLine="End Sub";
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

public static void initializeProcessGlobals() {
    if (mostCurrent != null && mostCurrent.activityBA != null) {
Debug.StartDebugging(mostCurrent.activityBA, 29277, new int[] {8, 14, 22, 3, 16, 7, 13, 10, 11, 5, 4, 3, 3}, "e6f6a5a7-16db-435c-b06a-2e86b8ed8d09");}

    if (processGlobalsRun == false) {
	    processGlobalsRun = true;
		try {
		        main._process_globals();
menu._process_globals();
viewproduct._process_globals();
details._process_globals();
add._process_globals();
sales._process_globals();
dbutils._process_globals();
editartist._process_globals();
httputils2service._process_globals();
help._process_globals();
anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (menu.mostCurrent != null);
vis = vis | (viewproduct.mostCurrent != null);
vis = vis | (details.mostCurrent != null);
vis = vis | (add.mostCurrent != null);
vis = vis | (sales.mostCurrent != null);
vis = vis | (editartist.mostCurrent != null);
vis = vis | (help.mostCurrent != null);
return vis;}

public static void killProgram() {
    
            if (main.previousOne != null) {
				Activity a = main.previousOne.get();
				if (a != null)
					a.finish();
			}


            if (menu.previousOne != null) {
				Activity a = menu.previousOne.get();
				if (a != null)
					a.finish();
			}


            if (viewproduct.previousOne != null) {
				Activity a = viewproduct.previousOne.get();
				if (a != null)
					a.finish();
			}


            if (details.previousOne != null) {
				Activity a = details.previousOne.get();
				if (a != null)
					a.finish();
			}


            if (add.previousOne != null) {
				Activity a = add.previousOne.get();
				if (a != null)
					a.finish();
			}


            if (sales.previousOne != null) {
				Activity a = sales.previousOne.get();
				if (a != null)
					a.finish();
			}


            if (editartist.previousOne != null) {
				Activity a = editartist.previousOne.get();
				if (a != null)
					a.finish();
			}

BA.applicationContext.stopService(new android.content.Intent(BA.applicationContext, httputils2service.class));

            if (help.previousOne != null) {
				Activity a = help.previousOne.get();
				if (a != null)
					a.finish();
			}

}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 36;BA.debugLine="Dim txtUsername As EditText";
mostCurrent._txtusername = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim txtPass As EditText";
mostCurrent._txtpass = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim lblWrongDetails As Label";
mostCurrent._lblwrongdetails = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim txtEDITDesc As EditText";
mostCurrent._txteditdesc = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim imgLogin As ImageView";
mostCurrent._imglogin = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim spLanguage As Spinner";
mostCurrent._splanguage = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim BoolcorrectCredentials, okToParse As Boolean";
_boolcorrectcredentials = false;
_oktoparse = false;
 //BA.debugLineNum = 43;BA.debugLine="Dim login As Label";
mostCurrent._login = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim lblAddtitle As Label";
mostCurrent._lbladdtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim lbluser As Label";
mostCurrent._lbluser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim lblEmptypass As Label";
mostCurrent._lblemptypass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _hc_responseerror(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,String _reason,int _statuscode,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseError (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Response", _response);
Debug.locals.put("Reason", _reason);
Debug.locals.put("StatusCode", _statuscode);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 225;BA.debugLine="Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)		'No connection :-(";
Debug.ShouldStop(1);
 BA.debugLineNum = 226;BA.debugLine="Log(\"Error connecting: \" & Reason & \" \" & StatusCode)";
Debug.ShouldStop(2);
anywheresoftware.b4a.keywords.Common.Log("Error connecting: "+_reason+" "+BA.NumberToString(_statuscode));
 BA.debugLineNum = 227;BA.debugLine="If Response <> Null Then";
Debug.ShouldStop(4);
if (_response!= null) { 
 BA.debugLineNum = 228;BA.debugLine="Log(Response.GetString(\"UTF8\"))";
Debug.ShouldStop(8);
anywheresoftware.b4a.keywords.Common.Log(_response.GetString("UTF8"));
 BA.debugLineNum = 229;BA.debugLine="ProgressDialogHide";
Debug.ShouldStop(16);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 230;BA.debugLine="Response.Release";
Debug.ShouldStop(32);
_response.Release();
 };
 BA.debugLineNum = 232;BA.debugLine="End Sub";
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
public static String  _hc_responsesuccess(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseSuccess (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
b4a.sysdev.ctoast _myt = null;
String _resultstring = "";
anywheresoftware.b4a.objects.collections.JSONParser _jp = null;
anywheresoftware.b4a.objects.collections.List _alist = null;
Debug.locals.put("Response", _response);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 177;BA.debugLine="Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)			'We got connection and data !!";
Debug.ShouldStop(65536);
 BA.debugLineNum = 178;BA.debugLine="Dim MyT As CToast";
Debug.ShouldStop(131072);
_myt = new b4a.sysdev.ctoast();Debug.locals.put("MyT", _myt);
 BA.debugLineNum = 179;BA.debugLine="MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)";
Debug.ShouldStop(262144);
_myt._initialize(mostCurrent.activityBA,mostCurrent._activity,main.getObject(),mostCurrent._activity.getHeight(),mostCurrent._activity.getWidth());
 BA.debugLineNum = 180;BA.debugLine="ProgressDialogHide	'Close the waiting message..";
Debug.ShouldStop(524288);
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 BA.debugLineNum = 182;BA.debugLine="Dim resultString As String";
Debug.ShouldStop(2097152);
_resultstring = "";Debug.locals.put("resultString", _resultstring);
 BA.debugLineNum = 183;BA.debugLine="resultString = Response.GetString(\"UTF8\")			'This holds the returned data";
Debug.ShouldStop(4194304);
_resultstring = _response.GetString("UTF8");Debug.locals.put("resultString", _resultstring);
 BA.debugLineNum = 185;BA.debugLine="Log(resultString)";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.Log(_resultstring);
 BA.debugLineNum = 187;BA.debugLine="If resultString = Null OR resultString = \"[]\" Then";
Debug.ShouldStop(67108864);
if (_resultstring== null || (_resultstring).equals("[]")) { 
 BA.debugLineNum = 188;BA.debugLine="lblWrongDetails.Visible = True";
Debug.ShouldStop(134217728);
mostCurrent._lblwrongdetails.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 190;BA.debugLine="txtPass.Text = \"\"";
Debug.ShouldStop(536870912);
mostCurrent._txtpass.setText((Object)(""));
 BA.debugLineNum = 191;BA.debugLine="txtUsername.Text = \"\"";
Debug.ShouldStop(1073741824);
mostCurrent._txtusername.setText((Object)(""));
 BA.debugLineNum = 192;BA.debugLine="txtUsername.RequestFocus";
Debug.ShouldStop(-2147483648);
mostCurrent._txtusername.RequestFocus();
 }else 
{ BA.debugLineNum = 194;BA.debugLine="Else If okToParse Then";
Debug.ShouldStop(2);
if (_oktoparse) { 
 BA.debugLineNum = 197;BA.debugLine="Dim Jp As JSONParser";
Debug.ShouldStop(16);
_jp = new anywheresoftware.b4a.objects.collections.JSONParser();Debug.locals.put("Jp", _jp);
 BA.debugLineNum = 198;BA.debugLine="Dim aList As List";
Debug.ShouldStop(32);
_alist = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("aList", _alist);
 BA.debugLineNum = 199;BA.debugLine="Jp.Initialize(resultString)";
Debug.ShouldStop(64);
_jp.Initialize(_resultstring);
 BA.debugLineNum = 200;BA.debugLine="aList = Jp.NextArray";
Debug.ShouldStop(128);
_alist = _jp.NextArray();Debug.locals.put("aList", _alist);
 BA.debugLineNum = 201;BA.debugLine="m = aList.Get(0)";
Debug.ShouldStop(256);
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_alist.Get((int)(0))));
 BA.debugLineNum = 203;BA.debugLine="check_creds";
Debug.ShouldStop(1024);
_check_creds();
 }};
 BA.debugLineNum = 223;BA.debugLine="End Sub";
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
public static String  _imageview2_click() throws Exception{
		Debug.PushSubsStack("ImageView2_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 235;BA.debugLine="Sub ImageView2_Click";
Debug.ShouldStop(1024);
 BA.debugLineNum = 236;BA.debugLine="StartActivity(\"editArtist\")";
Debug.ShouldStop(2048);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("editArtist"));
 BA.debugLineNum = 237;BA.debugLine="Activity.Finish";
Debug.ShouldStop(4096);
mostCurrent._activity.Finish();
 BA.debugLineNum = 238;BA.debugLine="End Sub";
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
public static String  _imglogin_click() throws Exception{
		Debug.PushSubsStack("imgLogin_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 251;BA.debugLine="Sub imgLogin_Click";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 252;BA.debugLine="login_Click";
Debug.ShouldStop(134217728);
_login_click();
 BA.debugLineNum = 253;BA.debugLine="End Sub";
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
public static String  _login_click() throws Exception{
		Debug.PushSubsStack("login_Click (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
String _remprodurl = "";
anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper _req = null;
 BA.debugLineNum = 135;BA.debugLine="Sub login_Click   'Connect to the remote server and get the messages.";
Debug.ShouldStop(64);
 BA.debugLineNum = 137;BA.debugLine="lblWrongDetails.Visible = False";
Debug.ShouldStop(256);
mostCurrent._lblwrongdetails.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 138;BA.debugLine="lbluser.Visible = False";
Debug.ShouldStop(512);
mostCurrent._lbluser.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 139;BA.debugLine="lblEmptypass.Visible = False";
Debug.ShouldStop(1024);
mostCurrent._lblemptypass.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 140;BA.debugLine="lblWrongDetails.Visible = False";
Debug.ShouldStop(2048);
mostCurrent._lblwrongdetails.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 141;BA.debugLine="okToParse = False";
Debug.ShouldStop(4096);
_oktoparse = anywheresoftware.b4a.keywords.Common.False;
 BA.debugLineNum = 142;BA.debugLine="If txtUsername.text <> \"\" Then";
Debug.ShouldStop(8192);
if ((mostCurrent._txtusername.getText()).equals("") == false) { 
 BA.debugLineNum = 143;BA.debugLine="If txtPass.text <> \"\" Then";
Debug.ShouldStop(16384);
if ((mostCurrent._txtpass.getText()).equals("") == false) { 
 BA.debugLineNum = 144;BA.debugLine="okToParse = True";
Debug.ShouldStop(32768);
_oktoparse = anywheresoftware.b4a.keywords.Common.True;
 BA.debugLineNum = 145;BA.debugLine="Dim remProdUrl As String";
Debug.ShouldStop(65536);
_remprodurl = "";Debug.locals.put("remProdUrl", _remprodurl);
 BA.debugLineNum = 146;BA.debugLine="Dim req As HttpRequest				'Set up an http request connection";
Debug.ShouldStop(131072);
_req = new anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper();Debug.locals.put("req", _req);
 BA.debugLineNum = 147;BA.debugLine="remProdUrl = \"http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/get-profile/\" & txtUsername.Text";
Debug.ShouldStop(262144);
_remprodurl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/get-profile/"+mostCurrent._txtusername.getText();Debug.locals.put("remProdUrl", _remprodurl);
 BA.debugLineNum = 148;BA.debugLine="req.InitializeGet(remProdUrl)	 'Initialize the http get request";
Debug.ShouldStop(524288);
_req.InitializeGet(_remprodurl);
 BA.debugLineNum = 149;BA.debugLine="If globLang = \"isiXhosa\" Then";
Debug.ShouldStop(1048576);
if ((_globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 150;BA.debugLine="ProgressDialogShow(\"Authenticating credentials on TeleWeaver...\")";
Debug.ShouldStop(2097152);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Authenticating credentials on TeleWeaver...");
 }else {
 BA.debugLineNum = 152;BA.debugLine="ProgressDialogShow(\"Authenticating credentials on TeleWeaver...\")";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Authenticating credentials on TeleWeaver...");
 };
 BA.debugLineNum = 154;BA.debugLine="hc.Execute(req,1)";
Debug.ShouldStop(33554432);
_hc.Execute(processBA,_req,(int)(1));
 }else {
 BA.debugLineNum = 158;BA.debugLine="lblEmptypass.Visible = True";
Debug.ShouldStop(536870912);
mostCurrent._lblemptypass.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 159;BA.debugLine="txtPass.Text = \"\"";
Debug.ShouldStop(1073741824);
mostCurrent._txtpass.setText((Object)(""));
 BA.debugLineNum = 160;BA.debugLine="txtPass.RequestFocus";
Debug.ShouldStop(-2147483648);
mostCurrent._txtpass.RequestFocus();
 };
 }else {
 BA.debugLineNum = 165;BA.debugLine="lbluser.Visible = True";
Debug.ShouldStop(16);
mostCurrent._lbluser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 166;BA.debugLine="txtUsername.Text = \"\"";
Debug.ShouldStop(32);
mostCurrent._txtusername.setText((Object)(""));
 BA.debugLineNum = 167;BA.debugLine="txtUsername.RequestFocus";
Debug.ShouldStop(64);
mostCurrent._txtusername.RequestFocus();
 };
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim DBFileName As String				: DBFileName = \"y.db\"";
_dbfilename = "";
 //BA.debugLineNum = 18;BA.debugLine="Dim DBFileName As String				: DBFileName = \"y.db\"";
_dbfilename = "y.db";
 //BA.debugLineNum = 21;BA.debugLine="Dim aSQL As SQL";
_asql = new anywheresoftware.b4a.sql.SQL();
 //BA.debugLineNum = 22;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim DBTableName As String				: DBTableName = \"lookupUsrPass\"";
_dbtablename = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim DBTableName As String				: DBTableName = \"lookupUsrPass\"";
_dbtablename = "lookupUsrPass";
 //BA.debugLineNum = 24;BA.debugLine="Dim DBTableProduct As String			: DBTableProduct = \"Product\"";
_dbtableproduct = "";
 //BA.debugLineNum = 24;BA.debugLine="Dim DBTableProduct As String			: DBTableProduct = \"Product\"";
_dbtableproduct = "Product";
 //BA.debugLineNum = 25;BA.debugLine="Dim DBTableArtist As String 			: DBTableArtist = \"Artist\"";
_dbtableartist = "";
 //BA.debugLineNum = 25;BA.debugLine="Dim DBTableArtist As String 			: DBTableArtist = \"Artist\"";
_dbtableartist = "Artist";
 //BA.debugLineNum = 26;BA.debugLine="Dim globLang As String";
_globlang = "";
 //BA.debugLineNum = 27;BA.debugLine="Dim globUsername, globPass, globSurn, globID, globName As String";
_globusername = "";
_globpass = "";
_globsurn = "";
_globid = "";
_globname = "";
 //BA.debugLineNum = 28;BA.debugLine="Dim hc As HttpClient";
_hc = new anywheresoftware.b4a.http.HttpClientWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _splanguage_itemclick(int _position,Object _value) throws Exception{
		Debug.PushSubsStack("spLanguage_ItemClick (main) ","main",0,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Position", _position);
Debug.locals.put("Value", _value);
 BA.debugLineNum = 242;BA.debugLine="Sub spLanguage_ItemClick (Position As Int, Value As Object)";
Debug.ShouldStop(131072);
 BA.debugLineNum = 243;BA.debugLine="If spLanguage.SelectedItem = \"English\" Then";
Debug.ShouldStop(262144);
if ((mostCurrent._splanguage.getSelectedItem()).equals("English")) { 
 BA.debugLineNum = 244;BA.debugLine="globLang = \"English\"";
Debug.ShouldStop(524288);
_globlang = "English";
 }else 
{ BA.debugLineNum = 245;BA.debugLine="Else If spLanguage.SelectedItem = \"isiXhosa\" Then";
Debug.ShouldStop(1048576);
if ((mostCurrent._splanguage.getSelectedItem()).equals("isiXhosa")) { 
 BA.debugLineNum = 246;BA.debugLine="globLang = \"isiXhosa\"";
Debug.ShouldStop(2097152);
_globlang = "isiXhosa";
 }};
 BA.debugLineNum = 248;BA.debugLine="changeLanguage";
Debug.ShouldStop(8388608);
_changelanguage();
 BA.debugLineNum = 250;BA.debugLine="End Sub";
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
		return new Object[] {"Activity",_activity,"DBFileName",_dbfilename,"aSQL",_asql,"cur",_cur,"DBTableName",_dbtablename,"DBTableProduct",_dbtableproduct,"DBTableArtist",_dbtableartist,"globLang",_globlang,"globUsername",_globusername,"globPass",_globpass,"globSurn",_globsurn,"globID",_globid,"globName",_globname,"hc",_hc,"m",_m,"txtUsername",_txtusername,"txtPass",_txtpass,"lblWrongDetails",_lblwrongdetails,"txtEDITDesc",_txteditdesc,"imgLogin",_imglogin,"spLanguage",_splanguage,"BoolcorrectCredentials",_boolcorrectcredentials,"okToParse",_oktoparse,"login",_login,"lblAddtitle",_lbladdtitle,"lbluser",_lbluser,"lblEmptypass",_lblemptypass,"Label1",_label1,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
