package shiful.android.healthconsult.patient;

import androidbangladesh.bengali.support.BengaliUnicodeString;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import shiful.android.healthconsult.Constant;
import shiful.android.healthconsult.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthTipsActivity extends AppCompatActivity {
    ListView CustomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Health Information");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        CustomList=(ListView)findViewById(R.id.health_tips_list);
        String topicList[] = {"অগ্নাশয়", "অস্থি ও অস্থিসন্ধি", "ওজন ও উচ্চতা", "কোভিড-১৯ অভিভাবকদের কী জানা উচিত", "কোভিড ১৯ সম্পর্কে বিস্তারিত", "করোনা ভাইরাস এর পরিসংখ্যান", "কিডনী ও মূত্রনালী", "ক্যান্সার / টিউমার", "ঘুম", "ঘরোয়া চিকিৎসা", "হাত ও পা", "হার্নিয়া অপারেশন", "চোখ", "ত্বক ও চুল", "দাঁত ও মাড়ি", "দাম্পত্য জীবন", "নাক, কান ও গলা", "নখের যত্ন", "পাকস্থলী ও পরিপাকতন্ত্র", "প্রোস্টেট গ্লান্ড / গ্রন্থি", "ফুসফুস ও শ্বাসনালী", "বিভিন্ন পরীক্ষা / ডায়াগনোসিস", "মস্তিস্ক ও স্নায়ুতন্ত্র", "মানসিক স্বাস্থ্য", "মা ও শিশুর স্বাস্থ্য", "মাংশপেশী", "মুখ ও জিহবা", "মায়ের দুধ", "লিভার পিত্ত ও পেটের পীড়া", "হৃৎযন্ত্র রক্ত ও রক্তনালী", "রূপচর্চা", "নারীর স্বাস্থ্য", "পুরুষের স্বাস্থ্য", "প্রজনন স্বাস্থ্য", "প্রবীণদের স্বাস্থ্য", "যৌন স্বাস্থ্য", "স্বাস্থ্য টিপস", "বিবিধ"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.health_tips_list_items, R.id.textView, topicList);
        CustomList.setAdapter(arrayAdapter);

        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(HealthTipsActivity.this, WebActivity.class);
                        intent.putExtra("url", "https://bdhealth.org/%e0%a6%85%e0%a6%97%e0%a7%8d%e0%a6%a8%e0%a6%be%e0%a6%b6%e0%a7%9f%e0%a7%87%e0%a6%b0-%e0%a6%ac%e0%a6%bf%e0%a6%ad%e0%a6%bf%e0%a6%a8%e0%a7%8d%e0%a6%a8-%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d-211/");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent i = new Intent(HealthTipsActivity.this, WebActivity.class);
                        i.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%85%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a6%bf-%e0%a6%93-%e0%a6%85%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a6%bf%e0%a6%b8%e0%a6%a8%e0%a7%8d%e0%a6%a7%e0%a6%bf/");
                        startActivity(i);
                        break;
                    case 2:
                        Intent a = new Intent(HealthTipsActivity.this, WebActivity.class);
                        a.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%93-%e0%a6%b8%e0%a7%8c%e0%a6%a8%e0%a7%8d%e0%a6%a6%e0%a6%b0%e0%a7%8d%e0%a6%af/%e0%a6%93%e0%a6%9c%e0%a6%a8-%e0%a6%93-%e0%a6%89%e0%a6%9a%e0%a7%8d%e0%a6%9a%e0%a6%a4%e0%a6%be/");
                        startActivity(a);
                        break;
                    case 3:
                        Intent b = new Intent(HealthTipsActivity.this, WebActivity.class);
                        b.putExtra("url", "https://www.unicef.org/bangladesh/%E0%A6%95%E0%A6%B0%E0%A7%8B%E0%A6%A8%E0%A6%BE%E0%A6%AD%E0%A6%BE%E0%A6%87%E0%A6%B0%E0%A6%BE%E0%A6%B8-%E0%A6%B0%E0%A7%8B%E0%A6%97-%E0%A6%95%E0%A7%8B%E0%A6%AD%E0%A6%BF%E0%A6%A1-%E0%A7%A7%E0%A7%AF-%E0%A6%85%E0%A6%AD%E0%A6%BF%E0%A6%AD%E0%A6%BE%E0%A6%AC%E0%A6%95%E0%A6%A6%E0%A7%87%E0%A6%B0-%E0%A6%95%E0%A7%80-%E0%A6%9C%E0%A6%BE%E0%A6%A8%E0%A6%BE-%E0%A6%89%E0%A6%9A%E0%A6%BF%E0%A6%A4");
                        startActivity(b);
                        break;
                    case 4:
                        Intent c = new Intent(HealthTipsActivity.this, WebActivity.class);
                        c.putExtra("url", "https://bn.wikipedia.org/wiki/%E0%A7%A8%E0%A7%A6%E0%A7%A7%E0%A7%AF%E2%80%93%E0%A7%A8%E0%A7%A6_%E0%A6%95%E0%A6%B0%E0%A7%8B%E0%A6%A8%E0%A6%BE%E0%A6%AD%E0%A6%BE%E0%A6%87%E0%A6%B0%E0%A6%BE%E0%A6%B8%E0%A7%87%E0%A6%B0_%E0%A6%AC%E0%A7%88%E0%A6%B6%E0%A7%8D%E0%A6%AC%E0%A6%BF%E0%A6%95_%E0%A6%AE%E0%A6%B9%E0%A6%BE%E0%A6%AE%E0%A6%BE%E0%A6%B0%E0%A7%80");
                        startActivity(c);
                        break;
                    case 5:
                        Intent d = new Intent(HealthTipsActivity.this, WebActivity.class);
                        d.putExtra("url", "https://news.google.com/covid19/map?hl=bn&gl=BD&ceid=BD:bn");
                        startActivity(d);
                        break;
                    case 6:
                        Intent e = new Intent(HealthTipsActivity.this, WebActivity.class);
                        e.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%95%e0%a6%bf%e0%a6%a1%e0%a6%a8%e0%a7%80-%e0%a6%93-%e0%a6%ae%e0%a7%82%e0%a6%a4%e0%a7%8d%e0%a6%b0%e0%a6%a8%e0%a6%be%e0%a6%b2%e0%a7%80/");
                        startActivity(e);
                        break;
                    case 7:
                        Intent f = new Intent(HealthTipsActivity.this, WebActivity.class);
                        f.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%95%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a8%e0%a7%8d%e0%a6%b8%e0%a6%be%e0%a6%b0%e0%a6%9f%e0%a6%bf%e0%a6%89%e0%a6%ae%e0%a6%be%e0%a6%b0/");
                        startActivity(f);
                        break;
                    case 8:
                        Intent g = new Intent(HealthTipsActivity.this, WebActivity.class);
                        g.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%98%e0%a7%81%e0%a6%ae/");
                        startActivity(g);
                        break;
                    case 9:
                        Intent h = new Intent(HealthTipsActivity.this, WebActivity.class);
                        h.putExtra("url", "https://bdhealth.org/category/%e0%a6%86%e0%a6%aa%e0%a6%a8%e0%a6%be%e0%a6%b0-%e0%a6%9a%e0%a6%bf%e0%a6%95%e0%a6%bf%e0%a6%a4%e0%a7%8d%e2%80%8d%e0%a6%b8%e0%a6%be/%e0%a6%98%e0%a6%b0%e0%a7%8b%e0%a7%9f%e0%a6%be-%e0%a6%9a%e0%a6%bf%e0%a6%95%e0%a6%bf%e0%a7%8e%e0%a6%b8%e0%a6%be/");
                        startActivity(h);
                        break;
                    case 10:
                        Intent j = new Intent(HealthTipsActivity.this, WebActivity.class);
                        j.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%93-%e0%a6%b8%e0%a7%8c%e0%a6%a8%e0%a7%8d%e0%a6%a6%e0%a6%b0%e0%a7%8d%e0%a6%af/%e0%a6%b9%e0%a6%be%e0%a6%a4-%e0%a6%93-%e0%a6%aa%e0%a6%be/");
                        startActivity(j);
                        break;
                    case 11:
                        Intent k = new Intent(HealthTipsActivity.this, WebActivity.class);
                        k.putExtra("url", "https://bdhealth.org/%e0%a6%b9%e0%a6%be%e0%a6%b0%e0%a7%8d%e0%a6%a8%e0%a6%bf%e0%a7%9f%e0%a6%be-%e0%a6%85%e0%a6%aa%e0%a6%be%e0%a6%b0%e0%a7%87%e0%a6%b6%e0%a6%a8-245/");
                        startActivity(k);
                        break;
                    case 12:
                        Intent z = new Intent(HealthTipsActivity.this, WebActivity.class);
                        z.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%9a%e0%a7%8b%e0%a6%96/");
                        startActivity(z);
                        break;
                    case 13:
                        Intent m = new Intent(HealthTipsActivity.this, WebActivity.class);
                        m.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%a4%e0%a7%8d%e0%a6%ac%e0%a6%95-%e0%a6%93-%e0%a6%9a%e0%a7%81%e0%a6%b2/");
                        startActivity(m);
                        break;
                    case 14:
                        Intent n = new Intent(HealthTipsActivity.this, WebActivity.class);
                        n.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%a6%e0%a6%be%e0%a6%81%e0%a6%a4-%e0%a6%93-%e0%a6%ae%e0%a6%be%e0%a7%9c%e0%a6%bf/");
                        startActivity(n);
                        break;
                    case 15:
                        Intent o = new Intent(HealthTipsActivity.this, WebActivity.class);
                        o.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%93-%e0%a6%b8%e0%a7%8c%e0%a6%a8%e0%a7%8d%e0%a6%a6%e0%a6%b0%e0%a7%8d%e0%a6%af/%e0%a6%a6%e0%a6%be%e0%a6%ae%e0%a7%8d%e0%a6%aa%e0%a6%a4%e0%a7%8d%e0%a6%af-%e0%a6%9c%e0%a7%80%e0%a6%ac%e0%a6%a8/");
                        startActivity(o);
                        break;
                    case 16:
                        Intent p = new Intent(HealthTipsActivity.this, WebActivity.class);
                        p.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%a8%e0%a6%be%e0%a6%95%e0%a6%95%e0%a6%be%e0%a6%a8-%e0%a6%93-%e0%a6%97%e0%a6%b2%e0%a6%be/");
                        startActivity(p);
                        break;
                    case 17:
                        Intent q = new Intent(HealthTipsActivity.this, WebActivity.class);
                        q.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%93-%e0%a6%b8%e0%a7%8c%e0%a6%a8%e0%a7%8d%e0%a6%a6%e0%a6%b0%e0%a7%8d%e0%a6%af/%e0%a6%a8%e0%a6%96%e0%a7%87%e0%a6%b0-%e0%a6%af%e0%a6%a4%e0%a7%8d%e0%a6%a8/");
                        startActivity(q);
                        break;
                    case 18:
                        Intent r = new Intent(HealthTipsActivity.this, WebActivity.class);
                        r.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%aa%e0%a6%be%e0%a6%95%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a6%b2%e0%a7%80-%e0%a6%93-%e0%a6%aa%e0%a6%b0%e0%a6%bf%e0%a6%aa%e0%a6%be%e0%a6%95%e0%a6%a4%e0%a6%a8%e0%a7%8d%e0%a6%a4%e0%a7%8d%e0%a6%b0/");
                        startActivity(r);
                        break;
                    case 19:
                        Intent s = new Intent(HealthTipsActivity.this, WebActivity.class);
                        s.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%aa%e0%a7%8d%e0%a6%b0%e0%a7%87%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%9f%e0%a7%87%e0%a6%9f-%e0%a6%97%e0%a7%8d%e0%a6%b2%e0%a6%be%e0%a6%a8%e0%a7%8d%e0%a6%a1%e0%a6%97%e0%a7%8d%e0%a6%b0%e0%a6%a8%e0%a7%8d/");
                        startActivity(s);
                        break;
                    case 20:
                        Intent t = new Intent(HealthTipsActivity.this, WebActivity.class);
                        t.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%ab%e0%a7%81%e0%a6%b8%e0%a6%ab%e0%a7%81%e0%a6%b8-%e0%a6%93-%e0%a6%b6%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a6%a8%e0%a6%be%e0%a6%b2%e0%a7%80/");
                        startActivity(t);
                        break;
                    case 21:
                        Intent u = new Intent(HealthTipsActivity.this, WebActivity.class);
                        u.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%ac%e0%a6%bf%e0%a6%ad%e0%a6%bf%e0%a6%a8%e0%a7%8d%e0%a6%a8-%e0%a6%aa%e0%a6%b0%e0%a7%80%e0%a6%95%e0%a7%8d%e0%a6%b7%e0%a6%be-%e0%a6%a1%e0%a6%be%e0%a7%9f%e0%a6%be%e0%a6%97%e0%a6%a8%e0%a7%8b/");
                        startActivity(u);
                        break;
                    case 22:
                        Intent v = new Intent(HealthTipsActivity.this, WebActivity.class);
                        v.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%ae%e0%a6%b8%e0%a7%8d%e0%a6%a4%e0%a6%bf%e0%a6%b8%e0%a7%8d%e0%a6%95-%e0%a6%93-%e0%a6%b8%e0%a7%8d%e0%a6%a8%e0%a6%be%e0%a7%9f%e0%a7%81%e0%a6%a4%e0%a6%a8%e0%a7%8d%e0%a6%a4%e0%a7%8d%e0%a6%b0/");
                        startActivity(v);
                        break;
                    case 23:
                        Intent w = new Intent(HealthTipsActivity.this, WebActivity.class);
                        w.putExtra("url", "https://bdhealth.org/category/%e0%a6%86%e0%a6%aa%e0%a6%a8%e0%a6%be%e0%a6%b0-%e0%a6%9a%e0%a6%bf%e0%a6%95%e0%a6%bf%e0%a6%a4%e0%a7%8d%e2%80%8d%e0%a6%b8%e0%a6%be/%e0%a6%ae%e0%a6%be%e0%a6%a8%e0%a6%b8%e0%a6%bf%e0%a6%95-%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af/");
                        startActivity(w);
                        break;
                    case 24:
                        Intent aa = new Intent(HealthTipsActivity.this, WebActivity.class);
                        aa.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%b8%e0%a7%87%e0%a6%ac%e0%a6%be/%e0%a6%ae%e0%a6%be-%e0%a6%93-%e0%a6%b6%e0%a6%bf%e0%a6%b6%e0%a7%81%e0%a6%b0-%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af/");
                        startActivity(aa);
                        break;
                    case 25:
                        Intent bb = new Intent(HealthTipsActivity.this, WebActivity.class);
                        bb.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%ae%e0%a6%be%e0%a6%82%e0%a6%b6%e0%a6%aa%e0%a7%87%e0%a6%b6%e0%a7%80/");
                        startActivity(bb);
                        break;
                    case 26:
                        Intent cc = new Intent(HealthTipsActivity.this, WebActivity.class);
                        cc.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%ae%e0%a7%81%e0%a6%96-%e0%a6%93-%e0%a6%9c%e0%a6%bf%e0%a6%b9%e0%a6%ac%e0%a6%be/");
                        startActivity(cc);
                        break;
                    case 27:
                        Intent dd = new Intent(HealthTipsActivity.this, WebActivity.class);
                        dd.putExtra("url", "https://bdhealth.org/tag/%e0%a6%ae%e0%a6%be%e0%a7%9f%e0%a7%87%e0%a6%b0-%e0%a6%a6%e0%a7%81%e0%a6%a7/");
                        startActivity(dd);
                        break;
                    case 28:
                        Intent ee = new Intent(HealthTipsActivity.this, WebActivity.class);
                        ee.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%b2%e0%a6%bf%e0%a6%ad%e0%a6%be%e0%a6%b0-%e0%a6%aa%e0%a6%bf%e0%a6%a4%e0%a7%8d%e0%a6%a4-%e0%a6%93-%e0%a6%aa%e0%a7%87%e0%a6%9f%e0%a7%87%e0%a6%b0-%e0%a6%aa%e0%a7%80%e0%a7%9c%e0%a6%be/");
                        startActivity(ee);
                        break;
                    case 29:
                        Intent ff = new Intent(HealthTipsActivity.this, WebActivity.class);
                        ff.putExtra("url", "https://bdhealth.org/category/%e0%a6%b0%e0%a7%8b%e0%a6%97-%e0%a6%ac%e0%a7%8d%e0%a6%af%e0%a6%be%e0%a6%a7%e0%a6%bf/%e0%a6%b9%e0%a7%83%e0%a7%8e%e0%a6%af%e0%a6%a8%e0%a7%8d%e0%a6%a4%e0%a7%8d%e0%a6%b0-%e0%a6%b0%e0%a6%95%e0%a7%8d%e0%a6%a4-%e0%a6%93-%e0%a6%b0%e0%a6%95%e0%a7%8d%e0%a6%a4%e0%a6%a8%e0%a6%be%e0%a6%b2/");
                        startActivity(ff);
                        break;
                    case 30:
                        Intent gg = new Intent(HealthTipsActivity.this, WebActivity.class);
                        gg.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%93-%e0%a6%b8%e0%a7%8c%e0%a6%a8%e0%a7%8d%e0%a6%a6%e0%a6%b0%e0%a7%8d%e0%a6%af/%e0%a6%b0%e0%a7%82%e0%a6%aa%e0%a6%9a%e0%a6%b0%e0%a7%8d%e0%a6%9a%e0%a6%be/");
                        startActivity(gg);
                        break;
                    case 31:
                        Intent hh = new Intent(HealthTipsActivity.this, WebActivity.class);
                        hh.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%b8%e0%a7%87%e0%a6%ac%e0%a6%be/%e0%a6%a8%e0%a6%be%e0%a6%b0%e0%a7%80%e0%a6%b0-%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af/");
                        startActivity(hh);
                        break;
                    case 32:
                        Intent ii = new Intent(HealthTipsActivity.this, WebActivity.class);
                        ii.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%b8%e0%a7%87%e0%a6%ac%e0%a6%be/%e0%a6%aa%e0%a7%81%e0%a6%b0%e0%a7%81%e0%a6%b7%e0%a7%87%e0%a6%b0-%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af/");
                        startActivity(ii);
                        break;
                    case 33:
                        Intent jj = new Intent(HealthTipsActivity.this, WebActivity.class);
                        jj.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%b8%e0%a7%87%e0%a6%ac%e0%a6%be/%e0%a6%aa%e0%a7%8d%e0%a6%b0%e0%a6%9c%e0%a6%a8%e0%a6%a8-%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af/");
                        startActivity(jj);
                        break;
                    case 34:
                        Intent kk = new Intent(HealthTipsActivity.this, WebActivity.class);
                        kk.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%b8%e0%a7%87%e0%a6%ac%e0%a6%be/%e0%a6%aa%e0%a7%8d%e0%a6%b0%e0%a6%ac%e0%a7%80%e0%a6%a3%e0%a6%a6%e0%a7%87%e0%a6%b0-%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af/");
                        startActivity(kk);
                        break;
                    case 35:
                        Intent mm = new Intent(HealthTipsActivity.this, WebActivity.class);
                        mm.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%b8%e0%a7%87%e0%a6%ac%e0%a6%be/%e0%a6%af%e0%a7%8c%e0%a6%a8-%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af/");
                        startActivity(mm);
                        break;
                    case 36:
                        Intent nn = new Intent(HealthTipsActivity.this, WebActivity.class);
                        nn.putExtra("url", "https://bdhealth.org/category/%e0%a6%b8%e0%a7%8d%e0%a6%ac%e0%a6%be%e0%a6%b8%e0%a7%8d%e0%a6%a5%e0%a7%8d%e0%a6%af-%e0%a6%9f%e0%a6%bf%e0%a6%aa%e0%a6%b8/");
                        startActivity(nn);
                        break;
                    case 37:
                        Intent oo = new Intent(HealthTipsActivity.this, WebActivity.class);
                        oo.putExtra("url", "https://bdhealth.org/category/%e0%a6%ac%e0%a6%bf%e0%a6%ac%e0%a6%bf%e0%a6%a7/");
                        startActivity(oo);
                        break;
                    default:
                        //doesn't match with any of case above
                        break;
                }
            }
        });

    }
    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

