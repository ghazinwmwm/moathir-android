package com.joinmoathir.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.*;
import android.view.*;
import android.widget.*;

public class Ui {
  static LinearLayout v(Context c){LinearLayout l=new LinearLayout(c);l.setOrientation(LinearLayout.VERTICAL);l.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);return l;}
  static LinearLayout h(Context c){LinearLayout l=new LinearLayout(c);l.setOrientation(LinearLayout.HORIZONTAL);l.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);return l;}
  static LinearLayout card(Context c){LinearLayout l=v(c);l.setBackground(round(0xffffffff,22));l.setElevation(dp(c,1));return l;}
  static TextView t(Context c,String s,int size,int color,boolean bold){TextView t=new TextView(c);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setTypeface(Typeface.DEFAULT,bold?Typeface.BOLD:Typeface.NORMAL);t.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);return t;}
  static EditText input(Context c,String hint){EditText e=new EditText(c);e.setHint(hint);e.setHintTextColor(0xff8b95a7);e.setTextColor(0xff172033);e.setTextSize(16);e.setSingleLine(true);e.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);e.setPadding(dp(c,14),0,dp(c,14),0);e.setBackground(round(0xfff8fafd,15));e.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);return e;}
  static Button primary(Context c,String label){Button b=new Button(c);b.setText(label);b.setTextColor(Color.WHITE);b.setTextSize(14);b.setTypeface(Typeface.DEFAULT,Typeface.BOLD);b.setAllCaps(false);b.setGravity(Gravity.CENTER);b.setBackground(round(0xff446ff2,16));return b;}
  static Button ghost(Context c,String label){Button b=new Button(c);b.setText(label);b.setTextColor(0xff446ff2);b.setTextSize(13);b.setTypeface(Typeface.DEFAULT,Typeface.BOLD);b.setAllCaps(false);b.setGravity(Gravity.CENTER);b.setBackgroundColor(Color.TRANSPARENT);return b;}
  static Button nav(Context c,String label){Button b=ghost(c,label);b.setTextColor(0xff7b869b);b.setTextSize(11);return b;}
  static TextView empty(Context c,String s){TextView t=t(c,s,13,0xff7b869b,false);t.setGravity(Gravity.CENTER);t.setPadding(dp(c,12),dp(c,34),dp(c,12),dp(c,34));return t;}
  static GradientDrawable round(int color,int radius){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(radius*3f);if(color==0xffffffff)d.setStroke(1,0xffe6eaf1);return d;}
  static int dp(Context c,int n){return Math.round(n*c.getResources().getDisplayMetrics().density);}
  static LinearLayout.LayoutParams mw(){return new LinearLayout.LayoutParams(-1,-2);} static LinearLayout.LayoutParams field(Context c){LinearLayout.LayoutParams p=mw();p.height=dp(c,52);p.topMargin=dp(c,10);return p;} static LinearLayout.LayoutParams area(Context c){LinearLayout.LayoutParams p=mw();p.height=dp(c,104);p.topMargin=dp(c,10);return p;} static LinearLayout.LayoutParams button(Context c){LinearLayout.LayoutParams p=mw();p.height=dp(c,52);p.topMargin=dp(c,10);return p;} static LinearLayout.LayoutParams cardLp(Context c){LinearLayout.LayoutParams p=mw();p.bottomMargin=dp(c,10);return p;}
  static class Watcher implements TextWatcher{final Runnable r;Watcher(Runnable x){r=x;}public void beforeTextChanged(CharSequence s,int a,int b,int c){}public void onTextChanged(CharSequence s,int a,int b,int c){r.run();}public void afterTextChanged(Editable e){}}
}
