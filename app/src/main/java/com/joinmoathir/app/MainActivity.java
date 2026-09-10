package com.joinmoathir.app;

import android.app.*;
import android.os.Bundle;
import android.text.InputType;
import android.view.*;
import android.widget.*;
import org.json.*;
import java.util.*;

public class MainActivity extends Activity {
  static final int BLUE=0xff446ff2, BG=0xfff6f8fc, TEXT=0xff172033, MUTED=0xff7b869b;
  FrameLayout body; LinearLayout nav; String tab="home";
  JSONArray creators = new JSONArray();
  JSONArray projects = new JSONArray();
  JSONArray posts = new JSONArray();

  @Override public void onCreate(Bundle b){
    super.onCreate(b);
    getWindow().setStatusBarColor(0xffffffff);
    seed();
    showAuth(false);
  }

  void seed(){
    creators.put(obj("display_name","زهراء علي","city","بغداد","primary_category","جمال ولايف ستايل","verified",true,"followers",184000));
    creators.put(obj("display_name","علي حسن","city","أربيل","primary_category","تقنية","verified",true,"followers",97000));
    creators.put(obj("display_name","نور أحمد","city","البصرة","primary_category","مطاعم وتجارب","verified",false,"followers",63000));
    creators.put(obj("display_name","مصطفى كريم","city","النجف","primary_category","رياضة","verified",true,"followers",211000));
    creators.put(obj("display_name","سارة محمد","city","الموصل","primary_category","موضة","verified",false,"followers",42000));
    creators.put(obj("display_name","حسين ياسر","city","كركوك","primary_category","سيارات","verified",false,"followers",118000));

    projects.put(obj("title","تغطية افتتاح فرع جديد","description","نبحث عن صانع محتوى لتغطية افتتاح فرع مطعم وتصوير ريل واحد مع 3 ستوريات.","slots_needed",4,"city","بغداد"));
    projects.put(obj("title","تجربة منتج عناية بالبشرة","description","تعاون مقابل منتج + أجر لصناعة فيديو تجربة صادق وواضح.","slots_needed",6,"city","العراق"));
    projects.put(obj("title","حملة تطبيق توصيل","description","مطلوب مؤثرين محليين لنشر فيديو قصير عن تجربة الطلب والتوصيل.","slots_needed",8,"city","بغداد والبصرة"));

    posts.put(obj("body","شنو أكثر نوع تعاون تحبون تشوفوه داخل مؤثر؟","likes_count",28,"replies_count",11));
    posts.put(obj("body","اليوم خلصت أول تعاون إلي عن طريق المنصة 🙌","likes_count",54,"replies_count",17));
    posts.put(obj("body","نصيحة للمؤثرين الجدد: خلو البروفايل واضح وأرقامكم محدثة دائماً.","likes_count",37,"replies_count",9));
  }

  JSONObject obj(Object... kv){
    JSONObject o=new JSONObject();
    try{for(int i=0;i<kv.length;i+=2)o.put(String.valueOf(kv[i]),kv[i+1]);}catch(Exception ignored){}
    return o;
  }

  void showAuth(boolean signup){
    LinearLayout root=Ui.v(this); root.setBackgroundColor(BG); root.setPadding(Ui.dp(this,22),Ui.dp(this,36),Ui.dp(this,22),Ui.dp(this,24));
    Space top=new Space(this); root.addView(top,new LinearLayout.LayoutParams(1,0,1));
    TextView mark=Ui.t(this,"م",36,0xffffffff,true); mark.setGravity(Gravity.CENTER); mark.setBackground(Ui.round(BLUE,24));
    LinearLayout.LayoutParams mlp=new LinearLayout.LayoutParams(Ui.dp(this,76),Ui.dp(this,76)); mlp.gravity=Gravity.CENTER_HORIZONTAL; root.addView(mark,mlp);
    TextView brand=Ui.t(this,"مؤثر",31,TEXT,true); brand.setGravity(Gravity.CENTER); root.addView(brand,Ui.mw());
    TextView sub=Ui.t(this,"منصة صناع المحتوى في العراق",14,MUTED,false); sub.setGravity(Gravity.CENTER); root.addView(sub,Ui.mw());

    LinearLayout card=Ui.card(this); card.setPadding(Ui.dp(this,18),Ui.dp(this,18),Ui.dp(this,18),Ui.dp(this,18));
    TextView title=Ui.t(this,signup?"إنشاء حساب":"تسجيل الدخول",23,TEXT,true); title.setGravity(Gravity.RIGHT); card.addView(title,Ui.mw());
    EditText name=Ui.input(this,"الاسم"); if(signup) card.addView(name,Ui.field(this));
    EditText email=Ui.input(this,"الإيميل"); email.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS); card.addView(email,Ui.field(this));
    EditText pass=Ui.input(this,"كلمة السر"); pass.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD); card.addView(pass,Ui.field(this));
    Button go=Ui.primary(this,signup?"إنشاء الحساب":"دخول"); card.addView(go,Ui.button(this));
    Button toggle=Ui.ghost(this,signup?"عندك حساب؟ سجل دخول":"ما عندك حساب؟ سجّل الآن"); card.addView(toggle,Ui.button(this));
    Button demo=Ui.ghost(this,"تجاوز تسجيل الدخول — نسخة تجريبية"); card.addView(demo,Ui.button(this));

    go.setOnClickListener(v->showApp());
    toggle.setOnClickListener(v->showAuth(!signup));
    demo.setOnClickListener(v->showApp());

    TextView note=Ui.t(this,"هذه نسخة معاينة محلية بدون باك إند أو اتصال بالإنترنت.",11,MUTED,false); note.setGravity(Gravity.CENTER); card.addView(note,Ui.mw());
    LinearLayout.LayoutParams clp=Ui.mw(); clp.topMargin=Ui.dp(this,28); root.addView(card,clp);
    Space bottom=new Space(this); root.addView(bottom,new LinearLayout.LayoutParams(1,0,1));
    setContentView(root);
  }

  void showApp(){
    LinearLayout root=Ui.v(this); root.setBackgroundColor(BG);
    LinearLayout head=Ui.h(this); head.setGravity(Gravity.CENTER_VERTICAL); head.setPadding(Ui.dp(this,18),0,Ui.dp(this,18),0); head.setBackgroundColor(0xffffffff);
    TextView brand=Ui.t(this,"مؤثر",23,TEXT,true); head.addView(brand,new LinearLayout.LayoutParams(0,Ui.dp(this,62),1));
    TextView nativeTag=Ui.t(this,"DEMO NATIVE",10,BLUE,true); nativeTag.setGravity(Gravity.CENTER); nativeTag.setBackground(Ui.round(0xffeef3ff,99));
    head.addView(nativeTag,new LinearLayout.LayoutParams(Ui.dp(this,112),Ui.dp(this,32)));
    root.addView(head,new LinearLayout.LayoutParams(-1,Ui.dp(this,62)));

    body=new FrameLayout(this); root.addView(body,new LinearLayout.LayoutParams(-1,0,1));
    nav=Ui.h(this); nav.setBackgroundColor(0xffffffff);
    String[][] tabs={{"home","الرئيسية","⌂"},{"discover","اكتشف","⌕"},{"projects","الفرص","▣"},{"community","المجتمع","◉"},{"me","حسابي","●"}};
    for(String[] x:tabs){
      Button b=Ui.nav(this,x[2]+"\n"+x[1]); b.setTag(x[0]); b.setOnClickListener(v->switchTab((String)v.getTag()));
      nav.addView(b,new LinearLayout.LayoutParams(0,Ui.dp(this,70),1));
    }
    root.addView(nav,new LinearLayout.LayoutParams(-1,Ui.dp(this,70)));
    setContentView(root); switchTab("home");
  }

  void switchTab(String id){
    tab=id;
    for(int i=0;i<nav.getChildCount();i++){
      Button b=(Button)nav.getChildAt(i); boolean on=id.equals(b.getTag());
      b.setTextColor(on?BLUE:MUTED); b.setBackgroundColor(on?0xffeef3ff:0xffffffff);
    }
    if(id.equals("home")) home(); else if(id.equals("discover")) discover(); else if(id.equals("projects")) projects(); else if(id.equals("community")) community(); else account();
  }

  ScrollView page(){
    ScrollView s=new ScrollView(this); LinearLayout box=Ui.v(this);
    box.setPadding(Ui.dp(this,14),Ui.dp(this,14),Ui.dp(this,14),Ui.dp(this,28)); s.addView(box); s.setTag(box); return s;
  }
  LinearLayout box(ScrollView s){return (LinearLayout)s.getTag();}
  void putPage(ScrollView s){body.removeAllViews();body.addView(s,new FrameLayout.LayoutParams(-1,-1));}
  void section(LinearLayout b,String title){TextView t=Ui.t(this,title,19,TEXT,true);t.setGravity(Gravity.RIGHT);LinearLayout.LayoutParams p=Ui.mw();p.topMargin=Ui.dp(this,8);p.bottomMargin=Ui.dp(this,10);b.addView(t,p);}

  void home(){
    ScrollView s=page(); LinearLayout b=box(s);
    LinearLayout hero=Ui.v(this); hero.setPadding(Ui.dp(this,20),Ui.dp(this,18),Ui.dp(this,20),Ui.dp(this,18)); hero.setBackground(Ui.round(BLUE,24));
    TextView h=Ui.t(this,"أهلاً بك في مؤثر",25,0xffffffff,true);h.setGravity(Gravity.RIGHT);hero.addView(h,Ui.mw());
    TextView q=Ui.t(this,"اكتشف صناع محتوى وفرص تعاون حقيقية",13,0xffdce5ff,false);q.setGravity(Gravity.RIGHT);hero.addView(q,Ui.mw());
    b.addView(hero,Ui.cardLp(this));

    section(b,"صناع محتوى مقترحون");
    for(int i=0;i<Math.min(4,creators.length());i++) b.addView(creatorCard(creators.optJSONObject(i)),Ui.cardLp(this));

    section(b,"فرص جديدة");
    for(int i=0;i<Math.min(2,projects.length());i++) b.addView(projectCard(projects.optJSONObject(i)),Ui.cardLp(this));
    putPage(s);
  }

  void discover(){
    ScrollView s=page();LinearLayout b=box(s);
    EditText search=Ui.input(this,"ابحث عن مؤثر...");b.addView(search,Ui.field(this));
    LinearLayout list=Ui.v(this);b.addView(list,Ui.mw());
    Runnable draw=()->{
      list.removeAllViews();String k=search.getText().toString().trim().toLowerCase(Locale.ROOT);
      for(int i=0;i<creators.length();i++){
        JSONObject x=creators.optJSONObject(i);
        String hay=(x.optString("display_name")+" "+x.optString("city")+" "+x.optString("primary_category")).toLowerCase(Locale.ROOT);
        if(k.isEmpty()||hay.contains(k))list.addView(creatorCard(x),Ui.cardLp(this));
      }
    };
    search.addTextChangedListener(new Ui.Watcher(draw));draw.run();putPage(s);
  }

  void projects(){
    ScrollView s=page();LinearLayout b=box(s);
    LinearLayout line=Ui.h(this);TextView t=Ui.t(this,"فرص التعاون",21,TEXT,true);t.setGravity(Gravity.RIGHT);
    line.addView(t,new LinearLayout.LayoutParams(0,Ui.dp(this,48),1));
    Button add=Ui.primary(this,"+ فرصة");line.addView(add,new LinearLayout.LayoutParams(Ui.dp(this,110),Ui.dp(this,46)));
    b.addView(line,Ui.mw());add.setOnClickListener(v->projectDialog());
    for(int i=0;i<projects.length();i++)b.addView(projectCard(projects.optJSONObject(i)),Ui.cardLp(this));
    putPage(s);
  }

  void community(){
    ScrollView s=page();LinearLayout b=box(s);
    LinearLayout composer=Ui.card(this);composer.setPadding(Ui.dp(this,14),Ui.dp(this,14),Ui.dp(this,14),Ui.dp(this,14));
    EditText post=Ui.input(this,"بشنو تفكر؟");post.setSingleLine(false);post.setMinLines(3);composer.addView(post,Ui.area(this));
    Button send=Ui.primary(this,"نشر");composer.addView(send,Ui.button(this));
    b.addView(composer,Ui.cardLp(this));
    LinearLayout feed=Ui.v(this);b.addView(feed,Ui.mw());
    Runnable redraw=()->{feed.removeAllViews();for(int i=0;i<posts.length();i++)feed.addView(postCard(posts.optJSONObject(i)),Ui.cardLp(this));};
    send.setOnClickListener(v->{String txt=post.getText().toString().trim();if(!txt.isEmpty()){JSONArray n=new JSONArray();n.put(obj("body",txt,"likes_count",0,"replies_count",0));for(int i=0;i<posts.length();i++)n.put(posts.opt(i));posts=n;post.setText("");redraw.run();}});
    redraw.run();putPage(s);
  }

  void account(){
    ScrollView s=page();LinearLayout b=box(s);
    LinearLayout c=Ui.card(this);c.setGravity(Gravity.CENTER_HORIZONTAL);c.setPadding(Ui.dp(this,20),Ui.dp(this,22),Ui.dp(this,20),Ui.dp(this,22));
    TextView av=Ui.t(this,"غ",32,BLUE,true);av.setGravity(Gravity.CENTER);av.setBackground(Ui.round(0xffeef3ff,24));c.addView(av,new LinearLayout.LayoutParams(Ui.dp(this,86),Ui.dp(this,86)));
    TextView n=Ui.t(this,"غازي — حساب تجريبي",22,TEXT,true);n.setGravity(Gravity.CENTER);c.addView(n,Ui.mw());
    TextView mail=Ui.t(this,"demo@joinmoathir.local",12,MUTED,false);mail.setGravity(Gravity.CENTER);c.addView(mail,Ui.mw());
    TextView bio=Ui.t(this,"هذا الحساب موجود فقط لغرض معاينة القالب.",13,0xff344057,false);bio.setGravity(Gravity.CENTER);c.addView(bio,Ui.mw());
    Button out=Ui.ghost(this,"العودة لشاشة الدخول");c.addView(out,Ui.button(this));out.setOnClickListener(v->showAuth(false));
    b.addView(c,Ui.cardLp(this));putPage(s);
  }

  View creatorCard(JSONObject x){
    LinearLayout c=Ui.card(this);c.setPadding(Ui.dp(this,14),Ui.dp(this,14),Ui.dp(this,14),Ui.dp(this,14));
    LinearLayout r=Ui.h(this);
    TextView av=Ui.t(this,initial(x.optString("display_name")),22,BLUE,true);av.setGravity(Gravity.CENTER);av.setBackground(Ui.round(0xffeef3ff,20));
    r.addView(av,new LinearLayout.LayoutParams(Ui.dp(this,58),Ui.dp(this,58)));
    LinearLayout info=Ui.v(this);info.setPadding(Ui.dp(this,12),0,0,0);
    TextView n=Ui.t(this,x.optString("display_name","صانع محتوى")+(x.optBoolean("verified")?" ✓":""),16,TEXT,true);n.setGravity(Gravity.RIGHT);info.addView(n,Ui.mw());
    TextView m=Ui.t(this,meta(x.optString("primary_category"),x.optString("city")),12,MUTED,false);m.setGravity(Gravity.RIGHT);info.addView(m,Ui.mw());
    TextView ff=Ui.t(this,followers(x.optLong("followers"))+" متابع",11,BLUE,true);ff.setGravity(Gravity.RIGHT);info.addView(ff,Ui.mw());
    r.addView(info,new LinearLayout.LayoutParams(0,-2,1));c.addView(r,Ui.mw());
    c.setOnClickListener(v->new AlertDialog.Builder(this).setTitle(x.optString("display_name","مؤثر")).setMessage(meta(x.optString("primary_category"),x.optString("city"))+"\n"+followers(x.optLong("followers"))+" متابع").setPositiveButton("حسناً",null).show());
    return c;
  }

  View projectCard(JSONObject x){
    LinearLayout c=Ui.card(this);c.setPadding(Ui.dp(this,15),Ui.dp(this,15),Ui.dp(this,15),Ui.dp(this,15));
    TextView t=Ui.t(this,x.optString("title","فرصة تعاون"),16,TEXT,true);t.setGravity(Gravity.RIGHT);c.addView(t,Ui.mw());
    TextView d=Ui.t(this,x.optString("description"),13,0xff344057,false);d.setGravity(Gravity.RIGHT);d.setLineSpacing(0,1.2f);c.addView(d,Ui.mw());
    TextView m=Ui.t(this,x.optInt("slots_needed",1)+" مطلوب • "+x.optString("city","العراق"),11,MUTED,false);m.setGravity(Gravity.RIGHT);c.addView(m,Ui.mw());
    Button apply=Ui.ghost(this,"معاينة التقديم");c.addView(apply,Ui.button(this));
    apply.setOnClickListener(v->Toast.makeText(this,"تمت المعاينة فقط — لا يوجد إرسال حقيقي",Toast.LENGTH_SHORT).show());
    return c;
  }

  View postCard(JSONObject x){
    LinearLayout c=Ui.card(this);c.setPadding(Ui.dp(this,15),Ui.dp(this,15),Ui.dp(this,15),Ui.dp(this,15));
    TextView author=Ui.t(this,"مستخدم مؤثر",13,TEXT,true);author.setGravity(Gravity.RIGHT);c.addView(author,Ui.mw());
    TextView bt=Ui.t(this,x.optString("body"),14,0xff344057,false);bt.setGravity(Gravity.RIGHT);c.addView(bt,Ui.mw());
    TextView m=Ui.t(this,"♡ "+x.optInt("likes_count")+"   ◌ "+x.optInt("replies_count"),11,MUTED,false);m.setGravity(Gravity.RIGHT);c.addView(m,Ui.mw());
    return c;
  }

  void projectDialog(){
    LinearLayout b=Ui.v(this);b.setPadding(Ui.dp(this,18),0,Ui.dp(this,18),0);
    EditText title=Ui.input(this,"عنوان الفرصة");EditText desc=Ui.input(this,"تفاصيل التعاون المطلوب");desc.setSingleLine(false);desc.setMinLines(4);
    b.addView(title,Ui.field(this));b.addView(desc,Ui.area(this));
    new AlertDialog.Builder(this).setTitle("نشر فرصة تجريبية").setView(b).setNegativeButton("إلغاء",null).setPositiveButton("نشر",(d,w)->{
      String t=title.getText().toString().trim(),de=desc.getText().toString().trim();
      if(!t.isEmpty()){JSONArray n=new JSONArray();n.put(obj("title",t,"description",de,"slots_needed",1,"city","العراق"));for(int i=0;i<projects.length();i++)n.put(projects.opt(i));projects=n;projects();}
    }).show();
  }

  String initial(String s){s=s==null?"":s.trim();return s.isEmpty()?"م":s.substring(0,1);}
  String meta(String a,String b){if(a==null)a="";if(b==null)b="";if(a.isEmpty())return b;if(b.isEmpty())return a;return a+" • "+b;}
  String followers(long n){if(n>=1000000)return String.format(Locale.US,"%.1fM",n/1000000.0);if(n>=1000)return String.format(Locale.US,"%.0fK",n/1000.0);return String.valueOf(n);}
}
