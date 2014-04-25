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
'	Dim btnReturn As Button
'	Dim ImageView1 As ImageView
'	Dim lblCompleted As Label
'	Dim lblHeader As Label
'	Dim lblProduct As Label
'	Dim lblQuantity As Label
'	Dim lblSort As Label
'	Dim ListView1 As ListView
'	Dim ListView2 As ListView
'	Dim ListView3 As ListView
'	Dim Spinner1 As Spinner
'	Dim ToggleButton1 As ToggleButton
'	
'	Dim EditText1 As EditText
'	Dim imageHome As ImageView
'	Dim Label1 As Label
	Dim lblHome As Label
'	Dim lblSales As Label
'	Dim listProducts As ListView
'	Dim listQuantityHand As ListView
'	Dim listQuantitySold As ListView
'	Dim listSales As ListView
'	Dim lblGrandTotal As Label
Dim hc As HttpClient
Dim wb As WebView
Dim mm As Map
  Dim sd As String 
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("Sales")
	 changeLanguage
	 hc.Initialize("hc")
	 fetchSales
    
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub fetchSales
    Dim remProdUrl As String	
 	Dim req As HttpRequest				'Set up an http request connection	  
	remProdUrl = "http://146.231.88.170:8080/reedhousesystems-its-africa-web/service/art/get-sales-by-product/Shield/"
    req.InitializeGet(remProdUrl)	 'Initialize the http get request
	
    ProgressDialogShow("Fetching Sales data from TeleWeaver...")
    hc.Execute(req,2)	


End Sub


Sub hc_ResponseSuccess (Response As HttpResponse, TaskId As Int)			'We got connection and data !!
    
    ProgressDialogHide	'Close the waiting message..

    Dim resultString As String
    resultString = Response.GetString("UTF8")			'This holds the returned data 
	' resultString = resultString.SubString(1) 'removes first character
    Log(resultString)
	
	   'begin JSON Parsing	
		Dim Jp As JSONParser
	    Dim aList As List
		Jp.Initialize(resultString)
		aList = Jp.NextArray
		
		
		mm = aList.Get(0)
		Dim SomeTime As Long
       Dim d,m, y As String
	   
	   SomeTime = mm.Get("saleDate")
	   y = DateTime.GetYear(SomeTime)
	   m = DateTime.GetMonth(SomeTime)
	   d = DateTime.GetDayOfMonth(SomeTime)
	   y = d & "/" & m & "/" & y
	
		Main.aSQL.ExecNonQuery("DELETE FROM Sales")
		Main.aSQL.ExecNonQuery2("INSERT INTO Sales VALUES(?,?,?,?,?)", Array As Object(mm.Get("id"),mm.Get("buyer"),mm.Get("quantity"),mm.Get("totalAmount"),y))
	    
		
		mm = aList.Get(1) 		
	   SomeTime = mm.Get("saleDate")
	   y = DateTime.GetYear(SomeTime)
	   m = DateTime.GetMonth(SomeTime)
	   d = DateTime.GetDayOfMonth(SomeTime)
	   y = d & "/" & m & "/" & y
        
		Main.aSQL.ExecNonQuery2("INSERT INTO Sales VALUES(?,?,?,?,?)", Array As Object(mm.Get("id"),mm.Get("buyer"),mm.Get("quantity"),mm.Get("totalAmount"),y))
     	   
	   sd = "SELECT * FROM Sales"	   
	   wb.LoadHtml(DBUtils.ExecuteHtml(Main.aSQL,sd,Null, 0, True))

	
End Sub


Sub hc_ResponseError (Response As HttpResponse, Reason As String, StatusCode As Int, TaskId As Int)		'No connection :-(
   Log("Error connecting: " & Reason & " " & StatusCode)
    If Response <> Null Then
        Log(Response.GetString("UTF8"))
		ProgressDialogHide
        Response.Release
    End If
End Sub 

Sub changeLanguage
	If Main.globlang = "English" Then 
'	
'		lblProduct.Text = "Product name"
'		lblQuantity.Text = "Quantity sold"
'		lblCompleted.Text = "Quantiy on hand"
'		lblSales.Text = "Total Sales (ZAR)"
'		lblGrandTotal.text = "Grand Total: R"
    lblHome.Text = "Home"
'	
	Else If Main.globlang = "isiXhosa" Then
'		lblHeader.Text = "Indibanisela"
'		lblProduct.Text = "Imveliso igama"
'		lblQuantity.Text = "Ezithengisiweyo"
'		lblCompleted.Text = "Ezishiyekileyo"
'		lblSales.Text = "Indibanisela (ZAR)"
'		lblGrandTotal.text = "Indibanisela yazozonke: R"
		lblHome.Text = "Ekhaya"
'		
	End If
	
End Sub
Sub Spinner1_ItemClick (Position As Int, Value As Object)
	
End Sub

Sub lblHome_Click
	imageHome_Click
End Sub

Sub imageHome_Click
	Activity.Finish
End Sub



Sub JobDone(Job As HttpJob)
''	ProgressDialogHide
''	If Job.Success Then
''	Dim res As String
''		res = Job.GetString
''		Log("Response from server: " & res)
''		Dim parser As JSONParser
''		parser.Initialize(res)
''	
''		Select Job.JobName
''			Case COUNTRIES_LIST
''				Dim COUNTRIES As List
''				COUNTRIES = parser.NextArray 'returns a list with maps
''				For i = 0 To COUNTRIES.Size - 1
''					Dim m As Map
''					m = COUNTRIES.Get(i)
''					'We are using a custom type named TwoLines (declared in Sub Globals).
''					'It allows us to later get the two values when the user presses on an item.
''					Dim tl As TwoLines
''					tl.First = m.Get("id")
''					tl.Second = m.Get("name")
''					ListView1.AddTwoLines2(tl.First, tl.Second, tl)
''				Next
''			Case COUNTRY_POPULATION
''				Dim l As List
''				l = parser.NextArray
''				If l.Size = 0 Then
''					lblPopulation.Text = "N/A"
''				Else
''					Dim m As Map
''					m = l.Get(0)
''					lblPopulation.Text = NumberFormat2(m.Get("population"),0, 0, 0, True) & " (K)"
''				End If
''		End Select 
''	Else
''		ToastMessageShow("Error: " & Job.ErrorMessage, True)
''	End If
''	Job.Release
End Sub