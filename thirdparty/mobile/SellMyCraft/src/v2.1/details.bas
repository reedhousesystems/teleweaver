Type=Activity
Version=2.71
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
	Dim btnUpload As Button
	Dim etPrice As EditText
	Dim etProdDes As EditText
	Dim etProdName As EditText
	Dim imgSelected As ImageView
	Dim lblHeader As Label
	Dim btnDelete As Button
	Dim confirmDel As Int
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
'	Activity.LoadLayout("ProductDetails")
'	'imgSelected.Bitmap = viewproduct.gselectedPicture
'	etPrice.Text = viewproduct.gprodPrice
'	etProdDes.Text = viewproduct.gprodDes
'	etProdName.Text = viewproduct.gprodName
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub btnUpload_Click

'	Msgbox("Product updated on TeleWeaver","")
'	viewproduct.gprodDes = etProdDes.text
'	viewproduct.gprodName = etProdName.text
'	viewproduct.gprodPrice = etPrice.text
'	Activity.Finish
	
End Sub
Sub btnCancel_Click

	Activity.Finish
	
End Sub
Sub btnDelete_Click
	
'	confirmDel = Msgbox2("Are you sure you wish to remove this product?","","Yes","","No",Null)
'	If confirmDel = DialogResponse.POSITIVE Then
'		Msgbox("Product Removed","")
'		viewproduct.gprodDeleted = 4
'		Activity.finish
'	End If
	
End Sub