Type=Class
Version=2.71
@EndOfDesignText@
'Class module

'CustomMsgBox
'By NJDude - Version 1.4
'Donations are appreciated.

Sub Class_Globals

				Private MsgModule As Object
				Private BackPanel As Panel
				Private MsgOrientation As String
				Private MsgNumberOfButtons As Int
						
				Private mbIcon As ImageView
				Dim Panel As Panel
				Private Shadow As Panel
				Dim Title As Label
    			Private MsgScrollView As ScrollView
				Dim Message As Label
				Dim YesButtonPanel As Panel
				Dim NoButtonPanel As Panel
				Dim CancelButtonPanel As Panel
				
				Dim YesButtonCaption As Label
				Dim NoButtonCaption As Label
				Dim CancelButtonCaption As Label
				
				Private MsgBoxEvent As String
				Dim ButtonSelected As String

End Sub

'Initializes the Custom MsgBox.
'Activity        = Activity a MANDATORY setting, do not change
'Module          = Me a Mandatory setting, do not change
'MsgBoxName      = The event name that will receive the value of the pressed button
'Orientation     = H (Horizontal - default) V (Vertical)
'NumberOfButtons = Number of button on the MsgBox (minimum 1, maximum 3)
'Width           = Custom MsgBox width (dip or %)
'Height          = Custom MsgBox height (dip or %)
'Icon            = Icon for the Custom MsgBox (pass NULL if no icon is wanted)
Public Sub Initialize(Activity As Activity, Module As Object, MsgBoxName As String, Orientation As String, NumberOfButtons As Int, Width As Double, Height As Double, Icon As Bitmap)

							MsgModule = Module
							MsgOrientation = Orientation
							MsgNumberOfButtons = NumberOfButtons

							Delta = ((100%x + 100%y) / (320dip + 480dip) - 1)
       Rate = 0.3
       Scale = Rate * Delta	+ 1

							MsgBoxEvent = MsgBoxName
							
							BackPanel.Initialize("BackPanel")
							
							Panel.Initialize("")
							Shadow.Initialize("")
							mbIcon.Initialize("")
							Title.Initialize("")
       MsgScrollView.Initialize(0dip)
							Message.Initialize("")
							YesButtonPanel.Initialize("PressedButton")
							NoButtonPanel.Initialize("PressedButton")
							CancelButtonPanel.Initialize("PressedButton")
							YesButtonCaption.Initialize("")
							NoButtonCaption.Initialize("")
							CancelButtonCaption.Initialize("")
							
							Activity.AddView(BackPanel, 0dip, 0dip, 100%x, 100%y)

							BackPanel.Color = Colors.ARGB(160, 0, 0, 0)
							
							BackPanel.AddView(Panel, 0dip, 0dip, 100%x, 100%y)
							
							Panel.Width = Width
							Panel.Height = Height
							Panel.Color = Colors.DarkGray
							Panel.Left = (100%x - Panel.Width) / 2
							Panel.Top = (100%y - Panel.Height) / 2		
       BackPanel.AddView(Shadow, Panel.Left + 5dip, Panel.Top + 5dip, Panel.Width, Panel.Height)
       Shadow.SendToBack
							Panel.AddView(mbIcon, 5dip, 5dip, 50dip, 50dip)
							Panel.AddView(Title, 60dip, 0dip, Panel.Width, 60dip)
							Panel.AddView(MsgScrollView, 5dip, 70dip, Panel.Width, 60dip)
							
							Panel.AddView(YesButtonPanel, 0dip, Panel.Height - 50dip, 50dip, 50dip)
							Panel.AddView(NoButtonPanel, 0dip, Panel.Height - 50dip, 50dip, 50dip)
							Panel.AddView(CancelButtonPanel, 0dip, Panel.Height - 50dip, 50dip, 50dip)			
  				
       MsgScrollView.Panel.AddView(Message, 0dip, 0dip, MsgScrollView.Width, MsgScrollView.Height)		

       Message.Height = YesButtonPanel.Top - Message.Top - 10dip
       MsgScrollView.Height = YesButtonPanel.Top - MsgScrollView.Top - 10dip
       MsgScrollView.Panel.Height = MsgScrollView.Height
	
							If Orientation.ToUpperCase = "V" Then
							
							   YesButtonPanel.Top = Panel.Height - (50dip * NumberOfButtons)
										NoButtonPanel.Top = YesButtonPanel.Top + 52dip
										CancelButtonPanel.Top = NoButtonPanel.Top + 52dip

							End If

							If Icon.IsInitialized Then
							
							   mbIcon.SetBackgroundImage(Icon)
										
							Else
							
							   Title.Text = Title.Text.Trim
										Title.Left = 5dip
										
							End If
			
       Title.Width = Panel.Width - Title.Left - 10dip
							Title.Gravity = Gravity.CENTER_VERTICAL
							Title.TextColor = Colors.Cyan
							Title.TextSize = Title.TextSize * Scale + 4
							
							Message.Width = Panel.Width - Message.Left - 10dip
							'Message.Gravity = Gravity.CENTER_VERTICAL
							Message.TextColor = Colors.White
							Message.TextSize = Message.TextSize * Scale + 1
														
							If Orientation.ToUpperCase = "V" Then
							
							   ButtonSize = Panel.Width
							
							Else
							
							   ButtonSize = (Panel.Width / NumberOfButtons) - 2
										
							End If
							
							YesButtonPanel.Color = Colors.DarkGray
							YesButtonPanel.Width = ButtonSize
							YesButtonPanel.Left = 0dip
							YesButtonPanel.Tag = "yes"
							YesButtonPanel.AddView(YesButtonCaption, 0dip, 0dip, YesButtonPanel.Width, 30dip)
							
							YesButtonCaption.Gravity = Gravity.CENTER_HORIZONTAL
							YesButtonCaption.Top = ((YesButtonPanel.Height - YesButtonCaption.Height) / 2) + 5dip
							YesButtonCaption.TextColor = Colors.White
							YesButtonCaption.Text = "Yes"
							
							NoButtonPanel.Color = Colors.DarkGray
							NoButtonPanel.Width = ButtonSize
							
							If Orientation.ToUpperCase = "V" Then
							
							   NoButtonPanel.Left = 0dip
							
							Else
							
							   NoButtonPanel.Left = YesButtonPanel.Width + 4dip
										
							End If
			
							NoButtonPanel.Tag = "no"
							NoButtonPanel.AddView(NoButtonCaption, 0dip, 0dip, NoButtonPanel.Width, 30dip)
							
							NoButtonCaption.Gravity = Gravity.CENTER_HORIZONTAL
							NoButtonCaption.Top = ((NoButtonPanel.Height - NoButtonCaption.Height) / 2) + 5dip
							NoButtonCaption.TextColor = Colors.White
							NoButtonCaption.Text = "No"

							CancelButtonPanel.Color = Colors.DarkGray
							CancelButtonPanel.Width = ButtonSize
							
							If Orientation.ToUpperCase = "V" Then
							
							   CancelButtonPanel.Left = 0dip
							
							Else
							
							   CancelButtonPanel.Left = YesButtonPanel.Width + NoButtonPanel.Width + 8dip
										
							End If
							
							CancelButtonPanel.Tag = "cancel"
							CancelButtonPanel.AddView(CancelButtonCaption, 0dip, 0dip, CancelButtonPanel.Width, 30dip)
							
							CancelButtonCaption.Gravity = Gravity.CENTER_HORIZONTAL
							CancelButtonCaption.Top = ((CancelButtonPanel.Height - CancelButtonCaption.Height) / 2) + 5dip
							CancelButtonCaption.TextColor = Colors.White
							CancelButtonCaption.Text = "Cancel"
							
							ShowSeparators(Colors.Cyan, Colors.Gray)

End Sub

'Text to display on the Custom MsgBox
'Alignment: L = Left, C = Center, R = Right
Public Sub ShowMessage(BoxMessage As String, Alignment As String)

       Dim su As StringUtils

       Locate = Gravity.LEFT
							
							Select Case Alignment.ToUpperCase
							
							       Case "L"
														
														     Locate = Gravity.LEFT
																			
														Case "C"
														
														     Locate = Gravity.CENTER
														
														Case "R"
														
														     Locate = Gravity.RIGHT
														
							End Select
							
							Message.Text = BoxMessage
							Message.Gravity = Locate

       MsgScrollView.Panel.Height = su.MeasureMultilineTextHeight(Message, Message.Text) 
       Message.Height = su.MeasureMultilineTextHeight(Message, Message.Text)

End Sub

'RichString to display on the Custom MsgBox
'Alignment: L = Left, C = Center, R = Right
Public Sub ShowRichMessage(BoxMessage As RichString, Alignment As String)

       Dim su As StringUtils
							
       Locate = Gravity.LEFT
							
							Select Case Alignment.ToUpperCase
							
							       Case "L"
														
														     Locate = Gravity.LEFT
																			
														Case "C"
														
														     Locate = Gravity.CENTER
														
														Case "R"
														
														     Locate = Gravity.RIGHT
														
							End Select
							
							Message.Text = BoxMessage
							Message.Gravity = Locate

       MsgScrollView.Panel.Height = su.MeasureMultilineTextHeight(Message, Message.Text) 
       Message.Height = su.MeasureMultilineTextHeight(Message, Message.Text)

End Sub

'Display line under the Custom MsgBox and button separators
Public Sub ShowSeparators(TitleSeparator As Int, ButtonSeparator As Int)

       Dim LineSeparator As Canvas
							
							LineSeparator.Initialize(Panel)
							LineSeparator.DrawLine(0dip, 65dip, 100%x, 65dip, TitleSeparator, 3)
							LineSeparator.DrawLine(0dip, YesButtonPanel.Top - 2dip, 100%x, YesButtonPanel.Top - 2dip, ButtonSeparator, 1)
							
							If MsgNumberOfButtons = 2 AND MsgOrientation.ToUpperCase <> "V" Then

										LineSeparator.DrawLine(Panel.Width / 2, YesButtonPanel.Top - 2dip, Panel.Width / 2, Panel.Height, ButtonSeparator, 1)
							
							Else

										LineSeparator.DrawLine(0dip, NoButtonPanel.Top - 2dip, Panel.Width, NoButtonPanel.Top - 2dip, ButtonSeparator, 1)
										
							End If
							
							If MsgNumberOfButtons = 3 AND MsgOrientation.ToUpperCase <> "V" Then

										LineSeparator.DrawLine(Panel.Width / 3, YesButtonPanel.Top - 2dip, Panel.Width / 3, Panel.Height, ButtonSeparator, 1)
										LineSeparator.DrawLine(Panel.Width / 3 + YesButtonPanel.Width + 3dip, YesButtonPanel.Top - 2dip, Panel.Width / 3 + YesButtonPanel.Width + 3dip, Panel.Height, ButtonSeparator, 1)
							
							Else
							
							   LineSeparator.DrawLine(0dip, CancelButtonPanel.Top - 2dip, Panel.Width, CancelButtonPanel.Top - 2dip, ButtonSeparator, 1)
										
							End If							

		     Panel.Invalidate		
							
End Sub

'Display a drop shadow
Public Sub ShowShadow(Color As Int)

       Shadow.Color = Color
							
End Sub

Private Sub PressedButton_Touch(Action As Int, X As Float, Y As Float)

								Dim Pressed As Panel
				
				    Pressed = Sender

				    If Action = 0 Then

											Pressed.Color = Colors.ARGB(255, 0, 140, 140)
							
				    End If

				    If Action = 1 Then

											Pressed.Color = Colors.DarkGray
											
											ButtonSelected = Pressed.Tag
										
											BackPanel.Visible = False

											If SubExists(MsgModule, MsgBoxEvent & "_Click") Then
											
											   CallSubDelayed(MsgModule, MsgBoxEvent & "_Click")

											End If

				    End If

End Sub

Private Sub BackPanel_Touch(Action As Int, X As Float, Y As Float)
        'Consume touch
End Sub