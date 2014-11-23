
package jp.baroqueworksdev.myapidemo.data;

import android.os.Parcel;
import android.os.Parcelable;

public class SampleData implements Parcelable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
    }

}
