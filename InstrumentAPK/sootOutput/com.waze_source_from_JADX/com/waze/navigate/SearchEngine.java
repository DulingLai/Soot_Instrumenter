package com.waze.navigate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchEngine {
    public static final int ERROR_STATE_ERROR_NONE = 0;
    public static final int ERROR_STATE_ERROR_NORETRY = 1;
    public static final int ERROR_STATE_ERROR_RETRY = 2;
    private static final int RESULTS_INIT_CAPACITY = 32;
    private SearchButton button;
    public boolean canShowOnMap;
    private int errorState;
    private int id;
    private String name;
    private String provider;
    public boolean requestedResultEta;
    private List<AddressItem> results;
    private boolean searched;
    private boolean searching;

    class C21491 implements Comparator<AddressItem> {
        C21491() throws  {
        }

        public int compare(AddressItem $r1, AddressItem $r2) throws  {
            if ($r1.sponsored != $r2.sponsored) {
                if ($r1.sponsored) {
                    return -1;
                }
                return 1;
            } else if ($r1.price <= $r2.price) {
                return $r1.price == 0.0f ? 1 : -1;
            } else {
                if ($r2.price != 0.0f) {
                    return 1;
                }
                return -1;
            }
        }
    }

    class C21502 implements Comparator<AddressItem> {
        C21502() throws  {
        }

        public int compare(AddressItem $r1, AddressItem $r2) throws  {
            return $r1.sponsored != $r2.sponsored ? $r1.sponsored ? -1 : 1 : $r1.distanceMeters > $r2.distanceMeters ? 1 : -1;
        }
    }

    public SearchEngine(int $i0, String $r1, String $r2, boolean $z0, SearchButton $r3) throws  {
        this.results = new ArrayList(32);
        this.errorState = 0;
        this.requestedResultEta = false;
        this.id = $i0;
        this.name = $r1;
        this.provider = $r2;
        this.button = $r3;
        this.searched = false;
        this.searching = false;
        this.canShowOnMap = $z0;
    }

    public SearchEngine(int $i0, String $r1, String $r2, boolean $z0) throws  {
        this($i0, $r1, $r2, $z0, null);
    }

    public AddressItem[] getResults() throws  {
        AddressItem[] $r1 = new AddressItem[this.results.size()];
        this.results.toArray($r1);
        return $r1;
    }

    public void addResult(AddressItem $r1) throws  {
        this.results.add($r1);
    }

    public void finalizeSearch() throws  {
        boolean $z0 = false;
        this.searching = false;
        if (this.results.size() > 0) {
            $z0 = true;
        }
        this.searched = $z0;
    }

    public void setResults(AddressItem[] $r1) throws  {
        this.searching = false;
        if ($r1 == null) {
            clear();
            return;
        }
        this.searched = true;
        this.results.clear();
        Collections.addAll(this.results, $r1);
    }

    public AddressItem getResult(int $i0) throws  {
        return (AddressItem) this.results.get($i0);
    }

    public void setResult(int $i0, AddressItem $r1) throws  {
        this.results.set($i0, $r1);
    }

    public void clear() throws  {
        this.searched = false;
        this.searching = false;
        this.results.clear();
        setErrorState(0);
    }

    public SearchButton getButton() throws  {
        return this.button;
    }

    public String getName() throws  {
        return this.name;
    }

    public int getId() throws  {
        return this.id;
    }

    public boolean getSearched() throws  {
        return this.searched;
    }

    public void setSearched(boolean $z0) throws  {
        this.searched = $z0;
    }

    public boolean hasError() throws  {
        return this.errorState != 0;
    }

    public int getErrorState() throws  {
        return this.errorState;
    }

    public void setErrorState(int $i0) throws  {
        this.errorState = $i0;
    }

    public String getProvider() throws  {
        return this.provider;
    }

    public void setButton(SearchButton $r1) throws  {
        this.button = $r1;
    }

    public void setId(int $i0) throws  {
        this.id = $i0;
    }

    public boolean getSearching() throws  {
        return this.searching;
    }

    public void setSearching(boolean $z0) throws  {
        if ($z0) {
            clear();
        }
        this.searching = $z0;
    }

    public void sortResults(final SortPreferences $r1) throws  {
        List $r2 = this.results;
        Comparator $r3 = null;
        if ($r1.type == 0) {
            $r3 = r4;
            C21491 r4 = new C21491();
        } else if ($r1.type == 1) {
            $r3 = r5;
            C21502 r5 = new C21502();
        } else if ($r1.type == 2) {
            $r3 = r6;
            C21513 r6 = new Comparator<AddressItem>() {
                public int compare(AddressItem $r1, AddressItem $r2) throws  {
                    if ($r1.sponsored != $r2.sponsored) {
                        return $r1.sponsored ? -1 : 1;
                    } else {
                        boolean $z0;
                        if ($r1.brand == null || $r1.brand.equals("")) {
                            $z0 = true;
                        } else {
                            $z0 = false;
                        }
                        boolean $z1;
                        if ($r2.brand == null || $r2.brand.equals("")) {
                            $z1 = true;
                        } else {
                            $z1 = false;
                        }
                        if ($z0 && $z1) {
                            return $r1.getTitle().compareTo($r2.getTitle());
                        }
                        if ($z0 && !$z1) {
                            return 1;
                        }
                        if (!$z0 && $z1) {
                            return -1;
                        }
                        if ($r1.brand == null || $r1.brand.equals("")) {
                            $z0 = false;
                        } else {
                            $z0 = true;
                        }
                        if ($z0) {
                            $z0 = $r1.brand.equals($r1.brand);
                            if ($z0 != $r2.brand.equals($r1.brand)) {
                                if ($z0) {
                                    return -1;
                                }
                                return 1;
                            }
                        }
                        return $r1.brand.compareTo($r2.brand);
                    }
                }
            };
        }
        Collections.sort($r2, $r3);
        this.results = $r2;
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        $r1.append("SearchEngine: ");
        $r1.append("id = ");
        $r1.append(this.id);
        $r1.append(" name = ");
        $r1.append(this.name);
        $r1.append(" provider = ");
        $r1.append(this.provider);
        return $r1.toString();
    }
}
