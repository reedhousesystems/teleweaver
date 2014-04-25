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
 Dim CC As ContentChooser
 Dim Timer1 As Timer

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim picSelected As Boolean
	Dim scvMain As ScrollView
	Dim pnlEdit_Products As Panel
	Dim btnEditBack As Button
	Dim imageviewOnEdit, imagevTEMP As ImageView
	Dim pictureAnswer As String
	Dim txtEDITName As EditText
	Dim txtEDITprice As EditText
	Dim btnUpdate As Button
	Dim theProdID As Int
	Dim spEDITtype As Spinner
	Dim txtSize As EditText
	Dim hexaPicture As String
	Dim su As StringUtils
	Dim Dir2, FileName2 As String
	Dim ImagevwEDITProdPic As ImageView 
	Dim iCounter As Int
	Dim Buff() As Byte 'declare an empty byte array
	Dim i2 As Int
	Dim lblemptyprice As Label
	Dim lblemptyprod As Label
	Dim lblWrongSize As Label
	Dim txtType As EditText
	Dim lblpricenumber As Label
	Dim txtEDITDesc As EditText
	Dim Bitt As Bitmap	
	Dim bool_view As Boolean
	Dim aLabel1 As Label
	Dim imageUpdate As ImageView
	Dim txtEditSize As EditText
	Dim ImageView1 As ImageView
	
	Dim lblEditProdName As Label
	Dim lblEditProdPrice As Label
	Dim lblEditProdType As Label
	Dim lblEditProdSize As Label
	 Dim errorDelOnTW As Boolean
	Dim lblEditProdDes As Label
	Dim lblUpdate As Label
	Dim lblCancel As Label
	Dim Label1 As Label
	Dim myMsgBox As CustomMsgBox
	Dim btnDelIDTag As Int
	Dim hc As HttpClient
End Sub

Sub Activity_Create(FirstTime As Boolean)
	scvMain.Initialize(50)
	Activity.AddView(scvMain,0,120,100%x,100%y)
'	Activity.Height = 50dip
	pnlEdit_Products.Initialize("")
	Activity.AddView(pnlEdit_Products, 0, 10, 100%x, 100%y)
	pnlEdit_Products.LoadLayout("pnlEdit_products")
	spEDITtype.AddAll(Array As String("Art","Craft"))
	pnlEdit_Products.Visible = False
	CC.Initialize("chooser2")
	getProducts
lblemptyprod.Visible = False
lblemptyprice.Visible = False
lblWrongSize.Visible = False
lblpricenumber.Visible = False
changeLanguage



	hc.Initialize("hc")	
End Sub

Sub Activity_Resume
getProducts
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	'Activity.Finish
End Sub

Sub changeLanguage
	If Main.globlang = "English" Then
		Label1.text = "Edit Product Information"
		lblEditProdName.text = "Product name"
		txtEDITName.hint = "Edit name here"
		lblEditProdPrice.text = "Product price"
		txtEDITprice.Hint = "Edit price here"
		lblEditProdType.text = "Product type"
		spEDITtype.Prompt = "Select product type"
		lblEditProdSize.text = "Product dimensions"
		txtEditSize.Hint = "Edit dimensions here"
		lblEditProdDes.text = "Product description"
		txtEDITDesc.Hint = "Edit description here"
		lblUpdate.text = "Save"
		lblCancel.text = "Cancel"
		
		lblpricenumber.Text = "Product Price should be a number"
		lblemptyprice.Text = "Please enter a Product Price"
		lblemptyprod.Text = "Please enter a Product Name"
		lblWrongSize.Text = "Product size should be a number"
		
	Else If Main.globlang = "isiXhosa" Then
		Label1.text = "Hlela umveliso linkcukacha"
		lblEditProdName.text = "Imveliso igama"
		txtEDITName.hint = "Hlela igama apha"
		lblEditProdPrice.text = "Imveliso ixabiso"
		txtEDITprice.Hint = "Hlela ixabiso apha"
		lblEditProdType.text = "Umhlobo wemveliso"
		spEDITtype.Prompt = "Khetha umhlobo wemveliso"
		lblEditProdSize.text = "Umlinganiselo wobude"
		txtEditSize.Hint = "Hlela umlinganiselo wobude"
		lblEditProdDes.text = "Cacisa umveliso"
		txtEDITDesc.Hint = "Hlela cacisa umveliso"
		lblUpdate.text = "Gcina"
		lblCancel.text = "Cima"
	End If
	End Sub
	
Public Sub getProducts

	Dim Panel0 As Panel
	Dim PanelTop, PanelHeight, aLabelel2Top As Int
	Dim lblTitle As Label
	Dim fisrtTime, totInTable As Int
	totInTable = 0
	Activity.Title = "View Products Details"
	Activity.Color = Colors.RGB(255, 250, 240)
	'image  next to title
	Dim ImageViewLogo As ImageView  
	ImageViewLogo.Initialize("Im")   
	ImageViewLogo.Bitmap = LoadBitmapSample  (File.DirAssets, "sellmycraft.png",45dip,45dip)
	Activity.AddView(ImageViewLogo,0dip,0dip,90dip,80dip)
	
	
		
	Main.cur = Main.aSQL.ExecQuery2("SELECT * FROM product WHERE ArtistID_fk = ?", Array As String(Main.m.Get("id")))	
	
	
	'Creates the underline under the panel
	PanelTop = 5dip
	Panel0 = scvMain.Panel
	Panel0.Color = Colors.black
	
	PanelHeight = 140dip

	For i = 0 To Main.cur.RowCount - 1
		Main.cur.Position = i
 		Dim ID As Int
        totInTable = totInTable + 1
		'create new 'views' for components
		Dim Panel1 As Panel
		ID = Main.cur.GetInt("ProductID")
		theProdID = ID
	'The main black panel
	 	Panel1.Initialize("View")
		
		'Tags are used to allow click event
		Panel1.Tag = ID
		'panel1.AddView(left,top,w,h)
	   Panel0.AddView(Panel1,0, PanelTop,scvMain.Width,PanelHeight)
		Panel1.Color = Colors.RGB(255, 250, 240)
		

'title
		Dim aLab As Label
		aLab.Initialize("aLab")
		Activity.AddView(aLab,92dip,15dip,240dip,40dip)
		aLab.TextColor = Colors.black  
		aLab.TextSize = 23
		If Main.globlang = "isiXhosa" Then
'		    aLab.text = ""
			aLab.text = "Umveliso linkcukacha"
'			aLab.TextSize = 20
		Else
'		    aLab.text = ""
			aLab.text = "Saved Products " 
		End If
		
	'------------------------------------------	
	    Dim ImageView2 As ImageView
	    ImageView2.Initialize("ImageView2")
	    ImageView2.Tag = ID  		
        ImageView2.Bitmap = LoadBitmapSample(File.DirAssets, "garbage.png",40dip,32dip)
		
		Panel1.AddView(ImageView2,270dip,7dip,40dip,32dip)	
	'------------------------------------------	
	 Dim ImageView3 As ImageView
	    ImageView3.Initialize("ImageView3")
	    ImageView3.Tag = ID  		
        ImageView3.Bitmap = LoadBitmapSample(File.DirAssets, "edit.png",40dip,32dip)
		
		Panel1.AddView(ImageView3,215dip,7dip,40dip,32dip)
	'-------------------------------------------

		
	'product name
		Dim aLabel As Label
		aLabel.Initialize("aLabel")
		Panel1.AddView(aLabel,90dip,5dip,240dip,40dip)
		aLabel.TextColor = Colors.black
		aLabel.TextSize = 20
		aLabel.Tag=ID
		aLabel.text = Main.cur.GetString("Name")
	
	'Size	
		Dim aLabel As Label
		aLabel.Initialize("aLabel")
		Panel1.AddView(aLabel,90dip,40dip,50dip,30dip)
		aLabel.TextColor = Colors.black
		aLabel.Tag = ID
		aLabel.Width = 95dip
		aLabel.text = Main.cur.GetString("Size") & aLabel.text  & " (cm)"

	'Price
		Dim aLabel As Label
		aLabel.Initialize("aLabel")
		Panel1.AddView(aLabel,190dip,40dip,50dip,30dip)
		aLabel.TextColor = Colors.black
		aLabel.Tag = ID
		aLabel.Width = 95dip
		aLabel.text =aLabel.text  & "R" & Main.cur.GetString("Price") & ".00"
  
	'Descr
		Dim aLabel As Label
		aLabel.Initialize("aLabel")
		Panel1.AddView(aLabel,90dip,80dip,300dip,150dip)
		aLabel.TextColor = Colors.black
		aLabel.Tag = ID
		aLabel.Height = 100dip
		aLabel.Width = 230dip
		aLabel.text = Main.cur.GetString("Description") & ID
	
	'Type
		Dim aLabel As Label
		aLabel.Initialize("aLabel")
		Panel1.AddView(aLabel,270dip,40dip,50dip,30dip)
		aLabel.TextColor = Colors.black
		aLabel.Tag = ID
		aLabel.Width = 95dip
		aLabel.text = Main.cur.GetString("Type")
			
	'Picture_product		
		
		ImageView1.Initialize("ImageView1")
		ImageView1.Tag = ID  
        bool_view = True
		getImage  ' collect the image from DB	
		
		Panel1.AddView(ImageView1,3dip,5dip,80dip,80dip)
	   
		PanelTop = PanelTop + PanelHeight + 1dip
	Next
'	  If Main.globlang = "isiXhosa" Then
'		    aLab.text = ""
'			aLab.text = "Umveliso linkcukacha (" & totInTable & ")"
'			aLab.TextSize = 20
'		Else
'		    aLab.text = ""
'			aLab.text = "Saved Products (" & totInTable & ")"	 
'		End If
	Panel0.Height = PanelTop
	
End Sub


Sub Activity_KeyPress(KeyCode As Int) As Boolean 

'back Button makes edit Panel disappear
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		If pnlEdit_Products.Visible = True Then
			pnlEdit_Products.Visible = False
			aLabel1.TextColor = Colors.black
			Return True
		Else
			Return False
		End If
	End If
	
End Sub

Sub View_LongClick
	Dim Send As View
	bool_view = False
	Send = Sender
	theProdID = Send.Tag
	
	Main.cur = Main.asql.ExecQuery("SELECT * FROM Product WHERE ProductID = " & Send.Tag)
	Main.cur.Position = 0
	
	txtEDITName.Text = Main.cur.GetString("Name")
	txtEDITprice.Text = Main.cur.GetString("Price")
	txtEditSize.Text = Main.cur.GetString("Size")
	'spEDITtype.SelectedIndex = Main.cur.GetString("Type")
	txtEDITDesc.Text = Main.cur.GetString("Description")
	
		getImage_forEdit
		
    aLabel1.TextColor = Colors.black
	pnlEdit_Products.Visible = True
	

End Sub
Sub View_Click
	getProducts
End Sub

Sub ImageView2_Click

Dim Send As ImageView

	Send = Sender
	btnDelIDTag =Send.Tag
	
		
   showDelMsgBox
		
 End Sub

Sub btnUpdate_Click

lblemptyprod.Visible = False
lblemptyprice.Visible = False
lblpricenumber.Visible = False
lblWrongSize.Visible = False

   If txtEDITName.Text.Trim.Length = 0 Then 
		lblemptyprod.Visible = True
		txtEDITprice.RequestFocus
	Else If txtEDITprice.Text.Trim.Length = 0 Then	
		lblemptyprice.Visible = True
		txtEDITprice.RequestFocus
	Else If Not (IsNumber (txtEDITprice.Text)) Then
			lblpricenumber.Visible = True
		  txtEDITprice.RequestFocus
	Else
	
	    Main.cur = Main.aSQL.ExecQuery("SELECT * FROM Product WHERE ProductID = " & theProdID)
		Main.cur.Position = 0
		
		
		 'ATTMEPT UPDATE ON TW
		'=======================
		ProgressDialogShow("Modifying Product on TeleWeaver...")
		 modProdOnTW
		'======================= 
			
		
	    If picSelected Then 'check if a different image was actually selected from gallery
			 Insert_Image		
		     Main.aSQL.ExecNonQuery2("UPDATE product SET Name=?, Price=?, Size=?, Type=?, Description=?, Picture=? WHERE ProductID=" & theProdID , Array As Object(txtEDITName.Text,txtEDITprice.Text,txtEditSize.Text, spEDITtype.SelectedItem,txtEDITDesc.Text, Buff))		   
		Else
			Main.aSQL.ExecNonQuery("UPDATE product SET Name = '" & txtEDITName.Text & "' , Price = " & txtEDITprice.Text &  ", Description = '" & txtEDITDesc.Text & "' , Size = '" & txtEditSize.Text & "' , Type = '" & spEDITtype.SelectedItem & "' WHERE ProductID = " & theProdID)
		End If 
	 	
	'	Main.aSQL.ExecNonQuery2("UPDATE product SET Status=? WHERE ProductID = " & theProdID , Array As Object("mod"))
		getProducts  ' refresh page
		 

		pnlEdit_Products.Visible = False
		picSelected = False
   End If
	
	
End Sub
Sub modProdOnTW

	Dim modProdJob As HttpJob
	Dim m As Map
	Dim modProdUrl As String	
	modProdUrl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/modify-product/"

	m.Initialize
	m.Put("id", theProdID)
	m.Put("profile", Main.m.Get("id"))
	m.Put("name", txtEDITName.Text)
	m.Put("dimension", txtEditSize.Text)
	m.Put("price", txtEDITprice.Text)
	m.Put("description", txtEDITDesc.Text)
	m.Put("type", spEDITtype.SelectedItem)
	m.Put("quantity", 2)
	If picSelected Then
	    retrieveANDconvert     ' retrieves the image from SQLite
		m.Put("picture", hexaPicture)
	End If
	
	Dim JSONGenerator As JSONGenerator
	JSONGenerator.Initialize(m)	   

	modProdJob.Initialize("ModProd", Me)
	modProdJob.PostString(modProdUrl, JSONGenerator.ToString())
	modProdJob.GetRequest.SetContentType("application/json")


End Sub
Sub retrieveANDconvert    
 	
   hexaPicture = su.EncodeBase64(Buff)
 '  Msgbox(hexaPicture,"hexa")
	   
End Sub

Sub RemProdOnTW   'Connect to the remote server and get the messages.

   Dim req As HttpRequest				'Set up an http request connection	
   Dim remProdUrl As String	
 	  
	remProdUrl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/remove-product/" & btnDelIDTag
    req.InitializeGet(remProdUrl)	 'Initialize the http get request
    ProgressDialogShow("Deleting Product on TeleWeaver...")
    hc.Execute(req, 1)						' And the execute it.


End Sub
Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)			'We got connection and data !!
     Dim MyT As CToast
	MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)
    ProgressDialogHide	'Close the waiting message..

    Dim resultString As String
    resultString = Response.GetString("UTF8")			'This holds the returned data 
	If resultString = "product removal unsuccessful" Then
	     
		   MyT.ToastMessageShow2("Sorry could not sync with Teleweaver at this time. Delete will be made when TeleWeaver is available.",8,60,50, "", Colors.white, Colors.black,20, True)
		
			
		   Main.aSQL.ExecNonQuery2("UPDATE product SET Status=? WHERE ProductID = " & theProdID , Array As Object("del")) ' remember it needs to be re-synced. LATERRR
	
	Else
			MyT.ToastMessageShow2("Product has been successfully deleted!",5,60,50,"", Colors.white, Colors.black,20, True)
		    Main.aSQL.ExecNonQuery("DELETE FROM product WHERE ProductID = " & btnDelIDTag)
			   
	End If
	' resultString = resultString.SubString(1) 'removes first character
   ' Log(resultString)
		
End Sub

Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)		'No connection :-(
   Log("Error connecting: " & Reason & " " & StatusCode)
    If Response <> Null Then
        Log(Response.GetString("UTF8"))
		ProgressDialogHide
        Response.Release
    End If
End Sub 


Sub JobDone (Job As HttpJob)
  ProgressDialogHide
       Dim MyT As CToast
	    MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)
	Log("JobName = " & Job.JobName & ", Success = " & Job.Success)
	If Job.Success = True Then
		Select Job.JobName			
			Case "ModProd"
				Log(Job.GetString)	
				MyT.ToastMessageShow2("Product has been successfully synchronized and updated!",6,60,50,"", Colors.white, Colors.black,20, True)	
      		End Select
	Else	
		Log("Error: " & Job.ErrorMessage)				
		MyT.ToastMessageShow2("Sorry could not sync with Teleweaver at this time. Changes were only local.",5,60,50, "", Colors.white, Colors.black,20, True)
		
		If Job.JobName = "ModProd" Then  ' no need to update later for delete
		   Main.aSQL.ExecNonQuery2("UPDATE product SET Status=? WHERE ProductID = " & theProdID , Array As Object("mod"))  ' remember it needs to be re-synced. LATERRR
		End If
		
	End If
	
	Job.Release
	
End Sub
Sub Insert_Image
    
    Dim InputStream1 As InputStream	
	If picSelected Then 'check if a different image was actually selected from gallery
		InputStream1 = File.OpenInput(Dir2, FileName2)
	End If 
	
    Dim OutputStream1 As OutputStream
    OutputStream1.InitializeToBytesArray(1000)
    File.Copy2(InputStream1, OutputStream1)    
    Buff = OutputStream1.ToBytesArray

	  
End Sub


 Sub getImage 
   
		Dim InputStrea1 As InputStream  
		Buff = Null
		If Main.cur.GetString("PicExist") = "yes" Then
	       Buff = Main.cur.GetBlob("Picture")
	        InputStrea1.InitializeFromBytesArray(Buff, 0, Buff.Length)  
		  
		    Bitt.Initialize2(InputStrea1)
		    InputStrea1.Close		
			
			 ImageView1.SetBackgroundImage(Bitt)
   		 Else
			ImageView1.Bitmap = LoadBitmapSample  (File.DirAssets, "empty_gallery.png",ImageView1.Width,ImageView1.Height)	
		End If
End Sub


Sub getImage_forEdit
   
		Dim InputStrea1 As InputStream    
		Buff = Null

		If Main.cur.GetString("PicExist") = "yes" Then
		       Buff = Main.cur.GetBlob("Picture")
		        InputStrea1.InitializeFromBytesArray(Buff, 0, Buff.Length)  			  
			    Bitt.Initialize2(InputStrea1)
			    InputStrea1.Close				
				ImagevwEDITProdPic.SetBackgroundImage(Bitt)
				
   		 Else
				ImagevwEDITProdPic.Bitmap = LoadBitmapSample  (File.DirAssets, "empty_gallery.png",ImagevwEDITProdPic.Width,ImagevwEDITProdPic.Height)	
		End If
		
End Sub

Sub chooser2_Result (Success As Boolean, Dir As String, FileName As String)

	If Success Then 
		ImagevwEDITProdPic.Bitmap = LoadBitmapSample  (Dir, FileName,ImagevwEDITProdPic.Width,ImagevwEDITProdPic.Height)
		picSelected = Success
		Dir2 = Dir
		FileName2 = FileName 
		
	End If

End Sub

Sub ImagevwEDITProdPic_Click	
  showMsgBoxCamera
End Sub


Sub imageCancel_Click
pnlEdit_Products.Visible = False
			aLabel1.TextColor = Colors.white
			Return True
End Sub

Sub spEDITtype_ItemClick (Position As Int, Value As Object)
	
End Sub
Sub imageUpdate_Click
	btnUpdate_Click
End Sub
Sub lblCancel_Click
	
End Sub

Sub ImageView3_Click

	 Dim Send As ImageView
	 Dim btnIDTag As Int
	Send = Sender
	btnIDTag =Send.Tag
	
	theProdID = Send.Tag
	
	Main.cur = Main.asql.ExecQuery("SELECT * FROM Product WHERE ProductID = " & Send.Tag)
	Main.cur.Position = 0
	
	txtEDITName.Text = Main.cur.GetString("Name")
	txtEDITprice.Text = Main.cur.GetString("Price")
	txtEditSize.Text = Main.cur.GetString("Size")
	'spEDITtype.SelectedIndex = Main.cur.GetString("Type")
	txtEDITDesc.Text = Main.cur.GetString("Description")
	getImage_forEdit
	pnlEdit_Products.Visible = True
		
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
    End If
	     myMsgBox.Title.Text = "Select Picture"
		 myMsgBox.Title.TextSize = 25
		 myMsgBox.ShowMessage("Please select an option","C")
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
		CC.show("image/*", "Choose a Picture")
'		Dim i As Intent
'                i.Initialize(i.ACTION_PICK, "")
'				i.SetType("image/*")
'    	StartActivity(i)
	End If	 
	 
End Sub

Sub showDelMsgBox   
	myMsgBox.Initialize(Activity, Me, "Del", "H", 2, 95%x, 200dip, LoadBitmap(File.DirAssets, "warningicon.png"))

	
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
		myMsgBox.Message.TextSize = 16  
		myMsgBox.Title.TextSize = 23
      
    If Main.globlang = "isiXhosa" Then
	   'XHOSA
	'   myMsgBox.NoButtonCaption.Text = "Xha"
	'	myMsgBox.YesButtonCaption.Text = "Ewe"
	'	  myMsgBox.Title.Text = "Delete Product"		
	'	 myMsgBox.ShowMessage("Are you sure you wish to remove this product?","C")
    Else 
	     myMsgBox.Title.Text = "Delete Product"		
		 myMsgBox.ShowMessage("Are you sure you wish to remove this product?","C")
    End If
	
End Sub

Sub Del_Click  

    Dim MyT As CToast
	MyT.Initialize(Activity, Me, Activity.Height, Activity.Width)
	    If myMsgBox.ButtonSelected = "yes" Then	 		
			    'ATTMEPT DELETE ON TW
				'=======================
				RemProdOnTW		
		         
		End If		 

	getProducts  ' refresh page
	
End Sub



