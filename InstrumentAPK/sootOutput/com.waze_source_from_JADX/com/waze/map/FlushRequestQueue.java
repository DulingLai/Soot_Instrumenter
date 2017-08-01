package com.waze.map;

import java.util.ArrayList;

public class FlushRequestQueue {
    private final ArrayList<FlushRequest> mRequests = new ArrayList();

    public static class FlushRequest {
        public int frame_id;
        public boolean is_render;
        public int queue_id;
        public boolean request_posted;

        public FlushRequest(int $i0, int $i1, boolean $z0, boolean $z1) throws  {
            this.queue_id = $i0;
            this.frame_id = $i1;
            this.request_posted = $z0;
            this.is_render = $z1;
        }
    }

    public boolean isEmpty() throws  {
        return this.mRequests.size() == 0;
    }

    public int size() throws  {
        return this.mRequests.size();
    }

    public int push(FlushRequest $r1) throws  {
        this.mRequests.add($r1);
        return this.mRequests.size();
    }

    public FlushRequest pop() throws  {
        if (this.mRequests.isEmpty()) {
            return null;
        }
        return (FlushRequest) this.mRequests.remove(0);
    }

    public FlushRequest head() throws  {
        if (this.mRequests.isEmpty()) {
            return null;
        }
        return (FlushRequest) this.mRequests.get(0);
    }

    public void clear() throws  {
        this.mRequests.clear();
    }
}
