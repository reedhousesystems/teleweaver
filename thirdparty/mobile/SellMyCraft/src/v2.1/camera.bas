Type=Activity
Version=2.5
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
	
End Sub

Sub OpenCam(Directory As String, PictureName As String)

    Dim i As Intent

    i.Initialize("android.media.action.IMAGE_CAPTURE", "")
    i.PutExtra("output", ParseUri("file://" & File.Combine(Directory, PictureName)))
               
    StartActivity(i)
    ScanFiles(Array As String(File.Combine(Directory, PictureName)))
                
End Sub

Sub ParseUri(FileName As String) As Object

    'Dim r As Reflector
	'Return r.RunStaticMethod("android.net.Uri", "parse", Array As Object(FileName), Array As String("java.lang.String"))

End Sub

Sub ScanFiles(files() As String)

    'Dim r As Reflector
    'r.RunStaticMethod("android.media.MediaScannerConnection", "scanFile", _
    '    Array As Object(r.GetContext, files, Null, Null), _
    '    Array As String("android.content.Context", "[Ljava.lang.String;", "[Ljava.lang.String;", _
    '       "android.media.MediaScannerConnection$OnScanCompletedListener"))
	
End Sub


