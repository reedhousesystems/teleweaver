Type=Activity
Version=2.7
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim btnConfirm As Button
	Dim btnSelectPicture As Button
	Dim etPrice As EditText
	Dim etProdDes As EditText
	Dim etProdName As EditText
	Dim ImageView1 As ImageView
	Dim lblDescription As Label
	Dim lblHeader As Label
	Dim lblName As Label
	Dim lblPicture As Label
	Dim lblPrice As Label
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("artwork")

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub btnSelectPicture_Click
	
	Dim i As Intent
                i.Initialize(i.ACTION_PICK, "")
				i.SetType("image/*")
    StartActivity(i)
	
	'Dim strFile As String
    'random filename
    'strFile = DateTime.Now
    'camera.OpenCam(File.DirRootExternal & "/DCIM/Camera", strFile & ".jpg")
	
End Sub

Sub btnConfirm_Click

	Msgbox("Product added to TeleWeaver.","")
	StartActivity("menu")
	
End Sub