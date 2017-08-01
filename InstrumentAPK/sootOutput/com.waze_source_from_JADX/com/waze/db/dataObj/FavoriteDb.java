package com.waze.db.dataObj;

import dalvik.annotation.Signature;

public class FavoriteDb {

    public enum FavoriteType {
        Default(0),
        Home(1),
        Work(2),
        Other(3);
        
        private int value;

        private FavoriteType(@Signature({"(I)V"}) int $i1) throws  {
            this.value = $i1;
        }
    }
}
