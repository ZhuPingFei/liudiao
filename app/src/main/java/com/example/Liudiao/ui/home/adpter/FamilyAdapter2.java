package com.example.Liudiao.ui.home.adpter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Liudiao.R;

import java.util.List;
import java.util.Map;

public class FamilyAdapter2 extends BaseAdapter {

    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;


    public FamilyAdapter2(Context context, List<Map<String,String>> mDatas, ListView listView) {
        this.mDatas = mDatas;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.listView = listView;

    }

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        if (mDatas == null) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //preferences  = mContext.getSharedPreferences("daiban", Activity.MODE_PRIVATE);
        //editor = preferences.edit();
        final FamilyAdapter2.ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.baogao_family, null);
                holder = new FamilyAdapter2.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            }
            else {
                holder = (FamilyAdapter2.ViewHolder) convertView.getTag();
            }

            int relationText = Integer.parseInt(mDatas.get(position).get("relation")+"");
            String relation;
            if (relationText ==0){
                relation="未知";
            }else if (relationText == 1){
                relation="父亲";
            }else if (relationText == 2){
                relation="母亲";
            }else if (relationText == 3){
                relation="儿子";
            }else if (relationText == 4){
                relation="女儿";
            }else if (relationText == 5){
                relation="夫妻";
            }else if (relationText == 6){
                relation="兄弟";
            }else if (relationText == 7){
                relation="姐妹";
            }else if (relationText == 8){
                relation="（外）祖父母";
            }else if (relationText == 9){
                relation="（外）孙子孙女";
            }else {
                relation="（其他关系）";
            }

            //4.9
            String contact_type = mDatas.get(position).get("contact_type")+"";
            StringBuffer stringBuffer = new StringBuffer("");
            if (contact_type.length()!=0&& !contact_type.equals("null")) {
                String sym = contact_type;
                if (sym.length() == 1) {
                    if (Integer.parseInt(sym) == 1) {
                        stringBuffer.append("同吃");
                    } else if (Integer.parseInt(sym) == 2) {
                        stringBuffer.append("同行");
                    } else if (Integer.parseInt(sym) == 3) {
                        stringBuffer.append("同住");
                    } else {
                        stringBuffer.append("同事");
                    }
                } else {
                    String[] split = sym.split(",");
                    for (int i = 0; i < split.length; i++) {
                        if (Integer.parseInt(split[i]) == 1) {
                            stringBuffer.append("同吃 ");
                        } else if (Integer.parseInt(split[i]) == 2) {
                            stringBuffer.append("同行 ");
                        } else if (Integer.parseInt(split[i]) == 3) {
                            stringBuffer.append("同住 ");
                        } else {
                            stringBuffer.append("同事");
                        }
                    }
                }
            }
            String cardID;
            if ( (mDatas.get(position).get("card_id")+"").equals("null") ||(mDatas.get(position).get("card_id")+"").equals("")){
                cardID = "";
            }else {
                cardID = mDatas.get(position).get("card_id")+"";
            }
            String address = mDatas.get(position).get("address")+"";
            String address_detail =mDatas.get(position).get("address_detail")+"";
            String dizhi;
            if (address.equals("null") || address.equals("")){
                if (address_detail.equals("null") || address_detail.equals("")){
                    dizhi = "";
                }else {
                    dizhi = address_detail;
                }
            }else {
                if (address_detail.equals("null") || address_detail.equals("")){
                    dizhi = address;
                }else {
                    dizhi = address+address_detail;
                }
            }

            if (!cardID.equals("null")&&cardID.length()!=0){
                if (!dizhi.equals("")){
                    holder.name.setText("    该病例曾与其"+relation+" "+mDatas.get(position).get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+mDatas.get(position).get("mobile").toString()
                            +",身份证号是 "+cardID+"，住在 "+dizhi+"。");

                }else {
                    holder.name.setText("    该病例曾与其"+relation+" "+mDatas.get(position).get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+mDatas.get(position).get("mobile").toString()
                            +",身份证号是 "+cardID+"。");
                }
            }else {
                holder.name.setText("    该病例曾与其"+relation+" "+mDatas.get(position).get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+mDatas.get(position).get("mobile").toString()+"。");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        TextView name;

    }

}
