package com.abaltatech.mcs.common;

public class MemoryPoolTrackerList {
    public int Count = 0;
    public MemoryPoolTracker Head = null;

    public void insert(MemoryPoolTracker $r1) throws  {
        if ($r1 != null) {
            $r1.m_next = this.Head;
            $r1.m_previous = null;
            if (this.Head != null) {
                this.Head.m_previous = $r1;
            }
            this.Head = $r1;
            this.Count++;
        }
    }

    public void remove(MemoryPoolTracker $r1) throws  {
        if ($r1 != null) {
            if ($r1 == this.Head) {
                this.Head = $r1.m_next;
                if (this.Head != null) {
                    this.Head.m_previous = null;
                }
                $r1.m_next = null;
            } else {
                if ($r1.m_previous != null) {
                    $r1.m_previous.m_next = $r1.m_next;
                }
                if ($r1.m_next != null) {
                    $r1.m_next.m_previous = $r1.m_previous;
                }
                $r1.m_previous = null;
                $r1.m_next = null;
            }
            this.Count--;
        }
    }
}
