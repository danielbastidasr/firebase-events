package uk.co.xakra.erasmusvipvalencia.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dabasra on 11/08/2017.
 */

public class MyTickets implements Parcelable {


    String img,quantity,day,hour,info,name,id;

    public MyTickets(String img, String quantity, String day, String hour, String info, String name, String id) {
        this.img = img;
        this.quantity = quantity;
        this.day = day;
        this.hour = hour;
        this.info = info;
        this.name = name;
        this.id = id;
    }

    public static final Creator<MyTickets> CREATOR = new Creator<MyTickets>() {
        @Override
        public MyTickets createFromParcel(Parcel in) {
            return new MyTickets(in);
        }

        @Override
        public MyTickets[] newArray(int size) {
            return new MyTickets[size];
        }
    };

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeStringArray(new String[] {
                this.day,this.hour,
                this.info,
                this.name,
                this.img,
                this.quantity,
                this.id
        });

    }

    protected MyTickets( Parcel in ){
        String[] data = new String[7];
        in.readStringArray(data);
        this.day  = data[0];
        this.hour = data[1];
        this.info = data[2];
        this.name = data[3];
        this.img  = data[4];
        this.quantity= data[5];
        this.id = data[6];
    }
}
