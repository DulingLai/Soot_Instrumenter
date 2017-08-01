package com.waze.autocomplete;

import com.waze.user.PersonBase;

public class Person extends PersonBase implements Comparable {
    private static final long serialVersionUID = 1;

    public Person(String $r1, String $r2, String $r3) throws  {
        this.mNickName = $r1;
        this.mPhone = $r2;
        this.mImageUrl = $r3;
        this.mID = -1;
    }

    public Person(String $r1, String $r2, String $r3, int $i0) throws  {
        this.mNickName = $r1;
        this.mPhone = $r2;
        this.mImageUrl = $r3;
        this.mID = $i0;
    }

    public Person(Person $r1) throws  {
        super($r1);
    }

    public String toString() throws  {
        return this.mNickName;
    }

    public int compareTo(Object $r1) throws  {
        if ($r1 == null) {
            return 1;
        }
        if (!($r1 instanceof Person)) {
            return 1;
        }
        Person $r2 = (Person) $r1;
        if (this.mNickName.compareTo($r2.mNickName) != 0) {
            return 1;
        }
        if (this.mPhone.compareTo($r2.mImageUrl) != 0) {
            return 1;
        }
        if (this.mImageUrl.compareTo($r2.mImageUrl) == 0) {
            return this.mID == $r2.mID ? 0 : 1;
        } else {
            return 1;
        }
    }
}
