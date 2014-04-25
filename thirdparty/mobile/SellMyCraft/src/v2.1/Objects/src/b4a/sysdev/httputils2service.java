package b4a.sysdev;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class httputils2service extends android.app.Service {
	public static class httputils2service_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, httputils2service.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static httputils2service mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return httputils2service.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "b4a.sysdev", "b4a.sysdev.httputils2service");
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Service (httputils2service) Create **");
        processBA.raiseEvent(null, "service_create");
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		handleStart(intent);
    }
    @Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
    	handleStart(intent);
		return android.app.Service.START_NOT_STICKY;
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (httputils2service) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
        BA.LogInfo("** Service (httputils2service) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.http.HttpClientWrapper _hc = null;
public static anywheresoftware.b4a.objects.collections.Map _taskidtojob = null;
public static String _tempfolder = "";
public static int _taskcounter = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.dbutils _dbutils = null;
public b4a.sysdev.editartist _editartist = null;
public b4a.sysdev.help _help = null;
public static String  _completejob(int _taskid,boolean _success,String _errormessage) throws Exception{
		Debug.PushSubsStack("CompleteJob (httputils2service) ","httputils2service",11,processBA,mostCurrent);
try {
anywheresoftware.b4a.samples.httputils2.httpjob _job = null;
Debug.locals.put("TaskId", _taskid);
Debug.locals.put("success", _success);
Debug.locals.put("errorMessage", _errormessage);
 BA.debugLineNum = 64;BA.debugLine="Sub CompleteJob(TaskId As Int, success As Boolean, errorMessage As String)";
Debug.ShouldStop(-2147483648);
 BA.debugLineNum = 65;BA.debugLine="Dim job As HttpJob";
Debug.ShouldStop(1);
_job = new anywheresoftware.b4a.samples.httputils2.httpjob();Debug.locals.put("job", _job);
 BA.debugLineNum = 66;BA.debugLine="job = TaskIdToJob.Get(TaskId)";
Debug.ShouldStop(2);
_job = (anywheresoftware.b4a.samples.httputils2.httpjob)(_taskidtojob.Get((Object)(_taskid)));Debug.locals.put("job", _job);
 BA.debugLineNum = 67;BA.debugLine="TaskIdToJob.Remove(TaskId)";
Debug.ShouldStop(4);
_taskidtojob.Remove((Object)(_taskid));
 BA.debugLineNum = 68;BA.debugLine="job.success = success";
Debug.ShouldStop(8);
_job._success = _success;Debug.locals.put("job", _job);
 BA.debugLineNum = 69;BA.debugLine="job.errorMessage = errorMessage";
Debug.ShouldStop(16);
_job._errormessage = _errormessage;Debug.locals.put("job", _job);
 BA.debugLineNum = 70;BA.debugLine="job.Complete(TaskId)";
Debug.ShouldStop(32);
_job._complete(_taskid);
 BA.debugLineNum = 71;BA.debugLine="End Sub";
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
public static String  _hc_responseerror(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,String _reason,int _statuscode,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseError (httputils2service) ","httputils2service",11,processBA,mostCurrent);
try {
Debug.locals.put("Response", _response);
Debug.locals.put("Reason", _reason);
Debug.locals.put("StatusCode", _statuscode);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 52;BA.debugLine="Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)";
Debug.ShouldStop(524288);
 BA.debugLineNum = 53;BA.debugLine="If Response <> Null Then";
Debug.ShouldStop(1048576);
if (_response!= null) { 
 BA.debugLineNum = 54;BA.debugLine="Try";
Debug.ShouldStop(2097152);
try { BA.debugLineNum = 55;BA.debugLine="Log(Response.GetString(\"UTF8\"))";
Debug.ShouldStop(4194304);
anywheresoftware.b4a.keywords.Common.Log(_response.GetString("UTF8"));
 } 
       catch (Exception e40) {
			processBA.setLastException(e40); BA.debugLineNum = 57;BA.debugLine="Log(\"Failed to read error message.\")";
Debug.ShouldStop(16777216);
anywheresoftware.b4a.keywords.Common.Log("Failed to read error message.");
 };
 BA.debugLineNum = 59;BA.debugLine="Response.Release";
Debug.ShouldStop(67108864);
_response.Release();
 };
 BA.debugLineNum = 61;BA.debugLine="CompleteJob(TaskId, False, Reason)";
Debug.ShouldStop(268435456);
_completejob(_taskid,anywheresoftware.b4a.keywords.Common.False,_reason);
 BA.debugLineNum = 62;BA.debugLine="End Sub";
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
public static String  _hc_responsesuccess(anywheresoftware.b4a.http.HttpClientWrapper.HttpResponeWrapper _response,int _taskid) throws Exception{
		Debug.PushSubsStack("hc_ResponseSuccess (httputils2service) ","httputils2service",11,processBA,mostCurrent);
try {
Debug.locals.put("Response", _response);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 39;BA.debugLine="Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)";
Debug.ShouldStop(64);
 BA.debugLineNum = 40;BA.debugLine="Response.GetAsynchronously(\"response\", File.OpenOutput(TempFolder, TaskId, False), _ 		True, TaskId)";
Debug.ShouldStop(128);
_response.GetAsynchronously(processBA,"response",(java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(_tempfolder,BA.NumberToString(_taskid),anywheresoftware.b4a.keywords.Common.False).getObject()),anywheresoftware.b4a.keywords.Common.True,_taskid);
 BA.debugLineNum = 42;BA.debugLine="End Sub";
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
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Private hc As HttpClient";
_hc = new anywheresoftware.b4a.http.HttpClientWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Private TaskIdToJob As Map";
_taskidtojob = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 10;BA.debugLine="Public TempFolder As String";
_tempfolder = "";
 //BA.debugLineNum = 11;BA.debugLine="Private taskCounter As Int";
_taskcounter = 0;
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _response_streamfinish(boolean _success,int _taskid) throws Exception{
		Debug.PushSubsStack("Response_StreamFinish (httputils2service) ","httputils2service",11,processBA,mostCurrent);
try {
Debug.locals.put("Success", _success);
Debug.locals.put("TaskId", _taskid);
 BA.debugLineNum = 44;BA.debugLine="Sub Response_StreamFinish (Success As Boolean, TaskId As Int)";
Debug.ShouldStop(2048);
 BA.debugLineNum = 45;BA.debugLine="If Success Then";
Debug.ShouldStop(4096);
if (_success) { 
 BA.debugLineNum = 46;BA.debugLine="CompleteJob(TaskId, Success, \"\")";
Debug.ShouldStop(8192);
_completejob(_taskid,_success,"");
 }else {
 BA.debugLineNum = 48;BA.debugLine="CompleteJob(TaskId, Success, LastException.Message)";
Debug.ShouldStop(32768);
_completejob(_taskid,_success,anywheresoftware.b4a.keywords.Common.LastException(processBA).getMessage());
 };
 BA.debugLineNum = 50;BA.debugLine="End Sub";
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
public static String  _service_create() throws Exception{
		Debug.PushSubsStack("Service_Create (httputils2service) ","httputils2service",11,processBA,mostCurrent);
try {
 BA.debugLineNum = 14;BA.debugLine="Sub Service_Create";
Debug.ShouldStop(8192);
 BA.debugLineNum = 15;BA.debugLine="TempFolder = File.DirInternalCache";
Debug.ShouldStop(16384);
_tempfolder = anywheresoftware.b4a.keywords.Common.File.getDirInternalCache();
 BA.debugLineNum = 16;BA.debugLine="hc.Initialize(\"hc\")";
Debug.ShouldStop(32768);
Debug.DebugWarningEngine.CheckInitialize(_hc);_hc.Initialize("hc");
 BA.debugLineNum = 17;BA.debugLine="TaskIdToJob.Initialize";
Debug.ShouldStop(65536);
_taskidtojob.Initialize();
 BA.debugLineNum = 18;BA.debugLine="End Sub";
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
public static String  _service_destroy() throws Exception{
		Debug.PushSubsStack("Service_Destroy (httputils2service) ","httputils2service",11,processBA,mostCurrent);
try {
 BA.debugLineNum = 24;BA.debugLine="Sub Service_Destroy";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 26;BA.debugLine="End Sub";
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
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
		Debug.PushSubsStack("Service_Start (httputils2service) ","httputils2service",11,processBA,mostCurrent);
try {
Debug.locals.put("StartingIntent", _startingintent);
 BA.debugLineNum = 20;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
Debug.ShouldStop(524288);
 BA.debugLineNum = 22;BA.debugLine="End Sub";
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
public static int  _submitjob(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
		Debug.PushSubsStack("SubmitJob (httputils2service) ","httputils2service",11,processBA,mostCurrent);
try {
Debug.locals.put("job", _job);
 BA.debugLineNum = 28;BA.debugLine="Public Sub SubmitJob(job As HttpJob) As Int";
Debug.ShouldStop(134217728);
 BA.debugLineNum = 29;BA.debugLine="taskCounter = taskCounter + 1";
Debug.ShouldStop(268435456);
_taskcounter = (int)(_taskcounter+1);
 BA.debugLineNum = 30;BA.debugLine="TaskIdToJob.Put(taskCounter, job)";
Debug.ShouldStop(536870912);
_taskidtojob.Put((Object)(_taskcounter),(Object)(_job));
 BA.debugLineNum = 31;BA.debugLine="If job.Username <> \"\" AND job.Password <> \"\" Then";
Debug.ShouldStop(1073741824);
if ((_job._username).equals("") == false && (_job._password).equals("") == false) { 
 BA.debugLineNum = 32;BA.debugLine="hc.ExecuteCredentials(job.GetRequest, taskCounter, job.Username, job.Password)";
Debug.ShouldStop(-2147483648);
_hc.ExecuteCredentials(processBA,_job._getrequest(),_taskcounter,_job._username,_job._password);
 }else {
 BA.debugLineNum = 34;BA.debugLine="hc.Execute(job.GetRequest, taskCounter)";
Debug.ShouldStop(2);
_hc.Execute(processBA,_job._getrequest(),_taskcounter);
 };
 BA.debugLineNum = 36;BA.debugLine="Return taskCounter";
Debug.ShouldStop(8);
if (true) return _taskcounter;
 BA.debugLineNum = 37;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return 0;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
  public Object[] GetGlobals() {
		return new Object[] {"Service",_service,"hc",_hc,"TaskIdToJob",_taskidtojob,"TempFolder",_tempfolder,"taskCounter",_taskcounter,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
