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
	Dim hc As HttpClient
	Dim lblHeader As Label
	Dim lblUser As Label
	Dim imageLogo As ImageView
	Dim btnConnected As ToggleButton
	Dim imageUser As ImageView
	Dim myMsgBox As CustomMsgBox
	Dim lblSynch As Label
	Dim pbSynch As ProgressBar
	Dim pnlPage1, pnlPage2, pnlPage3 As Panel
	Dim curs As Cursor
	Dim tbhPages As TabHost
	Dim Button1 As Button
	Dim b() As Byte
	
	Dim imageAddProd As ImageView
	Dim imageEditProfile As ImageView
	Dim imageSales As ImageView
	Dim imageViewProd As ImageView
	Dim imageSync As ImageView
	Dim imageLogOut As ImageView
	
	Dim lblAddProd As Label
	Dim lblEditProfile As Label
	Dim lblViewSales As Label
	Dim lblViewProduct As Label
	Dim lblSync As Label
	Dim lblLogout As Label
    Dim hexaPicture As String
	  Dim MyT As CToast
	Dim Language As Spinner
	Dim imgHelpBtn As ImageView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("theTab")
	Activity.LoadLayout("central")
	Language.AddAll(Array As String("English","isiXhosa"))
	'tbhPages.AddTab("Home","central")		
	'tbhPages.AddTab("Products","Products")	'a List of all products in DB -- created dynamically
	'tbhPages.AddTab("Sales","Sales")  ' ideally should be linked to the product	
	lblUser.Text =  Main.globName
    getImage_forEdit
    changeLanguage
	hc.Initialize("hc")	
  
	MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)
End Sub

Sub changeLanguage
	If Main.globlang = "English" Then
		lblHeader.Text = "Welcome"
		lblAddProd.Text = "Add Product"
		lblEditProfile.Text = "Edit My Info"
		lblViewSales.Text = "View Sales"
		lblViewProduct.Text = "View Product"
		lblSync.Text = "Synchronize"
		lblSynch.Text = "Synchronizing..."
		lblLogout.Text = "Exit"
	Else If  Main.globlang = "isiXhosa" Then
		lblHeader.Text = "Wamnkelekile"
		lblAddProd.Text = "Faka imveliso"
		lblEditProfile.Text = "Inkcukacha zam"
		lblViewSales.Text = "Jonga intengiso"
		lblViewProduct.Text = "Jonga imveliso"
		lblSync.Text = "Ngqamanisa"
		lblLogout.Text = "Phuma"
	End If
End Sub
Sub getImage_forEdit

   Main.cur = Main.aSQL.ExecQuery2("SELECT * FROM Artist WHERE ArtistID = ?", Array As String(Main.globID))
	Main.cur.Position = 0
		Dim InputStrea As InputStream    
		Dim Bitt As Bitmap
		
		b = Null
		If Main.cur.GetString("PicExist") = "yes" Then
	        b = Main.cur.GetBlob("ArtistPic")
	        InputStrea.InitializeFromBytesArray(b, 0, b.Length) 
		  
		    Bitt.Initialize2(InputStrea)
		    InputStrea.Close		
			imageUser.SetBackgroundImage(Bitt)
   		 Else
			imageUser.Bitmap = LoadBitmapSample  (File.DirAssets, "empty_gallery.png",imageUser.Width,imageUser.Height)	
			
		End If
		
		
'	b = Null
'    If Main.cur.GetBlob("ArtistPic") <> Null OR Main.cur.GetBlob("ArtistPic").Length > 0 Then
'        b = Main.cur.GetBlob("ArtistPic")
'		Msgbox("There is a pic in here","")
'    End If
'  
'    If b = Null OR Main.cur.GetBlob("ArtistPic").Length < 1 Then
'	Msgbox("There is a NO pic in here","")
'       imageUser.Bitmap = LoadBitmapSample  (File.DirAssets, "empty_gallery.png",imageUser.Width,imageUser.Height)
'    Else
'			Msgbox("im going to grab the images now","")
'          b = Main.cur.GetBlob("ArtistPic")
'	        InputStrea.InitializeFromBytesArray(b, 0, b.Length) 
'		  
'		    Bitt.Initialize2(InputStrea)
'		    InputStrea.Close		
'			imageUser.SetBackgroundImage(Bitt)
'    End If
		
End Sub

Sub Activity_Resume
    getImage_forEdit

  	lblUser.Text =  Main.globName
	
	If EditArtist.ArtistUpdated Then
	   Dim MyT As CToast
	     MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)
		 
		 MyT.ToastMessageShow2("Your Artist information has been successfully updated",4,60,50, "", Colors.white, Colors.black, 20, True)
	End If
	
	EditArtist.ArtistUpdated = False
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub tbhPages_TabChanged
  Dim TabIdx As Int
  TabIdx = tbhPages.CurrentTab    ' Get the tab just activated
  Select TabIdx
    Case 0
      ' First tab is now active
    Case 1
      StartActivity("viewproduct")
    Case 2
      ' Third tab active
    Case Else
      Msgbox("Something is badly wrong! We have only three tabs", "HEY")
  End Select
End Sub

Sub imageUser_Click
	StartActivity("editArtist")
End Sub

Sub imageViewProd_Click
	StartActivity("viewproduct")
End Sub

Sub imageSales_Click
	StartActivity("sales")
End Sub

Sub imageEditProfile_Click
	StartActivity("editArtist")
End Sub

Sub imageAddProd_Click
    
	StartActivity("add")
End Sub



Sub imageLogOut_Click
	Activity.Finish
End Sub

Sub lblViewSales_Click
	imageSales_Click
End Sub

Sub lblViewProduct_Click
	imageViewProd_Click
End Sub

Sub lblUser_Click
	imageUser_Click
End Sub

Sub lblSync_Click
	imageSync_Click
End Sub

Sub lblLogout_Click
	imageLogOut_Click
End Sub
Sub lblEditProfile_Click
	imageEditProfile_Click
End Sub

Sub lblAddProd_Click
	imageAddProd_Click
End Sub

Sub Language_ItemClick (Position As Int, Value As Object)
	If Language.SelectedItem = "English" Then
		Main.globLang = "English"
	Else If Language.SelectedItem = "isiXhosa" Then
		Main.globLang = "isiXhosa"
	End If
	changeLanguage
End Sub


Sub imageSync_Click
	Dim  countToSync,addd,moodd As Int
	countToSync = 0
	
	'traverse through SQLite table and look for "mod" or "add"
	Main.cur = Main.aSQL.ExecQuery2("SELECT * FROM product WHERE ArtistID_fk = ?", Array As String(Main.m.Get("id")))
	
	
	
	For i = 0 To Main.cur.RowCount - 1
	
		Main.cur.Position = i
		If Main.cur.GetString("Status") = "add" Then
			      ProgressDialogShow("Adding Product(s) to TeleWeaver...")
			      addProdToTW
				  countToSync = countToSync + 1
				  addd = addd + 1
			
	    End If	
'	Next
	

'	For i = 0 To Main.cur.RowCount - 1
		Main.cur.Position = i
		 If Main.cur.GetString("Status") = "mod" Then
		      ProgressDialogShow("Updating Product(s) to TeleWeaver...")
		      modProdOnTW
			  countToSync = countToSync + 1
			  moodd = moodd + 1
			
			  
		 End If		 
'	Next 
'	

'	For i = 0 To Main.cur.RowCount - 1
		Main.cur.Position = i
		 If Main.cur.GetString("Status") = "del" Then
		      ProgressDialogShow("Deleting Product(s) from TeleWeaver...")
		      RemProdOnTW
			
			  countToSync = countToSync + 1
		 End If		 
	Next 
		
	If  countToSync = 0 Then
	   MyT.ToastMessageShow2("No Products need to be synchronized at this time.",6,60,50,"", Colors.white, Colors.black,20, True)
	End If
	

End Sub

Sub addProdToTW
    
		Dim postProdJob As HttpJob
		Dim m As Map
		Dim postProdUrl As String
		
	    postProdUrl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/add-product/"      
				
		m.Initialize
		m.Put("id", Main.cur.GetInt("ProductID"))
		m.Put("profile", Main.globID)
		m.Put("name", Main.cur.GetString("Name"))
		m.Put("dimension", Main.cur.GetString("Size"))
		m.Put("price", Main.cur.GetString("Price"))
		m.Put("description", Main.cur.GetString("Description"))
		m.Put("type",Main.cur.GetString("Type"))
		m.Put("quantity", 4) 
	    retrieveANDconvert     ' retrieves the image from SQLite
		m.Put("picture", hexaPicture)
		
		Dim JSONGenerator As JSONGenerator
	    JSONGenerator.Initialize(m)  		
       postProdJob.Initialize("PostProd", Me)
	   postProdJob.PostString(postProdUrl, JSONGenerator.ToString())
	   postProdJob.GetRequest.SetContentType("application/json")
                    

End Sub


Sub modProdOnTW
    
	Dim modProdJob As HttpJob
	Dim m As Map
	Dim modProdUrl As String	
	modProdUrl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/modify-product/"

	m.Initialize
	m.Put("id", Main.cur.GetInt("ProductID"))
	m.Put("profile", Main.globID)
	m.Put("name", Main.cur.GetString("Name"))
	m.Put("dimension", Main.cur.GetString("Size"))
	m.Put("price", Main.cur.GetString("Price"))
	m.Put("description", Main.cur.GetString("Description"))
	m.Put("type",Main.cur.GetString("Type"))
	m.Put("quantity", 23) 
    retrieveANDconvert     ' retrieves the image from SQLite
	m.Put("picture", hexaPicture)
		
	Dim JSONGenerator As JSONGenerator
	JSONGenerator.Initialize(m)	   

	modProdJob.Initialize("ModProd", Me)
	modProdJob.PostString(modProdUrl, JSONGenerator.ToString())
	modProdJob.GetRequest.SetContentType("application/json")


End Sub
Sub retrieveANDconvert    
   Dim su As StringUtils
   hexaPicture = su.EncodeBase64(Main.cur.Getblob("Picture"))

	   
End Sub


Sub JobDone (Job As HttpJob)
  ProgressDialogHide
       
	Log("JobName = " & Job.JobName & ", Success = " & Job.Success)
	If Job.Success = True Then
		Select Job.JobName
			Case "PostProd"
			   Log(Job.GetString)
			 '  Msgbox ( Main.cur.GetString("Name") & " has been synced and added","")
			  MyT.ToastMessageShow2("Product(s) have been successfully synchronized and added!",6,60,50,"", Colors.white, Colors.black,20, True)
			  Main.aSQL.ExecNonQuery2("UPDATE Product SET Status=? WHERE ProductID = " & Main.cur.GetInt("ProductID") , Array As Object("none"))
			Case "ModProd"
				Log(Job.GetString)	
				   ' Msgbox ( Main.cur.GetString("Name") & " has been modded syncd","")
					MyT.ToastMessageShow2("Products have been successfully synchronized and updated!",6,60,50,"", Colors.white, Colors.black,20, True)
					Main.aSQL.ExecNonQuery2("UPDATE Product SET Status=? WHERE ProductID = " & Main.cur.GetInt("ProductID") , Array As Object("none"))
			
	  End Select
	Else	
		Log("Error: " & Job.ErrorMessage)				
		MyT.ToastMessageShow2("Sorry could not sync with Teleweaver at this time. Try again later.",5,60,50, "", Colors.white, Colors.black,20, True)	
	End If
	
	Job.Release
	
End Sub



Sub RemProdOnTW   'Connect to the remote server and get the messages.

   Dim req As HttpRequest				'Set up an http request connection	
   Dim remProdUrl As String	
 	  
	remProdUrl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/remove-product/" & Main.globID
    req.InitializeGet(remProdUrl)	 'Initialize the http get request
    'ProgressDialogShow("Deleting Product on TeleWeaver...")
    hc.Execute(req, 1)						' And the execute it.


End Sub

Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)			'We got connection and data !!
    ProgressDialogHide	'Close the waiting message.
	
	Dim MyT As CToast
	MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)
    
    Dim resultString As String
    resultString = Response.GetString("UTF8")			'This holds the returned data 
	If resultString = "product removal unsuccessful" Then
	 
		   MyT.ToastMessageShow2("Sorry could not sync with Teleweaver at this time. Delete will be made when TeleWeaver is available.",8,60,50, "", Colors.white, Colors.black,20, True)
				
	Else
		   MyT.ToastMessageShow2("Product has been successfully deleted!",7,60,50,"", Colors.white, Colors.black,20, True)
		    Main.aSQL.ExecNonQuery2("UPDATE product SET Status=? WHERE ProductID = " & Main.globID , Array As Object("none")) 	
			
	End If
	
	' resultString = resultString.SubString(1) 'removes first character
    Log(resultString)
  
End Sub

Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)		'No connection :-(
   Log("Error connecting: " & Reason & " " & StatusCode)
    If Response <> Null Then
        Log(Response.GetString("UTF8"))
		ProgressDialogHide
        Response.Release
    End If
End Sub 

Sub imgHelpBtn_Click
	StartActivity("Help")
End Sub