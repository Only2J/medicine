package com.example.myapplication.food;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class Foods {
    public int status;
    public String msg;
    public ResultDTO result;

    public static class ResultDTO {
        public int total;
        public int num;
        public List<ListDTO> list;

        public static class ListDTO  implements Parcelable {
            public int id;
            public int classid;
            public String name;
            public String peoplenum;
            public String preparetime;
            public String cookingtime;
            public String content;
            public String pic;
            public String tag;
            public List<MaterialDTO> material;
            public List<ProcessDTO> process;

            protected ListDTO(Parcel in) {
                id = in.readInt();
                classid = in.readInt();
                name = in.readString();
                peoplenum = in.readString();
                preparetime = in.readString();
                cookingtime = in.readString();
                content = in.readString();
                pic = in.readString();
                tag = in.readString();
            }

            public static final Creator<ListDTO> CREATOR = new Creator<ListDTO>() {
                @Override
                public ListDTO createFromParcel(Parcel in) {
                    return new ListDTO(in);
                }

                @Override
                public ListDTO[] newArray(int size) {
                    return new ListDTO[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(id);
                parcel.writeInt(classid);
                parcel.writeString(name);
                parcel.writeString(peoplenum);
                parcel.writeString(preparetime);
                parcel.writeString(cookingtime);
                parcel.writeString(content);
                parcel.writeString(pic);
                parcel.writeString(tag);
            }

            public static class MaterialDTO {
                public String mname;
                public int type;
                public String amount;
            }

            public static class ProcessDTO {
                public String pcontent;
                public String pic;
            }
        }
    }
}
