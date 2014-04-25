Type=Activity
Version=2.71
@EndOfDesignText@
'Activity module
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim scvImages As ScrollView
	Dim imvImage As ImageView
	Dim bmpImage As Bitmap
	Dim lblImage As Label
	Dim imgWidth As Int			: imgWidth=280dip		' image width
	Dim imgHeight As Int		: imgHeight=210dip	' image height
	Dim imgSpace As Int			: imgSpace=5dip			' space between images
	Dim nbrImage As Int			: nbrImage=3				' number of images beginning with 0
	Dim lfmImage As Int													' left margin of images
	Dim btnBack As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("ImageScrollView")			' loads the layout file
	lfmImage=(scvImages.Width-imgWidth)/2			' calculates the left margin
	InitScrollView															' inits the ScrollView
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub InitScrollView
	Dim i As Int
	If Main.globlang = "isiXhosa" Then
		For i=0 To 3
			btnBack.Text = "Cima"
			Dim imvImage As ImageView																			
			Dim bmpImage As Bitmap
			bmpImage.Initialize(File.DirAssets,"xhosahelp"&i&".png")
	'		bmpImage.Initialize(File.DirInternal,"Image"&i&".jpg")
	'		bmpImage.Initialize(File.DirDefaultExternal,"Image"&i&".jpg")
			imvImage.Initialize("imvImage")
			imvImage.Gravity=Gravity.FILL
			imvImage.Tag=i
			imvImage.Bitmap=bmpImage
			scvImages.Panel.AddView(imvImage,lfmImage,i*(imgHeight+imgSpace),imgWidth,imgHeight)
		Next
		scvImages.Panel.Height=(nbrImage+1)*(imgHeight+imgSpace)
	Else
		For i=0 To 3
			btnBack.Text = "Back"
			Dim imvImage As ImageView																			
			Dim bmpImage As Bitmap
			bmpImage.Initialize(File.DirAssets,"englishHelp"&i&".png")
	'		bmpImage.Initialize(File.DirInternal,"Image"&i&".jpg")
	'		bmpImage.Initialize(File.DirDefaultExternal,"Image"&i&".jpg")
			imvImage.Initialize("imvImage")
			imvImage.Gravity=Gravity.FILL
			imvImage.Tag=i
			imvImage.Bitmap=bmpImage
			scvImages.Panel.AddView(imvImage,lfmImage,i*(imgHeight+imgSpace),imgWidth,imgHeight)
		Next
		scvImages.Panel.Height=(nbrImage+1)*(imgHeight+imgSpace)
	End If
End Sub

Sub btnBack_Click
	Activity.Finish
End Sub