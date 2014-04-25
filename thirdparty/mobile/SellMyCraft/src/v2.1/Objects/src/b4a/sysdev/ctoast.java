package b4a.sysdev;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class ctoast extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.sysdev.ctoast");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
        }
        _class_globals();
    }


 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _tms2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _tms2pan = null;
public anywheresoftware.b4a.objects.LabelWrapper _tms2lab = null;
public anywheresoftware.b4a.objects.LabelWrapper _tms2text = null;
public anywheresoftware.b4a.objects.Timer _ttime = null;
public int _acth = 0;
public int _actw = 0;
public int _np = 0;
public String _myeventname = "";
public Object _mymodule = null;
public boolean _tac = false;
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.editartist _editartist = null;
public b4a.sysdev.help _help = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Private TMS2 As Reflector";
_tms2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 8;BA.debugLine="Private TMS2pan As Panel";
_tms2pan = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Private TMS2lab As Label";
_tms2lab = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Private TMS2text As Label";
_tms2text = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private TTime As Timer";
_ttime = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 12;BA.debugLine="Private ActH As Int";
_acth = 0;
 //BA.debugLineNum = 13;BA.debugLine="Private ActW As Int";
_actw = 0;
 //BA.debugLineNum = 14;BA.debugLine="Private NP As Int";
_np = 0;
 //BA.debugLineNum = 15;BA.debugLine="Private MyEventName As String";
_myeventname = "";
 //BA.debugLineNum = 16;BA.debugLine="Private MyModule As Object";
_mymodule = new Object();
 //BA.debugLineNum = 17;BA.debugLine="Private Tac As Boolean";
_tac = false;
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public String  _closenow() throws Exception{
		Debug.PushSubsStack("CloseNow (ctoast) ","ctoast",9,ba,this);
try {
 BA.debugLineNum = 123;BA.debugLine="Sub CloseNow";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 124;BA.debugLine="TTime_Tick";
Debug.ShouldStop(134217728);
_ttime_tick();
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
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ActivityWrapper _activity,Object _module,int _act_h,int _act_w) throws Exception{
innerInitialize(_ba);
		Debug.PushSubsStack("Initialize (ctoast) ","ctoast",9,ba,this);
try {
Debug.locals.put("ba", _ba);
Debug.locals.put("Activity", _activity);
Debug.locals.put("Module", _module);
Debug.locals.put("Act_H", _act_h);
Debug.locals.put("Act_W", _act_w);
 BA.debugLineNum = 24;BA.debugLine="Sub Initialize(Activity As Activity, Module As Object, Act_H As Int, Act_W As Int)";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 25;BA.debugLine="Tac = True";
Debug.ShouldStop(16777216);
_tac = __c.True;
 BA.debugLineNum = 26;BA.debugLine="ActH = Act_H";
Debug.ShouldStop(33554432);
_acth = _act_h;
 BA.debugLineNum = 27;BA.debugLine="ActW = Act_W";
Debug.ShouldStop(67108864);
_actw = _act_w;
 BA.debugLineNum = 28;BA.debugLine="MyModule = Module";
Debug.ShouldStop(134217728);
_mymodule = _module;
 BA.debugLineNum = 29;BA.debugLine="TMS2lab.Initialize(\"TMS2lab\")";
Debug.ShouldStop(268435456);
_tms2lab.Initialize(ba,"TMS2lab");
 BA.debugLineNum = 30;BA.debugLine="TMS2pan.Initialize(\"TMS2pan\")";
Debug.ShouldStop(536870912);
_tms2pan.Initialize(ba,"TMS2pan");
 BA.debugLineNum = 31;BA.debugLine="Activity.AddView(TMS2pan, 100, 50, 200, 50)";
Debug.ShouldStop(1073741824);
_activity.AddView((android.view.View)(_tms2pan.getObject()),(int)(100),(int)(50),(int)(200),(int)(50));
 BA.debugLineNum = 32;BA.debugLine="TMS2pan.AddView(TMS2lab, 100, 50, 200, 40)";
Debug.ShouldStop(-2147483648);
_tms2pan.AddView((android.view.View)(_tms2lab.getObject()),(int)(100),(int)(50),(int)(200),(int)(40));
 BA.debugLineNum = 33;BA.debugLine="TMS2pan.Visible = False";
Debug.ShouldStop(1);
_tms2pan.setVisible(__c.False);
 BA.debugLineNum = 34;BA.debugLine="TMS2text.Initialize(\"TMS2text\")";
Debug.ShouldStop(2);
_tms2text.Initialize(ba,"TMS2text");
 BA.debugLineNum = 35;BA.debugLine="Activity.AddView(TMS2text,0,0,100,60)";
Debug.ShouldStop(4);
_activity.AddView((android.view.View)(_tms2text.getObject()),(int)(0),(int)(0),(int)(100),(int)(60));
 BA.debugLineNum = 36;BA.debugLine="End Sub";
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
public String  _setninepatchdrawable(anywheresoftware.b4a.objects.ConcreteViewWrapper _control,String _imagename) throws Exception{
		Debug.PushSubsStack("SetNinePatchDrawable (ctoast) ","ctoast",9,ba,this);
try {
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
String _package = "";
int _id = 0;
Debug.locals.put("Control", _control);
Debug.locals.put("ImageName", _imagename);
 BA.debugLineNum = 126;BA.debugLine="Private Sub SetNinePatchDrawable(Control As View, ImageName As String)";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 127;BA.debugLine="ImageName = ImageName.SubString2(0, ImageName.IndexOf(\".\"))";
Debug.ShouldStop(1073741824);
_imagename = _imagename.substring((int)(0),_imagename.indexOf("."));Debug.locals.put("ImageName", _imagename);
 BA.debugLineNum = 128;BA.debugLine="Dim r As Reflector";
Debug.ShouldStop(-2147483648);
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();Debug.locals.put("r", _r);
 BA.debugLineNum = 129;BA.debugLine="Dim package As String";
Debug.ShouldStop(1);
_package = "";Debug.locals.put("package", _package);
 BA.debugLineNum = 130;BA.debugLine="Dim id As Int";
Debug.ShouldStop(2);
_id = 0;Debug.locals.put("id", _id);
 BA.debugLineNum = 131;BA.debugLine="package = r.GetStaticField(\"anywheresoftware.b4a.BA\", \"packageName\")";
Debug.ShouldStop(4);
_package = String.valueOf(_r.GetStaticField("anywheresoftware.b4a.BA","packageName"));Debug.locals.put("package", _package);
 BA.debugLineNum = 132;BA.debugLine="Try";
Debug.ShouldStop(8);
try { BA.debugLineNum = 133;BA.debugLine="id = r.GetStaticField(package & \".R$drawable\", ImageName)";
Debug.ShouldStop(16);
_id = (int)(BA.ObjectToNumber(_r.GetStaticField(_package+".R$drawable",_imagename)));Debug.locals.put("id", _id);
 BA.debugLineNum = 134;BA.debugLine="r.Target = r.GetContext";
Debug.ShouldStop(32);
_r.Target = (Object)(_r.GetContext(ba));Debug.locals.put("r", _r);
 BA.debugLineNum = 135;BA.debugLine="r.Target = r.RunMethod(\"getResources\")";
Debug.ShouldStop(64);
_r.Target = _r.RunMethod("getResources");Debug.locals.put("r", _r);
 BA.debugLineNum = 136;BA.debugLine="Control.Background = r.RunMethod2(\"getDrawable\", id, \"java.lang.int\")";
Debug.ShouldStop(128);
_control.setBackground((android.graphics.drawable.Drawable)(_r.RunMethod2("getDrawable",BA.NumberToString(_id),"java.lang.int")));
 BA.debugLineNum = 137;BA.debugLine="NP = 1";
Debug.ShouldStop(256);
_np = (int)(1);
 } 
       catch (Exception e121) {
			ba.setLastException(e121); BA.debugLineNum = 139;BA.debugLine="Msgbox(\"9 Patch File: [\"& ImageName & \"], not found. Be sure to select Tools & Clean Project in the B4A IDE.\", \"NOTICE\")";
Debug.ShouldStop(1024);
__c.Msgbox("9 Patch File: ["+_imagename+"], not found. Be sure to select Tools & Clean Project in the B4A IDE.","NOTICE",ba);
 };
 BA.debugLineNum = 141;BA.debugLine="End Sub";
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
public String  _settoast(int _tset) throws Exception{
		Debug.PushSubsStack("SetToast (ctoast) ","ctoast",9,ba,this);
try {
Debug.locals.put("Tset", _tset);
 BA.debugLineNum = 108;BA.debugLine="Private Sub SetToast(Tset As Int)";
Debug.ShouldStop(2048);
 BA.debugLineNum = 109;BA.debugLine="TTime.Enabled = False";
Debug.ShouldStop(4096);
_ttime.setEnabled(__c.False);
 BA.debugLineNum = 110;BA.debugLine="TTime.Initialize(\"TTime\", (Tset * 1000))";
Debug.ShouldStop(8192);
Debug.DebugWarningEngine.CheckInitialize(_ttime);_ttime.Initialize(ba,"TTime",(long)((_tset*1000)));
 BA.debugLineNum = 111;BA.debugLine="TTime.Enabled = True";
Debug.ShouldStop(16384);
_ttime.setEnabled(__c.True);
 BA.debugLineNum = 112;BA.debugLine="End Sub";
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
public String  _tms2lab_click() throws Exception{
		Debug.PushSubsStack("TMS2lab_Click (ctoast) ","ctoast",9,ba,this);
try {
 BA.debugLineNum = 117;BA.debugLine="Private Sub TMS2lab_Click";
Debug.ShouldStop(1048576);
 BA.debugLineNum = 118;BA.debugLine="If Tac = False Then";
Debug.ShouldStop(2097152);
if (_tac==__c.False) { 
 BA.debugLineNum = 119;BA.debugLine="Tac = True : TTime_Tick";
Debug.ShouldStop(4194304);
_tac = __c.True;
 BA.debugLineNum = 119;BA.debugLine="Tac = True : TTime_Tick";
Debug.ShouldStop(4194304);
_ttime_tick();
 };
 BA.debugLineNum = 121;BA.debugLine="End Sub";
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
public String  _toastmessageshow2(String _tmessage,int _tseconds,int _tpercentdownvertical,int _tpercentacrosshorizontal,String _timage,long _tcolor,long _tbackcolor,int _ttextsize,boolean _tautoclose) throws Exception{
		Debug.PushSubsStack("ToastMessageShow2 (ctoast) ","ctoast",9,ba,this);
try {
int _tmfl = 0;
int _height = 0;
int _width = 0;
int _abc = 0;
Debug.locals.put("Tmessage", _tmessage);
Debug.locals.put("Tseconds", _tseconds);
Debug.locals.put("TpercentDownVertical", _tpercentdownvertical);
Debug.locals.put("TpercentAcrossHorizontal", _tpercentacrosshorizontal);
Debug.locals.put("Timage", _timage);
Debug.locals.put("Tcolor", _tcolor);
Debug.locals.put("Tbackcolor", _tbackcolor);
Debug.locals.put("Ttextsize", _ttextsize);
Debug.locals.put("Tautoclose", _tautoclose);
 BA.debugLineNum = 46;BA.debugLine="Sub ToastMessageShow2(Tmessage As String, Tseconds As Int, TpercentDownVertical As Int, TpercentAcrossHorizontal As Int, Timage As String, Tcolor As Long, Tbackcolor As Long, Ttextsize As Int, Tautoclose As Boolean)";
Debug.ShouldStop(8192);
 BA.debugLineNum = 47;BA.debugLine="If Tautoclose = False Then TTime.Enabled = False";
Debug.ShouldStop(16384);
if (_tautoclose==__c.False) { 
_ttime.setEnabled(__c.False);};
 BA.debugLineNum = 48;BA.debugLine="TMS2text.Visible = True";
Debug.ShouldStop(32768);
_tms2text.setVisible(__c.True);
 BA.debugLineNum = 49;BA.debugLine="TMS2text.TextColor = Colors.Transparent";
Debug.ShouldStop(65536);
_tms2text.setTextColor(__c.Colors.Transparent);
 BA.debugLineNum = 50;BA.debugLine="Dim tmfl As Int : tmfl = Tseconds";
Debug.ShouldStop(131072);
_tmfl = 0;Debug.locals.put("tmfl", _tmfl);
 BA.debugLineNum = 50;BA.debugLine="Dim tmfl As Int : tmfl = Tseconds";
Debug.ShouldStop(131072);
_tmfl = _tseconds;Debug.locals.put("tmfl", _tmfl);
 BA.debugLineNum = 51;BA.debugLine="Dim Height, Width As Int";
Debug.ShouldStop(262144);
_height = 0;Debug.locals.put("Height", _height);
_width = 0;Debug.locals.put("Width", _width);
 BA.debugLineNum = 52;BA.debugLine="TMS2text.Text = Tmessage";
Debug.ShouldStop(524288);
_tms2text.setText((Object)(_tmessage));
 BA.debugLineNum = 53;BA.debugLine="TMS2text.TextSize = Ttextsize";
Debug.ShouldStop(1048576);
_tms2text.setTextSize((float)(_ttextsize));
 BA.debugLineNum = 54;BA.debugLine="TMS2text.Width = -2";
Debug.ShouldStop(2097152);
_tms2text.setWidth((int)(-2));
 BA.debugLineNum = 55;BA.debugLine="TMS2text.Height = -2";
Debug.ShouldStop(4194304);
_tms2text.setHeight((int)(-2));
 BA.debugLineNum = 56;BA.debugLine="DoEvents";
Debug.ShouldStop(8388608);
__c.DoEvents();
 BA.debugLineNum = 57;BA.debugLine="TMS2.Target = TMS2text";
Debug.ShouldStop(16777216);
_tms2.Target = (Object)(_tms2text.getObject());
 BA.debugLineNum = 58;BA.debugLine="Width = TMS2.RunMethod(\"getWidth\")";
Debug.ShouldStop(33554432);
_width = (int)(BA.ObjectToNumber(_tms2.RunMethod("getWidth")));Debug.locals.put("Width", _width);
 BA.debugLineNum = 59;BA.debugLine="Height = TMS2.RunMethod(\"getHeight\")";
Debug.ShouldStop(67108864);
_height = (int)(BA.ObjectToNumber(_tms2.RunMethod("getHeight")));Debug.locals.put("Height", _height);
 BA.debugLineNum = 60;BA.debugLine="TMS2text.Visible = False";
Debug.ShouldStop(134217728);
_tms2text.setVisible(__c.False);
 BA.debugLineNum = 61;BA.debugLine="TMS2lab.TextColor = Colors.DarkGray";
Debug.ShouldStop(268435456);
_tms2lab.setTextColor(__c.Colors.DarkGray);
 BA.debugLineNum = 62;BA.debugLine="TMS2lab.TextSize = Ttextsize";
Debug.ShouldStop(536870912);
_tms2lab.setTextSize((float)(_ttextsize));
 BA.debugLineNum = 63;BA.debugLine="TMS2lab.Gravity = Gravity.CENTER";
Debug.ShouldStop(1073741824);
_tms2lab.setGravity(__c.Gravity.CENTER);
 BA.debugLineNum = 64;BA.debugLine="TMS2pan.Color = Tbackcolor";
Debug.ShouldStop(-2147483648);
_tms2pan.setColor((int)(_tbackcolor));
 BA.debugLineNum = 65;BA.debugLine="If Timage <> \"\" Then";
Debug.ShouldStop(1);
if ((_timage).equals("") == false) { 
 BA.debugLineNum = 66;BA.debugLine="If File.Exists(File.DirAssets, Timage) Then";
Debug.ShouldStop(2);
if (__c.File.Exists(__c.File.getDirAssets(),_timage)) { 
 BA.debugLineNum = 67;BA.debugLine="TMS2pan.SetBackgroundImage(LoadBitmap(File.DirAssets, Timage))";
Debug.ShouldStop(4);
_tms2pan.SetBackgroundImage((android.graphics.Bitmap)(__c.LoadBitmap(__c.File.getDirAssets(),_timage).getObject()));
 }else {
 BA.debugLineNum = 69;BA.debugLine="SetNinePatchDrawable(TMS2pan, Timage)";
Debug.ShouldStop(16);
_setninepatchdrawable((anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_tms2pan.getObject())),_timage);
 };
 };
 BA.debugLineNum = 72;BA.debugLine="TMS2lab.Text = Tmessage";
Debug.ShouldStop(128);
_tms2lab.setText((Object)(_tmessage));
 BA.debugLineNum = 73;BA.debugLine="TMS2pan.BringToFront";
Debug.ShouldStop(256);
_tms2pan.BringToFront();
 BA.debugLineNum = 74;BA.debugLine="Dim abc As Int";
Debug.ShouldStop(512);
_abc = 0;Debug.locals.put("abc", _abc);
 BA.debugLineNum = 75;BA.debugLine="If TpercentDownVertical > 0 AND TpercentDownVertical < 91 Then";
Debug.ShouldStop(1024);
if (_tpercentdownvertical>0 && _tpercentdownvertical<91) { 
 BA.debugLineNum = 76;BA.debugLine="TMS2pan.Top = (ActH * (TpercentDownVertical/100))";
Debug.ShouldStop(2048);
_tms2pan.setTop((int)((_acth*(_tpercentdownvertical/(double)100))));
 }else {
 BA.debugLineNum = 78;BA.debugLine="TMS2pan.Top = (ActH * .10)";
Debug.ShouldStop(8192);
_tms2pan.setTop((int)((_acth*.10)));
 };
 BA.debugLineNum = 80;BA.debugLine="If TpercentAcrossHorizontal > 0 AND TpercentAcrossHorizontal < 91 Then";
Debug.ShouldStop(32768);
if (_tpercentacrosshorizontal>0 && _tpercentacrosshorizontal<91) { 
 BA.debugLineNum = 81;BA.debugLine="TMS2pan.Left = (ActW * (TpercentAcrossHorizontal/100)) - ((Width+60)/2)";
Debug.ShouldStop(65536);
_tms2pan.setLeft((int)((_actw*(_tpercentacrosshorizontal/(double)100))-((_width+60)/(double)2)));
 }else {
 BA.debugLineNum = 83;BA.debugLine="TMS2pan.Left = (ActW/2) - ((Width+60)/2)";
Debug.ShouldStop(262144);
_tms2pan.setLeft((int)((_actw/(double)2)-((_width+60)/(double)2)));
 };
 BA.debugLineNum = 85;BA.debugLine="TMS2lab.Left = 30";
Debug.ShouldStop(1048576);
_tms2lab.setLeft((int)(30));
 BA.debugLineNum = 86;BA.debugLine="TMS2lab.Top = 5";
Debug.ShouldStop(2097152);
_tms2lab.setTop((int)(5));
 BA.debugLineNum = 87;BA.debugLine="TMS2pan.Width = Width + 60";
Debug.ShouldStop(4194304);
_tms2pan.setWidth((int)(_width+60));
 BA.debugLineNum = 88;BA.debugLine="TMS2lab.Width = Width";
Debug.ShouldStop(8388608);
_tms2lab.setWidth(_width);
 BA.debugLineNum = 89;BA.debugLine="If NP > 0 Then";
Debug.ShouldStop(16777216);
if (_np>0) { 
 BA.debugLineNum = 90;BA.debugLine="TMS2pan.Height = Height + (TMS2text.TextSize / 1.4) : NP = 0";
Debug.ShouldStop(33554432);
_tms2pan.setHeight((int)(_height+(_tms2text.getTextSize()/(double)1.4)));
 BA.debugLineNum = 90;BA.debugLine="TMS2pan.Height = Height + (TMS2text.TextSize / 1.4) : NP = 0";
Debug.ShouldStop(33554432);
_np = (int)(0);
 }else {
 BA.debugLineNum = 92;BA.debugLine="TMS2pan.Height = Height + (TMS2text.TextSize / 1.8)";
Debug.ShouldStop(134217728);
_tms2pan.setHeight((int)(_height+(_tms2text.getTextSize()/(double)1.8)));
 };
 BA.debugLineNum = 94;BA.debugLine="TMS2lab.Height = Height";
Debug.ShouldStop(536870912);
_tms2lab.setHeight(_height);
 BA.debugLineNum = 95;BA.debugLine="DoEvents";
Debug.ShouldStop(1073741824);
__c.DoEvents();
 BA.debugLineNum = 96;BA.debugLine="If Tcolor <> 0 Then";
Debug.ShouldStop(-2147483648);
if (_tcolor!=0) { 
 BA.debugLineNum = 97;BA.debugLine="TMS2lab.TextColor = Tcolor";
Debug.ShouldStop(1);
_tms2lab.setTextColor((int)(_tcolor));
 };
 BA.debugLineNum = 99;BA.debugLine="TMS2lab.Visible = True";
Debug.ShouldStop(4);
_tms2lab.setVisible(__c.True);
 BA.debugLineNum = 100;BA.debugLine="TMS2pan.Visible = True";
Debug.ShouldStop(8);
_tms2pan.setVisible(__c.True);
 BA.debugLineNum = 101;BA.debugLine="DoEvents";
Debug.ShouldStop(16);
__c.DoEvents();
 BA.debugLineNum = 102;BA.debugLine="If Tautoclose = False Then";
Debug.ShouldStop(32);
if (_tautoclose==__c.False) { 
 BA.debugLineNum = 103;BA.debugLine="Tac = False";
Debug.ShouldStop(64);
_tac = __c.False;
 }else {
 BA.debugLineNum = 105;BA.debugLine="SetToast(Tseconds)";
Debug.ShouldStop(256);
_settoast(_tseconds);
 };
 BA.debugLineNum = 107;BA.debugLine="End Sub";
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
public String  _ttime_tick() throws Exception{
		Debug.PushSubsStack("TTime_Tick (ctoast) ","ctoast",9,ba,this);
try {
 BA.debugLineNum = 113;BA.debugLine="Private Sub TTime_Tick";
Debug.ShouldStop(65536);
 BA.debugLineNum = 114;BA.debugLine="TTime.Enabled = False";
Debug.ShouldStop(131072);
_ttime.setEnabled(__c.False);
 BA.debugLineNum = 115;BA.debugLine="TMS2pan.Visible = False";
Debug.ShouldStop(262144);
_tms2pan.setVisible(__c.False);
 BA.debugLineNum = 116;BA.debugLine="End Sub";
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
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
ba.sharedProcessBA.sender = sender;
return BA.SubDelegator.SubNotFound;
}
  public Object[] GetGlobals() {
		return new Object[] {"TMS2",_tms2,"TMS2pan",_tms2pan,"TMS2lab",_tms2lab,"TMS2text",_tms2text,"TTime",_ttime,"ActH",_acth,"ActW",_actw,"NP",_np,"MyEventName",_myeventname,"MyModule",_mymodule,"Tac",_tac,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
