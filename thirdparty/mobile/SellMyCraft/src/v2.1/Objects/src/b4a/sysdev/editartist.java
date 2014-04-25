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

public class editartist extends Activity implements B4AActivity{
	public static editartist mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.sysdev", "b4a.sysdev.editartist");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (editartist).");
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
		activityBA = new BA(this, layout, processBA, "b4a.sysdev", "b4a.sysdev.editartist");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (editartist) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (editartist) Resume **");
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
		return editartist.class;
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
        BA.LogInfo("** Activity (editartist) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (editartist) Resume **");
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
public static long _selecteddateinticks = 0L;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public static boolean _artistupdated = false;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtsurn = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtbio = null;
public static String _dir2 = "";
public static String _filename2 = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _imgselected = null;
public static boolean _picselected = false;
public static String _pictureanswer = "";
public anywheresoftware.b4a.objects.EditTextWrapper _txtphone = null;
public static byte[] _buf = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnoname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnosurn = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndob = null;
public anywheresoftware.b4a.objects.LabelWrapper _dob = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagecancel = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagelogo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imagesave = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcancel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldate = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblphone = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsave = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsurname = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper _tbconnection = null;
public b4a.sysdev.custommsgbox _mymsgbox = null;
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.help _help = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
		Debug.PushSubsStack("Activity_Create (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("FirstTime", _firsttime);
 BA.debugLineNum = 47;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
Debug.ShouldStop(16384);
 BA.debugLineNum = 50;BA.debugLine="Activity.LoadLayout(\"editArtist\")";
Debug.ShouldStop(131072);
mostCurrent._activity.LoadLayout("editArtist",mostCurrent.activityBA);
 BA.debugLineNum = 51;BA.debugLine="lblNoName.Visible = False";
Debug.ShouldStop(262144);
mostCurrent._lblnoname.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 52;BA.debugLine="lblNoSurn.Visible = False";
Debug.ShouldStop(524288);
mostCurrent._lblnosurn.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 53;BA.debugLine="cc.Initialize(\"chooser\")";
Debug.ShouldStop(1048576);
Debug.DebugWarningEngine.CheckInitialize(_cc);_cc.Initialize("chooser");
 BA.debugLineNum = 54;BA.debugLine="loadToControls";
Debug.ShouldStop(2097152);
_loadtocontrols();
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
public static String  _activity_pause(boolean _userclosed) throws Exception{
		Debug.PushSubsStack("Activity_Pause (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("UserClosed", _userclosed);
 BA.debugLineNum = 62;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
Debug.ShouldStop(536870912);
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
public static String  _activity_resume() throws Exception{
		Debug.PushSubsStack("Activity_Resume (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 58;BA.debugLine="Sub Activity_Resume";
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
public static String  _btndob_click() throws Exception{
		Debug.PushSubsStack("btnDob_Click (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog _dd = null;
String _ret = "";
 BA.debugLineNum = 148;BA.debugLine="Sub btnDob_Click";
Debug.ShouldStop(524288);
 BA.debugLineNum = 149;BA.debugLine="Dim Dd As DateDialog";
Debug.ShouldStop(1048576);
_dd = new anywheresoftware.b4a.agraham.dialogs.InputDialog.DateDialog();Debug.locals.put("Dd", _dd);
 BA.debugLineNum = 151;BA.debugLine="Dd.DateTicks = SelectedDateInTicks";
Debug.ShouldStop(4194304);
_dd.setDateTicks(_selecteddateinticks);
 BA.debugLineNum = 152;BA.debugLine="ret = Dd.Show(\"Please choose a date\", \"Date-Chooser\", \"OK\", \"\", \"Cancel\", Null)";
Debug.ShouldStop(8388608);
_ret = BA.NumberToString(_dd.Show("Please choose a date","Date-Chooser","OK","","Cancel",mostCurrent.activityBA,(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null)));Debug.locals.put("ret", _ret);
 BA.debugLineNum = 153;BA.debugLine="If ret = DialogResponse.CANCEL Then";
Debug.ShouldStop(16777216);
if ((_ret).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL))) { 
 BA.debugLineNum = 155;BA.debugLine="lblDate.Text = \"\"";
Debug.ShouldStop(67108864);
mostCurrent._lbldate.setText((Object)(""));
 BA.debugLineNum = 156;BA.debugLine="ToastMessageShow(\"cancelled\", True)";
Debug.ShouldStop(134217728);
anywheresoftware.b4a.keywords.Common.ToastMessageShow("cancelled",anywheresoftware.b4a.keywords.Common.True);
 }else {
 BA.debugLineNum = 160;BA.debugLine="lblDate.Text = DateTime.Date(Dd.DateTicks)";
Debug.ShouldStop(-2147483648);
mostCurrent._lbldate.setText((Object)(anywheresoftware.b4a.keywords.Common.DateTime.Date(_dd.getDateTicks())));
 BA.debugLineNum = 162;BA.debugLine="SelectedDateInTicks = Dd.DateTicks";
Debug.ShouldStop(2);
_selecteddateinticks = _dd.getDateTicks();
 };
 BA.debugLineNum = 165;BA.debugLine="End Sub";
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
public static String  _cam_click() throws Exception{
		Debug.PushSubsStack("Cam_Click (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 BA.debugLineNum = 286;BA.debugLine="Sub Cam_Click";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 287;BA.debugLine="If  myMsgBox.ButtonSelected = \"yes\" Then";
Debug.ShouldStop(1073741824);
if ((mostCurrent._mymsgbox._buttonselected).equals("yes")) { 
 BA.debugLineNum = 289;BA.debugLine="Dim i As Intent";
Debug.ShouldStop(1);
_i = new anywheresoftware.b4a.objects.IntentWrapper();Debug.locals.put("i", _i);
 BA.debugLineNum = 290;BA.debugLine="i.Initialize(\"android.media.action.IMAGE_CAPTURE\", \"\")";
Debug.ShouldStop(2);
_i.Initialize("android.media.action.IMAGE_CAPTURE","");
 BA.debugLineNum = 292;BA.debugLine="StartActivity(i)";
Debug.ShouldStop(8);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_i.getObject()));
 }else 
{ BA.debugLineNum = 293;BA.debugLine="Else If myMsgBox.ButtonSelected = \"no\" Then";
Debug.ShouldStop(16);
if ((mostCurrent._mymsgbox._buttonselected).equals("no")) { 
 BA.debugLineNum = 295;BA.debugLine="cc.show(\"image/*\", \"Choose a Picture\")";
Debug.ShouldStop(64);
_cc.Show(processBA,"image/*","Choose a Picture");
 }};
 BA.debugLineNum = 301;BA.debugLine="End Sub";
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
public static String  _changelanguage() throws Exception{
		Debug.PushSubsStack("changeLanguage (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 66;BA.debugLine="Sub changeLanguage";
Debug.ShouldStop(2);
 BA.debugLineNum = 68;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(8);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 BA.debugLineNum = 70;BA.debugLine="lblName.Text = \"Igama\"";
Debug.ShouldStop(32);
mostCurrent._lblname.setText((Object)("Igama"));
 BA.debugLineNum = 71;BA.debugLine="txtName.Hint = \"Faka name\"";
Debug.ShouldStop(64);
mostCurrent._txtname.setHint("Faka name");
 BA.debugLineNum = 72;BA.debugLine="lblSurname.Text = \"Ifani\"";
Debug.ShouldStop(128);
mostCurrent._lblsurname.setText((Object)("Ifani"));
 BA.debugLineNum = 73;BA.debugLine="txtSurn.Hint = \"Faka surname\"";
Debug.ShouldStop(256);
mostCurrent._txtsurn.setHint("Faka surname");
 BA.debugLineNum = 74;BA.debugLine="Dob.Text = \"Umhla lokuzalwa\"";
Debug.ShouldStop(512);
mostCurrent._dob.setText((Object)("Umhla lokuzalwa"));
 BA.debugLineNum = 75;BA.debugLine="btnDob.Text = \"Set Date\"";
Debug.ShouldStop(1024);
mostCurrent._btndob.setText((Object)("Set Date"));
 BA.debugLineNum = 76;BA.debugLine="lblPhone.Text = \"Icingo Le Ndaba\"";
Debug.ShouldStop(2048);
mostCurrent._lblphone.setText((Object)("Icingo Le Ndaba"));
 BA.debugLineNum = 77;BA.debugLine="txtPhone.Hint = \"Faka icingo le ndaba\"";
Debug.ShouldStop(4096);
mostCurrent._txtphone.setHint("Faka icingo le ndaba");
 BA.debugLineNum = 78;BA.debugLine="txtBio.Hint = \"Cacisa imisebenzi yakho\"";
Debug.ShouldStop(8192);
mostCurrent._txtbio.setHint("Cacisa imisebenzi yakho");
 BA.debugLineNum = 80;BA.debugLine="lblSave.Text = \"Gcina\"";
Debug.ShouldStop(32768);
mostCurrent._lblsave.setText((Object)("Gcina"));
 BA.debugLineNum = 81;BA.debugLine="lblCancel.Text = \"Cima\"";
Debug.ShouldStop(65536);
mostCurrent._lblcancel.setText((Object)("Cima"));
 }else {
 BA.debugLineNum = 83;BA.debugLine="lblName.Text = \"Name\"";
Debug.ShouldStop(262144);
mostCurrent._lblname.setText((Object)("Name"));
 BA.debugLineNum = 84;BA.debugLine="txtName.Hint = \"Enter name\"";
Debug.ShouldStop(524288);
mostCurrent._txtname.setHint("Enter name");
 BA.debugLineNum = 85;BA.debugLine="lblSurname.Text = \"Surname\"";
Debug.ShouldStop(1048576);
mostCurrent._lblsurname.setText((Object)("Surname"));
 BA.debugLineNum = 86;BA.debugLine="txtSurn.Hint = \"Enter surname\"";
Debug.ShouldStop(2097152);
mostCurrent._txtsurn.setHint("Enter surname");
 BA.debugLineNum = 87;BA.debugLine="Dob.Text = \"Date of Birth\"";
Debug.ShouldStop(4194304);
mostCurrent._dob.setText((Object)("Date of Birth"));
 BA.debugLineNum = 88;BA.debugLine="btnDob.Text = \"Set Date\"";
Debug.ShouldStop(8388608);
mostCurrent._btndob.setText((Object)("Set Date"));
 BA.debugLineNum = 89;BA.debugLine="lblPhone.Text = \"Phone Number\"";
Debug.ShouldStop(16777216);
mostCurrent._lblphone.setText((Object)("Phone Number"));
 BA.debugLineNum = 90;BA.debugLine="txtPhone.Hint = \"Enter phone number\"";
Debug.ShouldStop(33554432);
mostCurrent._txtphone.setHint("Enter phone number");
 BA.debugLineNum = 91;BA.debugLine="txtBio.Hint = \"Enter your brief biography\"";
Debug.ShouldStop(67108864);
mostCurrent._txtbio.setHint("Enter your brief biography");
 BA.debugLineNum = 93;BA.debugLine="lblSave.Text = \"Save\"";
Debug.ShouldStop(268435456);
mostCurrent._lblsave.setText((Object)("Save"));
 BA.debugLineNum = 94;BA.debugLine="lblCancel.Text = \"Cancel\"";
Debug.ShouldStop(536870912);
mostCurrent._lblcancel.setText((Object)("Cancel"));
 };
 BA.debugLineNum = 97;BA.debugLine="End Sub";
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
public static String  _chooser_result(boolean _success,String _dir,String _filename) throws Exception{
		Debug.PushSubsStack("chooser_Result (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
Debug.locals.put("Success", _success);
Debug.locals.put("Dir", _dir);
Debug.locals.put("FileName", _filename);
 BA.debugLineNum = 167;BA.debugLine="Sub chooser_Result (Success As Boolean, Dir As String, FileName As String)";
Debug.ShouldStop(64);
 BA.debugLineNum = 169;BA.debugLine="If Success Then";
Debug.ShouldStop(256);
if (_success) { 
 BA.debugLineNum = 170;BA.debugLine="imgSelected.Bitmap = LoadBitmapSample  (Dir, FileName,imgSelected.Width,imgSelected.Height)";
Debug.ShouldStop(512);
mostCurrent._imgselected.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(_dir,_filename,mostCurrent._imgselected.getWidth(),mostCurrent._imgselected.getHeight()).getObject()));
 BA.debugLineNum = 171;BA.debugLine="picSelected = Success";
Debug.ShouldStop(1024);
_picselected = _success;
 BA.debugLineNum = 172;BA.debugLine="Dir2 = Dir";
Debug.ShouldStop(2048);
mostCurrent._dir2 = _dir;
 BA.debugLineNum = 173;BA.debugLine="FileName2 = FileName";
Debug.ShouldStop(4096);
mostCurrent._filename2 = _filename;
 };
 BA.debugLineNum = 176;BA.debugLine="End Sub";
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
public static String  _getimage_foredit() throws Exception{
		Debug.PushSubsStack("getImage_forEdit (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _inputstrea = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bitt = null;
 BA.debugLineNum = 115;BA.debugLine="Sub getImage_forEdit";
Debug.ShouldStop(262144);
 BA.debugLineNum = 117;BA.debugLine="Dim InputStrea As InputStream";
Debug.ShouldStop(1048576);
_inputstrea = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("InputStrea", _inputstrea);
 BA.debugLineNum = 118;BA.debugLine="Dim Bitt As Bitmap";
Debug.ShouldStop(2097152);
_bitt = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();Debug.locals.put("Bitt", _bitt);
 BA.debugLineNum = 133;BA.debugLine="Buf = Null";
Debug.ShouldStop(16);
_buf = (byte[])(anywheresoftware.b4a.keywords.Common.Null);
 BA.debugLineNum = 134;BA.debugLine="If Main.cur.GetString(\"PicExist\") = \"yes\" Then";
Debug.ShouldStop(32);
if ((mostCurrent._main._cur.GetString("PicExist")).equals("yes")) { 
 BA.debugLineNum = 135;BA.debugLine="Buf = Main.cur.GetBlob(\"ArtistPic\")";
Debug.ShouldStop(64);
_buf = mostCurrent._main._cur.GetBlob("ArtistPic");
 BA.debugLineNum = 136;BA.debugLine="InputStrea.InitializeFromBytesArray(Buf, 0, Buf.Length)";
Debug.ShouldStop(128);
_inputstrea.InitializeFromBytesArray(_buf,(int)(0),_buf.length);
 BA.debugLineNum = 138;BA.debugLine="Bitt.Initialize2(InputStrea)";
Debug.ShouldStop(512);
_bitt.Initialize2((java.io.InputStream)(_inputstrea.getObject()));
 BA.debugLineNum = 139;BA.debugLine="InputStrea.Close";
Debug.ShouldStop(1024);
_inputstrea.Close();
 BA.debugLineNum = 140;BA.debugLine="imgSelected.SetBackgroundImage(Bitt)";
Debug.ShouldStop(2048);
mostCurrent._imgselected.SetBackgroundImage((android.graphics.Bitmap)(_bitt.getObject()));
 }else {
 BA.debugLineNum = 142;BA.debugLine="imgSelected.Bitmap = LoadBitmapSample  (File.DirAssets, \"empty_gallery.png\",imgSelected.Width,imgSelected.Height)";
Debug.ShouldStop(8192);
mostCurrent._imgselected.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"empty_gallery.png",mostCurrent._imgselected.getWidth(),mostCurrent._imgselected.getHeight()).getObject()));
 };
 BA.debugLineNum = 145;BA.debugLine="End Sub";
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim txtName As EditText";
mostCurrent._txtname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim txtSurn As EditText";
mostCurrent._txtsurn = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim txtBio As EditText";
mostCurrent._txtbio = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim Dir2, FileName2 As String";
mostCurrent._dir2 = "";
mostCurrent._filename2 = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim imgSelected As ImageView";
mostCurrent._imgselected = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim picSelected As Boolean";
_picselected = false;
 //BA.debugLineNum = 25;BA.debugLine="Dim pictureAnswer As String";
mostCurrent._pictureanswer = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim txtPhone As EditText";
mostCurrent._txtphone = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim Buf() As Byte";
_buf = new byte[(int)(0)];
;
 //BA.debugLineNum = 28;BA.debugLine="Dim lblNoName As Label";
mostCurrent._lblnoname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim lblNoSurn As Label";
mostCurrent._lblnosurn = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim btnDob As Button";
mostCurrent._btndob = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim Dob As Label";
mostCurrent._dob = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim imageCancel As ImageView";
mostCurrent._imagecancel = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim imageLogo As ImageView";
mostCurrent._imagelogo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim imageSave As ImageView";
mostCurrent._imagesave = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim lblCancel As Label";
mostCurrent._lblcancel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim lblDate As Label";
mostCurrent._lbldate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim lblName As Label";
mostCurrent._lblname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim lblPhone As Label";
mostCurrent._lblphone = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim lblSave As Label";
mostCurrent._lblsave = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim lblSurname As Label";
mostCurrent._lblsurname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim tbConnection As ToggleButton";
mostCurrent._tbconnection = new anywheresoftware.b4a.objects.CompoundButtonWrapper.ToggleButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim myMsgBox As CustomMsgBox";
mostCurrent._mymsgbox = new b4a.sysdev.custommsgbox();
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _imagecancel_click() throws Exception{
		Debug.PushSubsStack("imageCancel_Click (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 233;BA.debugLine="Sub imageCancel_Click";
Debug.ShouldStop(256);
 BA.debugLineNum = 234;BA.debugLine="Activity.Finish";
Debug.ShouldStop(512);
mostCurrent._activity.Finish();
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
public static String  _imagesave_click() throws Exception{
		Debug.PushSubsStack("imageSave_Click (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 183;BA.debugLine="Sub imageSave_Click";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 184;BA.debugLine="lblNoName.Visible = False";
Debug.ShouldStop(8388608);
mostCurrent._lblnoname.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 185;BA.debugLine="lblNoSurn.Visible = False";
Debug.ShouldStop(16777216);
mostCurrent._lblnosurn.setVisible(anywheresoftware.b4a.keywords.Common.False);
 BA.debugLineNum = 188;BA.debugLine="Main.cur = Main.aSQL.ExecQuery(\"SELECT * FROM Artist WHERE ArtistID = \" & Main.m.Get(\"id\"))";
Debug.ShouldStop(134217728);
mostCurrent._main._cur.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery("SELECT * FROM Artist WHERE ArtistID = "+String.valueOf(mostCurrent._main._m.Get((Object)("id"))))));
 BA.debugLineNum = 189;BA.debugLine="Main.cur.Position = 0";
Debug.ShouldStop(268435456);
mostCurrent._main._cur.setPosition((int)(0));
 BA.debugLineNum = 191;BA.debugLine="If txtName.Text = \"\" Then";
Debug.ShouldStop(1073741824);
if ((mostCurrent._txtname.getText()).equals("")) { 
 BA.debugLineNum = 192;BA.debugLine="lblNoName.Visible = True";
Debug.ShouldStop(-2147483648);
mostCurrent._lblnoname.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else 
{ BA.debugLineNum = 193;BA.debugLine="Else If txtSurn.Text = \"\"  Then";
Debug.ShouldStop(1);
if ((mostCurrent._txtsurn.getText()).equals("")) { 
 BA.debugLineNum = 194;BA.debugLine="lblNoSurn.visible = True";
Debug.ShouldStop(2);
mostCurrent._lblnosurn.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 BA.debugLineNum = 197;BA.debugLine="If picSelected Then";
Debug.ShouldStop(16);
if (_picselected) { 
 BA.debugLineNum = 198;BA.debugLine="Insert_Image";
Debug.ShouldStop(32);
_insert_image();
 BA.debugLineNum = 199;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE Artist SET Name=?, Surname=?, Dob=?, Phone=?, Bio=?, ArtistPic=?, PicExist=?  WHERE ArtistID=\" & Main.globID , Array As Object( txtName.Text,txtSurn.Text,lblDate.Text,txtPhone.Text,txtBio.Text ,Buf,\"yes\"))";
Debug.ShouldStop(64);
mostCurrent._main._asql.ExecNonQuery2("UPDATE Artist SET Name=?, Surname=?, Dob=?, Phone=?, Bio=?, ArtistPic=?, PicExist=?  WHERE ArtistID="+mostCurrent._main._globid,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._txtname.getText()),(Object)(mostCurrent._txtsurn.getText()),(Object)(mostCurrent._lbldate.getText()),(Object)(mostCurrent._txtphone.getText()),(Object)(mostCurrent._txtbio.getText()),(Object)(_buf),(Object)("yes")}));
 BA.debugLineNum = 200;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE lookupUsrPass SET Name=?, Surname=? WHERE ArtistID=\" &Main.m.Get(\"id\") , Array As Object( txtName.Text,txtSurn.Text))";
Debug.ShouldStop(128);
mostCurrent._main._asql.ExecNonQuery2("UPDATE lookupUsrPass SET Name=?, Surname=? WHERE ArtistID="+String.valueOf(mostCurrent._main._m.Get((Object)("id"))),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._txtname.getText()),(Object)(mostCurrent._txtsurn.getText())}));
 BA.debugLineNum = 201;BA.debugLine="Main.globName = txtName.Text & \" \" & txtSurn.Text";
Debug.ShouldStop(256);
mostCurrent._main._globname = mostCurrent._txtname.getText()+" "+mostCurrent._txtsurn.getText();
 }else {
 BA.debugLineNum = 203;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE Artist SET Name=?, Surname=?, Dob=?, Phone=?, Bio=? WHERE ArtistID=\" & Main.m.Get(\"id\") , Array As Object( txtName.Text,txtSurn.Text,lblDate.Text,txtPhone.Text,txtBio.Text ))";
Debug.ShouldStop(1024);
mostCurrent._main._asql.ExecNonQuery2("UPDATE Artist SET Name=?, Surname=?, Dob=?, Phone=?, Bio=? WHERE ArtistID="+String.valueOf(mostCurrent._main._m.Get((Object)("id"))),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._txtname.getText()),(Object)(mostCurrent._txtsurn.getText()),(Object)(mostCurrent._lbldate.getText()),(Object)(mostCurrent._txtphone.getText()),(Object)(mostCurrent._txtbio.getText())}));
 BA.debugLineNum = 204;BA.debugLine="Main.aSQL.ExecNonQuery2(\"UPDATE lookupUsrPass SET Name=?, Surname=? WHERE ArtistID=\" & Main.m.Get(\"id\") , Array As Object( txtName.Text,txtSurn.Text))";
Debug.ShouldStop(2048);
mostCurrent._main._asql.ExecNonQuery2("UPDATE lookupUsrPass SET Name=?, Surname=? WHERE ArtistID="+String.valueOf(mostCurrent._main._m.Get((Object)("id"))),anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._txtname.getText()),(Object)(mostCurrent._txtsurn.getText())}));
 BA.debugLineNum = 205;BA.debugLine="Main.globName = txtName.Text & \" \" & txtSurn.Text";
Debug.ShouldStop(4096);
mostCurrent._main._globname = mostCurrent._txtname.getText()+" "+mostCurrent._txtsurn.getText();
 };
 BA.debugLineNum = 213;BA.debugLine="ArtistUpdated = True";
Debug.ShouldStop(1048576);
_artistupdated = anywheresoftware.b4a.keywords.Common.True;
 BA.debugLineNum = 214;BA.debugLine="StartActivity(\"menu\")";
Debug.ShouldStop(2097152);
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("menu"));
 BA.debugLineNum = 215;BA.debugLine="Activity.Finish";
Debug.ShouldStop(4194304);
mostCurrent._activity.Finish();
 }};
 BA.debugLineNum = 218;BA.debugLine="End Sub";
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
public static String  _imgselected_click() throws Exception{
		Debug.PushSubsStack("imgSelected_Click (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 178;BA.debugLine="Sub imgSelected_Click";
Debug.ShouldStop(131072);
 BA.debugLineNum = 179;BA.debugLine="showMsgBoxCamera";
Debug.ShouldStop(262144);
_showmsgboxcamera();
 BA.debugLineNum = 181;BA.debugLine="End Sub";
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
public static String  _insert_image() throws Exception{
		Debug.PushSubsStack("Insert_Image (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _inputstream1 = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _outputstream1 = null;
 BA.debugLineNum = 221;BA.debugLine="Sub Insert_Image";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 222;BA.debugLine="Dim InputStream1 As InputStream";
Debug.ShouldStop(536870912);
_inputstream1 = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("InputStream1", _inputstream1);
 BA.debugLineNum = 223;BA.debugLine="InputStream1 = File.OpenInput(Dir2, FileName2)";
Debug.ShouldStop(1073741824);
_inputstream1 = anywheresoftware.b4a.keywords.Common.File.OpenInput(mostCurrent._dir2,mostCurrent._filename2);Debug.locals.put("InputStream1", _inputstream1);
 BA.debugLineNum = 225;BA.debugLine="Dim OutputStream1 As OutputStream";
Debug.ShouldStop(1);
_outputstream1 = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();Debug.locals.put("OutputStream1", _outputstream1);
 BA.debugLineNum = 226;BA.debugLine="OutputStream1.InitializeToBytesArray(1000)";
Debug.ShouldStop(2);
_outputstream1.InitializeToBytesArray((int)(1000));
 BA.debugLineNum = 227;BA.debugLine="File.Copy2(InputStream1, OutputStream1)";
Debug.ShouldStop(4);
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_inputstream1.getObject()),(java.io.OutputStream)(_outputstream1.getObject()));
 BA.debugLineNum = 229;BA.debugLine="Buf = OutputStream1.ToBytesArray";
Debug.ShouldStop(16);
_buf = _outputstream1.ToBytesArray();
 BA.debugLineNum = 231;BA.debugLine="End Sub";
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
public static String  _lblcancel_click() throws Exception{
		Debug.PushSubsStack("lblCancel_Click (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 241;BA.debugLine="Sub lblCancel_Click";
Debug.ShouldStop(65536);
 BA.debugLineNum = 242;BA.debugLine="imageCancel_Click";
Debug.ShouldStop(131072);
_imagecancel_click();
 BA.debugLineNum = 243;BA.debugLine="End Sub";
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
public static String  _lblsave_click() throws Exception{
		Debug.PushSubsStack("lblSave_Click (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 237;BA.debugLine="Sub lblSave_Click";
Debug.ShouldStop(4096);
 BA.debugLineNum = 238;BA.debugLine="imageSave_Click";
Debug.ShouldStop(8192);
_imagesave_click();
 BA.debugLineNum = 239;BA.debugLine="End Sub";
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
public static String  _loadtocontrols() throws Exception{
		Debug.PushSubsStack("loadToControls (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 99;BA.debugLine="Sub loadToControls";
Debug.ShouldStop(4);
 BA.debugLineNum = 101;BA.debugLine="Main.cur = Main.aSQL.ExecQuery2(\"SELECT * FROM Artist WHERE ArtistID = ?\", Array As String(Main.globID))";
Debug.ShouldStop(16);
mostCurrent._main._cur.setObject((android.database.Cursor)(mostCurrent._main._asql.ExecQuery2("SELECT * FROM Artist WHERE ArtistID = ?",new String[]{mostCurrent._main._globid})));
 BA.debugLineNum = 102;BA.debugLine="Main.cur.Position = 0";
Debug.ShouldStop(32);
mostCurrent._main._cur.setPosition((int)(0));
 BA.debugLineNum = 105;BA.debugLine="txtName.Text = Main.cur.GetString(\"Name\")";
Debug.ShouldStop(256);
mostCurrent._txtname.setText((Object)(mostCurrent._main._cur.GetString("Name")));
 BA.debugLineNum = 106;BA.debugLine="txtSurn.Text = Main.cur.GetString(\"Surname\")";
Debug.ShouldStop(512);
mostCurrent._txtsurn.setText((Object)(mostCurrent._main._cur.GetString("Surname")));
 BA.debugLineNum = 107;BA.debugLine="lblDate.Text = Main.cur.GetString(\"Dob\")";
Debug.ShouldStop(1024);
mostCurrent._lbldate.setText((Object)(mostCurrent._main._cur.GetString("Dob")));
 BA.debugLineNum = 108;BA.debugLine="txtPhone.Text = Main.cur.GetString(\"Phone\")";
Debug.ShouldStop(2048);
mostCurrent._txtphone.setText((Object)(mostCurrent._main._cur.GetString("Phone")));
 BA.debugLineNum = 109;BA.debugLine="txtBio.Text = Main.cur.GetString(\"Bio\")";
Debug.ShouldStop(4096);
mostCurrent._txtbio.setText((Object)(mostCurrent._main._cur.GetString("Bio")));
 BA.debugLineNum = 110;BA.debugLine="getImage_forEdit";
Debug.ShouldStop(8192);
_getimage_foredit();
 BA.debugLineNum = 113;BA.debugLine="End Sub";
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim SelectedDateInTicks As Long";
_selecteddateinticks = 0L;
 //BA.debugLineNum = 10;BA.debugLine="Dim cc As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 11;BA.debugLine="Dim  ArtistUpdated As Boolean";
_artistupdated = false;
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _showmsgboxcamera() throws Exception{
		Debug.PushSubsStack("showMsgBoxCamera (editartist) ","editartist",7,mostCurrent.activityBA,mostCurrent);
try {
 BA.debugLineNum = 245;BA.debugLine="Sub showMsgBoxCamera";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 247;BA.debugLine="myMsgBox.Initialize(Activity, Me, \"Cam\", \"H\", 3, 95%x, 200dip, LoadBitmap(File.DirAssets, \"cameraMsg.png\"))";
Debug.ShouldStop(4194304);
mostCurrent._mymsgbox._initialize(mostCurrent.activityBA,mostCurrent._activity,editartist.getObject(),"Cam","H",(int)(3),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(200)),anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cameraMsg.png"));
 BA.debugLineNum = 250;BA.debugLine="myMsgBox.Title.textColor = Colors.white";
Debug.ShouldStop(33554432);
mostCurrent._mymsgbox._title.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 251;BA.debugLine="myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD";
Debug.ShouldStop(67108864);
mostCurrent._mymsgbox._title.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 BA.debugLineNum = 252;BA.debugLine="myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, \"B.png\"))";
Debug.ShouldStop(134217728);
mostCurrent._mymsgbox._panel.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"B.png").getObject()));
 BA.debugLineNum = 254;BA.debugLine="myMsgBox.ShowSeparators(Colors.black, Colors.black)";
Debug.ShouldStop(536870912);
mostCurrent._mymsgbox._showseparators(anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 255;BA.debugLine="myMsgBox.Message.Height = 10dip";
Debug.ShouldStop(1073741824);
mostCurrent._mymsgbox._message.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(10)));
 BA.debugLineNum = 256;BA.debugLine="myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))";
Debug.ShouldStop(-2147483648);
mostCurrent._mymsgbox._showshadow(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int)(80),(int)(8),(int)(180),(int)(206)));
 BA.debugLineNum = 258;BA.debugLine="myMsgBox.YesButtonPanel.Color = Colors.white";
Debug.ShouldStop(2);
mostCurrent._mymsgbox._yesbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 259;BA.debugLine="myMsgBox.NoButtonPanel.Color = Colors.white";
Debug.ShouldStop(4);
mostCurrent._mymsgbox._nobuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 260;BA.debugLine="myMsgBox.CancelButtonPanel.Color = Colors.white";
Debug.ShouldStop(8);
mostCurrent._mymsgbox._cancelbuttonpanel.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 262;BA.debugLine="myMsgBox.NoButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(32);
mostCurrent._mymsgbox._nobuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 263;BA.debugLine="myMsgBox.CancelButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(64);
mostCurrent._mymsgbox._cancelbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 264;BA.debugLine="myMsgBox.YesButtonCaption.TextColor =Colors.Black";
Debug.ShouldStop(128);
mostCurrent._mymsgbox._yesbuttoncaption.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 BA.debugLineNum = 266;BA.debugLine="myMsgBox.Message.TextColor = Colors.white";
Debug.ShouldStop(512);
mostCurrent._mymsgbox._message.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 BA.debugLineNum = 267;BA.debugLine="myMsgBox.Message.TextSize = 23";
Debug.ShouldStop(1024);
mostCurrent._mymsgbox._message.setTextSize((float)(23));
 BA.debugLineNum = 269;BA.debugLine="If Main.globlang = \"isiXhosa\" Then";
Debug.ShouldStop(4096);
if ((mostCurrent._main._globlang).equals("isiXhosa")) { 
 }else {
 BA.debugLineNum = 275;BA.debugLine="myMsgBox.NoButtonCaption.Text = \"From Gallery\"";
Debug.ShouldStop(262144);
mostCurrent._mymsgbox._nobuttoncaption.setText((Object)("From Gallery"));
 BA.debugLineNum = 276;BA.debugLine="myMsgBox.YesButtonCaption.Text = \"Take Picture\"";
Debug.ShouldStop(524288);
mostCurrent._mymsgbox._yesbuttoncaption.setText((Object)("Take Picture"));
 BA.debugLineNum = 277;BA.debugLine="myMsgBox.CancelButtonCaption.Text = \"Cancel\"";
Debug.ShouldStop(1048576);
mostCurrent._mymsgbox._cancelbuttoncaption.setText((Object)("Cancel"));
 BA.debugLineNum = 278;BA.debugLine="myMsgBox.ShowMessage(\"Please select an option\",\"C\")";
Debug.ShouldStop(2097152);
mostCurrent._mymsgbox._showmessage("Please select an option","C");
 };
 BA.debugLineNum = 280;BA.debugLine="myMsgBox.Title.Text = \"Picture\"";
Debug.ShouldStop(8388608);
mostCurrent._mymsgbox._title.setText((Object)("Picture"));
 BA.debugLineNum = 281;BA.debugLine="myMsgBox.Title.TextSize = 25";
Debug.ShouldStop(16777216);
mostCurrent._mymsgbox._title.setTextSize((float)(25));
 BA.debugLineNum = 283;BA.debugLine="End Sub";
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
  public Object[] GetGlobals() {
		return new Object[] {"Activity",_activity,"SelectedDateInTicks",_selecteddateinticks,"cc",_cc,"ArtistUpdated",_artistupdated,"Label2",_label2,"txtName",_txtname,"txtSurn",_txtsurn,"EditText1",_edittext1,"txtBio",_txtbio,"Dir2",_dir2,"FileName2",_filename2,"imgSelected",_imgselected,"picSelected",_picselected,"pictureAnswer",_pictureanswer,"txtPhone",_txtphone,"Buf",_buf,"lblNoName",_lblnoname,"lblNoSurn",_lblnosurn,"btnDob",_btndob,"Dob",_dob,"imageCancel",_imagecancel,"imageLogo",_imagelogo,"imageSave",_imagesave,"lblCancel",_lblcancel,"lblDate",_lbldate,"lblName",_lblname,"lblPhone",_lblphone,"lblSave",_lblsave,"lblSurname",_lblsurname,"tbConnection",_tbconnection,"myMsgBox",_mymsgbox,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
