package b4a.sysdev;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class httpjob extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.sysdev.httpjob");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
        }
        _class_globals();
    }


 public anywheresoftware.b4a.keywords.Common __c = null;
public String _jobname = "";
public boolean _success = false;
public String _username = "";
public String _password = "";
public String _errormessage = "";
public Object _target = null;
public String _taskid = "";
public anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper _req = null;
public Object _tag = null;
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
 //BA.debugLineNum = 3;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Public JobName As String";
_jobname = "";
 //BA.debugLineNum = 5;BA.debugLine="Public Success As Boolean";
_success = false;
 //BA.debugLineNum = 6;BA.debugLine="Public Username, Password As String";
_username = "";
_password = "";
 //BA.debugLineNum = 7;BA.debugLine="Public ErrorMessage As String";
_errormessage = "";
 //BA.debugLineNum = 8;BA.debugLine="Private target As Object";
_target = new Object();
 //BA.debugLineNum = 9;BA.debugLine="Private taskId As String";
_taskid = "";
 //BA.debugLineNum = 10;BA.debugLine="Private req As HttpRequest";
_req = new anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Public Tag As Object";
_tag = new Object();
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public String  _complete(int _id) throws Exception{
		Debug.PushSubsStack("Complete (httpjob) ","httpjob",10,ba,this);
try {
Debug.locals.put("id", _id);
 BA.debugLineNum = 87;BA.debugLine="Public Sub Complete (id As Int)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 88;BA.debugLine="taskId = id";
Debug.ShouldStop(8388608);
_taskid = BA.NumberToString(_id);
 BA.debugLineNum = 89;BA.debugLine="CallSubDelayed2(target, \"JobDone\", Me)";
Debug.ShouldStop(16777216);
__c.CallSubDelayed2(getActivityBA(),_target,"JobDone",this);
 BA.debugLineNum = 90;BA.debugLine="End Sub";
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
public String  _download(String _link) throws Exception{
		Debug.PushSubsStack("Download (httpjob) ","httpjob",10,ba,this);
try {
Debug.locals.put("Link", _link);
 BA.debugLineNum = 57;BA.debugLine="Public Sub Download(Link As String)";
Debug.ShouldStop(16777216);
 BA.debugLineNum = 58;BA.debugLine="req.InitializeGet(Link)";
Debug.ShouldStop(33554432);
_req.InitializeGet(_link);
 BA.debugLineNum = 59;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", Me)";
Debug.ShouldStop(67108864);
__c.CallSubDelayed2(getActivityBA(),(Object)(_httputils2service.getObject()),"SubmitJob",this);
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
public String  _download2(String _link,String[] _parameters) throws Exception{
		Debug.PushSubsStack("Download2 (httpjob) ","httpjob",10,ba,this);
try {
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
int _i = 0;
Debug.locals.put("Link", _link);
Debug.locals.put("Parameters", _parameters);
 BA.debugLineNum = 66;BA.debugLine="Public Sub Download2(Link As String, Parameters() As String)";
Debug.ShouldStop(2);
 BA.debugLineNum = 67;BA.debugLine="Dim sb As StringBuilder";
Debug.ShouldStop(4);
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("sb", _sb);
 BA.debugLineNum = 68;BA.debugLine="sb.Initialize";
Debug.ShouldStop(8);
_sb.Initialize();
 BA.debugLineNum = 69;BA.debugLine="sb.Append(Link)";
Debug.ShouldStop(16);
_sb.Append(_link);
 BA.debugLineNum = 70;BA.debugLine="If Parameters.Length > 0 Then sb.Append(\"?\")";
Debug.ShouldStop(32);
if (_parameters.length>0) { 
_sb.Append("?");};
 BA.debugLineNum = 71;BA.debugLine="Dim su As StringUtils";
Debug.ShouldStop(64);
_su = new anywheresoftware.b4a.objects.StringUtils();Debug.locals.put("su", _su);
 BA.debugLineNum = 72;BA.debugLine="For i = 0 To Parameters.Length - 1 Step 2";
Debug.ShouldStop(128);
{
final double step50 = 2;
final double limit50 = (int)(_parameters.length-1);
for (_i = (int)(0); (step50 > 0 && _i <= limit50) || (step50 < 0 && _i >= limit50); _i += step50) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 73;BA.debugLine="If i > 0 Then sb.Append(\"&\")";
Debug.ShouldStop(256);
if (_i>0) { 
_sb.Append("&");};
 BA.debugLineNum = 74;BA.debugLine="sb.Append(su.EncodeUrl(Parameters(i), \"UTF8\")).Append(\"=\")";
Debug.ShouldStop(512);
_sb.Append(_su.EncodeUrl(_parameters[_i],"UTF8")).Append("=");
 BA.debugLineNum = 75;BA.debugLine="sb.Append(su.EncodeUrl(Parameters(i + 1), \"UTF8\"))";
Debug.ShouldStop(1024);
_sb.Append(_su.EncodeUrl(_parameters[(int)(_i+1)],"UTF8"));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 77;BA.debugLine="req.InitializeGet(sb.ToString)";
Debug.ShouldStop(4096);
_req.InitializeGet(_sb.ToString());
 BA.debugLineNum = 78;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", Me)";
Debug.ShouldStop(8192);
__c.CallSubDelayed2(getActivityBA(),(Object)(_httputils2service.getObject()),"SubmitJob",this);
 BA.debugLineNum = 79;BA.debugLine="End Sub";
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
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _getbitmap() throws Exception{
		Debug.PushSubsStack("GetBitmap (httpjob) ","httpjob",10,ba,this);
try {
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 BA.debugLineNum = 113;BA.debugLine="Public Sub GetBitmap As Bitmap";
Debug.ShouldStop(65536);
 BA.debugLineNum = 114;BA.debugLine="Dim b As Bitmap";
Debug.ShouldStop(131072);
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();Debug.locals.put("b", _b);
 BA.debugLineNum = 115;BA.debugLine="b = LoadBitmap(HttpUtils2Service.TempFolder, taskId)";
Debug.ShouldStop(262144);
_b = __c.LoadBitmap(_httputils2service._tempfolder,_taskid);Debug.locals.put("b", _b);
 BA.debugLineNum = 116;BA.debugLine="Return b";
Debug.ShouldStop(524288);
if (true) return _b;
 BA.debugLineNum = 117;BA.debugLine="End Sub";
Debug.ShouldStop(1048576);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public anywheresoftware.b4a.objects.streams.File.InputStreamWrapper  _getinputstream() throws Exception{
		Debug.PushSubsStack("GetInputStream (httpjob) ","httpjob",10,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
 BA.debugLineNum = 119;BA.debugLine="Public Sub GetInputStream As InputStream";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 120;BA.debugLine="Dim In As InputStream";
Debug.ShouldStop(8388608);
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("In", _in);
 BA.debugLineNum = 121;BA.debugLine="In = File.OpenInput(HttpUtils2Service.TempFolder, taskId)";
Debug.ShouldStop(16777216);
_in = __c.File.OpenInput(_httputils2service._tempfolder,_taskid);Debug.locals.put("In", _in);
 BA.debugLineNum = 122;BA.debugLine="Return In";
Debug.ShouldStop(33554432);
if (true) return _in;
 BA.debugLineNum = 123;BA.debugLine="End Sub";
Debug.ShouldStop(67108864);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public anywheresoftware.b4a.http.HttpClientWrapper.HttpUriRequestWrapper  _getrequest() throws Exception{
		Debug.PushSubsStack("GetRequest (httpjob) ","httpjob",10,ba,this);
try {
 BA.debugLineNum = 82;BA.debugLine="Public Sub GetRequest As HttpRequest";
Debug.ShouldStop(131072);
 BA.debugLineNum = 83;BA.debugLine="Return req";
Debug.ShouldStop(262144);
if (true) return _req;
 BA.debugLineNum = 84;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public String  _getstring() throws Exception{
		Debug.PushSubsStack("GetString (httpjob) ","httpjob",10,ba,this);
try {
 BA.debugLineNum = 98;BA.debugLine="Public Sub GetString As String";
Debug.ShouldStop(2);
 BA.debugLineNum = 99;BA.debugLine="Return GetString2(\"UTF8\")";
Debug.ShouldStop(4);
if (true) return _getstring2("UTF8");
 BA.debugLineNum = 100;BA.debugLine="End Sub";
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
public String  _getstring2(String _encoding) throws Exception{
		Debug.PushSubsStack("GetString2 (httpjob) ","httpjob",10,ba,this);
try {
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tr = null;
String _res = "";
Debug.locals.put("Encoding", _encoding);
 BA.debugLineNum = 103;BA.debugLine="Public Sub GetString2(Encoding As String) As String";
Debug.ShouldStop(64);
 BA.debugLineNum = 104;BA.debugLine="Dim tr As TextReader";
Debug.ShouldStop(128);
_tr = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();Debug.locals.put("tr", _tr);
 BA.debugLineNum = 105;BA.debugLine="tr.Initialize2(File.OpenInput(HttpUtils2Service.TempFolder, taskId), Encoding)";
Debug.ShouldStop(256);
_tr.Initialize2((java.io.InputStream)(__c.File.OpenInput(_httputils2service._tempfolder,_taskid).getObject()),_encoding);
 BA.debugLineNum = 106;BA.debugLine="Dim res As String";
Debug.ShouldStop(512);
_res = "";Debug.locals.put("res", _res);
 BA.debugLineNum = 107;BA.debugLine="res = tr.ReadAll";
Debug.ShouldStop(1024);
_res = _tr.ReadAll();Debug.locals.put("res", _res);
 BA.debugLineNum = 108;BA.debugLine="tr.Close";
Debug.ShouldStop(2048);
_tr.Close();
 BA.debugLineNum = 109;BA.debugLine="Return res";
Debug.ShouldStop(4096);
if (true) return _res;
 BA.debugLineNum = 110;BA.debugLine="End Sub";
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
public String  _initialize(anywheresoftware.b4a.BA _ba,String _name,Object _targetmodule) throws Exception{
innerInitialize(_ba);
		Debug.PushSubsStack("Initialize (httpjob) ","httpjob",10,ba,this);
try {
Debug.locals.put("ba", _ba);
Debug.locals.put("Name", _name);
Debug.locals.put("TargetModule", _targetmodule);
 BA.debugLineNum = 17;BA.debugLine="Public Sub Initialize (Name As String, TargetModule As Object)";
Debug.ShouldStop(65536);
 BA.debugLineNum = 18;BA.debugLine="JobName = Name";
Debug.ShouldStop(131072);
_jobname = _name;
 BA.debugLineNum = 19;BA.debugLine="target = TargetModule";
Debug.ShouldStop(262144);
_target = _targetmodule;
 BA.debugLineNum = 20;BA.debugLine="End Sub";
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
public String  _postbytes(String _link,byte[] _data) throws Exception{
		Debug.PushSubsStack("PostBytes (httpjob) ","httpjob",10,ba,this);
try {
Debug.locals.put("Link", _link);
Debug.locals.put("Data", _data);
 BA.debugLineNum = 27;BA.debugLine="Public Sub PostBytes(Link As String, Data() As Byte)";
Debug.ShouldStop(67108864);
 BA.debugLineNum = 28;BA.debugLine="req.InitializePost2(Link, Data)";
Debug.ShouldStop(134217728);
_req.InitializePost2(_link,_data);
 BA.debugLineNum = 29;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", Me)";
Debug.ShouldStop(268435456);
__c.CallSubDelayed2(getActivityBA(),(Object)(_httputils2service.getObject()),"SubmitJob",this);
 BA.debugLineNum = 30;BA.debugLine="End Sub";
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
public String  _postfile(String _link,String _dir,String _filename) throws Exception{
		Debug.PushSubsStack("PostFile (httpjob) ","httpjob",10,ba,this);
try {
int _length = 0;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
Debug.locals.put("Link", _link);
Debug.locals.put("Dir", _dir);
Debug.locals.put("FileName", _filename);
 BA.debugLineNum = 34;BA.debugLine="Public Sub PostFile(Link As String, Dir As String, FileName As String)";
Debug.ShouldStop(2);
 BA.debugLineNum = 35;BA.debugLine="Dim length As Int";
Debug.ShouldStop(4);
_length = 0;Debug.locals.put("length", _length);
 BA.debugLineNum = 36;BA.debugLine="If Dir = File.DirAssets Then";
Debug.ShouldStop(8);
if ((_dir).equals(__c.File.getDirAssets())) { 
 BA.debugLineNum = 37;BA.debugLine="Log(\"Cannot send files from the assets folder.\")";
Debug.ShouldStop(16);
__c.Log("Cannot send files from the assets folder.");
 BA.debugLineNum = 38;BA.debugLine="Return";
Debug.ShouldStop(32);
if (true) return "";
 };
 BA.debugLineNum = 40;BA.debugLine="length = File.Size(Dir, FileName)";
Debug.ShouldStop(128);
_length = (int)(__c.File.Size(_dir,_filename));Debug.locals.put("length", _length);
 BA.debugLineNum = 41;BA.debugLine="Dim In As InputStream";
Debug.ShouldStop(256);
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();Debug.locals.put("In", _in);
 BA.debugLineNum = 42;BA.debugLine="In = File.OpenInput(Dir, FileName)";
Debug.ShouldStop(512);
_in = __c.File.OpenInput(_dir,_filename);Debug.locals.put("In", _in);
 BA.debugLineNum = 43;BA.debugLine="If length < 1000000 Then '1mb";
Debug.ShouldStop(1024);
if (_length<1000000) { 
 BA.debugLineNum = 46;BA.debugLine="Dim out As OutputStream";
Debug.ShouldStop(8192);
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();Debug.locals.put("out", _out);
 BA.debugLineNum = 47;BA.debugLine="out.InitializeToBytesArray(length)";
Debug.ShouldStop(16384);
_out.InitializeToBytesArray(_length);
 BA.debugLineNum = 48;BA.debugLine="File.Copy2(In, out)";
Debug.ShouldStop(32768);
__c.File.Copy2((java.io.InputStream)(_in.getObject()),(java.io.OutputStream)(_out.getObject()));
 BA.debugLineNum = 49;BA.debugLine="PostBytes(Link, out.ToBytesArray)";
Debug.ShouldStop(65536);
_postbytes(_link,_out.ToBytesArray());
 }else {
 BA.debugLineNum = 51;BA.debugLine="req.InitializePost(Link, In, length)";
Debug.ShouldStop(262144);
_req.InitializePost(_link,(java.io.InputStream)(_in.getObject()),_length);
 BA.debugLineNum = 52;BA.debugLine="CallSubDelayed2(HttpUtils2Service, \"SubmitJob\", Me)";
Debug.ShouldStop(524288);
__c.CallSubDelayed2(getActivityBA(),(Object)(_httputils2service.getObject()),"SubmitJob",this);
 };
 BA.debugLineNum = 54;BA.debugLine="End Sub";
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
public String  _poststring(String _link,String _text) throws Exception{
		Debug.PushSubsStack("PostString (httpjob) ","httpjob",10,ba,this);
try {
Debug.locals.put("Link", _link);
Debug.locals.put("Text", _text);
 BA.debugLineNum = 22;BA.debugLine="Public Sub PostString(Link As String, Text As String)";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 23;BA.debugLine="PostBytes(Link, Text.GetBytes(\"UTF8\"))";
Debug.ShouldStop(4194304);
_postbytes(_link,_text.getBytes("UTF8"));
 BA.debugLineNum = 24;BA.debugLine="End Sub";
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
public String  _release() throws Exception{
		Debug.PushSubsStack("Release (httpjob) ","httpjob",10,ba,this);
try {
 BA.debugLineNum = 93;BA.debugLine="Public Sub Release";
Debug.ShouldStop(268435456);
 BA.debugLineNum = 94;BA.debugLine="File.Delete(HttpUtils2Service.TempFolder, taskId)";
Debug.ShouldStop(536870912);
__c.File.Delete(_httputils2service._tempfolder,_taskid);
 BA.debugLineNum = 95;BA.debugLine="End Sub";
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
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
ba.sharedProcessBA.sender = sender;
return BA.SubDelegator.SubNotFound;
}
  public Object[] GetGlobals() {
		return new Object[] {"JobName",_jobname,"Success",_success,"Username",_username,"Password",_password,"ErrorMessage",_errormessage,"target",_target,"taskId",_taskid,"req",_req,"Tag",_tag,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
