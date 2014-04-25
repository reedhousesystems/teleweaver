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
	Dim gselectedPicture As ImageView

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim lblHeader As Label
	Dim ImageView1 As ImageView
	Dim ImageView2 As ImageView
	Dim ImageView3 As ImageView
	Dim ImageView4 As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("Products")

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ImageView4_Click

	selectedPicture = ImageView4
	StartActivity("productDetails")
	
End Sub
Sub ImageView3_Click

	selectedPicture = ImageView3
	StartActivity("productDetails")
	
End Sub
Sub ImageView2_Click

	selectedPicture = ImageView2
	StartActivity("productDetails")
	
End Sub
Sub ImageView1_Click

	selectedPicture = ImageView1
	StartActivity("productDetails")
	
End Sub