package b4a.sysdev;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class dbutils {
private static dbutils mostCurrent = new dbutils();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static String _db_real = "";
public static String _db_integer = "";
public static String _db_blob = "";
public static String _db_text = "";
public static String _htmlcss = "";
public b4a.sysdev.httputils2service _httputils2service = null;
public b4a.sysdev.main _main = null;
public b4a.sysdev.menu _menu = null;
public b4a.sysdev.viewproduct _viewproduct = null;
public b4a.sysdev.details _details = null;
public b4a.sysdev.add _add = null;
public b4a.sysdev.sales _sales = null;
public b4a.sysdev.editartist _editartist = null;
public b4a.sysdev.help _help = null;
public static String  _copydbfromassets(anywheresoftware.b4a.BA _ba,String _filename) throws Exception{
		Debug.PushSubsStack("CopyDBFromAssets (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
String _targetdir = "";
Debug.locals.put("ba", _ba);
Debug.locals.put("FileName", _filename);
 BA.debugLineNum = 30;BA.debugLine="Sub CopyDBFromAssets (FileName As String) As String";
Debug.ShouldStop(536870912);
 BA.debugLineNum = 31;BA.debugLine="Dim TargetDir As String";
Debug.ShouldStop(1073741824);
_targetdir = "";Debug.locals.put("TargetDir", _targetdir);
 BA.debugLineNum = 32;BA.debugLine="If File.ExternalWritable Then TargetDir = File.DirDefaultExternal Else TargetDir = File.DirInternal";
Debug.ShouldStop(-2147483648);
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()) { 
_targetdir = anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal();Debug.locals.put("TargetDir", _targetdir);}
else {
_targetdir = anywheresoftware.b4a.keywords.Common.File.getDirInternal();Debug.locals.put("TargetDir", _targetdir);};
 BA.debugLineNum = 33;BA.debugLine="If File.Exists(TargetDir, FileName) = False Then";
Debug.ShouldStop(1);
if (anywheresoftware.b4a.keywords.Common.File.Exists(_targetdir,_filename)==anywheresoftware.b4a.keywords.Common.False) { 
 BA.debugLineNum = 34;BA.debugLine="File.Copy(File.DirAssets, FileName, TargetDir, FileName)";
Debug.ShouldStop(2);
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_filename,_targetdir,_filename);
 };
 BA.debugLineNum = 36;BA.debugLine="Return TargetDir";
Debug.ShouldStop(8);
if (true) return _targetdir;
 BA.debugLineNum = 37;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _createtable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _fieldsandtypes,String _primarykey) throws Exception{
		Debug.PushSubsStack("CreateTable (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
String _field = "";
String _ftype = "";
String _query = "";
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("TableName", _tablename);
Debug.locals.put("FieldsAndTypes", _fieldsandtypes);
Debug.locals.put("PrimaryKey", _primarykey);
 BA.debugLineNum = 43;BA.debugLine="Sub CreateTable(SQL As SQL, TableName As String, FieldsAndTypes As Map, PrimaryKey As String)";
Debug.ShouldStop(1024);
 BA.debugLineNum = 44;BA.debugLine="Dim sb As StringBuilder";
Debug.ShouldStop(2048);
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("sb", _sb);
 BA.debugLineNum = 45;BA.debugLine="sb.Initialize";
Debug.ShouldStop(4096);
_sb.Initialize();
 BA.debugLineNum = 46;BA.debugLine="sb.Append(\"(\")";
Debug.ShouldStop(8192);
_sb.Append("(");
 BA.debugLineNum = 47;BA.debugLine="For i = 0 To FieldsAndTypes.Size - 1";
Debug.ShouldStop(16384);
{
final double step21 = 1;
final double limit21 = (int)(_fieldsandtypes.getSize()-1);
for (_i = (int)(0); (step21 > 0 && _i <= limit21) || (step21 < 0 && _i >= limit21); _i += step21) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 48;BA.debugLine="Dim field, ftype As String";
Debug.ShouldStop(32768);
_field = "";Debug.locals.put("field", _field);
_ftype = "";Debug.locals.put("ftype", _ftype);
 BA.debugLineNum = 49;BA.debugLine="field = FieldsAndTypes.GetKeyAt(i)";
Debug.ShouldStop(65536);
_field = String.valueOf(_fieldsandtypes.GetKeyAt(_i));Debug.locals.put("field", _field);
 BA.debugLineNum = 50;BA.debugLine="ftype = FieldsAndTypes.GetValueAt(i)";
Debug.ShouldStop(131072);
_ftype = String.valueOf(_fieldsandtypes.GetValueAt(_i));Debug.locals.put("ftype", _ftype);
 BA.debugLineNum = 51;BA.debugLine="If i > 0 Then sb.Append(\", \")";
Debug.ShouldStop(262144);
if (_i>0) { 
_sb.Append(", ");};
 BA.debugLineNum = 52;BA.debugLine="sb.Append(\"[\").Append(field).Append(\"] \").Append(ftype)";
Debug.ShouldStop(524288);
_sb.Append("[").Append(_field).Append("] ").Append(_ftype);
 BA.debugLineNum = 53;BA.debugLine="If field = PrimaryKey Then sb.Append(\" PRIMARY KEY\")";
Debug.ShouldStop(1048576);
if ((_field).equals(_primarykey)) { 
_sb.Append(" PRIMARY KEY");};
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 55;BA.debugLine="sb.Append(\")\")";
Debug.ShouldStop(4194304);
_sb.Append(")");
 BA.debugLineNum = 56;BA.debugLine="Dim query As String";
Debug.ShouldStop(8388608);
_query = "";Debug.locals.put("query", _query);
 BA.debugLineNum = 57;BA.debugLine="query = \"CREATE TABLE IF NOT EXISTS [\" & TableName & \"] \" & sb.ToString";
Debug.ShouldStop(16777216);
_query = "CREATE TABLE IF NOT EXISTS ["+_tablename+"] "+_sb.ToString();Debug.locals.put("query", _query);
 BA.debugLineNum = 58;BA.debugLine="Log(\"CreateTable: \" & query)";
Debug.ShouldStop(33554432);
anywheresoftware.b4a.keywords.Common.Log("CreateTable: "+_query);
 BA.debugLineNum = 59;BA.debugLine="SQL.ExecNonQuery(query)";
Debug.ShouldStop(67108864);
_sql.ExecNonQuery(_query);
 BA.debugLineNum = 60;BA.debugLine="End Sub";
Debug.ShouldStop(134217728);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _deleterecord(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
		Debug.PushSubsStack("DeleteRecord (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("TableName", _tablename);
Debug.locals.put("WhereFieldEquals", _wherefieldequals);
 BA.debugLineNum = 392;BA.debugLine="Sub DeleteRecord(SQL As SQL, TableName As String, WhereFieldEquals As Map)";
Debug.ShouldStop(128);
 BA.debugLineNum = 393;BA.debugLine="Dim sb As StringBuilder";
Debug.ShouldStop(256);
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("sb", _sb);
 BA.debugLineNum = 394;BA.debugLine="sb.Initialize";
Debug.ShouldStop(512);
_sb.Initialize();
 BA.debugLineNum = 395;BA.debugLine="sb.Append(\"DELETE FROM [\").Append(TableName).Append(\"] WHERE \")";
Debug.ShouldStop(1024);
_sb.Append("DELETE FROM [").Append(_tablename).Append("] WHERE ");
 BA.debugLineNum = 396;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
Debug.ShouldStop(2048);
if (_wherefieldequals.getSize()==0) { 
 BA.debugLineNum = 397;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
Debug.ShouldStop(4096);
anywheresoftware.b4a.keywords.Common.Log("WhereFieldEquals map empty!");
 BA.debugLineNum = 398;BA.debugLine="Return";
Debug.ShouldStop(8192);
if (true) return "";
 };
 BA.debugLineNum = 400;BA.debugLine="Dim args As List";
Debug.ShouldStop(32768);
_args = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("args", _args);
 BA.debugLineNum = 401;BA.debugLine="args.Initialize";
Debug.ShouldStop(65536);
_args.Initialize();
 BA.debugLineNum = 402;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
Debug.ShouldStop(131072);
{
final double step309 = 1;
final double limit309 = (int)(_wherefieldequals.getSize()-1);
for (_i = (int)(0); (step309 > 0 && _i <= limit309) || (step309 < 0 && _i >= limit309); _i += step309) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 403;BA.debugLine="If i > 0 Then sb.Append(\" AND \")";
Debug.ShouldStop(262144);
if (_i>0) { 
_sb.Append(" AND ");};
 BA.debugLineNum = 404;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.GetKeyAt(i)).Append(\"] = ?\")";
Debug.ShouldStop(524288);
_sb.Append("[").Append(String.valueOf(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 BA.debugLineNum = 405;BA.debugLine="args.add(WhereFieldEquals.GetValueAt(i))";
Debug.ShouldStop(1048576);
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 407;BA.debugLine="Log(\"DeleteRecord: \" & sb.ToString)";
Debug.ShouldStop(4194304);
anywheresoftware.b4a.keywords.Common.Log("DeleteRecord: "+_sb.ToString());
 BA.debugLineNum = 408;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
Debug.ShouldStop(8388608);
_sql.ExecNonQuery2(_sb.ToString(),_args);
 BA.debugLineNum = 409;BA.debugLine="End Sub";
Debug.ShouldStop(16777216);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _droptable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename) throws Exception{
		Debug.PushSubsStack("DropTable (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
String _query = "";
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("TableName", _tablename);
 BA.debugLineNum = 63;BA.debugLine="Sub DropTable(SQL As SQL, TableName As String)";
Debug.ShouldStop(1073741824);
 BA.debugLineNum = 64;BA.debugLine="Dim query As String";
Debug.ShouldStop(-2147483648);
_query = "";Debug.locals.put("query", _query);
 BA.debugLineNum = 65;BA.debugLine="query = \"DROP TABLE IF EXISTS [\" & TableName & \"]\"";
Debug.ShouldStop(1);
_query = "DROP TABLE IF EXISTS ["+_tablename+"]";Debug.locals.put("query", _query);
 BA.debugLineNum = 66;BA.debugLine="Log(\"DropTable: \" & query)";
Debug.ShouldStop(2);
anywheresoftware.b4a.keywords.Common.Log("DropTable: "+_query);
 BA.debugLineNum = 67;BA.debugLine="SQL.ExecNonQuery(query)";
Debug.ShouldStop(4);
_sql.ExecNonQuery(_query);
 BA.debugLineNum = 68;BA.debugLine="End Sub";
Debug.ShouldStop(8);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _executehtml(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,boolean _clickable) throws Exception{
		Debug.PushSubsStack("ExecuteHtml (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.objects.collections.List _table = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
int _row = 0;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("Query", _query);
Debug.locals.put("StringArgs", _stringargs);
Debug.locals.put("Limit", _limit);
Debug.locals.put("Clickable", _clickable);
 BA.debugLineNum = 311;BA.debugLine="Sub ExecuteHtml(SQL As SQL, Query As String, StringArgs() As String, Limit As Int, Clickable As Boolean) As String";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 312;BA.debugLine="Dim Table As List";
Debug.ShouldStop(8388608);
_table = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("Table", _table);
 BA.debugLineNum = 313;BA.debugLine="Dim cur As Cursor";
Debug.ShouldStop(16777216);
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("cur", _cur);
 BA.debugLineNum = 314;BA.debugLine="If StringArgs <> Null Then";
Debug.ShouldStop(33554432);
if (_stringargs!= null) { 
 BA.debugLineNum = 315;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
Debug.ShouldStop(67108864);
_cur.setObject((android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 BA.debugLineNum = 317;BA.debugLine="cur = SQL.ExecQuery(Query)";
Debug.ShouldStop(268435456);
_cur.setObject((android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 BA.debugLineNum = 319;BA.debugLine="Log(\"ExecuteHtml: \" & Query)";
Debug.ShouldStop(1073741824);
anywheresoftware.b4a.keywords.Common.Log("ExecuteHtml: "+_query);
 BA.debugLineNum = 320;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount) Else Limit = cur.RowCount";
Debug.ShouldStop(-2147483648);
if (_limit>0) { 
_limit = (int)(anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));Debug.locals.put("Limit", _limit);}
else {
_limit = _cur.getRowCount();Debug.locals.put("Limit", _limit);};
 BA.debugLineNum = 321;BA.debugLine="Dim sb As StringBuilder";
Debug.ShouldStop(1);
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("sb", _sb);
 BA.debugLineNum = 322;BA.debugLine="sb.Initialize";
Debug.ShouldStop(2);
_sb.Initialize();
 BA.debugLineNum = 323;BA.debugLine="sb.Append(\"<html><body>\").Append(CRLF)";
Debug.ShouldStop(4);
_sb.Append("<html><body>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 BA.debugLineNum = 324;BA.debugLine="sb.Append(\"<style type='text/css'>\").Append(HtmlCSS).Append(\"</style>\").Append(CRLF)";
Debug.ShouldStop(8);
_sb.Append("<style type='text/css'>").Append(_htmlcss).Append("</style>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 BA.debugLineNum = 325;BA.debugLine="sb.Append(\"<table><tr>\").Append(CRLF)";
Debug.ShouldStop(16);
_sb.Append("<table><tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 BA.debugLineNum = 326;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
Debug.ShouldStop(32);
{
final double step253 = 1;
final double limit253 = (int)(_cur.getColumnCount()-1);
for (_i = (int)(0); (step253 > 0 && _i <= limit253) || (step253 < 0 && _i >= limit253); _i += step253) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 327;BA.debugLine="sb.Append(\"<th>\").Append(cur.GetColumnName(i)).Append(\"</th>\")";
Debug.ShouldStop(64);
_sb.Append("<th>").Append(_cur.GetColumnName(_i)).Append("</th>");
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 338;BA.debugLine="sb.Append(\"</tr>\").Append(CRLF)";
Debug.ShouldStop(131072);
_sb.Append("</tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 BA.debugLineNum = 339;BA.debugLine="For row = 0 To Limit - 1";
Debug.ShouldStop(262144);
{
final double step257 = 1;
final double limit257 = (int)(_limit-1);
for (_row = (int)(0); (step257 > 0 && _row <= limit257) || (step257 < 0 && _row >= limit257); _row += step257) {
Debug.locals.put("row", _row);
 BA.debugLineNum = 340;BA.debugLine="cur.Position = row";
Debug.ShouldStop(524288);
_cur.setPosition(_row);
 BA.debugLineNum = 341;BA.debugLine="If row Mod 2 = 0 Then";
Debug.ShouldStop(1048576);
if (_row%2==0) { 
 BA.debugLineNum = 342;BA.debugLine="sb.Append(\"<tr>\")";
Debug.ShouldStop(2097152);
_sb.Append("<tr>");
 }else {
 BA.debugLineNum = 344;BA.debugLine="sb.Append(\"<tr class='odd'>\")";
Debug.ShouldStop(8388608);
_sb.Append("<tr class='odd'>");
 };
 BA.debugLineNum = 346;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
Debug.ShouldStop(33554432);
{
final double step264 = 1;
final double limit264 = (int)(_cur.getColumnCount()-1);
for (_i = (int)(0); (step264 > 0 && _i <= limit264) || (step264 < 0 && _i >= limit264); _i += step264) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 347;BA.debugLine="sb.Append(\"<td>\")";
Debug.ShouldStop(67108864);
_sb.Append("<td>");
 BA.debugLineNum = 348;BA.debugLine="If Clickable Then";
Debug.ShouldStop(134217728);
if (_clickable) { 
 BA.debugLineNum = 349;BA.debugLine="sb.Append(\"<a href='http://\").Append(i).Append(\".\")";
Debug.ShouldStop(268435456);
_sb.Append("<a href='http://").Append(BA.NumberToString(_i)).Append(".");
 BA.debugLineNum = 350;BA.debugLine="sb.Append(row)";
Debug.ShouldStop(536870912);
_sb.Append(BA.NumberToString(_row));
 BA.debugLineNum = 351;BA.debugLine="sb.Append(\".com'>\").Append(cur.GetString2(i)).Append(\"</a>\")";
Debug.ShouldStop(1073741824);
_sb.Append(".com'>").Append(_cur.GetString2(_i)).Append("</a>");
 }else {
 BA.debugLineNum = 353;BA.debugLine="sb.Append(cur.GetString2(i))";
Debug.ShouldStop(1);
_sb.Append(_cur.GetString2(_i));
 };
 BA.debugLineNum = 355;BA.debugLine="sb.Append(\"</td>\")";
Debug.ShouldStop(4);
_sb.Append("</td>");
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 357;BA.debugLine="sb.Append(\"</tr>\").Append(CRLF)";
Debug.ShouldStop(16);
_sb.Append("</tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 }
}Debug.locals.put("row", _row);
;
 BA.debugLineNum = 359;BA.debugLine="cur.Close";
Debug.ShouldStop(64);
_cur.Close();
 BA.debugLineNum = 360;BA.debugLine="sb.Append(\"</table></body></html>\")";
Debug.ShouldStop(128);
_sb.Append("</table></body></html>");
 BA.debugLineNum = 361;BA.debugLine="Return sb.ToString";
Debug.ShouldStop(256);
if (true) return _sb.ToString();
 BA.debugLineNum = 362;BA.debugLine="End Sub";
Debug.ShouldStop(512);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static anywheresoftware.b4a.objects.collections.Map  _executejson(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.collections.List _dbtypes) throws Exception{
		Debug.PushSubsStack("ExecuteJSON (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.objects.collections.List _table = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
int _row = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _root = null;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("Query", _query);
Debug.locals.put("StringArgs", _stringargs);
Debug.locals.put("Limit", _limit);
Debug.locals.put("DBTypes", _dbtypes);
 BA.debugLineNum = 272;BA.debugLine="Sub ExecuteJSON (SQL As SQL, Query As String, StringArgs() As String, Limit As Int, DBTypes As List) As Map";
Debug.ShouldStop(32768);
 BA.debugLineNum = 273;BA.debugLine="Dim table As List";
Debug.ShouldStop(65536);
_table = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("table", _table);
 BA.debugLineNum = 274;BA.debugLine="Dim cur As Cursor";
Debug.ShouldStop(131072);
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("cur", _cur);
 BA.debugLineNum = 275;BA.debugLine="If StringArgs <> Null Then";
Debug.ShouldStop(262144);
if (_stringargs!= null) { 
 BA.debugLineNum = 276;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
Debug.ShouldStop(524288);
_cur.setObject((android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 BA.debugLineNum = 278;BA.debugLine="cur = SQL.ExecQuery(Query)";
Debug.ShouldStop(2097152);
_cur.setObject((android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 BA.debugLineNum = 280;BA.debugLine="Log(\"ExecuteJSON: \" & Query)";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.Log("ExecuteJSON: "+_query);
 BA.debugLineNum = 281;BA.debugLine="Dim table As List";
Debug.ShouldStop(16777216);
_table = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("table", _table);
 BA.debugLineNum = 282;BA.debugLine="table.Initialize";
Debug.ShouldStop(33554432);
_table.Initialize();
 BA.debugLineNum = 283;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount) Else Limit = cur.RowCount";
Debug.ShouldStop(67108864);
if (_limit>0) { 
_limit = (int)(anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));Debug.locals.put("Limit", _limit);}
else {
_limit = _cur.getRowCount();Debug.locals.put("Limit", _limit);};
 BA.debugLineNum = 284;BA.debugLine="For row = 0 To Limit - 1";
Debug.ShouldStop(134217728);
{
final double step214 = 1;
final double limit214 = (int)(_limit-1);
for (_row = (int)(0); (step214 > 0 && _row <= limit214) || (step214 < 0 && _row >= limit214); _row += step214) {
Debug.locals.put("row", _row);
 BA.debugLineNum = 285;BA.debugLine="cur.Position = row";
Debug.ShouldStop(268435456);
_cur.setPosition(_row);
 BA.debugLineNum = 286;BA.debugLine="Dim m As Map";
Debug.ShouldStop(536870912);
_m = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("m", _m);
 BA.debugLineNum = 287;BA.debugLine="m.Initialize";
Debug.ShouldStop(1073741824);
_m.Initialize();
 BA.debugLineNum = 288;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
Debug.ShouldStop(-2147483648);
{
final double step218 = 1;
final double limit218 = (int)(_cur.getColumnCount()-1);
for (_i = (int)(0); (step218 > 0 && _i <= limit218) || (step218 < 0 && _i >= limit218); _i += step218) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 289;BA.debugLine="Select DBTypes.Get(i)";
Debug.ShouldStop(1);
switch (BA.switchObjectToInt(_dbtypes.Get(_i),(Object)(_db_text),(Object)(_db_integer),(Object)(_db_real))) {
case 0:
 BA.debugLineNum = 291;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetString2(i))";
Debug.ShouldStop(4);
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetString2(_i)));
 break;
case 1:
 BA.debugLineNum = 293;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetLong2(i))";
Debug.ShouldStop(16);
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetLong2(_i)));
 break;
case 2:
 BA.debugLineNum = 295;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetDouble2(i))";
Debug.ShouldStop(64);
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetDouble2(_i)));
 break;
default:
 BA.debugLineNum = 297;BA.debugLine="Log(\"Invalid type: \" & DBTypes.Get(i))";
Debug.ShouldStop(256);
anywheresoftware.b4a.keywords.Common.Log("Invalid type: "+String.valueOf(_dbtypes.Get(_i)));
 break;
}
;
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 300;BA.debugLine="table.add(m)";
Debug.ShouldStop(2048);
_table.Add((Object)(_m.getObject()));
 }
}Debug.locals.put("row", _row);
;
 BA.debugLineNum = 302;BA.debugLine="cur.Close";
Debug.ShouldStop(8192);
_cur.Close();
 BA.debugLineNum = 303;BA.debugLine="Dim root As Map";
Debug.ShouldStop(16384);
_root = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("root", _root);
 BA.debugLineNum = 304;BA.debugLine="root.Initialize";
Debug.ShouldStop(32768);
_root.Initialize();
 BA.debugLineNum = 305;BA.debugLine="root.Put(\"root\", table)";
Debug.ShouldStop(65536);
_root.Put((Object)("root"),(Object)(_table.getObject()));
 BA.debugLineNum = 306;BA.debugLine="Return root";
Debug.ShouldStop(131072);
if (true) return _root;
 BA.debugLineNum = 307;BA.debugLine="End Sub";
Debug.ShouldStop(262144);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _executelistview(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.ListViewWrapper _listview1,boolean _twolines) throws Exception{
		Debug.PushSubsStack("ExecuteListView (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.objects.collections.List _table = null;
String[] _cols = null;
int _i = 0;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("Query", _query);
Debug.locals.put("StringArgs", _stringargs);
Debug.locals.put("Limit", _limit);
Debug.locals.put("ListView1", _listview1);
Debug.locals.put("TwoLines", _twolines);
 BA.debugLineNum = 247;BA.debugLine="Sub ExecuteListView(SQL As SQL, Query As String, StringArgs() As String, Limit As Int, ListView1 As ListView, _ 	TwoLines As Boolean)";
Debug.ShouldStop(4194304);
 BA.debugLineNum = 249;BA.debugLine="ListView1.Clear";
Debug.ShouldStop(16777216);
_listview1.Clear();
 BA.debugLineNum = 250;BA.debugLine="Dim Table As List";
Debug.ShouldStop(33554432);
_table = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("Table", _table);
 BA.debugLineNum = 251;BA.debugLine="Table = ExecuteMemoryTable(SQL, Query, StringArgs, Limit)";
Debug.ShouldStop(67108864);
_table = _executememorytable(_ba,_sql,_query,_stringargs,_limit);Debug.locals.put("Table", _table);
 BA.debugLineNum = 252;BA.debugLine="Dim Cols() As String";
Debug.ShouldStop(134217728);
_cols = new String[(int)(0)];
java.util.Arrays.fill(_cols,"");Debug.locals.put("Cols", _cols);
 BA.debugLineNum = 253;BA.debugLine="For i = 0 To Table.Size - 1";
Debug.ShouldStop(268435456);
{
final double step193 = 1;
final double limit193 = (int)(_table.getSize()-1);
for (_i = (int)(0); (step193 > 0 && _i <= limit193) || (step193 < 0 && _i >= limit193); _i += step193) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 254;BA.debugLine="Cols = Table.Get(i)";
Debug.ShouldStop(536870912);
_cols = (String[])(_table.Get(_i));Debug.locals.put("Cols", _cols);
 BA.debugLineNum = 255;BA.debugLine="If TwoLines Then";
Debug.ShouldStop(1073741824);
if (_twolines) { 
 BA.debugLineNum = 256;BA.debugLine="ListView1.AddTwoLines2(Cols(0), Cols(1), Cols)";
Debug.ShouldStop(-2147483648);
_listview1.AddTwoLines2(_cols[(int)(0)],_cols[(int)(1)],(Object)(_cols));
 }else {
 BA.debugLineNum = 258;BA.debugLine="ListView1.AddSingleLine2(Cols(0), Cols)";
Debug.ShouldStop(2);
_listview1.AddSingleLine2(_cols[(int)(0)],(Object)(_cols));
 };
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 261;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static anywheresoftware.b4a.objects.collections.Map  _executemap(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs) throws Exception{
		Debug.PushSubsStack("ExecuteMap (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.objects.collections.Map _res = null;
int _i = 0;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("Query", _query);
Debug.locals.put("StringArgs", _stringargs);
 BA.debugLineNum = 209;BA.debugLine="Sub ExecuteMap(SQL As SQL, Query As String, StringArgs() As String) As Map";
Debug.ShouldStop(65536);
 BA.debugLineNum = 210;BA.debugLine="Dim cur As Cursor";
Debug.ShouldStop(131072);
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("cur", _cur);
 BA.debugLineNum = 211;BA.debugLine="If StringArgs <> Null Then";
Debug.ShouldStop(262144);
if (_stringargs!= null) { 
 BA.debugLineNum = 212;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
Debug.ShouldStop(524288);
_cur.setObject((android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 BA.debugLineNum = 214;BA.debugLine="cur = SQL.ExecQuery(Query)";
Debug.ShouldStop(2097152);
_cur.setObject((android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 BA.debugLineNum = 216;BA.debugLine="Log(\"ExecuteMap: \" & Query)";
Debug.ShouldStop(8388608);
anywheresoftware.b4a.keywords.Common.Log("ExecuteMap: "+_query);
 BA.debugLineNum = 217;BA.debugLine="If cur.RowCount = 0 Then";
Debug.ShouldStop(16777216);
if (_cur.getRowCount()==0) { 
 BA.debugLineNum = 218;BA.debugLine="Log(\"No records found.\")";
Debug.ShouldStop(33554432);
anywheresoftware.b4a.keywords.Common.Log("No records found.");
 BA.debugLineNum = 219;BA.debugLine="Return Null";
Debug.ShouldStop(67108864);
if (true) return (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(anywheresoftware.b4a.keywords.Common.Null));
 };
 BA.debugLineNum = 221;BA.debugLine="Dim res As Map";
Debug.ShouldStop(268435456);
_res = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("res", _res);
 BA.debugLineNum = 222;BA.debugLine="res.Initialize";
Debug.ShouldStop(536870912);
_res.Initialize();
 BA.debugLineNum = 223;BA.debugLine="cur.Position = 0";
Debug.ShouldStop(1073741824);
_cur.setPosition((int)(0));
 BA.debugLineNum = 224;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
Debug.ShouldStop(-2147483648);
{
final double step172 = 1;
final double limit172 = (int)(_cur.getColumnCount()-1);
for (_i = (int)(0); (step172 > 0 && _i <= limit172) || (step172 < 0 && _i >= limit172); _i += step172) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 225;BA.debugLine="res.Put(cur.GetColumnName(i).ToLowerCase, cur.GetString2(i))";
Debug.ShouldStop(1);
_res.Put((Object)(_cur.GetColumnName(_i).toLowerCase()),(Object)(_cur.GetString2(_i)));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 227;BA.debugLine="cur.Close";
Debug.ShouldStop(4);
_cur.Close();
 BA.debugLineNum = 228;BA.debugLine="Return res";
Debug.ShouldStop(8);
if (true) return _res;
 BA.debugLineNum = 229;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static anywheresoftware.b4a.objects.collections.List  _executememorytable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit) throws Exception{
		Debug.PushSubsStack("ExecuteMemoryTable (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.objects.collections.List _table = null;
int _row = 0;
String[] _values = null;
int _col = 0;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("Query", _query);
Debug.locals.put("StringArgs", _stringargs);
Debug.locals.put("Limit", _limit);
 BA.debugLineNum = 182;BA.debugLine="Sub ExecuteMemoryTable(SQL As SQL, Query As String, StringArgs() As String, Limit As Int) As List";
Debug.ShouldStop(2097152);
 BA.debugLineNum = 183;BA.debugLine="Dim cur As Cursor";
Debug.ShouldStop(4194304);
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();Debug.locals.put("cur", _cur);
 BA.debugLineNum = 184;BA.debugLine="If StringArgs <> Null Then";
Debug.ShouldStop(8388608);
if (_stringargs!= null) { 
 BA.debugLineNum = 185;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
Debug.ShouldStop(16777216);
_cur.setObject((android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 BA.debugLineNum = 187;BA.debugLine="cur = SQL.ExecQuery(Query)";
Debug.ShouldStop(67108864);
_cur.setObject((android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 BA.debugLineNum = 189;BA.debugLine="Log(\"ExecuteMemoryTable: \" & Query)";
Debug.ShouldStop(268435456);
anywheresoftware.b4a.keywords.Common.Log("ExecuteMemoryTable: "+_query);
 BA.debugLineNum = 190;BA.debugLine="Dim table As List";
Debug.ShouldStop(536870912);
_table = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("table", _table);
 BA.debugLineNum = 191;BA.debugLine="table.Initialize";
Debug.ShouldStop(1073741824);
_table.Initialize();
 BA.debugLineNum = 192;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount) Else Limit = cur.RowCount";
Debug.ShouldStop(-2147483648);
if (_limit>0) { 
_limit = (int)(anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));Debug.locals.put("Limit", _limit);}
else {
_limit = _cur.getRowCount();Debug.locals.put("Limit", _limit);};
 BA.debugLineNum = 193;BA.debugLine="For row = 0 To Limit - 1";
Debug.ShouldStop(1);
{
final double step146 = 1;
final double limit146 = (int)(_limit-1);
for (_row = (int)(0); (step146 > 0 && _row <= limit146) || (step146 < 0 && _row >= limit146); _row += step146) {
Debug.locals.put("row", _row);
 BA.debugLineNum = 194;BA.debugLine="cur.Position = row";
Debug.ShouldStop(2);
_cur.setPosition(_row);
 BA.debugLineNum = 195;BA.debugLine="Dim values(cur.ColumnCount) As String";
Debug.ShouldStop(4);
_values = new String[_cur.getColumnCount()];
java.util.Arrays.fill(_values,"");Debug.locals.put("values", _values);
 BA.debugLineNum = 196;BA.debugLine="For col = 0 To cur.ColumnCount - 1";
Debug.ShouldStop(8);
{
final double step149 = 1;
final double limit149 = (int)(_cur.getColumnCount()-1);
for (_col = (int)(0); (step149 > 0 && _col <= limit149) || (step149 < 0 && _col >= limit149); _col += step149) {
Debug.locals.put("col", _col);
 BA.debugLineNum = 197;BA.debugLine="values(col) = cur.GetString2(col)";
Debug.ShouldStop(16);
_values[_col] = _cur.GetString2(_col);Debug.locals.put("values", _values);
 }
}Debug.locals.put("col", _col);
;
 BA.debugLineNum = 199;BA.debugLine="table.add(values)";
Debug.ShouldStop(64);
_table.Add((Object)(_values));
 }
}Debug.locals.put("row", _row);
;
 BA.debugLineNum = 201;BA.debugLine="cur.Close";
Debug.ShouldStop(256);
_cur.Close();
 BA.debugLineNum = 202;BA.debugLine="Return table";
Debug.ShouldStop(512);
if (true) return _table;
 BA.debugLineNum = 203;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return null;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _executespinner(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.SpinnerWrapper _spinner1) throws Exception{
		Debug.PushSubsStack("ExecuteSpinner (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.objects.collections.List _table = null;
String[] _cols = null;
int _i = 0;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("Query", _query);
Debug.locals.put("StringArgs", _stringargs);
Debug.locals.put("Limit", _limit);
Debug.locals.put("Spinner1", _spinner1);
 BA.debugLineNum = 232;BA.debugLine="Sub ExecuteSpinner(SQL As SQL, Query As String, StringArgs() As String, Limit As Int, Spinner1 As Spinner)";
Debug.ShouldStop(128);
 BA.debugLineNum = 233;BA.debugLine="Spinner1.Clear";
Debug.ShouldStop(256);
_spinner1.Clear();
 BA.debugLineNum = 234;BA.debugLine="Dim Table As List";
Debug.ShouldStop(512);
_table = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("Table", _table);
 BA.debugLineNum = 235;BA.debugLine="Table = ExecuteMemoryTable(SQL, Query, StringArgs, Limit)";
Debug.ShouldStop(1024);
_table = _executememorytable(_ba,_sql,_query,_stringargs,_limit);Debug.locals.put("Table", _table);
 BA.debugLineNum = 236;BA.debugLine="Dim Cols() As String";
Debug.ShouldStop(2048);
_cols = new String[(int)(0)];
java.util.Arrays.fill(_cols,"");Debug.locals.put("Cols", _cols);
 BA.debugLineNum = 237;BA.debugLine="For i = 0 To Table.Size - 1";
Debug.ShouldStop(4096);
{
final double step183 = 1;
final double limit183 = (int)(_table.getSize()-1);
for (_i = (int)(0); (step183 > 0 && _i <= limit183) || (step183 < 0 && _i >= limit183); _i += step183) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 238;BA.debugLine="Cols = Table.Get(i)";
Debug.ShouldStop(8192);
_cols = (String[])(_table.Get(_i));Debug.locals.put("Cols", _cols);
 BA.debugLineNum = 239;BA.debugLine="Spinner1.add(Cols(0))";
Debug.ShouldStop(16384);
_spinner1.Add(_cols[(int)(0)]);
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 241;BA.debugLine="End Sub";
Debug.ShouldStop(65536);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static int  _getdbversion(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql) throws Exception{
		Debug.PushSubsStack("GetDBVersion (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
int _count = 0;
int _version = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
 BA.debugLineNum = 366;BA.debugLine="Sub GetDBVersion (SQL As SQL) As Int";
Debug.ShouldStop(8192);
 BA.debugLineNum = 367;BA.debugLine="Dim count, version As Int";
Debug.ShouldStop(16384);
_count = 0;Debug.locals.put("count", _count);
_version = 0;Debug.locals.put("version", _version);
 BA.debugLineNum = 368;BA.debugLine="count = SQL.ExecQuerySingleResult(\"SELECT count(*) FROM sqlite_master WHERE Type='table' AND name='DBVersion'\")";
Debug.ShouldStop(32768);
_count = (int)(Double.parseDouble(_sql.ExecQuerySingleResult("SELECT count(*) FROM sqlite_master WHERE Type='table' AND name='DBVersion'")));Debug.locals.put("count", _count);
 BA.debugLineNum = 369;BA.debugLine="If count > 0 Then";
Debug.ShouldStop(65536);
if (_count>0) { 
 BA.debugLineNum = 370;BA.debugLine="version = SQL.ExecQuerySingleResult(\"SELECT version FROM DBVersion\")";
Debug.ShouldStop(131072);
_version = (int)(Double.parseDouble(_sql.ExecQuerySingleResult("SELECT version FROM DBVersion")));Debug.locals.put("version", _version);
 }else {
 BA.debugLineNum = 373;BA.debugLine="Dim m As Map";
Debug.ShouldStop(1048576);
_m = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("m", _m);
 BA.debugLineNum = 374;BA.debugLine="m.Initialize";
Debug.ShouldStop(2097152);
_m.Initialize();
 BA.debugLineNum = 375;BA.debugLine="m.Put(\"version\", DB_INTEGER)";
Debug.ShouldStop(4194304);
_m.Put((Object)("version"),(Object)(_db_integer));
 BA.debugLineNum = 376;BA.debugLine="CreateTable(SQL, \"DBVersion\", m, \"version\")";
Debug.ShouldStop(8388608);
_createtable(_ba,_sql,"DBVersion",_m,"version");
 BA.debugLineNum = 378;BA.debugLine="SQL.ExecNonQuery(\"INSERT INTO DBVersion VALUES (1)\")";
Debug.ShouldStop(33554432);
_sql.ExecNonQuery("INSERT INTO DBVersion VALUES (1)");
 BA.debugLineNum = 380;BA.debugLine="version = 1";
Debug.ShouldStop(134217728);
_version = (int)(1);Debug.locals.put("version", _version);
 };
 BA.debugLineNum = 383;BA.debugLine="Return version";
Debug.ShouldStop(1073741824);
if (true) return _version;
 BA.debugLineNum = 384;BA.debugLine="End Sub";
Debug.ShouldStop(-2147483648);
return 0;
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _insertmaps(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.List _listofmaps) throws Exception{
		Debug.PushSubsStack("InsertMaps (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _columns = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _values = null;
int _i1 = 0;
anywheresoftware.b4a.objects.collections.List _listofvalues = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
int _i2 = 0;
String _col = "";
Object _value = null;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("TableName", _tablename);
Debug.locals.put("ListOfMaps", _listofmaps);
 BA.debugLineNum = 74;BA.debugLine="Sub InsertMaps(SQL As SQL, TableName As String, ListOfMaps As List)";
Debug.ShouldStop(512);
 BA.debugLineNum = 75;BA.debugLine="Dim sb, columns, values As StringBuilder";
Debug.ShouldStop(1024);
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("sb", _sb);
_columns = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("columns", _columns);
_values = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("values", _values);
 BA.debugLineNum = 77;BA.debugLine="If ListOfMaps.Size > 1 AND ListOfMaps.Get(0) = ListOfMaps.Get(1) Then";
Debug.ShouldStop(4096);
if (_listofmaps.getSize()>1 && (_listofmaps.Get((int)(0))).equals(_listofmaps.Get((int)(1)))) { 
 BA.debugLineNum = 78;BA.debugLine="Log(\"Same Map found twice in list. Each item in the list should include a different map object.\")";
Debug.ShouldStop(8192);
anywheresoftware.b4a.keywords.Common.Log("Same Map found twice in list. Each item in the list should include a different map object.");
 BA.debugLineNum = 79;BA.debugLine="ToastMessageShow(\"Same Map found twice in list. Each item in the list should include a different map object.\", True)";
Debug.ShouldStop(16384);
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Same Map found twice in list. Each item in the list should include a different map object.",anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 80;BA.debugLine="Return";
Debug.ShouldStop(32768);
if (true) return "";
 };
 BA.debugLineNum = 82;BA.debugLine="SQL.BeginTransaction";
Debug.ShouldStop(131072);
_sql.BeginTransaction();
 BA.debugLineNum = 83;BA.debugLine="Try";
Debug.ShouldStop(262144);
try { BA.debugLineNum = 84;BA.debugLine="For i1 = 0 To ListOfMaps.Size - 1";
Debug.ShouldStop(524288);
{
final double step50 = 1;
final double limit50 = (int)(_listofmaps.getSize()-1);
for (_i1 = (int)(0); (step50 > 0 && _i1 <= limit50) || (step50 < 0 && _i1 >= limit50); _i1 += step50) {
Debug.locals.put("i1", _i1);
 BA.debugLineNum = 85;BA.debugLine="sb.Initialize";
Debug.ShouldStop(1048576);
_sb.Initialize();
 BA.debugLineNum = 86;BA.debugLine="columns.Initialize";
Debug.ShouldStop(2097152);
_columns.Initialize();
 BA.debugLineNum = 87;BA.debugLine="values.Initialize";
Debug.ShouldStop(4194304);
_values.Initialize();
 BA.debugLineNum = 88;BA.debugLine="Dim listOfValues As List";
Debug.ShouldStop(8388608);
_listofvalues = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("listOfValues", _listofvalues);
 BA.debugLineNum = 89;BA.debugLine="listOfValues.Initialize";
Debug.ShouldStop(16777216);
_listofvalues.Initialize();
 BA.debugLineNum = 90;BA.debugLine="sb.Append(\"INSERT INTO [\" & TableName & \"] (\")";
Debug.ShouldStop(33554432);
_sb.Append("INSERT INTO ["+_tablename+"] (");
 BA.debugLineNum = 91;BA.debugLine="Dim m As Map";
Debug.ShouldStop(67108864);
_m = new anywheresoftware.b4a.objects.collections.Map();Debug.locals.put("m", _m);
 BA.debugLineNum = 92;BA.debugLine="m = ListOfMaps.Get(i1)";
Debug.ShouldStop(134217728);
_m.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_listofmaps.Get(_i1)));
 BA.debugLineNum = 93;BA.debugLine="For i2 = 0 To m.Size - 1";
Debug.ShouldStop(268435456);
{
final double step59 = 1;
final double limit59 = (int)(_m.getSize()-1);
for (_i2 = (int)(0); (step59 > 0 && _i2 <= limit59) || (step59 < 0 && _i2 >= limit59); _i2 += step59) {
Debug.locals.put("i2", _i2);
 BA.debugLineNum = 94;BA.debugLine="Dim col As String";
Debug.ShouldStop(536870912);
_col = "";Debug.locals.put("col", _col);
 BA.debugLineNum = 95;BA.debugLine="Dim value As Object";
Debug.ShouldStop(1073741824);
_value = new Object();Debug.locals.put("value", _value);
 BA.debugLineNum = 96;BA.debugLine="col = m.GetKeyAt(i2)";
Debug.ShouldStop(-2147483648);
_col = String.valueOf(_m.GetKeyAt(_i2));Debug.locals.put("col", _col);
 BA.debugLineNum = 97;BA.debugLine="value = m.GetValueAt(i2)";
Debug.ShouldStop(1);
_value = _m.GetValueAt(_i2);Debug.locals.put("value", _value);
 BA.debugLineNum = 98;BA.debugLine="If i2 > 0 Then";
Debug.ShouldStop(2);
if (_i2>0) { 
 BA.debugLineNum = 99;BA.debugLine="columns.Append(\", \")";
Debug.ShouldStop(4);
_columns.Append(", ");
 BA.debugLineNum = 100;BA.debugLine="values.Append(\", \")";
Debug.ShouldStop(8);
_values.Append(", ");
 };
 BA.debugLineNum = 102;BA.debugLine="columns.Append(\"[\").Append(col).Append(\"]\")";
Debug.ShouldStop(32);
_columns.Append("[").Append(_col).Append("]");
 BA.debugLineNum = 103;BA.debugLine="values.Append(\"?\")";
Debug.ShouldStop(64);
_values.Append("?");
 BA.debugLineNum = 104;BA.debugLine="listOfValues.add(value)";
Debug.ShouldStop(128);
_listofvalues.Add(_value);
 }
}Debug.locals.put("i2", _i2);
;
 BA.debugLineNum = 106;BA.debugLine="sb.Append(columns.ToString).Append(\") VALUES (\").Append(values.ToString).Append(\")\")";
Debug.ShouldStop(512);
_sb.Append(_columns.ToString()).Append(") VALUES (").Append(_values.ToString()).Append(")");
 BA.debugLineNum = 107;BA.debugLine="If i1 = 0 Then Log(\"InsertMaps (first query out of \" & ListOfMaps.Size & \"): \" & sb.ToString)";
Debug.ShouldStop(1024);
if (_i1==0) { 
anywheresoftware.b4a.keywords.Common.Log("InsertMaps (first query out of "+BA.NumberToString(_listofmaps.getSize())+"): "+_sb.ToString());};
 BA.debugLineNum = 108;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, listOfValues)";
Debug.ShouldStop(2048);
_sql.ExecNonQuery2(_sb.ToString(),_listofvalues);
 }
}Debug.locals.put("i1", _i1);
;
 BA.debugLineNum = 110;BA.debugLine="SQL.TransactionSuccessful";
Debug.ShouldStop(8192);
_sql.TransactionSuccessful();
 } 
       catch (Exception e78) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e78); BA.debugLineNum = 112;BA.debugLine="ToastMessageShow(LastException.Message, True)";
Debug.ShouldStop(32768);
anywheresoftware.b4a.keywords.Common.ToastMessageShow(anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),anywheresoftware.b4a.keywords.Common.True);
 BA.debugLineNum = 113;BA.debugLine="Log(LastException)";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.Log(String.valueOf(anywheresoftware.b4a.keywords.Common.LastException(_ba)));
 };
 BA.debugLineNum = 115;BA.debugLine="SQL.EndTransaction";
Debug.ShouldStop(262144);
_sql.EndTransaction();
 BA.debugLineNum = 116;BA.debugLine="End Sub";
Debug.ShouldStop(524288);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim DB_REAL, DB_INTEGER, DB_BLOB, DB_TEXT As String";
_db_real = "";
_db_integer = "";
_db_blob = "";
_db_text = "";
 //BA.debugLineNum = 14;BA.debugLine="DB_REAL = \"REAL\"";
_db_real = "REAL";
 //BA.debugLineNum = 15;BA.debugLine="DB_INTEGER = \"INTEGER\"";
_db_integer = "INTEGER";
 //BA.debugLineNum = 16;BA.debugLine="DB_BLOB = \"BLOB\"";
_db_blob = "BLOB";
 //BA.debugLineNum = 17;BA.debugLine="DB_TEXT = \"TEXT\"";
_db_text = "TEXT";
 //BA.debugLineNum = 18;BA.debugLine="Dim HtmlCSS As String";
_htmlcss = "";
 //BA.debugLineNum = 19;BA.debugLine="HtmlCSS = \"table {width: 100%;border: 1px solid #cef;text-align: left; }\" _ 		& \" th { font-weight: bold;	background-color: #acf;	border-bottom: 1px solid #cef; }\" _  		& \"td,th {	padding: 4px 5px; }\" _ 		& \".odd {background-color: #def; } .odd td {border-bottom: 1px solid #cef; }\" _ 		& \"a { text-decoration:none; color: #000;}\"";
_htmlcss = "table {width: 100%;border: 1px solid #cef;text-align: left; }"+" th { font-weight: bold;	background-color: #acf;	border-bottom: 1px solid #cef; }"+"td,th {	padding: 4px 5px; }"+".odd {background-color: #def; } .odd td {border-bottom: 1px solid #cef; }"+"a { text-decoration:none; color: #000;}";
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _setdbversion(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,int _version) throws Exception{
		Debug.PushSubsStack("SetDBVersion (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("Version", _version);
 BA.debugLineNum = 387;BA.debugLine="Sub SetDBVersion (SQL As SQL, Version As Int)";
Debug.ShouldStop(4);
 BA.debugLineNum = 388;BA.debugLine="SQL.ExecNonQuery2(\"UPDATE DBVersion set version = ?\", Array As Object(Version))";
Debug.ShouldStop(8);
_sql.ExecNonQuery2("UPDATE DBVersion set version = ?",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_version)}));
 BA.debugLineNum = 389;BA.debugLine="End Sub";
Debug.ShouldStop(16);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _updaterecord(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,String _field,Object _newvalue,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
		Debug.PushSubsStack("UpdateRecord (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("TableName", _tablename);
Debug.locals.put("Field", _field);
Debug.locals.put("NewValue", _newvalue);
Debug.locals.put("WhereFieldEquals", _wherefieldequals);
 BA.debugLineNum = 120;BA.debugLine="Sub UpdateRecord(SQL As SQL, TableName As String, Field As String, NewValue As Object, _ 	WhereFieldEquals As Map)";
Debug.ShouldStop(8388608);
 BA.debugLineNum = 122;BA.debugLine="Dim sb As StringBuilder";
Debug.ShouldStop(33554432);
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("sb", _sb);
 BA.debugLineNum = 123;BA.debugLine="sb.Initialize";
Debug.ShouldStop(67108864);
_sb.Initialize();
 BA.debugLineNum = 124;BA.debugLine="sb.Append(\"UPDATE [\").Append(TableName).Append(\"] SET [\").Append(Field).Append(\"] = ? WHERE \")";
Debug.ShouldStop(134217728);
_sb.Append("UPDATE [").Append(_tablename).Append("] SET [").Append(_field).Append("] = ? WHERE ");
 BA.debugLineNum = 125;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
Debug.ShouldStop(268435456);
if (_wherefieldequals.getSize()==0) { 
 BA.debugLineNum = 126;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
Debug.ShouldStop(536870912);
anywheresoftware.b4a.keywords.Common.Log("WhereFieldEquals map empty!");
 BA.debugLineNum = 127;BA.debugLine="Return";
Debug.ShouldStop(1073741824);
if (true) return "";
 };
 BA.debugLineNum = 129;BA.debugLine="Dim args As List";
Debug.ShouldStop(1);
_args = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("args", _args);
 BA.debugLineNum = 130;BA.debugLine="args.Initialize";
Debug.ShouldStop(2);
_args.Initialize();
 BA.debugLineNum = 131;BA.debugLine="args.add(NewValue)";
Debug.ShouldStop(4);
_args.Add(_newvalue);
 BA.debugLineNum = 132;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
Debug.ShouldStop(8);
{
final double step94 = 1;
final double limit94 = (int)(_wherefieldequals.getSize()-1);
for (_i = (int)(0); (step94 > 0 && _i <= limit94) || (step94 < 0 && _i >= limit94); _i += step94) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 133;BA.debugLine="If i > 0 Then sb.Append(\" AND \")";
Debug.ShouldStop(16);
if (_i>0) { 
_sb.Append(" AND ");};
 BA.debugLineNum = 134;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.GetKeyAt(i)).Append(\"] = ?\")";
Debug.ShouldStop(32);
_sb.Append("[").Append(String.valueOf(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 BA.debugLineNum = 135;BA.debugLine="args.add(WhereFieldEquals.GetValueAt(i))";
Debug.ShouldStop(64);
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 137;BA.debugLine="Log(\"UpdateRecord: \" & sb.ToString)";
Debug.ShouldStop(256);
anywheresoftware.b4a.keywords.Common.Log("UpdateRecord: "+_sb.ToString());
 BA.debugLineNum = 138;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
Debug.ShouldStop(512);
_sql.ExecNonQuery2(_sb.ToString(),_args);
 BA.debugLineNum = 139;BA.debugLine="End Sub";
Debug.ShouldStop(1024);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
public static String  _updaterecord2(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _fields,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
		Debug.PushSubsStack("UpdateRecord2 (dbutils) ","dbutils",6,_ba,mostCurrent);
try {
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
Debug.locals.put("ba", _ba);
Debug.locals.put("SQL", _sql);
Debug.locals.put("TableName", _tablename);
Debug.locals.put("Fields", _fields);
Debug.locals.put("WhereFieldEquals", _wherefieldequals);
 BA.debugLineNum = 143;BA.debugLine="Sub UpdateRecord2(SQL As SQL, TableName As String, Fields As Map, WhereFieldEquals As Map)";
Debug.ShouldStop(16384);
 BA.debugLineNum = 144;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
Debug.ShouldStop(32768);
if (_wherefieldequals.getSize()==0) { 
 BA.debugLineNum = 145;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
Debug.ShouldStop(65536);
anywheresoftware.b4a.keywords.Common.Log("WhereFieldEquals map empty!");
 BA.debugLineNum = 146;BA.debugLine="Return";
Debug.ShouldStop(131072);
if (true) return "";
 };
 BA.debugLineNum = 148;BA.debugLine="If Fields.Size = 0 Then";
Debug.ShouldStop(524288);
if (_fields.getSize()==0) { 
 BA.debugLineNum = 149;BA.debugLine="Log(\"Fields empty\")";
Debug.ShouldStop(1048576);
anywheresoftware.b4a.keywords.Common.Log("Fields empty");
 BA.debugLineNum = 150;BA.debugLine="Return";
Debug.ShouldStop(2097152);
if (true) return "";
 };
 BA.debugLineNum = 152;BA.debugLine="Dim sb As StringBuilder";
Debug.ShouldStop(8388608);
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();Debug.locals.put("sb", _sb);
 BA.debugLineNum = 153;BA.debugLine="sb.Initialize";
Debug.ShouldStop(16777216);
_sb.Initialize();
 BA.debugLineNum = 154;BA.debugLine="sb.Append(\"UPDATE [\").Append(TableName).Append(\"] SET \")";
Debug.ShouldStop(33554432);
_sb.Append("UPDATE [").Append(_tablename).Append("] SET ");
 BA.debugLineNum = 155;BA.debugLine="Dim args As List";
Debug.ShouldStop(67108864);
_args = new anywheresoftware.b4a.objects.collections.List();Debug.locals.put("args", _args);
 BA.debugLineNum = 156;BA.debugLine="args.Initialize";
Debug.ShouldStop(134217728);
_args.Initialize();
 BA.debugLineNum = 157;BA.debugLine="For i=0 To Fields.Size-1";
Debug.ShouldStop(268435456);
{
final double step116 = 1;
final double limit116 = (int)(_fields.getSize()-1);
for (_i = (int)(0); (step116 > 0 && _i <= limit116) || (step116 < 0 && _i >= limit116); _i += step116) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 158;BA.debugLine="If i<>Fields.Size-1 Then";
Debug.ShouldStop(536870912);
if (_i!=_fields.getSize()-1) { 
 BA.debugLineNum = 159;BA.debugLine="sb.Append(\"[\").Append(Fields.GetKeyAt(i)).Append(\"]=?,\")";
Debug.ShouldStop(1073741824);
_sb.Append("[").Append(String.valueOf(_fields.GetKeyAt(_i))).Append("]=?,");
 }else {
 BA.debugLineNum = 161;BA.debugLine="sb.Append(\"[\").Append(Fields.GetKeyAt(i)).Append(\"]=?\")";
Debug.ShouldStop(1);
_sb.Append("[").Append(String.valueOf(_fields.GetKeyAt(_i))).Append("]=?");
 };
 BA.debugLineNum = 163;BA.debugLine="args.add(Fields.GetValueAt(i))";
Debug.ShouldStop(4);
_args.Add(_fields.GetValueAt(_i));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 166;BA.debugLine="sb.Append(\" WHERE \")";
Debug.ShouldStop(32);
_sb.Append(" WHERE ");
 BA.debugLineNum = 167;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
Debug.ShouldStop(64);
{
final double step125 = 1;
final double limit125 = (int)(_wherefieldequals.getSize()-1);
for (_i = (int)(0); (step125 > 0 && _i <= limit125) || (step125 < 0 && _i >= limit125); _i += step125) {
Debug.locals.put("i", _i);
 BA.debugLineNum = 168;BA.debugLine="If i > 0 Then";
Debug.ShouldStop(128);
if (_i>0) { 
 BA.debugLineNum = 169;BA.debugLine="sb.Append(\" AND \")";
Debug.ShouldStop(256);
_sb.Append(" AND ");
 };
 BA.debugLineNum = 171;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.GetKeyAt(i)).Append(\"] = ?\")";
Debug.ShouldStop(1024);
_sb.Append("[").Append(String.valueOf(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 BA.debugLineNum = 172;BA.debugLine="args.add(WhereFieldEquals.GetValueAt(i))";
Debug.ShouldStop(2048);
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
}Debug.locals.put("i", _i);
;
 BA.debugLineNum = 174;BA.debugLine="Log(\"UpdateRecord: \" & sb.ToString)";
Debug.ShouldStop(8192);
anywheresoftware.b4a.keywords.Common.Log("UpdateRecord: "+_sb.ToString());
 BA.debugLineNum = 175;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
Debug.ShouldStop(16384);
_sql.ExecNonQuery2(_sb.ToString(),_args);
 BA.debugLineNum = 176;BA.debugLine="End Sub";
Debug.ShouldStop(32768);
return "";
}
catch (Exception e) {
			Debug.ErrorCaught(e);
			throw e;
		} 
finally {
			Debug.PopSubsStack();
		}}
  public Object[] GetGlobals() {
		return new Object[] {"DB_REAL",_db_real,"DB_INTEGER",_db_integer,"DB_BLOB",_db_blob,"DB_TEXT",_db_text,"HtmlCSS",_htmlcss,"HttpUtils2Service",Debug.moduleToString(b4a.sysdev.httputils2service.class),"Main",Debug.moduleToString(b4a.sysdev.main.class),"menu",Debug.moduleToString(b4a.sysdev.menu.class),"viewproduct",Debug.moduleToString(b4a.sysdev.viewproduct.class),"details",Debug.moduleToString(b4a.sysdev.details.class),"add",Debug.moduleToString(b4a.sysdev.add.class),"sales",Debug.moduleToString(b4a.sysdev.sales.class),"EditArtist",Debug.moduleToString(b4a.sysdev.editartist.class),"Help",Debug.moduleToString(b4a.sysdev.help.class)};
}
}
