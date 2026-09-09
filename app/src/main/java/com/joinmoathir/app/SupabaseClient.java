package com.joinmoathir.app;

import android.content.*;
import org.json.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class SupabaseClient {
  static final String URL="https://hyzahcdivdhqfcggmnuk.supabase.co";
  static final String KEY="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJoeXphaGNkaXZkaHFmY2dnbW51ayIsInJvbGUiOiJhbm9uIiwiaWF0IjoxNzgwOTUyODk0LCJleHAiOjIwOTY1Mjg4OTR9.rOCiSz1JLY7ob876OciTtuvDERWRyOpIv1Zyk3Uy8DU";
  final ExecutorService pool=Executors.newFixedThreadPool(4); String token="",userId="",email="";
  interface Callback {void ok(JSONArray a);void fail(String e);}
  boolean restore(Context c){SharedPreferences p=c.getSharedPreferences("moathir_session",0);token=p.getString("token","");userId=p.getString("uid","");email=p.getString("email","");return !token.isEmpty()&&!userId.isEmpty();}
  void save(Context c){c.getSharedPreferences("moathir_session",0).edit().putString("token",token).putString("uid",userId).putString("email",email).apply();}
  void logout(Context c){token="";userId="";email="";c.getSharedPreferences("moathir_session",0).edit().clear().apply();}
  void auth(Context c,boolean signup,String mail,String password,String name,Callback cb){pool.execute(()->{try{JSONObject o=new JSONObject();o.put("email",mail);o.put("password",password);if(signup){JSONObject d=new JSONObject();d.put("display_name",name.isEmpty()?"مستخدم":name);o.put("data",d);}String path=signup?"/auth/v1/signup":"/auth/v1/token?grant_type=password";Result r=req("POST",path,o.toString(),false,null);JSONObject j=new JSONObject(r.body.isEmpty()?"{}":r.body);if(r.code<200||r.code>=300){cb.fail(err(r.body));return;}String at=j.optString("access_token");JSONObject u=j.optJSONObject("user");if(at.isEmpty()||u==null){cb.fail(signup?"تم إنشاء الحساب. إذا كان تأكيد الإيميل مفعلاً، أكده ثم سجل دخول.":"تعذر إنشاء جلسة");return;}token=at;userId=u.optString("id");email=u.optString("email",mail);save(c);cb.ok(new JSONArray());}catch(Exception e){cb.fail(message(e));}});}
  void get(String path,Callback cb){pool.execute(()->{try{Result r=req("GET",path,null,true,null);if(r.code<200||r.code>=300){cb.fail(err(r.body));return;}cb.ok(new JSONArray(r.body.isEmpty()?"[]":r.body));}catch(Exception e){cb.fail(message(e));}});}
  void post(String path,String json,String prefer,Callback cb){pool.execute(()->{try{Result r=req("POST",path,json,true,prefer);if(r.code<200||r.code>=300){cb.fail(err(r.body));return;}cb.ok(r.body.trim().startsWith("[")?new JSONArray(r.body):new JSONArray());}catch(Exception e){cb.fail(message(e));}});}
  Result req(String method,String path,String body,boolean auth,String prefer)throws Exception{HttpURLConnection c=(HttpURLConnection)new java.net.URL(URL+path).openConnection();c.setRequestMethod(method);c.setConnectTimeout(20000);c.setReadTimeout(20000);c.setRequestProperty("apikey",KEY);c.setRequestProperty("Accept","application/json");if(auth&&!token.isEmpty())c.setRequestProperty("Authorization","Bearer "+token);if(prefer!=null)c.setRequestProperty("Prefer",prefer);if(body!=null){c.setDoOutput(true);c.setRequestProperty("Content-Type","application/json");try(OutputStream os=c.getOutputStream()){os.write(body.getBytes(StandardCharsets.UTF_8));}}int code=c.getResponseCode();InputStream is=code>=400?c.getErrorStream():c.getInputStream();String text=read(is);c.disconnect();return new Result(code,text);}
  static String read(InputStream is)throws Exception{if(is==null)return"";StringBuilder b=new StringBuilder();try(BufferedReader r=new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))){String s;while((s=r.readLine())!=null)b.append(s);}return b.toString();}
  static String err(String raw){try{JSONObject j=new JSONObject(raw);String s=j.optString("message",j.optString("msg",j.optString("hint","تعذر تنفيذ الطلب")));return s.isEmpty()?"تعذر تنفيذ الطلب":s;}catch(Exception e){return raw==null||raw.isEmpty()?"تعذر تنفيذ الطلب":raw;}}
  static String message(Exception e){return e.getMessage()==null?"تعذر الاتصال بالخدمة":e.getMessage();}
  static String afterDays(int n){Date d=new Date(System.currentTimeMillis()+n*86400000L);SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.US);f.setTimeZone(TimeZone.getTimeZone("UTC"));return f.format(d);}
  static class Result{final int code;final String body;Result(int c,String b){code=c;body=b;}}
}
