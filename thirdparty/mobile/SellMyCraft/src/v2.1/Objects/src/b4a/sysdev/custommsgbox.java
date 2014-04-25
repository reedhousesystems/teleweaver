package b4a.sysdev;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class custommsgbox extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.sysdev.custommsgbox");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
        }
        _class_globals();
    }


 public anywheresoftware.b4a.keywords.Common __c = null;
public Object _msgmodule = null;
public anywheresoftware.b4a.objects.PanelWrapper _backpanel = null;
public String _msgorientation = "";
public int _msgnumberofbuttons = 0;
public anywheresoftware.b4a.objects.ImageViewWrapper _mbicon = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel = null;
public anywheresoftware.b4a.objects.PanelWrapper _shadow = null;
public anywheresoftware.b4a.objects.LabelWrapper _title = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _msgscrollview = null;
public anywheresoftware.b4a.objects.LabelWrapper _message = null;
public anywheresoftware.b4a.objects.PanelWrapper _yesbuttonpanel = null;
public anywheresoftware.b4a.objects.PanelWrapper _nobuttonpanel = null;
public anywheresoftware.b4a.objects.PanelWrapper _cancelbuttonpanel = null;
public anywheresoftware.b4a.objects.LabelWrapper _yesbuttoncaption = null;
public anywheresoftware.b4a.objects.LabelWrapper _nobuttoncaption = null;
public anywheresoftware.b4a.objects.LabelWrapper _cancelbuttoncaption = null;
public String _msgboxevent = "";
public String _buttonselected = "";
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
public String  _backpanel_touch(int _action,float _x,float _y) throws Exception{
		Debug.PushSubsStack("BackPanel_Touch (custommsgbox) ","custommsgbox",8,ba,this);
try {
Debug.locals.put("Action", _action);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
 BA.debugLineNum = 327;BA.debugLine="Private Sub BackPanel_Touch(Action As Int, X As Float, Y As Float)";
Debug.ShouldStop(64);
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
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private MsgModule As Object";
_msgmodule = new Object();
 //BA.debugLineNum = 10;BA.debugLine="Private BackPanel As Panel";
_backpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private MsgOrientation As String";
_msgorientation = "";
 //BA.debugLineNum = 12;BA.debugLine="Private MsgNumberOfButtons As Int";
_msgnumberofbuttons = 0;
 //BA.debugLineNum = 14;BA.debugLine="Private mbIcon As ImageView";
_mbicon = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim Panel As Panel";
_panel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private Shadow As Panel";
_shadow = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim Title As Label";
_title = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private MsgScrollView As ScrollView";
_msgscrollview = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim Message As Label";
_message = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim YesButtonPanel As Panel";
_yesbuttonpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim NoButtonPanel As Panel";
_nobuttonpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim CancelButtonPanel As Panel";
_cancelbuttonpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim YesButtonCaption As Label";
_yesbuttoncaption = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim NoButtonCaption As Label";
_nobuttoncaption = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim CancelButtonCaption As Label";
_cancelbuttoncaption = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private MsgBoxEvent As String";
_msgboxevent = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim ButtonSelected As String";
_buttonselected = "";
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ActivityWrapper _activity,Object _module,String _msgboxname,String _orientation,int _numberofbuttons,double _width,double _height,anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _icon) throws Exception{
innerInitialize(_ba);
		Debug.PushSubsStack("Initialize (custommsgbox) ","custommsgbox",8,ba,this);
try {
String _delta = "";
String _rate = "";
String _scale = "";
String _buttonsize = "";
Debug.locals.put("ba", _ba);
Debug.locals.put("Activity", _activity);
Debug.locals.put("Module", _module);
Debug.locals.put("MsgBoxName", _msgboxname);
Debug.locals.put("Orientation", _orientation);
Debug.locals.put("NumberOfButtons", _numberofbuttons);
Debug.locals.put("Width", _width);
Debug.locals.put("Height", _height);
Debug.locals.put("Icon", _icon);
 BA.debugLineNum = 42;BA.debugLine="Public Sub Initialize(Activity As Activity, Module As Object, MsgBoxName As String, Orientation As String, NumberOfButtons As Int, Width As Double, Height As Double, Icon As Bitmap)";
Debug.ShouldStop(512);
 BA.debugLineNum = 44;BA.debugLine="MsgModule = Module";
Debug.ShouldStop(2048);
_msgmodule = _module;
 BA.debugLineNum = 45;BA.debugLine="MsgOrientation = Orientation";
Debug.ShouldStop(4096);
_msgorientation = _orientation;
 BA.debugLineNum = 46;BA.debugLine="MsgNumberOfButtons = NumberOfButtons";
Debug.ShouldStop(8192);
_msgnumberofbuttons = _numberofbuttons;
 BA.debugLineNum = 48;BA.debugLine="Delta = ((100%x + 100%y) / (320dip + 480dip) - 1)";
Debug.ShouldStop(32768);
_delta = BA.NumberToString(((__c.PerXToCurrent((float)(100),ba)+__c.PerYToCurrent((float)(100),ba))/(double)(__c.DipToCurrent((int)(320))+__c.DipToCurrent((int)(480)))-1));Debug.locals.put("delta", _delta);
 BA.debugLineNum = 49;BA.debugLine="Rate = 0.3";
Debug.ShouldStop(65536);
_rate = BA.NumberToString(0.3);Debug.locals.put("rate", _rate);
 BA.debugLineNum = 50;BA.debugLine="Scale = Rate * Delta	+ 1";
Debug.ShouldStop(131072);
_scale = BA.NumberToString((double)(Double.parseDouble(_rate))*(double)(Double.parseDouble(_delta))+1);Debug.locals.put("scale", _scale);
 BA.debugLineNum = 52;BA.debugLine="MsgBoxEvent = MsgBoxName";
Debug.ShouldStop(524288);
_msgboxevent = _msgboxname;
 BA.debugLineNum = 54;BA.debugLine="BackPanel.Initialize(\"BackPanel\")";
Debug.ShouldStop(2097152);
_backpanel.Initialize(ba,"BackPanel");
 BA.debugLineNum = 56;BA.debugLine="Panel.Initialize(\"\")";
Debug.ShouldStop(8388608);
_panel.Initialize(ba,"");
 BA.debugLineNum = 57;BA.debugLine="Shadow.Initialize(\"\")";
Debug.ShouldStop(16777216);
_shadow.Initialize(ba,"");
 BA.debugLineNum = 58;BA.debugLine="mbIcon.Initialize(\"\")";
Debug.ShouldStop(33554432);
_mbicon.Initialize(ba,"");
 BA.debugLineNum = 59;BA.debugLine="Title.Initialize(\"\")";
Debug.ShouldStop(67108864);
_title.Initialize(ba,"");
 BA.debugLineNum = 60;BA.debugLine="MsgScrollView.Initialize(0dip)";
Debug.ShouldStop(134217728);
_msgscrollview.Initialize(ba,__c.DipToCurrent((int)(0)));
 BA.debugLineNum = 61;BA.debugLine="Message.Initialize(\"\")";
Debug.ShouldStop(268435456);
_message.Initialize(ba,"");
 BA.debugLineNum = 62;BA.debugLine="YesButtonPanel.Initialize(\"PressedButton\")";
Debug.ShouldStop(536870912);
_yesbuttonpanel.Initialize(ba,"PressedButton");
 BA.debugLineNum = 63;BA.debugLine="NoButtonPanel.Initialize(\"PressedButton\")";
Debug.ShouldStop(1073741824);
_nobuttonpanel.Initialize(ba,"PressedButton");
 BA.debugLineNum = 64;BA.debugLine="CancelButtonPanel.Initialize(\"PressedButton\")";
Debug.ShouldStop(-2147483648);
_cancelbuttonpanel.Initialize(ba,"PressedButton");
 BA.debugLineNum = 65;BA.debugLine="YesButtonCaption.Initialize(\"\")";
Debug.ShouldStop(1);
_yesbuttoncaption.Initialize(ba,"");
 BA.debugLineNum = 66;BA.debugLine="NoButtonCaption.Initialize(\"\")";
Debug.ShouldStop(2);
_nobuttoncaption.Initialize(ba,"");
 BA.debugLineNum = 67;BA.debugLine="CancelButtonCaption.Initialize(\"\")";
Debug.ShouldStop(4);
_cancelbuttoncaption.Initialize(ba,"");
 BA.debugLineNum = 69;BA.debugLine="Activity.AddView(BackPanel, 0dip, 0dip, 100%x, 100%y)";
Debug.ShouldStop(16);
_activity.AddView((android.view.View)(_backpanel.getObject()),__c.DipToCurrent((int)(0)),__c.DipToCurrent((int)(0)),__c.PerXToCurrent((float)(100),ba),__c.PerYToCurrent((float)(100),ba));
 BA.debugLineNum = 71;BA.debugLine="BackPanel.Color = Colors.ARGB(160, 0, 0, 0)";
Debug.ShouldStop(64);
_backpanel.setColor(__c.Colors.ARGB((int)(160),(int)(0),(int)(0),(int)(0)));
 BA.debugLineNum = 73;BA.debugLine="BackPanel.AddView(Panel, 0dip, 0dip, 100%x, 100%y)";
Debug.ShouldStop(256);
_backpanel.AddView((android.view.View)(_panel.getObject()),__c.DipToCurrent((int)(0)),__c.DipToCurrent((int)(0)),__c.PerXToCurrent((float)(100),ba),__c.PerYToCurrent((float)(100),ba));
 BA.debugLineNum = 75;BA.debugLine="Panel.Width = Width";
Debug.ShouldStop(1024);
_panel.setWidth((int)(_width));
 BA.debugLineNum = 76;BA.debugLine="Panel.Height = Height";
Debug.ShouldStop(2048);
_panel.setHeight((int)(_height));
 BA.debugLineNum = 77;BA.debugLine="Panel.Color = Colors.DarkGray";
Debug.ShouldStop(4096);
_panel.setColor(__c.Colors.DarkGray);
 BA.debugLineNum = 78;BA.debugLine="Panel.Left = (100%x - Panel.Width) / 2";
Debug.ShouldStop(8192);
_panel.setLeft((int)((__c.PerXToCurrent((float)(100),ba)-_panel.getWidth())/(double)2));
 BA.debugLineNum = 79;BA.debugLine="Panel.Top = (100%y - Panel.Height) / 2";
Debug.ShouldStop(16384);
_panel.setTop((int)((__c.PerYToCurrent((float)(100),ba)-_panel.getHeight())/(double)2));
 BA.debugLineNum = 80;BA.debugLine="BackPanel.AddView(Shadow, Panel.Left + 5dip, Panel.Top + 5dip, Panel.Width, Panel.Height)";
Debug.ShouldStop(32768);
_backpanel.AddView((android.view.View)(_shadow.getObject()),(int)(_panel.getLeft()+__c.DipToCurrent((int)(5))),(int)(_panel.getTop()+__c.DipToCurrent((int)(5))),_panel.getWidth(),_panel.getHeight());
 BA.debugLineNum = 81;BA.debugLine="Shadow.SendToBack";
Debug.ShouldStop(65536);
_shadow.SendToBack();
 BA.debugLineNum = 82;BA.debugLine="Panel.AddView(mbIcon, 5dip, 5dip, 50dip, 50dip)";
Debug.ShouldStop(131072);
_panel.AddView((android.view.View)(_mbicon.getObject()),__c.DipToCurrent((int)(5)),__c.DipToCurrent((int)(5)),__c.DipToCurrent((int)(50)),__c.DipToCurrent((int)(50)));
 BA.debugLineNum = 83;BA.debugLine="Panel.AddView(Title, 60dip, 0dip, Panel.Width, 60dip)";
Debug.ShouldStop(262144);
_panel.AddView((android.view.View)(_title.getObject()),__c.DipToCurrent((int)(60)),__c.DipToCurrent((int)(0)),_panel.getWidth(),__c.DipToCurrent((int)(60)));
 BA.debugLineNum = 84;BA.debugLine="Panel.AddView(MsgScrollView, 5dip, 70dip, Panel.Width, 60dip)";
Debug.ShouldStop(524288);
_panel.AddView((android.view.View)(_msgscrollview.getObject()),__c.DipToCurrent((int)(5)),__c.DipToCurrent((int)(70)),_panel.getWidth(),__c.DipToCurrent((int)(60)));
 BA.debugLineNum = 86;BA.debugLine="Panel.AddView(YesButtonPanel, 0dip, Panel.Height - 50dip, 50dip, 50dip)";
Debug.ShouldStop(2097152);
_panel.AddView((android.view.View)(_yesbuttonpanel.getObject()),__c.DipToCurrent((int)(0)),(int)(_panel.getHeight()-__c.DipToCurrent((int)(50))),__c.DipToCurrent((int)(50)),__c.DipToCurrent((int)(50)));
 BA.debugLineNum = 87;BA.debugLine="Panel.AddView(NoButtonPanel, 0dip, Panel.Height - 50dip, 50dip, 50dip)";
Debug.ShouldStop(4194304);
_panel.AddView((android.view.View)(_nobuttonpanel.getObject()),__c.DipToCurrent((int)(0)),(int)(_panel.getHeight()-__c.DipToCurrent((int)(50))),__c.DipToCurrent((int)(50)),__c.DipToCurrent((int)(50)));
 BA.debugLineNum = 88;BA.debugLine="Panel.AddView(CancelButtonPanel, 0dip, Panel.Height - 50dip, 50dip, 50dip)";
Debug.ShouldStop(8388608);
_panel.AddView((android.view.View)(_cancelbuttonpanel.getObject()),__c.DipToCurrent((int)(0)),(int)(_panel.getHeight()-__c.DipToCurrent((int)(50))),__c.DipToCurrent((int)(50)),__c.DipToCurrent((int)(50)));
 BA.debugLineNum = 90;BA.debugLine="MsgScrollView.Panel.AddView(Message, 0dip, 0dip, MsgScrollView.Width, MsgScrollView.Height)";
Debug.ShouldStop(33554432);
_msgscrollview.getPanel().AddView((android.view.View)(_message.getObject()),__c.DipToCurrent((int)(0)),__c.DipToCurrent((int)(0)),_msgscrollview.getWidth(),_msgscrollview.getHeight());
 BA.debugLineNum = 92;BA.debugLine="Message.Height = YesButtonPanel.Top - Message.Top - 10dip";
Debug.ShouldStop(134217728);
_message.setHeight((int)(_yesbuttonpanel.getTop()-_message.getTop()-__c.DipToCurrent((int)(10))));
 BA.debugLineNum = 93;BA.debugLine="MsgScrollView.Height = YesButtonPanel.Top - MsgScrollView.Top - 10dip";
Debug.ShouldStop(268435456);
_msgscrollview.setHeight((int)(_yesbuttonpanel.getTop()-_msgscrollview.getTop()-__c.DipToCurrent((int)(10))));
 BA.debugLineNum = 94;BA.debugLine="MsgScrollView.Panel.Height = MsgScrollView.Height";
Debug.ShouldStop(536870912);
_msgscrollview.getPanel().setHeight(_msgscrollview.getHeight());
 BA.debugLineNum = 96;BA.debugLine="If Orientation.ToUpperCase = \"V\" Then";
Debug.ShouldStop(-2147483648);
if ((_orientation.toUpperCase()).equals("V")) { 
 BA.debugLineNum = 98;BA.debugLine="YesButtonPanel.Top = Panel.Height - (50dip * NumberOfButtons)";
Debug.ShouldStop(2);
_yesbuttonpanel.setTop((int)(_panel.getHeight()-(__c.DipToCurrent((int)(50))*_numberofbuttons)));
 BA.debugLineNum = 99;BA.debugLine="NoButtonPanel.Top = YesButtonPanel.Top + 52dip";
Debug.ShouldStop(4);
_nobuttonpanel.setTop((int)(_yesbuttonpanel.getTop()+__c.DipToCurrent((int)(52))));
 BA.debugLineNum = 100;BA.debugLine="CancelButtonPanel.Top = NoButtonPanel.Top + 52dip";
Debug.ShouldStop(8);
_cancelbuttonpanel.setTop((int)(_nobuttonpanel.getTop()+__c.DipToCurrent((int)(52))));
 };
 BA.debugLineNum = 104;BA.debugLine="If Icon.IsInitialized Then";
Debug.ShouldStop(128);
if (_icon.IsInitialized()) { 
 BA.debugLineNum = 106;BA.debugLine="mbIcon.SetBackgroundImage(Icon)";
Debug.ShouldStop(512);
_mbicon.SetBackgroundImage((android.graphics.Bitmap)(_icon.getObject()));
 }else {
 BA.debugLineNum = 110;BA.debugLine="Title.Text = Title.Text.Trim";
Debug.ShouldStop(8192);
_title.setText((Object)(_title.getText().trim()));
 BA.debugLineNum = 111;BA.debugLine="Title.Left = 5dip";
Debug.ShouldStop(16384);
_title.setLeft(__c.DipToCurrent((int)(5)));
 };
 BA.debugLineNum = 115;BA.debugLine="Title.Width = Panel.Width - Title.Left - 10dip";
Debug.ShouldStop(262144);
_title.setWidth((int)(_panel.getWidth()-_title.getLeft()-__c.DipToCurrent((int)(10))));
 BA.debugLineNum = 116;BA.debugLine="Title.Gravity = Gravity.CENTER_VERTICAL";
Debug.ShouldStop(524288);
_title.setGravity(__c.Gravity.CENTER_VERTICAL);
 BA.debugLineNum = 117;BA.debugLine="Title.TextColor = Colors.Cyan";
Debug.ShouldStop(1048576);
_title.setTextColor(__c.Colors.Cyan);
 BA.debugLineNum = 118;BA.debugLine="Title.TextSize = Title.TextSize * Scale + 4";
Debug.ShouldStop(2097152);
_title.setTextSize((float)(_title.getTextSize()*(double)(Double.parseDouble(_scale))+4));
 BA.debugLineNum = 120;BA.debugLine="Message.Width = Panel.Width - Message.Left - 10dip";
Debug.ShouldStop(8388608);
_message.setWidth((int)(_panel.getWidth()-_message.getLeft()-__c.DipToCurrent((int)(10))));
 BA.debugLineNum = 122;BA.debugLine="Message.TextColor = Colors.White";
Debug.ShouldStop(33554432);
_message.setTextColor(__c.Colors.White);
 BA.debugLineNum = 123;BA.debugLine="Message.TextSize = Message.TextSize * Scale + 1";
Debug.ShouldStop(67108864);
_message.setTextSize((float)(_message.getTextSize()*(double)(Double.parseDouble(_scale))+1));
 BA.debugLineNum = 125;BA.debugLine="If Orientation.ToUpperCase = \"V\" Then";
Debug.ShouldStop(268435456);
if ((_orientation.toUpperCase()).equals("V")) { 
 BA.debugLineNum = 127;BA.debugLine="ButtonSize = Panel.Width";
Debug.ShouldStop(1073741824);
_buttonsize = BA.NumberToString(_panel.getWidth());Debug.locals.put("buttonsize", _buttonsize);
 }else {
 BA.debugLineNum = 131;BA.debugLine="ButtonSize = (Panel.Width / NumberOfButtons) - 2";
Debug.ShouldStop(4);
_buttonsize = BA.NumberToString((_panel.getWidth()/(double)_numberofbuttons)-2);Debug.locals.put("buttonsize", _buttonsize);
 };
 BA.debugLineNum = 135;BA.debugLine="YesButtonPanel.Color = Colors.DarkGray";
Debug.ShouldStop(64);
_yesbuttonpanel.setColor(__c.Colors.DarkGray);
 BA.debugLineNum = 136;BA.debugLine="YesButtonPanel.Width = ButtonSize";
Debug.ShouldStop(128);
_yesbuttonpanel.setWidth((int)(Double.parseDouble(_buttonsize)));
 BA.debugLineNum = 137;BA.debugLine="YesButtonPanel.Left = 0dip";
Debug.ShouldStop(256);
_yesbuttonpanel.setLeft(__c.DipToCurrent((int)(0)));
 BA.debugLineNum = 138;BA.debugLine="YesButtonPanel.Tag = \"yes\"";
Debug.ShouldStop(512);
_yesbuttonpanel.setTag((Object)("yes"));
 BA.debugLineNum = 139;BA.debugLine="YesButtonPanel.AddView(YesButtonCaption, 0dip, 0dip, YesButtonPanel.Width, 30dip)";
Debug.ShouldStop(1024);
_yesbuttonpanel.AddView((android.view.View)(_yesbuttoncaption.getObject()),__c.DipToCurrent((int)(0)),__c.DipToCurrent((int)(0)),_yesbuttonpanel.getWidth(),__c.DipToCurrent((int)(30)));
 BA.debugLineNum = 141;BA.debugLine="YesButtonCaption.Gravity = Gravity.CENTER_HORIZONTAL";
Debug.ShouldStop(4096);
_yesbuttoncaption.setGravity(__c.Gravity.CENTER_HORIZONTAL);
 BA.debugLineNum = 142;BA.debugLine="YesButtonCaption.Top = ((YesButtonPanel.Height - YesButtonCaption.Height) / 2) + 5dip";
Debug.ShouldStop(8192);
_yesbuttoncaption.setTop((int)(((_yesbuttonpanel.getHeight()-_yesbuttoncaption.getHeight())/(double)2)+__c.DipToCurrent((int)(5))));
 BA.debugLineNum = 143;BA.debugLine="YesButtonCaption.TextColor = Colors.White";
Debug.ShouldStop(16384);
_yesbuttoncaption.setTextColor(__c.Colors.White);
 BA.debugLineNum = 144;BA.debugLine="YesButtonCaption.Text = \"Yes\"";
Debug.ShouldStop(32768);
_yesbuttoncaption.setText((Object)("Yes"));
 BA.debugLineNum = 146;BA.debugLine="NoButtonPanel.Color = Colors.DarkGray";
Debug.ShouldStop(131072);
_nobuttonpanel.setColor(__c.Colors.DarkGray);
 BA.debugLineNum = 147;BA.debugLine="NoButtonPanel.Width = ButtonSize";
Debug.ShouldStop(262144);
_nobuttonpanel.setWidth((int)(Double.parseDouble(_buttonsize)));
 BA.debugLineNum = 149;BA.debugLine="If Orientation.ToUpperCase = \"V\" Then";
Debug.ShouldStop(1048576);
if ((_orientation.toUpperCase()).equals("V")) { 
 BA.debugLineNum = 151;BA.debugLine="NoButtonPanel.Left = 0dip";
Debug.ShouldStop(4194304);
_nobuttonpanel.setLeft(__c.DipToCurrent((int)(0)));
 }else {
 BA.debugLineNum = 155;BA.debugLine="NoButtonPanel.Left = YesButtonPanel.Width + 4dip";
Debug.ShouldStop(67108864);
_nobuttonpanel.setLeft((int)(_yesbuttonpanel.getWidth()+__c.DipToCurrent((int)(4))));
 };
 BA.debugLineNum = 159;BA.debugLine="NoButtonPanel.Tag = \"no\"";
Debug.ShouldStop(1073741824);
_nobuttonpanel.setTag((Object)("no"));
 BA.debugLineNum = 160;BA.debugLine="NoButtonPanel.AddView(NoButtonCaption, 0dip, 0dip, NoButtonPanel.Width, 30dip)";
Debug.ShouldStop(-2147483648);
_nobuttonpanel.AddView((android.view.View)(_nobuttoncaption.getObject()),__c.DipToCurrent((int)(0)),__c.DipToCurrent((int)(0)),_nobuttonpanel.getWidth(),__c.DipToCurrent((int)(30)));
 BA.debugLineNum = 162;BA.debugLine="NoButtonCaption.Gravity = Gravity.CENTER_HORIZONTAL";
Debug.ShouldStop(2);
_nobuttoncaption.setGravity(__c.Gravity.CENTER_HORIZONTAL);
 BA.debugLineNum = 163;BA.debugLine="NoButtonCaption.Top = ((NoButtonPanel.Height - NoButtonCaption.Height) / 2) + 5dip";
Debug.ShouldStop(4);
_nobuttoncaption.setTop((int)(((_nobuttonpanel.getHeight()-_nobuttoncaption.getHeight())/(double)2)+__c.DipToCurrent((int)(5))));
 BA.debugLineNum = 164;BA.debugLine="NoButtonCaption.TextColor = Colors.White";
Debug.ShouldStop(8);
_nobuttoncaption.setTextColor(__c.Colors.White);
 BA.debugLineNum = 165;BA.debugLine="NoButtonCaption.Text = \"No\"";
Debug.ShouldStop(16);
_nobuttoncaption.setText((Object)("No"));
 BA.debugLineNum = 167;BA.debugLine="CancelButtonPanel.Color = Colors.DarkGray";
Debug.ShouldStop(64);
_cancelbuttonpanel.setColor(__c.Colors.DarkGray);
 BA.debugLineNum = 168;BA.debugLine="CancelButtonPanel.Width = ButtonSize";
Debug.ShouldStop(128);
_cancelbuttonpanel.setWidth((int)(Double.parseDouble(_buttonsize)));
 BA.debugLineNum = 170;BA.debugLine="If Orientation.ToUpperCase = \"V\" Then";
Debug.ShouldStop(512);
if ((_orientation.toUpperCase()).equals("V")) { 
 BA.debugLineNum = 172;BA.debugLine="CancelButtonPanel.Left = 0dip";
Debug.ShouldStop(2048);
_cancelbuttonpanel.setLeft(__c.DipToCurrent((int)(0)));
 }else {
 BA.debugLineNum = 176;BA.debugLine="CancelButtonPanel.Left = YesButtonPanel.Width + NoButtonPanel.Width + 8dip";
Debug.ShouldStop(32768);
_cancelbuttonpanel.setLeft((int)(_yesbuttonpanel.getWidth()+_nobuttonpanel.getWidth()+__c.DipToCurrent((int)(8))));
 };
 BA.debugLineNum = 180;BA.debugLine="CancelButtonPanel.Tag = \"cancel\"";
Debug.ShouldStop(524288);
_cancelbuttonpanel.setTag((Object)("cancel"));
 BA.debugLineNum = 181;BA.debugLine="CancelButtonPanel.AddView(CancelButtonCaption, 0dip, 0dip, CancelButtonPanel.Width, 30dip)";
Debug.ShouldStop(1048576);
_cancelbuttonpanel.AddView((android.view.View)(_cancelbuttoncaption.getObject()),__c.DipToCurrent((int)(0)),__c.DipToCurrent((int)(0)),_cancelbuttonpanel.getWidth(),__c.DipToCurrent((int)(30)));
 BA.debugLineNum = 183;BA.debugLine="CancelButtonCaption.Gravity = Gravity.CENTER_HORIZONTAL";
Debug.ShouldStop(4194304);
_cancelbuttoncaption.setGravity(__c.Gravity.CENTER_HORIZONTAL);
 BA.debugLineNum = 184;BA.debugLine="CancelButtonCaption.Top = ((CancelButtonPanel.Height - CancelButtonCaption.Height) / 2) + 5dip";
Debug.ShouldStop(8388608);
_cancelbuttoncaption.setTop((int)(((_cancelbuttonpanel.getHeight()-_cancelbuttoncaption.getHeight())/(double)2)+__c.DipToCurrent((int)(5))));
 BA.debugLineNum = 185;BA.debugLine="CancelButtonCaption.TextColor = Colors.White";
Debug.ShouldStop(16777216);
_cancelbuttoncaption.setTextColor(__c.Colors.White);
 BA.debugLineNum = 186;BA.debugLine="CancelButtonCaption.Text = \"Cancel\"";
Debug.ShouldStop(33554432);
_cancelbuttoncaption.setText((Object)("Cancel"));
 BA.debugLineNum = 188;BA.debugLine="ShowSeparators(Colors.Cyan, Colors.Gray)";
Debug.ShouldStop(134217728);
_showseparators(__c.Colors.Cyan,__c.Colors.Gray);
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
public String  _pressedbutton_touch(int _action,float _x,float _y) throws Exception{
		Debug.PushSubsStack("PressedButton_Touch (custommsgbox) ","custommsgbox",8,ba,this);
try {
anywheresoftware.b4a.objects.PanelWrapper _pressed = null;
Debug.locals.put("Action", _action);
Debug.locals.put("X", _x);
Debug.locals.put("Y", _y);
 BA.debugLineNum = 297;BA.debugLine="Private Sub PressedButton_Touch(Action As Int, X As Float, Y As Float)";
Debug.ShouldStop(256);
 BA.debugLineNum = 299;BA.debugLine="Dim Pressed As Panel";
Debug.ShouldStop(1024);
_pressed = new anywheresoftware.b4a.objects.PanelWrapper();Debug.locals.put("Pressed", _pressed);
 BA.debugLineNum = 301;BA.debugLine="Pressed = Sender";
Debug.ShouldStop(4096);
_pressed.setObject((android.view.ViewGroup)(__c.Sender(ba)));
 BA.debugLineNum = 303;BA.debugLine="If Action = 0 Then";
Debug.ShouldStop(16384);
if (_action==0) { 
 BA.debugLineNum = 305;BA.debugLine="Pressed.Color = Colors.ARGB(255, 0, 140, 140)";
Debug.ShouldStop(65536);
_pressed.setColor(__c.Colors.ARGB((int)(255),(int)(0),(int)(140),(int)(140)));
 };
 BA.debugLineNum = 309;BA.debugLine="If Action = 1 Then";
Debug.ShouldStop(1048576);
if (_action==1) { 
 BA.debugLineNum = 311;BA.debugLine="Pressed.Color = Colors.DarkGray";
Debug.ShouldStop(4194304);
_pressed.setColor(__c.Colors.DarkGray);
 BA.debugLineNum = 313;BA.debugLine="ButtonSelected = Pressed.Tag";
Debug.ShouldStop(16777216);
_buttonselected = String.valueOf(_pressed.getTag());
 BA.debugLineNum = 315;BA.debugLine="BackPanel.Visible = False";
Debug.ShouldStop(67108864);
_backpanel.setVisible(__c.False);
 BA.debugLineNum = 317;BA.debugLine="If SubExists(MsgModule, MsgBoxEvent & \"_Click\") Then";
Debug.ShouldStop(268435456);
if (__c.SubExists(ba,_msgmodule,_msgboxevent+"_Click")) { 
 BA.debugLineNum = 319;BA.debugLine="CallSubDelayed(MsgModule, MsgBoxEvent & \"_Click\")";
Debug.ShouldStop(1073741824);
__c.CallSubDelayed(ba,_msgmodule,_msgboxevent+"_Click");
 };
 };
 BA.debugLineNum = 325;BA.debugLine="End Sub";
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
public String  _showmessage(String _boxmessage,String _alignment) throws Exception{
		Debug.PushSubsStack("ShowMessage (custommsgbox) ","custommsgbox",8,ba,this);
try {
anywheresoftware.b4a.objects.StringUtils _su = null;
String _locate = "";
Debug.locals.put("BoxMessage", _boxmessage);
Debug.locals.put("Alignment", _alignment);
 BA.debugLineNum = 194;BA.debugLine="Public Sub ShowMessage(BoxMessage As String, Alignment As String)";
Debug.ShouldStop(2);
 BA.debugLineNum = 196;BA.debugLine="Dim su As StringUtils";
Debug.ShouldStop(8);
_su = new anywheresoftware.b4a.objects.StringUtils();Debug.locals.put("su", _su);
 BA.debugLineNum = 198;BA.debugLine="Locate = Gravity.LEFT";
Debug.ShouldStop(32);
_locate = BA.NumberToString(__c.Gravity.LEFT);Debug.locals.put("locate", _locate);
 BA.debugLineNum = 200;BA.debugLine="Select Case Alignment.ToUpperCase";
Debug.ShouldStop(128);
switch (BA.switchObjectToInt(_alignment.toUpperCase(),"L","C","R")) {
case 0:
 BA.debugLineNum = 204;BA.debugLine="Locate = Gravity.LEFT";
Debug.ShouldStop(2048);
_locate = BA.NumberToString(__c.Gravity.LEFT);Debug.locals.put("locate", _locate);
 break;
case 1:
 BA.debugLineNum = 208;BA.debugLine="Locate = Gravity.CENTER";
Debug.ShouldStop(32768);
_locate = BA.NumberToString(__c.Gravity.CENTER);Debug.locals.put("locate", _locate);
 break;
case 2:
 BA.debugLineNum = 212;BA.debugLine="Locate = Gravity.RIGHT";
Debug.ShouldStop(524288);
_locate = BA.NumberToString(__c.Gravity.RIGHT);Debug.locals.put("locate", _locate);
 break;
}
;
 BA.debugLineNum = 216;BA.debugLine="Message.Text = BoxMessage";
Debug.ShouldStop(8388608);
_message.setText((Object)(_boxmessage));
 BA.debugLineNum = 217;BA.debugLine="Message.Gravity = Locate";
Debug.ShouldStop(16777216);
_message.setGravity((int)(Double.parseDouble(_locate)));
 BA.debugLineNum = 219;BA.debugLine="MsgScrollView.Panel.Height = su.MeasureMultilineTextHeight(Message, Message.Text)";
Debug.ShouldStop(67108864);
_msgscrollview.getPanel().setHeight(_su.MeasureMultilineTextHeight((android.widget.TextView)(_message.getObject()),_message.getText()));
 BA.debugLineNum = 220;BA.debugLine="Message.Height = su.MeasureMultilineTextHeight(Message, Message.Text)";
Debug.ShouldStop(134217728);
_message.setHeight(_su.MeasureMultilineTextHeight((android.widget.TextView)(_message.getObject()),_message.getText()));
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
public String  _showrichmessage(anywheresoftware.b4a.agraham.richstring.RichStringBuilder.RichString _boxmessage,String _alignment) throws Exception{
		Debug.PushSubsStack("ShowRichMessage (custommsgbox) ","custommsgbox",8,ba,this);
try {
anywheresoftware.b4a.objects.StringUtils _su = null;
String _locate = "";
Debug.locals.put("BoxMessage", _boxmessage);
Debug.locals.put("Alignment", _alignment);
 BA.debugLineNum = 226;BA.debugLine="Public Sub ShowRichMessage(BoxMessage As RichString, Alignment As String)";
Debug.ShouldStop(2);
 BA.debugLineNum = 228;BA.debugLine="Dim su As StringUtils";
Debug.ShouldStop(8);
_su = new anywheresoftware.b4a.objects.StringUtils();Debug.locals.put("su", _su);
 BA.debugLineNum = 230;BA.debugLine="Locate = Gravity.LEFT";
Debug.ShouldStop(32);
_locate = BA.NumberToString(__c.Gravity.LEFT);Debug.locals.put("locate", _locate);
 BA.debugLineNum = 232;BA.debugLine="Select Case Alignment.ToUpperCase";
Debug.ShouldStop(128);
switch (BA.switchObjectToInt(_alignment.toUpperCase(),"L","C","R")) {
case 0:
 BA.debugLineNum = 236;BA.debugLine="Locate = Gravity.LEFT";
Debug.ShouldStop(2048);
_locate = BA.NumberToString(__c.Gravity.LEFT);Debug.locals.put("locate", _locate);
 break;
case 1:
 BA.debugLineNum = 240;BA.debugLine="Locate = Gravity.CENTER";
Debug.ShouldStop(32768);
_locate = BA.NumberToString(__c.Gravity.CENTER);Debug.locals.put("locate", _locate);
 break;
case 2:
 BA.debugLineNum = 244;BA.debugLine="Locate = Gravity.RIGHT";
Debug.ShouldStop(524288);
_locate = BA.NumberToString(__c.Gravity.RIGHT);Debug.locals.put("locate", _locate);
 break;
}
;
 BA.debugLineNum = 248;BA.debugLine="Message.Text = BoxMessage";
Debug.ShouldStop(8388608);
_message.setText((Object)(_boxmessage.getObject()));
 BA.debugLineNum = 249;BA.debugLine="Message.Gravity = Locate";
Debug.ShouldStop(16777216);
_message.setGravity((int)(Double.parseDouble(_locate)));
 BA.debugLineNum = 251;BA.debugLine="MsgScrollView.Panel.Height = su.MeasureMultilineTextHeight(Message, Message.Text)";
Debug.ShouldStop(67108864);
_msgscrollview.getPanel().setHeight(_su.MeasureMultilineTextHeight((android.widget.TextView)(_message.getObject()),_message.getText()));
 BA.debugLineNum = 252;BA.debugLine="Message.Height = su.MeasureMultilineTextHeight(Message, Message.Text)";
Debug.ShouldStop(134217728);
_message.setHeight(_su.MeasureMultilineTextHeight((android.widget.TextView)(_message.getObject()),_message.getText()));
 BA.debugLineNum = 254;BA.debugLine="End Sub";
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
public String  _showseparators(int _titleseparator,int _buttonseparator) throws Exception{
		Debug.PushSubsStack("ShowSeparators (custommsgbox) ","custommsgbox",8,ba,this);
try {
anywheresoftware.b4a.objects.drawable.CanvasWrapper _lineseparator = null;
Debug.locals.put("TitleSeparator", _titleseparator);
Debug.locals.put("ButtonSeparator", _buttonseparator);
 BA.debugLineNum = 257;BA.debugLine="Public Sub ShowSeparators(TitleSeparator As Int, ButtonSeparator As Int)";
Debug.ShouldStop(1);
 BA.debugLineNum = 259;BA.debugLine="Dim LineSeparator As Canvas";
Debug.ShouldStop(4);
_lineseparator = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();Debug.locals.put("LineSeparator", _lineseparator);
 BA.debugLineNum = 261;BA.debugLine="LineSeparator.Initialize(Panel)";
Debug.ShouldStop(16);
_lineseparator.Initialize((android.view.View)(_panel.getObject()));
 BA.debugLineNum = 262;BA.debugLine="LineSeparator.DrawLine(0dip, 65dip, 100%x, 65dip, TitleSeparator, 3)";
Debug.ShouldStop(32);
_lineseparator.DrawLine((float)(__c.DipToCurrent((int)(0))),(float)(__c.DipToCurrent((int)(65))),(float)(__c.PerXToCurrent((float)(100),ba)),(float)(__c.DipToCurrent((int)(65))),_titleseparator,(float)(3));
 BA.debugLineNum = 263;BA.debugLine="LineSeparator.DrawLine(0dip, YesButtonPanel.Top - 2dip, 100%x, YesButtonPanel.Top - 2dip, ButtonSeparator, 1)";
Debug.ShouldStop(64);
_lineseparator.DrawLine((float)(__c.DipToCurrent((int)(0))),(float)(_yesbuttonpanel.getTop()-__c.DipToCurrent((int)(2))),(float)(__c.PerXToCurrent((float)(100),ba)),(float)(_yesbuttonpanel.getTop()-__c.DipToCurrent((int)(2))),_buttonseparator,(float)(1));
 BA.debugLineNum = 265;BA.debugLine="If MsgNumberOfButtons = 2 AND MsgOrientation.ToUpperCase <> \"V\" Then";
Debug.ShouldStop(256);
if (_msgnumberofbuttons==2 && (_msgorientation.toUpperCase()).equals("V") == false) { 
 BA.debugLineNum = 267;BA.debugLine="LineSeparator.DrawLine(Panel.Width / 2, YesButtonPanel.Top - 2dip, Panel.Width / 2, Panel.Height, ButtonSeparator, 1)";
Debug.ShouldStop(1024);
_lineseparator.DrawLine((float)(_panel.getWidth()/(double)2),(float)(_yesbuttonpanel.getTop()-__c.DipToCurrent((int)(2))),(float)(_panel.getWidth()/(double)2),(float)(_panel.getHeight()),_buttonseparator,(float)(1));
 }else {
 BA.debugLineNum = 271;BA.debugLine="LineSeparator.DrawLine(0dip, NoButtonPanel.Top - 2dip, Panel.Width, NoButtonPanel.Top - 2dip, ButtonSeparator, 1)";
Debug.ShouldStop(16384);
_lineseparator.DrawLine((float)(__c.DipToCurrent((int)(0))),(float)(_nobuttonpanel.getTop()-__c.DipToCurrent((int)(2))),(float)(_panel.getWidth()),(float)(_nobuttonpanel.getTop()-__c.DipToCurrent((int)(2))),_buttonseparator,(float)(1));
 };
 BA.debugLineNum = 275;BA.debugLine="If MsgNumberOfButtons = 3 AND MsgOrientation.ToUpperCase <> \"V\" Then";
Debug.ShouldStop(262144);
if (_msgnumberofbuttons==3 && (_msgorientation.toUpperCase()).equals("V") == false) { 
 BA.debugLineNum = 277;BA.debugLine="LineSeparator.DrawLine(Panel.Width / 3, YesButtonPanel.Top - 2dip, Panel.Width / 3, Panel.Height, ButtonSeparator, 1)";
Debug.ShouldStop(1048576);
_lineseparator.DrawLine((float)(_panel.getWidth()/(double)3),(float)(_yesbuttonpanel.getTop()-__c.DipToCurrent((int)(2))),(float)(_panel.getWidth()/(double)3),(float)(_panel.getHeight()),_buttonseparator,(float)(1));
 BA.debugLineNum = 278;BA.debugLine="LineSeparator.DrawLine(Panel.Width / 3 + YesButtonPanel.Width + 3dip, YesButtonPanel.Top - 2dip, Panel.Width / 3 + YesButtonPanel.Width + 3dip, Panel.Height, ButtonSeparator, 1)";
Debug.ShouldStop(2097152);
_lineseparator.DrawLine((float)(_panel.getWidth()/(double)3+_yesbuttonpanel.getWidth()+__c.DipToCurrent((int)(3))),(float)(_yesbuttonpanel.getTop()-__c.DipToCurrent((int)(2))),(float)(_panel.getWidth()/(double)3+_yesbuttonpanel.getWidth()+__c.DipToCurrent((int)(3))),(float)(_panel.getHeight()),_buttonseparator,(float)(1));
 }else {
 BA.debugLineNum = 282;BA.debugLine="LineSeparator.DrawLine(0dip, CancelButtonPanel.Top - 2dip, Panel.Width, CancelButtonPanel.Top - 2dip, ButtonSeparator, 1)";
Debug.ShouldStop(33554432);
_lineseparator.DrawLine((float)(__c.DipToCurrent((int)(0))),(float)(_cancelbuttonpanel.getTop()-__c.DipToCurrent((int)(2))),(float)(_panel.getWidth()),(float)(_cancelbuttonpanel.getTop()-__c.DipToCurrent((int)(2))),_buttonseparator,(float)(1));
 };
 BA.debugLineNum = 286;BA.debugLine="Panel.Invalidate";
Debug.ShouldStop(536870912);
_panel.Invalidate();
 BA.debugLineNum = 288;BA.debugLine="End Sub";
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
public String  _showshadow(int _color) throws Exception{
		Debug.PushSubsStack("ShowShadow (custommsgbox) ","custommsgbox",8,ba,this);
try {
Debug.locals.put("Color", _color);
 BA.debugLineNum = 291;BA.debugLine="Public Sub ShowShadow(Color As Int)";
Debug.ShouldStop(4);
 BA.debugLineNum = 293;BA.debugLine="Shadow.Color = Color";
Debug.ShouldStop(16);
_shadow.setColor(_color);
 BA.debugLineNum = 295;BA.debugLine="End Sub";
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
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
ba.sharedProcessBA.sender = sender;
return BA.SubDelegator.SubNotFound;
}
  public Object[] GetGlobals() {
		return new Object[] {"MsgModule",_msgmodule,"BackPanel",_backpanel,"MsgOrientation",_msgorientation,"MsgNumberOfButtons",_msgnumberofbuttons,"mbIcon",_mbicon,"Panel",_panel,"Shadow",_shadow,"Title",_title,"MsgScrollView",_msgscrollview,"Message",_message,"YesButtonPanel",_yesbuttonpanel,"NoButtonPanel",_nobuttonpanel,"CancelButtonPanel",_cancelbuttonpanel,"YesButtonCaption",_yesbuttoncaption,"NoButtonCaption",_nobuttoncaption,"CancelButtonCaption",_cancelbuttoncaption,"MsgBoxEvent",_msgboxevent,"ButtonSelected",_buttonselected,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"DBUtils",Debug.moduleToString(b4a.sysdev.dbutils.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
