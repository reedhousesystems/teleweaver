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
	Dim SelectedDateInTicks As Long
	Dim cc As ContentChooser
	Dim  ArtistUpdated As Boolean
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim Label2 As Label
	Dim txtName As EditText
	Dim txtSurn As EditText
	Dim EditText1 As EditText
	Dim txtBio As EditText
	Dim Dir2, FileName2 As String
	Dim imgSelected As ImageView
	Dim picSelected As Boolean
	Dim pictureAnswer As String
	Dim txtPhone As EditText
	Dim Buf() As Byte
	Dim lblNoName As Label
	Dim lblNoSurn As Label
	Dim btnDob As Button
	Dim Dob As Label
	Dim imageCancel As ImageView
	Dim imageLogo As ImageView
	Dim imageSave As ImageView
	Dim lblCancel As Label
	Dim lblDate As Label
	Dim lblName As Label
	Dim lblPhone As Label
	Dim lblSave As Label
	Dim lblSurname As Label
	Dim tbConnection As ToggleButton
	Dim myMsgBox As CustomMsgBox
	
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("editArtist")
	lblNoName.Visible = False
	lblNoSurn.Visible = False
	cc.Initialize("chooser")
	loadToControls
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub changeLanguage
	
	If Main.globlang = "isiXhosa" Then
		
		lblName.Text = "Igama"
		txtName.Hint = "Faka name"
		lblSurname.Text = "Ifani"
		txtSurn.Hint = "Faka surname"
		Dob.Text = "Umhla lokuzalwa"
		btnDob.Text = "Set Date"
		lblPhone.Text = "Icingo Le Ndaba"
		txtPhone.Hint = "Faka icingo le ndaba"
		txtBio.Hint = "Cacisa imisebenzi yakho"
		
		lblSave.Text = "Gcina"
		lblCancel.Text = "Cima"
	Else
		lblName.Text = "Name"
		txtName.Hint = "Enter name"
		lblSurname.Text = "Surname"
		txtSurn.Hint = "Enter surname"
		Dob.Text = "Date of Birth"
		btnDob.Text = "Set Date"
		lblPhone.Text = "Phone Number"
		txtPhone.Hint = "Enter phone number"
		txtBio.Hint = "Enter your brief biography"
		
		lblSave.Text = "Save"
		lblCancel.Text = "Cancel"
	End If
	
End Sub

Sub loadToControls

	Main.cur = Main.aSQL.ExecQuery2("SELECT * FROM Artist WHERE ArtistID = ?", Array As String(Main.globID))
	Main.cur.Position = 0
	
	 
		txtName.Text = Main.cur.GetString("Name")
		txtSurn.Text = Main.cur.GetString("Surname")
		lblDate.Text = Main.cur.GetString("Dob")
		txtPhone.Text = Main.cur.GetString("Phone")
		txtBio.Text = Main.cur.GetString("Bio")
		getImage_forEdit


End Sub

Sub getImage_forEdit
   
		Dim InputStrea As InputStream    
		Dim Bitt As Bitmap
		
'		Buf = Null
'		If Main.cur.GetBlob("ArtistPic").Length < 0 Then
'	        Buf = Main.cur.GetBlob("ArtistPic")
'	        InputStrea.InitializeFromBytesArray(Buf,0, Buf.Length) 
'		  
'		    Bitt.Initialize2(InputStrea)
'		    InputStrea.Close		
'			imgSelected.SetBackgroundImage(Bitt)
'			 InputStrea.Close
'   		 Else
'			imgSelected.Bitmap = LoadBitmapSample  (File.DirAssets, "empty_gallery.png",imgSelected.Width,imgSelected.Height)	
'		End If
		
	    Buf = Null
		If Main.cur.GetString("PicExist") = "yes" Then
	        Buf = Main.cur.GetBlob("ArtistPic")
	        InputStrea.InitializeFromBytesArray(Buf, 0, Buf.Length) 
		  
		    Bitt.Initialize2(InputStrea)
		    InputStrea.Close		
			imgSelected.SetBackgroundImage(Bitt)
   		 Else
			imgSelected.Bitmap = LoadBitmapSample  (File.DirAssets, "empty_gallery.png",imgSelected.Width,imgSelected.Height)	
		End If
		
End Sub


Sub btnDob_Click
	Dim Dd As DateDialog
	' set the date shown when the dialog is openend
	Dd.DateTicks = SelectedDateInTicks	
	ret = Dd.Show("Please choose a date", "Date-Chooser", "OK", "", "Cancel", Null)
	If ret = DialogResponse.CANCEL Then 
		
		lblDate.Text = ""
		ToastMessageShow("cancelled", True)
		'Return
	Else
	
	lblDate.Text = DateTime.Date(Dd.DateTicks)
	' save the selected date, so it can be shown again
	SelectedDateInTicks = Dd.DateTicks
	'ToastMessageShow(SelectedDateInTicks, True)
	End If
End Sub

Sub chooser_Result (Success As Boolean, Dir As String, FileName As String)

	If Success Then 
		imgSelected.Bitmap = LoadBitmapSample  (Dir, FileName,imgSelected.Width,imgSelected.Height)
		picSelected = Success
		Dir2 = Dir
		FileName2 = FileName 
	End If

End Sub

Sub imgSelected_Click
showMsgBoxCamera

End Sub

Sub imageSave_Click
	lblNoName.Visible = False
	lblNoSurn.Visible = False
    'Dim curs As Cursor
	
	Main.cur = Main.aSQL.ExecQuery("SELECT * FROM Artist WHERE ArtistID = " & Main.m.Get("id"))
	Main.cur.Position = 0
	
	If txtName.Text = "" Then
		lblNoName.Visible = True
	Else If txtSurn.Text = ""  Then 
			lblNoSurn.visible = True
	Else
	
		If picSelected Then     		
			    Insert_Image
				Main.aSQL.ExecNonQuery2("UPDATE Artist SET Name=?, Surname=?, Dob=?, Phone=?, Bio=?, ArtistPic=?, PicExist=?  WHERE ArtistID=" & Main.globID , Array As Object( txtName.Text,txtSurn.Text,lblDate.Text,txtPhone.Text,txtBio.Text ,Buf,"yes"))
				Main.aSQL.ExecNonQuery2("UPDATE lookupUsrPass SET Name=?, Surname=? WHERE ArtistID=" &Main.m.Get("id") , Array As Object( txtName.Text,txtSurn.Text))
				Main.globName = txtName.Text & " " & txtSurn.Text
		Else
			Main.aSQL.ExecNonQuery2("UPDATE Artist SET Name=?, Surname=?, Dob=?, Phone=?, Bio=? WHERE ArtistID=" & Main.m.Get("id") , Array As Object( txtName.Text,txtSurn.Text,lblDate.Text,txtPhone.Text,txtBio.Text ))
			Main.aSQL.ExecNonQuery2("UPDATE lookupUsrPass SET Name=?, Surname=? WHERE ArtistID=" & Main.m.Get("id") , Array As Object( txtName.Text,txtSurn.Text))
			Main.globName = txtName.Text & " " & txtSurn.Text
		End If 
	
'	     Dim MyT As CToast
'	     MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)
'		 
'		 MyT.ToastMessageShow2("Your Artist information has been successfully updated",5,60,50, "bup.png", Colors.Black, Colors.Gray, 20, True)

	     ArtistUpdated = True
	     StartActivity("menu")
	     Activity.Finish
	
	End If
End Sub


Sub Insert_Image    
    Dim InputStream1 As InputStream	
	InputStream1 = File.OpenInput(Dir2, FileName2)

    Dim OutputStream1 As OutputStream
    OutputStream1.InitializeToBytesArray(1000)
    File.Copy2(InputStream1, OutputStream1)
    
    Buf = OutputStream1.ToBytesArray

End Sub

Sub imageCancel_Click
	Activity.Finish
End Sub

Sub lblSave_Click
	imageSave_Click
End Sub

Sub lblCancel_Click
	imageCancel_Click
End Sub

Sub showMsgBoxCamera
   
	myMsgBox.Initialize(Activity, Me, "Cam", "H", 3, 95%x, 200dip, LoadBitmap(File.DirAssets, "cameraMsg.png"))

 
	myMsgBox.Title.textColor = Colors.white
	myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD
	myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, "B.png"))

   myMsgBox.ShowSeparators(Colors.black, Colors.black)
   myMsgBox.Message.Height = 10dip
   myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))
				
        myMsgBox.YesButtonPanel.Color = Colors.white
        myMsgBox.NoButtonPanel.Color = Colors.white
		myMsgBox.CancelButtonPanel.Color = Colors.white
		
		myMsgBox.NoButtonCaption.TextColor =Colors.Black
		myMsgBox.CancelButtonCaption.TextColor =Colors.Black
		myMsgBox.YesButtonCaption.TextColor =Colors.Black
		
		myMsgBox.Message.TextColor = Colors.white
		myMsgBox.Message.TextSize = 23
   
	   If Main.globlang = "isiXhosa" Then
	   'XHOSA
'		myMsgBox.NoButtonCaption.Text = "Xha"
'       myMsgBox.YesButtonCaption.Text = "Ewe"
'		myMsgBox.CancelButtonCaption.Text = "Ewe"
    Else
	    myMsgBox.NoButtonCaption.Text = "From Gallery"
		myMsgBox.YesButtonCaption.Text = "Take Picture"
		myMsgBox.CancelButtonCaption.Text = "Cancel"   
			myMsgBox.ShowMessage("Please select an option","C")
    End If
	     myMsgBox.Title.Text = "Picture"
		 myMsgBox.Title.TextSize = 25
		 
End Sub


Sub Cam_Click 
	If  myMsgBox.ButtonSelected = "yes" Then
		'open camera
		 Dim i As Intent
    	 i.Initialize("android.media.action.IMAGE_CAPTURE", "")
   		' i.PutExtra("output", ParseUri("file://" & File.Combine(Directory, PictureName)))        
   		 StartActivity(i)
	Else If myMsgBox.ButtonSelected = "no" Then
		'open gallery
		cc.show("image/*", "Choose a Picture")
'		Dim i As Intent
'                i.Initialize(i.ACTION_PICK, "")
'				i.SetType("image/*")
'    	StartActivity(i)
	End If	 
End Sub

