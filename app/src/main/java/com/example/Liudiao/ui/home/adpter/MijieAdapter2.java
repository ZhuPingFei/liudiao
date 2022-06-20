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

public class MijieAdapter2 extends BaseAdapter {

    View.OnClickListener onClickListener;
    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context mContext;
    private ListView listView;


    public MijieAdapter2(Context context, List<Map<String,String>> mDatas, ListView listView) {
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
        final MijieAdapter2.ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.baogao_mijie, null);
                holder = new MijieAdapter2.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            }
            else {
                holder = (MijieAdapter2.ViewHolder) convertView.getTag();
            }

            String contact_type = mDatas.get(position).get("contact_type".toString());
            StringBuffer stringBuffer = new StringBuffer("");
            if (contact_type.length()!=0&& !contact_type.equals("null")) {
                String sym = contact_type.substring(1, contact_type.length() - 1);
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

            String idCard = mDatas.get(position).get("card_id").toString();
            if (!idCard.equals("null")&&idCard.length()!=0){
                if (dizhi.equals("")){
                    holder.name.setText("    该病例曾与其非家人密接 "+mDatas.get(position).get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+mDatas.get(position).get("mobile").toString()
                            +",身份证号是 "+mDatas.get(position).get("card_id").toString()+"，住在 "+dizhi+"。");

                }else {
                    holder.name.setText("    该病例曾与其非家人密接 "+mDatas.get(position).get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+mDatas.get(position).get("mobile").toString()
                            +",身份证号是 "+mDatas.get(position).get("card_id").toString()+"。");
                }
            }else {
                holder.name.setText("    该病例曾与其非家人密接 "+mDatas.get(position).get("name").toString()+" "+stringBuffer.toString()+"，后者手机号是 "+mDatas.get(position).get("mobile").toString()+"。");
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
