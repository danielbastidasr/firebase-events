package uk.co.xakra.erasmusvipvalencia.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dabasra on 09/08/2017.
 */


public class Tickets implements Parcelable {
    private String price;
    private String name;
    private String url;
    private String id;
    private String info;
    private String day;
    private String hour;
    public Tickets(){}

    public Tickets (String price, String name, String url, String info){
        this.price = price;
        this.name = name;
        this.url = url;
        this.info = info;
        this.day = "";
        this.hour = "";
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(price);
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(id);
        parcel.writeString(info);
        parcel.writeString(day);
        parcel.writeString(hour);
    }

    protected Tickets(Parcel in) {
        price = in.readString();
        name = in.readString();
        url = in.readString();
        id = in.readString();
        info = in.readString();
        day = in.readString();
        hour = in.readString();
    }

    public static final Creator<Tickets> CREATOR = new Creator<Tickets>() {
        @Override
        public Tickets createFromParcel(Parcel in) {
            return new Tickets(in);
        }

        @Override
        public Tickets[] newArray(int size) {
            return new Tickets[size];
        }
    };

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("price", price);
        result.put("name", name);
        result.put("url",url);
        result.put("info",info);
        return result;
    }



    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }


}
