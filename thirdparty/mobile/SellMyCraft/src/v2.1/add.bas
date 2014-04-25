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
Dim cc As ContentChooser
End Sub

Sub Globals

	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Dim myMsgBox As CustomMsgBox

  	Dim picSelected As Boolean
	Dim imageLogo As ImageView
	Dim imgSelected As ImageView
	Dim ImageViewTemp As ImageView
	Dim lblHeader As Label
	Dim lblPrice As Label
	Dim tbConnection As ToggleButton
	Dim pictureAnswer As String
	
	Dim txtProdName As EditText
	Dim txtPrice As EditText
	Dim txtProdDes As EditText
	Dim spType As Spinner
	Dim imageAddProd As ImageView
	Dim imgCancel As ImageView
	Dim lblAddProd As Label
	Dim lblCancel As Label
	Dim lblDimensions As Label
	Dim lblprodName As Label
	Dim lblprodPrice As Label
	Dim lblprodType As Label
	Dim imageCancel As ImageView
	
	Dim curs As Cursor
	Dim s As SQL
	
	Dim txtSize As EditText
	Dim lblType As Label
	Dim lblemptyName As Label
	Dim lblEmptyPrice As Label
	Dim lblpricenumber As Label
	Dim hexaPicture As String
	Dim su As StringUtils
	Dim Buffer() As Byte 'declares an empty array
	Dim viewornot As String
	Dim WebView1 As WebView
	 Dim sss As String
	 Dim b As Bitmap
		Dim Button1 As Button
		Dim p_ID As Int
		Dim Dir2, FileName2 As String
	Dim lblwrongsize As Label
	Dim lblAddtitle As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("AddProduct")
	
	cc.Initialize ("chooser")
'		File.Copy(File.DirAssets,"egazini.db",File.DirInternal,"egazini.db")
changeLanguage
'	Main.aSQL.Initialize(File.DirInternal, "egazini.db", False)
	
End Sub
Sub changeLanguage
	
	If Main.globlang = "isiXhosa" Then
		lblAddtitle.text = "Faka Imveliso"
		lblAddtitle.TextSize = 20
		txtProdName.Hint = "Faka igama apha"
		lblprodName.Text = "Imveliso igama"
		lblprodPrice.Text = "Imveliso ixabiso"
		txtPrice.Hint = "Faka ixabiso apha" 
		lblprodType.Text = "Umhlobo wemveliso"
		spType.Prompt= "Faka umhlobo wemveliso"
		spType.AddAll(Array As String("Umzobo","Umkhubeko"))
		lblDimensions.Text = "Umlinganiselo wobude(LxBxH)"
		txtSize.Hint = "Faka ubude apha"
		txtProdDes.Hint = "Cacisa umveliso apha"
		
		lblAddProd.Text = "Qcina"
		lblCancel.Text = "Cima"
		
		lblType.Text = "Ndicela ufake umveliso"
		lblemptyName.Text = "Ndicela ufake igama lumveliso"
		lblEmptyPrice.Text = "Ndicela ufake ixabiso lomveliso"
		lblpricenumber.Text = "Ixabiso fanenele libe yinombolo"
		lblwrongsize.Text = "Ubude fanenele bube yinombolo"
	Else
	    txtProdName.Hint = "Enter name here"
		lblprodName.Text = "Product name"
		lblprodPrice.Text = "Product price"
		txtPrice.Hint = "Enter price here" 
		lblprodType.Text = "Product type"
		spType.Prompt= "Enter product type"
		spType.AddAll(Array As String("Art","Craft"))
		lblDimensions.Text = "Enter size (LxBxH) [cm]"
		txtSize.Hint = "Enter size here"
		txtProdDes.Hint = "Enter a brief description of the product."
		
		lblAddProd.Text = "Save"
		lblCancel.Text = "Cancel"
		
		lblType.Text = "Please enter the Product Type."
		lblemptyName.Text = "Please enter the Product Name."
		lblEmptyPrice.Text = "Please enter the Product Price."
		lblpricenumber.Text = "Product Price should be a number."
		lblwrongsize.Text = "The Size should be a number."
	
	End If
	
End Sub
Sub Activity_Resume
	'StateManager.RestoreState(Activity, "Main", 0)
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	'StateManager.SaveSettings
End Sub

'Sub imgSelected_Click
'	
'	'open phone camera gallery and selecy photo to use
'	pictureAnswer = Msgbox2("Picture selection:","","New picture","Cancel","From Gallery",Null)
'	If pictureAnswer = DialogResponse.positive Then
'		'open camera
'	Else If pictureAnswer = DialogResponse.negative Then
'		'open gallery
'		Dim i As Intent
'                i.Initialize(i.ACTION_PICK, "")
'				i.SetType("image/*")
'    	StartActivity(i)
'	End If
'	
'End Sub

Sub imageAddProd_Click
	lblemptyName.Visible = False
	lblEmptyPrice.Visible = False
	lblType.Visible = False
	lblwrongsize.Visible = False
'	
'	If txtSize.Text.Trim.Length > 0 Then
'		 If IsNumber(txtSize.Text) Then
'		 
'		   Else		 
'		 	lblwrongsize.Visible = True
'			txtSize.RequestFocus
'		 End If
	
'	Else 
  
   If txtProdName.Text = "" Then 
		lblemptyName.Visible = True
		txtProdName.RequestFocus
	Else If txtPrice.Text = "" Then	
		lblEmptyPrice.Visible = True
		txtPrice.RequestFocus
	Else If Not (IsNumber (txtPrice.Text)) Then
		lblpricenumber.Visible = True
		txtPrice.RequestFocus
	'Else If txtLength.text <> "" AND txtBreadth.text <> "" Then
		'txtSize = txtLength.text & "x" & txtBreadth.text
	
		'lblType.Visible = True
		'txtType.RequestFocus
		
	Else			
			'Grab the last ID number which is the highest number		  
			curs = Main.aSQL.ExecQuery("SELECT ProductID FROM " & Main.DBTableProduct)
	
			If curs.RowCount > 0 Then
				For i = 0 To curs.RowCount - 1	
					curs.Position = i					
					Dim NewProdID As Int
					NewProdID = curs.GetInt("ProductID")
				Next
			End If
			
			NewProdID = NewProdID + 1 ' add 1 to the ID number to make a new ID field
 			p_ID = NewProdID
			
			'perform insert to local SQLITE
			Insert_Image		
			Main.aSQL.ExecNonQuery2("INSERT INTO Product VALUES(?,?,?,?,?,?,?,?,?,?)", Array As Object(NewProdID,txtProdName.Text,txtSize.Text, txtPrice.Text,txtProdDes.Text,spType.SelectedItem,Main.globID,Buffer,"yes","none"))

			'Attempt to perform insert to TW
			'========================================
			ProgressDialogShow("Adding Product to TeleWeaver...")
			addProdToTW
			'========================================
			
		'	Main.aSQL.ExecNonQuery2("UPDATE Product SET Status=? WHERE ProductID = " & p_ID , Array As Object("add"))
			
	End If
End Sub
Sub addProdToTW
    
		Dim postProdJob As HttpJob
		Dim m As Map
		Dim postProdUrl As String
		
	    postProdUrl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/add-product/"      
				
		m.Initialize
		m.Put("id", p_ID)
		m.Put("profile", Main.m.Get("id"))
		m.Put("name", txtProdName.Text)
		m.Put("dimension", txtSize.Text)
		m.Put("price", txtPrice.Text)
		m.Put("description", txtProdDes.Text)
		m.Put("type", spType.SelectedItem)
		m.Put("quantity", 18)	
	    retrieveANDconvert     ' retrieves the image from SQLite or uses default
		m.Put("picture", hexaPicture)

		
		Dim JSONGenerator As JSONGenerator
	    JSONGenerator.Initialize(m)  		
       postProdJob.Initialize("PostProd", Me)
	   postProdJob.PostString(postProdUrl, JSONGenerator.ToString())
	   postProdJob.GetRequest.SetContentType("application/json")
                  

End Sub

Sub retrieveANDconvert

   hexaPicture = su.EncodeBase64(Buffer)
 '  Msgbox(hexaPicture,"hexa")
	   
End Sub

Sub JobDone (Job As HttpJob)
ProgressDialogHide
Dim MyT As CToast
	    MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)
	Log("JobName = " & Job.JobName & ", Success = " & Job.Success)
	If Job.Success = True Then
		Select Job.JobName
			Case "PostProd"				
				Log(Job.GetString)							
				showMsgBoxAllGood
		    End Select
	Else
		Log("Error: " & Job.ErrorMessage)
		showMsgBoxHalfGood
	End If
	Job.Release
	
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

Sub view_Image

'Dim readCursor As Cursor
'Dim imgBuffer As Byte
'Dim imgInputStream As InputStream
'Dim imgBitmap As Bitmap
'Dim imgOnScreen As ImageView
'
''... the SQL statements To populate the readCursor....
'
'imgBuffer = readCursor.GetBlob ("Picture")
'
''before I proceed from here I want to validate if the  imageBuffer is Null, because it can be empty (containing no image).
'If imgBuffer = Null Then  ' this is what I need to get solved
'    'skip and don't load anything
'Else
'     imgInputStream.InitiaizeFromBytesArray (imgBuffer, 0, imgBuffer.Length)
'     imgBitmap.Initialize (imgInputStream)
'     imgInputStream.Close
'     imgOnScreen.Bitmap = imgBitmap
'End If

End Sub

'Sub Button1_Click
'	Main.aSQL.ExecNonQuery("DELETE FROM Product WHERE ProductID > 0")
'	'WebView1.LoadHtml(DBUtils.ExecuteHtml(Main.aSQL, sss, Null, 0, True))
'End Sub

Sub Insert_Image    
    Dim InputStream1 As InputStream
	
		If picSelected Then 
    		InputStream1 = File.OpenInput(Dir2, FileName2)
		Else
			InputStream1 = File.OpenInput(File.DirAssets, "no product image.png") '****
		End If 
	
    Dim OutputStream1 As OutputStream
    OutputStream1.InitializeToBytesArray(1000)
    File.Copy2(InputStream1, OutputStream1)
   ' Buffer = Null
    Buffer = OutputStream1.ToBytesArray
    InputStream1.Close
	OutputStream1.Close
	  
End Sub

Sub showMsgBoxAllGood
   
	myMsgBox.Initialize(Activity, Me, "Add", "H", 2, 95%x, 200dip, LoadBitmap(File.DirAssets, "OKAddIcon.png"))

	
	myMsgBox.Title.textColor = Colors.white
	myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD
	myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, "B.png"))

	myMsgBox.ShowSeparators(Colors.black, Colors.black)

   	   myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))
				
   myMsgBox.YesButtonPanel.Color = Colors.white
   myMsgBox.NoButtonPanel.Color = Colors.white
		
		myMsgBox.NoButtonCaption.TextColor =Colors.Black
		myMsgBox.YesButtonCaption.TextColor =Colors.Black
		
		myMsgBox.Message.TextColor = Colors.white
		myMsgBox.Message.TextSize = 13
   
      
    If Main.globlang = "isiXhosa" Then
	   'XHOSA
	   myMsgBox.NoButtonCaption.Text = "Xha"
		myMsgBox.YesButtonCaption.Text = "Ewe"
    Else
        myMsgBox.Title.Text = "Product Added"
		 myMsgBox.Title.TextSize = 20
		 myMsgBox.ShowMessage("Product has been successfully synchronized and added!" & CRLF & "Would you like to add more Products now?","C")
    End If
	
End Sub

Sub showMsgBoxHalfGood
   
	myMsgBox.Initialize(Activity, Me, "Add", "H", 2, 95%x, 200dip, LoadBitmap(File.DirAssets, "OKAddIcon.png"))	
	myMsgBox.Title.textColor = Colors.white
	myMsgBox.Title.Typeface = Typeface.DEFAULT_BOLD
	myMsgBox.Panel.SetBackgroundImage(LoadBitmap(File.DirAssets, "B.png"))

	myMsgBox.ShowSeparators(Colors.black, Colors.black)

   	   myMsgBox.ShowShadow(Colors.ARGB(80, 8, 180, 206))
				
   myMsgBox.YesButtonPanel.Color = Colors.white
   myMsgBox.NoButtonPanel.Color = Colors.white
		
		myMsgBox.NoButtonCaption.TextColor =Colors.Black
		myMsgBox.YesButtonCaption.TextColor =Colors.Black
		
		myMsgBox.Message.TextColor = Colors.white
		myMsgBox.Message.TextSize = 13
         
    If Main.globlang = "isiXhosa" Then
	   'XHOSA
	   myMsgBox.NoButtonCaption.Text = "Xha"
		myMsgBox.YesButtonCaption.Text = "Ewe"
    Else
             myMsgBox.Title.Text = "Product Added Locally"
		 myMsgBox.Title.TextSize = 20
		 myMsgBox.ShowMessage("Sorry could not sync with Teleweaver at this time. Add was only local." & CRLF & "Would you like to add more Products now?","C")
		 
		 Main.aSQL.ExecNonQuery2("UPDATE Product SET Status=? WHERE ProductID = " & p_ID , Array As Object("add"))  ' remember it needs to be synced. LATERRR
'		 curs = Main.aSQL.ExecQuery("SELECT * FROM Product WHERE ProductID = " & p_ID)
'		 curs.Position = 0
    End If
	
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
		myMsgBox.Message.TextSize = 13
   
	   If Main.globlang = "isiXhosa" Then
	   'XHOSA
		myMsgBox.NoButtonCaption.Text = "Xha"
		myMsgBox.YesButtonCaption.Text = "Ewe"
		myMsgBox.CancelButtonCaption.Text = "Ewe"
    Else
	    myMsgBox.NoButtonCaption.Text = "From Gallery"
		myMsgBox.YesButtonCaption.Text = "Take Picture"
		myMsgBox.CancelButtonCaption.Text = "Cancel"
		
        myMsgBox.Title.Text = "Picture"
		 myMsgBox.Title.TextSize = 20
		myMsgBox.ShowMessage("Please select an option","C")
    End If
	
End Sub

Sub imageCancel_Click
	Activity.Finish
End Sub
Sub lblCancel_Click
	imageCancel_Click
End Sub
Sub lblAddProd_Click
	imageAddProd_Click
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

Sub Add_Click
	If myMsgBox.ButtonSelected = "yes" Then	 
		 txtProdName.Text = ""
         txtSize.Text = ""
		 txtPrice.Text= ""
		 txtProdDes.Text= ""
	     picSelected = False
		 imgSelected.Bitmap = LoadBitmapSample(File.DirAssets, "click to add image.png",imgSelected.Width,imgSelected.Height)
		 txtProdName.RequestFocus
	Else
		Activity.Finish
		Activity.LoadLayout("theTab")
	End If

End Sub


